import React from 'react';
import { Link, useNavigate } from 'react-router-dom';
import { useAuth } from '@/context/AuthContext';
import { Button } from '@/components/ui/Button';
import { BookOpen, GraduationCap, LayoutDashboard, Shield, LogOut, Heart } from 'lucide-react';

export const Navbar: React.FC = () => {
  const { user, role, isAuthenticated, logout, switchRole } = useAuth();
  const navigate = useNavigate();

  return (
    <header className="sticky top-0 z-50 bg-white/95 backdrop-blur border-b border-slate-200">
      <div className="max-w-7xl mx-auto px-4 sm:px-6 lg:px-8 h-16 flex items-center justify-between">
        {/* Brand */}
        <Link to="/" className="flex items-center gap-2.5 group">
          <div className="w-10 h-10 rounded-xl bg-gradient-to-tr from-brand-600 to-indigo-500 flex items-center justify-center text-white shadow-md shadow-brand-500/20 group-hover:scale-105 transition-transform">
            <GraduationCap className="w-6 h-6" />
          </div>
          <div>
            <span className="font-extrabold text-xl tracking-tight bg-gradient-to-r from-slate-900 to-slate-700 bg-clip-text text-transparent">
              Edu<span className="text-brand-600">Flow</span>
            </span>
            <span className="text-[10px] font-semibold text-brand-600 block leading-none">ENTERPRISE LMS</span>
          </div>
        </Link>

        {/* Navigation Links */}
        <nav className="hidden md:flex items-center gap-6 text-sm font-medium text-slate-600">
          <Link to="/courses" className="hover:text-brand-600 transition-colors flex items-center gap-1.5">
            <BookOpen className="w-4 h-4" />
            Explore Courses
          </Link>
          {isAuthenticated && (
            <>
              {role === 'STUDENT' && (
                <Link to="/student" className="hover:text-brand-600 transition-colors flex items-center gap-1.5">
                  <LayoutDashboard className="w-4 h-4" />
                  My Learning
                </Link>
              )}
              {role === 'INSTRUCTOR' && (
                <Link to="/instructor" className="hover:text-brand-600 transition-colors flex items-center gap-1.5 text-amber-600 font-semibold">
                  <LayoutDashboard className="w-4 h-4" />
                  Instructor Studio
                </Link>
              )}
              {role === 'ADMIN' && (
                <Link to="/admin" className="hover:text-brand-600 transition-colors flex items-center gap-1.5 text-rose-600 font-semibold">
                  <Shield className="w-4 h-4" />
                  Admin Console
                </Link>
              )}
            </>
          )}
        </nav>

        {/* Right Action / Role Switcher */}
        <div className="flex items-center gap-3">
          {isAuthenticated ? (
            <div className="flex items-center gap-3">
              {/* Role Toggle Selector */}
              <div className="flex items-center bg-slate-100 p-1 rounded-lg border border-slate-200 text-xs">
                {(['STUDENT', 'INSTRUCTOR', 'ADMIN'] as const).map((r) => (
                  <button
                    key={r}
                    onClick={() => {
                      switchRole(r);
                      if (r === 'STUDENT') navigate('/student');
                      if (r === 'INSTRUCTOR') navigate('/instructor');
                      if (r === 'ADMIN') navigate('/admin');
                    }}
                    className={`px-2 py-1 rounded font-medium transition-all cursor-pointer ${
                      role === r ? 'bg-white shadow-sm text-brand-700 font-semibold' : 'text-slate-600 hover:text-slate-900'
                    }`}
                  >
                    {r}
                  </button>
                ))}
              </div>

              {/* User Avatar */}
              <Link to="/student/profile" className="flex items-center gap-2 pl-2">
                <img
                  src={user?.avatarUrl || 'https://images.unsplash.com/photo-1534528741775-53994a69daeb?w=100&auto=format&fit=crop&q=80'}
                  alt={user?.fullName}
                  className="w-8 h-8 rounded-full border border-slate-200 object-cover"
                />
              </Link>

              {/* Logout */}
              <button
                onClick={() => logout()}
                className="p-2 text-slate-500 hover:text-rose-600 rounded-lg hover:bg-slate-100 transition-colors cursor-pointer"
                title="Sign out"
              >
                <LogOut className="w-4 h-4" />
              </button>
            </div>
          ) : (
            <div className="flex items-center gap-2">
              <Link to="/login">
                <Button variant="ghost" size="sm">Log In</Button>
              </Link>
              <Link to="/register">
                <Button variant="primary" size="sm">Get Started</Button>
              </Link>
            </div>
          )}
        </div>
      </div>
    </header>
  );
};
