# HelixURL — Technical Decisions

**Document ID:** HLX-TDD-001  
**Version:** 0.1.0  
**Status:** Approved — M2 Design Baseline  
**Project:** HelixURL  
**Milestone:** M2 — System Design  
**Last Updated:** 2026-11-22

## 1. Purpose

This document records the approved technical decisions required to implement the HelixURL MVP.

It complements:

- `architecture.md`
- `database-design.md`
- `../03-api/api-design.md`
- `../03-api/api-specification.yaml`
- `../01-requirements/decision-log.md`

The requirements decision log remains the authoritative record of business and policy decisions. This document records implementation strategies and technical trade-offs.

## 2. Decision Summary

| ID | Topic | Approved Decision |
|---|---|---|
| TD-001 | Architecture | Spring Boot modular monolith |
| TD-002 | Short-code generation | SecureRandom, Base62, 8 characters |
| TD-003 | Collision handling | Unique constraint and bounded transactional retries |
| TD-004 | URL parsing | java.net.URI and dedicated validation policy |
| TD-005 | Timestamp handling | RFC 3339 input, Instant internally, TIMESTAMPTZ persistence |
| TD-006 | Rate limiting | 10 creation requests/minute/client |
| TD-007 | Request size | Maximum 8 KiB creation request |
| TD-008 | Redirect caching | Cache-Control: no-store |
| TD-009 | Operator control | Restricted internal maintenance command |
| TD-010 | Database access | Spring Data JPA and Flyway |
| TD-011 | Testing | Unit, integration, API and end-to-end tests |
| TD-012 | Deployment | Single-instance MVP initially |

The technical decisions in this document are approved for the initial MVP implementation. Any material changes must be reviewed and reflected in the relevant requirements, API, database, and testing documentation.

## 3. Short-Code Generation

### TD-002 — Secure Random Base62

The application will generate fixed-length short codes using the following alphabet:

`0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz`

The initial code length will be eight characters.

A cryptographically secure random number generator, such as `java.security.SecureRandom`, will select each character.

The generation process must not expose internal database identifiers.

The code space contains:

`62^8 = 218,340,105,584,896`

possible values.

The short-code namespace is case-sensitive.

Random generation reduces predictability but does not eliminate collisions. PostgreSQL remains responsible for enforcing uniqueness.

The generator must not use `java.util.Random` or predictable timestamp-based values.

## 4. Collision Handling

### TD-003 — Bounded Transactional Retry

The `short_code` column must have a database unique constraint.

The application will attempt to persist a mapping using a generated candidate.

If and only if the insert fails because of the short-code unique constraint, the application may generate a new candidate and retry.

The maximum number of persistence attempts will be five.

Each failed database transaction must be rolled back before another attempt.

The retry coordinator must not continue using an aborted PostgreSQL transaction.

A retry implementation may use Spring transaction propagation or programmatic transaction management.

Other persistence failures must not be misclassified as short-code collisions.

If all attempts fail, the operation returns a server-side failure without claiming successful creation.

A lost connection around transaction commit may create an ambiguous outcome. The MVP does not promise exactly-once creation semantics.

## 5. URL Validation

### TD-004 — URI Parsing and Validation Policy

The backend will use `java.net.URI` for structural parsing.

A dedicated validation policy will enforce application requirements.

The policy must:

1. Accept only HTTP and HTTPS schemes.
2. Require an absolute URI with a valid host.
3. Reject userinfo credentials.
4. Reject malformed authority and host representations.
5. Reject control characters and invalid request values.
6. Reject prohibited loopback, private, link-local and special-use IP literal destinations.
7. Enforce the maximum submitted URL length of 2,048 characters.
8. Preserve accepted destination semantics.
9. Avoid server-side destination fetching during URL creation or redirection.

The exact treatment of internationalised domain names, percent encoding, IPv6 literals and other URL edge cases will be specified in implementation tests.

The destination safety policy is a baseline abuse-prevention measure, not a guarantee that every accepted URL is safe.

## 6. Timestamp and Expiration

### TD-005 — Absolute Time Semantics

The creation API will accept an optional RFC 3339 timestamp containing an explicit UTC offset.

Example:

`2026-12-01T10:00:00+10:00`

Timestamp values without a timezone offset will be rejected.

The API will use second-level precision for submitted expiration timestamps.

The backend will convert accepted timestamps into `java.time.Instant`.

PostgreSQL will store timestamps using `TIMESTAMPTZ`.

A mapping expires when:

`now >= expiresAt`

The expiration timestamp must be strictly after the defined creation reference instant and no later than 365 days after that instant.

The application will use an injected `Clock` to obtain consistent time references and support deterministic testing.

No scheduled expiration-status update is required.

## 7. Rate Limiting

### TD-006 — Anonymous Creation Rate Limit

The initial policy will permit up to ten creation requests per minute per client.

Requests exceeding the limit will receive HTTP 429 Too Many Requests.

Where appropriate, the response will include a `Retry-After` header.

For a single-instance MVP, an in-memory rate limiter is acceptable.

The implementation must document that counters are not shared across instances and reset after restart.

Client identity must be derived from trusted connection information.

Forwarded headers must be trusted only when supplied through an explicitly configured trusted proxy.

Rate limiting must be applied before mapping creation.

## 8. Request Payload Limits

### TD-007 — Creation Request Size

The maximum creation request body size will be 8 KiB (8,192 bytes).

Oversized requests will be rejected with HTTP 413 Content Too Large.

The limit must be enforced before unbounded request-body buffering or deserialisation.

The implementation must distinguish payload byte size from the 2,048-character destination URL limit.

## 9. Redirect Caching

### TD-008 — No-Store Redirect Responses

Successful HTTP 302 redirect responses and HTTP 404 unavailable-link responses will include:

`Cache-Control: no-store`

The system will return HTTP 302 Found and a `Location` header containing the stored destination URL.

The cache policy reduces the risk of stale redirects after expiration or administrative disabling.

It does not guarantee revocation of responses previously cached by clients or intermediaries.

## 10. Operator Disabling

### TD-009 — Restricted Maintenance Command

The MVP will provide an internal operational mechanism to disable mappings by short code.

The initial implementation will use a restricted maintenance command rather than a publicly exposed administrative REST endpoint.

The command must:

- Require access to an authorised operational environment.
- Identify a mapping by short code.
- Persist its disabled state.
- Report a clear result without exposing sensitive information.
- Avoid public access to administrative operations.

Operational access controls and audit requirements must be documented before public deployment.

## 11. Persistence

### TD-010 — JPA and Flyway

The backend will use Spring Data JPA for persistence.

PostgreSQL will enforce primary key, uniqueness and structural constraints.

Flyway will manage version-controlled schema migrations.

The initial migration will create the `url_mappings` table.

Hibernate schema management will use validation rather than automatic production schema creation.

The application will not introduce Redis, a message broker or additional databases for the initial MVP.

## 12. Testing

### TD-011 — Verification Strategy

The backend will use:

- JUnit for automated tests.
- Mockito for isolated unit tests where appropriate.
- Spring Boot Test for application and HTTP integration tests.
- Testcontainers with PostgreSQL for persistence integration tests.

Important scenarios include:

- Valid and invalid destination URLs.
- URL length boundaries.
- Expiration boundaries.
- Short-code uniqueness.
- Transactional collision retries.
- Concurrent creation requests.
- HTTP 201 creation responses.
- HTTP 302 redirects.
- HTTP 404 unavailable links.
- Rate limiting and oversized requests.
- Database failures and ambiguous persistence outcomes.
- Restricted operator disabling.

## 13. Initial Deployment Model

### TD-012 — Single-Instance MVP

The initial MVP will use one Spring Boot application instance and one PostgreSQL database.

Local development will use Docker Compose for PostgreSQL.

The production deployment provider remains undecided.

The architecture must not assume that an in-memory rate limiter remains globally accurate after horizontal scaling.

Public deployment requires HTTPS, safe configuration management, appropriate health checks and operational controls.

## 14. Implementation Order

Backend development should proceed through the following vertical slices:

1. Repository and Spring Boot project setup.
2. PostgreSQL, Docker Compose and Flyway migration.
3. URL creation and persistence.
4. Short-code generation and collision handling.
5. Redirect resolution and expiration.
6. Validation, error handling and abuse prevention.
7. Restricted operator disabling.
8. Integration tests, CI and release preparation.

Testing should be added throughout implementation rather than deferred until the end.

## 15. Remaining Implementation and Release Dependencies

The M2 technical decisions are approved. The following implementation and release details remain open:

- BD-009: mapping, operational log and deletion/retention policy before public release.
- Exact Unicode URL character-counting convention, aligned with PostgreSQL storage.
- IDN, IPv6 and special-use address validation edge cases and test coverage.
- Frontend/static asset reserved routes when frontend routing is designed.
- Operator CLI environment access controls and appropriate audit evidence.
- Production hosting, HTTPS, trusted proxies and client-identification configuration.
- Measurable performance objectives and browser support targets.
- Ambiguous commit outcome handling and safe database failure reporting.

These do not block initial Spring Boot project setup. They must be resolved before their dependent functionality or public release.

## 16. Approval Criteria

This design is approved as the M2 implementation baseline, subject to the following implementation readiness checks:

- Approved technical decisions are reflected in the requirements decision log.
- The requirements decision log has been reconciled.
- API and database designs remain consistent.
- Security decisions needed by the first backend slices are identified.
- The team can begin implementation without unresolved architectural blockers.

---

## Document History

| Version | Date | Description |
|---|---|---|
| 0.1.0 | 2026-11-22 | Initial technical decisions proposal |
| 0.1.1 | 2026-11-22 | Approved M2 decisions and remaining implementation dependencies |
