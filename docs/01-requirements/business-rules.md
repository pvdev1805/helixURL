# HelixURL — Business Rules Specification

**Document ID:** HLX-BRS-001  
**Version:** 0.1.0  
**Status:** Approved — M1 Requirements Baseline
**Project:** HelixURL  
**Target Release:** MVP v0.1.0  
**Document Owner:** Project Owner  
**Last Updated:** 2026-11-10

**Related Documents:**
- [Software Requirements Specification](./requirements.md)
- [Project Scope Statement](../00-initiation/scope.md)
- [Risk Register](../00-initiation/risk-register.md)
- [Use Cases](./use-cases.md) *(planned)*
- [Acceptance Criteria](./acceptance-criteria.md) *(planned)*

---

## 1. Purpose

This document defines the business rules governing the creation, validation, persistence, resolution, expiration, and operational management of shortened URLs within the HelixURL Minimum Viable Product (MVP).

Business rules establish the conditions, constraints, and policies that determine whether an operation is permitted and how the system must behave in specific circumstances.

This document aims to:

- Remove ambiguity from functional requirements.
- Define consistent behaviour across API and web interfaces.
- Establish domain invariants.
- Support testable acceptance criteria.
- Provide a foundation for database and API design.
- Identify policies requiring further review.
- Maintain traceability to the Software Requirements Specification.

This document does not prescribe detailed implementation algorithms, database schema definitions, or framework-specific solutions.

## 2. Scope and Authority

These rules apply to the HelixURL MVP and its publicly accessible URL shortening and redirection workflows.

The rules cover:

- Destination URL eligibility.
- URL creation.
- Short-code uniqueness.
- Duplicate destinations.
- Optional expiration.
- Redirect eligibility.
- Link disabling.
- Error disclosure.
- Abuse prevention.
- Data retention and privacy.
- Operational behaviour.

The approved Project Scope Statement defines the overall product boundary.

The Software Requirements Specification defines mandatory system capabilities.

This document provides the detailed business policies required to interpret and verify those capabilities.

## 3. Rule Identification and Classification

Each rule has a unique identifier using the format `BR-XXX`.

Rules are classified as:

| Classification | Meaning |
|---|---|
| Validation | Determines whether submitted input is acceptable |
| Creation | Governs new URL mapping creation |
| Integrity | Protects mapping correctness and uniqueness |
| Lifecycle | Governs expiration and availability |
| Redirection | Determines whether and how a redirect is permitted |
| Security | Restricts abuse or unsafe operations |
| Privacy | Governs information collection and exposure |
| Operational | Governs service administration and recovery |

Rule status values:

- **Proposed:** Awaiting review or confirmation.
- **Approved:** Accepted as part of the requirements baseline.
- **Deferred:** Requires a later design or operational decision.
- **Superseded:** Replaced by a documented later rule.

The M1 baseline is approved; M2 decisions are reconciled in `decision-log.md`. Explicitly open retention and operational details remain tracked.

## 4. Destination URL Validation Rules

### BR-001 — Destination URL Is Required

**Classification:** Validation  
**Related Requirements:** FR-001, FR-006

A URL creation request must contain a destination URL.

Missing, null, or empty destination values must be rejected.

### BR-002 — Destination Must Be an Absolute URL

**Classification:** Validation  
**Related Requirements:** FR-007

A destination URL must be an absolute URL containing a valid scheme and host.

Relative paths and scheme-relative references must not be accepted as destination URLs.

Examples:

- Accepted format: `https://example.com/page`
- Rejected format: `/products/123`
- Rejected format: `//example.com/page`

Acceptance remains subject to all other validation and security rules.

### BR-003 — Allowed URL Schemes

**Classification:** Validation, Security  
**Related Requirements:** FR-008, SEC-003

Only HTTP and HTTPS destination URLs are eligible for shortening.

Other schemes, including `javascript:`, `file:`, `data:`, and `ftp:`, must be rejected.

Scheme comparison must follow the relevant case-insensitive URI scheme rules.

### BR-004 — Maximum Destination URL Length

**Classification:** Validation  
**Related Requirements:** FR-009

The maximum accepted destination URL length is **2,048 characters**.

A destination URL exceeding the configured maximum must be rejected.

The exact counting convention and treatment of encoded characters must be defined consistently in the API contract.

**Status:** Approved — BD-001.

### BR-005 — Destination Host Requirement

**Classification:** Validation  
**Related Requirements:** FR-007, FR-010

An accepted destination URL must contain a syntactically valid host.

Malformed host representations and URLs without a valid host must be rejected.

### BR-006 — Destination URL Preservation

**Classification:** Validation, Integrity  
**Related Requirements:** FR-017, DR-001

The system must preserve the accepted destination URL's intended navigation semantics.

It must not silently remove path components, query parameters, or fragments.

Any URL normalisation must be documented and must not unexpectedly change the destination.

### BR-007 — URL Fragment Handling

**Classification:** Validation  
**Related Requirements:** FR-007, FR-017

Destination URLs containing fragments may be accepted.

The fragment must be preserved as part of the destination URL when a redirect is generated.

Example:

`https://example.com/docs#installation`

The fragment `#installation` must not be silently discarded.

### BR-008 — Unsafe Destination Restrictions

**Classification:** Security  
**Related Requirements:** FR-010, SEC-004

Destination URLs violating the approved destination acceptance policy must be rejected.

The policy must consider:

- Localhost and loopback destinations.
- Private and reserved network destinations.
- Unsupported or malformed host representations.
- Destinations prohibited by applicable service policies.
- Other destination patterns identified through security analysis.

The precise handling of public DNS names, IP literals, and special-use addresses requires further security design.

**Status:** Proposed — Detailed policy remains open.

### BR-009 — No Mandatory Destination Availability Check

**Classification:** Validation, Operational  
**Related Requirements:** SEC-011

The system is not required to contact or verify the availability of the destination website before accepting an otherwise eligible URL.

Successful shortening does not guarantee that the destination website is reachable, trustworthy, or safe.

The core MVP must not rely on server-side destination fetching.

## 5. URL Creation Rules

### BR-010 — Anonymous Creation

**Classification:** Creation  
**Related Requirements:** FR-001, FR-033

Eligible clients may create shortened URLs without registration or authentication, subject to validation and abuse prevention controls.

### BR-011 — Successful Creation Requires Persistence

**Classification:** Creation, Integrity  
**Related Requirements:** FR-003, FR-005

A creation request must not be reported as successful until the new mapping has been persisted successfully.

If persistence fails, the operation must return an appropriate failure response.

### BR-012 — Independent Mapping Creation

**Classification:** Creation  
**Related Requirements:** FR-004

Each accepted creation request creates a new URL mapping.

The system must not automatically return an existing mapping solely because its destination URL matches a previously submitted destination.

### BR-013 — Duplicate Destinations Are Permitted

**Classification:** Creation, Integrity  
**Related Requirements:** FR-004, DR-001

Multiple mappings may reference the same destination URL.

Destination URL equality must not be treated as a uniqueness constraint.

### BR-014 — Short-Code Uniqueness

**Classification:** Integrity  
**Related Requirements:** FR-012, FR-013, DR-002, DR-006

Every persisted short code must uniquely identify one URL mapping within the active namespace.

Two different mappings must not share the same short code.

### BR-015 — Short-Code Collision Handling

**Classification:** Integrity  
**Related Requirements:** FR-014

A short-code generation conflict must not overwrite or modify an existing mapping.

The system may retry generation or reject the creation operation according to the approved technical design.

The collision handling strategy must preserve database integrity under concurrent requests.

### BR-016 — Short-Code Format

**Classification:** Integrity  
**Related Requirements:** FR-012, FR-015

MVP short codes must be eight-character, case-sensitive Base62 values generated using `SecureRandom` (BD-004). Database uniqueness and bounded retries protect against collisions.

The specific format and generation algorithm will be determined during M2 — System Design.

**Status:** Approved — BD-004.

### BR-017 — Short URL Construction

**Classification:** Creation  
**Related Requirements:** FR-002, FR-003

A successfully generated short URL must contain a publicly resolvable short code and use the configured public base URL.

The production short URL must use HTTPS.

The system must not construct public short URLs using untrusted client-supplied host values.

### BR-018 — No Public Link Ownership

**Classification:** Creation, Security  
**Related Requirements:** FR-033, FR-039

Anonymous creators do not receive account-based ownership of their created mappings.

Knowledge of a short code does not grant permission to modify, disable, or delete the mapping.

## 6. Expiration Rules

### BR-019 — Optional Expiration

**Classification:** Lifecycle  
**Related Requirements:** FR-022

A creation request may specify an expiration timestamp.

Expiration is optional for the MVP.

### BR-020 — Default Non-Expiring Mapping

**Classification:** Lifecycle  
**Related Requirements:** FR-023

If no expiration is supplied, the mapping must have no scheduled expiration timestamp.

This does not guarantee permanent service availability.

A mapping may still become unavailable because of administrative disabling, retention policy, infrastructure failure, or service termination.

### BR-021 — Expiration Must Be in the Future

**Classification:** Validation, Lifecycle  
**Related Requirements:** FR-024

A supplied expiration timestamp must be strictly later than the request's defined evaluation time.

An expiration timestamp in the past or equal to the evaluation time must be rejected.

### BR-022 — Maximum Expiration Duration

**Classification:** Validation, Lifecycle  
**Related Requirements:** FR-024

A user-specified expiration timestamp must not exceed **365 days** from the defined creation-time reference.

A request exceeding this limit must be rejected.

**Status:** Approved — BD-002.

### BR-023 — Expiration Timestamp Format

**Classification:** Validation, Lifecycle  
**Related Requirements:** FR-024, DR-008

Expiration input must use an unambiguous timestamp representation with an explicit timezone offset.

RFC 3339 timestamps with explicit UTC offset and second-level precision are required (BD-003).

Examples:

- `2026-12-01T10:00:00Z`
- `2026-12-01T20:00:00+10:00`

The API specification must define the accepted precision and format.

### BR-024 — Consistent Time Comparison

**Classification:** Lifecycle  
**Related Requirements:** FR-025, FR-026, DR-008

Expiration must be evaluated using a consistent time reference.

Expiration comparisons use UTC instants (BD-003, TD-005).

A mapping is expired when:

`current_time >= expires_at`

### BR-025 — Expired Links Cannot Redirect

**Classification:** Lifecycle, Redirection  
**Related Requirements:** FR-019, FR-025

Once a mapping has expired, the system must not redirect requests to its stored destination.

Expiration enforcement must not depend on the physical deletion of the database record.

### BR-026 — Expiration Is Not Immediate Deletion

**Classification:** Lifecycle, Operational  
**Related Requirements:** DR-004, DR-010

Expiration changes a mapping's redirect eligibility.

It does not require immediate removal of the mapping from persistent storage.

Retention and cleanup policies will be defined separately.

## 7. Redirection Rules

### BR-027 — Redirect Only Existing Mappings

**Classification:** Redirection  
**Related Requirements:** FR-015, FR-016, FR-018

The system may redirect only when the requested short code resolves to an existing mapping.

Unknown short codes must not redirect.

### BR-028 — Redirect Only Eligible Mappings

**Classification:** Redirection, Lifecycle  
**Related Requirements:** FR-016, FR-019, FR-020

A mapping is eligible for redirection only when:

1. The mapping exists.
2. The mapping is not administratively disabled.
3. The mapping has not expired.

All conditions must be satisfied.

### BR-029 — Redirect to the Stored Destination

**Classification:** Redirection, Integrity  
**Related Requirements:** FR-017

An eligible short code must redirect to the destination associated with its persisted mapping.

The system must not substitute another mapping's destination.

### BR-030 — Redirect HTTP Semantics

**Classification:** Redirection  
**Related Requirements:** FR-016, FR-021, IR-004

The MVP redirect response is **HTTP 302 Found**.

The redirect response must contain a valid `Location` header identifying the stored destination.

Redirect cache behaviour must be explicitly defined in the API specification.

**Status:** Approved — BD-005 and BD-012. HTTP 302 and 404 responses include `Cache-Control: no-store`.

### BR-031 — Unknown, Expired, and Disabled Link Responses

**Classification:** Redirection, Security  
**Related Requirements:** FR-018, FR-019, FR-020, FR-034

The public response for unknown, expired, and disabled links is **HTTP 404 Not Found**.

Public responses should avoid unnecessarily disclosing whether a mapping exists but has expired or been disabled.

Operational diagnostics may distinguish these conditions internally, subject to logging and privacy policies.

**Status:** Approved — BD-006.

### BR-032 — External Destination Independence

**Classification:** Redirection, Operational  
**Related Requirements:** FR-016

HelixURL is not responsible for the availability or content of external destination websites.

A redirect response does not guarantee that the destination will load successfully.

## 8. Link Management and Operational Rules

### BR-033 — No Public Editing

**Classification:** Security, Operational  
**Related Requirements:** FR-039

The MVP must not expose an unrestricted public operation for changing an existing mapping's destination, short code, or expiration.

### BR-034 — No Public Deletion

**Classification:** Security, Operational  
**Related Requirements:** FR-039

The MVP must not expose an unrestricted public operation for deleting mappings.

### BR-035 — Controlled Administrative Disabling

**Classification:** Security, Operational  
**Related Requirements:** FR-020, FR-039, SEC-007

The Project Operator must have a controlled mechanism for disabling a mapping when necessary for abuse prevention or operational safety.

The mechanism must not be available to unauthorised public clients.

### BR-036 — Disabled Links Cannot Redirect

**Classification:** Lifecycle, Redirection  
**Related Requirements:** FR-020

An administratively disabled mapping must not redirect, regardless of whether its expiration timestamp has been reached.

### BR-037 — No Automatic Public Re-Enablement

**Classification:** Security, Operational  
**Related Requirements:** FR-039

Anonymous users must not be able to re-enable a disabled mapping.

Any operational re-enablement procedure must be explicitly authorised and documented if implemented.

## 9. Abuse Prevention Rules

### BR-038 — Public Creation Is Subject to Rate Limiting

**Classification:** Security  
**Related Requirements:** FR-036, SEC-001, SEC-002

Anonymous URL creation requests must be subject to documented request rate limits or equivalent protective controls.

The exact thresholds, enforcement scope, and storage mechanism remain to be determined.

### BR-039 — Excessive Requests Must Be Rejected

**Classification:** Security  
**Related Requirements:** FR-036, SEC-002

Requests exceeding the applicable rate limit must receive an appropriate HTTP error response.

HTTP 429 Too Many Requests is required when the approved limit of 10 creation requests/minute/client is exceeded (BD-007).

### BR-040 — Abuse Controls Before Public Exposure

**Classification:** Security, Operational  
**Related Requirements:** SEC-001, SEC-004, SEC-009

Public anonymous URL creation must not be enabled until mandatory baseline abuse controls and response procedures have been implemented and verified.

Local development and controlled testing may continue before this release condition is satisfied.

### BR-041 — Reported Abusive Links

**Classification:** Security, Operational  
**Related Requirements:** FR-039, SEC-009

The Project Operator must have a documented process for investigating reported abusive links and disabling confirmed abusive mappings.

The MVP does not require a public abuse-reporting dashboard.

### BR-042 — Request Size Restrictions

**Classification:** Security  
**Related Requirements:** FR-007, SEC-001

The system must enforce reasonable request size limits to reduce unnecessary resource consumption.

The exact maximum request payload size remains to be determined.

## 10. Data and Privacy Rules

### BR-043 — Minimum Required Mapping Data

**Classification:** Privacy, Integrity  
**Related Requirements:** DR-001, DR-002, DR-003, DR-004, DR-005

Each mapping must contain sufficient information to support:

- Destination resolution.
- Short-code uniqueness.
- Creation-time recording.
- Optional expiration.
- Administrative disabling.

Additional data must have a documented purpose.

### BR-044 — No Mandatory Visitor Tracking

**Classification:** Privacy  
**Related Requirements:** DR-009

The MVP must not require advanced visitor tracking, geolocation analytics, device fingerprinting, or user profiling.

### BR-045 — Sensitive Logging Restrictions

**Classification:** Privacy, Security  
**Related Requirements:** SEC-010, NFR-020

Operational logs must avoid unnecessary collection or exposure of sensitive destination URL contents, query parameters, credentials, and personal information.

### BR-046 — Retention Policy

**Classification:** Privacy, Operational  
**Related Requirements:** DR-010

The project must define retention and cleanup policies for stored URL mappings and operational logs before public release.

Retention rules must distinguish:

- Active mappings.
- Expired mappings.
- Disabled mappings.
- Operational logs.

Exact retention periods remain to be determined.

### BR-047 — No Permanent Availability Guarantee

**Classification:** Operational  
**Related Requirements:** FR-023, NFR-021

A link without a scheduled expiration must not be represented as guaranteed permanently available.

Service availability depends on infrastructure, operational policies, and continued project operation.

## 11. Error Handling Rules

### BR-048 — Invalid Creation Requests

**Classification:** Validation  
**Related Requirements:** FR-011, FR-034, FR-035

Invalid creation requests must be rejected without creating a new mapping.

The response must identify the relevant validation failure without exposing sensitive internal details.

### BR-049 — Unexpected Internal Failures

**Classification:** Operational, Security  
**Related Requirements:** FR-037, SEC-006

Unexpected internal failures must not expose stack traces, credentials, internal database details, or sensitive configuration values to public clients.

### BR-050 — Database Failure During Creation

**Classification:** Integrity, Operational  
**Related Requirements:** FR-005, FR-038

If the system cannot persist a mapping, it must not return a successful creation response.

The system must preserve mapping integrity and return an appropriate failure response.

### BR-051 — Consistent Error Contract

**Classification:** Operational  
**Related Requirements:** FR-034, IR-004

API errors must follow the documented response contract.

Error structures and status codes must be consistent across equivalent failure scenarios.

## 12. Domain Invariants

The following invariants must remain true regardless of implementation details.

| ID | Invariant |
|---|---|
| INV-001 | Every persisted short code identifies at most one mapping. |
| INV-002 | A successfully created mapping has a valid destination URL. |
| INV-003 | A creation request is not successful unless persistence succeeds. |
| INV-004 | An expired mapping is not eligible for redirection. |
| INV-005 | A disabled mapping is not eligible for redirection. |
| INV-006 | A redirect uses the destination associated with the requested mapping. |
| INV-007 | Duplicate destination URLs do not imply duplicate short codes. |
| INV-008 | Public clients cannot modify or delete existing mappings. |
| INV-009 | Expiration does not require immediate physical deletion. |
| INV-010 | Public creation is subject to applicable abuse controls. |

These invariants should be reflected in system design, implementation, and automated tests.

## 13. Business Rule Traceability

| Business Rules | Related Requirements |
|---|---|
| BR-001–BR-009 | FR-006–FR-011, SEC-003, SEC-004, SEC-011 |
| BR-010–BR-018 | FR-001–FR-005, FR-012–FR-015, DR-001, DR-002, DR-006 |
| BR-019–BR-026 | FR-019, FR-022–FR-026, DR-004, DR-008, DR-010 |
| BR-027–BR-032 | FR-015–FR-021, IR-004 |
| BR-033–BR-037 | FR-020, FR-039, SEC-007 |
| BR-038–BR-042 | FR-036, SEC-001, SEC-002, SEC-004, SEC-009 |
| BR-043–BR-047 | DR-001–DR-005, DR-009, DR-010, SEC-010, NFR-020, NFR-021 |
| BR-048–BR-051 | FR-005, FR-011, FR-034–FR-038, SEC-006 |

Detailed requirement-to-rule-to-test traceability will be maintained in `traceability.md`.

## 14. Open Business Decisions

The following decisions require review or further specification.

| ID | Decision | Direction | Target | Status |
|---|---|---|---|---|
| BD-001 | Maximum destination URL length | 2,048 characters | M1 | Approved |
| BD-002 | Maximum expiration duration | 365 days | M1 | Approved |
| BD-003 | Expiration timestamp format | ISO 8601 / RFC 3339-compatible | M1 | Approved |
| BD-004 | Short-code format | 8-character, case-sensitive Base62 | M2 | Approved |
| BD-005 | Redirect status code | HTTP 302 Found | M1 | Approved |
| BD-006 | Unavailable-link response | HTTP 404 Not Found | M1 | Approved |
| BD-007 | Rate-limit thresholds | 10 creation requests/minute/client | M1/M2 | Approved |
| BD-008 | Destination safety policy | HTTP/HTTPS, reject userinfo and prohibited IP literals | M1 | Approved |
| BD-009 | Mapping and log retention | Retention/deletion periods TBD | Before public release | Proposed |
| BD-010 | Request payload size limit | 8,192 bytes; HTTP 413 | M1/M2 | Approved |
| BD-011 | Short-code namespace and reserved paths | `/api`, `/actuator` reserved | M2 | Approved |
| BD-012 | Redirect caching policy | `Cache-Control: no-store` on 302 and 404 | M1/M2 | Approved |

Unresolved decisions must be recorded and must not be silently treated as approved policies.

## 15. Review and Acceptance Criteria

This document is ready for baseline approval when:

- All mandatory MVP business rules are represented.
- Business rules are consistent with the approved scope.
- Rule identifiers are unique and stable.
- Critical validation and lifecycle behaviour is unambiguous.
- Security-sensitive policies are adequately specified or explicitly tracked.
- Rules can be translated into verifiable acceptance criteria.
- Relevant open decisions have been resolved or assigned to an appropriate design stage.
- The Project Owner has completed the review.

## 16. Change Management

Material changes to approved business rules must be evaluated for their effects on:

- Functional requirements.
- API contracts.
- Database constraints.
- Domain invariants.
- Security and privacy controls.
- Use cases.
- Acceptance criteria.
- Automated tests.

Changes must be documented and reviewed through the project's version control workflow.

## 17. Review and Approval

| Field | Value |
|---|---|
| Document Owner | Project Owner |
| Review Status | Approved — M1 Requirements Baseline |
| Approval Authority | Project Owner |
| Approval Date | To be determined |
| Baseline Status | Approved — M1 Requirements Baseline |

This document will be reviewed together with the remaining Requirements Engineering deliverables before the M1 baseline is approved.

---

## Document History

| Version | Date | Description | Status |
|---|---|---|---|
| 0.1.0 | 2026-11-10 | Initial Business Rules Specification draft | Pending Review |
