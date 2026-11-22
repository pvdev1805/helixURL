# HelixURL — Requirements Decision Log

**Document ID:** HLX-RDL-001  
**Version:** 0.1.0  
**Status:** Approved — M2 Design Baseline  
**Project:** HelixURL  
**Target Release:** MVP v0.1.0  
**Last Updated:** 2026-11-22

## 1. Purpose

This document records significant product policy decisions made during M1 — Requirements Engineering.

Its purpose is to preserve decision rationale, distinguish approved policies from proposals, and identify design decisions deferred to later milestones.

A decision recorded as Proposed must not be treated as an approved requirement.

## 2. Approved Decisions

### BD-001 — Maximum Destination URL Length

**Status:** Approved

The maximum accepted destination URL length is 2,048 characters.

The limit applies to the submitted URL string. The exact character-counting convention must be specified consistently in the API contract and validation implementation.

**Rationale:** Establishes a bounded input size while accommodating common URL structures.

**Affected:** FR-009, BR-004, AC-010.

### BD-002 — Maximum Expiration Duration

**Status:** Approved

A user-specified expiration timestamp must be no later than 365 days after the defined creation-time reference.

An expiration timestamp must be strictly later than the request evaluation time.

The creation-time reference and comparison precision must be consistent and testable.

**Rationale:** Limits the maximum lifetime of explicitly expiring links without imposing an expiration on links created without one.

**Affected:** FR-024, BR-021, BR-022, AC-020, AC-021.

### BD-005 — Redirect Status

**Status:** Approved

An eligible short URL returns HTTP 302 Found with a `Location` header containing the stored destination URL.

**Rationale:** Supports temporary redirect semantics suitable for links that may later expire or be disabled.

**Affected:** FR-021, BR-030, UC-002, AC-025, AC-030.

### BD-006 — Unavailable-Link Response

**Status:** Approved

Unknown, expired, and administratively disabled short URLs return HTTP 404 Not Found.

Public responses must not unnecessarily disclose which of these conditions occurred.

**Rationale:** Establishes consistent public behaviour while limiting disclosure of internal mapping state.

**Affected:** FR-018–FR-020, BR-031, UC-005, AC-023, AC-026–AC-029.

### BD-003 — Expiration Timestamp Format

**Status:** Approved

- Accept RFC 3339-compatible timestamps with an explicit UTC offset.
- Support precision up to whole seconds for MVP input.
- Convert accepted values to a common instant for comparisons.
- Reject timestamps without an explicit timezone offset.
- Do not silently reinterpret a timestamp in the server's local timezone.

**Rationale:** Avoids ambiguous local times and simplifies reproducible boundary tests.

**Affected:** BR-023, BR-024, AC-019, AC-020, AC-064.

### BD-004 — Short-Code Format and Generation

**Status:** Approved

- Generate eight-character, case-sensitive Base62 short codes.
- Use the alphabet `0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz`.
- Use `java.security.SecureRandom`.
- Enforce uniqueness through the PostgreSQL `short_code` unique constraint.
- Retry only confirmed short-code uniqueness collisions.
- Allow a maximum of five persistence attempts.
- Roll back failed transactions before retrying.
- Do not expose internal database identifiers through short codes.

**Rationale:** Provides a large code space, reduces predictability, and maintains reliable uniqueness under concurrent creation requests.

**Affected:** BR-016 and related short-code requirements, acceptance criteria, database design, and API contract.

### BD-007 — Anonymous Creation Rate Limiting

**Status:** Approved

- Apply a baseline limit of 10 creation requests per minute per identified client.
- Reject requests exceeding the limit with HTTP 429 Too Many Requests.
- Apply the limit before creating a mapping.
- Document the client identification policy.
- Allow operational adjustment of the threshold without changing business logic.

The rate limit is a baseline abuse control, not a complete anti-abuse solution.

**Rationale:** Reduces accidental overload and some automated abuse while keeping anonymous creation practical.

**Affected:** BR-038, BR-039, AC-035.

### BD-008 — Destination Acceptance Policy

**Status:** Approved

The public creation API must:

- Accept syntactically valid absolute HTTP and HTTPS URLs.
- Reject URLs containing userinfo credentials.
- Reject loopback and private network IP literals.
- Reject known special-use IP literals that are not appropriate public destinations.
- Reject malformed host representations.
- Reject destinations prohibited by documented service policy.
- Preserve valid paths, query parameters, and fragments.
- Avoid fetching destination content during the shortening workflow.

Internationalised domain names and public DNS hostnames require consistent parsing and validation rules.

DNS resolution must not be assumed to prove that a destination is safe or trustworthy.

**Rationale:** Establishes a bounded destination policy without introducing an external URL scanning dependency into the MVP.

**Affected:** BR-002–BR-009, AC-007–AC-014.

### BD-010 — Request Payload Limit

**Status:** Approved

The URL creation endpoint must enforce a maximum request body size of 8,192 bytes, measured from the HTTP request body payload. Requests exceeding this limit must receive HTTP 413 Content Too Large.

Requests exceeding the approved limit must be rejected without creating a mapping.

The final API contract must specify how the limit is measured and the HTTP response semantics.

**Rationale:** Bounds request processing and reduces unnecessary resource consumption.

**Affected:** BR-042, AC-036.

### BD-011 — Routing Namespace

**Status:** Approved

- Public URL creation uses `/api/v1/urls`.
- Public redirects use `/{shortCode}`.
- Reserve `/api` and `/actuator` for application and operational routing.
- Reserve additional frontend and static asset paths when their routing design is finalised.
- Generated short codes must not conflict with reserved routing namespaces.

**Rationale:** Prevents application and operational route conflicts with the root-level redirect route.

**Affected:** FR-015, FR-021, API routing and short-code validation.

### BD-012 — Redirect Caching Policy

**Status:** Approved

Successful redirect responses (HTTP 302) and unavailable-link responses (HTTP 404) must include:

`Cache-Control: no-store`

The API contract must document the caching behaviour for redirect and unavailable-link responses.

**Rationale:** Reduces the risk of clients reusing a cached redirect after expiration or administrative disabling.

The application cannot guarantee that every external client or intermediary will honour caching directives.

**Affected:** BR-030, AC-030.

## 3. Proposed Decision — Retention Policy

### BD-009 — Data Retention

**Status:** Proposed

- Active mappings without scheduled expiration are not automatically deleted solely because of age.
- Expired mappings may remain stored until a documented cleanup process applies.
- Disabled mappings may be retained for operational investigation.
- Mapping retention, operational log retention, and deletion procedures must be documented before public release.
- No indefinite data retention guarantee is made to users.

**Rationale:** Separates link availability from physical record retention and supports operational investigation.

**Affected:** BR-026, BR-043–BR-047, AC-024, AC-054.

## 4. Additional Open Decisions

| ID | Topic | Required Outcome | Target |
|---|---|---|---|
| OD-001 | Performance targets | Measurable workload and response-time objectives | M2/M5 |
| OD-002 | Browser support | Supported browser/version policy | M2/M4 |
| OD-003 | Operator disabling | Restricted internal maintenance command; access/audit controls pending | M3/M6 |
| OD-004 | Hosting | Deployment topology and cost constraints | M2/M6 |
| OD-005 | Health checks | Actuator selected; detailed liveness/readiness and exposure pending | M3/M6 |
| OD-006 | Retention periods | Operational retention and cleanup policy | Before public release |
| OD-007 | Abuse handling | Reporting, investigation and disabling procedure | M2/M5 |
| OD-008 | URL parsing | URI policy approved; IDN, IPv6 and character-counting test details pending | M3 |
| OD-009 | Creation failure semantics | Bounded collision retries approved; ambiguous commit outcomes pending | M3 |

Deferred decisions must be resolved before the implementation or release activities that depend on them.

## 5. Decision Change Control

A material change to an approved decision requires:

1. Identification of the affected decision.
2. Review of related requirements and business rules.
3. Review of affected use cases and acceptance criteria.
4. Assessment of security, data and operational impacts.
5. Documentation of the new decision and rationale.
6. Updates to affected design and test artefacts.
7. Review through the project's version control process.

## 6. Approval

| Field | Value |
|---|---|
| Approved Decisions | BD-001, BD-002, BD-003, BD-004, BD-005, BD-006, BD-007, BD-008, BD-010, BD-011, BD-012 |
| Proposed Decisions | BD-009 |
| Deferred Decisions | None for the M2 design baseline |
| Overall Status | Approved — M2 Design Baseline |
| Approval Authority | Project Owner |

---

## Document History

| Version | Date | Description |
|---|---|---|
| 0.1.0 | 2026-11-13 | Initial requirements decision log |
| 0.1.1 | 2026-11-22 | Reconciled approved M2 policies and remaining release dependencies |
