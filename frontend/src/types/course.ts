export type CourseLevel = 'BEGINNER' | 'INTERMEDIATE' | 'ADVANCED' | 'ALL_LEVELS';
export type CourseStatus = 'DRAFT' | 'SUBMITTED' | 'APPROVED' | 'REJECTED' | 'ARCHIVED';
export type ContentType = 'VIDEO' | 'ARTICLE' | 'QUIZ';

export interface Category {
  id: string;
  name: string;
  slug: string;
  description?: string;
  iconName?: string;
}

export interface Lesson {
  id: string;
  moduleId: string;
  title: string;
  contentType: ContentType;
  videoUrl?: string;
  contentText?: string;
  durationMinutes: number;
  orderIndex: number;
  isFreePreview: boolean;
  isCompleted?: boolean;
}

export interface Module {
  id: string;
  courseId: string;
  title: string;
  description?: string;
  orderIndex: number;
  lessons: Lesson[];
}

export interface CourseSummary {
  id: string;
  title: string;
  slug: string;
  subtitle?: string;
  categoryName: string;
  instructorName: string;
  price: number;
  level: CourseLevel;
  status: CourseStatus;
  thumbnailUrl?: string;
  totalLessons?: number;
  totalDurationMinutes?: number;
  averageRating?: number;
  enrollmentCount?: number;
}

export interface CourseDetail extends CourseSummary {
  description: string;
  categoryId: string;
  instructorId: string;
  instructorBio?: string;
  promoVideoUrl?: string;
  modules: Module[];
  createdAt: string;
}

export interface Enrollment {
  id: string;
  course: CourseSummary;
  progressPercentage: number;
  status: 'ACTIVE' | 'COMPLETED' | 'DROPPED';
  enrolledAt: string;
  completedAt?: string;
  certificateCode?: string;
}

export interface Certificate {
  id: string;
  certificateCode: string;
  studentName: string;
  courseTitle: string;
  instructorName: string;
  issuedAt: string;
  pdfUrl?: string;
}
