
# Task Manager (Option A) - Java Spring Boot (Minimal Frontend)

This project is a minimal production-style Task Manager backend built with:
- Java 17
- Spring Boot 3.x
- JWT authentication (jjwt)
- PostgreSQL (runtime)
- Simple static frontend (HTML + JS) for demonstration

## Run locally (quick steps)
1. Ensure PostgreSQL is running and create database `taskdb` (or change application.properties).
2. Build: `mvn clean package`
3. Run: `java -jar target/task-manager-1.0.0.jar` (or `mvn spring-boot:run`)

## Endpoints
- `POST /auth/register` - register: { "email", "password" }
- `POST /auth/login` - login: { "email", "password" } -> returns { "token" }
- `GET /tasks` - get user's tasks (Authorization: Bearer <token>)
- `POST /tasks` - create task { title, description }
- `PUT /tasks/{id}/done` - mark done
- `DELETE /tasks/{id}` - delete task

## Minimal Frontend
Open `frontend/index.html` and use the UI to register/login and manage tasks (it talks to the same server at :8080).

## Notes
- This is an educational / resume-ready project. Replace JWT secret and configure secure storage for production.
