import React, { useEffect, useState } from 'react';
import { useParams, useNavigate, Link } from 'react-router-dom';
import { courseApi } from '@/services/api/courseApi';
import { CourseDetail } from '@/types/course';
import { Button } from '@/components/ui/Button';
import { Card, CardContent } from '@/components/ui/Card';
import { Badge } from '@/components/ui/Badge';
import { formatCurrency, formatDuration } from '@/lib/utils';
import { Star, Clock, CheckCircle2, Play, Lock, Award, ShieldCheck, ArrowRight } from 'lucide-react';

export const CourseDetailView: React.FC = () => {
  const { slug } = useParams<{ slug: string }>();
  const navigate = useNavigate();
  const [course, setCourse] = useState<CourseDetail | null>(null);
  const [isEnrolling, setIsEnrolling] = useState(false);

  useEffect(() => {
    if (slug) {
      courseApi.getCourseDetail(slug).then(setCourse);
    }
  }, [slug]);

  if (!course) {
    return (
      <div className="min-h-[50vh] flex items-center justify-center">
        <div className="animate-spin rounded-full h-8 w-8 border-b-2 border-brand-600"></div>
      </div>
    );
  }

  const handleEnroll = async () => {
    setIsEnrolling(true);
    try {
      await courseApi.enrollInCourse(course.id);
      navigate(`/learn/${course.id}`);
    } finally {
      setIsEnrolling(false);
    }
  };

  return (
    <div className="pb-20">
      {/* Header Banner */}
      <div className="bg-slate-900 text-white py-14">
        <div className="max-w-7xl mx-auto px-4 sm:px-6 lg:px-8">
          <div className="grid grid-cols-1 lg:grid-cols-3 gap-12 items-center">
            <div className="lg:col-span-2 space-y-4">
              <Badge variant="primary" className="bg-brand-500/20 text-brand-300 border-brand-400/30">
                {course.categoryName}
              </Badge>
              <h1 className="text-3xl sm:text-4xl font-extrabold tracking-tight leading-tight">
                {course.title}
              </h1>
              <p className="text-base text-slate-300 leading-relaxed">
                {course.subtitle}
              </p>
              <div className="flex flex-wrap items-center gap-4 text-xs text-slate-400 pt-2">
                <span className="flex items-center gap-1 font-semibold text-amber-400">
                  <Star className="w-4 h-4 fill-amber-400" /> {course.averageRating || 4.9} (1,420 ratings)
                </span>
                <span>•</span>
                <span>Taught by <strong className="text-white">{course.instructorName}</strong></span>
                <span>•</span>
                <span>Level: <strong className="text-white">{course.level}</strong></span>
              </div>
            </div>

            {/* Floating Enrollment Card */}
            <div className="bg-white text-slate-900 p-6 rounded-2xl shadow-2xl border border-slate-200">
              <div className="relative aspect-video rounded-xl overflow-hidden mb-6 bg-slate-100">
                <img
                  src={course.thumbnailUrl || 'https://images.unsplash.com/photo-1517694712202-14dd9538aa97?w=800&auto=format&fit=crop&q=60'}
                  alt={course.title}
                  className="w-full h-full object-cover"
                />
              </div>
              <div className="flex items-baseline justify-between mb-6">
                <div>
                  <span className="text-3xl font-extrabold text-slate-900">{formatCurrency(course.price)}</span>
                </div>
                <Badge variant="success">Instant Access</Badge>
              </div>

              <Button
                onClick={handleEnroll}
                isLoading={isEnrolling}
                size="lg"
                className="w-full text-base font-bold shadow-md shadow-brand-500/20 mb-4 cursor-pointer"
              >
                Enroll Now <ArrowRight className="w-4 h-4 ml-2" />
              </Button>

              <div className="space-y-2 text-xs text-slate-600 border-t border-slate-100 pt-4">
                <div className="flex items-center gap-2">
                  <CheckCircle2 className="w-4 h-4 text-emerald-500" />
                  <span>Full lifetime access to all lessons</span>
                </div>
                <div className="flex items-center gap-2">
                  <Award className="w-4 h-4 text-brand-600" />
                  <span>Verifiable Certificate of Completion</span>
                </div>
                <div className="flex items-center gap-2">
                  <ShieldCheck className="w-4 h-4 text-indigo-600" />
                  <span>30-day money-back guarantee</span>
                </div>
              </div>
            </div>
          </div>
        </div>
      </div>

      {/* Curriculum Outline */}
      <div className="max-w-7xl mx-auto px-4 sm:px-6 lg:px-8 mt-12">
        <div className="grid grid-cols-1 lg:grid-cols-3 gap-12">
          <div className="lg:col-span-2 space-y-10">
            <div>
              <h2 className="text-2xl font-bold text-slate-900 mb-4">Course Description</h2>
              <div className="prose prose-slate max-w-none text-slate-600 leading-relaxed text-sm">
                <p>{course.description}</p>
              </div>
            </div>

            <div>
              <h2 className="text-2xl font-bold text-slate-900 mb-4">Curriculum Outline</h2>
              <div className="space-y-4">
                {course.modules.map((mod, idx) => (
                  <Card key={mod.id}>
                    <div className="p-4 bg-slate-50 font-semibold text-sm text-slate-800 border-b border-slate-200 flex justify-between">
                      <span>{mod.title}</span>
                      <span className="text-xs text-slate-500 font-normal">{mod.lessons.length} lessons</span>
                    </div>
                    <div className="divide-y divide-slate-100">
                      {mod.lessons.map((lesson) => (
                        <div key={lesson.id} className="p-4 flex items-center justify-between text-sm hover:bg-slate-50/50">
                          <div className="flex items-center gap-3">
                            {lesson.isFreePreview ? (
                              <Play className="w-4 h-4 text-brand-600" />
                            ) : (
                              <Lock className="w-4 h-4 text-slate-400" />
                            )}
                            <span className="text-slate-700">{lesson.title}</span>
                          </div>
                          <span className="text-xs text-slate-400">{lesson.durationMinutes}m</span>
                        </div>
                      ))}
                    </div>
                  </Card>
                ))}
              </div>
            </div>
          </div>
        </div>
      </div>
    </div>
  );
};
