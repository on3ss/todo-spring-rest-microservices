# 🧩 Microservices Todo App – Spring Boot

A distributed **Todo application** built using **Spring Boot**, designed to explore microservices architecture, container orchestration, and service communication patterns.

This project emphasizes:

* Service decomposition
* API Gateway routing
* Service discovery (Eureka)
* JWT-based authentication
* Isolated databases per service
* Containerized deployment with Docker Compose

---

# 🏗 Architecture Overview

The system follows a classic microservices architecture:

```
Client
   ↓
Gateway Service (8080)
   ↓
Auth Service (8081)
Todo Service (8082)
   ↓
PostgreSQL (isolated per service)
```

All services register with a **Discovery Server (Eureka)** and communicate over a shared Docker bridge network.

---

# 🧱 Services

## 🔎 Discovery Server

* **Port:** `8761`
* **Technology:** Spring Cloud Netflix Eureka
* **Responsibility:**

  * Service registry
  * Service health monitoring
* Health endpoint:

  ```
  http://localhost:8761/actuator/health
  ```

All services register themselves on startup.

---

## 🔐 Auth Service

* **Port:** `8081`
* **Responsibilities:**

  * User authentication
  * JWT generation
  * Credential validation
* **Database:** `postgres-auth`
* **Environment variables:**

  * `JWT_SECRET`
  * `SPRING_DATASOURCE_URL`
  * `SPRING_DATASOURCE_USERNAME`
  * `SPRING_DATASOURCE_PASSWORD`

---

## 📝 Todo Service

* **Port:** `8082`
* **Responsibilities:**

  * Todo CRUD operations
  * Business logic enforcement
* **Database:** `postgres-todo`
* **Isolated database per service** (microservices best practice)

---

## 🌐 Gateway Service

* **Port:** `8080`
* **Responsibilities:**

  * Entry point for all client requests
  * Route forwarding
  * JWT validation
  * Service discovery integration

All client requests should go through:

```
http://localhost:8080/api/{service}
```

Example:

```
http://localhost:8080/api/auth/login
http://localhost:8080/api/todos
```

---

# 🗄 Database Layer

Each microservice owns its database.

| Service | Database Container | Volume    |
| ------- | ------------------ | --------- |
| Auth    | postgres-auth      | auth_data |
| Todo    | postgres-todo      | todo_data |

* Image: `postgres:15-alpine`
* Persistent storage via Docker volumes
* No shared database between services

---

# 🐳 Docker Compose Setup

All services are defined in `docker-compose.yml`.

## Network

All containers run inside:

```
todo-net (bridge network)
```

Internal service communication uses container names:

```
http://discovery-server:8761
jdbc:postgresql://postgres-auth:5432/...
jdbc:postgresql://postgres-todo:5432/...
```

---

## Startup Order & Health Checks

Service dependencies are controlled using `depends_on`.

* `auth-service` waits for:

  * discovery-server (healthy)
  * postgres-auth (started)

* `todo-service` waits for:

  * discovery-server (healthy)
  * postgres-todo (started)

* `gateway-service` waits for:

  * discovery-server (healthy)

The discovery server includes a health check:

```yaml
healthcheck:
  test: ["CMD", "wget", "--no-verbose", "--tries=1", "--spider", "http://localhost:8761/actuator/health"]
  interval: 10s
  retries: 5
  start_period: 30s
```

---

# 📦 Prerequisites

* Docker
* Java 21

---

# 🚀 Running the Application

## Build & Start Entire Stack

From project root:

```bash
./mvnw clean package && docker compose up --build -d
```

This will:

* Build all modules
* Create Docker images
* Start all services
* Attach databases
* Register services with Eureka

---

## Build & Start a Single Service

```bash
./mvnw clean package -am {service-name} && docker compose up {service-name} --build -d
```

Example:

```bash
./mvnw clean package -am auth-service && docker compose up auth-service --build -d
```

---

# 🔐 Environment Configuration

Create a `.env` file in the project root:

```
# Auth Database
AUTH_POSTGRES_DB_NAME=authdb
AUTH_POSTGRES_USERNAME=authuser
AUTH_POSTGRES_PASSWORD=authpass
AUTH_POSTGRES_PORT=5433

# Todo Database
TODO_POSTGRES_DB_NAME=tododb
TODO_POSTGRES_USERNAME=todouser
TODO_POSTGRES_PASSWORD=todopass
TODO_POSTGRES_PORT=5434

# Security
JWT_SECRET=your-super-secret-key
```

---

# 📂 Project Structure

```
root/
 ├── discovery-server/
 ├── auth-service/
 ├── todo-service/
 ├── gateway-service/
 ├── docker-compose.yml
 ├── pom.xml
 └── .env
```

---

# 🛣 Roadmap

* [x] Enable hot reloading inside Docker
* [ ] Implement Role-Based Access Control (RBAC)
* [ ] Add attachment uploads (SFTP + async processing)
* [ ] Introduce automated tests (move toward TDD)
* [ ] Add OpenAPI documentation
* [ ] Centralized logging & tracing
* [ ] Follow outbox pattern
* [ ] CI/CD pipeline

---

# 🎯 Design Principles Applied

* Database per service
* Externalized configuration
* Service discovery
* API gateway pattern
* Containerized deployment
* Fault-aware startup order

