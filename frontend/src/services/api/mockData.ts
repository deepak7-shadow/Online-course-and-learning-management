import { Category, CourseDetail, CourseSummary, Enrollment, Certificate } from '@/types/course';
import { Quiz } from '@/types/quiz';
import { UserProfile } from '@/types/auth';

export const mockCategories: Category[] = [
  { id: 'cat-1', name: 'Software Development', slug: 'software-development', iconName: 'Code', description: 'Full-stack, frontend, backend, and mobile software engineering.' },
  { id: 'cat-2', name: 'Cloud & DevOps', slug: 'cloud-devops', iconName: 'Cloud', description: 'AWS, Azure, Docker, Kubernetes, CI/CD, and infrastructure.' },
  { id: 'cat-3', name: 'Data Science & AI', slug: 'data-science-ai', iconName: 'Cpu', description: 'Machine learning, deep learning, Python, and analytics.' },
  { id: 'cat-4', name: 'Cybersecurity', slug: 'cybersecurity', iconName: 'Shield', description: 'Ethical hacking, network security, and defense engineering.' },
  { id: 'cat-5', name: 'UI/UX & Product Design', slug: 'ui-ux-design', iconName: 'Palette', description: 'Figma, user research, wireframing, and visual design.' },
  { id: 'cat-6', name: 'Business & Leadership', slug: 'business-leadership', iconName: 'Briefcase', description: 'Project management, Agile, Scrum, and tech entrepreneurship.' }
];

export const mockCourses: CourseDetail[] = [
  {
    id: 'c-101',
    title: 'Modern Full-Stack React & Spring Boot Masterclass',
    slug: 'modern-full-stack-react-spring-boot',
    subtitle: 'Build robust enterprise cloud applications with React 18, TypeScript, Spring Boot 3 & Supabase',
    description: 'Master modern full-stack development from scratch. This comprehensive curriculum takes you through building scalable REST APIs with Java 21, Spring Boot 3, and Spring Security, coupled with a high-performance React 18 frontend leveraging Vite, Tailwind CSS, and Supabase authentication.',
    categoryId: 'cat-1',
    categoryName: 'Software Development',
    instructorId: 'u-inst-1',
    instructorName: 'Sarah Jenkins',
    instructorBio: 'Principal Software Engineer & Cloud Architect with 12+ years of enterprise experience.',
    price: 89.99,
    level: 'INTERMEDIATE',
    status: 'APPROVED',
    thumbnailUrl: 'https://images.unsplash.com/photo-1517694712202-14dd9538aa97?w=800&auto=format&fit=crop&q=60',
    promoVideoUrl: 'https://www.youtube.com/watch?v=dQw4w9WgXcQ',
    totalLessons: 18,
    totalDurationMinutes: 420,
    averageRating: 4.9,
    enrollmentCount: 1420,
    createdAt: '2026-09-01T10:00:00Z',
    modules: [
      {
        id: 'mod-1',
        courseId: 'c-101',
        title: 'Module 1: Architecture & Environment Setup',
        description: 'Understand the multi-tier architecture, Supabase schema setup, and local developer setup.',
        orderIndex: 1,
        lessons: [
          {
            id: 'les-1',
            moduleId: 'mod-1',
            title: 'Welcome & System Architecture Overview',
            contentType: 'VIDEO',
            videoUrl: 'https://commondatastorage.googleapis.com/gtv-videos-bucket/sample/BigBuckBunny.mp4',
            durationMinutes: 14,
            orderIndex: 1,
            isFreePreview: true,
            isCompleted: true
          },
          {
            id: 'les-2',
            moduleId: 'mod-1',
            title: 'Setting up PostgreSQL & Supabase Auth',
            contentType: 'ARTICLE',
            contentText: 'In this section, we walk through configuring PostgreSQL tables, row-level security policies, and generating service tokens for Spring Boot.',
            durationMinutes: 20,
            orderIndex: 2,
            isFreePreview: true,
            isCompleted: true
          }
        ]
      },
      {
        id: 'mod-2',
        courseId: 'c-101',
        title: 'Module 2: Building Resilient REST APIs with Spring Boot 3',
        description: 'Deep dive into Spring Data JPA, JWT validation, custom exception handling, and service layers.',
        orderIndex: 2,
        lessons: [
          {
            id: 'les-3',
            moduleId: 'mod-2',
            title: 'Configuring OAuth2 Resource Server & Supabase JWKS',
            contentType: 'VIDEO',
            videoUrl: 'https://commondatastorage.googleapis.com/gtv-videos-bucket/sample/ElephantsDream.mp4',
            durationMinutes: 28,
            orderIndex: 1,
            isFreePreview: false,
            isCompleted: false
          },
          {
            id: 'les-4',
            moduleId: 'mod-2',
            title: 'Module Knowledge Check: Spring Security',
            contentType: 'QUIZ',
            durationMinutes: 15,
            orderIndex: 2,
            isFreePreview: false,
            isCompleted: false
          }
        ]
      }
    ]
  },
  {
    id: 'c-102',
    title: 'Cloud Native DevOps on Azure & Kubernetes (AKS)',
    slug: 'cloud-native-devops-azure-kubernetes',
    subtitle: 'From Docker containers to multi-region production Kubernetes clusters with GitOps and Terraform',
    description: 'Learn how modern high-growth companies ship code reliably. Cover Docker optimization, Helm charts, automated CI/CD pipelines via GitHub Actions, and production-grade monitoring with Azure Monitor and Prometheus.',
    categoryId: 'cat-2',
    categoryName: 'Cloud & DevOps',
    instructorId: 'u-inst-2',
    instructorName: 'David Zhang',
    instructorBio: 'DevOps Lead & Certified Kubernetes Administrator (CKA).',
    price: 94.99,
    level: 'ADVANCED',
    status: 'APPROVED',
    thumbnailUrl: 'https://images.unsplash.com/photo-1667372393119-3d4c48d07fc9?w=800&auto=format&fit=crop&q=60',
    totalLessons: 24,
    totalDurationMinutes: 510,
    averageRating: 4.8,
    enrollmentCount: 980,
    createdAt: '2026-08-15T12:00:00Z',
    modules: []
  },
  {
    id: 'c-103',
    title: 'Practical Deep Learning & Generative AI Systems',
    slug: 'practical-deep-learning-generative-ai',
    subtitle: 'Build LLM applications, RAG pipelines, fine-tuned transformer models, and AI agent swarms',
    description: 'Understand modern AI from foundational embeddings to enterprise Retrieval-Augmented Generation (RAG) and autonomous agent workflows.',
    categoryId: 'cat-3',
    categoryName: 'Data Science & AI',
    instructorId: 'u-inst-1',
    instructorName: 'Sarah Jenkins',
    instructorBio: 'Principal Software Engineer & Cloud Architect with 12+ years of enterprise experience.',
    price: 119.99,
    level: 'ALL_LEVELS',
    status: 'APPROVED',
    thumbnailUrl: 'https://images.unsplash.com/photo-1620712943543-bcc4688e7485?w=800&auto=format&fit=crop&q=60',
    totalLessons: 32,
    totalDurationMinutes: 720,
    averageRating: 4.95,
    enrollmentCount: 2310,
    createdAt: '2026-07-20T08:00:00Z',
    modules: []
  }
];

export const mockQuiz: Quiz = {
  id: 'quiz-1',
  lessonId: 'les-4',
  title: 'Spring Security & OAuth2 Assessment',
  description: 'Test your understanding of JWT tokens, claims, and role-based access control.',
  passingPercentage: 75,
  questions: [
    {
      id: 'q-1',
      questionText: 'What algorithm is commonly used for asymmetric verification of Supabase JWTs via JWKS?',
      questionType: 'MULTIPLE_CHOICE',
      points: 1,
      options: [
        { id: 'opt-1', optionText: 'RS256 (RSA Signature with SHA-256)', isCorrect: true },
        { id: 'opt-2', optionText: 'HS256 (HMAC with SHA-256)', isCorrect: false },
        { id: 'opt-3', optionText: 'MD5 Hashing', isCorrect: false },
        { id: 'opt-4', optionText: 'AES-128-CBC', isCorrect: false }
      ]
    },
    {
      id: 'q-2',
      questionText: 'In Spring Security 6, which filter chain method configures stateless session management?',
      questionType: 'MULTIPLE_CHOICE',
      points: 1,
      options: [
        { id: 'opt-5', optionText: 'SessionCreationPolicy.STATELESS', isCorrect: true },
        { id: 'opt-6', optionText: 'SessionCreationPolicy.ALWAYS', isCorrect: false },
        { id: 'opt-7', optionText: 'http.sessionManagement().disabled()', isCorrect: false },
        { id: 'opt-8', optionText: 'csrf.disableState()', isCorrect: false }
      ]
    },
    {
      id: 'q-3',
      questionText: 'True or False: Role-Based Access Control (RBAC) in Spring Security expects the prefix "ROLE_" by default.',
      questionType: 'TRUE_FALSE',
      points: 1,
      options: [
        { id: 'opt-9', optionText: 'True', isCorrect: true },
        { id: 'opt-10', optionText: 'False', isCorrect: false }
      ]
    }
  ]
};

export const mockEnrollments: Enrollment[] = [
  {
    id: 'enr-1',
    course: mockCourses[0],
    progressPercentage: 65,
    status: 'ACTIVE',
    enrolledAt: '2026-10-10T09:00:00Z'
  },
  {
    id: 'enr-2',
    course: mockCourses[1],
    progressPercentage: 100,
    status: 'COMPLETED',
    enrolledAt: '2026-09-01T14:30:00Z',
    completedAt: '2026-10-01T18:00:00Z',
    certificateCode: 'CERT-EDU-843192'
  }
];

export const mockCertificates: Certificate[] = [
  {
    id: 'cert-1',
    certificateCode: 'CERT-EDU-843192',
    studentName: 'Alex Mercer',
    courseTitle: 'Cloud Native DevOps on Azure & Kubernetes (AKS)',
    instructorName: 'David Zhang',
    issuedAt: '2026-10-01T18:00:00Z'
  }
];

export const mockDemoUser: UserProfile = {
  id: 'u-demo-1',
  email: 'student@eduflow.io',
  fullName: 'Alex Mercer',
  avatarUrl: 'https://images.unsplash.com/photo-1534528741775-53994a69daeb?w=200&auto=format&fit=crop&q=80',
  role: 'STUDENT',
  accountStatus: 'ACTIVE',
  bio: 'Full-stack developer looking to level up cloud engineering skills.',
  headline: 'Aspiring Cloud & Distributed Systems Engineer',
  createdAt: '2026-08-01T00:00:00Z'
};
