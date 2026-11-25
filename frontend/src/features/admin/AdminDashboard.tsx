import React from 'react';
import { Link } from 'react-router-dom';
import { Card, CardHeader, CardTitle, CardContent } from '@/components/ui/Card';
import { Badge } from '@/components/ui/Badge';
import { Button } from '@/components/ui/Button';
import { Users, BookOpen, Clock, ShieldAlert, CheckCircle, ArrowRight } from 'lucide-react';
import { mockCourses } from '@/services/api/mockData';

export const AdminDashboard: React.FC = () => {
  return (
    <div className="max-w-7xl mx-auto px-4 sm:px-6 lg:px-8 py-10 space-y-8">
      {/* Header */}
      <div>
        <h1 className="text-3xl font-extrabold text-slate-900">Admin Operations Console</h1>
        <p className="text-sm text-slate-500 mt-1">Platform moderation, user management, and course approval pipeline</p>
      </div>

      {/* Metrics Row */}
      <div className="grid grid-cols-1 sm:grid-cols-2 lg:grid-cols-4 gap-6">
        <Card className="p-6">
          <div className="flex items-center justify-between">
            <div>
              <p className="text-xs font-semibold text-slate-500 uppercase">Registered Users</p>
              <p className="text-2xl font-bold text-slate-900 mt-1">14,290</p>
            </div>
            <div className="w-10 h-10 rounded-lg bg-indigo-100 text-indigo-600 flex items-center justify-center">
              <Users className="w-5 h-5" />
            </div>
          </div>
          <p className="text-xs text-slate-500 mt-3">13.8k Students, 490 Instructors</p>
        </Card>

        <Card className="p-6">
          <div className="flex items-center justify-between">
            <div>
              <p className="text-xs font-semibold text-slate-500 uppercase">Pending Approvals</p>
              <p className="text-2xl font-bold text-amber-600 mt-1">4 Courses</p>
            </div>
            <div className="w-10 h-10 rounded-lg bg-amber-100 text-amber-600 flex items-center justify-center">
              <Clock className="w-5 h-5" />
            </div>
          </div>
          <p className="text-xs text-amber-600 font-medium mt-3">Requires compliance review</p>
        </Card>

        <Card className="p-6">
          <div className="flex items-center justify-between">
            <div>
              <p className="text-xs font-semibold text-slate-500 uppercase">Active Courses</p>
              <p className="text-2xl font-bold text-slate-900 mt-1">128</p>
            </div>
            <div className="w-10 h-10 rounded-lg bg-emerald-100 text-emerald-600 flex items-center justify-center">
              <BookOpen className="w-5 h-5" />
            </div>
          </div>
          <p className="text-xs text-emerald-600 font-medium mt-3">99.8% platform uptime</p>
        </Card>

        <Card className="p-6">
          <div className="flex items-center justify-between">
            <div>
              <p className="text-xs font-semibold text-slate-500 uppercase">Security Alerts</p>
              <p className="text-2xl font-bold text-slate-900 mt-1">0</p>
            </div>
            <div className="w-10 h-10 rounded-lg bg-emerald-100 text-emerald-600 flex items-center justify-center">
              <ShieldAlert className="w-5 h-5" />
            </div>
          </div>
          <p className="text-xs text-emerald-600 font-medium mt-3">All RLS policies active</p>
        </Card>
      </div>

      {/* Navigation Quick Links */}
      <div className="grid grid-cols-1 md:grid-cols-2 gap-6">
        <Card className="p-6 flex items-center justify-between hover:border-brand-500 transition-colors">
          <div>
            <h3 className="font-bold text-slate-900 text-base">Course Approval Queue</h3>
            <p className="text-xs text-slate-500 mt-1">Review newly submitted curricula before public release</p>
          </div>
          <Link to="/admin/approvals">
            <Button size="sm" className="gap-2 cursor-pointer">
              Review Queue <ArrowRight className="w-4 h-4" />
            </Button>
          </Link>
        </Card>

        <Card className="p-6 flex items-center justify-between hover:border-brand-500 transition-colors">
          <div>
            <h3 className="font-bold text-slate-900 text-base">User Directory & Roles</h3>
            <p className="text-xs text-slate-500 mt-1">Manage student/instructor privileges and account suspensions</p>
          </div>
          <Link to="/admin/users">
            <Button size="sm" variant="outline" className="gap-2 cursor-pointer">
              Manage Users <ArrowRight className="w-4 h-4" />
            </Button>
          </Link>
        </Card>
      </div>

      {/* Course Approval Table */}
      <Card className="overflow-hidden">
        <CardHeader>
          <CardTitle>Recent Moderation Submissions</CardTitle>
        </CardHeader>
        <div className="overflow-x-auto">
          <table className="w-full text-left text-sm text-slate-600">
            <thead className="bg-slate-50 text-xs uppercase font-semibold text-slate-500 border-b border-slate-200">
              <tr>
                <th className="px-6 py-3.5">Course</th>
                <th className="px-6 py-3.5">Instructor</th>
                <th className="px-6 py-3.5">Submitted Date</th>
                <th className="px-6 py-3.5">Decision</th>
              </tr>
            </thead>
            <tbody className="divide-y divide-slate-100">
              {mockCourses.slice(0, 2).map((c) => (
                <tr key={c.id}>
                  <td className="px-6 py-4 font-semibold text-slate-900">{c.title}</td>
                  <td className="px-6 py-4">{c.instructorName}</td>
                  <td className="px-6 py-4">Nov 24, 2026</td>
                  <td className="px-6 py-4">
                    <span className="inline-flex items-center gap-1.5 text-xs text-emerald-600 font-semibold">
                      <CheckCircle className="w-4 h-4" /> Approved
                    </span>
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
