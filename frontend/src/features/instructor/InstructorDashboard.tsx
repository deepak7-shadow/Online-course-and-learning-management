import React, { useState } from 'react';
import { Link } from 'react-router-dom';
import { mockCourses } from '@/services/api/mockData';
import { Card, CardContent } from '@/components/ui/Card';
import { Button } from '@/components/ui/Button';
import { Badge } from '@/components/ui/Badge';
import { formatCurrency } from '@/lib/utils';
import { Plus, Users, DollarSign, BookOpen, Star, TrendingUp, Edit, Eye } from 'lucide-react';
import { ResponsiveContainer, AreaChart, Area, XAxis, YAxis, Tooltip } from 'recharts';

const earningsData = [
  { month: 'May', earnings: 1400 },
  { month: 'Jun', earnings: 2100 },
  { month: 'Jul', earnings: 2800 },
  { month: 'Aug', earnings: 3600 },
  { month: 'Sep', earnings: 4900 },
  { month: 'Oct', earnings: 6200 },
];

export const InstructorDashboard: React.FC = () => {
  const [courses] = useState(mockCourses);

  return (
    <div className="max-w-7xl mx-auto px-4 sm:px-6 lg:px-8 py-10 space-y-8">
      {/* Header */}
      <div className="flex flex-col sm:flex-row items-start sm:items-center justify-between gap-4">
        <div>
          <h1 className="text-3xl font-extrabold text-slate-900">Instructor Studio</h1>
          <p className="text-sm text-slate-500 mt-1">Manage curricula, review earnings, and publish courses</p>
        </div>
        <Link to="/instructor/courses/new">
          <Button className="gap-2 shadow-md cursor-pointer">
            <Plus className="w-4 h-4" /> Create New Course
          </Button>
        </Link>
      </div>

      {/* Metrics Row */}
      <div className="grid grid-cols-1 sm:grid-cols-2 lg:grid-cols-4 gap-6">
        <Card className="p-6">
          <div className="flex items-center justify-between">
            <div>
              <p className="text-xs font-semibold text-slate-500 uppercase">Total Revenue</p>
              <p className="text-2xl font-bold text-slate-900 mt-1">$21,000</p>
            </div>
            <div className="w-10 h-10 rounded-lg bg-emerald-100 text-emerald-600 flex items-center justify-center">
              <DollarSign className="w-5 h-5" />
            </div>
          </div>
          <p className="text-xs text-emerald-600 font-medium mt-3 flex items-center gap-1">
            <TrendingUp className="w-3.5 h-3.5" /> +18.4% from last month
          </p>
        </Card>

        <Card className="p-6">
          <div className="flex items-center justify-between">
            <div>
              <p className="text-xs font-semibold text-slate-500 uppercase">Total Students</p>
              <p className="text-2xl font-bold text-slate-900 mt-1">4,710</p>
            </div>
            <div className="w-10 h-10 rounded-lg bg-brand-100 text-brand-600 flex items-center justify-center">
              <Users className="w-5 h-5" />
            </div>
          </div>
          <p className="text-xs text-brand-600 font-medium mt-3 flex items-center gap-1">
            <TrendingUp className="w-3.5 h-3.5" /> +120 new this week
          </p>
        </Card>

        <Card className="p-6">
          <div className="flex items-center justify-between">
            <div>
              <p className="text-xs font-semibold text-slate-500 uppercase">Authored Courses</p>
              <p className="text-2xl font-bold text-slate-900 mt-1">{courses.length}</p>
            </div>
            <div className="w-10 h-10 rounded-lg bg-amber-100 text-amber-600 flex items-center justify-center">
              <BookOpen className="w-5 h-5" />
            </div>
          </div>
          <p className="text-xs text-slate-500 mt-3">2 Published, 1 Draft</p>
        </Card>

        <Card className="p-6">
          <div className="flex items-center justify-between">
            <div>
              <p className="text-xs font-semibold text-slate-500 uppercase">Instructor Rating</p>
              <p className="text-2xl font-bold text-slate-900 mt-1">4.92 / 5</p>
            </div>
            <div className="w-10 h-10 rounded-lg bg-rose-100 text-rose-600 flex items-center justify-center">
              <Star className="w-5 h-5 fill-rose-500 text-rose-500" />
            </div>
          </div>
          <p className="text-xs text-slate-500 mt-3">Across 1,840 student reviews</p>
        </Card>
      </div>

      {/* Revenue Graph */}
      <Card className="p-6">
        <h3 className="text-base font-bold text-slate-900 mb-4">Enrollment Revenue Growth</h3>
        <div className="h-64 w-full">
          <ResponsiveContainer width="100%" height="100%">
            <AreaChart data={earningsData}>
              <defs>
                <linearGradient id="colorEarnings" x1="0" y1="0" x2="0" y2="1">
                  <stop offset="5%" stopColor="#6366f1" stopOpacity={0.4}/>
                  <stop offset="95%" stopColor="#6366f1" stopOpacity={0}/>
                </linearGradient>
              </defs>
              <XAxis dataKey="month" stroke="#94a3b8" fontSize={12} />
              <YAxis stroke="#94a3b8" fontSize={12} tickFormatter={(v) => `$${v}`} />
              <Tooltip formatter={(value: number) => [`$${value}`, 'Revenue']} />
              <Area type="monotone" dataKey="earnings" stroke="#4f46e5" strokeWidth={3} fillOpacity={1} fill="url(#colorEarnings)" />
            </AreaChart>
          </ResponsiveContainer>
        </div>
      </Card>

      {/* Authored Courses Table */}
      <Card className="overflow-hidden">
        <div className="p-6 border-b border-slate-100 flex items-center justify-between">
          <h3 className="font-bold text-slate-900 text-base">Course Portfolio</h3>
        </div>
        <div className="overflow-x-auto">
          <table className="w-full text-left text-sm text-slate-600">
            <thead className="bg-slate-50 text-xs uppercase font-semibold text-slate-500 border-b border-slate-200">
              <tr>
                <th className="px-6 py-3.5">Course</th>
                <th className="px-6 py-3.5">Category</th>
                <th className="px-6 py-3.5">Price</th>
                <th className="px-6 py-3.5">Status</th>
                <th className="px-6 py-3.5 text-right">Actions</th>
              </tr>
            </thead>
            <tbody className="divide-y divide-slate-100">
              {courses.map((course) => (
                <tr key={course.id} className="hover:bg-slate-50/50">
                  <td className="px-6 py-4 font-semibold text-slate-900 flex items-center gap-3">
                    <img
                      src={course.thumbnailUrl || 'https://images.unsplash.com/photo-1517694712202-14dd9538aa97?w=100&auto=format&fit=crop&q=60'}
                      alt=""
                      className="w-12 h-8 rounded object-cover"
                    />
                    <span>{course.title}</span>
                  </td>
                  <td className="px-6 py-4">{course.categoryName}</td>
                  <td className="px-6 py-4 font-medium text-slate-900">{formatCurrency(course.price)}</td>
                  <td className="px-6 py-4">
                    <Badge variant={course.status === 'APPROVED' ? 'success' : 'warning'}>
                      {course.status}
                    </Badge>
                  </td>
                  <td className="px-6 py-4 text-right">
                    <div className="inline-flex items-center gap-2">
                      <Link to={`/courses/${course.slug}`}>
                        <Button variant="ghost" size="sm" className="cursor-pointer">
                          <Eye className="w-4 h-4" />
                        </Button>
                      </Link>
                      <Link to={`/instructor/courses/edit/${course.id}`}>
                        <Button variant="outline" size="sm" className="cursor-pointer">
                          <Edit className="w-4 h-4" /> Edit
                        </Button>
                      </Link>
                    </div>
                  </td>
                </tr>
              ))}
            </tbody>
          </table>
        </div>
      </Card>
    </div>
  );
};
