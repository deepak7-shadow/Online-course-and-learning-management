# Database Schema & Implementation Notes

This document describes the relational database design implemented for PostgreSQL via Supabase.

## 1. Schema Conventions
- All primary keys use UUID (`gen_random_uuid()`) for decentralized, secure ID generation.
- All timestamps use `TIMESTAMP WITH TIME ZONE` defaulting to `CURRENT_TIMESTAMP`.
- Audit columns (`created_at`, `updated_at`) are maintained automatically via database triggers.
- Soft-delete semantics are optionally supported on courses and user profiles where applicable, or enforced via lifecycle statuses.

## 2. Table Summary

| Table | Purpose | Key Relationships |
|---|---|---|
| `user_profiles` | User account metadata & role management | Extends Supabase `auth.users(id)` |
| `categories` | Course category hierarchy & categorization | Self-referencing (`parent_id`) |
| `courses` | Course catalog, pricing, levels, lifecycle | Belongs to `user_profiles` (instructor) & `categories` |
| `course_approvals` | Admin review history & audit trail for course publishing | Belongs to `courses` & `user_profiles` (admin reviewer) |
| `modules` | Chapter/module division of a course | Belongs to `courses` (cascade delete) |
| `lessons` | Granular instructional units (videos, text, quizzes) | Belongs to `modules` (cascade delete) |
| `lesson_resources`| Attached PDFs, code samples, external links | Belongs to `lessons` |
| `enrollments` | Student course registrations | Links `user_profiles` (student) and `courses` |
| `lesson_progress` | Granular lesson completion tracking | Belongs to `enrollments` and `lessons` |
| `quizzes` | Assessments attached to lessons or courses | Belongs to `lessons` |
| `questions` | Quiz questions (multiple choice, true/false) | Belongs to `quizzes` |
| `question_options`| Selectable choices for questions | Belongs to `questions` |
| `quiz_attempts` | Student quiz submission logs and scores | Belongs to `enrollments` and `quizzes` |
| `quiz_answers` | Granular answers submitted per attempt | Belongs to `quiz_attempts` and `questions` |
| `certificates` | Issued completion credentials with validation codes | Belongs to `enrollments` |
| `reviews` | Star ratings and student comments | Belongs to `courses` and `user_profiles` |
| `wishlists` | Saved courses for later enrollment | Belongs to `user_profiles` and `courses` |
| `audit_logs` | Security, role changes, and admin action audit logs | Belongs to `user_profiles` |

## 3. RLS (Row Level Security) Policies
- `user_profiles`: Users can read their own profile; public users can view instructor bios; admins can view/edit all.
- `courses`: Published courses are publicly readable. Draft/submitted courses are visible only to the authoring instructor or admins.
- `enrollments`: Students can only read/manage their own enrollments; instructors can view enrollments for their courses.
- `lesson_progress`: Students can only view and update their own progress.
- `certificates`: Publicly verifiable via unique certificate code.
