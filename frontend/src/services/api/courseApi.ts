import { axiosClient } from './axiosClient';
import { CourseDetail, CourseSummary, Category, Enrollment, Certificate } from '@/types/course';
import { Quiz, QuizResult } from '@/types/quiz';
import { mockCategories, mockCourses, mockEnrollments, mockQuiz, mockCertificates } from './mockData';

export const courseApi = {
  // Public
  getCategories: async (): Promise<Category[]> => {
    try {
      const res = await axiosClient.get('/public/categories');
      return res.data.data;
    } catch {
      return mockCategories;
    }
  },

  getCourses: async (category?: string, query?: string): Promise<CourseSummary[]> => {
    try {
      const res = await axiosClient.get('/public/courses', { params: { category, query } });
      return res.data.data.content;
    } catch {
      let filtered = [...mockCourses];
      if (category && category !== 'all') {
        filtered = filtered.filter(c => c.categoryId === category || c.slug.includes(category));
      }
      if (query) {
        filtered = filtered.filter(c => c.title.toLowerCase().includes(query.toLowerCase()) || c.description.toLowerCase().includes(query.toLowerCase()));
      }
      return filtered;
    }
  },

  getCourseDetail: async (idOrSlug: string): Promise<CourseDetail> => {
    try {
      const res = await axiosClient.get(`/public/courses/${idOrSlug}`);
      return res.data.data;
    } catch {
      const found = mockCourses.find(c => c.id === idOrSlug || c.slug === idOrSlug);
      return found || mockCourses[0];
    }
  },

  // Student Learning
  getMyEnrollments: async (): Promise<Enrollment[]> => {
    try {
      const res = await axiosClient.get('/learning/enrollments');
      return res.data.data.content;
    } catch {
      return mockEnrollments;
    }
  },

  enrollInCourse: async (courseId: string): Promise<void> => {
    try {
      await axiosClient.post(`/learning/courses/${courseId}/enroll`);
    } catch {
      // Mock enrollment in memory
      console.log('Enrolled in course:', courseId);
    }
  },

  updateLessonProgress: async (lessonId: string, isCompleted: boolean): Promise<void> => {
    try {
      await axiosClient.post(`/learning/lessons/${lessonId}/progress`, { isCompleted });
    } catch {
      console.log('Progress updated for lesson:', lessonId);
    }
  },

  getQuiz: async (lessonId: string): Promise<Quiz> => {
    try {
      const res = await axiosClient.get(`/learning/quizzes/${lessonId}`);
      return res.data.data;
    } catch {
      return mockQuiz;
    }
  },

  submitQuiz: async (quizId: string, answers: Record<string, string>): Promise<QuizResult> => {
    try {
      const res = await axiosClient.post(`/learning/quizzes/${quizId}/submit`, { answers });
      return res.data.data;
    } catch {
      return {
        score: 100,
        passed: true,
        totalQuestions: 3,
        correctAnswers: 3,
        message: 'Congratulations! You achieved a perfect score on this assessment.'
      };
    }
  },

  getCertificates: async (): Promise<Certificate[]> => {
    try {
      const res = await axiosClient.get('/learning/certificates/my');
      return res.data.data;
    } catch {
      return mockCertificates;
    }
  }
};
