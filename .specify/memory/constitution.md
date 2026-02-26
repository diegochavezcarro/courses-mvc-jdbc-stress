<!--
Sync Impact Report
- Version change: template draft → 1.0.0
- Modified principles:
  - placeholder principle 1 → I. Skills-First Engineering Standard
  - placeholder principle 2 → II. Spring Boot 3 + Java 21 Baseline
  - placeholder principle 3 → III. API Correctness and Data Integrity
  - placeholder principle 4 → IV. Testing and Performance Evidence
  - placeholder principle 5 → V. Focused Commits and Decision Traceability
- Added sections:
  - Technical Guardrails
  - Delivery Workflow
- Removed sections:
  - None
- Templates requiring updates:
  - ✅ updated: .specify/templates/plan-template.md
  - ✅ updated: .specify/templates/spec-template.md
  - ✅ updated: .specify/templates/tasks-template.md
  - ⚠ pending: .specify/templates/commands/*.md (directory not present in this repository)
- Runtime guidance updates:
  - ✅ updated: README.md
- Deferred follow-ups:
  - None
-->

# Courses MVC JDBC Constitution

## Core Principles

### I. Skills-First Engineering Standard
Work in this repository MUST follow the standards in `skills/backend/SKILL.md`.
When guidance conflicts arise, the skills file takes precedence over ad hoc style
preferences. Implementations MUST align with its architecture, framework, and
quality constraints.

Rationale: A single source of engineering truth reduces inconsistency and review
friction.

### II. Spring Boot 3 + Java 21 Baseline
All production code MUST target Spring Boot 3.x and Java 21+ idioms already used
by this repository. Dependency injection MUST use constructors. APIs MUST use
`@RestController` and correct HTTP semantics. Field injection and deprecated
Spring Boot 2.x patterns are prohibited.

Rationale: Consistent modern Spring practices improve maintainability,
compatibility, and operational safety.

### III. API Correctness and Data Integrity
Inputs at API boundaries MUST be validated (`@Valid` and constraints as needed).
Business operations spanning multiple data steps MUST define transaction
boundaries (`@Transactional`) where applicable. Exceptions exposed to clients
MUST be normalized through centralized handling (for example, `@ControllerAdvice`)
and MUST NOT leak internal stack details.

Rationale: Predictable API behavior and data consistency prevent production
incidents and client regressions.

### IV. Testing and Performance Evidence
Changes to behavior MUST include or update tests at the correct level (unit,
integration, or application tests). Performance-sensitive changes MUST include
evidence (for example, k6 output or measured latency/throughput deltas) when
claims are made. Mixing blocking and reactive styles in the same execution path
MUST be explicitly justified in the plan.

Rationale: Objective evidence is required to validate correctness and
non-functional expectations.

### V. Focused Commits and Decision Traceability
Implementation work MUST be organized into focused, reviewable commits that each
cover one coherent change. Key technical decisions and trade-offs MUST be
documented in spec/plan artifacts before or alongside implementation.

Rationale: Small commits improve review quality; documented decisions preserve
project memory.

## Technical Guardrails

- Secrets, credentials, and environment-specific sensitive values MUST NOT be
	committed in code or static configuration files.
- Configuration MUST be externalized through `application.yml` conventions and
	type-safe binding where structured settings are introduced.
- Use stereotype annotations intentionally: `@Service`, `@Repository`, and
	`@Controller`/`@RestController` MUST be preferred over generic `@Component`
	when semantics are known.
- New dependencies SHOULD be minimized and MUST be justified in the plan when
	they increase operational or maintenance surface area.

## Delivery Workflow

1. Specification and plan artifacts MUST capture scope, constraints, and key
	 decisions before substantial code changes.
2. Implementation MUST preserve layered boundaries (controller/service/repository)
	 and keep classes focused.
3. Verification MUST include targeted tests first, then broader checks as needed.
4. Pull requests MUST show constitution compliance in the plan's Constitution
	 Check and include references to decision notes.

## Governance

This constitution supersedes repository-level conventions when conflicts occur,
except for mandatory platform or legal requirements.

Amendment procedure:
- Amendments MUST be proposed in a pull request that includes rationale,
	impacted principles, and required template or documentation updates.
- At least one maintainer approval is REQUIRED before merge.
- Ratified amendments MUST update the Sync Impact Report in this file.

Versioning policy (semantic versioning):
- MAJOR: Backward-incompatible governance changes or principle removals.
- MINOR: New principle/section or materially expanded obligations.
- PATCH: Clarifications, wording improvements, typo fixes, or non-semantic edits.

Compliance review expectations:
- Every feature plan MUST pass Constitution Check gates before implementation.
- Code review MUST verify skills-file compliance, focused commit scope, and
	decision traceability in spec/plan artifacts.
- Exceptions MUST be explicitly documented in the Complexity Tracking section of
	the implementation plan.

**Version**: 1.0.0 | **Ratified**: 2026-02-26 | **Last Amended**: 2026-02-26
