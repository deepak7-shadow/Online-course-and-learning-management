import React from 'react';
import { Link } from 'react-router-dom';
import { GraduationCap, Github, Twitter, Linkedin, Heart } from 'lucide-react';

export const Footer: React.FC = () => {
  return (
    <footer className="bg-slate-900 text-slate-400 py-12 border-t border-slate-800 mt-auto">
      <div className="max-w-7xl mx-auto px-4 sm:px-6 lg:px-8">
        <div className="grid grid-cols-1 md:grid-cols-4 gap-8 mb-8">
          <div>
            <div className="flex items-center gap-2 text-white font-bold text-lg mb-3">
              <GraduationCap className="w-6 h-6 text-brand-400" />
              EduFlow
            </div>
            <p className="text-sm text-slate-400 leading-relaxed mb-4">
              Enterprise Online Course & Learning Management Platform powered by React, Spring Boot, and Supabase.
            </p>
            <div className="flex items-center gap-3">
              <a href="https://github.com" target="_blank" rel="noreferrer" className="hover:text-white transition-colors">
                <Github className="w-5 h-5" />
              </a>
              <a href="https://twitter.com" target="_blank" rel="noreferrer" className="hover:text-white transition-colors">
                <Twitter className="w-5 h-5" />
              </a>
              <a href="https://linkedin.com" target="_blank" rel="noreferrer" className="hover:text-white transition-colors">
                <Linkedin className="w-5 h-5" />
              </a>
            </div>
          </div>

          <div>
            <h4 className="text-white font-semibold text-sm mb-3">Explore</h4>
            <ul className="space-y-2 text-sm">
              <li><Link to="/courses" className="hover:text-white transition-colors">Course Catalog</Link></li>
              <li><Link to="/courses?category=software-development" className="hover:text-white transition-colors">Software Engineering</Link></li>
              <li><Link to="/courses?category=cloud-devops" className="hover:text-white transition-colors">Cloud & DevOps</Link></li>
              <li><Link to="/courses?category=data-science-ai" className="hover:text-white transition-colors">AI & Data Science</Link></li>
            </ul>
          </div>

          <div>
            <h4 className="text-white font-semibold text-sm mb-3">Platform Roles</h4>
            <ul className="space-y-2 text-sm">
              <li><Link to="/student" className="hover:text-white transition-colors">Student Dashboard</Link></li>
              <li><Link to="/instructor" className="hover:text-white transition-colors">Instructor Studio</Link></li>
              <li><Link to="/admin" className="hover:text-white transition-colors">Admin Console</Link></li>
              <li><Link to="/student/certificates" className="hover:text-white transition-colors">Credential Verification</Link></li>
            </ul>
          </div>

          <div>
            <h4 className="text-white font-semibold text-sm mb-3">Architecture</h4>
            <ul className="space-y-2 text-sm text-slate-400">
              <li>Frontend: React 18, Vite, Tailwind CSS</li>
              <li>Backend: Spring Boot 3, Java 21</li>
              <li>Database: Supabase PostgreSQL 15</li>
              <li>Auth: Supabase OAuth2 / JWKS</li>
            </ul>
          </div>
        </div>

        <div className="border-t border-slate-800 pt-8 flex flex-col sm:flex-row items-center justify-between text-xs text-slate-500">
          <p>© 2026 EduFlow LMS. All rights reserved.</p>
          <p className="flex items-center gap-1 mt-2 sm:mt-0">
            Crafted for enterprise learning with <Heart className="w-3.5 h-3.5 text-rose-500 fill-rose-500" />
          </p>
        </div>
      </div>
    </footer>
  );
};
