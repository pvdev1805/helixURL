# HelixURL — Project Scope Statement

**Document ID:** HLX-SCP-001  
**Version:** 1.0.0
**Status:** Approved — Initial Baseline  
**Project:** HelixURL  
**Release Target:** MVP v0.1.0  
**Document Owner:** Project Owner  
**Last Updated:** 2026-11-04  
**Related Document:** [Project Charter](./project-charter.md)

---

## 1. Purpose

This document defines the scope of the HelixURL Minimum Viable Product (MVP), including its intended deliverables, functional boundaries, quality expectations, assumptions, constraints, exclusions, and scope acceptance criteria.

The Project Scope Statement establishes a shared understanding of what the initial release will and will not deliver.

It is intended to:

- Translate the high-level direction of the Project Charter into a defined delivery boundary.
- Prevent uncontrolled expansion of the MVP.
- Clarify responsibilities and expected deliverables.
- Identify assumptions that require validation.
- Provide a baseline for subsequent requirements analysis, system design, implementation, and testing.
- Support objective evaluation of MVP completion.

This document defines project scope rather than detailed software behaviour. Specific business rules, API contracts, validation rules, data models, and measurable non-functional requirements will be developed during subsequent phases.

## 2. Project Overview

HelixURL is a public web-based URL shortening application that allows users to transform long URLs into shorter, shareable links.

The initial MVP will support anonymous URL creation and redirection without requiring user registration or authentication.

The system will persist URL mappings in PostgreSQL and expose its core functionality through a backend API and a minimal web interface.

The application will be developed using Java, Spring Boot, and supporting technologies, with a preference for open-source tools and no-cost infrastructure.

The initial release will prioritise:

1. Functional correctness.
2. Reliable URL mapping and redirection.
3. Basic security and abuse prevention.
4. Maintainable application architecture.
5. Automated verification.
6. Reproducible deployment.
7. Professional technical documentation.

Advanced link management and analytics capabilities are explicitly deferred.

## 3. Product Scope

### 3.1 Product Boundary

The HelixURL MVP will provide a URL shortening service with two primary user workflows.

**Workflow A — Create a Short URL**

A public user submits an eligible destination URL through the web interface or API.

The system validates the request, generates a short identifier, persists the mapping, and returns a shortened URL.

**Workflow B — Follow a Short URL**

A visitor opens a previously generated short URL.

The system resolves the identifier, evaluates the link's availability, and redirects the visitor to the stored destination when the link is valid and active.

The MVP will also include supporting capabilities required to operate these workflows safely and predictably.

### 3.2 Primary User Groups

| User Group | Supported Activities | Limitations |
|---|---|---|
| Anonymous Creator | Submit a URL, optionally configure expiration, receive and copy a short link | No account-based ownership, dashboard, editing, or deletion |
| Link Visitor | Open an active short link and follow its redirect | Cannot manage the destination or link configuration |
| Project Operator | Deploy, configure, monitor, and troubleshoot the service | Operational activities are not exposed as a public administration dashboard |
| API Consumer | Invoke documented public API functionality | Subject to validation, request limits, and acceptable-use controls |

### 3.3 Access Model

The MVP will use an anonymous public access model.

No user registration, login, password storage, or account-based authorisation will be required for URL creation or redirection.

Because anonymous users cannot establish ownership of created links, the MVP will not provide public link modification or deletion operations.

Operational intervention for abuse response may be supported through controlled internal procedures, but an administration dashboard is outside the MVP scope.

## 4. In-Scope Functional Capabilities

### 4.1 URL Creation

**Scope ID:** SCP-F-001  
**Priority:** Must Have

The system will allow anonymous users to submit eligible destination URLs and receive shortened URLs.

The capability includes:

- Accepting a destination URL through the public API.
- Accepting a destination URL through the web interface.
- Validating the request before creating a mapping.
- Generating a short identifier.
- Ensuring identifier uniqueness.
- Persisting the URL mapping.
- Returning the generated shortened URL.
- Reporting validation or processing failures consistently.

Detailed request and response structures will be defined in the API specification.

### 4.2 Destination URL Validation

**Scope ID:** SCP-F-002  
**Priority:** Must Have

The system will validate submitted destination URLs before accepting them.

The proposed baseline includes:

- Requiring an absolute URL.
- Allowing only HTTP and HTTPS schemes.
- Rejecting malformed or unsupported URL formats.
- Enforcing documented input length limits.
- Rejecting destination URLs that violate the defined security policy.
- Returning meaningful validation errors.

Additional destination restrictions, including rules for local, private, reserved, and otherwise unsafe network destinations, will be defined during security requirements analysis.

Validation must not be treated as proof that an external website is trustworthy.

### 4.3 Short Identifier Generation

**Scope ID:** SCP-F-003  
**Priority:** Must Have

The system will generate a short identifier for each accepted creation request.

The implementation must:

- Produce identifiers that comply with documented format constraints.
- Prevent duplicate identifiers from being assigned to different URL mappings.
- Handle identifier generation conflicts safely.
- Support reliable identifier lookup.

The specific generation algorithm, character set, and identifier length will be determined during system design.

The MVP does not require identifiers to be cryptographically unpredictable unless subsequent security analysis establishes that requirement.

### 4.4 URL Redirection

**Scope ID:** SCP-F-004  
**Priority:** Must Have

The system will resolve short identifiers and redirect visitors to the corresponding destinations.

The capability includes:

- Looking up an identifier.
- Retrieving the stored destination.
- Checking whether the link is active and unexpired.
- Returning an appropriate HTTP redirect response.
- Handling unknown, expired, or unavailable links.
- Preventing internal application details from being exposed through error responses.

The exact HTTP status codes, cache behaviour, and redirect semantics will be specified during API design.

### 4.5 URL Persistence

**Scope ID:** SCP-F-005  
**Priority:** Must Have

The system will persist URL mappings in PostgreSQL.

The persistence layer will support:

- Storage of destination URLs.
- Storage of short identifiers.
- Creation timestamps.
- Optional expiration timestamps.
- Link availability information where required.
- Uniqueness and integrity constraints.
- Retrieval by short identifier.

Database entities, tables, indexes, and migration scripts will be defined during database design and implementation.

### 4.6 Optional Link Expiration

**Scope ID:** SCP-F-006  
**Priority:** Must Have

Users will be able to create links with an optional expiration time.

The system will:

- Accept a valid expiration configuration.
- Store the corresponding expiration timestamp.
- Permit redirection before expiration.
- Prevent redirection after expiration.
- Handle invalid expiration requests.
- Provide a consistent response for expired links.

For links without a user-specified expiration, the initial proposal is to apply no scheduled expiration by default.

A non-expiring link is not guaranteed permanent availability. Links may be disabled for security, abuse prevention, or operational reasons in accordance with documented policies.

This proposal remains subject to operational, abuse prevention, storage, and retention policy review.

Expired records are not required to be physically deleted immediately.

### 4.7 Minimal Web Interface

**Scope ID:** SCP-F-007  
**Priority:** Must Have

The MVP will include a responsive web interface for the core URL shortening workflow.

The interface will allow users to:

- Enter a destination URL.
- Optionally configure link expiration.
- Submit a shortening request.
- View the generated short link.
- Copy the short link.
- Receive validation and error feedback.

The interface should provide basic keyboard accessibility, appropriate form labels, and usable layouts across common desktop and mobile screen sizes.

A user dashboard, account profile, analytics interface, and advanced link management interface are excluded.

### 4.8 Error Handling

**Scope ID:** SCP-F-008  
**Priority:** Must Have

The system will provide consistent error handling across its public interfaces.

Expected error categories include:

- Invalid input.
- Unknown short identifier.
- Expired or unavailable link.
- Request limit exceeded.
- Unexpected server failure.
- Temporary dependency or service unavailability.

The detailed error response contract will be defined during API design.

### 4.9 Baseline Abuse Prevention

**Scope ID:** SCP-F-009  
**Priority:** Must Have

The MVP will include baseline controls appropriate for a publicly accessible anonymous URL shortening service.

The scope includes:

- Request rate limiting or equivalent abuse controls.
- Input and destination policy enforcement.
- Rejection of unsupported URL schemes.
- Restrictions intended to reduce unsafe or prohibited destinations.
- Appropriate request and error logging.
- A controlled mechanism for disabling abusive links.
- Operational guidance for handling reported misuse.

The exact rate limits, enforcement mechanism, destination policies, and administrative procedures will be determined during security design.

The MVP is not expected to provide comprehensive phishing detection or guarantee that all malicious destinations can be identified.

### 4.10 Application Health and Diagnostics

**Scope ID:** SCP-F-010  
**Priority:** Must Have

The application will provide basic operational diagnostics, including:

- Application health information.
- Structured or consistently formatted application logs.
- Appropriate error diagnostics.
- Configuration of logging behaviour.
- Basic visibility into application and database failures.

Public diagnostic endpoints must not expose sensitive configuration or internal details.

Advanced monitoring dashboards and distributed tracing are excluded.

## 5. In-Scope Engineering Deliverables

### 5.1 Backend Application

A maintainable Spring Boot application implementing the defined MVP API and business logic.

### 5.2 Database

A PostgreSQL database schema with version-controlled migration scripts and appropriate integrity constraints.

### 5.3 Frontend Application

A minimal web application supporting URL creation and result presentation.

React is the proposed frontend technology, subject to confirmation during architecture design.

### 5.4 Automated Tests

Automated tests covering critical business logic, persistence behaviour, API interactions, and relevant error scenarios.

### 5.5 CI Pipeline

A GitHub Actions workflow capable of running the project's required build and verification steps.

### 5.6 Containerised Development Environment

A reproducible local development setup using Docker and Docker Compose where appropriate.

### 5.7 Public Demonstration

A publicly accessible HTTPS deployment of the MVP, subject to infrastructure availability and acceptable-use requirements.

### 5.8 Technical Documentation

Documentation covering:

- Project objectives and scope.
- Functional and non-functional requirements.
- Business rules and use cases.
- Domain and database design.
- API contracts.
- Architecture and significant design decisions.
- Local development setup.
- Testing strategy and results.
- Security considerations.
- Deployment and operational procedures.
- Known limitations and future enhancements.

## 6. Out-of-Scope Capabilities

The following capabilities are not part of the MVP commitment.

| ID | Capability | Reason for Exclusion |
|---|---|---|
| SCP-X-001 | User registration and authentication | Introduces additional security and account-management complexity |
| SCP-X-002 | Account-based link ownership | Depends on an identity and authorisation model |
| SCP-X-003 | Public link editing and deletion | Requires a secure ownership or capability-based access mechanism |
| SCP-X-004 | User dashboards | Not required for the core shortening workflow |
| SCP-X-005 | Click analytics dashboards | Requires additional data collection, aggregation, and privacy decisions |
| SCP-X-006 | Geographic traffic analytics | Introduces geolocation accuracy, privacy, and retention considerations |
| SCP-X-007 | Device and browser analytics | Not necessary for core functionality |
| SCP-X-008 | QR code generation | Optional enhancement unrelated to core URL mapping |
| SCP-X-009 | Custom aliases | Requires additional naming, reservation, and abuse policies |
| SCP-X-010 | Custom domains | Introduces domain ownership verification and DNS management |
| SCP-X-011 | Subscription plans and billing | No commercial monetisation planned |
| SCP-X-012 | Team workspaces and role-based access | Requires multi-user identity and permission management |
| SCP-X-013 | Comprehensive threat intelligence | Beyond the intended MVP complexity and budget |
| SCP-X-014 | Redis caching | Not justified before establishing performance requirements and measurements |
| SCP-X-015 | Microservices and message brokers | Unnecessary for initial operational requirements |
| SCP-X-016 | Multi-region infrastructure | Incompatible with the initial project scale and cost constraints |
| SCP-X-017 | Guaranteed 24/7 availability | Cannot be committed under the proposed infrastructure constraints |
| SCP-X-018 | Dedicated native mobile applications | The MVP is web-based |

The exclusions do not prevent future implementation through separately approved enhancements.

## 7. Scope Boundaries and Proposed Policies

### 7.1 Duplicate Destination URLs

The MVP will permit multiple shortening requests for the same destination URL.

The proposed behaviour is to create a new mapping for each accepted request rather than automatically reusing an existing short link.

**Rationale:** Independent mappings simplify the creation contract and avoid ambiguity when expiration settings differ.

This policy will be reviewed during Requirements Engineering.

### 7.2 Short Link Ownership

Anonymous links will not have account-based owners.

The system will not assume that knowledge of a short URL establishes permission to modify or delete it.

### 7.3 Link Editing and Deletion

Public editing and deletion operations are excluded.

An internal operational capability to disable abusive links is included as part of baseline abuse prevention.

The internal implementation and access controls will be designed separately.

### 7.4 Link Expiration and Retention

Expiration will determine whether a link remains eligible for redirection.

Expiration does not automatically imply immediate deletion of the underlying database record.

Data retention and cleanup policies will be established before public deployment.

### 7.5 URL Destination Availability

HelixURL will maintain URL mappings but will not guarantee that external destination websites remain available, safe, or unchanged.

The service is not responsible for the content or uptime of external websites.

### 7.6 Service Availability

The MVP will target a usable public demonstration but will not guarantee uninterrupted availability.

Any provider-imposed cold starts, quotas, or maintenance limitations must be documented.

### 7.7 Data Collection

The MVP will avoid collecting unnecessary visitor information.

Advanced click tracking, geographic analytics, and device fingerprinting are excluded.

Security and operational logs may contain limited request metadata, subject to documented minimisation, access, and retention policies.

## 8. Non-Functional Scope

### 8.1 Reliability

The application must preserve the correctness of URL mappings and handle expected failures predictably.

### 8.2 Performance

The system should support responsive URL creation and redirection under an agreed MVP workload.

Numerical latency and throughput targets will be defined during Requirements Engineering and evaluated against the selected deployment environment.

### 8.3 Security

The application must implement reasonable baseline security controls for anonymous public access.

Security requirements will address input validation, abuse prevention, secrets management, error exposure, logging, and deployment configuration.

### 8.4 Maintainability

The backend should use a modular structure with clearly separated responsibilities.

Code should follow documented conventions and remain testable without unnecessary infrastructure dependencies.

### 8.5 Testability

Critical business workflows must be verifiable through automated tests.

Tests should provide meaningful evidence of expected behaviour, including failure scenarios.

### 8.6 Usability and Accessibility

The web interface should be simple, understandable, keyboard-operable, and usable on common screen sizes.

Detailed accessibility acceptance criteria will be established during requirements analysis.

### 8.7 Deployability

The application must support documented local setup and reproducible deployment.

Environment-specific configuration and secrets must be managed separately from source code.

### 8.8 Observability

The application must provide sufficient health information and diagnostic logging to investigate common operational failures.

### 8.9 Cost Efficiency

The initial deployment should avoid recurring infrastructure charges.

Cost and resource limitations must be evaluated before selecting a hosting provider.

## 9. Project Deliverables and Acceptance Evidence

| Deliverable | Expected Evidence |
|---|---|
| Requirements baseline | Reviewed requirements, business rules, and acceptance criteria |
| Architecture design | Architecture diagrams and documented design decisions |
| Database implementation | Migration scripts and verified persistence behaviour |
| Backend API | Working endpoints and API specification |
| Frontend interface | Usable shortening workflow |
| Automated verification | Passing test results for agreed critical scenarios |
| CI pipeline | Successful automated build and test execution |
| Security baseline | Documented controls and verification evidence |
| Deployment | Public HTTPS URL and deployment instructions |
| Documentation | Updated README and relevant engineering documents |
| MVP release | Tagged release with known limitations and release notes |

## 10. MVP Acceptance Criteria

The MVP will be considered complete when all mandatory criteria below are satisfied.

| ID | Acceptance Criterion |
|---|---|
| AC-SCP-001 | An anonymous user can submit a valid destination URL and obtain a shortened URL. |
| AC-SCP-002 | The generated short identifier uniquely identifies its stored URL mapping. |
| AC-SCP-003 | A valid, active short URL redirects to its stored destination. |
| AC-SCP-004 | Invalid destination URLs are rejected according to documented validation rules. |
| AC-SCP-005 | Unknown identifiers produce the documented unavailable-link response. |
| AC-SCP-006 | Expired links do not redirect after their expiration time. |
| AC-SCP-007 | URL mappings persist across normal application restarts. |
| AC-SCP-008 | The minimal web interface supports the complete URL creation workflow. |
| AC-SCP-009 | Baseline public abuse prevention controls are implemented and verified. |
| AC-SCP-010 | Critical business and API workflows have passing automated tests. |
| AC-SCP-011 | The required CI pipeline completes successfully. |
| AC-SCP-012 | The application is accessible through a public HTTPS deployment at the time of release verification. |
| AC-SCP-013 | Local setup, configuration, deployment, and known limitations are documented. |
| AC-SCP-014 | No credentials or other sensitive secrets are committed to the repository. |
| AC-SCP-015 | No unresolved release-blocking defects remain. |

Detailed test scenarios and objective quality thresholds will be defined in the requirements and testing documents.

## 11. Assumptions

The scope is based on the following assumptions:

- A single developer will implement and maintain the MVP.
- Initial traffic will be low enough for a single backend application and relational database.
- Anonymous access is acceptable for the initial product model.
- No advanced user management is required for the MVP.
- The frontend can remain minimal without compromising core usability.
- The selected infrastructure can support a functional demonstration within free-tier constraints.
- External destination websites are outside the operational control of HelixURL.
- Advanced features can be developed independently after MVP completion.

Assumptions will be reviewed whenever new requirements or technical evidence emerge.

## 12. Constraints

### 12.1 Budget

The project targets zero recurring infrastructure expenditure.

### 12.2 Team Size

Development and operations will initially be performed by one developer.

### 12.3 Technology

The initial technical direction includes Java 25, Spring Boot, PostgreSQL, Docker, and a web frontend.

Technology decisions remain subject to compatibility and architecture review.

### 12.4 Infrastructure

Free-tier provider restrictions may affect availability, performance, storage, and deployment options.

### 12.5 Schedule

Delivery will follow milestone completion rather than an unverified fixed release deadline.

### 12.6 Security

The public anonymous access model requires baseline abuse prevention before deployment.

## 13. Dependencies

The MVP depends on:

- Completion of requirements and acceptance criteria.
- Approval of core domain and API design.
- A supported backend build environment.
- Availability of a PostgreSQL development environment.
- Successful implementation of persistence and redirect behaviour.
- Availability of suitable public deployment infrastructure.
- Implementation of mandatory security controls.
- Successful automated and manual verification.

Dependencies will be refined in the project roadmap.

## 14. Scope Prioritisation

The project will use a simplified MoSCoW prioritisation approach.

**Must Have**

Capabilities required for the MVP to function safely and satisfy its release criteria.

**Should Have**

Useful improvements that can be deferred without preventing MVP release.

**Could Have**

Optional enhancements considered after mandatory requirements are complete.

**Won't Have (This Release)**

Capabilities explicitly excluded from the initial MVP.

| Priority | Capabilities |
|---|---|
| Must Have | URL creation, validation, unique identifiers, persistence, redirection, expiration, minimal UI, baseline abuse prevention, automated tests, CI, deployment |
| Should Have | Improved error-page presentation, additional diagnostic tooling, developer experience refinements |
| Could Have | Enhanced UI polish, additional configuration options, optional usage metrics that do not introduce visitor tracking |
| Won't Have | Authentication, dashboards, advanced analytics, QR codes, custom aliases, Redis caching, microservices |

A Should Have or Could Have item must not delay completion of mandatory release criteria.

## 15. Scope Change Management

### 15.1 Change Identification

A proposed scope change must describe:

- The requested capability or modification.
- The reason for the change.
- The expected user or engineering value.
- Its impact on existing requirements.
- Its technical and operational implications.
- Its impact on cost, security, and delivery effort.

### 15.2 Change Evaluation

The Project Owner will assess whether the change:

1. Is necessary for core MVP functionality.
2. Addresses a release-blocking defect or security concern.
3. Can be deferred to a later release.
4. Introduces dependencies or complexity disproportionate to its value.
5. Requires updates to requirements, design, or operational documentation.

### 15.3 Change Decision

A proposed change may be:

- Approved for the current MVP.
- Deferred to the enhancement backlog.
- Rejected.
- Returned for further analysis.

Material changes to the approved scope must be reflected in this document and the related requirements or roadmap.

### 15.4 Scope Baseline

Once reviewed and approved, this document will serve as the initial MVP scope baseline.

Subsequent changes will be tracked through version control and the project's normal review process.

## 16. Definition of Done

An implementation task is considered complete when:

- Its agreed acceptance criteria are satisfied.
- Relevant automated tests pass.
- Existing functionality has not been knowingly broken.
- Relevant documentation is updated.
- No sensitive information is introduced into version control.
- The change has been reviewed.
- The change has been integrated into the appropriate development branch.

The MVP as a whole is complete only when the mandatory acceptance criteria in Section 10 are satisfied.

Task-level completion does not automatically imply release readiness.

## 17. Scope Review and Approval

| Field | Value |
|---|---|
| Document Owner | Project Owner |
| Review Status | Pending Review |
| Approval Authority | Project Owner |
| Approval Date | To be determined |
| Baseline Status | Not Yet Approved |

The document will be reviewed together with the Project Charter, Roadmap, and Risk Register before the Phase 00 scope baseline is established.

---

## Document History

| Version | Date | Description | Status |
|---|---|---|---|
| 1.0.0 | 2026-11-08 | Initial MVP Scope Statement | Approved - Initial Baseline |
| 0.1.0 | 2026-11-04 | Initial MVP Scope Statement draft | Pending Review |
