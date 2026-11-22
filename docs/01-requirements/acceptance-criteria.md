# HelixURL — Acceptance Criteria Specification

**Document ID:** HLX-ACS-001  
**Version:** 0.1.0  
**Status:** Approved — M1 Requirements Baseline
**Project:** HelixURL  
**Target Release:** MVP v0.1.0  
**Document Owner:** Project Owner  
**Last Updated:** 2026-11-12

**Related Documents:**
- [Software Requirements Specification](./requirements.md)
- [Business Rules Specification](./business-rules.md)
- [Use Case Specification](./use-cases.md)
- [Project Scope Statement](../00-initiation/scope.md)
- [Risk Register](../00-initiation/risk-register.md)
- [Requirements Traceability Matrix](./traceability.md)

---

## 1. Purpose

This document defines the acceptance criteria for the HelixURL Minimum Viable Product (MVP).

Acceptance criteria establish observable and verifiable conditions that must be satisfied before the corresponding functionality can be considered complete and acceptable.

The objectives of this document are to:

- Translate functional requirements into testable scenarios.
- Verify compliance with approved business rules.
- Cover successful, alternative, and exceptional workflows.
- Identify boundary conditions and failure cases.
- Establish measurable quality and security acceptance conditions.
- Provide a foundation for automated and manual testing.
- Support traceability from requirements to verification evidence.
- Define acceptance conditions for the MVP release.

This document specifies expected behaviour rather than test implementation details.

## 2. Scope

The acceptance criteria cover:

- Anonymous URL creation.
- Destination validation.
- Short-code uniqueness.
- Persistent URL mappings.
- Optional expiration.
- URL redirection.
- Unavailable-link responses.
- Administrative link disabling.
- Abuse prevention.
- API error handling.
- Web interface behaviour.
- Data integrity and reliability.
- Operational health.
- Security and privacy.
- Build, testing, and deployment readiness.

Post-MVP features are excluded unless explicitly required to verify an MVP constraint.

## 3. Acceptance Criteria Conventions

### 3.1 Identifier Format

Each acceptance criterion has a stable identifier using the format:

`AC-XXX`

Identifiers must not be reused after baseline approval.

### 3.2 Scenario Format

Behavioural criteria use the following structure:

```gherkin
Given <initial condition>
When <action or event>
Then <observable expected result>
```

Additional `And` statements may specify related conditions or outcomes.

### 3.3 Acceptance Status

| Status | Meaning |
|---|---|
| Draft | Criterion has been proposed but not approved |
| Approved | Criterion has been accepted into the requirements baseline |
| Ready for Test | Implementation and verification procedure are available |
| Passed | Required verification has succeeded |
| Failed | Verification has identified non-compliant behaviour |
| Blocked | Verification cannot proceed because of an unresolved dependency |
| Not Applicable | Criterion does not apply to the evaluated configuration |

Approval of an acceptance criterion does not mean that the implementation has passed it.

### 3.4 Verification Methods

| Code | Method | Description |
|---|---|---|
| UT | Unit Test | Verify isolated business logic |
| IT | Integration Test | Verify interactions between application components or dependencies |
| API | API Contract Test | Verify HTTP request and response behaviour |
| E2E | End-to-End Test | Verify complete user-facing workflows |
| SEC | Security Test | Verify security controls and relevant failure cases |
| PERF | Performance Test | Verify measurable performance behaviour |
| OPS | Operational Test | Verify deployment, health, recovery, and operational controls |
| REV | Review / Inspection | Verify documentation, configuration, or implementation evidence |

### 3.5 Policy Dependencies

The M2 policy decisions have been approved. Retention periods and selected implementation/release details remain open.

The following values distinguish approved policies from outstanding proposals:

| Policy | Value / Proposal | Related Decision | Status |
|---|---|---|---|
| Maximum destination URL length | 2,048 characters | BD-001 | Approved |
| Maximum expiration duration | 365 days | BD-002 | Approved |
| Expiration timestamp representation | RFC 3339, explicit UTC offset, seconds precision | BD-003 | Approved |
| Redirect status | HTTP 302 Found | BD-005 | Approved |
| Unknown, expired, and disabled response | HTTP 404 Not Found | BD-006 | Approved |
| Rate-limit thresholds | 10 creation requests/minute/client; HTTP 429 | BD-007 | Approved |
| Destination acceptance policy | HTTP/HTTPS, no userinfo, prohibited IP literals rejected | BD-008 | Approved |
| Retention periods | To be determined | BD-009 | Proposed |
| Request payload limit | 8,192 bytes; HTTP 413 | BD-010 | Approved |
| Redirect cache policy | `Cache-Control: no-store` on 302 and 404 | BD-012 | Approved |

Only scenarios depending on decisions still marked Proposed or Deferred remain provisional. See [Requirements Decision Log](./decision-log.md).

## 4. URL Creation Acceptance Criteria

### AC-001 — Create a Short URL Successfully

**Related Requirements:** FR-001, FR-002, FR-003, FR-005  
**Related Business Rules:** BR-010, BR-011, BR-017  
**Related Use Case:** UC-001  
**Verification:** API, IT, E2E

```gherkin
Scenario: Create a short URL from a valid destination
  Given the URL creation service is available
  And the destination URL is valid and permitted
  And the request is within applicable usage limits
  When an anonymous client submits the creation request
  Then the system must create a new URL mapping
  And the mapping must be persisted
  And the response must indicate successful creation
  And the response must contain the generated short URL
```

### AC-002 — Anonymous Creation Requires No Account

**Related Requirements:** FR-001, FR-033  
**Related Business Rules:** BR-010, BR-018  
**Related Use Case:** UC-001  
**Verification:** API, E2E

```gherkin
Scenario: Create a short URL without authentication
  Given the public URL creation service is enabled
  And the client has no user account
  And the client provides a valid permitted destination
  When the client submits a creation request
  Then the request must not require login or registration
  And the request must be processed subject to applicable controls
```

### AC-003 — Duplicate Destinations Create Independent Mappings

**Related Requirements:** FR-004  
**Related Business Rules:** BR-012, BR-013  
**Related Use Case:** UC-001  
**Verification:** API, IT

```gherkin
Scenario: Create two mappings for the same destination
  Given a destination URL has already been shortened
  When the client submits the same destination URL again
  Then the system must create a new mapping
  And the new mapping must have a different short code
  And both mappings must reference the submitted destination
```

### AC-004 — Successful Creation Requires Persistence

**Related Requirements:** FR-005, DR-007  
**Related Business Rules:** BR-011  
**Related Use Case:** UC-001  
**Verification:** IT

```gherkin
Scenario: Confirm persistence before reporting success
  Given the creation service can access persistent storage
  When a valid creation request is completed successfully
  Then the generated mapping must be committed to persistent storage
  And the mapping must be retrievable using its short code
```

### AC-005 — Creation Without Expiration

**Related Requirements:** FR-023, DR-004  
**Related Business Rules:** BR-019, BR-020  
**Related Use Cases:** UC-001, UC-003  
**Verification:** API, IT

```gherkin
Scenario: Create a mapping without an expiration timestamp
  Given a valid destination URL is supplied
  And no expiration value is provided
  When the creation request succeeds
  Then the persisted mapping must have no scheduled expiration
  And the mapping must remain eligible for redirection
  Unless it is disabled or otherwise unavailable under operational policy
```

## 5. Destination Validation Acceptance Criteria

### AC-006 — Reject Missing Destination

**Related Requirements:** FR-006, FR-011  
**Related Business Rules:** BR-001, BR-048  
**Related Use Case:** UC-004  
**Verification:** API, UT

```gherkin
Scenario Outline: Reject a missing destination
  Given the destination field is <value>
  When the client submits a creation request
  Then the request must be rejected as invalid
  And no new mapping must be created

Examples:
  | value         |
  | missing       |
  | null          |
  | empty string  |
```

### AC-007 — Reject Relative URLs

**Related Requirements:** FR-007  
**Related Business Rules:** BR-002  
**Related Use Case:** UC-004  
**Verification:** UT, API

```gherkin
Scenario Outline: Reject a non-absolute destination
  Given the destination URL is <destination>
  When the client submits a creation request
  Then the system must reject the request
  And no new mapping must be created

Examples:
  | destination           |
  | /products/123         |
  | //example.com/page    |
  | example.com/page      |
```

### AC-008 — Accept Supported URL Schemes

**Related Requirements:** FR-008  
**Related Business Rules:** BR-003  
**Related Use Case:** UC-001  
**Verification:** UT, API

```gherkin
Scenario Outline: Accept a supported URL scheme
  Given the destination URL is <destination>
  And the destination satisfies all other validation rules
  When the client submits a creation request
  Then the scheme must be accepted

Examples:
  | destination                  |
  | http://example.com/page     |
  | https://example.com/page    |
```

### AC-009 — Reject Unsupported Schemes

**Related Requirements:** FR-008  
**Related Business Rules:** BR-003  
**Related Use Case:** UC-004  
**Verification:** UT, API, SEC

```gherkin
Scenario Outline: Reject an unsupported URL scheme
  Given the destination URL is <destination>
  When the client submits a creation request
  Then the system must reject the request
  And no new mapping must be created

Examples:
  | destination                 |
  | javascript:alert(1)        |
  | file:///etc/passwd         |
  | data:text/plain,hello      |
  | ftp://example.com/file     |
```

### AC-010 — Enforce Maximum URL Length

**Related Requirements:** FR-009  
**Related Business Rules:** BR-004  
**Related Use Case:** UC-004  
**Verification:** UT, API

```gherkin
Scenario: Reject a destination exceeding the configured limit
  Given the submitted destination is a syntactically valid HTTP or HTTPS URL
  And its submitted length is 2049 characters
  When the client submits a creation request
  Then the system must reject the request
  And no new mapping must be created
```

**Boundary verification:**

- A valid destination of 2,047 characters.
- A valid destination of exactly 2,048 characters (must not fail solely due to length).
- A valid destination of 2,049 characters (must be rejected).

The counting convention must be defined before test implementation.

### AC-011 — Reject Invalid Hosts

**Related Requirements:** FR-007, FR-010  
**Related Business Rules:** BR-005  
**Related Use Case:** UC-004  
**Verification:** UT, API

```gherkin
Scenario: Reject a destination without a valid host
  Given a destination contains an invalid or missing host
  When the client submits a creation request
  Then the system must reject the request
  And no new mapping must be created
```

### AC-012 — Preserve Destination Components

**Related Requirements:** FR-017, DR-001  
**Related Business Rules:** BR-006, BR-007  
**Related Use Cases:** UC-001, UC-002  
**Verification:** API, IT

```gherkin
Scenario: Preserve path, query, and fragment
  Given the destination is
    "https://example.com/docs?page=2#installation"
  When a short URL is created
  And the resulting short URL is requested
  Then the redirect Location must preserve
    "https://example.com/docs?page=2#installation"
```

### AC-013 — Reject Prohibited Destinations

**Related Requirements:** FR-010, SEC-004  
**Related Business Rules:** BR-008  
**Related Use Case:** UC-004  
**Verification:** UT, API, SEC

```gherkin
Scenario: Reject a destination prohibited by policy
  Given a destination matches a prohibited destination rule
  When the client submits a creation request
  Then the system must reject the request
  And no new mapping must be created
```

**Dependency:** The detailed destination policy must be approved before this criterion can be fully tested.

### AC-014 — No Mandatory Destination Fetching

**Related Requirements:** SEC-011  
**Related Business Rules:** BR-009  
**Related Use Case:** UC-001  
**Verification:** SEC, IT, REV

```gherkin
Scenario: Shorten an eligible URL without contacting its website
  Given a syntactically valid destination is permitted by policy
  And the destination website is unreachable
  When the client submits a creation request
  Then the system must not require the website to respond
  And the creation request may succeed
  And the shortening workflow must not fetch destination content
```

## 6. Short-Code Integrity Acceptance Criteria

### AC-015 — Unique Short Codes

**Related Requirements:** FR-012, FR-013, DR-006  
**Related Business Rules:** BR-014  
**Related Use Case:** UC-001  
**Verification:** IT

```gherkin
Scenario: Persist unique short codes
  Given multiple valid creation requests are submitted
  When the mappings are persisted
  Then each mapping must have a unique short code
  And no short code may identify multiple mappings
```

### AC-016 — Collision Handling

**Related Requirements:** FR-014  
**Related Business Rules:** BR-015  
**Related Use Case:** UC-001  
**Verification:** UT, IT

```gherkin
Scenario: Handle a generated short-code collision
  Given a generated short code already belongs to a mapping
  When another creation attempts to use the same short code
  Then the existing mapping must remain unchanged
  And no duplicate short-code mapping may be committed
  And the creation operation must follow the documented collision policy
```

### AC-017 — Concurrent Creation Integrity

**Related Requirements:** FR-013, FR-014, DR-006  
**Related Business Rules:** BR-014, BR-015  
**Related Use Case:** UC-001  
**Verification:** IT

```gherkin
Scenario: Concurrent requests cannot create duplicate short codes
  Given two creation operations attempt to persist the same short code
  When the operations execute concurrently
  Then at most one mapping may be committed with that short code
  And the other operation must follow the collision handling policy
  And existing mapping data must not be overwritten
```

## 7. Expiration Acceptance Criteria

### AC-018 — Create an Expiring Link

**Related Requirements:** FR-022, DR-004  
**Related Business Rules:** BR-019  
**Related Use Case:** UC-003  
**Verification:** API, IT

```gherkin
Scenario: Create a link with a valid expiration
  Given a valid destination is provided
  And the expiration timestamp satisfies the approved policy
  When the creation request succeeds
  Then the mapping must contain the specified expiration instant
  And the short URL must be returned
```

### AC-019 — Reject Invalid Expiration Format

**Related Requirements:** FR-024  
**Related Business Rules:** BR-023  
**Related Use Case:** UC-003  
**Verification:** UT, API

```gherkin
Scenario: Reject an unparseable expiration value
  Given a valid destination URL
  And an expiration value that violates the approved timestamp format
  When the client submits the creation request
  Then the request must be rejected
  And no new mapping must be created
```

### AC-020 — Reject Past or Present Expiration

**Related Requirements:** FR-024  
**Related Business Rules:** BR-021  
**Related Use Case:** UC-003  
**Verification:** UT, API

```gherkin
Scenario Outline: Reject expiration that is not in the future
  Given the evaluation time is 2026-12-01T10:00:00Z
  And the requested expiration is <expiration>
  When the client submits the creation request
  Then the request must be rejected
  And no new mapping must be created

Examples:
  | expiration               |
  | 2026-12-01T09:59:59Z     |
  | 2026-12-01T10:00:00Z     |
```

### AC-021 — Enforce Maximum Expiration Duration

**Related Requirements:** FR-024  
**Related Business Rules:** BR-022  
**Related Use Case:** UC-003  
**Verification:** UT, API

```gherkin
Scenario: Reject an expiration beyond the maximum duration
  Given the maximum expiration duration is 365 days from the defined creation-time reference
  And the requested expiration exceeds that duration
  When the client submits the creation request
  Then the request must be rejected
  And no new mapping must be created
```

Boundary tests must cover the approved maximum and the smallest representable increment beyond it.

### AC-022 — Redirect Before Expiration

**Related Requirements:** FR-016, FR-025  
**Related Business Rules:** BR-024, BR-025  
**Related Use Case:** UC-002  
**Verification:** UT, IT

```gherkin
Scenario: Redirect immediately before expiration
  Given an active mapping expires at 2026-12-01T10:00:00Z
  And the current time is 2026-12-01T09:59:59Z
  When a visitor requests the short URL
  Then the system must return the approved redirect response
```

### AC-023 — Do Not Redirect at Expiration Boundary

**Related Requirements:** FR-019, FR-026  
**Related Business Rules:** BR-024, BR-025  
**Related Use Cases:** UC-002, UC-005  
**Verification:** UT, IT, API

```gherkin
Scenario: Reject redirection exactly at expiration
  Given a mapping expires at 2026-12-01T10:00:00Z
  And the current time is 2026-12-01T10:00:00Z
  When a visitor requests the short URL
  Then the system must not redirect
  And the response must follow the unavailable-link policy
```

### AC-024 — Expiration Does Not Require Deletion

**Related Requirements:** FR-025, DR-004  
**Related Business Rules:** BR-025, BR-026  
**Related Use Case:** UC-005  
**Verification:** IT

```gherkin
Scenario: An expired mapping remains stored
  Given a mapping has reached its expiration time
  When its short URL is requested
  Then the system must not redirect
  And expiration enforcement must not require deleting the mapping
```

Retention-related deletion may occur separately under the approved retention policy.

## 8. Redirection Acceptance Criteria

### AC-025 — Redirect an Active Mapping

**Related Requirements:** FR-015, FR-016, FR-017  
**Related Business Rules:** BR-027–BR-030  
**Related Use Case:** UC-002  
**Verification:** API, IT, E2E

```gherkin
Scenario: Redirect to the correct destination
  Given an active mapping exists for short code "aB123"
  And its destination is "https://example.com/page"
  When a visitor requests the short URL
  Then the system must return HTTP 302 Found
  And the Location header must identify
    "https://example.com/page"
```

### AC-026 — Unknown Short Code

**Related Requirements:** FR-018  
**Related Business Rules:** BR-027, BR-031  
**Related Use Case:** UC-005  
**Verification:** API, IT

```gherkin
Scenario: Request an unknown short code
  Given no mapping exists for short code "unknown123"
  When a visitor requests the short URL
  Then the system must not redirect
  And the response must be HTTP 404 Not Found
```

### AC-027 — Expired Link Is Unavailable

**Related Requirements:** FR-019  
**Related Business Rules:** BR-025, BR-031  
**Related Use Case:** UC-005  
**Verification:** API, IT

```gherkin
Scenario: Request an expired short URL
  Given a mapping exists
  And its expiration time has passed
  When a visitor requests the short URL
  Then the system must not redirect
  And the response must be HTTP 404 Not Found
```

### AC-028 — Disabled Link Is Unavailable

**Related Requirements:** FR-020  
**Related Business Rules:** BR-028, BR-036  
**Related Use Cases:** UC-005, UC-006  
**Verification:** API, IT

```gherkin
Scenario: Request a disabled short URL
  Given a mapping exists
  And it has been administratively disabled
  When a visitor requests the short URL
  Then the system must not redirect
  And the response must be HTTP 404 Not Found
```

### AC-029 — Unavailable-Link Disclosure Policy

**Related Requirements:** FR-018–FR-020, FR-034  
**Related Business Rules:** BR-031  
**Related Use Case:** UC-005  
**Verification:** API, SEC

```gherkin
Scenario: Avoid exposing internal mapping availability
  Given an unknown, expired, or disabled short code is requested
  When the system generates the public response
  Then the response must follow the approved disclosure policy
  And it must not expose internal mapping details
```

### AC-030 — Redirect Cache Policy

**Related Requirements:** FR-021  
**Related Business Rules:** BR-030  
**Related Use Case:** UC-002  
**Verification:** API, REV

```gherkin
Scenario: Return the approved redirect caching headers
  Given an active mapping exists
  When a visitor requests the short URL
  Then the response must contain HTTP 302 Found
  And the response caching behaviour must match the API contract
```

**Dependency:** Final redirect caching rules must be approved.

## 9. Administrative Link Management Acceptance Criteria

### AC-031 — Disable an Existing Mapping

**Related Requirements:** FR-020, FR-039  
**Related Business Rules:** BR-035, BR-036  
**Related Use Case:** UC-006  
**Verification:** IT, OPS

```gherkin
Scenario: Operator disables an abusive mapping
  Given an active mapping exists
  And the operator is authorised to disable it
  When the operator completes the disabling operation
  Then the mapping must be marked as disabled
  And the change must be persisted
  And subsequent requests must not redirect
```

### AC-032 — Prevent Unauthorised Disabling

**Related Requirements:** FR-039, SEC-007  
**Related Business Rules:** BR-035, BR-037  
**Related Use Case:** UC-006  
**Verification:** SEC, OPS

```gherkin
Scenario: Reject unauthorised mapping modification
  Given a mapping exists
  And an unauthorised client attempts to disable it
  When the operation is attempted
  Then the system must reject the operation
  And the mapping must remain unchanged
```

### AC-033 — No Public Editing or Deletion

**Related Requirements:** FR-039, SEC-007  
**Related Business Rules:** BR-033, BR-034  
**Related Use Case:** UC-006  
**Verification:** API, SEC, REV

```gherkin
Scenario: Public clients cannot edit or delete mappings
  Given a mapping exists
  When an anonymous client attempts an unsupported edit or delete
  Then no public operation may authorise that modification
  And the existing mapping must remain unchanged
```

### AC-034 — Disable an Already Disabled Mapping

**Related Requirements:** FR-039  
**Related Business Rules:** BR-035–BR-037  
**Related Use Case:** UC-006  
**Verification:** IT, OPS

```gherkin
Scenario: Operator attempts to disable an already disabled mapping
  Given a mapping is already disabled
  And the operator is authorised
  When the operator repeats the disabling operation
  Then the mapping must remain disabled
  And no unintended modification may occur
  And the outcome must follow the documented operational contract
```

## 10. Abuse Prevention and Security Acceptance Criteria

### AC-035 — Apply Creation Rate Limits

**Related Requirements:** FR-036, SEC-001, SEC-002  
**Related Business Rules:** BR-038, BR-039  
**Related Use Case:** UC-001  
**Verification:** SEC, API

```gherkin
Scenario: Reject requests exceeding the creation limit
  Given the approved creation rate limit is configured
  And a client has exceeded the applicable limit
  When another creation request is submitted
  Then the request must be rejected
  And the response must use the approved rate-limit status
  And no new mapping must be created
```

### AC-036 — Protect Against Excessive Request Payloads

**Related Requirements:** FR-007, SEC-001  
**Related Business Rules:** BR-042  
**Related Use Case:** UC-004  
**Verification:** API, SEC

```gherkin
Scenario: Reject a request exceeding the payload limit
  Given the approved request payload limit is configured
  And a creation request exceeds that limit
  When the request is received
  Then the request must be rejected
  And the system must not persist a new mapping
```

### AC-037 — Protect Sensitive Error Information

**Related Requirements:** FR-037, SEC-006  
**Related Business Rules:** BR-049  
**Related Use Cases:** UC-001, UC-002  
**Verification:** API, SEC

```gherkin
Scenario: Do not expose internal details on failure
  Given an unexpected internal failure occurs
  When the system returns a public error response
  Then the response must not expose stack traces
  And it must not expose credentials
  And it must not expose internal database connection details
```

### AC-038 — Secrets Are Not Committed to Source Control

**Related Requirements:** SEC-005  
**Related Business Rules:** BR-045  
**Verification:** SEC, REV

```gherkin
Scenario: Review repository secrets handling
  Given the application requires sensitive configuration
  When the repository and CI configuration are reviewed
  Then credentials must not be stored in tracked source files
  And sensitive configuration must use approved secret handling
```

### AC-039 — Verify Abuse Response Readiness

**Related Requirements:** SEC-001, SEC-009  
**Related Business Rules:** BR-040, BR-041  
**Related Use Case:** UC-006  
**Verification:** OPS, SEC, REV

```gherkin
Scenario: Verify public release abuse controls
  Given public anonymous creation is planned for deployment
  When release readiness is evaluated
  Then mandatory abuse controls must be implemented
  And the link-disabling mechanism must be verified
  And the abuse response procedure must be documented
  And unresolved release-blocking risks must be addressed
```

### AC-040 — Enforce HTTPS for Public Deployment

**Related Requirements:** SEC-008, IR-001  
**Related Business Rules:** BR-017  
**Verification:** OPS, SEC

```gherkin
Scenario: Access the publicly deployed service
  Given HelixURL is deployed publicly
  When a client accesses the supported public interface
  Then the interface must be available through HTTPS
  And generated production short URLs must use HTTPS
```

## 11. Data Integrity and Reliability Acceptance Criteria

### AC-041 — Persist Mapping Across Restart

**Related Requirements:** DR-007, NFR-003  
**Related Business Rules:** BR-011, BR-043  
**Related Use Cases:** UC-001, UC-002  
**Verification:** IT, OPS

```gherkin
Scenario: Mapping remains available after restart
  Given a URL mapping has been successfully persisted
  When the application is restarted normally
  And persistent storage remains available
  Then the mapping must remain retrievable
  And the short URL must retain its expected behaviour
```

### AC-042 — Handle Database Failure During Creation

**Related Requirements:** FR-005, FR-038  
**Related Business Rules:** BR-050  
**Related Use Case:** UC-001  
**Verification:** IT

```gherkin
Scenario: Database unavailable during URL creation
  Given persistent storage is unavailable
  When a client submits a valid creation request
  Then the system must not report successful creation
  And the response must follow the documented failure contract
  And existing mappings must not be corrupted
```

A failed response alone does not prove that a transaction was never committed if infrastructure failure occurred after commit but before acknowledgement.

### AC-043 — Preserve Data Integrity Under Invalid Requests

**Related Requirements:** NFR-001, NFR-002  
**Related Business Rules:** BR-048, BR-050  
**Verification:** IT

```gherkin
Scenario: Invalid requests do not corrupt existing mappings
  Given one or more valid mappings already exist
  When invalid creation requests are submitted
  Then the invalid requests must be rejected
  And existing mappings must remain unchanged
  And valid mappings must continue resolving correctly
```

### AC-044 — Verify Database Schema Migration

**Related Requirements:** IR-010  
**Verification:** IT, OPS

```gherkin
Scenario: Initialise the database from migrations
  Given an empty supported PostgreSQL database
  When the documented migration process runs
  Then the required schema must be created successfully
  And the schema must enforce the documented integrity constraints
```

## 12. Web Interface Acceptance Criteria

### AC-045 — Submit URL Through Web Form

**Related Requirements:** FR-027, FR-033  
**Related Business Rules:** BR-010  
**Related Use Case:** UC-001  
**Verification:** E2E

```gherkin
Scenario: Submit a valid URL using the web interface
  Given an anonymous visitor opens the HelixURL web page
  When the visitor enters a valid destination
  And submits the form
  Then the frontend must send the creation request
  And a successful result must be displayed when creation succeeds
```

### AC-046 — Optional Expiration Input

**Related Requirements:** FR-028  
**Related Business Rules:** BR-019–BR-023  
**Related Use Case:** UC-003  
**Verification:** E2E

```gherkin
Scenario: Specify an expiration using the web interface
  Given an anonymous visitor opens the creation form
  When the visitor supplies a valid destination
  And selects or enters an allowed expiration
  And submits the form
  Then the request must include the selected expiration
  And the resulting mapping must use that expiration
```

### AC-047 — Submission Progress Feedback

**Related Requirements:** FR-029  
**Related Use Case:** UC-001  
**Verification:** E2E

```gherkin
Scenario: Show processing feedback during creation
  Given a visitor submits a valid creation form
  And the request has not yet completed
  When the request is being processed
  Then the interface must provide visible processing feedback
```

### AC-048 — Display and Copy Short URL

**Related Requirements:** FR-030, FR-031  
**Related Use Case:** UC-001  
**Verification:** E2E

```gherkin
Scenario: Copy a successfully created short URL
  Given the creation request has succeeded
  And the generated short URL is displayed
  When the visitor activates the copy action
  Then the application must attempt to copy the complete short URL
  And successful copying must provide appropriate feedback
```

### AC-049 — Present Validation and Service Errors

**Related Requirements:** FR-032  
**Related Business Rules:** BR-048, BR-051  
**Related Use Case:** UC-004  
**Verification:** E2E

```gherkin
Scenario: Display a meaningful validation error
  Given the visitor submits an invalid destination
  When the backend rejects the request
  Then the interface must display understandable error feedback
  And it must not display a generated short URL for that request
```

### AC-050 — Responsive and Keyboard-Accessible Core Workflow

**Related Requirements:** IR-007, IR-008, NFR-014, NFR-015  
**Verification:** E2E, REV

```gherkin
Scenario: Use the creation form on a supported viewport
  Given the interface is displayed at a supported viewport size
  When a visitor navigates using a keyboard
  Then the essential form controls must be reachable
  And the controls must have understandable labels
  And the primary workflow must remain usable
```

Representative viewports and browser support targets must be approved before final verification.

## 13. Operational Acceptance Criteria

### AC-051 — Application Health Check

**Related Requirements:** FR-040, NFR-019  
**Related Use Case:** UC-007  
**Verification:** OPS, API

```gherkin
Scenario: Report healthy application status
  Given the application is operating normally
  And required health conditions are satisfied
  When the documented health check is performed
  Then the system must return the documented healthy result
```

### AC-052 — Dependency Failure Health Reporting

**Related Requirements:** FR-040, NFR-019  
**Related Use Case:** UC-007  
**Verification:** OPS, IT

```gherkin
Scenario: Report unhealthy dependency status
  Given the application is running
  And a required dependency is unavailable
  When the appropriate dependency-aware health check is performed
  Then the result must indicate an unhealthy condition
  And sensitive connection details must not be exposed
```

### AC-053 — Operational Logging

**Related Requirements:** FR-041, SEC-010, NFR-020  
**Related Business Rules:** BR-045  
**Verification:** OPS, SEC, REV

```gherkin
Scenario: Record a diagnostic event safely
  Given an operationally relevant failure occurs
  When the application records diagnostic information
  Then the event must be sufficiently identifiable for investigation
  And the log must not unnecessarily expose sensitive URL contents
  And the log must not expose credentials
```

### AC-054 — Document Retention and Recovery Policies

**Related Requirements:** DR-010, NFR-017  
**Related Business Rules:** BR-046  
**Verification:** REV, OPS

```gherkin
Scenario: Review operational data policies
  Given the application is being prepared for public release
  When the operations documentation is reviewed
  Then mapping retention rules must be documented
  And log retention rules must be documented
  And available backup and recovery procedures must be described
  And known recovery limitations must be disclosed
```

## 14. Quality and Delivery Acceptance Criteria

### AC-055 — Automated Core Test Suite

**Related Requirements:** NFR-010, NFR-011  
**Verification:** UT, IT, REV

```gherkin
Scenario: Verify core functionality using automated tests
  Given the required automated test suite is available
  When the test suite executes in the supported test environment
  Then critical creation and redirect behaviours must be tested
  And expiration and validation behaviours must be tested
  And persistence integrity must be tested
  And all release-required tests must pass
```

### AC-056 — Continuous Integration

**Related Requirements:** NFR-012  
**Verification:** OPS, REV

```gherkin
Scenario: Verify the CI pipeline
  Given a change is submitted through the configured Git workflow
  When the required CI workflow runs
  Then the project must be built using the supported toolchain
  And required automated checks must execute
  And their results must be visible to reviewers
```

### AC-057 — Reproducible Local Setup

**Related Requirements:** NFR-016  
**Verification:** OPS, REV

```gherkin
Scenario: Set up the application from project documentation
  Given a supported development environment
  And the documented prerequisites are installed
  When the documented setup steps are followed
  Then the required services must be configurable
  And the application must start successfully
  And the core workflows must be testable locally
```

### AC-058 — Public Deployment Readiness

**Related Requirements:** NFR-017, NFR-018, NFR-021  
**Verification:** OPS, REV

```gherkin
Scenario: Verify MVP deployment readiness
  Given the MVP is prepared for public release
  When the release checklist is evaluated
  Then the documented deployment must be operational
  And HTTPS must be enabled
  And mandatory security controls must be verified
  And hosting limitations must be documented
  And the selected infrastructure must satisfy the approved cost constraint
```

### AC-059 — Performance Verification

**Related Requirements:** NFR-004, NFR-005, NFR-006  
**Verification:** PERF

```gherkin
Scenario: Verify performance under an agreed workload
  Given performance targets have been approved
  And the representative workload is defined
  When the performance tests are executed
  Then creation and redirect measurements must be recorded
  And results must be compared with the approved targets
  And the test environment and methodology must be documented
```

No numerical performance result is considered verified until supporting measurements are available.

### AC-060 — API Contract Documentation

**Related Requirements:** IR-002, IR-003, IR-004  
**Verification:** API, REV

```gherkin
Scenario: Verify the published API contract
  Given the public API implementation is complete
  When its OpenAPI specification is reviewed
  Then supported endpoints must be documented
  And request and response structures must be documented
  And relevant success and error statuses must be documented
  And the documented contract must match implemented behaviour
```

## 15. Supplementary Acceptance Criteria — Traceability Gap Resolution

The following criteria address specification-level gaps identified in the Requirements Traceability Matrix. Their existence does not imply implementation or test completion.

### AC-061 — Verify Modular Architecture

**Related Requirements:** NFR-007  
**Verification:** REV, UT

```gherkin
Scenario: Review the backend's modular structure
  Given the backend implementation is available
  When its architecture and source structure are reviewed
  Then presentation, application, domain, and persistence responsibilities must have documented boundaries
  And business rules must not depend directly on web framework request or response types
  And documented dependency rules must be satisfied
```

**Evidence:** Architecture review and relevant automated checks, where implemented.

### AC-062 — Verify Documentation Maintenance

**Related Requirements:** NFR-009  
**Verification:** REV

```gherkin
Scenario: Review documentation affected by a change
  Given a change modifies documented system behaviour
  When the pull request is reviewed
  Then affected documentation must be updated
  Or the pull request must explain why no update is needed
  And requirement identifiers must remain consistent
```

**Evidence:** Pull request and documentation review.

### AC-063 — Persist Creation Timestamp

**Related Requirements:** DR-003  
**Verification:** IT

```gherkin
Scenario: Store the creation time of a mapping
  Given a valid URL creation request
  When the mapping is successfully persisted
  Then the mapping must contain a creation timestamp
  And the timestamp must represent the creation instant
  And it must be retrievable from persistent storage
```

**Evidence:** Database integration test.

### AC-064 — Verify UTC Timestamp Consistency

**Related Requirements:** DR-008  
**Verification:** UT, IT

```gherkin
Scenario: Interpret equivalent expiration timestamps
  Given two expiration values represent the same instant
  But use different valid UTC offsets
  When the system parses and stores the values
  Then both must represent the same expiration instant
  And expiration decisions must be equivalent
```

**Evidence:** Parsing and persistence tests.

### AC-065 — Verify Data Minimisation

**Related Requirements:** DR-009  
**Verification:** SEC, REV

```gherkin
Scenario: Review personal data collection
  Given the anonymous URL creation workflow is implemented
  When request handling, persistence, and logging are reviewed
  Then the system must not require a user account
  And only approved operationally necessary data may be retained
  And sensitive URL contents must not be unnecessarily logged
```

**Evidence:** Data model and logging reviews.

### AC-066 — Verify Browser-to-API Integration

**Related Requirements:** IR-005  
**Verification:** IT, E2E

```gherkin
Scenario: Submit a request from the deployed frontend
  Given the frontend and backend are deployed in a supported configuration
  When a visitor submits a valid URL
  Then the browser must complete the documented API request
  And the application must display the result
  And cross-origin requests, when applicable, must follow the approved origin policy
```

**Evidence:** Browser integration tests.

### AC-067 — Verify Supported Browser Compatibility

**Related Requirements:** IR-006  
**Verification:** E2E

```gherkin
Scenario: Execute the core workflow in a supported browser
  Given a browser belongs to the approved support matrix
  When a visitor creates a short URL and copies the result
  Then the core workflow must function correctly
  And essential content and controls must remain usable
```

**Dependency:** Browser support matrix to be approved during M2/M4.

### AC-068 — Verify Core Workflow Usability

**Related Requirements:** NFR-013  
**Verification:** E2E, REV

```gherkin
Scenario: Complete the core workflow without instructions
  Given a representative user opens the public interface
  When the user attempts to shorten a valid destination
  Then the primary action must be identifiable
  And the generated result must be understandable and copyable
  And failures must provide actionable feedback
```

**Dependency:** Representative tasks and evaluation criteria to be defined before release verification.

### AC-069 — Verify Environment Configuration Separation

**Related Requirements:** NFR-008  
**Verification:** OPS, REV

```gherkin
Scenario: Configure the application for different environments
  Given the application supports local and deployed environments
  When environment-specific settings are reviewed
  Then configuration must be externalised where appropriate
  And environment changes must not require modifying business logic
  And sensitive settings must not be committed to source control
```

**Evidence:** Configuration and deployment review.

---

## 16. Acceptance Coverage Summary

| Area | Acceptance Criteria | Count |
|---|---|---:|
| URL Creation | AC-001–AC-005 | 5 |
| Destination Validation | AC-006–AC-014 | 9 |
| Short-Code Integrity | AC-015–AC-017 | 3 |
| Expiration | AC-018–AC-024 | 7 |
| Redirection | AC-025–AC-030 | 6 |
| Administrative Management | AC-031–AC-034 | 4 |
| Security and Abuse Prevention | AC-035–AC-040 | 6 |
| Data Integrity and Reliability | AC-041–AC-044 | 4 |
| Web Interface | AC-045–AC-050 | 6 |
| Operations | AC-051–AC-054 | 4 |
| Quality and Delivery | AC-055–AC-060 | 6 |
| Supplementary Traceability Criteria | AC-061–AC-069 | 9 |
| **Total** | **AC-001–AC-069** | **69** |

The acceptance criteria provide an initial verification baseline. Additional scenarios may be added when detailed API contracts, security policies, and architecture decisions are finalised.

## 17. Acceptance Execution and Evidence

During implementation and testing, each criterion should be associated with:

- A test case or review procedure.
- The applicable test environment.
- A verification result.
- Evidence such as automated test output, CI results, or review records.
- A relevant GitHub Issue or Pull Request where appropriate.
- The date of verification.
- Any unresolved defects or deviations.

A suggested verification record is:

| Field | Example |
|---|---|
| Acceptance Criterion | AC-023 |
| Verification Method | UT, IT |
| Test Reference | To be assigned |
| Environment | Local integration |
| Result | Not Run |
| Evidence | Pending |
| Defect Reference | None |
| Verified On | Pending |

Acceptance criteria should not be marked Passed solely because the corresponding code has been written.

## 18. MVP Acceptance and Release Gate

The MVP is eligible for release consideration when:

1. Mandatory functional acceptance criteria have passed.
2. Critical security and abuse prevention controls have been verified.
3. Database integrity requirements have passed integration testing.
4. Core web and API workflows have been verified.
5. Required CI checks pass.
6. Operational health and deployment procedures have been verified.
7. The applicable performance targets have been evaluated.
8. No unresolved release-blocking defects remain.
9. Relevant residual risks have been reviewed.
10. The Project Owner has approved the release decision.

A criterion may be marked Not Applicable only with a documented justification.

Public release must not proceed solely because the application builds or starts successfully.

## 19. Open Acceptance Decisions

| ID | Open Item | Related Criteria | Target |
|---|---|---|---|
| AD-001 | Length approved: 2,048; counting convention pending API design | AC-010 | Partially resolved |
| AD-002 | Destination acceptance policy | AC-013 | M1 |
| AD-003 | Maximum approved: 365 days; precision proposed | AC-019–AC-021 | Partially resolved |
| AD-004 | HTTP 302 approved; cache headers proposed | AC-025, AC-030 | Partially resolved |
| AD-005 | HTTP 404 approved (BD-006) | AC-023, AC-026–AC-029 | Approved |
| AD-006 | Rate-limiting thresholds and scope | AC-035 | M1/M2 |
| AD-007 | Maximum request payload size | AC-036 | M1/M2 |
| AD-008 | Operational disabling mechanism | AC-031–AC-034 | M2 |
| AD-009 | Supported browsers and viewports | AC-050 | M1/M4 |
| AD-010 | Mapping and log retention policy | AC-054 | M1/M2 |
| AD-011 | Performance targets and workloads | AC-059 | M1/M5 |
| AD-012 | Health-check contract | AC-051, AC-052 | M2 |

Open decisions must be resolved before the affected criteria can be fully verified.

## 20. Review and Approval

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
| 0.1.0 | 2026-11-12 | Initial Acceptance Criteria Specification draft | Pending Review |
