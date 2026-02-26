# Data Model — Refactor to Idiomatic Reactive Data Flow

## Entity: Course
- Purpose: Core domain object returned by `GET /courses`.
- Fields:
  - `id: Long` (required, immutable identifier)
  - `code: String` (required, non-empty)
  - `title: String` (required, non-empty)
  - `description: String` (optional)
  - `level: String` (required)
  - `durationH: Integer` (optional, non-negative when present)
  - `active: Boolean` (required)
  - `createdAt: OffsetDateTime` (required)
- Validation rules:
  - Returned records must map consistently from persistence to API shape.
  - Ordering is deterministic according to repository query contract.
- Relationships:
  - Read-only in this feature scope; no child aggregates are introduced.

## Entity: CourseListRequest
- Purpose: Captures API input constraints for course retrieval.
- Fields:
  - `limit: Integer` (default `100` when omitted)
- Validation rules:
  - `limit` must be greater than `0`.
  - `limit` must be less than or equal to `1000`.
- State transitions:
  - `Received` → `Validated` → (`Rejected` | `Processed`).

## Entity: ApiErrorResponse
- Purpose: Standardized error payload for validation and unexpected failures.
- Fields:
  - `code: String` (required, stable machine-readable category)
  - `message: String` (required, client-safe description)
  - `timestamp: OffsetDateTime` (required, generation time)
  - `path: String` (required, request URI path)
- Validation rules:
  - All four fields must be present in every error response covered by this feature.
  - Payload must not expose stack traces, SQL statements, or internal class names.
- State transitions:
  - `ErrorDetected` → `MappedToApiErrorResponse` → `SerializedAndReturned`.

## Entity: CourseStreamResult
- Purpose: Reactive representation of course list retrieval output.
- Fields:
  - `items: Flux<Course>` (logical stream; serialized as existing list response shape)
- Validation rules:
  - Stream completion and serialization preserve strict API compatibility for clients.
- State transitions:
  - `QueryStarted` → `RowsMapped` → `ResponseWritten`.
