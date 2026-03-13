# Distributed Rate Limiter

This project demonstrates how production systems enforce request quotas across multiple service instances while maintaining atomic state using Redis.

[![Live Demo](https://img.shields.io/badge/Live%20Demo-Render-46E3B7?logo=render)](https://distributed-rate-limiter-7pn5.onrender.com)

![Java](https://img.shields.io/badge/Java-17-orange)
![Spring Boot](https://img.shields.io/badge/SpringBoot-3.x-green?logo=springboot)
![Redis](https://img.shields.io/badge/Redis-Distributed%20Storage-red?logo=redis)
![Docker](https://img.shields.io/badge/Docker-Containerized-blue?logo=docker)
![Distributed Systems](https://img.shields.io/badge/Architecture-Distributed%20Systems-purple)

---

## Example API Request
```
GET /api/test
```

Example response headers:
```
X-RateLimit-Limit: 3
X-RateLimit-Remaining: 2
X-RateLimit-Reset: 1711212000
```

## Architecture

Client requests pass through a Spring interceptor before reaching the API endpoint.
```
Client
↓
RateLimitInterceptor
↓
RateLimiterService
↓
Redis (Token Bucket State)
```

Redis maintains the bucket state for each user so rate limits remain consistent across distributed services.

---

## Features

- Token Bucket rate limiting algorithm
- Redis-backed distributed state
- Atomic bucket updates using Lua scripts
- Spring Boot request interceptor
- Configurable rate limits via `application.yaml`
- Rate limit response headers
- Dockerized Redis instance

---

## Rate Limiting Headers
```
X-RateLimit-Limit
X-RateLimit-Remaining
X-RateLimit-Reset
```

---

## Configuration

Rate limits can be configured in `application.yml`.

---
## Running the Project

Start Redis with Docker:
```
docker compose up -d
```

Run the Spring Boot service:
```
./mvnw spring-boot:run
```
---

## Testing the Rate Limiter

Example request:
```
curl -i localhost:8080/api/test -H “X-User-Id: user1”
```

## Author

Derek Serrano
