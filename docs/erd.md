# Entity Relationship Diagram & Data Models

## Core Tables Overview

| Table | Purpose |
|---|---|
| `auth.users` | Managed by Supabase Auth (identities & credentials). |
| `profiles` | Application profile, name, avatar, role (`STUDENT`, `INSTRUCTOR`, `ADMIN`), account status. ID references `auth.users(id)`. |
| `student_profiles` | Student-specific profile information. |
| `instructor_profiles` | Instructor bio, headline, qualifications. |
| `categories` | Course categories/disciplines. |
| `courses` | Course details, title, description, price, status (`DRAFT`, `PENDING_APPROVAL`, `PUBLISHED`, `REJECTED`), instructor UUID. |
| `modules` | Course chapters/sections. |
| `lessons` | Individual lessons within a module (video, text, document). |
| `lesson_resources` | Supplementary files, PDFs, links. |
| `enrollments` | Student course enrollment records and enrollment date. |
| `lesson_progress` | Completed status, last accessed timestamp, watch progress. |
| `quizzes` | Assessment metadata, passing marks, time limits. |
| `questions` | Quiz questions, options, correct answer keys. |
| `quiz_attempts` | Student submissions, answers, scores, pass/fail status. |
| `certificates` | Issued certificates with unique verification identifiers. |
| `reviews` | Course ratings (1-5) and written feedback. |
| `wishlists` | Saved courses per student. |
| `notifications` | User notifications and activity alerts. |
| `course_approvals` | Admin review history, comments, and decision timestamps. |

## Relationships
- `profiles.id` 1:1 `auth.users.id`
- `profiles` 1:1 `student_profiles`
- `profiles` 1:1 `instructor_profiles`
- `categories` 1:N `courses`
- `profiles` (Instructor) 1:N `courses`
- `courses` 1:N `modules`
- `modules` 1:N `lessons`
- `lessons` 1:N `lesson_resources`
- `lessons` 1:N `quizzes`
- `quizzes` 1:N `questions`
- `profiles` (Student) N:M `courses` (via `enrollments`)
- `enrollments` 1:N `lesson_progress`
- `quizzes` 1:N `quiz_attempts`

