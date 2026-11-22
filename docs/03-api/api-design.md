# HelixURL — REST API Design

**Document ID:** HLX-API-001  
**Version:** 0.1.0  
**Status:** Approved — M2 Design Baseline  
**Milestone:** M2 — System Design  
**Last Updated:** 2026-11-22

## 1. Purpose

Define the MVP HTTP contract between browser/API clients and the Spring Boot backend. The machine-readable contract is maintained in [`api-specification.yaml`](./api-specification.yaml).

## 2. Design Principles

- Use JSON over HTTPS for public creation operations.
- Separate the versioned management API (`/api/v1`) from root-level short-link redirects.
- Return HTTP 201 only after successful persistence.
- Return HTTP 302 for eligible redirects, and HTTP 404 for unknown, expired, or disabled links.
- Keep internal database IDs and operational state out of public responses.
- Use RFC 9457-style Problem Details for structured errors.
- Avoid exposing unauthenticated administrative endpoints.
- Treat the approved OpenAPI contract as the implementation baseline; review material changes through version control.

## 3. Endpoint Inventory

| Method | Path | Purpose | Access |
|---|---|---|---|
| POST | `/api/v1/urls` | Create an anonymous short URL | Public |
| GET | `/{shortCode}` | Redirect an eligible mapping | Public |
| GET | `/actuator/health` | Application health | Operational; exposure details TBD |
| Internal CLI | Not a public HTTP endpoint | Disable mapping | Restricted operator access |

The health endpoint is a Spring Boot Actuator operational interface and is intentionally not specified as part of the public OpenAPI contract. Operator disabling remains a required MVP capability but will not be published as an unauthenticated API.

## 4. Create URL

**Endpoint:** `POST /api/v1/urls`  
**Content-Type:** `application/json`

Request example:

```json
{
  "originalUrl": "https://example.com/articles/42",
  "expiresAt": "2026-12-01T10:00:00Z"
}
```

`expiresAt` may be omitted. The accepted expiration must be in the future and no more than 365 days after the defined creation-time reference. Accepted timestamps must follow RFC 3339 with an explicit UTC offset and second-level precision; values without an offset are rejected.

**Success:** `201 Created`, JSON body and `Location` header containing the absolute short URL.

```json
{
  "shortCode": "aB3xY9k2",
  "shortUrl": "https://helixurl.example/aB3xY9k2",
  "originalUrl": "https://example.com/articles/42",
  "createdAt": "2026-11-22T03:00:00Z",
  "expiresAt": "2026-12-01T10:00:00Z"
}
```

When no expiration is scheduled, the response always includes `"expiresAt": null`. `shortUrl` is constructed from a trusted configured public origin, not from an untrusted request `Host` header.

**Non-idempotency:** Repeating an identical POST may create another mapping. A client retry after a timeout may create a duplicate mapping if the original database commit succeeded.

## 5. Resolve URL

**Endpoint:** `GET /{shortCode}`

For an existing, enabled, non-expired mapping:

```http
HTTP/1.1 302 Found
Location: https://example.com/articles/42
Cache-Control: no-store
```

For unknown, disabled, or expired mappings:

```http
HTTP/1.1 404 Not Found
Cache-Control: no-store
```

A mapping expires when `now >= expiresAt`. Both HTTP 302 redirects and HTTP 404 unavailable-link responses include `Cache-Control: no-store` (BD-012).

The `/api` and `/actuator` namespaces are reserved (BD-011); frontend/static routes will be added when selected. The root redirect handler must match only valid eight-character Base62 codes.

## 6. Validation

The application validates:

- JSON structure and required `originalUrl`.
- Absolute HTTP or HTTPS URL syntax.
- A maximum submitted URL length of 2,048 characters.
- Destination host and IP literal safety restrictions.
- Rejection of URL userinfo credentials.
- Optional RFC 3339 expiration timestamps with explicit UTC offset and second-level precision.
- Expiration strictly after the creation-time reference and no later than 365 days after that reference.
- A maximum creation request body size of 8,192 bytes.
- A creation rate limit of 10 requests per minute per identified client.

The application must not fetch destination webpage content as part of URL creation or redirection.

## 7. Error Contract

Use `application/problem+json` with `type`, `title`, `status`, `detail`, `instance`, and extension field `errorCode` where applicable.

```json
{
  "type": "about:blank",
  "title": "Bad Request",
  "status": 400,
  "detail": "The destination URL must use HTTP or HTTPS.",
  "instance": "/api/v1/urls",
  "errorCode": "INVALID_DESTINATION_URL"
}
```

| Status | Suggested `errorCode` | Condition |
|---|---|---|
| 400 | `INVALID_REQUEST` | Malformed JSON or invalid request shape |
| 400 | `INVALID_DESTINATION_URL` | Destination fails validation |
| 400 | `INVALID_EXPIRATION` | Invalid expiration |
| 404 | `SHORT_URL_NOT_FOUND` | Unknown, expired, or disabled mapping |
| 413 | `REQUEST_TOO_LARGE` | Configured payload limit exceeded |
| 429 | `RATE_LIMIT_EXCEEDED` | Configured creation limit exceeded |
| 500 | `INTERNAL_ERROR` | Unexpected server error |
| 503 | `SERVICE_UNAVAILABLE` | Required dependency unavailable |

The response must not disclose stack traces, SQL details, credentials, internal identifiers, or why a particular mapping was disabled.

## 8. Operational and Security Boundaries

- Creation and redirect endpoints are anonymous.
- No public edit, delete, listing, analytics, or user account endpoints exist in the MVP.
- Operator disabling uses a restricted internal maintenance command; no unauthenticated public administrative API is exposed.
- Health check exposure must avoid revealing sensitive dependency details to the public.
- In public deployment, HTTPS and a trusted configured public origin are required.
- Approved rate limiting, request size, destination safety and caching policies must be implemented and verified before public release.

## 9. Implementation Notes

- Spring MVC controllers should delegate to application services.
- Request DTOs must not be reused as JPA entities.
- Prefer explicit response DTOs and a central exception-to-Problem-Details handler.
- Use `Clock` for deterministic expiration tests.
- Persist successfully before returning `201`.
- Database unique constraints remain authoritative for short-code collisions.
- Retry only confirmed short-code uniqueness collisions, with a maximum of five persistence attempts and a rolled-back transaction before each retry.

## 10. Contract Verification

Before implementing endpoints, verify the OpenAPI file parses and all examples match the schema. During implementation, test at minimum: valid creation, no-expiry creation, malformed JSON, invalid scheme, URL length boundary, expiration boundary, duplicate destination, short-code collision, 302 with Location, and 404 for unknown/expired/disabled mappings.

## 11. Remaining Implementation Details

The M2 API policies have been approved. Before the dependent implementation or public release, verify:

1. Unicode URL length-counting and PostgreSQL compatibility.
2. URI edge cases (IDN, IPv6 literals and special-use addresses).
3. Trusted proxy/client identification and rate-limit behaviour.
4. Enforcement of the 8,192-byte JSON body limit before unbounded buffering.
5. Operator CLI access controls and auditability.
6. Frontend/static route reservations and production hosting.
7. Retention and deletion policy (BD-009).

The response always includes `expiresAt`, using `null` when expiration is not configured. HTTP 302 and HTTP 404 responses both include `Cache-Control: no-store`.

## 12. Document History

| Version | Date | Description |
|---|---|---|
| 0.1.0 | 2026-11-22 | Initial REST API design proposal |
