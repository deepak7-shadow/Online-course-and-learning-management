# REST API Contracts

All endpoints return JSON responses with standard envelope format:
```json
{
  "success": true,
  "message": "Operation completed successfully",
  "data": { ... },
  "timestamp": "2026-11-25T14:30:00Z"
}
```

## 1. Authentication & Users (`/api/v1/users`)
- `GET /api/v1/users/me`: Retrieve current user profile from JWT claims.
- `PUT /api/v1/users/me`: Update profile (name, bio, avatar).
- `GET /api/v1/users/instructors/{id}`: Public instructor bio and course list.

## 2. Public Catalog (`/api/v1/public`)
- `GET /api/v1/public/courses`: Search & filter courses (`category`, `level`, `search`, `page`, `size`, `sort`).
- `GET /api/v1/public/courses/{idOrSlug}`: Course details, curriculum outline, reviews summary.
- `GET /api/v1/public/categories`: All active course categories and tree hierarchy.

## 3. Instructor APIs (`/api/v1/instructor`)
- `GET /api/v1/instructor/courses`: List instructor's authored courses.
- `POST /api/v1/instructor/courses`: Create new draft course.
- `PUT /api/v1/instructor/courses/{id}`: Update course details.
- `POST /api/v1/instructor/courses/{id}/submit`: Submit course for admin review.
- `POST /api/v1/instructor/courses/{courseId}/modules`: Add curriculum module.
- `POST /api/v1/instructor/modules/{moduleId}/lessons`: Add lesson (video, article, quiz).
- `POST /api/v1/instructor/lessons/{lessonId}/quiz`: Attach quiz with questions and options.
- `GET /api/v1/instructor/analytics`: Earnings, total enrollments, average ratings.

## 4. Student Learning APIs (`/api/v1/learning`)
- `GET /api/v1/learning/enrollments`: List current student enrollments with progress.
- `POST /api/v1/learning/courses/{courseId}/enroll`: Enroll in course (free or paid).
- `GET /api/v1/learning/courses/{courseId}/player`: Full course content player (all lessons).
- `POST /api/v1/learning/lessons/{lessonId}/progress`: Mark lesson completed / update timestamp.
- `POST /api/v1/learning/quizzes/{quizId}/submit`: Submit quiz answers and receive score.
- `GET /api/v1/learning/certificates/{code}`: Verify and fetch certificate.
- `GET /api/v1/learning/wishlist`: List user wishlist items.
- `POST /api/v1/learning/wishlist/{courseId}`: Add/remove from wishlist.

## 5. Admin APIs (`/api/v1/admin`)
- `GET /api/v1/admin/dashboard`: Platform metrics (total users, active enrollments, revenue, pending approvals).
- `GET /api/v1/admin/users`: Paginated user list with role and status filters.
- `PUT /api/v1/admin/users/{id}/status`: Suspend or activate user account.
- `PUT /api/v1/admin/users/{id}/role`: Elevate or change user role.
- `GET /api/v1/admin/courses/pending`: List submitted courses awaiting review.
- `POST /api/v1/admin/courses/{id}/approve`: Approve or reject submitted course with comments.
- `POST /api/v1/admin/categories`: Create or update category.
