# Phase 0 Research — Refactor to Idiomatic Reactive Data Flow

## Decision 1: Use native reactive persistence (R2DBC) for WebFlux request paths
- Decision: Replace blocking JDBC/JPA-style access in the `/courses` read path with Spring Data R2DBC repositories and reactive return types.
- Rationale: The clarified requirement explicitly mandates non-blocking persistence in WebFlux paths, and this aligns with idiomatic reactive design and event-loop safety.
- Alternatives considered:
  - Wrap blocking data access in `boundedElastic`: rejected because it preserves blocking IO and increases operational complexity.
  - Keep current blocking MVC/JDBC implementation: rejected because it conflicts with migration objective and clarified constraints.

## Decision 2: Preserve strict API compatibility for `/courses`
- Decision: Keep endpoint path, query parameter names/defaults, and response payload shape unchanged for existing consumers.
- Rationale: The spec requires transparent migration with no client-breaking contract changes.
- Alternatives considered:
  - Add response metadata fields: rejected for this phase to avoid contract drift.
  - Introduce versioned endpoint: rejected because backward compatibility is required without forcing client migration.

## Decision 3: Enforce `limit` validation with max=1000 and explicit error response
- Decision: Validate `limit` at API boundary; reject `limit <= 0` and `limit > 1000` with standardized validation errors.
- Rationale: Prevents load amplification and keeps behavior deterministic and testable.
- Alternatives considered:
  - Silent truncation to max: rejected due to hidden behavior and debugging ambiguity.
  - No maximum: rejected due to service risk under heavy client input.

## Decision 4: Standardize error response schema
- Decision: Return stable error payload fields `code`, `message`, `timestamp`, and `path` for validation and unexpected failures.
- Rationale: Creates predictable client behavior and clear test assertions while avoiding internal detail leakage.
- Alternatives considered:
  - Keep ad hoc/default framework errors: rejected due to instability across failure types.
  - Full RFC 7807 adoption now: deferred to avoid unnecessary contract expansion in this refactor.

## Decision 5: Keep layered architecture with focused migration boundaries
- Decision: Retain `controller` → `service` → `repository` separation, replacing internals with reactive equivalents and explicit DTO/validation/error boundaries.
- Rationale: Matches existing repository structure and constitution constraints for maintainable, focused changes.
- Alternatives considered:
  - Collapse service/repository for a single endpoint: rejected due to reduced extensibility and weaker test boundaries.
  - Broad package restructuring during migration: rejected to minimize risk and preserve reviewability.

## Decision 6: Verification approach
- Decision: Combine functional integration checks (WebTestClient) with comparative k6 load evidence.
- Rationale: Covers both correctness and non-functional targets in spec success criteria.
- Alternatives considered:
  - Unit tests only: rejected because endpoint contract and runtime behavior under load are key outcomes.
  - Load tests only: rejected because they do not prove validation and error-schema correctness.
