# courses-mvc-jdbc

## Engineering Standards

- Repository constitution: `.specify/memory/constitution.md`
- Primary engineering standard: `skills/backend/SKILL.md`
- Keep commits focused and document key decisions in spec/plan artifacts.

    
## Comandos

```bash
./mvnw spring-boot:run
```

```bash
curl "http://localhost:8080/courses?limit=100"
```

```bash
BASE_URL=http://localhost:8080 LIMIT=100 MAX_VUS=1000 \
k6 run --summary-export=summary-mvc-loom-pool30-100l-1000vu-simple.json k6-simple.js | tee run-mvc-loom-pool30-100l-1000vu-simple.txt
```
