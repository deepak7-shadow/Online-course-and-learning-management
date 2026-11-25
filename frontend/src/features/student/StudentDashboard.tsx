import React, { useEffect, useState } from 'react';
import { Link } from 'react-router-dom';
import { useAuth } from '@/context/AuthContext';
import { courseApi } from '@/services/api/courseApi';
import { Enrollment } from '@/types/course';
import { Card, CardContent } from '@/components/ui/Card';
import { Button } from '@/components/ui/Button';
import { Badge } from '@/components/ui/Badge';
import { BookOpen, Award, CheckCircle2, PlayCircle, Clock } from 'lucide-react';

export const StudentDashboard: React.FC = () => {
  const { user } = useAuth();
  const [enrollments, setEnrollments] = useState<Enrollment[]>([]);

  useEffect(() => {
    courseApi.getMyEnrollments().then(setEnrollments);
  }, []);

  const completedCount = enrollments.filter(e => e.status === 'COMPLETED').length;

  return (
    <div className="max-w-7xl mx-auto px-4 sm:px-6 lg:px-8 py-10">
      {/* Student Welcome Header */}
      <div className="flex flex-col sm:flex-row items-start sm:items-center justify-between gap-4 mb-8">
        <div>
          <h1 className="text-3xl font-extrabold text-slate-900">
            Welcome back, {user?.fullName || 'Student'}! 👋
          </h1>
          <p className="text-sm text-slate-500 mt-1">Track your progress and continue learning where you left off</p>
        </div>
        <Link to="/courses">
          <Button variant="outline" size="sm" className="gap-2">
            <BookOpen className="w-4 h-4" /> Browse More Courses
          </Button>
        </Link>
      </div>

      {/* Stats Cards */}
      <div className="grid grid-cols-1 sm:grid-cols-3 gap-6 mb-10">
        <Card className="p-6 flex items-center gap-4 bg-gradient-to-br from-indigo-50/50 to-white">
          <div className="w-12 h-12 rounded-xl bg-brand-100 text-brand-600 flex items-center justify-center">
            <BookOpen className="w-6 h-6" />
          </div>
          <div>
            <p className="text-2xl font-bold text-slate-900">{enrollments.length}</p>
            <p className="text-xs text-slate-500 font-medium">Enrolled Courses</p>
          </div>
        </Card>

        <Card className="p-6 flex items-center gap-4 bg-gradient-to-br from-emerald-50/50 to-white">
          <div className="w-12 h-12 rounded-xl bg-emerald-100 text-emerald-600 flex items-center justify-center">
            <CheckCircle2 className="w-6 h-6" />
          </div>
          <div>
            <p className="text-2xl font-bold text-slate-900">{completedCount}</p>
            <p className="text-xs text-slate-500 font-medium">Completed Courses</p>
          </div>
        </Card>

        <Card className="p-6 flex items-center gap-4 bg-gradient-to-br from-amber-50/50 to-white">
          <div className="w-12 h-12 rounded-xl bg-amber-100 text-amber-600 flex items-center justify-center">
            <Award className="w-6 h-6" />
          </div>
          <div>
            <p className="text-2xl font-bold text-slate-900">{completedCount}</p>
            <p className="text-xs text-slate-500 font-medium">Earned Certificates</p>
          </div>
        </Card>
      </div>

      {/* Enrolled Courses Grid */}
      <h2 className="text-xl font-bold text-slate-900 mb-6">In-Progress Learning</h2>
      <div className="grid grid-cols-1 md:grid-cols-2 lg:grid-cols-3 gap-6">
        {enrollments.map((enrollment) => (
          <Card key={enrollment.id} className="flex flex-col hover:shadow-lg transition-shadow">
            <div className="relative aspect-video overflow-hidden bg-slate-100">
              <img
                src={enrollment.course.thumbnailUrl || 'https://images.unsplash.com/photo-1517694712202-14dd9538aa97?w=800&auto=format&fit=crop&q=60'}
                alt={enrollment.course.title}
                className="w-full h-full object-cover"
              />
              <Badge className="absolute top-3 right-3 bg-white/90 backdrop-blur" variant={enrollment.status === 'COMPLETED' ? 'success' : 'primary'}>
                {enrollment.status}
              </Badge>
            </div>
            <CardContent className="p-6 flex-1 flex flex-col justify-between">
              <div>
                <h3 className="font-bold text-slate-900 line-clamp-1 mb-2">
                  {enrollment.course.title}
                </h3>
                <p className="text-xs text-slate-500 mb-4">{enrollment.course.instructorName}</p>

                {/* Progress Bar */}
                <div className="space-y-1 mb-4">
                  <div className="flex justify-between text-xs font-semibold text-slate-600">
                    <span>Progress</span>
                    <span>{enrollment.progressPercentage}%</span>
                  </div>
                  <div className="w-full bg-slate-100 rounded-full h-2 overflow-hidden">
                    <div
                      className="bg-brand-600 h-2 rounded-full transition-all duration-500"
                      style={{ width: `${enrollment.progressPercentage}%` }}
                    />
                  </div>
                </div>
              </div>

              <div className="flex items-center gap-2 pt-2">
                <Link to={`/learn/${enrollment.course.id}`} className="flex-1">
                  <Button className="w-full gap-2 text-xs cursor-pointer">
                    <PlayCircle className="w-4 h-4" /> Continue
                  </Button>
                </Link>
                {enrollment.certificateCode && (
                  <Link to={`/certificates/${enrollment.certificateCode}`}>
                    <Button variant="outline" size="md" title="View Certificate" className="cursor-pointer">
                      <Award className="w-4 h-4 text-amber-600" />
                    </Button>
                  </Link>
                )}
              </div>
            </CardContent>
          </Card>
        ))}
      </div>
    </div>
  );
};
