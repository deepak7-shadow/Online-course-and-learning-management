# Online Course & Learning Management System (LMS)

A complete, role-based online learning platform built with **React 18 + TypeScript + Vite**, **Java 21 + Spring Boot 3**, **Supabase Auth**, and **Supabase PostgreSQL**.

---

## 🚀 Live Demo & Deployment

- **Frontend (Vercel):** Deployable with one click on Vercel (`cd frontend && npm run build`).
- **Backend (Render / Railway):** Java 21 Spring Boot REST API.
- **Database (Supabase PostgreSQL):** Hosted PostgreSQL database with Flyway migrations & Row-Level Security.

---

## 👥 4-Member Work Division & Responsibilities

| Member | Focus | Tech Stack | Branch |
|---|---|---|---|
| **Member 1** | **Student & Public Frontend** | React, TypeScript, Vite, Tailwind CSS, React Router, Axios, Supabase JS | `feature/student-public-frontend` |
| **Member 2** | **Instructor & Admin Frontend** | React, TypeScript, Vite, Tailwind CSS, Recharts, React Router, Axios | `feature/instructor-admin-frontend` |
| **Member 3** | **Java Backend: Auth, Security & Admin** | Java 21, Spring Boot 3, Spring Security, JWT Validation, PostgreSQL, Maven | `feature/supabase-jwt-admin-backend` |
| **Member 4** | **Java Backend: Courses, Learning & DB** | Java 21, Spring Boot 3, Spring Data JPA, Flyway, PostgreSQL | `feature/course-learning-backend` |

---

## 🛠️ Technology Stack

- **Frontend:**
  - React 18 with TypeScript & Vite
  - Tailwind CSS + Lucide Icons + Recharts
  - Supabase Auth SDK (`@supabase/supabase-js`)
  - Axios with JWT Interceptor
  - React Router DOM v6
- **Backend:**
  - Java 21 & Spring Boot 3.3.5
  - Spring Security OAuth2 Resource Server (Supabase JWT cryptographic validation)
  - Spring Data JPA & Hibernate
  - Flyway Database Migrations
  - PostgreSQL Driver
- **Authentication:**
  - Supabase Auth (Sign in, Sign up, Session persistence, Token refresh)

---

## 📂 Repository Structure

```text
online-lms/
├── frontend/                       # React + TypeScript + Vite application
│   ├── public/
│   ├── src/
│   │   ├── components/             # Layout (Navbar, Sidebar, Footer), UI
│   │   ├── context/                # AuthContext (Supabase Auth session)
│   │   ├── features/
│   │   │   ├── auth/               # Login, Register, Forgot Password
│   │   │   ├── student/            # Dashboard, Enrolled Courses, Player, Quiz, Certificates
│   │   │   ├── instructor/         # Dashboard, Course Editor, Quiz Builder, Analytics
│   │   │   ├── admin/              # User Management, Course Approvals, Categories
│   │   │   └── courses/            # Public Catalog, Course Detail
│   │   ├── services/               # Axios API client, Supabase client
│   │   ├── routes/                 # Protected Routes & App Routing
│   │   └── types/                  # TypeScript Data Models
│   ├── package.json
│   ├── vite.config.ts
│   └── vercel.json
├── backend/                        # Java 21 + Spring Boot 3 application
│   ├── src/main/java/com/lms/
│   │   ├── config/                 # SecurityConfig, CorsConfig
│   │   ├── security/               # JwtAuthConverter, SecurityUtils
│   │   ├── entity/                 # UserProfile, Course, Module, Lesson, Quiz, etc.
│   │   ├── repository/             # Spring Data JPA Repositories
│   │   ├── service/                # UserService, AdminService, CourseService, LearningService
│   │   ├── controller/             # REST Endpoints
│   │   └── exception/              # GlobalExceptionHandler
│   ├── src/main/resources/
│   │   ├── application.yml
│   │   └── db/migration/           # Flyway SQL migrations
│   └── pom.xml
├── database/                       # Migrations & schema documentation
│   ├── migrations/
│   └── schema_notes.md
├── docs/                           # Architecture, ERD, API contracts
└── vercel.json                     # Monorepo Vercel build configuration
```

---

## ⚡ Quick Start

### 1. Frontend
```bash
cd frontend
npm install
npm run dev
```

### 2. Backend
```bash
cd backend
mvn clean install
mvn spring-boot:run
```

### 3. Vercel Deployment
1. Connect this repository to **Vercel**.
2. Root Directory: `./` (or `frontend`).
3. Set Environment Variables:
   - `VITE_SUPABASE_URL`: `https://uwlyqosmckfwnmezajrf.supabase.co`
   - `VITE_SUPABASE_ANON_KEY`: `sb_publishable_J04LCXG858cL5qAOobk83A_MOsf8KH_`
   - `VITE_API_BASE_URL`: `http://localhost:8080/api/v1` (or your deployed backend URL)
