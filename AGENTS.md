# DropZero Backend - Agent Rules & Instructions

## 0. Role and Rules
You are a senior backend engineer building the API for DropZero, an Android shopping app that sells limited "drops" in India with UPI payments, order tracking, and a rewards "Vault".
- **Phased Development:** Work in phases B0 to B8 (one PR per phase). After each phase: build, lint, and tests must pass, then STOP and summarize.
- **Contract First:** `api/openapi.yaml` is the single source of truth. All request/response types must be generated from or validated against it. Never change or add an endpoint without updating `api/openapi.yaml`.
- **Clean Repository:** No scratch scripts committed. No secrets in the repo. All configuration must come from environment variables, documented in `.env.example`.
- **Scope & Preservation:** The repository contains both the Android client code and backend API code. Do not break or remove existing Android project setup, Gradle configs, or source files while introducing the backend Node/TypeScript application.

## 1. Stack
- **Runtime & Language:** Node LTS, TypeScript (strict mode)
- **Framework:** Fastify
- **Validation & Schemas:** Zod (OpenAPI generated or validated against Zod schemas)
- **Database & ORM:** PostgreSQL with Drizzle ORM and plain SQL migrations
- **Caching & Locks:** Redis (rate limits, short-lived counters, locks)
- **Background Jobs:** BullMQ
- **Testing:** Vitest; integration tests using real Postgres via Testcontainers
- **Containers:** Docker Compose for local Postgres and Redis
- **CI/CD:** GitHub Actions CI (install, lint, typecheck, test, build)

## 2. Conventions (Non-Negotiable)
- **Money:** Store and compute as integers in paise (`BIGINT`). Never use floating point numbers. Server computes all prices, taxes, and totals. Client values are never trusted.
- **Time:** Store all timestamps in UTC. Expose `GET /v1/time`. All drop start/end logic relies strictly on server time.
- **IDs & Pagination:** UUIDs for resource identifiers. Cursor-based pagination.
- **Versioning:** `/v1` prefix for all endpoints. Backward compatibility maintained for older app versions.
- **Error Format:** Standardized `{ code, message, details?, requestId }` with appropriate HTTP status codes and stable machine-readable codes (e.g. `OUT_OF_STOCK`, `DROP_NOT_STARTED`, `PAYMENT_FAILED`, `OTP_INVALID`, `RATE_LIMITED`).
- **Idempotency:** Every `POST` request that creates an order, payment, or reward claim requires an `Idempotency-Key` header.
  - Same key + same payload: returns original response.
  - Same key + different payload: returns an error (`IDEMPOTENCY_CONFLICT`).
- **Logging:** Structured JSON logging with `requestId`. **NEVER** log phone numbers, OTPs, auth/JWT tokens, card details, or payment secrets.
- **Rate Limiting:** Enforced per IP and per account on authentication, order creation, and reward endpoints.

## 3. Data Model Summary
Tables include: `users`, `auth_otps`, `refresh_tokens`, `consents`, `addresses`, `devices`, `products`, `product_variants`, `product_images`, `categories`, `drops`, `drop_items`, `inventory`, `reservations`, `wishlists`, `orders`, `order_items`, `order_events`, `payments`, `refunds`, `webhook_events`, `reward_config`, `reward_grants`, `gem_ledger`, `streaks`, `memberships`, `idempotency_keys`, `audit_log`, `admin_users`.

## 4. Critical Business Logic
- **Checkout & Stock:** Stock reservation is performed in a single database transaction with atomic stock decrement/reservation (`WHERE on_hand - reserved >= qty`). Reservations expire in 10 minutes if unfulfilled.
- **Order State Machine:** Allowed states are `PENDING_PAYMENT`, `PLACED`, `CONFIRMED`, `PACKED`, `SHIPPED`, `OUT_FOR_DELIVERY`, `DELIVERED`, `CANCELLED`. Enforced via a central state transition validator. Every transition writes to `order_events`.
- **Payment Verification:** Server trusts verified webhooks only (`POST /webhooks/payments/{provider}`). Payment amounts are matched against order total in paise.
- **Rewards Engine:** Pure function with injected RNG for deterministic testing. Rolls reward outcome on `POST /rewards/{grantId}/open`. Max 1 grant per paid order. Daily user grant limits and kill switch in `/config`.

## 5. Development Phases
- **B0 Setup:** Repo setup, `AGENTS.md`, Docker Compose, CI workflow, config/logging, health endpoints, complete `api/openapi.yaml`.
- **B1 Database:** Database schemas, Drizzle migrations, seed data.
- **B2 Auth & Profile:** OTP auth flow, short-lived JWTs, rotated refresh tokens, `/me`, consents, device management, account deletion flow.
- **B3 Catalog & Drops:** Home, products, search suggestions, drop status, `/time`, `/config`.
- **B4 Wishlist & Bag:** Wishlist CRUD, addresses CRUD, bag quotation endpoint (`POST /bag/quote`).
- **B5 Orders & Checkout:** Stock reservation, payment intent creation, FakeGateway integration, idempotency, state machine, reservation expiration job.
- **B6 Rewards & Vault:** Vault summary, reward engine, odds, grants, streaks, gems ledger, daily limit, kill switch.
- **B7 Fulfilment & Admin:** Courier webhooks, simulator, push notification triggers, role-based admin endpoints, audit logging.
- **B8 Hardening & Load Test:** Razorpay sandbox integration, refunds, Redis waiting room, rate limits, k6 load testing, error tracking, backup notes.
