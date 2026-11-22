# HelixURL — High-Level Architecture

**Document ID:** HLX-ARC-001  
**Version:** 0.2.0  
**Status:** Approved — M2 Design Baseline
**Project:** HelixURL  
**Milestone:** M2 — System Design  
**Last Updated:** 2026-11-22

## 1. Purpose

This document defines the initial high-level software architecture for the HelixURL MVP.

The architecture supports anonymous URL shortening, persistent URL mappings, optional expiration, HTTP redirection, and basic operational controls.

It is intended to guide database design, API design, implementation, and testing.

## 2. Architectural Goals

The architecture should:

1. Satisfy the approved MVP requirements.
2. Remain simple enough for a single developer to implement and maintain.
3. Separate HTTP concerns from application and domain logic.
4. Persist mappings reliably in PostgreSQL.
5. Enforce short-code uniqueness under concurrent requests.
6. Support deterministic expiration behaviour.
7. Allow automated unit and integration testing.
8. Support reproducible local development and deployment.
9. Avoid unnecessary distributed-system complexity.
10. Remain adaptable to future requirements.

## 3. Architecture Style

**Selected architecture:** Modular Monolith.

The backend will be implemented as one deployable Spring Boot application with clear internal module boundaries.

The application will use a layered structure influenced by Clean Architecture and Hexagonal Architecture principles, without requiring a full framework-heavy implementation of either pattern.

The initial backend layers are:

- Presentation: HTTP controllers, request/response models, validation and error mapping.
- Application: use-case orchestration and transaction boundaries.
- Domain: business concepts, invariants and policies.
- Infrastructure: database persistence, configuration and external adapters.

Domain rules should not depend on Spring MVC controllers or HTTP-specific request and response classes.

The architecture does not require microservices, an API gateway, message broker, distributed cache or event streaming infrastructure for the MVP.

## 4. Technology Stack

| Area | Technology | Responsibility |
|---|---|---|
| Backend language | Java 25 LTS | Application implementation |
| Backend framework | Spring Boot | HTTP API and application runtime |
| Build | Maven | Dependency and build management |
| Persistence | PostgreSQL | Durable mapping storage |
| Data access | Spring Data JPA | Persistence integration |
| Schema migrations | Flyway | Version-controlled database changes |
| Local infrastructure | Docker Compose | Reproducible PostgreSQL environment |
| API contract | OpenAPI 3.x | Interface specification |
| Testing | JUnit 5, Spring Boot Test, Testcontainers | Unit and integration verification |
| CI | GitHub Actions | Automated build and tests |
| Frontend | React candidate | Minimal browser interface |

Frontend framework selection remains a separate implementation decision.

## 5. System Context

The MVP interacts with the following actors and systems:

**Anonymous Creator:** Submits a destination URL and optionally an expiration timestamp.

**Link Visitor:** Opens a short URL and receives a redirect or unavailable-link response.

**Project Operator:** Performs authorised operational actions, including disabling abusive mappings.

**PostgreSQL:** Stores persistent URL mapping data.

The system does not fetch destination webpage content as part of normal URL creation or redirection.

## 6. Logical Components

### 6.1 URL Creation Controller

Accepts URL creation requests, performs HTTP-level validation, invokes the application service, and returns the creation response.

### 6.2 Redirect Controller

Receives short-code requests and returns either:

- HTTP 302 Found with the destination in the Location header.
- HTTP 404 Not Found for unknown, expired or disabled mappings.

### 6.3 URL Application Service

Coordinates URL creation and resolution use cases.

Responsibilities include:

- Enforcing application-level business policies.
- Requesting short-code generation.
- Managing persistence operations.
- Evaluating expiration and disabled state.
- Returning results independently of HTTP presentation details.

### 6.4 Short-Code Generator

Produces candidate short codes.

The short-code generator produces eight-character, case-sensitive Base62 codes using SecureRandom. PostgreSQL enforces uniqueness, and confirmed short-code collisions are handled through a maximum of five transactional persistence attempts.

Database uniqueness constraints remain the authoritative protection against duplicate short codes.

### 6.5 URL Validation Policy

Enforces approved destination acceptance rules.

The policy must support HTTP/HTTPS restrictions, maximum destination length and any additional approved destination safety constraints.

Validation must not depend on fetching the destination webpage.

### 6.6 URL Mapping Repository

Provides persistence operations for URL mappings.

Application services should depend on a repository abstraction rather than direct SQL or HTTP-specific objects.

### 6.7 Operational Components

Provide health checks, safe diagnostic logging, configuration management and restricted administrative controls.

Operator disabling uses a restricted internal maintenance command; operational access and audit controls will be verified before public release.

## 7. Core Data Concept

The central domain concept is a URL mapping.

An initial mapping is expected to contain:

- A persistent identifier.
- A unique short code.
- The original destination URL.
- A creation timestamp.
- An optional expiration timestamp.
- An administrative disabled state.

This list is conceptual. Column types, constraints, indexes and exact schema details will be defined in `database-design.md`.

## 8. Core Processing Flows

### 8.1 Create Short URL

1. Receive an anonymous creation request.
2. Validate request structure and destination URL.
3. Apply relevant business and abuse-prevention policies.
4. Generate a candidate short code.
5. Attempt to persist the mapping.
6. Resolve uniqueness collisions using a bounded retry strategy.
7. Return the generated short URL only after successful persistence.

If a persistence operation fails, the application must not claim successful creation without confirmation.

Ambiguous outcomes after connection failures require explicit handling during detailed design.

### 8.2 Resolve Short URL

1. Receive a short-code request.
2. Look up the corresponding mapping.
3. Return an unavailable result if no mapping exists.
4. Reject administratively disabled mappings.
5. Reject mappings whose expiration instant has been reached.
6. Return the stored destination for eligible mappings.
7. Translate the result into HTTP 302 or HTTP 404.

Expiration is evaluated using a consistent time source.

### 8.3 Disable Mapping

1. Receive an authorised operational request.
2. Identify the target mapping.
3. Verify the request is permitted.
4. Persist the disabled state.
5. Ensure subsequent resolution requests no longer redirect.

The operator interface is a restricted internal maintenance command. Environment access controls and audit procedures remain implementation tasks.

## 9. Data Integrity and Concurrency

PostgreSQL will enforce short-code uniqueness.

Application-level collision detection alone is insufficient under concurrent creation requests.

The implementation must account for transaction rollback and retry boundaries when a unique constraint violation occurs.

Duplicate destination URLs are permitted and may produce different short codes.

## 10. Error Handling

The presentation layer will translate application outcomes into documented HTTP responses.

Known validation errors should produce consistent client-facing responses.

Unexpected failures must not expose stack traces, credentials or internal implementation details.

Dependency failures must not be misrepresented as successful operations.

## 11. Security Considerations

The architecture must support:

- Anonymous request rate limiting.
- HTTP/HTTPS destination restrictions.
- Destination safety validation.
- Request size limits.
- Safe handling of secrets and configuration.
- Restricted operator functionality.
- HTTPS in public deployment.
- Safe logging and error responses.
- Abuse reporting and administrative disabling.

Some security thresholds and implementation mechanisms remain open and must be resolved before the corresponding functionality is released publicly.

## 12. Testing Strategy

**Unit tests:** Domain policies, expiration boundaries, validation and short-code behaviour.

**Integration tests:** PostgreSQL persistence, uniqueness constraints, migrations and transactional behaviour.

**API tests:** HTTP status codes, response bodies, validation errors and redirect headers.

**End-to-end tests:** Browser creation and redirect workflows.

**Operational tests:** Application startup, configuration and health behaviour.

## 13. Deployment Considerations

The MVP should support:

- Local development using Docker Compose for PostgreSQL.
- Environment-based configuration.
- Version-controlled database migrations.
- Automated CI verification.
- HTTPS for public access.
- A documented deployment procedure.

Hosting provider, reverse proxy and frontend hosting decisions remain open.

## 14. Remaining Implementation and Release Decisions

The M2 architecture, schema and API contract have been approved. Remaining details include:

- URL character-counting, IDN, IPv6 and special-use IP validation tests.
- Rate-limit enforcement implementation and trusted client identification.
- Operator command access controls and audit procedure.
- Frontend/static route reservations.
- Production deployment topology and HTTPS configuration.
- Performance targets and representative workloads.
- Mapping/log retention and deletion policy before public release.

## 15. Architecture Review Criteria

This design is ready for implementation when:

- Component responsibilities are clear.
- The domain model and database schema are defined.
- The REST API contract is documented.
- Core transaction and concurrency behaviour is specified.
- Security decisions required by the first implementation slice are resolved.
- The initial backend package structure is agreed upon.

---

## Document History

| Version | Date | Description |
|---|---|---|
| 0.1.0 | 2026-11-15 | Initial high-level architecture proposal |
| 0.2.0 | 2026-11-22 | Updated architecture based on M2 design review |
