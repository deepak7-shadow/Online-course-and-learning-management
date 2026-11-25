import React, { useState } from 'react';
import { useParams, useNavigate } from 'react-router-dom';
import { courseApi } from '@/services/api/courseApi';
import { mockQuiz } from '@/services/api/mockData';
import { Quiz, QuizResult } from '@/types/quiz';
import { Card, CardContent } from '@/components/ui/Card';
import { Button } from '@/components/ui/Button';
import { Badge } from '@/components/ui/Badge';
import { HelpCircle, CheckCircle2, XCircle, ArrowLeft } from 'lucide-react';

export const QuizView: React.FC = () => {
  const { courseId, lessonId } = useParams<{ courseId: string; lessonId: string }>();
  const navigate = useNavigate();
  const [quiz] = useState<Quiz>(mockQuiz);
  const [selectedAnswers, setSelectedAnswers] = useState<Record<string, string>>({});
  const [result, setResult] = useState<QuizResult | null>(null);
  const [isSubmitting, setIsSubmitting] = useState(false);

  const handleSelect = (questionId: string, optionId: string) => {
    setSelectedAnswers(prev => ({ ...prev, [questionId]: optionId }));
  };

  const handleSubmit = async () => {
    setIsSubmitting(true);
    try {
      const res = await courseApi.submitQuiz(quiz.id, selectedAnswers);
      setResult(res);
    } finally {
      setIsSubmitting(false);
    }
  };

  return (
    <div className="max-w-4xl mx-auto px-4 py-10">
      <button
        onClick={() => navigate(`/learn/${courseId}`)}
        className="inline-flex items-center gap-1.5 text-xs text-slate-500 hover:text-slate-800 mb-6 cursor-pointer"
      >
        <ArrowLeft className="w-4 h-4" /> Return to Course Player
      </button>

      <Card className="p-8 shadow-xl">
        <div className="border-b border-slate-100 pb-6 mb-8">
          <Badge variant="primary" className="mb-2">Assessment</Badge>
          <h1 className="text-2xl font-bold text-slate-900">{quiz.title}</h1>
          <p className="text-sm text-slate-500 mt-1">{quiz.description}</p>
        </div>

        {result ? (
          <div className="text-center py-8 space-y-4">
            {result.passed ? (
              <CheckCircle2 className="w-16 h-16 text-emerald-500 mx-auto" />
            ) : (
              <XCircle className="w-16 h-16 text-rose-500 mx-auto" />
            )}
            <h2 className="text-2xl font-bold text-slate-900">
              {result.passed ? 'Assessment Passed!' : 'Need More Practice'}
            </h2>
            <p className="text-lg font-semibold text-slate-700">
              Score: <span className={result.passed ? 'text-emerald-600' : 'text-rose-600'}>{result.score}%</span>
            </p>
            <p className="text-sm text-slate-500 max-w-md mx-auto">{result.message}</p>
            <div className="pt-6">
              <Button onClick={() => navigate(`/learn/${courseId}`)} className="cursor-pointer">
                Back to Curriculum
              </Button>
            </div>
          </div>
        ) : (
          <div className="space-y-8">
            {quiz.questions.map((q, idx) => (
              <div key={q.id} className="p-6 bg-slate-50 rounded-xl border border-slate-200">
                <p className="font-semibold text-slate-900 mb-4 text-base">
                  {idx + 1}. {q.questionText}
                </p>
                <div className="space-y-2.5">
                  {q.options.map((opt) => {
                    const isSelected = selectedAnswers[q.id] === opt.id;
                    return (
                      <button
                        type="button"
                        key={opt.id}
                        onClick={() => handleSelect(q.id, opt.id)}
                        className={`w-full p-3.5 text-left rounded-lg text-sm border font-medium transition-all cursor-pointer ${
                          isSelected
                            ? 'bg-brand-50 border-brand-500 text-brand-900 shadow-sm'
                            : 'bg-white border-slate-200 text-slate-700 hover:bg-slate-100'
                        }`}
                      >
                        {opt.optionText}
                      </button>
                    );
                  })}
                </div>
              </div>
            ))}

            <div className="pt-4 flex justify-end">
              <Button
                onClick={handleSubmit}
                isLoading={isSubmitting}
                size="lg"
                className="cursor-pointer font-bold"
              >
                Submit Answers
              </Button>
            </div>
          </div>
        )}
      </Card>
    </div>
  );
};
