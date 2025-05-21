# 🔗 URL Shortener Kotlin API

This is a lightweight, production-ready MVP for a URL shortening service built with **Kotlin**, **Spring Boot**, and **Redis**.

It provides RESTful APIs to:
- Shorten a long URL into a unique, short hash.
- Retrieve the original long URL from the short hash.

---

## 🚀 Tech Stack

| Layer        | Tech                                      |
|--------------|-------------------------------------------|
| Language     | Kotlin (JVM)                              |
| Framework    | Spring Boot                               |
| Storage      | Redis (in-memory DB)                      |
| Validation   | Jakarta Validation)                       |
| Build Tool   | Gradle                                    |
| Logging      | SLF4J + LoggerFactory                     |

---

## 📦 Features

✅ Shorten any valid URL using `POST /api/shorten`  
✅ Retrieve full URL using `GET /api/{hash}`  
✅ Base62 encoding for compact URL hash  
✅ URL format validation  
✅ Redis-backed persistence  
✅ Global exception handler for clean API responses  
✅ Production-ready structure and logging

---

## 🔧 Getting Started

### 🔁 Prerequisites

- Java 17+
- Kotlin 1.9+
- Redis server running locally (default: `localhost:6379`)
- Gradle

### ▶️ Run the App

```bash
./gradlew bootRun
```

### API Endpoints
- Shorten URL
  - Request:

```bash
POST /api/shorten
Content-Type: application/json

{
  "url": "https://example.com"
}
```
  - Response:
    
```bash
"https://short.ly/abc123"
```
- Resolve URL
    - Request:
```bash
  GET /api/abc123
```
  - Response:
    
```bash
{
    "url": "https://example.com"
}
```
If hash not found:
```bash
"URL not found"
```

## Base62 Encoding Logic
 - Uses Redis' atomic increment (INCR) on a global url_counter.
 - Encodes the numeric ID into Base62 using alphanumeric characters.
 - Maps encoded key → original URL in Redis.

## Production-Readiness & Scalability Discussion

This MVP follows clean architecture principles with a modular controller/service/DTO structure. It uses Spring Boot for scalable, production-ready backend services and Redis for fast and atomic ID mapping.

- Clean, maintainable Kotlin codebase with validation and logging.
- Stateless API supports horizontal scaling.
- Redis + Base62 ID generation ensures globally unique and compact identifiers.
- Validation with @Pattern and @NotBlank ensures input safety.
- Environment configuration can be externalized using application.properties/profiles, making it suitable for multi-environment deployment (dev/stage/prod).
- Logging using SLF4J adds observability.
- Global exception handler improves reliability and client error handling.
- Potential Bottlenecks & Solutions: Redis is a single point of failure in MVP; in production, use Redis Cluster or fallback caching layer.




