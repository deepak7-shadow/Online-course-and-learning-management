import React, { useState } from 'react';
import { useNavigate, Link } from 'react-router-dom';
import { Card, CardHeader, CardTitle, CardContent } from '@/components/ui/Card';
import { Button } from '@/components/ui/Button';
import { Input } from '@/components/ui/Input';
import { mockCategories } from '@/services/api/mockData';
import { ArrowLeft, Plus, Trash2, CheckCircle2 } from 'lucide-react';

export const CourseEditor: React.FC = () => {
  const navigate = useNavigate();
  const [title, setTitle] = useState('');
  const [subtitle, setSubtitle] = useState('');
  const [description, setDescription] = useState('');
  const [price, setPrice] = useState('49.99');
  const [category, setCategory] = useState(mockCategories[0].id);
  const [modules, setModules] = useState<{ id: string; title: string; lessons: string[] }[]>([
    { id: '1', title: 'Module 1: Introduction', lessons: ['Welcome & Setup', 'First Concepts'] }
  ]);
  const [isSaved, setIsSaved] = useState(false);

  const addModule = () => {
    setModules(prev => [
      ...prev,
      { id: String(Date.now()), title: `Module ${prev.length + 1}: New Topic`, lessons: ['Initial Lesson'] }
    ]);
  };

  const handleSave = () => {
    setIsSaved(true);
    setTimeout(() => {
      navigate('/instructor');
    }, 1200);
  };

  return (
    <div className="max-w-4xl mx-auto px-4 py-10 space-y-8">
      <Link to="/instructor" className="inline-flex items-center gap-1.5 text-xs text-slate-500 hover:text-slate-800">
        <ArrowLeft className="w-4 h-4" /> Back to Studio
      </Link>

      <div className="flex items-center justify-between">
        <div>
          <h1 className="text-3xl font-extrabold text-slate-900">Course Authoring Studio</h1>
          <p className="text-sm text-slate-500 mt-1">Create or edit comprehensive multi-module curriculum</p>
        </div>
        <Button onClick={handleSave} className="gap-2 cursor-pointer font-bold">
          {isSaved ? <CheckCircle2 className="w-4 h-4 text-emerald-300" /> : null}
          {isSaved ? 'Saved!' : 'Save & Submit'}
        </Button>
      </div>

      <Card>
        <CardHeader>
          <CardTitle>Course Details</CardTitle>
        </CardHeader>
        <CardContent className="space-y-4">
          <Input
            label="Course Title"
            placeholder="e.g. Mastering Cloud Native Kubernetes"
            value={title}
            onChange={(e) => setTitle(e.target.value)}
          />
          <Input
            label="Subtitle"
            placeholder="A short punchy summary for the catalog card"
            value={subtitle}
            onChange={(e) => setSubtitle(e.target.value)}
          />
          <div className="grid grid-cols-2 gap-4">
            <div>
              <label className="block text-sm font-medium text-slate-700 mb-1">Category</label>
              <select
                value={category}
                onChange={(e) => setCategory(e.target.value)}
                className="w-full px-3.5 py-2 text-sm bg-white border border-slate-300 rounded-lg shadow-sm focus:ring-2 focus:ring-brand-500 focus:outline-none"
              >
                {mockCategories.map((c) => (
                  <option key={c.id} value={c.id}>{c.name}</option>
                ))}
              </select>
            </div>
            <Input
              label="Price (USD)"
              type="number"
              value={price}
              onChange={(e) => setPrice(e.target.value)}
            />
          </div>
          <div>
            <label className="block text-sm font-medium text-slate-700 mb-1">Course Description</label>
            <textarea
              rows={4}
              placeholder="Detailed syllabus, prerequisites, and learning outcomes..."
              value={description}
              onChange={(e) => setDescription(e.target.value)}
              className="w-full px-3.5 py-2 text-sm bg-white border border-slate-300 rounded-lg shadow-sm focus:ring-2 focus:ring-brand-500 focus:outline-none"
            />
          </div>
        </CardContent>
      </Card>

      {/* Curriculum Builder */}
      <Card>
        <CardHeader className="flex flex-row items-center justify-between">
          <CardTitle>Curriculum Builder</CardTitle>
          <Button onClick={addModule} variant="outline" size="sm" className="gap-1 cursor-pointer">
            <Plus className="w-4 h-4" /> Add Module
          </Button>
        </CardHeader>
        <CardContent className="space-y-6">
          {modules.map((m, mIdx) => (
            <div key={m.id} className="p-4 bg-slate-50 rounded-xl border border-slate-200 space-y-3">
              <div className="flex items-center justify-between">
                <input
                  type="text"
                  value={m.title}
                  onChange={(e) => {
                    const val = e.target.value;
                    setModules(prev => prev.map((item, idx) => idx === mIdx ? { ...item, title: val } : item));
                  }}
                  className="font-bold text-slate-900 bg-transparent border-b border-dashed border-slate-300 pb-0.5 focus:outline-none focus:border-brand-500"
                />
                <button
                  onClick={() => setModules(prev => prev.filter((_, idx) => idx !== mIdx))}
                  className="text-slate-400 hover:text-rose-500 cursor-pointer"
                >
                  <Trash2 className="w-4 h-4" />
                </button>
              </div>

              <div className="pl-4 space-y-2 border-l-2 border-slate-200">
                {m.lessons.map((lesson, lIdx) => (
                  <div key={lIdx} className="p-2.5 bg-white rounded-lg border border-slate-200 text-xs font-medium text-slate-700 flex justify-between items-center">
                    <span>{lesson}</span>
                    <span className="text-slate-400 text-[10px]">VIDEO • 15m</span>
                  </div>
                ))}
              </div>
            </div>
          ))}
        </CardContent>
      </Card>
    </div>
  );
};
