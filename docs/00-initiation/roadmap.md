# HelixURL — Project Development Roadmap

**Document ID:** HLX-RDM-001  
**Version:** 1.0.0
**Status:** Approved — Initial Baseline
**Project:** HelixURL  
**Release Target:** MVP v0.1.0  
**Document Owner:** Project Owner  
**Last Updated:** 2026-11-04

**Related Documents:**
- [Project Charter](./project-charter.md)
- [Project Scope Statement](./scope.md)
- [Risk Register](./risk-register.md) *(planned)*

---

## 1. Purpose

This document defines the planned development roadmap for HelixURL, including delivery milestones, workstreams, dependencies, expected deliverables, completion criteria, and release checkpoints.

The roadmap translates the approved project direction into an actionable, incremental delivery plan.

Its objectives are to:

- Establish a logical sequence of engineering activities.
- Identify dependencies between requirements, design, implementation, testing, and deployment.
- Maintain a clear distinction between MVP deliverables and future enhancements.
- Support milestone-based progress tracking.
- Define verifiable exit criteria.
- Reduce the risk of premature optimisation and unnecessary architectural complexity.
- Provide a reference for planning GitHub Issues, branches, and Pull Requests.

This roadmap is a planning baseline, not a fixed contractual schedule. It will be updated as requirements, technical constraints, and implementation evidence become clearer.

## 2. Delivery Strategy

### 2.1 Development Approach

HelixURL will follow an incremental, milestone-driven development approach with lightweight Agile practices.

The project will combine:

- Requirements-driven planning.
- Architecture and database design.
- Iterative implementation.
- Vertical-slice delivery.
- Continuous automated testing.
- Documentation maintained alongside source code.
- Incremental integration through GitHub Pull Requests.
- Evidence-based technical decisions.

### 2.2 MVP-First Principle

The primary delivery objective is a working, secure-enough-for-public-demonstration, tested, and documented URL shortening service.

The MVP will take precedence over:

- Advanced analytics.
- QR code generation.
- User accounts.
- Custom aliases.
- Redis caching.
- Distributed architecture.
- Nonessential interface enhancements.

Additional functionality must not delay mandatory MVP acceptance criteria unless it addresses a release-blocking defect or security concern.

### 2.3 Vertical-Slice Development

After foundational requirements and design activities, implementation will prioritise complete user-facing workflows.

The initial vertical slices are:

**Slice A — URL Creation**

Receive a request, validate the destination, generate a short identifier, persist the mapping, and return a shortened URL.

**Slice B — URL Redirection**

Receive a short identifier, resolve its mapping, evaluate its validity, and redirect to the destination.

**Slice C — Expiration and Error Handling**

Support optional expiration and consistently handle invalid, expired, and nonexistent links.

**Slice D — Public Web Interface**

Connect the minimal frontend to the backend and demonstrate the complete shortening workflow.

**Slice E — Operational and Security Readiness**

Implement and verify baseline abuse controls, health checks, deployment configuration, and release readiness.

### 2.4 Planning Principles

The following principles guide the roadmap:

1. Deliver working functionality before advanced features.
2. Prefer simple solutions until additional complexity is justified.
3. Keep technical decisions traceable.
4. Test critical functionality as it is implemented.
5. Maintain documentation incrementally.
6. Avoid introducing infrastructure without a defined requirement.
7. Review security risks before public deployment.
8. Keep development tasks small enough for meaningful review.
9. Use measurable evidence to evaluate quality.
10. Reassess the roadmap when assumptions change.

## 3. Milestone Overview

| ID | Milestone | Primary Outcome | Release Relevance |
|---|---|---|---|
| M0 | Project Initiation | Agreed project direction and scope | MVP foundation |
| M1 | Requirements Engineering | Testable requirements baseline | MVP foundation |
| M2 | System Design | Reviewed domain, database, API, and architecture design | MVP foundation |
| M3 | Backend MVP | Working URL creation, persistence, redirection, and expiration | Core MVP |
| M4 | Web MVP | Functional public web interface | Core MVP |
| M5 | Quality and Security | Verified functionality and baseline security controls | Release gate |
| M6 | Deployment | Public HTTPS deployment and operational verification | Release gate |
| M7 | Portfolio Release | Documented and demonstrable MVP release | MVP completion |
| M8 | Post-MVP Enhancements | Selected advanced features | Future releases |

Milestones M0–M7 constitute the planned MVP delivery path.

M8 is a separate enhancement stage and is not required for MVP completion.

## 4. M0 — Project Initiation

### Objective

Establish the project vision, scope, delivery direction, and initial risk awareness.

### Planned Activities

- [x] Verify the local development environment.
- [x] Initialise the GitHub repository.
- [x] Establish the initial Git branching strategy.
- [x] Create the repository directory structure.
- [x] Configure repository-level development conventions.
- [x] Finalise the Project Charter.
- [x] Finalise the Project Scope Statement.
- [x] Finalise the Project Development Roadmap.
- [x] Prepare the initial Risk Register.
- [x] Review consistency across initiation documents.
- [x] Approve the initial project baseline.

### Deliverables

| ID | Deliverable | Location |
|---|---|---|
| M0-D01 | Project Charter | `docs/00-initiation/project-charter.md` |
| M0-D02 | Scope Statement | `docs/00-initiation/scope.md` |
| M0-D03 | Development Roadmap | `docs/00-initiation/roadmap.md` |
| M0-D04 | Risk Register | `docs/00-initiation/risk-register.md` |
| M0-D05 | Initial Repository Structure | Repository root |

### Exit Criteria

M0 is complete when:

- The Project Charter has been reviewed and accepted.
- MVP scope and exclusions are explicitly documented.
- Milestones and dependencies are defined.
- Major initial risks have been identified.
- The initiation documents are consistent.
- The Project Owner approves the Phase 00 baseline.
- Documentation is merged into the `develop` branch.

### Dependencies

None beyond the initial development environment and repository setup.

## 5. M1 — Requirements Engineering

### Objective

Convert the approved MVP scope into clear, consistent, testable software requirements.

### Planned Activities

- [ ] Identify user roles and system boundaries.
- [ ] Define functional requirements.
- [ ] Define non-functional requirements.
- [ ] Document business rules.
- [ ] Develop primary and alternative use cases.
- [ ] Define request validation expectations.
- [ ] Define URL expiration behaviour.
- [ ] Define duplicate destination URL behaviour.
- [ ] Define error and failure scenarios.
- [ ] Define baseline security and abuse prevention requirements.
- [ ] Establish measurable quality targets.
- [ ] Develop acceptance criteria.
- [ ] Review requirements for ambiguity, completeness, and consistency.
- [ ] Establish requirements traceability.

### Expected Deliverables

| ID | Deliverable | Proposed Location |
|---|---|---|
| M1-D01 | Software Requirements Specification | `docs/01-requirements/requirements.md` |
| M1-D02 | Use Cases | `docs/01-requirements/use-cases.md` |
| M1-D03 | Business Rules | `docs/01-requirements/business-rules.md` |
| M1-D04 | Acceptance Criteria | `docs/01-requirements/acceptance-criteria.md` |
| M1-D05 | Requirements Traceability | `docs/01-requirements/traceability.md` |

### Exit Criteria

M1 is complete when:

- Every mandatory MVP capability has corresponding requirements.
- Requirements use unique identifiers.
- Critical functional requirements have verifiable acceptance criteria.
- Relevant failure scenarios are documented.
- Security and operational constraints are represented.
- Conflicting or ambiguous requirements are resolved or explicitly tracked.
- The requirements baseline is reviewed and accepted.

### Dependencies

M0 — Project Initiation.

## 6. M2 — System Design

### Objective

Translate the requirements baseline into an implementable system design.

### Workstream A — Domain Modelling

Activities:

- [ ] Identify core domain concepts.
- [ ] Define entity responsibilities.
- [ ] Define relationships and lifecycle rules.
- [ ] Establish domain invariants.
- [ ] Identify application boundaries.

Expected output:

- Domain model.
- Domain glossary.
- Key business invariants.

### Workstream B — Database Design

Activities:

- [ ] Identify required data entities and attributes.
- [ ] Define primary and foreign keys where applicable.
- [ ] Define uniqueness and integrity constraints.
- [ ] Evaluate appropriate PostgreSQL data types.
- [ ] Design indexes for expected access patterns.
- [ ] Define expiration-related data requirements.
- [ ] Plan database migration management.
- [ ] Review data retention considerations.

Expected output:

- Entity-Relationship Diagram.
- Relational schema.
- Data Dictionary.
- Database design rationale.

### Workstream C — API Design

Activities:

- [ ] Identify public API resources and operations.
- [ ] Define request and response schemas.
- [ ] Define validation errors.
- [ ] Define redirection semantics.
- [ ] Define expiration-related behaviour.
- [ ] Define rate-limit response conventions.
- [ ] Prepare an OpenAPI specification.
- [ ] Review API consistency and usability.

Expected output:

- OpenAPI specification.
- API design notes.
- Representative request and response examples.

### Workstream D — Application Architecture

Activities:

- [ ] Define backend module boundaries.
- [ ] Establish the application layering strategy.
- [ ] Define configuration management.
- [ ] Define exception handling conventions.
- [ ] Define database access boundaries.
- [ ] Plan testing boundaries.
- [ ] Identify security-sensitive operations.
- [ ] Document significant architecture decisions.

Expected output:

- Architecture overview.
- Component diagram.
- Sequence diagrams.
- Initial Architecture Decision Records.

### Expected Deliverables

| ID | Deliverable | Proposed Location |
|---|---|---|
| M2-D01 | Domain Model | `docs/02-design/domain-model.md` |
| M2-D02 | ERD | `docs/02-design/erd.md` |
| M2-D03 | Data Dictionary | `docs/02-design/data-dictionary.md` |
| M2-D04 | Database Design | `docs/02-design/database-design.md` |
| M2-D05 | Architecture Overview | `docs/02-design/architecture.md` |
| M2-D06 | Sequence Diagrams | `docs/02-design/sequence-diagrams.md` |
| M2-D07 | Architecture Decision Records | `docs/02-design/adr/` |
| M2-D08 | OpenAPI Specification | `docs/03-api/openapi.yaml` |

### Exit Criteria

M2 is complete when:

- Core domain concepts are identified.
- The proposed database supports mandatory data requirements.
- Key integrity constraints are defined.
- Public API behaviour is specified.
- The architecture supports core MVP workflows.
- Significant design trade-offs are documented.
- Security-sensitive design assumptions have been reviewed.
- The design is sufficiently detailed to begin implementation.

### Dependencies

M1 — Requirements Engineering.

### Design Flexibility

Design documents may be refined during implementation when new evidence emerges.

Changes that materially affect approved requirements or architecture must be recorded and reviewed.

## 7. M3 — Backend MVP

### Objective

Implement and verify the core URL shortening backend.

### Workstream A — Backend Foundation

Activities:

- [ ] Generate the Spring Boot project.
- [ ] Configure Java and Maven.
- [ ] Establish package and module conventions.
- [ ] Configure environment-specific application settings.
- [ ] Integrate PostgreSQL.
- [ ] Configure Flyway migrations.
- [ ] Establish consistent exception handling.
- [ ] Configure application logging.
- [ ] Add application health support.
- [ ] Create a reproducible local development environment.

### Workstream B — URL Creation

Activities:

- [ ] Implement URL creation request handling.
- [ ] Implement destination validation.
- [ ] Implement short-code generation.
- [ ] Implement uniqueness enforcement.
- [ ] Implement persistence.
- [ ] Define successful and unsuccessful API responses.
- [ ] Add unit and integration tests.

### Workstream C — URL Redirection

Activities:

- [ ] Implement short-code lookup.
- [ ] Implement destination resolution.
- [ ] Implement redirect responses.
- [ ] Handle unknown identifiers.
- [ ] Handle unavailable links.
- [ ] Add relevant tests.

### Workstream D — Link Expiration

Activities:

- [ ] Implement optional expiration input.
- [ ] Validate expiration configuration.
- [ ] Persist expiration timestamps.
- [ ] Reject redirects for expired links.
- [ ] Define behaviour for non-expiring links.
- [ ] Add boundary-condition tests.

### Workstream E — Backend Verification

Activities:

- [ ] Verify API contracts.
- [ ] Test database persistence.
- [ ] Test uniqueness and collision handling.
- [ ] Test expected failure scenarios.
- [ ] Validate application startup and configuration.
- [ ] Review database migration behaviour.
- [ ] Update backend documentation.

### Expected Deliverables

- Executable Spring Boot backend.
- PostgreSQL integration.
- Versioned database migrations.
- Working URL creation API.
- Working redirect functionality.
- Optional expiration support.
- Automated backend tests.
- Updated API documentation.
- Local backend setup instructions.

### Exit Criteria

M3 is complete when:

- Core backend workflows execute successfully.
- URL mappings persist correctly.
- Identifier uniqueness is enforced.
- Expiration behaviour is correct.
- Required API and error contracts are satisfied.
- Critical backend tests pass.
- The backend can be started using documented instructions.

### Dependencies

M2 — System Design.

## 8. M4 — Web MVP

### Objective

Provide a usable web interface for anonymous URL creation.

### Planned Activities

- [ ] Confirm the frontend framework.
- [ ] Initialise the frontend application.
- [ ] Establish frontend folder structure.
- [ ] Implement the URL submission form.
- [ ] Implement optional expiration input.
- [ ] Integrate the URL creation API.
- [ ] Display generated shortened URLs.
- [ ] Implement copy-to-clipboard functionality.
- [ ] Display validation and server errors.
- [ ] Implement loading and submission states.
- [ ] Ensure responsive layouts.
- [ ] Verify basic keyboard accessibility.
- [ ] Add appropriate frontend tests.
- [ ] Document frontend setup and configuration.

### Expected Deliverables

- Functional frontend application.
- Backend API integration.
- Responsive URL creation interface.
- Copy-to-clipboard interaction.
- Error and loading states.
- Relevant automated frontend tests.

### Exit Criteria

M4 is complete when:

- An anonymous user can complete the URL shortening workflow through the browser.
- Successful requests display usable short links.
- Invalid requests produce understandable feedback.
- The interface functions on representative desktop and mobile layouts.
- Basic keyboard interaction works.
- Relevant frontend tests pass.

### Dependencies

M3 — Backend MVP.

Frontend scaffolding and design exploration may begin earlier if they do not block backend implementation.

## 9. M5 — Quality and Security

### Objective

Verify the integrated MVP and establish the minimum safeguards required before public deployment.

### Workstream A — Functional Verification

Activities:

- [ ] Review requirements-to-test traceability.
- [ ] Execute unit tests.
- [ ] Execute integration tests.
- [ ] Execute API contract tests.
- [ ] Verify frontend and backend integration.
- [ ] Test expiration boundaries.
- [ ] Test invalid input handling.
- [ ] Test unknown and unavailable links.
- [ ] Verify persistence across application restarts.
- [ ] Perform manual end-to-end testing.

### Workstream B — Security and Abuse Prevention

Activities:

- [ ] Review destination URL validation.
- [ ] Implement and verify baseline request rate limiting.
- [ ] Define and verify unsafe destination restrictions.
- [ ] Review error response information exposure.
- [ ] Review secrets and configuration handling.
- [ ] Establish an operational link-disabling procedure.
- [ ] Review logging and data minimisation.
- [ ] Review dependency and container security.
- [ ] Document known security limitations.

### Workstream C — Performance Verification

Activities:

- [ ] Define representative test workloads.
- [ ] Measure URL creation latency.
- [ ] Measure redirection latency.
- [ ] Evaluate database lookup performance.
- [ ] Identify obvious bottlenecks.
- [ ] Document test conditions and results.
- [ ] Evaluate whether caching is justified.

Performance optimisation must be driven by evidence rather than assumptions.

### Workstream D — Continuous Integration

Activities:

- [ ] Configure automated backend builds.
- [ ] Configure automated frontend builds.
- [ ] Execute required tests in CI.
- [ ] Add appropriate static checks.
- [ ] Verify pipeline failure behaviour.
- [ ] Document CI workflow expectations.

CI implementation may begin during M3 and M4. M5 serves as the final verification checkpoint.

### Expected Deliverables

- Testing strategy.
- Automated test suite.
- Test execution evidence.
- Security review.
- Baseline abuse prevention controls.
- Performance verification results.
- Working CI pipeline.
- Documented known limitations.

### Exit Criteria

M5 is complete when:

- Mandatory MVP requirements have verification evidence.
- Critical automated tests pass.
- No known release-blocking defects remain.
- Baseline security controls have been implemented and reviewed.
- The CI pipeline passes.
- Relevant performance results are documented.
- Remaining risks are understood and accepted or mitigated.

### Dependencies

M3 — Backend MVP.

M4 — Web MVP.

Security design and testing should begin earlier and continue throughout implementation.

## 10. M6 — Deployment

### Objective

Deploy HelixURL to a publicly accessible environment and verify operational readiness.

### Planned Activities

- [ ] Evaluate available no-cost hosting options.
- [ ] Review provider resource limits and acceptable-use policies.
- [ ] Select backend and database hosting.
- [ ] Select frontend hosting.
- [ ] Configure environment variables and secrets.
- [ ] Configure production database migrations.
- [ ] Configure public application URLs.
- [ ] Enable HTTPS.
- [ ] Configure appropriate CORS behaviour if required.
- [ ] Verify application startup and database connectivity.
- [ ] Verify public URL creation.
- [ ] Verify public redirection.
- [ ] Verify expiration behaviour.
- [ ] Verify health checks and diagnostic logging.
- [ ] Test restart and recovery behaviour.
- [ ] Document deployment and operational procedures.
- [ ] Record hosting limitations and known operational risks.

### Expected Deliverables

- Public frontend URL.
- Public URL shortening service.
- Hosted PostgreSQL database.
- Deployment configuration.
- Deployment guide.
- Basic operations and troubleshooting guide.
- Public smoke-test evidence.

### Exit Criteria

M6 is complete when:

- The application is reachable over HTTPS.
- The core shortening workflow works in the deployed environment.
- Generated short links resolve correctly.
- Persistent data survives normal service restarts.
- Required secrets are managed outside source control.
- Public abuse controls are active.
- Health and failure diagnostics are available.
- Deployment procedures are documented.
- Infrastructure limitations are recorded.

### Dependencies

M5 — Quality and Security.

Infrastructure evaluation and deployment rehearsals may begin before M5 completion.

Public exposure must not bypass mandatory security verification.

## 11. M7 — Portfolio Release

### Objective

Publish a stable, demonstrable, and professionally documented initial MVP release.

### Planned Activities

- [ ] Review all MVP acceptance criteria.
- [ ] Confirm release-blocking defects are resolved.
- [ ] Review the public demo.
- [ ] Update the root README.
- [ ] Document the technology stack accurately.
- [ ] Add architecture diagrams and screenshots.
- [ ] Document local development instructions.
- [ ] Document testing and CI instructions.
- [ ] Summarise important design decisions.
- [ ] Document deployment limitations.
- [ ] Prepare release notes.
- [ ] Create an MVP release tag.
- [ ] Merge the approved release into `main`.
- [ ] Publish a GitHub Release.
- [ ] Verify repository links and demonstration instructions.
- [ ] Prepare a concise technical portfolio summary.

### Expected Deliverables

- HelixURL MVP v0.1.0.
- Updated GitHub README.
- Public demonstration.
- Release notes.
- Tagged source code.
- Architecture and testing documentation.
- Portfolio-ready technical summary.

### Exit Criteria

M7 is complete when:

- All mandatory MVP acceptance criteria are satisfied.
- The deployed application can be demonstrated.
- The repository accurately describes implemented functionality.
- The release has an identifiable version and source revision.
- Setup and testing instructions are reproducible.
- Important limitations are disclosed.
- The Project Owner approves the initial portfolio release.

### Dependencies

M6 — Deployment.

## 12. M8 — Post-MVP Enhancements

### Objective

Extend HelixURL with selected capabilities after the initial MVP is complete.

M8 is not required for MVP acceptance.

### Candidate Enhancements

| Candidate | Potential Value | Key Considerations |
|---|---|---|
| QR Code Generation | Convenient sharing through printed or visual media | QR generation and download formats |
| Privacy-Conscious Analytics | Understand aggregate link usage | Data minimisation, retention, and aggregation |
| Geographic Insights | Understand broad traffic distribution | IP geolocation accuracy and privacy |
| Device and Browser Analytics | Understand client characteristics | User-agent reliability and privacy |
| User Accounts | Support persistent link ownership | Authentication and authorisation |
| Link Management Dashboard | Allow users to manage links | Ownership and access control |
| Custom Aliases | Improve link readability and branding | Namespace collisions and abuse |
| Redis Caching | Improve repeated lookup performance | Cache consistency and operational cost |
| Advanced Abuse Detection | Reduce malicious usage | Detection accuracy and operational overhead |
| Performance Optimisation | Improve efficiency under higher load | Measured bottlenecks and workload evidence |

### Enhancement Selection Criteria

Future enhancements should be evaluated based on:

1. User value.
2. Engineering learning value.
3. Architectural impact.
4. Security and privacy impact.
5. Operational cost.
6. Implementation complexity.
7. Maintenance burden.
8. Availability of reliable verification methods.

Each selected enhancement should have its own requirements, design decisions, tests, and delivery plan.

## 13. Milestone Dependencies

The primary delivery sequence is:

```text
M0 Project Initiation
         |
         v
M1 Requirements Engineering
         |
         v
M2 System Design
         |
         v
M3 Backend MVP
         |
         v
M4 Web MVP
         |
         v
M5 Quality & Security
         |
         v
M6 Deployment
         |
         v
M7 Portfolio Release
         |
         v
M8 Post-MVP Enhancements
```

This sequence represents milestone completion gates rather than a strict prohibition on overlapping activities.

For example:

- CI setup may begin during backend implementation.
- Security testing may begin before M5.
- Frontend prototyping may begin before M3 is complete.
- Hosting evaluation may begin during architecture design.
- Documentation should be updated throughout development.

## 14. Suggested GitHub Work Breakdown

Milestones may be represented using GitHub Milestones.

Individual tasks may be tracked using GitHub Issues.

### Recommended Issue Structure

Each implementation issue should normally include:

- Clear title.
- Problem or objective.
- Relevant requirements.
- Scope of work.
- Acceptance criteria.
- Dependencies.
- Testing expectations.
- Relevant documentation updates.

### Suggested Issue Labels

| Label | Purpose |
|---|---|
| `type:feature` | New functionality |
| `type:bug` | Defect correction |
| `type:docs` | Documentation |
| `type:test` | Testing work |
| `type:chore` | Maintenance or configuration |
| `area:backend` | Backend work |
| `area:frontend` | Frontend work |
| `area:database` | Database work |
| `area:infra` | Infrastructure |
| `area:security` | Security-related work |
| `priority:high` | High-priority work |
| `priority:medium` | Medium-priority work |
| `priority:low` | Low-priority work |

Labels and milestones should be introduced incrementally to avoid unnecessary administrative overhead.

## 15. Git Workflow and Integration

The project will use the following initial branch roles:

- `main` — Stable release branch.
- `develop` — Integration branch.
- `feature/*` — Feature implementation.
- `fix/*` — Bug fixes.
- `docs/*` — Documentation changes.
- `chore/*` — Repository and tooling maintenance.

### Pull Request Expectations

Pull Requests should:

- Describe the purpose of the change.
- Summarise relevant implementation details.
- Reference related issues where applicable.
- Provide appropriate verification evidence.
- Identify limitations or follow-up work.
- Avoid including unrelated changes.

### Integration Policy

Feature and documentation branches will normally target `develop`.

Approved release changes will be integrated into `main` when the relevant release criteria are satisfied.

## 16. Definition of Done

### 16.1 Task-Level Definition of Done

A task is complete when:

- Its acceptance criteria are met.
- Required code or documentation changes are implemented.
- Relevant tests pass.
- No known regression has been introduced.
- Applicable documentation is updated.
- The change has been reviewed.
- The change is merged into the appropriate branch.

### 16.2 Milestone-Level Definition of Done

A milestone is complete when:

- Mandatory deliverables exist.
- Exit criteria have been satisfied.
- Relevant verification evidence is available.
- Blocking issues have been resolved.
- Outstanding risks or limitations are documented.
- The Project Owner approves milestone completion.

### 16.3 MVP Release Definition of Done

The MVP is ready for release when:

- Mandatory scope acceptance criteria are satisfied.
- Core user workflows are functional.
- Automated tests and CI checks pass.
- Required security controls are active.
- Public deployment is verified.
- Documentation is accurate.
- Known limitations are disclosed.
- A versioned release is prepared.

## 17. Schedule and Estimation Policy

The project will use milestone-based planning rather than committing to a fixed delivery date before requirements and technical complexity are sufficiently understood.

Task estimates may be introduced during implementation planning.

Estimates should account for:

- Requirements clarification.
- Design and implementation.
- Testing.
- Debugging.
- Documentation.
- Integration.
- Deployment.
- Unexpected technical issues.

Estimates will be reviewed when new information becomes available.

The absence of fixed dates does not remove the requirement to track progress or prioritise delivery.

## 18. Progress Tracking

Progress will be monitored using:

- GitHub Issues.
- Pull Requests.
- Milestone completion.
- Automated test results.
- CI pipeline status.
- Demonstrable application functionality.
- Updated technical documentation.

A milestone should not be marked complete solely because its code has been written.

Completion must be supported by the corresponding exit criteria.

## 19. Roadmap Review and Change Control

The roadmap should be reviewed:

- At the completion of each milestone.
- When material requirements change.
- When a major technical assumption is invalidated.
- When infrastructure constraints affect delivery.
- When significant security risks are identified.
- Before the MVP release.

Changes should be documented through version control and relevant Pull Requests.

Material changes affecting approved project scope must also be reflected in the Scope Statement and related requirements.

## 20. Approval

| Field | Value |
|---|---|
| Document Owner | Project Owner |
| Review Status | Pending Review |
| Approval Authority | Project Owner |
| Approval Date | To be determined |
| Baseline Status | Not Yet Approved |

This roadmap will be reviewed alongside the Project Charter, Scope Statement, and Risk Register before the Phase 00 baseline is approved.

---

## Document History

| Version | Date | Description | Status |
|---|---|---|---|
| 1.0.0 | 2026-11-08 | Initial development roadmap | Approved - Initial Baseline |
| 0.1.0 | 2026-11-04 | Initial development roadmap draft | Pending Review |
