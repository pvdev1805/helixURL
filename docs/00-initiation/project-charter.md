# HelixURL — Project Charter

**Document ID:** HLX-CHR-001  
**Version:** 1.0.0
**Status:** Approved — Initial Baseline
**Project:** HelixURL  
**Project Type:** Software Engineering Portfolio Project  
**Development Model:** Solo Development  
**Prepared By:** Project Owner / Developer  
**Last Updated:** 2026-11-02

---

## 1. Executive Summary

HelixURL is a web-based URL shortening service designed to transform long URLs into shorter, shareable links while providing a reliable and straightforward redirection experience.

The initial release will operate as a **public URL shortener**, allowing users to create shortened links without registering or signing into an account.

The project will be developed incrementally, beginning with a Minimum Viable Product (MVP) that implements the essential URL shortening workflow, persistent storage, input validation, expiration handling, and basic abuse prevention.

Subsequent releases may introduce advanced capabilities, including QR code generation, privacy-conscious click analytics, geographic traffic insights, custom aliases, and account-based link management.

HelixURL also serves as a practical software engineering portfolio project, demonstrating the application of requirements engineering, system analysis, database design, API development, software testing, security principles, continuous integration, deployment, and technical documentation.

The project will prioritise maintainability, reliability, security, and cost-conscious deployment over unnecessary architectural complexity.

## 2. Project Background

### 2.1 Context

URLs are essential for navigating and sharing resources on the internet. However, long URLs containing complex paths, query parameters, and tracking information can be inconvenient to share, particularly in messages, printed materials, and other space-constrained environments.

URL shortening services address this problem by mapping long URLs to shorter identifiers that can redirect users to their intended destinations.

Although the core concept is relatively simple, implementing a dependable public URL shortening service introduces several important software engineering challenges:

- Generating unique and manageable short identifiers.
- Persisting URL mappings and retrieving them efficiently.
- Handling invalid, expired, disabled, or nonexistent links.
- Managing concurrent requests and preventing identifier collisions.
- Protecting publicly accessible endpoints against automated abuse.
- Maintaining reliable redirection behaviour.
- Supporting future growth without introducing unnecessary complexity.
- Operating within limited infrastructure and financial resources.

These challenges provide an opportunity to apply software engineering principles in a practical, end-to-end development project.

### 2.2 Problem Statement

Users need a simple and accessible method of converting lengthy URLs into shorter links that are easier to share.

A basic shortening mechanism alone is insufficient for a dependable public service. The system must also validate submitted URLs, manage link lifecycles, prevent identifier conflicts, and handle failures predictably.

Furthermore, operating a publicly accessible URL shortening service introduces security and abuse risks that must be considered from the beginning of the project.

HelixURL aims to address these requirements through a lightweight, maintainable, and extensible application.

### 2.3 Project Opportunity

HelixURL provides an opportunity to:

1. Develop a functional URL shortening application accessible through a web browser.
2. Apply a structured software development lifecycle from project initiation through deployment.
3. Demonstrate backend engineering, relational database design, REST API development, and automated testing.
4. Explore reliability, security, performance, and scalability trade-offs.
5. Establish a foundation for future analytics and link-management capabilities.
6. Produce a documented and demonstrable software engineering portfolio project.

## 3. Project Vision

> To build a reliable, accessible, and extensible URL shortening platform that simplifies link sharing while demonstrating sound software engineering practices.

The long-term vision is to evolve HelixURL from a basic URL shortener into a privacy-conscious link management and analytics platform.

This long-term vision does not expand the committed scope of the initial MVP.

## 4. Project Objectives

The following objectives define the intended outcomes of the initial project.

| ID | Objective | Expected Outcome |
|---|---|---|
| OBJ-01 | Deliver core URL shortening functionality | Users can submit valid URLs and receive shortened links. |
| OBJ-02 | Provide reliable redirection | Valid, active short links redirect to their associated destinations. |
| OBJ-03 | Ensure persistent URL storage | URL mappings survive application restarts and deployments under normal operating conditions. |
| OBJ-04 | Enforce input and data integrity | Invalid inputs are rejected and duplicate short-code assignments are prevented. |
| OBJ-05 | Support basic link lifecycle management | The system handles active, expired, and nonexistent links appropriately. |
| OBJ-06 | Establish baseline security controls | Public endpoints implement appropriate validation and basic abuse protection. |
| OBJ-07 | Maintain software quality | Critical functionality is covered by automated tests and documented engineering practices. |
| OBJ-08 | Provide an accessible demonstration | A minimal web interface and public HTTPS deployment are available for evaluation. |
| OBJ-09 | Maintain cost-conscious operation | Development and initial deployment target zero recurring infrastructure charges. |
| OBJ-10 | Produce professional project documentation | Requirements, design decisions, testing, and deployment procedures are recorded and maintained. |

Detailed acceptance criteria, performance targets, and quality thresholds will be established during Requirements Engineering.

## 5. Target Users and Stakeholders

### 5.1 Target Users

**Primary Users — Public Visitors**

Individuals who want to shorten and share URLs without creating an account.

Expected activities include:

- Submitting a destination URL.
- Receiving a shortened URL.
- Copying and sharing the shortened URL.
- Following a shortened URL to its destination.
- Optionally specifying an expiration time if supported by the final MVP requirements.

**Secondary Users — API Consumers**

Developers or automated clients that may interact with the URL shortening API.

Programmatic access will be subject to the same applicable validation and abuse prevention requirements as browser-based access.

A dedicated developer platform, API key management system, or commercial API service is not included in the initial scope.

### 5.2 Project Stakeholders

| Stakeholder | Role | Primary Interest |
|---|---|---|
| Project Owner | Product owner, analyst, developer, tester, and operator | Successful delivery, technical quality, maintainability, and learning outcomes |
| Public Users | End users | Ease of use, dependable redirection, and accessibility |
| Portfolio Reviewers | Recruiters, technical interviewers, and engineering reviewers | Demonstrable functionality, code quality, documentation, and engineering decisions |
| Infrastructure Providers | External service providers | Compliance with acceptable-use policies and resource limits |
| External Website Operators | Owners of destination websites | Appropriate handling of redirects and prevention of malicious misuse |

Stakeholder needs will be refined during Requirements Engineering.

## 6. High-Level Project Scope

### 6.1 MVP Scope

The initial MVP is expected to include:

**URL Creation**

- Accept an original URL through a public interface or API.
- Validate submitted URLs according to defined rules.
- Generate a unique short identifier.
- Store the mapping between the short identifier and original URL.
- Return a usable shortened link.

**URL Redirection**

- Resolve an existing short identifier.
- Redirect users to the associated original URL.
- Handle nonexistent, expired, or otherwise unavailable links consistently.

**Link Lifecycle**

- Record creation timestamps.
- Support optional link expiration.
- Prevent expired links from redirecting after their validity period.

**Web Interface**

- Provide a minimal interface for submitting URLs.
- Display the generated short link.
- Allow users to copy the generated link.
- Display meaningful validation and failure messages.

**Security and Reliability**

- Apply URL input validation.
- Enforce uniqueness at the persistence layer.
- Introduce basic request abuse controls before public deployment.
- Avoid exposing internal implementation details through public error responses.
- Provide appropriate application health and diagnostic capabilities.

**Engineering Quality**

- Maintain source code in Git.
- Provide documented setup instructions.
- Implement automated tests for critical workflows.
- Establish a basic continuous integration pipeline.
- Package the application for reproducible deployment.
- Deploy a publicly accessible demonstration over HTTPS.

### 6.2 Out of Scope for the Initial MVP

The following capabilities are deferred to potential future releases:

- User registration and authentication.
- Personal dashboards and account-based URL management.
- Advanced click analytics.
- Browser, operating system, and device analytics.
- IP-based geographic traffic analytics.
- QR code generation and campaign management.
- Custom aliases and branded short domains.
- Team collaboration and role-based access control.
- Subscription plans, payments, and billing.
- Advanced threat intelligence integrations.
- Distributed microservices architecture.
- Multi-region deployment and high-availability infrastructure.

These exclusions may be reconsidered through the project's change management process.

### 6.3 Scope Management

The MVP scope will be refined in a dedicated Scope Statement and Software Requirements Specification.

Any proposed feature that materially affects development effort, operational cost, security, or architectural complexity must be evaluated before inclusion.

Priority will be given to delivering a stable and testable end-to-end URL shortening workflow before introducing advanced features.

## 7. Proposed Technical Approach

The following technology choices represent the initial technical direction and are subject to validation during architecture design.

| Component | Proposed Technology |
|---|---|
| Programming Language | Java 25 LTS |
| Backend Framework | Spring Boot |
| Database | PostgreSQL |
| Database Migration | Flyway |
| Backend Build Tool | Maven |
| Frontend | React, subject to final confirmation |
| API Style | REST over HTTP |
| API Documentation | OpenAPI |
| Automated Testing | JUnit, Mockito, and integration testing tools |
| Containerisation | Docker and Docker Compose |
| Version Control | Git and GitHub |
| Continuous Integration | GitHub Actions |
| Deployment | Free-tier or no-cost infrastructure, subject to provider availability |

### 7.1 Architectural Direction

The initial implementation will favour a modular monolithic backend.

This approach is intended to:

- Minimise operational complexity.
- Keep development and deployment manageable for a solo developer.
- Support clear separation of responsibilities.
- Facilitate automated testing and debugging.
- Allow selected components to evolve as requirements become clearer.

Distributed infrastructure, message brokers, and microservices will not be introduced without a demonstrated technical need.

### 7.2 Technical Decision Management

Significant technical decisions will be documented using Architecture Decision Records (ADRs).

Each relevant ADR should capture:

- The problem or context.
- The decision made.
- Alternatives considered.
- The rationale for the decision.
- Expected consequences and trade-offs.

Detailed component design, database schema, API contracts, and implementation algorithms are outside the scope of this charter.

## 8. Project Constraints

### 8.1 Financial Constraints

The project targets zero recurring infrastructure expenditure during development and initial demonstration.

Preference will be given to:

- Open-source software.
- Free development tools.
- Free hosting tiers.
- Free managed database tiers where appropriate.
- Existing local development hardware.

No paid cloud subscription or infrastructure commitment is authorised by this charter.

Free-tier quotas, availability, service suspension policies, and billing requirements must be reviewed before deployment.

### 8.2 Resource Constraints

The project will initially be developed and maintained by a single developer.

The primary development environment consists of:

- Windows 11.
- Lenovo ThinkPad T14s Gen 3.
- Intel Core i5-1240P processor.
- 16 GB RAM.
- 512 GB SSD.
- Docker Desktop with WSL 2.

Infrastructure and tooling choices should remain compatible with these resource limitations.

### 8.3 Schedule Constraints

Development will follow an incremental, milestone-based approach.

No fixed public release date has been committed at project initiation.

Milestone estimates will be established and refined as requirements, implementation complexity, and available development time become clearer.

### 8.4 Operational Constraints

The project must account for limitations commonly associated with no-cost infrastructure, including:

- Resource quotas.
- Cold starts and service inactivity policies.
- Limited storage and compute capacity.
- Provider availability and policy changes.
- Database persistence and backup limitations.
- Potential service suspension or resource reclamation.

Continuous 24/7 availability cannot be guaranteed solely through the selection of free-tier infrastructure.

## 9. Assumptions and Dependencies

### 9.1 Initial Assumptions

- The MVP will initially serve a small and unpredictable volume of traffic.
- Users will not be required to authenticate to create shortened URLs.
- A single backend application and relational database will be sufficient for initial development.
- A minimal frontend will be sufficient for demonstrating core functionality.
- No production-scale traffic commitment is required for the MVP.
- Advanced analytics and distributed processing can be deferred.
- Public deployment is contingent on the availability of suitable no-cost infrastructure.

These assumptions must be reviewed if new evidence or requirements emerge.

### 9.2 Key Dependencies

The project depends on:

- A functioning Java development environment.
- A supported Spring Boot and Java version combination.
- GitHub for source control and collaboration workflows.
- PostgreSQL for persistent data storage.
- Docker for reproducible local development.
- A suitable public hosting environment.
- HTTPS and a usable public application URL.
- Compliance with third-party provider terms and acceptable-use policies.

## 10. High-Level Delivery Milestones

| Milestone | Description | Primary Deliverables |
|---|---|---|
| M0 — Initiation | Establish project purpose and scope | Project Charter, Scope Statement, Roadmap, Risk Register |
| M1 — Requirements | Define expected behaviour and quality requirements | Requirements Specification, Use Cases, Acceptance Criteria |
| M2 — System Design | Design the domain, database, API, and architecture | ERD, Data Dictionary, OpenAPI Specification, Architecture Diagrams |
| M3 — Backend MVP | Implement core URL shortening and redirection | Working Backend, Database Migrations, Automated Tests |
| M4 — Web MVP | Provide a usable public interface | Minimal Frontend, End-to-End Workflow |
| M5 — Quality & Security | Validate reliability, correctness, and abuse controls | Test Results, Security Review, CI Pipeline |
| M6 — Deployment | Publish and validate the MVP | Public HTTPS Demo, Deployment Documentation |
| M7 — Portfolio Release | Finalise the initial demonstrable release | Release Notes, Updated README, Screenshots, Technical Summary |
| M8 — Enhancements | Evaluate and deliver selected future capabilities | Feature-specific Requirements, Designs, Implementations, and Tests |

Detailed milestones, dependencies, and completion criteria will be maintained in the project roadmap.

## 11. Success Criteria

The MVP will be considered ready for its initial portfolio release when the following conditions are satisfied.

### 11.1 Functional Success

- Users can submit a valid URL and receive a shortened link.
- Generated short identifiers are protected against duplicate assignment.
- Valid, active short links redirect to the correct destinations.
- Invalid submissions are rejected with meaningful responses.
- Nonexistent and expired links are handled according to documented requirements.
- URL mappings persist across ordinary application restarts.
- A minimal web interface supports the core user workflow.

### 11.2 Engineering Quality

- Critical business logic is covered by automated tests.
- Core API workflows are verified through integration testing.
- A continuous integration pipeline executes the required automated checks.
- Database schema changes are managed through versioned migrations.
- Application configuration is separated from source code.
- Sensitive credentials are not committed to the repository.
- Major architecture decisions are documented.
- The application can be built and run using documented instructions.

### 11.3 Security and Operational Readiness

- Public input validation and baseline abuse prevention are implemented.
- Public-facing errors do not disclose sensitive internal information.
- The deployment uses HTTPS.
- Application health can be checked.
- Relevant operational failures can be diagnosed through logging.
- Deployment limitations and recovery procedures are documented.
- No unexpected recurring infrastructure charges are required.

### 11.4 Portfolio Readiness

- The GitHub repository includes a clear and accurate README.
- The application has a publicly accessible demonstration at release time.
- Architecture and technical decisions can be explained and reviewed.
- Automated test and CI results are available.
- The repository contains sufficient instructions to reproduce the application locally.
- Claims about performance, scalability, and availability are supported by evidence rather than assumptions.

### 11.5 Measurable Quality Targets

Specific performance thresholds, load profiles, test coverage expectations, and availability targets will be proposed during Requirements Engineering.

They will be validated against the available development and deployment environments before being treated as release criteria.

## 12. Initial Risk Assessment

| ID | Risk | Potential Impact | Initial Response |
|---|---|---|---|
| R-01 | Free hosting becomes unavailable or unsuitable | Public demo interruption | Evaluate alternative providers and maintain reproducible deployment |
| R-02 | Public endpoints are abused for spam or phishing | Security incidents, reputation damage, provider suspension | Apply validation, request controls, monitoring, and an abuse response process |
| R-03 | Short-code generation produces collisions | Failed requests or incorrect URL mapping | Enforce database uniqueness and implement collision handling |
| R-04 | Database or application failures disrupt redirects | Unavailable shortened links | Implement error handling, health checks, persistence safeguards, and recovery procedures |
| R-05 | Scope expands before MVP completion | Delivery delays and increased complexity | Maintain explicit MVP boundaries and a separate enhancement backlog |
| R-06 | Insufficient automated testing | Regressions and unreliable releases | Test critical workflows and run checks through CI |
| R-07 | Analytics features introduce privacy concerns | Unnecessary personal-data collection and compliance risk | Defer analytics until privacy requirements and retention policies are defined |
| R-08 | Solo development creates a maintenance bottleneck | Slower progress and unresolved operational issues | Keep architecture manageable and document essential procedures |

Risk likelihood, impact, ownership, and mitigation plans will be maintained in a separate Risk Register.

## 13. Project Governance and Development Practices

### 13.1 Development Responsibilities

The Project Owner is responsible for:

- Requirements analysis.
- Project planning and prioritisation.
- Architecture and database design.
- Application implementation.
- Testing and quality assurance.
- Deployment and maintenance.
- Technical documentation.
- Release decisions.

### 13.2 Version Control

The repository will use Git and GitHub.

The initial branching strategy is:

- `main` — Stable release branch.
- `develop` — Integration branch.
- `feature/*` — Feature development.
- `fix/*` — Bug fixes.
- `docs/*` — Documentation changes.
- `chore/*` — Maintenance and configuration work.

Changes will normally be integrated through Pull Requests.

### 13.3 Documentation

Documentation will be maintained alongside the source code and updated as relevant decisions or implementations change.

Key documentation categories include:

- Project initiation.
- Requirements engineering.
- Domain and database design.
- API specifications.
- Architecture and decision records.
- Testing and quality assurance.
- Security.
- Deployment and operations.

### 13.4 Quality Assurance

Quality assurance activities will include, where applicable:

- Code review and self-review.
- Automated unit and integration tests.
- API contract verification.
- Static analysis and formatting checks.
- Security review.
- CI validation.
- Manual end-to-end verification.
- Performance testing against documented workloads.

### 13.5 Change Management

Changes affecting approved scope, architecture, security, deployment cost, or delivery priorities should be recorded and reviewed.

Minor implementation refinements may be handled through normal development tasks.

Significant changes should result in updates to the relevant requirements, design documents, ADRs, or roadmap.

## 14. Future Development Opportunities

After completing the MVP, potential enhancements may include:

### 14.1 QR Code Generation

Generate downloadable QR codes associated with shortened URLs.

### 14.2 Privacy-Conscious Analytics

Provide aggregated statistics about link usage while minimising unnecessary collection and retention of personal information.

### 14.3 Geographic Insights

Estimate broad geographic traffic distribution using an appropriately evaluated IP geolocation approach.

### 14.4 Device and Browser Analytics

Provide aggregated browser, operating system, and device statistics using user-agent information, subject to accuracy and privacy limitations.

### 14.5 Advanced Abuse Prevention

Introduce stronger request controls, suspicious destination detection, and operational abuse monitoring.

### 14.6 Account-Based Link Management

Allow registered users to manage links, review analytics, and configure advanced settings.

### 14.7 Scalability Improvements

Evaluate caching, asynchronous analytics processing, database optimisation, and horizontal scaling where justified by performance evidence.

These capabilities are candidates for future development and are not committed MVP deliverables.

## 15. Project Completion and Release Principles

The project will follow an iterative delivery model.

The initial MVP will be prioritised over advanced functionality.

A milestone will be considered complete only when its documented completion criteria have been satisfied.

The first portfolio release must represent a working, tested, and documented application rather than an incomplete collection of planned features.

Subsequent enhancements will be prioritised based on their user value, engineering learning outcomes, operational cost, and maintenance implications.

## 16. Charter Review and Approval

| Field | Value |
|---|---|
| Document Owner | Project Owner |
| Review Status | Pending Review |
| Approval Authority | Project Owner |
| Approval Date | To be determined |
| Next Review | Following completion of the initial Scope Statement |

This charter becomes the approved project baseline when reviewed and accepted by the Project Owner.

Future material changes will be reflected through document version updates and the project's change management process.

---

## Document History

| Version | Date | Description | Status |
|---|---|---|---|
| 1.0.0 | 2026-11-08 | Initial Project Charter | Approved - Initial Baseline |
| 0.1.0 | 2026-11-02 | Initial Project Charter draft | Pending Review |
