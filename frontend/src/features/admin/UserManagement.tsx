import React, { useState } from 'react';
import { Card, CardHeader, CardTitle } from '@/components/ui/Card';
import { Button } from '@/components/ui/Button';
import { Badge } from '@/components/ui/Badge';
import { Role, AccountStatus } from '@/types/auth';

interface AdminUser {
  id: string;
  name: string;
  email: string;
  role: Role;
  status: AccountStatus;
}

const mockUsers: AdminUser[] = [
  { id: '1', name: 'Sarah Jenkins', email: 'sarah@eduflow.io', role: 'INSTRUCTOR', status: 'ACTIVE' },
  { id: '2', name: 'David Zhang', email: 'david@eduflow.io', role: 'INSTRUCTOR', status: 'ACTIVE' },
  { id: '3', name: 'Alex Mercer', email: 'alex@eduflow.io', role: 'STUDENT', status: 'ACTIVE' },
  { id: '4', name: 'Marcus Bell', email: 'marcus@badactor.net', role: 'STUDENT', status: 'SUSPENDED' },
];

export const UserManagement: React.FC = () => {
  const [users, setUsers] = useState<AdminUser[]>(mockUsers);

  const toggleStatus = (id: string) => {
    setUsers(prev => prev.map(u => {
      if (u.id === id) {
        return { ...u, status: u.status === 'ACTIVE' ? 'SUSPENDED' : 'ACTIVE' };
      }
      return u;
    }));
  };

  return (
    <div className="max-w-7xl mx-auto px-4 sm:px-6 lg:px-8 py-10 space-y-8">
      <div>
        <h1 className="text-3xl font-extrabold text-slate-900">User Governance</h1>
        <p className="text-sm text-slate-500 mt-1">Audit permissions, roles, and account statuses</p>
      </div>

      <Card className="overflow-hidden">
        <CardHeader>
          <CardTitle>Registered Accounts</CardTitle>
        </CardHeader>
        <div className="overflow-x-auto">
          <table className="w-full text-left text-sm text-slate-600">
            <thead className="bg-slate-50 text-xs uppercase font-semibold text-slate-500 border-b border-slate-200">
              <tr>
                <th className="px-6 py-3.5">User</th>
                <th className="px-6 py-3.5">Role</th>
                <th className="px-6 py-3.5">Status</th>
                <th className="px-6 py-3.5 text-right">Moderation Action</th>
              </tr>
            </thead>
            <tbody className="divide-y divide-slate-100">
              {users.map((u) => (
                <tr key={u.id} className="hover:bg-slate-50/50">
                  <td className="px-6 py-4">
                    <p className="font-semibold text-slate-900">{u.name}</p>
                    <p className="text-xs text-slate-400">{u.email}</p>
                  </td>
                  <td className="px-6 py-4">
                    <Badge variant={u.role === 'INSTRUCTOR' ? 'warning' : 'primary'}>{u.role}</Badge>
                  </td>
                  <td className="px-6 py-4">
                    <Badge variant={u.status === 'ACTIVE' ? 'success' : 'danger'}>{u.status}</Badge>
                  </td>
                  <td className="px-6 py-4 text-right">
                    <Button
                      onClick={() => toggleStatus(u.id)}
                      variant={u.status === 'ACTIVE' ? 'outline' : 'primary'}
                      size="sm"
                      className="cursor-pointer"
                    >
                      {u.status === 'ACTIVE' ? 'Suspend Account' : 'Reactivate'}
                    </Button>
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
