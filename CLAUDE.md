# CLAUDE.md

This file provides guidance to Claude Code (claude.ai/code) when working with code in this repository.

## Commands

```bash
# Build
./mvnw clean package

# Run all tests
./mvnw test

# Run a single test class
./mvnw test -Dtest=ClassName

# Run a single test method
./mvnw test -Dtest=ClassName#methodName

# Run the application
./mvnw spring-boot:run
```

## Prerequisites

MySQL must be running on `localhost:3307` with database `tododb`, username `root`, password `secret`. Schema is auto-managed by Hibernate (`ddl-auto=update`).

Swagger UI is available at `http://localhost:8080/docs`.

## Architecture

Layered Spring Boot REST API with JWT authentication:

```
Controller → Service → Repository → MySQL
```

**Package layout** (`com.luv2code.springboot.todos`):
- `controller/` — REST endpoints (`AuthenticationController`, `UserController`, plus `TodoController` in progress)
- `service/` — Business logic interfaces + implementations (`AuthenticationServiceImpl`, `UserServiceImpl`, `TodoServiceImpl`, `JwtServiceImpl`)
- `repository/` — Spring Data JPA interfaces (`UserRepository`, `TodoRepository`)
- `entity/` — JPA domain models (`User`, `Todo`, `Authority`)
- `request/` / `response/` — DTOs for inbound/outbound data
- `config/` — `SecurityConfig`, `JwtAuthenticationFilter`, `SwaggerConfig`
- `exception/` — Global exception handling
- `util/` — `FindAuthenticatedUser` extracts the current user from the `SecurityContext`

## Key Behaviors

**JWT auth:** All endpoints except `/api/auth/**` require `Authorization: Bearer <token>`. Tokens expire in 15 minutes. Secret and expiration are configured in `application.properties`.

**Role assignment:** The first registered user gets `ROLE_ADMIN` + `ROLE_EMPLOYEE`; all subsequent users get only `ROLE_EMPLOYEE`. The service prevents deletion of the last admin.

**Todo ownership:** `Todo` has a many-to-one relationship to `User`. The `FindAuthenticatedUser` util is used in service methods to associate todos with the currently authenticated user.
