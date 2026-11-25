# Architecture Overview

The Online Course & Learning Management System (LMS) is structured as a decoupled, modern enterprise full-stack application.

```mermaid
graph TD
    Client[React 18 + Vite SPA Client]
    Vercel[Vercel Edge Hosting / CDN]
    SupabaseAuth[Supabase Auth / GoTrue]
    SpringBoot[Spring Boot 3 REST API]
    SupabaseDB[(Supabase PostgreSQL 15+)]
    Storage[Supabase Storage Buckets]

    Client -->|Static Delivery| Vercel
    Client -->|Auth Signup/Login & Tokens| SupabaseAuth
    Client -->|Bearer JWT Requests| SpringBoot
    Client -->|Direct File Uploads / Assets| Storage
    SpringBoot -->|JWT Verification (JWK Set)| SupabaseAuth
    SpringBoot -->|JPA / Hibernate JDBC Pool| SupabaseDB
    SupabaseAuth -->|Triggers / auth.users| SupabaseDB
```

## System Components

### 1. Frontend Layer (Members 1 & 2)
- **Framework**: React 18 with TypeScript and Vite.
- **Styling**: Tailwind CSS with custom enterprise design system.
- **Routing**: React Router DOM v6 with route guards for authenticated students, instructors, and system administrators.
- **State & Data**: React Context for Auth, Axios with automatic Bearer token injection, and graceful fallback to offline mock data for instant previewing.
- **Deployment**: Optimized for Vercel with single-page app (SPA) fallback rewrites in `vercel.json`.

### 2. Backend Layer (Members 3 & 4)
- **Framework**: Spring Boot 3.3.x on Java 21 LTS.
- **Security**: Spring Security 6 with OAuth2 Resource Server validating Supabase-issued asymmetric JWTs via JWKS.
- **Data Access**: Spring Data JPA / Hibernate with PostgreSQL dialect and HikariCP connection pooling.
- **Architecture**: Domain-Driven Layered Architecture (Controller -> Service -> Repository -> Entity).
- **Validation & Exception Handling**: JSR-380 Bean Validation with global RFC-7807 compliant error responses.

### 3. Database & Auth Layer (Supabase)
- **PostgreSQL 15+**: Managed relational database hosted on Supabase with foreign keys, compound indexes, and audit timestamps.
- **Supabase Auth**: Manages identity, email verification, passwords, and issues RS256/ES256 signed JWTs containing user UUIDs and claims.
