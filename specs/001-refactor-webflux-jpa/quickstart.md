# Quickstart — Refactor to Idiomatic Reactive Data Flow

## 1) Prerequisites
- Java 21
- Docker or local PostgreSQL with dataset compatible with existing `courses` table
- Maven Wrapper (`./mvnw`)
- Optional for load benchmark: k6 CLI

## 2) Run the application
```bash
export DB_R2DBC_URL='r2dbc:postgresql://localhost:5432/coursesdb'
export DB_USERNAME='appuser'
export DB_PASSWORD='change-me'
./mvnw spring-boot:run
```

## 3) Verify primary flow (P1)
```bash
curl "http://localhost:8080/courses?limit=100"
```
Expected:
- HTTP 200
- Response shape remains compatible with existing clients
- Maximum 100 records returned

## 4) Verify validation and error schema (P2)
```bash
curl -i "http://localhost:8080/courses?limit=0"
curl -i "http://localhost:8080/courses?limit=1001"
```
Expected for both:
- Client error status (4xx)
- JSON body with fields: `code`, `message`, `timestamp`, `path`

## 5) Verify empty-result behavior
- With an empty `courses` dataset:
```bash
curl -i "http://localhost:8080/courses?limit=100"
```
Expected:
- HTTP 200
- Empty collection response (not an error)

## 6) Run automated tests
```bash
./mvnw test
```
Expected:
- Context and endpoint tests pass
- Validation/error-schema assertions pass

Latest local verification:
- `./mvnw test -q` completed successfully on 2026-02-26

## 7) Run comparative load benchmark
Use existing k6 scripts to compare before/after behavior.

Example:
```bash
BASE_URL=http://localhost:8080 LIMIT=100 MAX_VUS=1000 k6 run --summary-export=summary-webflux-r2dbc-10l-1000vu.json k6/k6-simple.js
```
Expected:
- Meets success criteria targets in spec (`SC-001`, `SC-002`)
- No contract regression in successful responses

Suggested benchmark artifacts to track:
- `k6/summary-webflux-r2dbc-10l-1000vu.json`
- `k6/run-webflux-r2dbc-10l-1000vu.txt`
