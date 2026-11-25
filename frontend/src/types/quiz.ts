export type QuestionType = 'MULTIPLE_CHOICE' | 'TRUE_FALSE';

export interface QuestionOption {
  id: string;
  optionText: string;
  isCorrect?: boolean;
}

export interface Question {
  id: string;
  questionText: string;
  questionType: QuestionType;
  points: number;
  options: QuestionOption[];
}

export interface Quiz {
  id: string;
  lessonId: string;
  title: string;
  description?: string;
  passingPercentage: number;
  questions: Question[];
}

export interface QuizResult {
  score: number;
  passed: boolean;
  totalQuestions: number;
  correctAnswers: number;
  message: string;
}
