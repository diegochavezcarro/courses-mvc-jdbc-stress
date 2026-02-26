# Tasks: Refactor to Idiomatic Reactive Data Flow

**Input**: Design documents from `/specs/001-refactor-webflux-jpa/`  
**Prerequisites**: plan.md, spec.md, research.md, data-model.md, contracts/courses-api.yaml, quickstart.md

## Format: `[ID] [P?] [Story] Description`

- **[P]**: Can run in parallel (different files, no dependencies)
- **[Story]**: User story label (`[US1]`, `[US2]`, `[US3]`)
- Every task includes an exact file path

## Phase 1: Setup (Shared Infrastructure)

**Purpose**: Prepare project dependencies and base runtime configuration for reactive stack migration.

- [ ] T001 Update reactive dependencies in `/pom.xml` (add WebFlux, Spring Data R2DBC, R2DBC PostgreSQL; remove blocking web/jdbc starters)
- [ ] T002 Configure reactive PostgreSQL connection settings in `/src/main/resources/application.yml`
- [ ] T003 [P] Add test dependencies and plugins needed for reactive endpoint/integration tests in `/pom.xml`

---

## Phase 2: Foundational (Blocking Prerequisites)

**Purpose**: Implement shared primitives required by all stories before endpoint behavior work.

- [ ] T004 Create standardized API error payload record in `/src/main/java/com/diegochavez/courses/model/ApiErrorResponse.java`
- [ ] T005 [P] Create error code enum/constants for API failures in `/src/main/java/com/diegochavez/courses/model/ApiErrorCode.java`
- [ ] T006 Create global exception handler for validation/unexpected errors in `/src/main/java/com/diegochavez/courses/controller/GlobalExceptionHandler.java`
- [ ] T007 [P] Add reactive database row mapping support for `Course` in `/src/main/java/com/diegochavez/courses/repository/CourseRowMapper.java`
- [ ] T008 Create validated request model for course list limit constraints in `/src/main/java/com/diegochavez/courses/model/CourseListRequest.java`
- [ ] T009 Introduce reactive repository contract for course queries in `/src/main/java/com/diegochavez/courses/repository/CourseReactiveRepository.java`

**Checkpoint**: Shared reactive and error-handling foundations are complete.

---

## Phase 3: User Story 1 - Reliable Course Retrieval Under Load (Priority: P1) 🎯 MVP

**Goal**: Deliver non-blocking course retrieval using reactive persistence while preserving strict API compatibility.

**Independent Test**: Call `GET /courses?limit=100` and validate 200 responses + contract shape under concurrent requests.

### Tests for User Story 1

- [ ] T010 [P] [US1] Add contract test for `GET /courses` success payload shape in `/src/test/java/com/diegochavez/courses/controller/CourseControllerContractTest.java`
- [ ] T011 [P] [US1] Add integration test for deterministic ordering and limit handling in `/src/test/java/com/diegochavez/courses/service/CourseServiceIntegrationTest.java`

### Implementation for User Story 1

- [ ] T012 [US1] Replace blocking repository implementation with reactive data access in `/src/main/java/com/diegochavez/courses/repository/CourseRepository.java`
- [ ] T013 [US1] Refactor service to reactive return types and orchestration in `/src/main/java/com/diegochavez/courses/service/CourseService.java`
- [ ] T014 [US1] Refactor controller endpoint to WebFlux handler preserving existing API contract in `/src/main/java/com/diegochavez/courses/controller/CourseController.java`
- [ ] T015 [US1] Ensure default `limit=100` behavior and response compatibility in `/src/main/java/com/diegochavez/courses/controller/CourseController.java`
- [ ] T016 [US1] Add repository-level query test coverage for reactive limit behavior in `/src/test/java/com/diegochavez/courses/repository/CourseRepositoryTest.java`

**Checkpoint**: US1 is independently functional and benchmarkable.

---

## Phase 4: User Story 2 - Predictable Validation and Error Responses (Priority: P2)

**Goal**: Enforce API input constraints (`1..1000`) and return standardized error payloads.

**Independent Test**: Send invalid `limit` values (`0`, `-1`, `1001`) and verify stable `code/message/timestamp/path` response.

### Tests for User Story 2

- [ ] T017 [P] [US2] Add controller validation test for `limit <= 0` in `/src/test/java/com/diegochavez/courses/controller/CourseControllerValidationTest.java`
- [ ] T018 [P] [US2] Add controller validation test for `limit > 1000` in `/src/test/java/com/diegochavez/courses/controller/CourseControllerValidationTest.java`
- [ ] T019 [P] [US2] Add error-schema contract test for unexpected failures in `/src/test/java/com/diegochavez/courses/controller/CourseControllerErrorContractTest.java`

### Implementation for User Story 2

- [ ] T020 [US2] Add endpoint-level validation annotations and constraints for `limit` in `/src/main/java/com/diegochavez/courses/controller/CourseController.java`
- [ ] T021 [US2] Implement validation error mapping to standardized payload in `/src/main/java/com/diegochavez/courses/controller/GlobalExceptionHandler.java`
- [ ] T022 [US2] Implement unexpected error mapping to standardized payload in `/src/main/java/com/diegochavez/courses/controller/GlobalExceptionHandler.java`
- [ ] T023 [US2] Ensure all error responses include `code`, `message`, `timestamp`, `path` in `/src/main/java/com/diegochavez/courses/model/ApiErrorResponse.java`

**Checkpoint**: US2 is independently testable with deterministic validation/error behavior.

---

## Phase 5: User Story 3 - Maintainable Service Boundaries for Future Changes (Priority: P3)

**Goal**: Improve internal maintainability with clear reactive boundaries and documented evolution points.

**Independent Test**: Verify boundary tests and documentation allow new maintainers to understand reactive flow quickly.

### Tests for User Story 3

- [ ] T024 [P] [US3] Add service unit tests for orchestration boundaries in `/src/test/java/com/diegochavez/courses/service/CourseServiceUnitTest.java`
- [ ] T025 [P] [US3] Add repository contract test for reactive query abstraction in `/src/test/java/com/diegochavez/courses/repository/CourseReactiveRepositoryContractTest.java`

### Implementation for User Story 3

- [ ] T026 [US3] Introduce service interface to formalize read-use-case boundary in `/src/main/java/com/diegochavez/courses/service/CourseQueryService.java`
- [ ] T027 [US3] Update service implementation to implement boundary interface in `/src/main/java/com/diegochavez/courses/service/CourseService.java`
- [ ] T028 [US3] Decouple controller from concrete service by injecting boundary interface in `/src/main/java/com/diegochavez/courses/controller/CourseController.java`
- [ ] T029 [US3] Add repository adapter class if needed to isolate persistence details in `/src/main/java/com/diegochavez/courses/repository/CourseRepositoryAdapter.java`
- [ ] T030 [US3] Document maintainability decisions and extension points in `/specs/001-refactor-webflux-jpa/research.md`

**Checkpoint**: US3 provides maintainable boundaries and clearer extensibility.

---

## Phase 6: Polish & Cross-Cutting Concerns

**Purpose**: Final consistency, performance evidence, and documentation validation across stories.

- [ ] T031 [P] Update quickstart verification steps with final commands/results in `/specs/001-refactor-webflux-jpa/quickstart.md`
- [ ] T032 Run full test suite and record result notes in `/specs/001-refactor-webflux-jpa/quickstart.md`
- [ ] T033 [P] Execute comparative k6 benchmark and document summary artifact names in `/specs/001-refactor-webflux-jpa/quickstart.md`
- [ ] T034 Final dependency/config cleanup for removed blocking stack items in `/pom.xml`

---

## Dependencies & Execution Order

### Phase Dependencies

- **Phase 1 (Setup)**: starts immediately.
- **Phase 2 (Foundational)**: depends on Phase 1 and blocks all user stories.
- **Phase 3 (US1)**: depends on Phase 2.
- **Phase 4 (US2)**: depends on Phase 2 and can run after/alongside US1 when foundation is complete.
- **Phase 5 (US3)**: depends on Phase 2 and should run after US1 core refactor stabilizes.
- **Phase 6 (Polish)**: depends on all selected user stories.

### User Story Dependencies

- **US1 (P1)**: no dependency on other stories after foundation.
- **US2 (P2)**: independent from US1 functionality but validates same endpoint boundaries.
- **US3 (P3)**: depends on reactive flow from US1 to finalize maintainability boundaries.

### Within Each User Story

- Tests first (write and verify failing state where applicable)
- Repository/service/controller implementation after tests
- Story-level verification before moving to next story

---

## Parallel Execution Examples

### User Story 1

- Run in parallel:
  - T010 in `/src/test/java/com/diegochavez/courses/controller/CourseControllerContractTest.java`
  - T011 in `/src/test/java/com/diegochavez/courses/service/CourseServiceIntegrationTest.java`

### User Story 2

- Run in parallel:
  - T017 in `/src/test/java/com/diegochavez/courses/controller/CourseControllerValidationTest.java`
  - T019 in `/src/test/java/com/diegochavez/courses/controller/CourseControllerErrorContractTest.java`

### User Story 3

- Run in parallel:
  - T024 in `/src/test/java/com/diegochavez/courses/service/CourseServiceUnitTest.java`
  - T025 in `/src/test/java/com/diegochavez/courses/repository/CourseReactiveRepositoryContractTest.java`

---

## Implementation Strategy

### MVP First (US1)

1. Finish Setup + Foundational phases.
2. Deliver US1 end-to-end with passing story tests.
3. Validate performance and contract compatibility on `/courses`.

### Incremental Delivery

1. Build foundation once.
2. Deliver US1 (MVP).
3. Deliver US2 validation/error guarantees.
4. Deliver US3 maintainability boundaries.
5. Polish with full tests + k6 evidence.

### Parallel Team Strategy

1. One developer handles foundational error/validation primitives.
2. One developer handles reactive repository/service/controller migration (US1).
3. Another developer prepares validation/error tests (US2) once endpoint compiles.
4. Final contributor handles maintainability boundary and docs (US3).

---

## Notes

- Tasks follow strict checklist format with IDs, optional `[P]`, and story labels only in story phases.
- Keep commits focused per coherent change slice.
- Update spec/plan artifacts if design decisions change during implementation.
