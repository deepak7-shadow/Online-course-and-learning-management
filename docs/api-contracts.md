# API Contracts & Endpoints Specification

## Base URL
- Local: `http://localhost:8080/api/v1`

## Standard Request Headers
```http
Authorization: Bearer <Supabase_JWT_Token>
Content-Type: application/json
```

## Standard Response Wrapper
```json
{
  "success": true,
  "message": "Operation completed successfully",
  "data": {},
  "timestamp": "2026-11-12T12:00:00Z"
}
```

## Core Endpoint Groups

### 1. User & Profile APIs
- `GET /api/v1/users/me` - Get current authenticated user profile
- `PUT /api/v1/users/me` - Update current profile
- `POST /api/v1/users/sync` - Sync Supabase user with public profile

### 2. Admin APIs (Requires ADMIN role)
- `GET /api/v1/admin/users` - List all users (paginated, filtered by role)
- `PATCH /api/v1/admin/users/{userId}/status` - Activate or suspend user account
- `GET /api/v1/admin/courses/pending` - List courses pending review
- `POST /api/v1/admin/courses/{courseId}/approval` - Approve or reject course
- `POST /api/v1/admin/categories` - Create new category
- `PUT /api/v1/admin/categories/{categoryId}` - Update category

### 3. Public & Student Course APIs
- `GET /api/v1/courses` - Search/filter published courses
- `GET /api/v1/courses/{courseId}` - Get course details
- `POST /api/v1/courses/{courseId}/enroll` - Enroll in course
- `GET /api/v1/student/enrollments` - Get student's enrolled courses
- `GET /api/v1/student/courses/{courseId}/progress` - Get student course progress
- `POST /api/v1/student/lessons/{lessonId}/complete` - Mark lesson as complete

### 4. Instructor Course Management APIs (Requires INSTRUCTOR role)
- `GET /api/v1/instructor/courses` - Get instructor's created courses
- `POST /api/v1/instructor/courses` - Create new course draft
- `PUT /api/v1/instructor/courses/{courseId}` - Update course
- `POST /api/v1/instructor/courses/{courseId}/submit` - Submit course for admin review
- `POST /api/v1/instructor/courses/{courseId}/modules` - Add module to course
- `POST /api/v1/instructor/modules/{moduleId}/lessons` - Add lesson to module
- `GET /api/v1/instructor/courses/{courseId}/analytics` - Get course performance analytics

### 5. Quiz & Assessment APIs
- `GET /api/v1/quizzes/{quizId}` - Get quiz questions (for student)
- `POST /api/v1/quizzes/{quizId}/submit` - Submit answers and calculate score
- `GET /api/v1/quizzes/{quizId}/attempts` - Get past attempts and scores
