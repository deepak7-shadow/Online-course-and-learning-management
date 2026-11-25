import React from 'react';
import { useAuth } from '@/context/AuthContext';
import { Card, CardHeader, CardTitle, CardContent } from '@/components/ui/Card';
import { Button } from '@/components/ui/Button';
import { Input } from '@/components/ui/Input';
import { Badge } from '@/components/ui/Badge';

export const StudentProfile: React.FC = () => {
  const { user } = useAuth();

  return (
    <div className="max-w-3xl mx-auto px-4 py-10">
      <Card className="shadow-lg">
        <CardHeader>
          <CardTitle>User Profile Settings</CardTitle>
        </CardHeader>
        <CardContent className="space-y-6">
          <div className="flex items-center gap-4 pb-6 border-b border-slate-100">
            <img
              src={user?.avatarUrl || 'https://images.unsplash.com/photo-1534528741775-53994a69daeb?w=200&auto=format&fit=crop&q=80'}
              alt={user?.fullName}
              className="w-16 h-16 rounded-full object-cover border-2 border-brand-500"
            />
            <div>
              <h2 className="text-xl font-bold text-slate-900">{user?.fullName}</h2>
              <p className="text-xs text-slate-500">{user?.email}</p>
              <Badge variant="primary" className="mt-1">{user?.role}</Badge>
            </div>
          </div>

          <div className="space-y-4">
            <Input label="Full Name" defaultValue={user?.fullName} />
            <Input label="Email Address" defaultValue={user?.email} disabled />
            <Input label="Headline" defaultValue={user?.headline || 'Learner on EduFlow'} />
            <div>
              <label className="block text-sm font-medium text-slate-700 mb-1">Biography</label>
              <textarea
                rows={4}
                className="w-full px-3.5 py-2 text-sm bg-white border border-slate-300 rounded-lg shadow-sm focus:ring-2 focus:ring-brand-500 focus:outline-none"
                defaultValue={user?.bio || ''}
              />
            </div>
            <div className="pt-2 flex justify-end">
              <Button className="cursor-pointer">Save Changes</Button>
            </div>
          </div>
        </CardContent>
      </Card>
    </div>
  );
};
