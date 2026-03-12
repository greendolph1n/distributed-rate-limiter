# Distributed Rate Limiter

A distributed API rate limiter built with **Java, Spring Boot, Redis, and Lua scripts** implementing the **Token Bucket algorithm**.

This project demonstrates how production systems enforce request quotas across multiple service instances while maintaining atomic state using Redis.

---

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
