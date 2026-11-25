import React from 'react';
import { Routes, Route, Navigate } from 'react-router-dom';
import { MainLayout } from '@/components/layout/MainLayout';
import { ProtectedRoute } from './ProtectedRoute';

// Public & Catalog
import { PublicLanding } from '@/features/courses/PublicLanding';
import { CourseCatalog } from '@/features/courses/CourseCatalog';
import { CourseDetailView } from '@/features/courses/CourseDetail';

// Auth
import { LoginView } from '@/features/auth/Login';
import { RegisterView } from '@/features/auth/Register';

// Student
import { StudentDashboard } from '@/features/student/StudentDashboard';
import { CoursePlayer } from '@/features/student/CoursePlayer';
import { QuizView } from '@/features/student/QuizView';
import { CertificatesView } from '@/features/student/CertificatesView';
import { StudentProfile } from '@/features/student/StudentProfile';

// Instructor
import { InstructorDashboard } from '@/features/instructor/InstructorDashboard';
import { CourseEditor } from '@/features/instructor/CourseEditor';

// Admin
import { AdminDashboard } from '@/features/admin/AdminDashboard';
import { UserManagement } from '@/features/admin/UserManagement';
import { CourseApprovals } from '@/features/admin/CourseApprovals';

export const AppRoutes: React.FC = () => {
  return (
    <Routes>
      {/* Public Pages with MainLayout */}
      <Route element={<MainLayout />}>
        <Route path="/" element={<PublicLanding />} />
        <Route path="/courses" element={<CourseCatalog />} />
        <Route path="/courses/:slug" element={<CourseDetailView />} />
        <Route path="/login" element={<LoginView />} />
        <Route path="/register" element={<RegisterView />} />
        <Route path="/certificates/:code" element={<CertificatesView />} />

        {/* Student Routes */}
        <Route element={<ProtectedRoute allowedRoles={['STUDENT', 'INSTRUCTOR', 'ADMIN']} />}>
          <Route path="/student" element={<StudentDashboard />} />
          <Route path="/student/certificates" element={<CertificatesView />} />
          <Route path="/student/profile" element={<StudentProfile />} />
        </Route>

        {/* Instructor Routes */}
        <Route element={<ProtectedRoute allowedRoles={['INSTRUCTOR', 'ADMIN']} />}>
          <Route path="/instructor" element={<InstructorDashboard />} />
          <Route path="/instructor/courses/new" element={<CourseEditor />} />
          <Route path="/instructor/courses/edit/:id" element={<CourseEditor />} />
        </Route>

        {/* Admin Routes */}
        <Route element={<ProtectedRoute allowedRoles={['ADMIN']} />}>
          <Route path="/admin" element={<AdminDashboard />} />
          <Route path="/admin/users" element={<UserManagement />} />
          <Route path="/admin/approvals" element={<CourseApprovals />} />
        </Route>
      </Route>

      {/* Standalone Player (Full Screen Experience) */}
      <Route element={<ProtectedRoute allowedRoles={['STUDENT', 'INSTRUCTOR', 'ADMIN']} />}>
        <Route path="/learn/:courseId" element={<CoursePlayer />} />
        <Route path="/learn/:courseId/quiz/:lessonId" element={<QuizView />} />
      </Route>

      {/* Catch-all redirect */}
      <Route path="*" element={<Navigate to="/" replace />} />
    </Routes>
  );
};
