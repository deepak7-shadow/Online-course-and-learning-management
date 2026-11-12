# 4-Member Work Division

## MEMBER 1 — Student & Public Frontend
- **Technology:** React, TypeScript, Vite, Tailwind CSS, Shadcn UI, React Router, Axios, Supabase JS
- **Branch:** `feature/student-public-frontend`
- **Key Responsibilities:**
  - Create the public landing page, navigation, footer, about/contact sections and responsive layout.
  - Build the course catalogue with search, category filters, sorting and course-detail pages.
  - Build student registration, sign-in, sign-out, password-reset and email-verification screens.
  - Integrate Supabase Auth using `@supabase/supabase-js`. Handle the user session and obtain current access tokens.
  - Configure the shared Axios layer to attach `Authorization: Bearer <Supabase access token>` to protected Java API requests.
  - Build the student dashboard, enrolled courses, course learning page/lesson player, lesson progress, quiz interface, quiz results, certificates, wishlist and profile/settings.
  - Implement student-facing protected routes and role-aware navigation.
  - Integrate with Member 3 and Member 4 APIs.
  - Test responsive design, form validation, loading states, API errors and complete student journey.

---

## MEMBER 2 — Instructor & Admin Frontend
- **Technology:** React, TypeScript, Vite, Tailwind CSS, Shadcn UI, React Router, Axios, Recharts
- **Branch:** `feature/instructor-admin-frontend`
- **Key Responsibilities:**
  - Create instructor and admin dashboards with responsive layouts and reusable components.
  - Build instructor profile and course creation/editing forms, category selection, course thumbnail/media upload UI, and draft/publish/submit-for-approval screens.
  - Build module, lesson and resource management interfaces.
  - Build quiz builder screens for creating questions, options, correct answers, marks and settings.
  - Build enrolled-student lists, learner progress views and course analytics using Recharts.
  - Build admin screens for managing students/instructors, activating/suspending accounts, managing categories and approving/rejecting courses.
  - Build review moderation, reports and notification screens if included in MVP.
  - Use common Supabase session/auth layer and Axios client to call protected Java APIs.
  - Implement role-aware UI and test instructor/admin workflows.

---

## MEMBER 3 — Java Backend: Supabase Auth, JWT Security & Admin
- **Technology:** Java 21, Spring Boot 3.x, Spring Security, Spring Web, Spring Validation, Spring Data JPA, PostgreSQL, Maven
- **Branch:** `feature/supabase-jwt-admin-backend`
- **Key Responsibilities:**
  - Create the Spring Boot backend project structure: controller, service, repository, entity, DTO, security, config and exception packages.
  - Integrate Supabase-issued JWT validation with Spring Security (validate signature using Supabase signing key/JWKS, issuer, expiration, audience/claims).
  - Extract authenticated Supabase user UUID from verified JWT subject (`sub`) and make available to application services.
  - Do NOT create a second password login system or store passwords in the LMS database.
  - Coordinate secure profile-creation strategy and user mapping (`profiles.id` matching `auth.users.id`). Never allow self-assigned ADMIN roles.
  - Implement Spring Security authorization for `STUDENT`, `INSTRUCTOR`, and `ADMIN` roles, plus account-status checks.
  - Build profile APIs and admin APIs: list/manage users, activate/suspend accounts, manage categories, and approve/reject courses.
  - Implement DTOs, validation, centralized exception handling, CORS configuration, API documentation and security tests.

---

## MEMBER 4 — Java Backend: Courses, Learning & Supabase PostgreSQL
- **Technology:** Java 21, Spring Boot 3.x, Spring Web, Spring Data JPA/Hibernate, PostgreSQL, Maven, Flyway
- **Branch:** `feature/course-learning-backend`
- **Key Responsibilities:**
  - Configure Spring Boot to connect to Supabase PostgreSQL using environment variables.
  - Design and maintain LMS database schema and Flyway migrations (UUID identifiers where appropriate).
  - Create entities/repositories and APIs for Course, Category, Module, Lesson and LessonResource.
  - Implement course CRUD, catalogue, search, filters, instructor ownership checks, draft/publish and approval workflows.
  - Implement student enrollment, lesson access, lesson completion, progress tracking and resume-learning APIs.
  - Implement Quiz, Question, QuizAttempt, answer submission, scoring and result APIs.
  - Implement certificate records and generation/verification flow.
  - Implement reviews/ratings, wishlist and course analytics if included in MVP.
  - Integrate media upload/storage metadata (store large files in Cloudinary/object storage).
  - Write JUnit/Mockito/MockMvc tests for business logic, database access and ownership-sensitive operations.

