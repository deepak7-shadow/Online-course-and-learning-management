import React, { useEffect, useState } from 'react';
import { Link } from 'react-router-dom';
import { courseApi } from '@/services/api/courseApi';
import { CourseSummary, Category } from '@/types/course';
import { Button } from '@/components/ui/Button';
import { Card, CardContent } from '@/components/ui/Card';
import { Badge } from '@/components/ui/Badge';
import { formatCurrency, formatDuration } from '@/lib/utils';
import { Play, Sparkles, BookOpen, Users, Award, ShieldCheck, ArrowRight, Star, Clock } from 'lucide-react';

export const PublicLanding: React.FC = () => {
  const [courses, setCourses] = useState<CourseSummary[]>([]);
  const [categories, setCategories] = useState<Category[]>([]);

  useEffect(() => {
    courseApi.getCourses().then(setCourses);
    courseApi.getCategories().then(setCategories);
  }, []);

  return (
    <div className="space-y-20 pb-20">
      {/* Hero Section */}
      <section className="relative overflow-hidden pt-12 pb-20 lg:pt-20 bg-gradient-to-b from-indigo-50/50 via-white to-slate-50">
        <div className="max-w-7xl mx-auto px-4 sm:px-6 lg:px-8">
          <div className="text-center max-w-3xl mx-auto space-y-6">
            <div className="inline-flex items-center gap-2 px-3 py-1.5 rounded-full bg-brand-50 border border-brand-200 text-brand-700 text-xs font-semibold">
              <Sparkles className="w-3.5 h-3.5" /> Next-Generation Learning Management System
            </div>
            <h1 className="text-4xl sm:text-5xl lg:text-6xl font-extrabold text-slate-900 tracking-tight leading-tight">
              Master High-Impact Skills with <span className="text-brand-600">Enterprise Confidence</span>
            </h1>
            <p className="text-lg text-slate-600 leading-relaxed">
              Accelerate your engineering, cloud, and AI career with interactive curricula, verifiable certificates, and world-class expert mentorship.
            </p>
            <div className="flex flex-wrap items-center justify-center gap-4 pt-4">
              <Link to="/courses">
                <Button size="lg" className="gap-2 shadow-lg shadow-brand-500/25">
                  Browse All Courses <ArrowRight className="w-4 h-4" />
                </Button>
              </Link>
              <Link to="/register">
                <Button variant="outline" size="lg">
                  Start Learning for Free
                </Button>
              </Link>
            </div>

            {/* Quick Metrics */}
            <div className="grid grid-cols-2 md:grid-cols-4 gap-6 pt-12 border-t border-slate-200/60 max-w-4xl mx-auto text-left">
              <div>
                <p className="text-3xl font-bold text-slate-900">45k+</p>
                <p className="text-xs text-slate-500 font-medium">Active Learners</p>
              </div>
              <div>
                <p className="text-3xl font-bold text-slate-900">120+</p>
                <p className="text-xs text-slate-500 font-medium">Enterprise Courses</p>
              </div>
              <div>
                <p className="text-3xl font-bold text-slate-900">99.4%</p>
                <p className="text-xs text-slate-500 font-medium">Completion Rate</p>
              </div>
              <div>
                <p className="text-3xl font-bold text-slate-900">4.9 / 5</p>
                <p className="text-xs text-slate-500 font-medium">Satisfaction Score</p>
              </div>
            </div>
          </div>
        </div>
      </section>

      {/* Featured Courses */}
      <section className="max-w-7xl mx-auto px-4 sm:px-6 lg:px-8">
        <div className="flex items-end justify-between mb-8">
          <div>
            <h2 className="text-2xl sm:text-3xl font-bold text-slate-900">Featured Courses</h2>
            <p className="text-sm text-slate-500 mt-1">Hand-picked by industry leaders for career growth</p>
          </div>
          <Link to="/courses" className="text-sm font-semibold text-brand-600 hover:text-brand-700 flex items-center gap-1">
            View All <ArrowRight className="w-4 h-4" />
          </Link>
        </div>

        <div className="grid grid-cols-1 md:grid-cols-3 gap-8">
          {courses.slice(0, 3).map((course) => (
            <Card key={course.id} className="group hover:shadow-xl transition-all duration-300 flex flex-col">
              <div className="relative aspect-video overflow-hidden bg-slate-100">
                <img
                  src={course.thumbnailUrl || 'https://images.unsplash.com/photo-1517694712202-14dd9538aa97?w=800&auto=format&fit=crop&q=60'}
                  alt={course.title}
                  className="w-full h-full object-cover group-hover:scale-105 transition-transform duration-300"
                />
                <Badge className="absolute top-3 left-3 bg-white/90 backdrop-blur text-slate-800 shadow-sm">
                  {course.categoryName}
                </Badge>
              </div>
              <CardContent className="flex-1 flex flex-col p-6">
                <div className="flex items-center gap-2 text-xs text-slate-500 mb-2">
                  <span className="flex items-center gap-1 font-semibold text-amber-600">
                    <Star className="w-3.5 h-3.5 fill-amber-500 text-amber-500" /> {course.averageRating || 4.8}
                  </span>
                  <span>•</span>
                  <span className="flex items-center gap-1">
                    <Clock className="w-3.5 h-3.5" /> {formatDuration(course.totalDurationMinutes || 120)}
                  </span>
                  <span>•</span>
                  <span>{course.totalLessons || 12} lessons</span>
                </div>
                <h3 className="font-bold text-slate-900 text-lg group-hover:text-brand-600 transition-colors line-clamp-2 mb-2">
                  <Link to={`/courses/${course.slug}`}>{course.title}</Link>
                </h3>
                <p className="text-xs text-slate-500 line-clamp-2 mb-4 flex-1">
                  {course.subtitle}
                </p>
                <div className="pt-4 border-t border-slate-100 flex items-center justify-between">
                  <span className="text-xs text-slate-600 font-medium">{course.instructorName}</span>
                  <span className="text-lg font-extrabold text-slate-900">{formatCurrency(course.price)}</span>
                </div>
              </CardContent>
            </Card>
          ))}
        </div>
      </section>

      {/* Category Grid */}
      <section className="bg-slate-100/60 py-16 border-y border-slate-200">
        <div className="max-w-7xl mx-auto px-4 sm:px-6 lg:px-8">
          <div className="text-center max-w-2xl mx-auto mb-12">
            <h2 className="text-2xl sm:text-3xl font-bold text-slate-900">Explore by Technical Domain</h2>
            <p className="text-sm text-slate-500 mt-1">Structured career tracks across modern technologies</p>
          </div>
          <div className="grid grid-cols-2 md:grid-cols-3 lg:grid-cols-6 gap-4">
            {categories.map((cat) => (
              <Link
                key={cat.id}
                to={`/courses?category=${cat.slug}`}
                className="p-5 bg-white rounded-xl border border-slate-200 hover:border-brand-500 hover:shadow-md transition-all text-center group"
              >
                <div className="w-12 h-12 mx-auto rounded-lg bg-brand-50 text-brand-600 flex items-center justify-center mb-3 group-hover:bg-brand-600 group-hover:text-white transition-colors">
                  <BookOpen className="w-6 h-6" />
                </div>
                <h3 className="font-semibold text-sm text-slate-900 group-hover:text-brand-600 transition-colors">
                  {cat.name}
                </h3>
              </Link>
            ))}
          </div>
        </div>
      </section>
    </div>
  );
};
