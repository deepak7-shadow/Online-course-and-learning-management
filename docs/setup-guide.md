# Setup and Deployment Guide

## 1. Prerequisites
- **Node.js**: v18.0.0 or higher (v20+ recommended)
- **Java**: OpenJDK 21 LTS or higher
- **PostgreSQL**: v15+ or Supabase hosted database
- **Git**

## 2. Environment Variables

Create `.env` in the root (and `frontend/.env` for frontend):
```ini
# Supabase Configuration
VITE_SUPABASE_URL=https://uwlyqosmckfwnmezajrf.supabase.co
VITE_SUPABASE_ANON_KEY=sb_publishable_J04LCXG858cL5qAOobk83A_MOsf8KH_
VITE_API_BASE_URL=http://localhost:8080/api/v1

# Backend Configuration (application-dev.yml or environment)
SPRING_DATASOURCE_URL=jdbc:postgresql://db.uwlyqosmckfwnmezajrf.supabase.co:5432/postgres
SPRING_DATASOURCE_USERNAME=postgres
SPRING_DATASOURCE_PASSWORD=YourPasswordHere
SUPABASE_JWT_ISSUER_URI=https://uwlyqosmckfwnmezajrf.supabase.co/auth/v1
SUPABASE_JWK_SET_URI=https://uwlyqosmckfwnmezajrf.supabase.co/auth/v1/.well-known/jwks.json
```

## 3. Database Migration
Apply SQL migrations to Supabase via SQL Editor:
1. `database/migrations/V1__initial_schema.sql` (Creates 18 tables, constraints, indexes, triggers)
2. `database/migrations/V2__seed_data.sql` (Inserts categories and sample taxonomy)

## 4. Running the Frontend Locally
```bash
cd frontend
npm install
npm run dev
```
Open http://localhost:5173 to explore the full LMS.

## 5. Running the Backend Locally
```bash
cd backend
./mvnw spring-boot:run
```
The API starts on http://localhost:8080.

## 6. Deploying to Vercel
This repository is pre-configured with `vercel.json` for seamless 1-click monorepo deployment:
- Root directory: `./`
- Framework Preset: `Vite`
- Build Command: `cd frontend && npm install && npm run build`
- Output Directory: `frontend/dist`
- Environment Variables in Vercel Project Settings:
  - `VITE_SUPABASE_URL`: `https://uwlyqosmckfwnmezajrf.supabase.co`
  - `VITE_SUPABASE_ANON_KEY`: `sb_publishable_J04LCXG858cL5qAOobk83A_MOsf8KH_`
  - `VITE_API_BASE_URL`: (Optional backend URL, defaults to mock fallback)
