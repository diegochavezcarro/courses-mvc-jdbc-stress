# Implementation Plan: Refactor to Idiomatic Reactive Data Flow

**Branch**: `001-refactor-webflux-jpa` | **Date**: 2026-02-26 | **Spec**: `/specs/001-refactor-webflux-jpa/spec.md`
**Input**: Feature specification from `/specs/001-refactor-webflux-jpa/spec.md`

**Note**: This template is filled in by the `/speckit.plan` command. See `.specify/templates/plan-template.md` for the execution workflow.

## Summary

Refactor the current blocking Spring MVC + JDBC retrieval path to an idiomatic
Spring WebFlux flow backed by native reactive persistence (R2DBC), while
preserving strict backward compatibility of the `/courses` API contract. The
plan prioritizes stable behavior under load, explicit validation for `limit`
(including max=1000), and standardized sanitized error payloads.

## Technical Context

**Language/Version**: Java 21, Spring Boot 3.5.x  
**Primary Dependencies**: `spring-boot-starter-webflux`, `spring-boot-starter-data-r2dbc`, PostgreSQL R2DBC driver, `spring-boot-starter-validation`, `spring-boot-starter-test`  
**Storage**: PostgreSQL (`courses` table), reactive access via R2DBC  
**Testing**: JUnit 5 + Spring Boot Test (context/integration), WebTestClient for endpoint verification, existing k6 scripts for comparative load checks  
**Target Platform**: Linux/macOS server runtime with JVM 21
**Project Type**: single-module backend web service  
**Performance Goals**: meet spec criteria (>=99% success under sustained load, p95 <= 1s for agreed benchmark profiles)  
**Constraints**: strict API compatibility; max `limit` = 1000 with validation error on exceed; standardized error schema (`code`,`message`,`timestamp`,`path`)  
**Scale/Scope**: single read endpoint `/courses` and supporting validation/error/data-access refactor

## Constitution Check

*GATE: Must pass before Phase 0 research. Re-check after Phase 1 design.*

- Pre-design gate status: PASS
  - Skills-first compliance confirmed against `skills/backend/SKILL.md`.
  - Stack baseline confirmed for Spring Boot 3.x and Java 21.
  - API correctness controls identified (validation and centralized error handling).
  - Test and performance evidence strategy identified (WebTestClient + k6).
  - Decision traceability captured in spec and this plan.

- Post-design gate status: PASS
  - Design selects native reactive persistence (R2DBC), avoiding blocking access
    in reactive request paths.
  - API contract preservation and validation/error schema rules are reflected in
    data model, contract, and quickstart artifacts.
  - Planned work remains decomposable into focused commit slices.

## Project Structure

### Documentation (this feature)

```text
specs/001-refactor-webflux-jpa/
├── plan.md              # This file (/speckit.plan command output)
├── research.md          # Phase 0 output (/speckit.plan command)
├── data-model.md        # Phase 1 output (/speckit.plan command)
├── quickstart.md        # Phase 1 output (/speckit.plan command)
├── contracts/           # Phase 1 output (/speckit.plan command)
└── tasks.md             # Phase 2 output (/speckit.tasks command - NOT created by /speckit.plan)
```

### Source Code (repository root)
```text
src/
├── main/
│   ├── java/com/diegochavez/courses/
│   │   ├── controller/
│   │   ├── service/
│   │   ├── repository/
│   │   └── model/
│   └── resources/
└── test/
  └── java/com/diegochavez/courses/

k6/
└── *.js + baseline summary/output files
```

**Structure Decision**: Keep single-module layout and evolve existing layers in
place (`controller` → reactive API boundary, `service` orchestration,
`repository` reactive persistence), plus contracts and design docs under the
feature spec directory.

## Complexity Tracking

> **Fill ONLY if Constitution Check has violations that must be justified**

| Violation | Why Needed | Simpler Alternative Rejected Because |
|-----------|------------|-------------------------------------|
| None | N/A | N/A |
