# AGENTS.md

## Project overview

**Taller Automotriz** — Sistema de gestión de órdenes de trabajo para un taller mecánico. Proyecto universitario de Análisis y Diseño de Software I, modelado con **Domain-Driven Design (DDD)** y una arquitectura hexagonal (dominio puro, use cases, adapters de persistencia/web).

```
backend/          # Java 21, Spring Boot, Maven, MariaDB + JPA
frontend/         # HTML/CSS/JS vanilla (SPA), sin framework
database/         # scripts SQL (schema.sql y data.sql)
postman/          # colección Postman para probar la API
db.json           # datos de ejemplo para json-server (mock)
```

## Tech stack

| Componente  | Tecnología |
|-------------|------------|
| Backend     | Java 21, Spring Boot 4.0.6, Maven |
| Persistencia| MariaDB + Spring Data JPA (Hibernate) |
| Frontend    | HTML/CSS/JS vanilla, browser-sync |
| Mock API    | json-server (db.json, puerto 3002) |
| Pruebas     | JUnit (carpeta backend/src/test) |

## Arquitectura del backend

```
backend/src/main/java/com/taller/
├── domain/          # Núcleo DDD: entities, valueobjects, enums, repositories, errors, interfaces, utils (Result)
├── usecases/        # Casos de uso de la aplicación (command objects en dto/)
├── adapters/
│   ├── persistence/jpa/   # Entidades JPA, repositorios e implementaciones concretas
│   └── web/               # Controllers REST (/api/...) y DTOs
└── external/        # Config (AppConfig, CorsConf), exceptions
```

Patrón `Result<T, E>` (domain/utils/Result.java): el dominio no lanza excepciones para errores esperados; cada operación retorna un `Result`.

## Comandos útiles

```bash
# Backend (desde backend/)
./mvnw compile            # compilar
./mvnw test               # correr tests
./mvnw package            # generar JAR
./mvnw spring-boot:run    # arrancar API en http://localhost:8080

# Frontend (desde frontend/)
npm run dev               # browser-sync (servidor estático con live reload)
npm run api               # json-server con db.json en el puerto 3002
```

La API del frontend apunta a `http://localhost:8080/api` (ver `frontend/js/api.js`).

## REGLA CRÍTICA — Proyecto de aprendizaje, SOLO LECTURA

Este es un **proyecto de aprendizaje**. El objetivo del usuario es aprender escribiendo el código él mismo.

- **NO crear, modificar, editar, renombrar ni eliminar ningún archivo del proyecto.** El repositorio es de solo lectura para los agentes.
- **NO ejecutar comandos que alteren el código o el estado del repo** (p. ej. `git commit`, `git push`, instalación de dependencias, generación de archivos).
- Los agentes actúan únicamente como **tutor/guía**: pueden **leer y explorar el proyecto** y **conversar con el usuario**, pero **nunca escribir código por él**.

### Cómo ayudar (lo que SÍ se puede hacer)

1. **Explicar** cómo funciona el proyecto o una parte del código (responder preguntas).
2. **Notificar al usuario qué debe hacer**: dar pasos, instrucciones y recomendaciones concretas para que el propio usuario escriba o corrija el código.
3. **Diagnosticar** errores o problemas leyendo el código y explicando la causa y cómo resolverlo.
4. **Sugerir** qué archivo modificar, qué métodos crear y por qué, dejando que el usuario lo implemente.
5. Al dar guía, mostrar fragmentos de código solo como **referencia didáctica** para que el usuario lo entienda, no como texto para pegar directamente.

Si el usuario pide que le escribas o modifiques el código, recuérdale que el proyecto es de solo lectura y ofrécele guiarlo paso a paso para que lo haga él.
