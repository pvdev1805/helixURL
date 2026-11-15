# HelixURL — Requirements Traceability Matrix

**Document ID:** HLX-RTM-001  
**Version:** 0.1.0  
**Status:** Draft — Pending Review  
**Project:** HelixURL  
**Target Release:** MVP v0.1.0  
**Document Owner:** Project Owner  
**Last Updated:** 2026-11-12

**Related Documents:**
- [Software Requirements Specification](./requirements.md)
- [Business Rules Specification](./business-rules.md)
- [Use Case Specification](./use-cases.md)
- [Acceptance Criteria Specification](./acceptance-criteria.md)
- [Project Scope Statement](../00-initiation/scope.md)
- [Development Roadmap](../00-initiation/roadmap.md)
- [Risk Register](../00-initiation/risk-register.md)

---

## 1. Introduction

### 1.1 Purpose

This document establishes the Requirements Traceability Matrix (RTM) for the HelixURL Minimum Viable Product (MVP).

The RTM connects approved and proposed software requirements with their associated business rules, use cases, acceptance criteria, and future verification evidence.

Its objectives are to:

- Maintain forward and backward requirements traceability.
- Identify requirements without adequate verification coverage.
- Support change impact analysis.
- Prevent undocumented scope expansion.
- Connect requirements to design and implementation activities.
- Provide a structured basis for release verification.
- Support requirements baseline review and approval.

The RTM is a living document and must be maintained throughout the development lifecycle.

### 1.2 Scope

The RTM covers the following requirement categories defined in the Software Requirements Specification:

| Category | Identifier Range | Count |
|---|---|---:|
| Functional Requirements | FR-001–FR-041 | 41 |
| Non-Functional Requirements | NFR-001–NFR-021 | 21 |
| Interface Requirements | IR-001–IR-010 | 10 |
| Data Requirements | DR-001–DR-010 | 10 |
| Security Requirements | SEC-001–SEC-011 | 11 |
| **Total** | | **93** |

All 93 requirement identifiers are included in this initial matrix.

### 1.3 Traceability Relationships

The following relationships are maintained:

**Requirement → Business Rule**

Identifies business policies that constrain or clarify the requirement.

**Requirement → Use Case**

Identifies actor interactions and behavioural scenarios associated with the requirement.

**Requirement → Acceptance Criterion**

Identifies observable conditions used to verify compliance.

**Requirement → Design**

Identifies the architectural or detailed design artefacts that implement the requirement.

**Requirement → Test**

Identifies the automated or manual verification procedures used to establish compliance.

**Requirement → Evidence**

Identifies objective results demonstrating that the requirement has been satisfied.

Not every requirement requires every relationship type. For example, an infrastructure requirement may be verified through deployment inspection rather than a user-facing use case.

### 1.4 Traceability Notation

| Notation | Meaning |
|---|---|
| `FR-XXX` | Functional Requirement |
| `NFR-XXX` | Non-Functional Requirement |
| `IR-XXX` | Interface Requirement |
| `DR-XXX` | Data Requirement |
| `SEC-XXX` | Security Requirement |
| `BR-XXX` | Business Rule |
| `UC-XXX` | Use Case |
| `AC-XXX` | Acceptance Criterion |
| `TBD` | To Be Determined |
| `—` | No direct relationship currently identified |

A dash does not automatically indicate a coverage defect. However, missing verification coverage must be investigated.

### 1.5 Coverage Classification

The RTM distinguishes between the existence of a traceability reference and the adequacy of verification.

| Classification | Meaning |
|---|---|
| Mapped | At least one related acceptance criterion has been identified |
| Partial | Related criteria exist but do not fully verify the requirement |
| Gap | No sufficiently relevant acceptance criterion has been identified |
| Pending Decision | Verification depends on an unresolved policy or specification |
| Verified | Implementation has passed the required verification activities |

These classifications are not mutually exclusive in every context. A requirement may be mapped but still depend on an unresolved policy.

No implementation requirement is classified as Verified in this initial RTM.

---

## 2. Functional Requirements Traceability

### 2.1 URL Creation

| Requirement | Business Rules | Use Cases | Acceptance Criteria |
|---|---|---|---|
| FR-001 | BR-010 | UC-001 | AC-001, AC-002 |
| FR-002 | BR-017 | UC-001 | AC-001 |
| FR-003 | BR-011, BR-017 | UC-001 | AC-001, AC-048 |
| FR-004 | BR-012, BR-013 | UC-001 | AC-003 |
| FR-005 | BR-011, BR-050 | UC-001 | AC-004, AC-042 |

### 2.2 Destination Validation

| Requirement | Business Rules | Use Cases | Acceptance Criteria |
|---|---|---|---|
| FR-006 | BR-001 | UC-004 | AC-006 |
| FR-007 | BR-002, BR-005 | UC-004 | AC-007, AC-011 |
| FR-008 | BR-003 | UC-001, UC-004 | AC-008, AC-009 |
| FR-009 | BR-004 | UC-004 | AC-010 |
| FR-010 | BR-008 | UC-004 | AC-013 |
| FR-011 | BR-048 | UC-004 | AC-006–AC-013, AC-049 |

### 2.3 Short-Code Generation

| Requirement | Business Rules | Use Cases | Acceptance Criteria |
|---|---|---|---|
| FR-012 | BR-014, BR-016 | UC-001 | AC-015 |
| FR-013 | BR-014 | UC-001 | AC-015, AC-017 |
| FR-014 | BR-015 | UC-001 | AC-016, AC-017 |
| FR-015 | BR-016, BR-027 | UC-002 | AC-025, AC-026 |

### 2.4 Redirection

| Requirement | Business Rules | Use Cases | Acceptance Criteria |
|---|---|---|---|
| FR-016 | BR-028, BR-030 | UC-002 | AC-025 |
| FR-017 | BR-006, BR-029 | UC-002 | AC-012, AC-025 |
| FR-018 | BR-027, BR-031 | UC-005 | AC-026, AC-029 |
| FR-019 | BR-025, BR-031 | UC-005 | AC-023, AC-027 |
| FR-020 | BR-028, BR-036 | UC-005, UC-006 | AC-028, AC-031 |
| FR-021 | BR-030 | UC-002 | AC-025, AC-030 |

### 2.5 Expiration

| Requirement | Business Rules | Use Cases | Acceptance Criteria |
|---|---|---|---|
| FR-022 | BR-019 | UC-003 | AC-018, AC-046 |
| FR-023 | BR-020, BR-047 | UC-001, UC-003 | AC-005 |
| FR-024 | BR-021–BR-023 | UC-003 | AC-019–AC-021 |
| FR-025 | BR-024–BR-026 | UC-002, UC-005 | AC-022–AC-024 |
| FR-026 | BR-024 | UC-002, UC-005 | AC-023 |

### 2.6 Web Interface

| Requirement | Business Rules | Use Cases | Acceptance Criteria |
|---|---|---|---|
| FR-027 | BR-010 | UC-001 | AC-045 |
| FR-028 | BR-019–BR-023 | UC-003 | AC-046 |
| FR-029 | — | UC-001 | AC-047 |
| FR-030 | BR-017 | UC-001 | AC-045, AC-048 |
| FR-031 | — | UC-001 | AC-048 |
| FR-032 | BR-048, BR-051 | UC-004 | AC-049 |
| FR-033 | BR-010 | UC-001 | AC-002, AC-045 |

### 2.7 Error Handling

| Requirement | Business Rules | Use Cases | Acceptance Criteria |
|---|---|---|---|
| FR-034 | BR-031, BR-051 | UC-004, UC-005 | AC-029, AC-049, AC-060 |
| FR-035 | BR-048 | UC-004 | AC-006–AC-013 |
| FR-036 | BR-038, BR-039 | UC-001 | AC-035 |
| FR-037 | BR-049 | UC-001, UC-002 | AC-037 |
| FR-038 | BR-050 | UC-001, UC-002 | AC-042, AC-052 |

### 2.8 Operational Controls

| Requirement | Business Rules | Use Cases | Acceptance Criteria |
|---|---|---|---|
| FR-039 | BR-033–BR-037 | UC-006 | AC-031–AC-034 |
| FR-040 | — | UC-007 | AC-051, AC-052 |
| FR-041 | BR-045 | UC-006, UC-007 | AC-053 |

---

## 3. Non-Functional Requirements Traceability

### 3.1 Reliability

| Requirement | Related Rules | Acceptance Criteria | Notes |
|---|---|---|---|
| NFR-001 | BR-014, BR-029 | AC-015, AC-025, AC-043 | Mapping correctness |
| NFR-002 | BR-048, BR-050 | AC-042, AC-043 | Failure isolation |
| NFR-003 | BR-011, BR-043 | AC-041 | Restart persistence |

### 3.2 Performance

| Requirement | Related Rules | Acceptance Criteria | Notes |
|---|---|---|---|
| NFR-004 | — | AC-059 | Pending numerical target |
| NFR-005 | — | AC-059 | Pending numerical target |
| NFR-006 | — | AC-059 | Measurement evidence required |

### 3.3 Maintainability

| Requirement | Related Rules | Acceptance Criteria | Notes |
|---|---|---|---|
| NFR-007 | — | AC-061 | Modular structure inspection required |
| NFR-008 | — | AC-038, AC-057, AC-069 | Partial: configuration separation |
| NFR-009 | — | AC-062 | Documentation change review required |

### 3.4 Testability

| Requirement | Related Rules | Acceptance Criteria | Notes |
|---|---|---|---|
| NFR-010 | — | AC-055 | Automated core verification |
| NFR-011 | BR-014, BR-015 | AC-017, AC-044, AC-055 | Database integration |
| NFR-012 | — | AC-056 | CI verification |

### 3.5 Usability and Accessibility

| Requirement | Related Rules | Acceptance Criteria | Notes |
|---|---|---|---|
| NFR-013 | — | AC-045, AC-049, AC-068 | Partial: usability evaluation needed |
| NFR-014 | — | AC-050 | Keyboard operability |
| NFR-015 | — | AC-050 | Responsive layout |

### 3.6 Deployability

| Requirement | Related Rules | Acceptance Criteria | Notes |
|---|---|---|---|
| NFR-016 | — | AC-057 | Local setup |
| NFR-017 | BR-046 | AC-054, AC-058 | Deployment documentation |
| NFR-018 | — | AC-058 | Cost constraint |

### 3.7 Observability and Availability

| Requirement | Related Rules | Acceptance Criteria | Notes |
|---|---|---|---|
| NFR-019 | — | AC-051, AC-052 | Health visibility |
| NFR-020 | BR-045 | AC-037, AC-053 | Failure diagnostics |
| NFR-021 | BR-047 | AC-058 | Availability transparency |

---

## 4. Interface Requirements Traceability

| Requirement | Related Rules | Acceptance Criteria | Notes |
|---|---|---|---|
| IR-001 | BR-017 | AC-040, AC-060 | HTTP/HTTPS transport |
| IR-002 | BR-051 | AC-060 | JSON contract proposal |
| IR-003 | — | AC-060 | OpenAPI documentation |
| IR-004 | BR-030, BR-031, BR-051 | AC-025–AC-030, AC-060 | HTTP semantics |
| IR-005 | — | AC-045, AC-066 | Partial: browser integration/CORS verification |
| IR-006 | — | AC-045, AC-050, AC-067 | Partial: browser compatibility |
| IR-007 | — | AC-050 | Responsive presentation |
| IR-008 | — | AC-050 | Accessible controls |
| IR-009 | BR-043 | AC-041, AC-044 | PostgreSQL persistence |
| IR-010 | — | AC-044 | Version-controlled migrations |

---

## 5. Data Requirements Traceability

| Requirement | Related Rules | Acceptance Criteria | Notes |
|---|---|---|---|
| DR-001 | BR-006, BR-013, BR-043 | AC-003, AC-012 | Destination storage |
| DR-002 | BR-014, BR-043 | AC-015 | Short-code storage |
| DR-003 | BR-043 | AC-063 | Creation timestamp verification needed |
| DR-004 | BR-019, BR-020, BR-026 | AC-005, AC-018, AC-024 | Optional expiration |
| DR-005 | BR-035, BR-036, BR-043 | AC-028, AC-031 | Disabled state |
| DR-006 | BR-014, BR-015 | AC-015–AC-017, AC-044 | Unique constraint |
| DR-007 | BR-011 | AC-004, AC-041 | Restart persistence |
| DR-008 | BR-023, BR-024 | AC-018–AC-023, AC-064 | Partial: UTC/time representation |
| DR-009 | BR-044, BR-045 | AC-053, AC-065 | Partial: data minimisation |
| DR-010 | BR-046 | AC-054 | Retention policy |

---

## 6. Security Requirements Traceability

| Requirement | Related Rules | Acceptance Criteria | Notes |
|---|---|---|---|
| SEC-001 | BR-038–BR-042 | AC-035, AC-036, AC-039 | Baseline abuse controls |
| SEC-002 | BR-038, BR-039 | AC-035 | Rate limiting |
| SEC-003 | BR-003 | AC-008, AC-009 | Scheme restrictions |
| SEC-004 | BR-008, BR-040 | AC-013, AC-039 | Destination safety policy |
| SEC-005 | — | AC-038 | Secrets management |
| SEC-006 | BR-049 | AC-037 | Sensitive error protection |
| SEC-007 | BR-035, BR-037 | AC-032, AC-033 | Operator access restriction |
| SEC-008 | BR-017 | AC-040 | HTTPS |
| SEC-009 | BR-040, BR-041 | AC-031, AC-039 | Abuse response |
| SEC-010 | BR-045 | AC-053 | Sensitive logging |
| SEC-011 | BR-009 | AC-014 | No destination fetching |

---

## 7. Use Case to Acceptance Criteria Matrix

| Use Case | Related Acceptance Criteria |
|---|---|
| UC-001 — Create Short URL | AC-001–AC-005, AC-015–AC-017, AC-035, AC-045, AC-047, AC-048 |
| UC-002 — Redirect Using Short URL | AC-012, AC-022, AC-023, AC-025, AC-030 |
| UC-003 — Create Expiring Short URL | AC-005, AC-018–AC-021, AC-046 |
| UC-004 — Handle Invalid URL Submission | AC-006–AC-011, AC-013, AC-036, AC-049 |
| UC-005 — Handle Unknown or Unavailable Link | AC-023, AC-024, AC-026–AC-029 |
| UC-006 — Disable Abusive Link | AC-028, AC-031–AC-034, AC-039 |
| UC-007 — Check Application Health | AC-051, AC-052 |

This matrix identifies primary behavioural relationships. Cross-cutting security, reliability, and operational acceptance criteria may apply to multiple use cases.

## 8. Acceptance Criteria Coverage Audit

The Acceptance Criteria Specification contains 69 acceptance criteria, AC-001 through AC-069.

The original 60 criteria are retained; AC-061–AC-069 address nine identified specification-level coverage gaps.

### 8.1 Acceptance Criteria Without Direct User-Facing Use Cases

Some acceptance criteria verify system-wide properties rather than individual user interactions.

Examples include:

- AC-038 — Secrets handling.
- AC-040 — HTTPS.
- AC-041 — Restart persistence.
- AC-044 — Database migration.
- AC-054 — Retention and recovery documentation.
- AC-055 — Automated tests.
- AC-056 — Continuous integration.
- AC-057 — Local setup.
- AC-058 — Deployment readiness.
- AC-059 — Performance verification.
- AC-060 — API documentation.

These criteria are valid even without a dedicated use case.

### 8.2 Identified Coverage Gaps

The initial RTM identified nine gaps. AC-061–AC-069 address them at the specification level; implementation and verification remain pending.

| Gap ID | Requirement | Resolution | Status |
|---|---|---|---|
| GAP-001 | NFR-007 | AC-061 | Addressed in specification |
| GAP-002 | NFR-009 | AC-062 | Addressed in specification |
| GAP-003 | DR-003 | AC-063 | Addressed in specification |
| GAP-004 | DR-008 | AC-064 | Addressed in specification |
| GAP-005 | DR-009 | AC-065 | Addressed in specification |
| GAP-006 | IR-005 | AC-066 | Addressed in specification |
| GAP-007 | IR-006 | AC-067 | Addressed in specification |
| GAP-008 | NFR-013 | AC-068 | Addressed in specification |
| GAP-009 | NFR-008 | AC-069 | Addressed in specification |

A mapped criterion is not evidence of a passed test. The browser matrix, performance targets, security policies and other deferred details remain tracked in the decision log.

### 8.3 Policy-Dependent Coverage

The following acceptance areas include approved decisions and unresolved business or technical details:

| Area | Related Criteria | Dependency |
|---|---|---|
| URL length | AC-010 | BD-001 approved; counting convention deferred to API design |
| Destination restrictions | AC-013 | BD-008 |
| Expiration validation | AC-019–AC-021 | BD-002 approved; BD-003 proposed |
| Redirect behaviour | AC-023, AC-025–AC-030 | BD-005 and BD-006 approved; BD-012 proposed |
| Rate limiting | AC-035 | BD-007 |
| Request payload limits | AC-036 | BD-010 |
| Operator disabling | AC-031–AC-034 | Operational design |
| Browser compatibility | AC-050 | Browser support policy |
| Retention | AC-054 | BD-009 |
| Performance | AC-059 | Approved targets and workloads |

A requirement may have a mapped acceptance criterion while remaining insufficiently specified for final verification.

---

## 9. Coverage Assessment

### 9.1 Requirement Inventory

| Category | Total Requirements | With AC Reference | Without AC Reference |
|---|---:|---:|---:|
| Functional | 41 | 41 | 0 |
| Non-Functional | 21 | 21 | 0 |
| Interface | 10 | 10 | 0 |
| Data | 10 | 10 | 0 |
| Security | 11 | 11 | 0 |
| **Total** | **93** | **93** | **0** |

### 9.2 Reference Coverage

Reference coverage is defined as:

`Requirements with at least one AC reference / Total requirements × 100%`

After AC-061–AC-069 are added:

`93 / 93 × 100% = 100%`

This figure measures the presence of references only.

It does not measure:

- Adequacy of the acceptance criteria.
- Completeness of business rules.
- Implementation progress.
- Automated test coverage.
- Successful test execution.
- Production readiness.

Nine previously identified specification gaps now have mapped acceptance criteria. Their implementation and verification remain pending.

### 9.3 Verification Status

| Metric | Current Status |
|---|---|
| Requirements inventoried | 93 |
| Acceptance criteria defined | 69 |
| Requirements with AC references | 93 |
| Requirements without AC references | 0 |
| Specification-level gaps addressed | 9 |
| Implemented requirements | Not yet assessed |
| Executed acceptance tests | 0 |
| Verified requirements | 0 |

All verification results remain pending until implementation and testing produce evidence.

---

## 10. Future Design Traceability

Detailed design references will be added during M2 — System Design.

The following mappings are planned.

| Requirement Area | Planned Design Artefacts |
|---|---|
| URL creation | Component design, API contract, sequence diagram |
| URL validation | Validation policy, request model, domain rules |
| Short-code generation | Generation strategy, collision handling design |
| URL redirection | Redirect sequence, HTTP contract |
| Expiration | Domain model, lifecycle rules, database design |
| Persistence | ERD, data dictionary, schema migrations |
| Link disabling | Operational design, access controls |
| Abuse prevention | Security design, rate-limiting architecture |
| Web interface | UI flow, frontend architecture |
| Health and diagnostics | Operational architecture, monitoring design |

The RTM will reference actual design document sections or identifiers once those artefacts exist.

No design component should be represented as implemented merely because it appears in this planning table.

## 11. Future Implementation Traceability

During M3 and M4, requirements will be linked to implementation components.

Potential implementation references include:

- Backend packages and modules.
- REST controllers.
- Application services.
- Domain objects.
- Persistence repositories.
- Database migrations.
- Frontend pages and components.
- Configuration modules.
- Operational scripts.

Exact filenames and class names will be recorded after implementation decisions are made.

Implementation references must be updated when code is moved, renamed, or removed.

## 12. Future Test Traceability

During M3–M5, acceptance criteria will be connected to executable tests.

The following naming convention is proposed:

- `UT-XXX` — Unit Test.
- `IT-XXX` — Integration Test.
- `API-T-XXX` — API Test.
- `E2E-XXX` — End-to-End Test.
- `SEC-T-XXX` — Security Test.
- `PERF-XXX` — Performance Test.
- `OPS-T-XXX` — Operational Test.

Test identifiers are logical references and do not prescribe test class or method names.

A future verification record may contain:

| Field | Example |
|---|---|
| Requirement ID | FR-019 |
| Business Rule | BR-025 |
| Use Case | UC-005 |
| Acceptance Criteria | AC-023, AC-027 |
| Design Reference | TBD |
| Implementation Reference | TBD |
| Test Reference | TBD |
| Test Status | Not Run |
| Evidence | Pending |
| Defect Reference | None |

Test references should be linked to repository paths, CI reports, or other reviewable evidence where appropriate.

---

## 13. Change Impact Analysis

The RTM must be used when a material requirement or business rule changes.

### 13.1 Example — Changing Maximum URL Length

Suppose the approved maximum destination URL length changes from 2,048 to 4,096 characters.

Potentially affected artefacts include:

| Artefact | Impact |
|---|---|
| FR-009 | Requirement threshold |
| BR-004 | Business policy |
| UC-004 | Invalid submission scenarios |
| AC-010 | Boundary test inputs |
| API specification | Input validation constraints |
| Backend validation | Maximum length enforcement |
| Database design | Storage capacity considerations |
| Frontend | Input validation and error feedback |
| Automated tests | Updated boundary expectations |

The change should not be considered complete until affected documentation and tests have been reviewed.

### 13.2 Example — Changing Expiration Behaviour

If the maximum expiration duration changes, the following must be reviewed:

- FR-024.
- BR-022.
- UC-003.
- AC-021.
- API contract.
- Expiration validation logic.
- Automated boundary tests.
- Relevant user interface controls.

### 13.3 Example — Changing Redirect Semantics

If the redirect status changes from HTTP 302 to another status:

- FR-021 must be reviewed.
- BR-030 must be updated.
- UC-002 must remain behaviourally consistent.
- AC-025 and AC-030 must be reviewed.
- API documentation must be updated.
- Redirect caching implications must be assessed.
- Existing automated tests must be updated.

### 13.4 Change Review Procedure

For each material change:

1. Identify the originating requirement or rule.
2. Locate related entries in the RTM.
3. Determine affected use cases and acceptance criteria.
4. Review affected design and implementation artefacts.
5. Update relevant documentation.
6. Modify or add verification procedures.
7. Record the change in version control.
8. Re-run applicable verification activities.
9. Update traceability and verification evidence.

---

## 14. Scope Control

Every implementation feature should have a documented justification.

A proposed feature may be classified as:

- Required for an approved MVP requirement.
- Necessary to satisfy a security or operational constraint.
- An approved technical dependency.
- A separately approved scope change.
- A post-MVP enhancement.

Features without an approved justification should not automatically be added to the MVP.

Examples of post-MVP features include:

- User accounts.
- Custom aliases.
- QR code generation.
- Analytics dashboards.
- Public link management.
- Custom domains.
- Advanced administration.

Technical design decisions may introduce internal components without creating new user-facing features. Such components must still be justified by the relevant requirements and architectural constraints.

## 15. RTM Maintenance Responsibilities

The Project Owner is responsible for maintaining this document.

The RTM should be reviewed:

- When requirements change.
- When business rules are modified.
- When use cases or acceptance criteria are added.
- When architectural decisions are approved.
- When implementation components are introduced or removed.
- When automated tests are added or changed.
- Before major milestone completion.
- Before public release.

The RTM should remain consistent with the current approved requirements baseline.

## 16. M1 Baseline Readiness Criteria

The Requirements Engineering milestone is ready for final approval when:

- All mandatory MVP requirements are identified.
- Business rules are documented and reviewed.
- Use cases cover the core actor goals.
- Acceptance criteria are testable.
- Requirement identifiers are unique and stable.
- Forward traceability is established.
- Backward traceability has been reviewed.
- Missing acceptance coverage is resolved or explicitly accepted with justification.
- Important business decisions are confirmed.
- Deferred technical decisions have owners and target milestones.
- No unresolved contradiction exists among the five M1 documents.
- The Project Owner approves the requirements baseline.

A complete traceability matrix does not require implementation or test execution to have started.

## 17. Review and Approval

| Field | Value |
|---|---|
| Document Owner | Project Owner |
| Review Status | Pending Review |
| Approval Authority | Project Owner |
| Approval Date | To be determined |
| Baseline Status | Not Yet Approved |

This RTM will be reviewed together with the other M1 documents before the requirements baseline is approved.

---

## Document History

| Version | Date | Description | Status |
|---|---|---|---|
| 0.1.0 | 2026-11-12 | Initial Requirements Traceability Matrix | Pending Review |
