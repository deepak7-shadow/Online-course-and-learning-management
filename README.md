# Online Course & Learning Management System (LMS)

A role-based online learning platform with Student, Instructor, and Admin experiences.

---

## Technology Stack

- **Frontend:** React.js, TypeScript, Vite, Tailwind CSS, Shadcn UI, React Router, Axios, Supabase JS
- **Backend:** Java 21, Spring Boot 3.x, Spring Security (Supabase JWT verification), Spring Data JPA, PostgreSQL, Flyway, Maven
- **Authentication & Database:** Supabase Auth & Supabase PostgreSQL
- **Media / Storage:** Cloudinary (optional)
- **Deployment:** Vercel (Frontend), Render / Railway (Backend)

---

## 4-Member Work Division

- **Member 1 — Student & Public Frontend:**
  - Public landing page, navigation, course catalog & filters, student auth (Supabase JS), student dashboard, course player, quizzes, progress tracking.
  - Branch: `feature/student-public-frontend`

- **Member 2 — Instructor & Admin Frontend:**
  - Instructor dashboard, course editor, module/lesson builder, quiz builder, course analytics (Recharts), admin user management, course approvals.
  - Branch: `feature/instructor-admin-frontend`

- **Member 3 — Java Backend (Supabase Auth, JWT Security & Admin):**
  - Supabase JWT verification with Spring Security, user profile mapping, role-based authorization (STUDENT, INSTRUCTOR, ADMIN), admin APIs.
  - Branch: `feature/supabase-jwt-admin-backend`

- **Member 4 — Java Backend (Courses, Learning & Supabase PostgreSQL):**
  - Course, category, module, lesson entities & APIs, enrollment, progress tracking, quiz grading, certificate generation.
  - Branch: `feature/course-learning-backend`

---

## Project Structure

```text
online-lms/
├── frontend/               # React + TypeScript + Vite application
│   ├── public/
│   ├── src/
│   │   ├── assets/
│   │   ├── components/     # common, layout, ui
│   │   ├── context/
│   │   ├── features/       # admin, auth, courses, instructor, student
│   │   ├── hooks/
│   │   ├── lib/
│   │   ├── routes/
│   │   ├── services/       # api, supabase
│   │   └── types/
│   └── ...
├── backend/                # Java Spring Boot 3.x application
│   ├── src/
│   │   ├── main/
│   │   │   ├── java/com/lms/
│   │   │   │   ├── config/
│   │   │   │   ├── controller/
│   │   │   │   ├── dto/
│   │   │   │   ├── entity/
│   │   │   │   ├── enums/
│   │   │   │   ├── exception/
│   │   │   │   ├── repository/
│   │   │   │   ├── security/
│   │   │   │   └── service/
│   │   │   └── resources/
│   │   └── test/
│   └── pom.xml
├── database/               # Database migrations & schema notes
│   ├── migrations/
│   └── schema_notes.md
├── docs/                   # Project documentation & contracts
│   ├── architecture.md
│   ├── erd.md
│   ├── api-contracts.md
│   ├── team-work-division.md
│   └── setup-guide.md
├── .env.example
├── .gitignore
└── README.md
```

---

## Branching & Collaboration Guidelines

1. Always branch off updated `main`:
   ```bash
   git switch main
   git pull origin main
   git switch -c feature/<your-feature-name>
   ```
2. Commit in small, meaningful increments.
3. Open a Pull Request to merge into `main` after review and testing.
