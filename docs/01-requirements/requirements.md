# HelixURL — Software Requirements Specification

**Document ID:** HLX-SRS-001  
**Version:** 0.1.0  
**Status:** Draft — Pending Review  
**Project:** HelixURL  
**Target Release:** MVP v0.1.0  
**Document Owner:** Project Owner  
**Last Updated:** 2026-11-08

**Related Documents:**
- [Project Charter](../00-initiation/project-charter.md)
- [Project Scope Statement](../00-initiation/scope.md)
- [Development Roadmap](../00-initiation/roadmap.md)
- [Risk Register](../00-initiation/risk-register.md)

---

## 1. Introduction

### 1.1 Purpose

This Software Requirements Specification (SRS) defines the functional and non-functional requirements for the HelixURL Minimum Viable Product (MVP).

The document establishes the expected externally observable behaviour of the system and provides a foundation for system design, implementation, verification, and release acceptance.

Its primary objectives are to:

- Define the capabilities required for the MVP.
- Specify system behaviour under normal and exceptional conditions.
- Identify system interfaces and external dependencies.
- Establish measurable quality requirements where sufficient evidence exists.
- Record unresolved requirements requiring further analysis.
- Support traceability between requirements, design, implementation, and testing.

This document describes what the system must do rather than prescribing detailed implementation algorithms or database structures.

### 1.2 Product Overview

HelixURL is a public URL shortening application that converts eligible destination URLs into shorter, shareable links.

The MVP supports anonymous link creation and URL redirection without requiring user registration or authentication.

The application will provide a public web interface and an HTTP API, backed by persistent relational storage.

### 1.3 Scope

The MVP includes:

- Anonymous URL creation.
- Destination URL validation.
- Unique short identifier generation.
- Persistent URL mapping.
- URL redirection.
- Optional link expiration.
- Minimal web interface.
- Consistent error handling.
- Baseline abuse prevention.
- Operational health and diagnostics.
- Automated testing and public deployment.

The MVP excludes user accounts, public link editing and deletion, analytics dashboards, QR code generation, custom aliases, custom domains, billing, and distributed microservices.

The approved Project Scope Statement remains the authority for MVP boundaries.

### 1.4 Intended Audience

This document is intended for:

- The Project Owner.
- Software developers and reviewers.
- Testers.
- System designers.
- Technical interviewers and portfolio reviewers.
- Future contributors.

### 1.5 Requirement Terminology

The following terms are used consistently:

| Term | Meaning |
|---|---|
| **Shall** | Mandatory requirement |
| **Should** | Recommended but not mandatory |
| **May** | Optional capability |
| **Destination URL** | Original URL supplied for shortening |
| **Short Code** | Identifier used to resolve a stored URL mapping |
| **Short URL** | Public URL containing a short code |
| **URL Mapping** | Persistent association between a short code and destination URL |
| **Active Link** | Link eligible for redirection |
| **Expired Link** | Link whose configured expiration time has been reached |
| **Disabled Link** | Link administratively prevented from redirecting |
| **MVP** | Minimum Viable Product |

### 1.6 Requirement Identification

Requirements use the following identifiers:

- `FR-XXX` — Functional Requirement.
- `NFR-XXX` — Non-Functional Requirement.
- `IR-XXX` — Interface Requirement.
- `DR-XXX` — Data Requirement.
- `SEC-XXX` — Security Requirement.

Each identifier must remain stable after baseline approval.

Detailed business rules and acceptance tests will reference these identifiers.

---

## 2. Overall Description

### 2.1 Product Perspective

HelixURL is a standalone web application comprising:

1. A public web interface.
2. A backend application exposing HTTP endpoints.
3. A relational database for persistent URL mappings.
4. Supporting deployment and operational infrastructure.

The backend will implement the core business logic and act as the authoritative interface for URL creation and resolution.

The initial system will use a modular monolithic architecture.

### 2.2 User Classes

**Anonymous Creator**

A visitor who submits a destination URL and receives a shortened link.

**Link Visitor**

A visitor who opens a shortened link and is redirected to its destination.

**API Consumer**

An application or client that invokes the public URL creation API.

**Project Operator**

The person responsible for deployment, configuration, monitoring, incident response, and disabling abusive links.

The Project Operator is an operational role, not a public authenticated user role in the MVP.

### 2.3 Operating Environment

The proposed environment includes:

- Java 25 LTS.
- Spring Boot.
- PostgreSQL.
- Maven.
- Docker and Docker Compose.
- A browser-based frontend, with React proposed.
- GitHub Actions for continuous integration.
- A publicly accessible HTTPS deployment.

Final versions and hosting providers will be validated during system design and deployment planning.

### 2.4 Constraints

- The initial development team consists of one developer.
- The project targets zero recurring infrastructure charges.
- The MVP must not require user accounts.
- Persistent mappings must be stored in PostgreSQL.
- Public deployment must use HTTPS.
- Baseline abuse controls are mandatory before enabling public anonymous URL creation.
- Free-tier infrastructure may limit availability, storage, and performance.

### 2.5 Assumptions

- Initial usage will be relatively low.
- A single backend and database will be sufficient for the MVP.
- The minimal frontend will provide an adequate user experience.
- The system will not fetch destination website content as part of the core shortening workflow.
- External destination websites may become unavailable independently of HelixURL.
- Advanced analytics are not required for MVP acceptance.

---

## 3. Functional Requirements

### 3.1 URL Creation

**FR-001 — Anonymous URL Creation**

The system shall allow an unauthenticated client to submit a destination URL for shortening.

**FR-002 — Short URL Generation**

For each accepted creation request, the system shall generate a short URL containing a short code.

**FR-003 — Creation Response**

The system shall return the generated short URL and sufficient information for the client to identify the created mapping.

**FR-004 — Independent URL Mappings**

The system shall allow multiple mappings to reference the same destination URL.

Each accepted creation request shall create a new mapping rather than automatically reusing an existing mapping.

**FR-005 — Atomic Creation**

The system shall not report successful URL creation unless the corresponding mapping has been persisted successfully.

### 3.2 Destination Validation

**FR-006 — Required Destination**

The system shall reject a creation request that does not contain a destination URL.

**FR-007 — URL Syntax Validation**

The system shall reject malformed destination URLs according to the documented validation rules.

**FR-008 — Supported Schemes**

The system shall accept only HTTP and HTTPS destination schemes.

**FR-009 — Maximum Destination URL Length**

The system shall reject a destination URL whose submitted string exceeds 2,048 characters.

The character-counting convention shall be specified in the API contract and applied consistently across validation and verification.

**FR-010 — Destination Policy Enforcement**

The system shall reject destination URLs that violate the defined destination acceptance policy.

**FR-011 — Validation Feedback**

The system shall return an appropriate client error response when destination validation fails.

### 3.3 Short-Code Generation

**FR-012 — Short-Code Assignment**

The system shall assign a short code to each successfully created URL mapping.

**FR-013 — Short-Code Uniqueness**

The system shall prevent a short code from being assigned to more than one mapping.

**FR-014 — Collision Handling**

The system shall handle short-code generation conflicts without overwriting an existing mapping.

**FR-015 — Short-Code Resolution**

The system shall support retrieving a stored URL mapping using its short code.

The short-code generation algorithm and format will be determined during system design.

### 3.4 URL Redirection

**FR-016 — Active Link Redirection**

When a client requests an existing, active short URL, the system shall respond with an HTTP redirect to the associated destination URL.

**FR-017 — Correct Destination**

The redirect destination shall correspond to the mapping associated with the requested short code.

**FR-018 — Unknown Short Code**

The system shall return HTTP 404 Not Found when the requested short code does not exist.

**FR-019 — Expired Short URL**

The system shall return HTTP 404 Not Found when the requested short URL has expired.

**FR-020 — Disabled Short URL**

The system shall return HTTP 404 Not Found when the requested short URL has been administratively disabled.

**FR-021 — HTTP Redirect Semantics**

For an active, non-expired, and non-disabled short URL, the system shall return HTTP 302 Found with a Location header containing the stored destination URL.

### 3.5 Link Expiration

**FR-022 — Optional Expiration**

The system shall support creating a short link with a specified expiration time.

**FR-023 — Non-Expiring Default**

When no expiration is supplied, the system shall create a mapping without a scheduled expiration timestamp.

This does not guarantee permanent availability.

**FR-024 — Expiration Validation**

The system shall accept an optional expiration timestamp only when it represents a future instant and does not exceed 365 days from the defined creation-time reference.

The timestamp format and precision shall follow the approved API contract.

**FR-025 — Expiration Enforcement**

The system shall evaluate expiration eligibility when resolving a short code.

**FR-026 — Expiration Boundary**

A link shall be considered expired when the current evaluation time is greater than or equal to its configured expiration timestamp.

### 3.6 Web Interface

**FR-027 — URL Submission Form**

The web interface shall provide a form for submitting a destination URL.

**FR-028 — Expiration Input**

The web interface shall allow the user to specify an optional expiration value.

**FR-029 — Submission Feedback**

The web interface shall indicate when a shortening request is being processed.

**FR-030 — Successful Result**

After successful creation, the web interface shall display the generated short URL.

**FR-031 — Copy Functionality**

The web interface shall provide a method for copying the generated short URL.

**FR-032 — Error Presentation**

The web interface shall present understandable feedback for invalid requests and service failures.

**FR-033 — Anonymous Access**

The core web workflow shall be accessible without registration or login.

### 3.7 Error Handling

**FR-034 — Consistent API Errors**

The system shall provide a documented, consistent structure for API error responses.

**FR-035 — Invalid Request Handling**

The system shall return an appropriate client error for invalid creation requests.

**FR-036 — Request Limit Handling**

The system shall return an appropriate response when a request is rejected by rate limiting.

**FR-037 — Internal Failure Handling**

The system shall handle unexpected internal failures without exposing sensitive implementation details.

**FR-038 — Dependency Failure Handling**

The system shall respond predictably when required dependencies, including the database, are unavailable.

### 3.8 Operational Controls

**FR-039 — Link Disabling**

The system shall provide a controlled operational mechanism for preventing a specified mapping from redirecting.

This mechanism shall not be exposed as an unrestricted public API.

**FR-040 — Health Reporting**

The system shall provide a mechanism for determining basic application health.

**FR-041 — Diagnostic Logging**

The system shall record operationally relevant application events and failures in accordance with the logging and privacy policies.

---

## 4. Interface Requirements

### 4.1 HTTP API

**IR-001 — API Transport**

The backend shall expose its public application functionality through HTTP-based interfaces.

Public deployment shall use HTTPS.

**IR-002 — Structured Data Exchange**

The URL creation API shall use a documented structured request and response format.

JSON is the proposed format.

**IR-003 — API Documentation**

The public API shall be documented using an OpenAPI specification.

**IR-004 — HTTP Status Semantics**

API responses shall use HTTP status codes consistent with the documented operation outcomes.

**IR-005 — Browser Integration**

The backend shall support the required browser-to-API interactions for the selected frontend deployment architecture.

Cross-origin access, where required, shall be explicitly configured.

### 4.2 User Interface

**IR-006 — Browser Access**

The web interface shall be accessible using supported modern web browsers.

**IR-007 — Responsive Presentation**

The interface shall support representative desktop and mobile viewport sizes.

**IR-008 — Accessible Form Controls**

Core form controls shall have appropriate labels and support keyboard interaction.

### 4.3 Database Interface

**IR-009 — PostgreSQL Persistence**

The backend shall use PostgreSQL for persistent URL mapping storage.

**IR-010 — Managed Schema Changes**

Database schema changes shall be managed through version-controlled migrations.

---

## 5. Data Requirements

**DR-001 — Destination Storage**

Each URL mapping shall store its destination URL.

**DR-002 — Identifier Storage**

Each URL mapping shall store a unique short code.

**DR-003 — Creation Timestamp**

Each mapping shall record its creation timestamp.

**DR-004 — Optional Expiration Timestamp**

A mapping may contain an expiration timestamp.

**DR-005 — Link Availability**

The system shall maintain sufficient information to determine whether a mapping has been administratively disabled.

**DR-006 — Mapping Integrity**

Persistent storage shall enforce short-code uniqueness.

**DR-007 — Persistence Across Restarts**

Successfully persisted mappings shall remain available after normal application restarts, subject to applicable retention and operational policies.

**DR-008 — Time Representation**

The system shall use a consistent time representation for stored timestamps and expiration comparisons.

UTC-based storage and evaluation are proposed.

**DR-009 — Data Minimisation**

The system shall avoid collecting visitor information that is unnecessary for the MVP's functional, security, and operational requirements.

**DR-010 — Retention Policy**

The project shall document a retention policy for URL mappings and operational logs before public release.

---

## 6. Security Requirements

**SEC-001 — Public Access Controls**

The system shall implement baseline abuse controls appropriate for anonymous URL creation.

**SEC-002 — Request Rate Limiting**

The system shall limit excessive URL creation requests according to a documented policy.

Exact thresholds and enforcement scope remain to be determined.

**SEC-003 — URL Scheme Restriction**

The system shall reject unsupported or potentially dangerous destination URL schemes.

**SEC-004 — Unsafe Destination Policy**

The system shall implement a documented policy for handling destinations that are prohibited or considered unsafe.

**SEC-005 — Secrets Management**

Credentials and other sensitive configuration values shall not be committed to source control.

**SEC-006 — Error Information Protection**

Public responses shall not disclose stack traces, credentials, internal connection details, or other sensitive implementation information.

**SEC-007 — Operational Control Protection**

The mechanism for disabling links shall be restricted to authorised operational use.

**SEC-008 — Secure Transport**

Publicly deployed interfaces shall use HTTPS.

**SEC-009 — Abuse Response**

The project shall define an operational procedure for investigating and disabling reported abusive links.

**SEC-010 — Logging Protection**

Application logging shall avoid unnecessary exposure of sensitive URL contents and personal information.

**SEC-011 — Destination Fetching Boundary**

The core MVP shortening workflow shall not require server-side retrieval of destination website content.

Any future server-side fetching capability shall undergo dedicated security review before implementation.

---

## 7. Non-Functional Requirements

### 7.1 Reliability

**NFR-001 — Mapping Correctness**

The system shall preserve the association between each short code and its intended destination.

**NFR-002 — Failure Isolation**

Expected invalid requests shall not corrupt stored mappings or prevent unrelated valid requests from being processed.

**NFR-003 — Restart Persistence**

The system shall retain committed URL mappings across ordinary application restarts when persistent storage remains available.

### 7.2 Performance

**NFR-004 — URL Creation Responsiveness**

The system shall meet an agreed URL creation response-time target under a defined representative workload.

**NFR-005 — Redirect Responsiveness**

The system shall meet an agreed redirect response-time target under a defined representative workload.

**NFR-006 — Performance Verification**

Performance claims shall be supported by documented measurements, test conditions, and results.

Numerical performance thresholds will be established before the M5 verification milestone.

### 7.3 Maintainability

**NFR-007 — Modular Structure**

The backend shall separate responsibilities sufficiently to support independent testing and maintainable code.

**NFR-008 — Configuration Separation**

Environment-specific configuration shall be separated from application source code.

**NFR-009 — Documentation Maintenance**

Technical documentation shall be updated when relevant implementation or architectural decisions change.

### 7.4 Testability

**NFR-010 — Automated Core Verification**

Critical business logic and API workflows shall be covered by automated tests.

**NFR-011 — Database Integration Verification**

Persistence and database constraint behaviour shall be verified through integration testing.

**NFR-012 — Continuous Integration**

The project shall provide a CI pipeline that executes required build and verification checks.

### 7.5 Usability and Accessibility

**NFR-013 — Clear User Workflow**

The primary URL creation workflow shall be understandable without requiring technical knowledge.

**NFR-014 — Keyboard Operability**

Core web interface interactions shall be operable using a keyboard.

**NFR-015 — Responsive Layout**

The interface shall remain usable on agreed representative desktop and mobile viewport sizes.

### 7.6 Deployability

**NFR-016 — Reproducible Setup**

The project shall provide documented steps for configuring and running the application locally.

**NFR-017 — Deployment Documentation**

The project shall document the selected deployment process, required configuration, and operational limitations.

**NFR-018 — Cost Constraint**

The initial deployment shall target zero recurring infrastructure charges.

### 7.7 Observability

**NFR-019 — Health Visibility**

The deployed application shall expose or otherwise provide sufficient health information for basic operational checks.

**NFR-020 — Failure Diagnostics**

Application logs shall provide sufficient information to investigate common operational failures without unnecessarily exposing sensitive information.

### 7.8 Availability

**NFR-021 — Availability Transparency**

The project shall document infrastructure-related availability limitations and shall not claim guaranteed continuous uptime without supporting evidence or service commitments.

---

## 8. External Dependencies

The MVP depends on:

- A supported Java and Spring Boot runtime environment.
- PostgreSQL.
- The selected frontend runtime and build tooling.
- A suitable HTTPS hosting environment.
- Source control and CI infrastructure.
- External destination websites for successful end-user navigation.

The availability and behaviour of external destination websites are outside the control of HelixURL.

## 9. Business Rule References

Detailed business rules will be maintained in:

`docs/01-requirements/business-rules.md`

The initial business rule topics include:

- Eligible destination URL formats.
- Duplicate destination handling.
- Short-code uniqueness.
- Default expiration.
- Expiration boundary semantics.
- Disabled-link behaviour.
- Public access limitations.
- Request limits.
- Retention and abuse response policies.

Business rules will be assigned stable `BR-XXX` identifiers.

## 10. Use Case References

Detailed use cases will be maintained in:

`docs/01-requirements/use-cases.md`

Initial use case candidates include:

| ID | Use Case |
|---|---|
| UC-001 | Create Short URL |
| UC-002 | Redirect Using Short URL |
| UC-003 | Create Expiring Short URL |
| UC-004 | Handle Invalid URL Submission |
| UC-005 | Handle Unknown or Unavailable Link |
| UC-006 | Disable Abusive Link |
| UC-007 | Check Application Health |

Use cases will describe actors, preconditions, triggers, main success scenarios, alternative flows, and postconditions.

## 11. Acceptance and Verification

Each mandatory requirement shall be associated with an appropriate verification method.

Verification methods may include:

- Unit testing.
- Integration testing.
- API contract testing.
- End-to-end testing.
- Security testing.
- Performance testing.
- Configuration inspection.
- Documentation review.
- Operational verification.

Detailed acceptance criteria will be maintained in:

`docs/01-requirements/acceptance-criteria.md`

Requirement-to-test relationships will be recorded in:

`docs/01-requirements/traceability.md`

## 12. Open Requirements and Decisions

The following items require further analysis before the relevant requirements or design baseline is approved.

| ID | Item / Decision Status | Resolution Stage / Status |
|---|---|---|
| OQ-001 | Maximum accepted destination URL length — resolved: 2,048 characters (BD-001) | Approved |
| OQ-002 | Maximum allowed expiration duration — resolved: 365 days (BD-002) | Approved |
| OQ-003 | Expiration input format and precision | M1 |
| OQ-004 | Short-code character set and length | M2 |
| OQ-005 | Short-code generation algorithm | M2 |
| OQ-006 | Redirect status approved: HTTP 302 (BD-005); caching remains proposed (BD-012) | Partially resolved |
| OQ-007 | Unavailable-link response approved: HTTP 404 (BD-006) | Approved |
| OQ-008 | Rate-limiting policy and thresholds | M1/M2 |
| OQ-009 | Destination acceptance and abuse policy | M1 |
| OQ-010 | URL mapping and log retention periods | M1/M2 |
| OQ-011 | Performance targets and representative workloads | M1/M5 |
| OQ-012 | Frontend framework confirmation | M2 |
| OQ-013 | Hosting provider and operational limitations | M2/M6 |
| OQ-014 | Controlled link-disabling mechanism | M2 |
| OQ-015 | Browser and accessibility verification targets | M1 |
| OQ-016 | Public creation restrictions during initial demo | M1/M6 |

For decision status and rationale, see [Requirements Decision Log](./decision-log.md).

Open questions must not be silently treated as approved requirements.

Items affecting mandatory MVP behaviour must be resolved before the relevant implementation or release decision.

## 13. Requirements Review Criteria

The requirements baseline will be considered ready for approval when:

- Mandatory MVP capabilities are represented.
- Requirements are clear and individually identifiable.
- Critical behaviours are testable.
- Business rules are documented.
- Relevant failure scenarios are covered.
- Security and operational constraints are addressed.
- Conflicting requirements are resolved.
- Important numerical thresholds are specified or explicitly scheduled for validation.
- Use cases and acceptance criteria are consistent with the requirements.
- Requirements traceability has been established.
- The Project Owner has completed the review.

## 14. Requirements Change Management

Material changes to approved requirements must be assessed for their impact on:

- MVP scope.
- Business rules.
- API contracts.
- Database design.
- Security controls.
- Testing.
- Delivery milestones.
- Deployment and operational cost.

Approved changes must be reflected in the relevant documentation and version control history.

## 15. Review and Approval

| Field | Value |
|---|---|
| Document Owner | Project Owner |
| Review Status | Pending Review |
| Approval Authority | Project Owner |
| Approval Date | To be determined |
| Baseline Status | Not Yet Approved |

This document will be reviewed together with the Business Rules, Use Cases, Acceptance Criteria, and Requirements Traceability Matrix before the M1 baseline is approved.

---

## Document History

| Version | Date | Description | Status |
|---|---|---|---|
| 0.1.0 | 2026-11-08 | Initial Software Requirements Specification draft | Pending Review |
