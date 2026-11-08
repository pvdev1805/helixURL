# HelixURL — Project Risk Register

**Document ID:** HLX-RSK-001  
**Version:** 1.0.0
**Status:** Approved — Initial Baseline
**Project:** HelixURL  
**Release Target:** MVP v0.1.0  
**Document Owner:** Project Owner  
**Last Updated:** 2026-11-06

**Related Documents:**
- [Project Charter](./project-charter.md)
- [Project Scope Statement](./scope.md)
- [Development Roadmap](./roadmap.md)

---

## 1. Purpose

This document identifies, assesses, prioritises, and tracks risks that could affect the successful delivery and operation of HelixURL.

The Risk Register provides a structured approach to:

- Identify potential threats to project objectives.
- Evaluate the likelihood and impact of identified risks.
- Prioritise risk management activities.
- Define preventive and mitigating controls.
- Establish contingency actions.
- Assign risk ownership.
- Monitor changes in risk exposure.
- Support informed engineering and release decisions.

Risk management is an ongoing activity throughout the project lifecycle.

This document establishes an initial assessment and will be updated as requirements, design decisions, implementation results, and operational conditions evolve.

## 2. Risk Management Approach

### 2.1 Risk Identification

Risks may be identified through:

- Requirements analysis.
- Architecture and database design.
- Security threat modelling.
- Code review.
- Automated and manual testing.
- Performance evaluation.
- Infrastructure assessment.
- Deployment verification.
- Operational observations.
- Changes in project scope or external dependencies.

### 2.2 Risk Categories

| Category | Description |
|---|---|
| Security | Risks involving malicious use, vulnerabilities, unauthorised access, or unsafe redirects |
| Technical | Risks affecting correctness, architecture, performance, or maintainability |
| Data | Risks affecting persistence, integrity, retention, or recovery |
| Infrastructure | Risks involving hosting, availability, deployment, or external services |
| Delivery | Risks affecting scope, effort, schedule, or project completion |
| Quality | Risks involving testing, defects, or insufficient verification |
| Privacy | Risks involving collection, exposure, or retention of personal information |
| Operational | Risks involving monitoring, incident response, or ongoing maintenance |

### 2.3 Assessment Method

Each risk is assessed using two factors:

**Likelihood (L)** — The estimated probability that the risk event will occur.

**Impact (I)** — The estimated severity of its consequences if it occurs.

The initial risk score is calculated as:

`Risk Score = Likelihood × Impact`

Scores are used to prioritise risk treatment and are not intended to represent statistically measured probabilities.

## 3. Risk Rating Scales

### 3.1 Likelihood Scale

| Score | Rating | Description |
|---|---|---|
| 1 | Rare | Expected only under exceptional circumstances |
| 2 | Unlikely | Not expected under normal conditions, but possible |
| 3 | Possible | Could reasonably occur during the project |
| 4 | Likely | Expected to occur under some foreseeable conditions |
| 5 | Almost Certain | Highly likely to occur without effective intervention |

### 3.2 Impact Scale

| Score | Rating | Description |
|---|---|---|
| 1 | Insignificant | Negligible effect on objectives or operation |
| 2 | Minor | Limited disruption with straightforward recovery |
| 3 | Moderate | Meaningful disruption requiring corrective work |
| 4 | Major | Serious disruption to functionality, delivery, or service operation |
| 5 | Severe | Potential release failure, major security incident, significant data loss, or unacceptable operational consequences |

### 3.3 Risk Priority

| Score | Priority | Required Response |
|---|---|---|
| 1–4 | Low | Accept or monitor with proportionate controls |
| 5–9 | Medium | Plan mitigation and review at relevant milestones |
| 10–16 | High | Prioritise mitigation and verify controls before the affected release gate |
| 17–25 | Critical | Escalate immediately; do not proceed with affected public exposure until the risk is appropriately treated |

### 3.4 Risk Status

| Status | Meaning |
|---|---|
| Identified | Risk has been recorded but treatment is not yet planned in detail |
| Open | Risk is being monitored or managed |
| Mitigating | Planned risk controls are being implemented |
| Monitoring | Controls are implemented and residual exposure is being observed |
| Closed | Risk is no longer applicable or has been formally retired |
| Realised | Risk event has occurred and must be managed as an issue or incident |

A risk should not be marked Closed merely because a mitigation task has been completed.

## 4. Initial Risk Register

The following scores represent preliminary assessments made before detailed requirements, architecture design, and implementation.

| ID | Risk | Category | L | I | Score | Priority | Owner | Status |
|---|---|---|---:|---:|---:|---:|---|---|
| R-01 | Free-tier hosting becomes unavailable or unsuitable | Infrastructure | 4 | 4 | 16 | High | Project Owner | Open |
| R-02 | Public URL shortening is abused for phishing or spam | Security | 4 | 5 | 20 | Critical | Project Owner | Open |
| R-03 | Short-code collision or incorrect mapping | Technical | 3 | 5 | 15 | High | Project Owner | Open |
| R-04 | Database failure or data loss | Data | 3 | 5 | 15 | High | Project Owner | Open |
| R-05 | Uncontrolled MVP scope expansion | Delivery | 4 | 4 | 16 | High | Project Owner | Open |
| R-06 | Insufficient automated testing | Quality | 4 | 4 | 16 | High | Project Owner | Open |
| R-07 | Unnecessary collection or exposure of visitor data | Privacy | 3 | 4 | 12 | High | Project Owner | Open |
| R-08 | Solo development causes delivery or maintenance bottlenecks | Delivery | 4 | 3 | 12 | High | Project Owner | Open |
| R-09 | Unsafe destination handling or SSRF exposure | Security | 3 | 5 | 15 | High | Project Owner | Open |
| R-10 | Public API receives excessive automated traffic | Security | 4 | 4 | 16 | High | Project Owner | Open |
| R-11 | Expiration or redirect behaviour is incorrect | Technical | 3 | 4 | 12 | High | Project Owner | Open |
| R-12 | Production configuration exposes credentials or internal details | Security | 3 | 5 | 15 | High | Project Owner | Open |
| R-13 | Dependency or platform compatibility problems | Technical | 3 | 3 | 9 | Medium | Project Owner | Open |
| R-14 | Inadequate operational monitoring and incident response | Operational | 3 | 4 | 12 | High | Project Owner | Open |
| R-15 | Performance degrades under unexpected traffic | Technical | 3 | 3 | 9 | Medium | Project Owner | Open |

**Assessment note:** The initial ratings are qualitative estimates. They must be reassessed as technical evidence and deployment conditions become available.

## 5. Detailed Risk Assessments

### R-01 — Free-Tier Hosting Becomes Unavailable or Unsuitable

**Category:** Infrastructure  
**Likelihood:** 4 — Likely  
**Impact:** 4 — Major  
**Initial Score:** 16 — High  
**Owner:** Project Owner  
**Status:** Open  
**Relevant Milestones:** M2, M6, M7

**Risk Description**

Free-tier infrastructure may impose resource limits, inactivity suspension, storage restrictions, policy changes, or service termination that prevent reliable public demonstration of HelixURL.

**Potential Causes**

- Free-tier policy changes.
- Compute or database resource exhaustion.
- Application inactivity suspension.
- Service provider outages.
- Unanticipated usage restrictions.

**Potential Consequences**

- Public demo becomes inaccessible.
- Short links temporarily stop working.
- Deployment must be migrated.
- Additional engineering effort is required.
- Portfolio reviewers cannot evaluate the application.

**Mitigation Strategy**

- Evaluate provider restrictions before deployment.
- Keep deployment configuration reproducible.
- Avoid unnecessary provider-specific dependencies.
- Document infrastructure limitations.
- Maintain database migration scripts.
- Assess backup and recovery options.
- Avoid assuming free-tier services provide guaranteed availability.

**Contingency Plan**

- Investigate the provider incident or restriction.
- Restore service on the existing provider where practical.
- Migrate to a suitable alternative if required.
- Restore persistent data from an available verified backup.
- Update public documentation and demonstration links when necessary.

**Verification Evidence**

- Documented hosting assessment.
- Successful deployment rehearsal.
- Recovery procedure.
- Verified persistence and backup approach.

### R-02 — Public URL Shortening Is Abused for Phishing or Spam

**Category:** Security  
**Likelihood:** 4 — Likely  
**Impact:** 5 — Severe  
**Initial Score:** 20 — Critical  
**Owner:** Project Owner  
**Status:** Open  
**Relevant Milestones:** M1, M2, M3, M5, M6

**Risk Description**

Malicious users may exploit anonymous URL creation to distribute phishing links, spam, malware destinations, or other harmful content.

**Potential Causes**

- Anonymous public access.
- Insufficient destination restrictions.
- Lack of request controls.
- No abuse reporting or response procedure.
- Inadequate operational visibility.

**Potential Consequences**

- Harm to users following malicious links.
- Damage to project reputation.
- Hosting or domain suspension.
- Security complaints.
- Public service shutdown.

**Mitigation Strategy**

- Define an acceptable-use and destination policy.
- Validate supported URL schemes and destination formats.
- Apply rate limiting and abuse controls.
- Provide a controlled link-disabling mechanism.
- Establish an abuse reporting and response procedure.
- Review provider acceptable-use policies.
- Consider additional destination screening where justified.
- Avoid presenting URL validation as a guarantee of destination safety.

**Contingency Plan**

- Disable confirmed abusive links.
- Temporarily restrict or suspend URL creation if necessary.
- Preserve only appropriate incident evidence.
- Investigate the reported misuse.
- Adjust controls before restoring affected functionality.
- Notify the hosting provider or other relevant parties when required.

**Verification Evidence**

- Security requirements and threat review.
- Abuse-control test results.
- Verified link-disabling procedure.
- Documented incident response process.

**Release Condition**

Public URL creation must not be enabled until the mandatory baseline abuse controls and response procedures have been implemented and verified.

This release condition applies to publicly accessible anonymous URL creation. Local development and controlled testing may continue while mitigation measures are being
designed and implemented.

### R-03 — Short-Code Collision or Incorrect Mapping

**Category:** Technical  
**Likelihood:** 3 — Possible  
**Impact:** 5 — Severe  
**Initial Score:** 15 — High  
**Owner:** Project Owner  
**Status:** Open  
**Relevant Milestones:** M2, M3, M5

**Risk Description**

Short-code generation or persistence defects may produce duplicate identifiers or associate an identifier with an incorrect destination.

**Potential Causes**

- Inadequate generation strategy.
- Race conditions.
- Missing database uniqueness constraints.
- Incorrect retry behaviour.
- Persistence defects.

**Potential Consequences**

- Failed URL creation.
- Incorrect redirects.
- Data integrity violations.
- Loss of user trust.

**Mitigation Strategy**

- Enforce a unique constraint on short identifiers.
- Design explicit collision handling.
- Review concurrent creation behaviour.
- Avoid relying solely on application-level uniqueness checks.
- Test relevant database and service-layer failure scenarios.

**Contingency Plan**

- Stop or restrict affected creation operations.
- Investigate the integrity failure.
- Correct the implementation.
- Restore or repair affected records using verified evidence.
- Add regression tests before restoring normal operation.

**Verification Evidence**

- Database constraints.
- Collision handling tests.
- Concurrent creation tests.
- Persistence integration tests.

### R-04 — Database Failure or Data Loss

**Category:** Data  
**Likelihood:** 3 — Possible  
**Impact:** 5 — Severe  
**Initial Score:** 15 — High  
**Owner:** Project Owner  
**Status:** Open  
**Relevant Milestones:** M2, M3, M5, M6

**Risk Description**

Database outages, accidental deletion, infrastructure failure, or inadequate recovery procedures may cause unavailable or permanently lost URL mappings.

**Potential Causes**

- Provider failure.
- Misconfigured storage.
- Failed migrations.
- Accidental data deletion.
- Insufficient backups.
- Incorrect deployment procedures.

**Potential Consequences**

- Existing short links stop working.
- Permanent loss of URL mappings.
- Extended service interruption.
- Failed portfolio demonstration.

**Mitigation Strategy**

- Use version-controlled database migrations.
- Review migration changes.
- Evaluate persistent storage guarantees.
- Establish backup and restore procedures.
- Test database connectivity and failure handling.
- Avoid destructive operations without appropriate safeguards.

**Contingency Plan**

- Investigate database availability.
- Restore service connectivity where possible.
- Recover data from a verified backup if available.
- Reapply migrations as appropriate.
- Document unrecoverable losses and corrective actions.

**Verification Evidence**

- Database migration tests.
- Persistence restart tests.
- Documented backup strategy.
- Restore test results.

### R-05 — Uncontrolled MVP Scope Expansion

**Category:** Delivery  
**Likelihood:** 4 — Likely  
**Impact:** 4 — Major  
**Initial Score:** 16 — High  
**Owner:** Project Owner  
**Status:** Open  
**Relevant Milestones:** M0–M7

**Risk Description**

Additional features may be introduced before the core MVP is complete, increasing complexity and delaying delivery.

**Potential Causes**

- Unclear priorities.
- Premature optimisation.
- Frequent technology changes.
- Interest in advanced features.
- Insufficient scope control.

**Potential Consequences**

- Delayed MVP.
- Incomplete core workflows.
- Increased maintenance burden.
- Reduced time for testing and deployment.

**Mitigation Strategy**

- Maintain an approved Scope Statement.
- Prioritise mandatory MVP requirements.
- Use milestone exit criteria.
- Track enhancements separately.
- Evaluate proposed changes before implementation.
- Prefer vertical-slice delivery.

**Contingency Plan**

- Review unfinished work.
- Defer nonessential features.
- Reprioritise remaining tasks.
- Update the roadmap if necessary.
- Focus on completing a demonstrable MVP.

**Verification Evidence**

- Scope baseline.
- GitHub milestone tracking.
- Change records.
- Completed MVP acceptance criteria.

### R-06 — Insufficient Automated Testing

**Category:** Quality  
**Likelihood:** 4 — Likely  
**Impact:** 4 — Major  
**Initial Score:** 16 — High  
**Owner:** Project Owner  
**Status:** Open  
**Relevant Milestones:** M3, M4, M5, M7

**Risk Description**

Inadequate automated verification may allow functional defects, regressions, and data integrity issues to reach the public release.

**Potential Causes**

- Testing deferred until late development.
- Missing edge-case coverage.
- Limited integration testing.
- Unreliable test environments.
- Insufficient CI checks.

**Potential Consequences**

- Incorrect redirects.
- Unexpected API failures.
- Broken expiration behaviour.
- Deployment regressions.
- Increased debugging effort.

**Mitigation Strategy**

- Define acceptance criteria before implementation.
- Add tests alongside feature development.
- Include integration tests for persistence and API behaviour.
- Test expected failure scenarios.
- Run automated checks through CI.
- Review test coverage based on risk and criticality.

**Contingency Plan**

- Delay release if critical functionality lacks verification.
- Add missing regression tests.
- Fix identified defects.
- Re-run the required test suite.
- Reassess release readiness.

**Verification Evidence**

- Automated test results.
- CI execution records.
- Requirements-to-test traceability.
- Regression test coverage.

### R-07 — Unnecessary Collection or Exposure of Visitor Data

**Category:** Privacy  
**Likelihood:** 3 — Possible  
**Impact:** 4 — Major  
**Initial Score:** 12 — High  
**Owner:** Project Owner  
**Status:** Open  
**Relevant Milestones:** M1, M2, M5, M6

**Risk Description**

Application logs, analytics, or third-party infrastructure may collect or expose more visitor information than necessary.

**Potential Causes**

- Excessive request logging.
- Unreviewed third-party services.
- Unclear retention policies.
- Sensitive data in URL query parameters.
- Inadequate access controls.

**Potential Consequences**

- Privacy concerns.
- Sensitive information exposure.
- Compliance obligations.
- Increased operational risk.

**Mitigation Strategy**

- Minimise personal data collection.
- Avoid logging unnecessary query parameters or sensitive values.
- Define logging and retention policies.
- Restrict access to operational data.
- Review hosting provider data handling.
- Defer advanced analytics until privacy requirements are defined.

**Contingency Plan**

- Restrict access to affected data.
- Stop unnecessary collection.
- Investigate exposure.
- Remove improperly retained information where appropriate.
- Review applicable notification and legal obligations.

**Verification Evidence**

- Logging configuration review.
- Data inventory.
- Retention policy.
- Security and privacy review.

### R-08 — Solo Development Bottlenecks

**Category:** Delivery  
**Likelihood:** 4 — Likely  
**Impact:** 3 — Moderate  
**Initial Score:** 12 — High  
**Owner:** Project Owner  
**Status:** Open  
**Relevant Milestones:** M0–M8

**Risk Description**

A single developer is responsible for requirements, design, implementation, testing, documentation, deployment, and maintenance.

**Potential Causes**

- Competing commitments.
- Limited development time.
- Unfamiliar technologies.
- Excessive documentation overhead.
- Operational interruptions.

**Potential Consequences**

- Slower delivery.
- Incomplete milestones.
- Accumulated technical debt.
- Delayed issue resolution.

**Mitigation Strategy**

- Keep milestone tasks manageable.
- Prioritise essential work.
- Avoid unnecessary infrastructure.
- Maintain concise, useful documentation.
- Track progress through GitHub Issues.
- Reassess workload regularly.

**Contingency Plan**

- Reduce optional work.
- Replan milestone sequencing.
- Defer enhancements.
- Prioritise critical defects and core functionality.

**Verification Evidence**

- Updated issue backlog.
- Milestone progress.
- Periodic roadmap reviews.

### R-09 — Unsafe Destination Handling or SSRF Exposure

**Category:** Security  
**Likelihood:** 3 — Possible  
**Impact:** 5 — Severe  
**Initial Score:** 15 — High  
**Owner:** Project Owner  
**Status:** Open  
**Relevant Milestones:** M1, M2, M3, M5

**Risk Description**

Unsafe URL handling may allow prohibited destination schemes, internal network targeting, or server-side request forgery if server-side URL fetching or preview features are introduced.

**Potential Causes**

- Incomplete URL parsing.
- Insufficient destination policy enforcement.
- Unsafe server-side HTTP requests.
- Inadequate redirect handling.
- Unreviewed future preview or metadata-fetching features.

**Potential Consequences**

- Unsafe redirects.
- Exposure of internal services if server-side fetching is introduced.
- Abuse of infrastructure.
- Security incidents.

**Mitigation Strategy**

- Use appropriate URL parsing and validation.
- Restrict accepted schemes to HTTP and HTTPS.
- Define a destination acceptance policy.
- Avoid server-side fetching of user-supplied URLs in the MVP unless explicitly required.
- If server-side fetching is introduced, perform dedicated SSRF threat modelling and implement network-level protections.
- Test representative malicious and malformed inputs.

**Contingency Plan**

- Disable the vulnerable operation.
- Investigate exposure.
- Correct validation or network controls.
- Add regression tests.
- Reassess related features before re-enabling them.

**Verification Evidence**

- URL validation tests.
- Security review.
- Documented destination policy.
- SSRF assessment if applicable.

**Technical Note**

A redirect-only application does not automatically perform server-side requests to submitted destinations. SSRF risk becomes especially relevant when the backend fetches, previews, verifies, or otherwise connects to user-supplied URLs.

### R-10 — Excessive Automated Traffic

**Category:** Security  
**Likelihood:** 4 — Likely  
**Impact:** 4 — Major  
**Initial Score:** 16 — High  
**Owner:** Project Owner  
**Status:** Open  
**Relevant Milestones:** M2, M3, M5, M6

**Risk Description**

Automated clients may submit excessive creation or redirect requests, exhausting application, database, or hosting resources.

**Potential Causes**

- Unrestricted public endpoints.
- Bot traffic.
- Repeated automated requests.
- Limited infrastructure capacity.
- Ineffective request controls.

**Potential Consequences**

- Increased latency.
- Database overload.
- Resource quota exhaustion.
- Service suspension.
- Public demo unavailability.

**Mitigation Strategy**

- Implement appropriate rate limiting.
- Review resource limits.
- Apply reasonable request size constraints.
- Monitor relevant error and traffic patterns.
- Define protective responses for excessive requests.
- Evaluate infrastructure-level controls.

**Contingency Plan**

- Restrict affected endpoints.
- Apply temporary traffic controls.
- Investigate resource usage.
- Adjust limits based on evidence.
- Restore normal access when stable.

**Verification Evidence**

- Rate-limit tests.
- Load test results.
- Resource monitoring.
- Operational response procedures.

### R-11 — Incorrect Expiration or Redirect Behaviour

**Category:** Technical  
**Likelihood:** 3 — Possible  
**Impact:** 4 — Major  
**Initial Score:** 12 — High  
**Owner:** Project Owner  
**Status:** Open  
**Relevant Milestones:** M1, M2, M3, M5

**Risk Description**

Incorrect expiration comparisons, timezone handling, or HTTP redirect semantics may cause links to remain active beyond expiration or fail unexpectedly.

**Potential Causes**

- Ambiguous expiration rules.
- Incorrect timestamp handling.
- Timezone assumptions.
- Boundary-condition defects.
- Incorrect HTTP status or cache behaviour.

**Potential Consequences**

- Expired links continue redirecting.
- Valid links become unavailable.
- Inconsistent client behaviour.
- Incorrect link lifecycle handling.

**Mitigation Strategy**

- Define expiration business rules.
- Use consistent timestamp conventions.
- Test expiration boundaries.
- Document redirect status and cache behaviour.
- Verify error responses.
- Include regression tests.

**Contingency Plan**

- Correct affected logic.
- Restrict impacted functionality if necessary.
- Review affected mappings.
- Add regression tests.
- Revalidate documented behaviour.

**Verification Evidence**

- Expiration tests.
- Redirect integration tests.
- API contract verification.
- Business rule traceability.

### R-12 — Production Configuration Exposes Sensitive Information

**Category:** Security  
**Likelihood:** 3 — Possible  
**Impact:** 5 — Severe  
**Initial Score:** 15 — High  
**Owner:** Project Owner  
**Status:** Open  
**Relevant Milestones:** M3, M5, M6

**Risk Description**

Credentials, database connection information, or internal application details may be exposed through source control, logs, errors, or deployment configuration.

**Potential Causes**

- Hard-coded secrets.
- Incorrect environment configuration.
- Public diagnostic endpoints.
- Excessive error details.
- Misconfigured repository exclusions.
- Insecure CI settings.

**Potential Consequences**

- Unauthorised access.
- Data compromise.
- Infrastructure misuse.
- Required credential rotation.
- Service disruption.

**Mitigation Strategy**

- Store secrets outside source control.
- Use environment-specific configuration.
- Review Git ignore rules.
- Restrict diagnostic endpoint exposure.
- Sanitize public error responses.
- Review CI and deployment permissions.
- Scan for accidentally committed secrets.

**Contingency Plan**

- Revoke or rotate exposed credentials.
- Restrict affected services.
- Investigate the exposure.
- Remove sensitive information from active configurations.
- Follow an appropriate repository history remediation process where necessary.
- Document corrective actions.

**Verification Evidence**

- Secret scanning results.
- Configuration review.
- Deployment security checklist.
- Error response tests.

### R-13 — Dependency or Platform Compatibility Problems

**Category:** Technical  
**Likelihood:** 3 — Possible  
**Impact:** 3 — Moderate  
**Initial Score:** 9 — Medium  
**Owner:** Project Owner  
**Status:** Open  
**Relevant Milestones:** M2, M3, M4, M5

**Risk Description**

Framework, Java runtime, database driver, or tooling incompatibilities may delay development or cause build and runtime failures.

**Potential Causes**

- Unsupported version combinations.
- Dependency conflicts.
- Breaking framework updates.
- Inconsistent local and CI environments.

**Potential Consequences**

- Build failures.
- Development delays.
- Additional migration work.
- Unstable application behaviour.

**Mitigation Strategy**

- Verify compatibility before project generation.
- Use supported dependency versions.
- Prefer managed dependency versions.
- Document the development environment.
- Keep CI and local build environments aligned.
- Avoid unnecessary dependency upgrades during critical milestones.

**Contingency Plan**

- Identify the incompatible component.
- Select a supported version combination.
- Revert problematic upgrades.
- Update documentation and tests.

**Verification Evidence**

- Successful local build.
- Successful CI build.
- Compatibility review.
- Dependency inventory.

### R-14 — Inadequate Monitoring and Incident Response

**Category:** Operational  
**Likelihood:** 3 — Possible  
**Impact:** 4 — Major  
**Initial Score:** 12 — High  
**Owner:** Project Owner  
**Status:** Open  
**Relevant Milestones:** M5, M6, M7

**Risk Description**

Operational failures or abusive activity may remain undetected or unresolved because of insufficient diagnostics and response procedures.

**Potential Causes**

- Missing health checks.
- Inadequate logging.
- No incident response plan.
- Unclear recovery procedures.
- Limited hosting observability.

**Potential Consequences**

- Prolonged outages.
- Delayed abuse response.
- Difficult troubleshooting.
- Reduced service reliability.

**Mitigation Strategy**

- Implement health checks.
- Configure appropriate logging.
- Document common failure scenarios.
- Establish incident response procedures.
- Define recovery steps.
- Review available hosting diagnostics.

**Contingency Plan**

- Investigate reported failures.
- Temporarily restrict affected functionality if necessary.
- Restore service from known-good configuration.
- Document the incident and corrective actions.

**Verification Evidence**

- Health-check tests.
- Logging review.
- Operations guide.
- Recovery rehearsal.

### R-15 — Performance Degradation Under Unexpected Traffic

**Category:** Technical  
**Likelihood:** 3 — Possible  
**Impact:** 3 — Moderate  
**Initial Score:** 9 — Medium  
**Owner:** Project Owner  
**Status:** Open  
**Relevant Milestones:** M3, M5, M6

**Risk Description**

URL creation or redirection performance may degrade under traffic levels exceeding the capacity of the selected application or database infrastructure.

**Potential Causes**

- Inefficient database queries.
- Missing indexes.
- Connection pool exhaustion.
- Limited compute resources.
- Unexpected request volume.
- Inefficient application logic.

**Potential Consequences**

- Increased response latency.
- Request failures.
- Reduced usability.
- Resource exhaustion.

**Mitigation Strategy**

- Design indexes for expected lookup patterns.
- Measure representative workloads.
- Review database queries.
- Configure connection pooling appropriately.
- Monitor resource consumption.
- Introduce caching only when justified by evidence.

**Contingency Plan**

- Apply temporary traffic controls.
- Investigate bottlenecks.
- Optimise affected queries or components.
- Adjust resource configuration where possible.
- Reassess infrastructure suitability.

**Verification Evidence**

- Performance test results.
- Query analysis.
- Resource usage observations.
- Documented optimisation decisions.

## 6. Risk Treatment Priorities

The initial treatment sequence is based on risk severity and project dependencies.

### Priority A — Address Before Public Exposure

- R-02 — Phishing and spam abuse.
- R-09 — Unsafe destination handling.
- R-10 — Excessive automated traffic.
- R-12 — Sensitive configuration exposure.
- R-07 — Visitor data privacy.
- R-14 — Operational incident response.

### Priority B — Address During Core Implementation

- R-03 — Short-code collisions.
- R-04 — Database failure and data loss.
- R-06 — Insufficient testing.
- R-11 — Incorrect expiration or redirect behaviour.
- R-13 — Dependency compatibility.

### Priority C — Monitor Throughout Delivery

- R-01 — Free-tier infrastructure suitability.
- R-05 — Scope expansion.
- R-08 — Solo development bottlenecks.
- R-15 — Performance degradation.

These groups indicate the primary treatment stage rather than exclusive ownership by that stage.

## 7. Residual Risk Assessment

Residual risk is the remaining risk after planned controls have been implemented.

Residual scores will not be assigned as confirmed values until mitigation measures have been implemented and evaluated.

The residual assessment should record:

| Field | Description |
|---|---|
| Risk ID | Reference to the original risk |
| Controls Implemented | Verified preventive or mitigating measures |
| Residual Likelihood | Reassessed likelihood after controls |
| Residual Impact | Reassessed impact after controls |
| Residual Score | Residual likelihood multiplied by residual impact |
| Evidence | Tests, reviews, operational records, or other supporting material |
| Acceptance Decision | Accept, further mitigate, transfer, avoid, or escalate |
| Review Date | Date of the reassessment |

High or Critical residual risks affecting public exposure require explicit review and a documented release decision.

## 8. Risk Monitoring and Review

The Risk Register will be reviewed:

- At the completion of each project milestone.
- When significant requirements change.
- When major architecture decisions are made.
- When new vulnerabilities are discovered.
- After relevant test failures.
- Before public deployment.
- Following operational incidents.
- Before major releases.

Each review should determine:

1. Whether existing risks remain relevant.
2. Whether likelihood or impact has changed.
3. Whether mitigation actions have been completed.
4. Whether controls are effective.
5. Whether new risks must be added.
6. Whether any risks have become active issues.
7. Whether residual risk is acceptable.

## 9. Risk Escalation and Release Gates

### 9.1 Critical Risks

Critical risks require immediate attention.

A Critical risk that directly affects public exposure must not remain untreated when enabling the relevant public functionality.

### 9.2 High Risks

High risks require documented mitigation plans and verification before their relevant release gates.

### 9.3 Medium and Low Risks

Medium and Low risks may be monitored or accepted when their expected impact is proportionate to the project's constraints.

### 9.4 Release Decision

The Project Owner is responsible for determining whether remaining risks are acceptable for the intended release.

A release decision must consider:

- Implemented controls.
- Verification evidence.
- Residual risk.
- Operational limitations.
- Potential harm to users or external parties.
- Applicable hosting policies.

The project must not treat a successful deployment as sufficient evidence of security or operational readiness.

## 10. Risk-to-Milestone Traceability

| Milestone | Primary Risk Focus |
|---|---|
| M0 — Initiation | R-05, R-08 |
| M1 — Requirements | R-02, R-07, R-09, R-11 |
| M2 — System Design | R-02, R-03, R-04, R-09, R-10, R-13 |
| M3 — Backend MVP | R-03, R-04, R-06, R-09, R-10, R-11, R-12 |
| M4 — Web MVP | R-06, R-07, R-12 |
| M5 — Quality and Security | R-02, R-03, R-06, R-07, R-09, R-10, R-11, R-12, R-14, R-15 |
| M6 — Deployment | R-01, R-02, R-04, R-07, R-10, R-12, R-14 |
| M7 — Portfolio Release | All unresolved release-relevant risks |
| M8 — Enhancements | Reassess existing risks and identify feature-specific risks |

## 11. Risk Ownership

The Project Owner is the initial owner of all identified risks because HelixURL is a solo development project.

Risk ownership includes:

- Monitoring the risk.
- Maintaining mitigation plans.
- Reviewing relevant evidence.
- Updating risk status.
- Escalating significant changes.
- Recording residual risk and acceptance decisions.

Risk ownership may be revised if additional contributors or operational stakeholders become involved.

## 12. Risk Register Maintenance

Each material update should record:

- The affected risk identifier.
- The nature of the change.
- The reason for reassessment.
- Updated ratings or status.
- New mitigation actions.
- Relevant evidence.
- The date of review.

Risk records should retain stable identifiers to support traceability.

New risks should receive the next available identifier rather than reusing retired identifiers.

## 13. Review and Approval

| Field | Value |
|---|---|
| Document Owner | Project Owner |
| Review Status | Reviewed and Accepted |
| Approval Authority | Project Owner |
| Approval Date | 2026-11-08 |
| Baseline Status | Approved - Initial Baseline |

The initial Risk Register will be reviewed with the Project Charter, Scope Statement, and Development Roadmap before the Phase 00 baseline is approved.

---

## Document History

| Version | Date | Description | Status |
|---|---|---|---|
| 1.0.0 | 2026-11-08 | Initial Risk Register | Approved - Initial Baseline |
| 0.1.0 | 2026-11-06 | Initial Risk Register draft | Pending Review |
