import React, { useState } from 'react';
import { Card, CardHeader, CardTitle } from '@/components/ui/Card';
import { Button } from '@/components/ui/Button';
import { Badge } from '@/components/ui/Badge';
import { mockCourses } from '@/services/api/mockData';
import { CourseDetail } from '@/types/course';
import { Check, X } from 'lucide-react';

export const CourseApprovals: React.FC = () => {
  const [courses, setCourses] = useState<CourseDetail[]>(mockCourses);

  const decide = (id: string, approved: boolean) => {
    setCourses(prev => prev.map(c => c.id === id ? { ...c, status: approved ? 'APPROVED' : 'REJECTED' } : c));
  };

  return (
    <div className="max-w-7xl mx-auto px-4 sm:px-6 lg:px-8 py-10 space-y-8">
      <div>
        <h1 className="text-3xl font-extrabold text-slate-900">Course Verification & Approvals</h1>
        <p className="text-sm text-slate-500 mt-1">Review course submissions and enforce quality benchmarks</p>
      </div>

      <Card className="overflow-hidden">
        <CardHeader>
          <CardTitle>Course Pipeline</CardTitle>
        </CardHeader>
        <div className="divide-y divide-slate-100">
          {courses.map((c) => (
            <div key={c.id} className="p-6 flex flex-col md:flex-row items-start md:items-center justify-between gap-4">
              <div>
                <div className="flex items-center gap-2 mb-1">
                  <Badge variant={c.status === 'APPROVED' ? 'success' : 'warning'}>{c.status}</Badge>
                  <span className="text-xs text-slate-400">• {c.categoryName}</span>
                </div>
                <h3 className="text-lg font-bold text-slate-900">{c.title}</h3>
                <p className="text-xs text-slate-500 mt-1">Instructor: {c.instructorName}</p>
              </div>

              <div className="flex items-center gap-2">
                <Button
                  onClick={() => decide(c.id, true)}
                  size="sm"
                  variant="primary"
                  className="gap-1 cursor-pointer"
                >
                  <Check className="w-4 h-4" /> Approve
                </Button>
                <Button
                  onClick={() => decide(c.id, false)}
                  size="sm"
                  variant="danger"
                  className="gap-1 cursor-pointer"
                >
                  <X className="w-4 h-4" /> Reject
                </Button>
              </div>
            </div>
          ))}
        </div>
      </Card>
    </div>
  );
};
