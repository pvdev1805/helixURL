# HelixURL — Use Case Specification

**Document ID:** HLX-UCS-001  
**Version:** 0.1.0  
**Status:** Draft — Pending Review  
**Project:** HelixURL  
**Target Release:** MVP v0.1.0  
**Document Owner:** Project Owner  
**Last Updated:** 2026-11-11

**Related Documents:**
- [Software Requirements Specification](./requirements.md)
- [Business Rules Specification](./business-rules.md)
- [Project Scope Statement](../00-initiation/scope.md)
- [Risk Register](../00-initiation/risk-register.md)
- [Acceptance Criteria](./acceptance-criteria.md) *(planned)*

---

## 1. Introduction

### 1.1 Purpose

This document describes the primary interactions between external actors and the HelixURL Minimum Viable Product (MVP).

Each use case identifies an actor's objective, the conditions required to perform an operation, the expected successful interaction, and the alternative or exceptional outcomes.

The document provides a behavioural foundation for:

- Functional analysis.
- API and user interface design.
- Domain modelling.
- Test case development.
- Acceptance criteria definition.
- Requirements traceability.

Use cases describe observable system behaviour without prescribing internal implementation details.

### 1.2 Scope

The use cases cover the following MVP capabilities:

- Anonymous short URL creation.
- Optional expiration.
- Destination URL validation.
- URL redirection.
- Handling unavailable links.
- Controlled administrative link disabling.
- Basic application health checking.

The use cases exclude authentication, public link editing and deletion, analytics dashboards, QR code generation, custom aliases, billing, and other post-MVP functionality.

### 1.3 Use Case Terminology

| Term | Definition |
|---|---|
| Actor | An external role interacting with the system |
| Primary Actor | Actor whose goal initiates a use case |
| Supporting Actor | External role or system supporting the interaction |
| Trigger | Event that starts a use case |
| Precondition | Condition required before a use case begins |
| Main Success Scenario | Normal sequence leading to the actor's goal |
| Alternative Flow | Variation of the main scenario |
| Exception Flow | Failure preventing normal completion |
| Postcondition | System state after the use case |
| Extension Point | Step where another scenario may apply |

### 1.4 Use Case Conventions

Use case identifiers follow the format `UC-XXX`.

Each use case contains:

1. Identification and objective.
2. Actors.
3. Trigger.
4. Preconditions.
5. Main success scenario.
6. Alternative and exception flows.
7. Success and failure postconditions.
8. Related requirements and business rules.

Use case steps describe externally observable interactions and outcomes. Internal implementation details belong in the System Design documentation.

## 2. System Boundary and Actors

### 2.1 System Boundary

The HelixURL system includes:

- The public web interface.
- The backend application.
- URL mapping persistence.
- The redirection service.
- Basic operational functionality.

The following remain outside the system boundary:

- External destination websites.
- End-user browser implementations.
- Third-party hosting infrastructure.
- External network availability.

### 2.2 Actor Catalogue

#### ACT-001 — Anonymous Creator

A person who submits a destination URL and receives a shortened URL without creating an account.

**Primary goals:**

- Create a shortened URL.
- Optionally specify an expiration time.
- Receive understandable validation and error feedback.
- Copy and share the generated link.

#### ACT-002 — Link Visitor

A person or client application that opens an existing short URL.

**Primary goals:**

- Reach the intended destination.
- Receive an appropriate response when the link is unavailable.

#### ACT-003 — API Consumer

An external application or HTTP client that submits URL creation requests programmatically.

**Primary goals:**

- Create shortened URLs using the documented API.
- Receive structured success or failure responses.

An API Consumer follows the same business rules as an Anonymous Creator.

#### ACT-004 — Project Operator

The authorised person responsible for operating and maintaining HelixURL.

**Primary goals:**

- Check service health.
- Investigate operational failures.
- Disable abusive or unsafe mappings.
- Maintain service availability within documented constraints.

The Project Operator does not require a public administrative web dashboard in the MVP.

### 2.3 Supporting Systems

**Persistent Data Store**

PostgreSQL provides durable storage for URL mappings. It is treated as an internal infrastructure component rather than an independent business actor.

**Hosting and Monitoring Infrastructure**

External hosting or monitoring services may support deployment and operational checks.

**Destination Website**

The external website identified by the stored destination URL is not controlled by HelixURL.

## 3. Use Case Catalogue

| ID | Name | Primary Actor | Priority | Related Requirements |
|---|---|---|---|---|
| UC-001 | Create Short URL | Anonymous Creator / API Consumer | Must | FR-001–FR-005, FR-012–FR-014 |
| UC-002 | Redirect Using Short URL | Link Visitor | Must | FR-015–FR-021 |
| UC-003 | Create Expiring Short URL | Anonymous Creator / API Consumer | Must | FR-022–FR-026 |
| UC-004 | Handle Invalid URL Submission | Anonymous Creator / API Consumer | Must | FR-006–FR-011, FR-034–FR-035 |
| UC-005 | Handle Unknown or Unavailable Link | Link Visitor | Must | FR-018–FR-020 |
| UC-006 | Disable Abusive Link | Project Operator | Must | FR-020, FR-039, SEC-007, SEC-009 |
| UC-007 | Check Application Health | Project Operator | Must | FR-040, NFR-019 |

## 4. UC-001 — Create Short URL

### 4.1 Overview

| Attribute | Description |
|---|---|
| Use Case ID | UC-001 |
| Name | Create Short URL |
| Primary Actor | Anonymous Creator / API Consumer |
| Goal | Generate a short URL for an eligible destination |
| Priority | Must Have |
| Trigger | Actor submits a URL creation request |

### 4.2 Preconditions

- The URL creation interface is accessible.
- The creation service is enabled.
- The request is subject to the applicable abuse prevention controls.
- Required persistence infrastructure is available for successful completion.

### 4.3 Main Success Scenario

1. The actor opens the web interface or prepares an API request.
2. The actor supplies a destination URL.
3. The actor submits the creation request.
4. The system receives the request.
5. The system validates the request against the applicable destination and creation rules.
6. The system confirms that the request is permitted under the current abuse prevention policy.
7. The system creates a new URL mapping with a unique short code.
8. The system persists the mapping.
9. The system constructs the public short URL.
10. The system returns a successful creation response.
11. The actor receives the generated short URL.
12. If using the web interface, the actor may copy the short URL.

### 4.4 Alternative Flows

**A1 — Optional Expiration Supplied**

At Step 2, the actor supplies an expiration value.

The flow continues through UC-003 — Create Expiring Short URL.

**A2 — Duplicate Destination URL**

At Step 5, the destination matches a previously stored destination.

The system continues normal creation and creates an independent mapping.

**A3 — Short-Code Collision**

At Step 7, the generated short code conflicts with an existing mapping.

The system applies the documented collision handling strategy.

If a new unique short code is obtained, the flow resumes at Step 8.

Otherwise, the operation fails without modifying an existing mapping.

### 4.5 Exception Flows

**E1 — Invalid Destination**

At Step 5, validation fails.

The system rejects the request and returns an appropriate validation error.

UC-004 applies.

**E2 — Rate Limit Exceeded**

At Step 6, the request exceeds the applicable creation limit.

The system rejects the request with the documented rate-limit response.

No new mapping is created.

**E3 — Persistence Failure**

At Step 8, the mapping cannot be persisted.

The system returns an appropriate failure response.

The system does not report successful creation.

**E4 — Unexpected Internal Error**

At any processing step, an unexpected internal error prevents successful completion.

The system returns a safe failure response without exposing sensitive implementation details.

### 4.6 Postconditions

**Success:**

- A new mapping has been persisted.
- Its short code is unique.
- The mapping references the accepted destination.
- The actor has received a short URL.

**Failure:**

- The actor has not received a successful creation response.
- No new mapping is committed as a result of the failed operation, except that an ambiguous infrastructure failure may require reconciliation of the actual commit outcome.
- Existing mappings remain unchanged.

### 4.7 Related Requirements

FR-001–FR-005, FR-012–FR-014, FR-027–FR-032, FR-034–FR-038, DR-001–DR-007.

### 4.8 Related Business Rules

BR-001–BR-018, BR-038–BR-042, BR-048–BR-051.

## 5. UC-002 — Redirect Using Short URL

### 5.1 Overview

| Attribute | Description |
|---|---|
| Use Case ID | UC-002 |
| Name | Redirect Using Short URL |
| Primary Actor | Link Visitor |
| Goal | Navigate to the destination associated with a short code |
| Priority | Must Have |
| Trigger | Actor requests a short URL |

### 5.2 Preconditions

- The short URL is syntactically compatible with the public routing scheme.
- The redirection service is reachable.
- The request can be processed by the application.

### 5.3 Main Success Scenario

1. The actor opens a HelixURL short URL.
2. The system receives the request containing a short code.
3. The system resolves the short code to a stored mapping.
4. The system determines that the mapping is not administratively disabled.
5. The system determines that the mapping has not expired.
6. The system retrieves the destination associated with the mapping.
7. The system returns HTTP 302 Found with a `Location` header containing the stored destination URL.
8. The actor's HTTP client may follow the redirect to the destination website.

### 5.4 Alternative and Exception Flows

**A1 — Unknown Short Code**

At Step 3, no mapping exists.

UC-005 applies.

**A2 — Disabled Mapping**

At Step 4, the mapping is disabled.

UC-005 applies.

**A3 — Expired Mapping**

At Step 5, the mapping has expired.

UC-005 applies.

**E1 — Database Unavailable**

At Step 3, the system cannot access required mapping data.

The system returns an appropriate service failure response.

**E2 — Unexpected Processing Failure**

The system returns a safe error response without exposing internal implementation details.

### 5.5 Postconditions

**Success:**

- The system has returned a redirect response for the correct stored destination.
- The stored mapping remains valid and unchanged by the redirection operation.

**Failure:**

- The system does not issue a destination redirect.
- The client receives an appropriate error response where the application can respond.

### 5.6 Related Requirements

FR-015–FR-021, DR-001–DR-008, IR-004, NFR-001.

### 5.7 Related Business Rules

BR-024–BR-032, BR-036.

## 6. UC-003 — Create Expiring Short URL

### 6.1 Overview

| Attribute | Description |
|---|---|
| Use Case ID | UC-003 |
| Name | Create Expiring Short URL |
| Primary Actor | Anonymous Creator / API Consumer |
| Goal | Create a shortened URL with a defined expiration time |
| Priority | Must Have |
| Trigger | Actor submits a creation request with an expiration value |

### 6.2 Preconditions

- The conditions for UC-001 are satisfied.
- The actor provides an expiration value.

### 6.3 Main Success Scenario

1. The actor supplies a destination URL.
2. The actor supplies an expiration timestamp.
3. The actor submits the creation request.
4. The system validates the destination URL.
5. The system parses and validates the expiration timestamp.
6. The system confirms that the expiration timestamp is later than the defined evaluation time.
7. The system confirms that the expiration timestamp does not exceed 365 days from the defined creation-time reference (BD-002).
8. The system completes the remaining creation steps described in UC-001.
9. The system persists the expiration timestamp with the new mapping.
10. The system returns the generated short URL.

### 6.4 Alternative and Exception Flows

**A1 — Expiration Not Supplied**

The system follows UC-001 and creates a mapping without a scheduled expiration timestamp.

**E1 — Invalid Timestamp Format**

At Step 5, the expiration value cannot be parsed according to the approved format.

The system rejects the request.

**E2 — Expiration in the Past or Present**

At Step 6, the expiration timestamp is earlier than or equal to the evaluation time.

The system rejects the request.

**E3 — Expiration Exceeds Maximum**

At Step 7, the expiration exceeds the approved maximum duration.

The system rejects the request.

**E4 — Creation or Persistence Failure**

The applicable failure flow from UC-001 applies.

### 6.5 Postconditions

**Success:**

- A new mapping has been persisted.
- The mapping contains the approved expiration timestamp.
- The link is eligible for redirection only until the expiration boundary, unless disabled earlier.

**Failure:**

- No successful creation response is returned.
- Invalid expiration values do not result in a committed new mapping.

### 6.6 Related Requirements

FR-022–FR-026, DR-004, DR-008.

### 6.7 Related Business Rules

BR-019–BR-026.

## 7. UC-004 — Handle Invalid URL Submission

### 7.1 Overview

| Attribute | Description |
|---|---|
| Use Case ID | UC-004 |
| Name | Handle Invalid URL Submission |
| Primary Actor | Anonymous Creator / API Consumer |
| Goal | Receive clear feedback when a destination URL is invalid |
| Priority | Must Have |
| Trigger | A URL creation request fails destination validation |

### 7.2 Preconditions

- A creation request has been submitted.
- The system is able to evaluate the relevant validation rules.

### 7.3 Main Scenario

1. The actor submits a URL creation request.
2. The system evaluates the destination URL.
3. The system identifies one or more validation failures.
4. The system rejects the request.
5. The system returns a documented client error response.
6. The actor receives information sufficient to understand the validation failure.

### 7.4 Validation Failure Scenarios

**V1 — Missing Destination**

The destination is missing, null, or empty.

**V2 — Malformed URL**

The destination is not a valid absolute URL.

**V3 — Unsupported Scheme**

The destination uses a scheme other than HTTP or HTTPS.

**V4 — Excessive URL Length**

The destination exceeds the approved maximum length.

**V5 — Invalid Host**

The destination contains an invalid or missing host.

**V6 — Prohibited Destination**

The destination violates the approved destination acceptance policy.

### 7.5 Postconditions

- No new mapping is created from the rejected request.
- Existing mappings remain unchanged.
- The client receives an appropriate validation response.

### 7.6 Related Requirements

FR-006–FR-011, FR-034, FR-035.

### 7.7 Related Business Rules

BR-001–BR-009, BR-048, BR-051.

## 8. UC-005 — Handle Unknown or Unavailable Link

### 8.1 Overview

| Attribute | Description |
|---|---|
| Use Case ID | UC-005 |
| Name | Handle Unknown or Unavailable Link |
| Primary Actor | Link Visitor |
| Goal | Receive an appropriate response when a short link cannot be used |
| Priority | Must Have |
| Trigger | A requested short code is unknown, expired, or disabled |

### 8.2 Preconditions

- The actor requests a short URL.
- The system can evaluate the mapping's availability.

### 8.3 Main Scenario

1. The actor requests a HelixURL short URL.
2. The system attempts to resolve the short code.
3. The system determines that the link is unavailable.
4. The system does not issue a destination redirect.
5. The system returns the documented unavailable-link response.

### 8.4 Unavailable-Link Scenarios

**A1 — Unknown Short Code**

No mapping exists for the supplied short code.

**A2 — Expired Mapping**

The mapping exists, but its expiration timestamp has been reached.

**A3 — Disabled Mapping**

The mapping exists, but administrative disabling prevents redirection.

### 8.5 Response Policy

The approved public response is HTTP 404 Not Found for all three scenarios (BD-006).

The response must not unnecessarily reveal whether the mapping previously existed.

Internal diagnostics may distinguish the scenarios when permitted by logging and privacy policies.

### 8.6 Postconditions

- No destination redirect is issued.
- No mapping is modified by the request.
- The client receives the documented unavailable-link response.

### 8.7 Related Requirements

FR-018–FR-020, FR-034.

### 8.8 Related Business Rules

BR-025, BR-027, BR-028, BR-031, BR-036.

## 9. UC-006 — Disable Abusive Link

### 9.1 Overview

| Attribute | Description |
|---|---|
| Use Case ID | UC-006 |
| Name | Disable Abusive Link |
| Primary Actor | Project Operator |
| Goal | Prevent a specified mapping from redirecting |
| Priority | Must Have |
| Trigger | A mapping is identified as abusive or operationally unsafe |

### 9.2 Preconditions

- The Project Operator has access to an authorised operational mechanism.
- A target mapping has been identified.
- The disabling mechanism is available.

### 9.3 Main Success Scenario

1. The Project Operator receives or identifies a report of potentially abusive link activity.
2. The Project Operator investigates the report using available evidence.
3. The Project Operator identifies the mapping requiring action.
4. The Project Operator invokes the authorised disabling mechanism.
5. The system validates the operation and identifies the target mapping.
6. The system updates the mapping's availability state to disabled.
7. The system persists the change.
8. The system confirms that the operation has completed.
9. Subsequent requests to the disabled short URL do not redirect.

### 9.4 Alternative and Exception Flows

**A1 — Mapping Already Disabled**

At Step 5, the mapping is already disabled.

The system reports the existing state or treats the operation as idempotently completed according to the operational contract.

**E1 — Mapping Not Found**

At Step 5, the target mapping cannot be found.

The system reports the failure through the authorised operational interface.

**E2 — Unauthorised Operation**

An unauthorised caller attempts to disable a mapping.

The operation is rejected without changing the mapping.

**E3 — Persistence Failure**

At Step 7, the system cannot persist the disabling change.

The operation is not reported as successfully completed.

The operator receives an appropriate failure indication.

### 9.5 Postconditions

**Success:**

- The target mapping is disabled.
- Subsequent resolution requests do not redirect.
- The operational action can be investigated using appropriate records.

**Failure:**

- The operation is not reported as successfully completed.
- The operator can investigate the outcome.
- No unauthorised mapping changes are permitted.

### 9.6 Security Considerations

- The disabling mechanism must not be publicly accessible without appropriate authorisation.
- The MVP does not require a public administrative dashboard.
- The implementation mechanism will be selected during system design.
- Operator actions should be documented and appropriately auditable.

### 9.7 Related Requirements

FR-020, FR-039, SEC-007, SEC-009.

### 9.8 Related Business Rules

BR-033–BR-037, BR-041.

## 10. UC-007 — Check Application Health

### 10.1 Overview

| Attribute | Description |
|---|---|
| Use Case ID | UC-007 |
| Name | Check Application Health |
| Primary Actor | Project Operator |
| Goal | Determine whether the application is operational |
| Priority | Must Have |
| Trigger | Operator or authorised monitoring system initiates a health check |

### 10.2 Preconditions

- The operator has access to the documented health-check mechanism.
- The relevant application endpoint or monitoring interface is reachable.

### 10.3 Main Success Scenario

1. The operator initiates a health check.
2. The system receives the health-check request.
3. The system evaluates the configured health conditions.
4. The system returns the documented health status.
5. The operator interprets the result and determines whether further investigation is necessary.

### 10.4 Alternative and Exception Flows

**A1 — Application Running but Dependency Unavailable**

The application process is running, but a required dependency is unavailable.

The system reports the appropriate degraded or unhealthy status according to the health-check contract.

**E1 — Application Unreachable**

The health-check interface cannot be reached.

The operator treats the absence of a response as an operational failure requiring investigation.

**E2 — Health Check Processing Failure**

The system cannot complete the configured health evaluation.

An appropriate failure outcome is returned when possible.

### 10.5 Postconditions

**Success:**

- The operator receives an interpretable health result.
- No business mapping is modified.

**Failure:**

- The operator has an indication that health status could not be confirmed.
- Investigation may be required.

### 10.6 Related Requirements

FR-040, NFR-019, NFR-020.

### 10.7 Related Business Rules

BR-049, BR-051.

## 11. Use Case Relationships

The following relationships are used to organise related behaviour:

| Source | Relationship | Target | Explanation |
|---|---|---|---|
| UC-003 | Specialisation | UC-001 | Creates a short URL with additional expiration rules |
| UC-004 | Exception scenario | UC-001 | Handles invalid destination submissions |
| UC-005 | Exception scenario | UC-002 | Handles unknown, expired, or disabled links |
| UC-006 | Operational effect | UC-002 | Disabling a link changes subsequent redirect eligibility |
| UC-007 | Independent | — | Provides operational health visibility |

These relationships are descriptive and do not imply that every use case requires a separate API endpoint or software component.

## 12. Cross-Cutting Behaviour

### 12.1 Security

All public creation requests are subject to the applicable validation and abuse prevention policies.

Administrative operations must be restricted to authorised use.

### 12.2 Privacy

Use case execution must not require unnecessary collection of personal information.

Operational diagnostics must comply with the approved logging and retention policies.

### 12.3 Error Handling

Equivalent failures should produce consistent responses according to the approved API error contract.

Unexpected failures must not expose sensitive implementation details.

### 12.4 Data Integrity

Successful creation and administrative disabling operations must be persisted before success is reported.

Short-code uniqueness must remain enforced.

### 12.5 External Website Behaviour

The success of UC-002 means HelixURL has issued the correct redirect response.

It does not mean the external destination website has loaded successfully.

## 13. Requirements Traceability

| Use Case | Primary Functional Requirements | Business Rules |
|---|---|---|
| UC-001 | FR-001–FR-005, FR-012–FR-014 | BR-010–BR-018 |
| UC-002 | FR-015–FR-021 | BR-027–BR-032 |
| UC-003 | FR-022–FR-026 | BR-019–BR-026 |
| UC-004 | FR-006–FR-011 | BR-001–BR-009 |
| UC-005 | FR-018–FR-020 | BR-025, BR-027–BR-031 |
| UC-006 | FR-020, FR-039 | BR-033–BR-037, BR-041 |
| UC-007 | FR-040 | BR-049, BR-051 |

Complete requirement-to-test traceability will be maintained in `traceability.md`.

## 14. Open Questions and Design Dependencies

| ID | Question | Target Stage |
|---|---|---|
| UQ-001 | Exact request and response structures for URL creation | M2 — API Design |
| UQ-002 | Short-code generation strategy | M2 — System Design |
| UQ-003 | Final expiration limits and input format | M1 |
| UQ-004 | Redirect status and cache policy | M1/M2 |
| UQ-005 | Final unavailable-link response semantics | M1 |
| UQ-006 | Administrative disabling mechanism | M2 |
| UQ-007 | Health-check contract and dependency checks | M2 |
| UQ-008 | Rate-limiting enforcement and thresholds | M1/M2 |
| UQ-009 | Destination acceptance policy | M1 |
| UQ-010 | Browser-side success and error presentation | M2/M4 |

## 15. Review and Acceptance Criteria

This document is ready for baseline approval when:

- All mandatory MVP actors are represented.
- Core user goals are covered.
- Main success scenarios are complete.
- Relevant alternative and exception flows are identified.
- Preconditions and postconditions are consistent.
- Use cases align with the Software Requirements Specification.
- Business rules are referenced consistently.
- Security-sensitive operations are appropriately constrained.
- Unresolved decisions are documented.
- The Project Owner has completed the review.

## 16. Review and Approval

| Field | Value |
|---|---|
| Document Owner | Project Owner |
| Review Status | Pending Review |
| Approval Authority | Project Owner |
| Approval Date | To be determined |
| Baseline Status | Not Yet Approved |

This document will be reviewed together with the remaining Requirements Engineering deliverables before the M1 baseline is approved.

---

## Document History

| Version | Date | Description | Status |
|---|---|---|---|
| 0.1.0 | 2026-11-11 | Initial Use Case Specification draft | Pending Review |
