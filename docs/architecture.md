# System Architecture

## Overview
The Online Course & Learning Management System utilizes a modern decoupled architecture:

```
[ React + TypeScript + Vite Frontend ]
              │
              ├── 1. Sign in / Sign up
              ▼
    [ Supabase Auth ]
              │
              ├── 2. Issues Access-Token JWT
              ▼
[ React Frontend / Axios Interceptor ]
              │
              ├── 3. Sends API requests with Authorization: Bearer <JWT>
              ▼
[ Java Spring Boot 3 Backend ]
   ├── Spring Security validates JWT cryptographically (Supabase JWKS / Secret)
   ├── Extracts UUID from JWT `sub` claim
   ├── Enforces Role (STUDENT / INSTRUCTOR / ADMIN) and resource ownership
   ▼
[ Spring Data JPA / Hibernate ]
              │
              ├── 4. Database queries
              ▼
[ Supabase PostgreSQL Database ]
```

## Security & Auth Principles
1. **Single Source of Auth:** Supabase Auth manages identities and passwords. Spring Boot does NOT store passwords or issue secondary JWTs.
2. **Cryptographic JWT Verification:** Backend cryptographically verifies the JWT signature against Supabase, ensuring issuer, expiration, and audience claims are valid.
3. **Identity Mapping:** User profiles in the public `profiles` table use the same UUID as Supabase `auth.users(id)`.
4. **Defense in Depth:** UI role checks tailor the user experience, while Spring Boot strictly enforces authorization rules on all endpoints.

