# courses-mvc-jpa

Proyecto de ejemplo para exponer una API de cursos con Spring Boot y persistencia relacional, incluyendo pruebas automatizadas y scripts de carga con k6.

## Requisitos

- Java 21 (o la versión definida en `pom.xml`)
- Maven Wrapper (incluido en el repositorio como `./mvnw`)
- k6 (opcional, para pruebas de carga)

## Engineering Standards

- Repository constitution: `.specify/memory/constitution.md`
- Primary engineering standard: `skills/backend/SKILL.md`
- Keep commits focused and document key decisions in spec/plan artifacts.

## Ejecutar la aplicación

```bash
./mvnw spring-boot:run
```

## Probar endpoint principal

```bash
curl "http://localhost:8080/courses?limit=100"
```

## Ejecutar tests

Ejecutar toda la suite de pruebas:

```bash
./mvnw test
```

Ejecutar una clase de test específica:

```bash
./mvnw -Dtest=CourseServiceUnitTest test
```

Ejecutar build completo con validaciones:

```bash
./mvnw clean verify
```

## Pruebas de carga (k6)

Desde la raíz del repositorio:

```bash
BASE_URL=http://localhost:8080 LIMIT=100 MAX_VUS=1000 \
k6 run --summary-export=k6/summary-mvc-loom-pool30-100l-1000vu-simple.json k6/k6-simple.js | tee k6/run-mvc-loom-pool30-100l-1000vu-simple.txt
```
