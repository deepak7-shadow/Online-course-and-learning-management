import React, { useEffect, useState } from 'react';
import { useSearchParams, Link } from 'react-router-dom';
import { courseApi } from '@/services/api/courseApi';
import { CourseSummary, Category } from '@/types/course';
import { Card, CardContent } from '@/components/ui/Card';
import { Badge } from '@/components/ui/Badge';
import { Input } from '@/components/ui/Input';
import { formatCurrency, formatDuration } from '@/lib/utils';
import { Search, Filter, Star, Clock, BookOpen } from 'lucide-react';

export const CourseCatalog: React.FC = () => {
  const [searchParams, setSearchParams] = useSearchParams();
  const [courses, setCourses] = useState<CourseSummary[]>([]);
  const [categories, setCategories] = useState<Category[]>([]);
  const [selectedCat, setSelectedCat] = useState(searchParams.get('category') || 'all');
  const [searchQuery, setSearchQuery] = useState(searchParams.get('q') || '');

  useEffect(() => {
    courseApi.getCategories().then(setCategories);
  }, []);

  useEffect(() => {
    courseApi.getCourses(selectedCat, searchQuery).then(setCourses);
  }, [selectedCat, searchQuery]);

  const handleCategoryChange = (slug: string) => {
    setSelectedCat(slug);
    setSearchParams(slug === 'all' ? {} : { category: slug });
  };

  return (
    <div className="max-w-7xl mx-auto px-4 sm:px-6 lg:px-8 py-10">
      <div className="mb-8">
        <h1 className="text-3xl font-extrabold text-slate-900">Explore Course Catalog</h1>
        <p className="text-sm text-slate-500 mt-1">Browse and filter verified engineering and technology programs</p>
      </div>

      {/* Filter and Search Bar */}
      <div className="flex flex-col md:flex-row gap-4 mb-8">
        <div className="flex-1 relative">
          <Input
            placeholder="Search courses by keyword, topic, or instructor..."
            value={searchQuery}
            onChange={(e) => setSearchQuery(e.target.value)}
            className="pl-10"
          />
          <Search className="w-4 h-4 text-slate-400 absolute left-3.5 top-3" />
        </div>

        {/* Categories Pills */}
        <div className="flex flex-wrap items-center gap-2">
          <button
            onClick={() => handleCategoryChange('all')}
            className={`px-3 py-1.5 rounded-lg text-xs font-medium cursor-pointer transition-colors ${
              selectedCat === 'all' ? 'bg-brand-600 text-white' : 'bg-white border border-slate-200 text-slate-700 hover:bg-slate-50'
            }`}
          >
            All Categories
          </button>
          {categories.map((c) => (
            <button
              key={c.id}
              onClick={() => handleCategoryChange(c.slug)}
              className={`px-3 py-1.5 rounded-lg text-xs font-medium cursor-pointer transition-colors ${
                selectedCat === c.slug ? 'bg-brand-600 text-white' : 'bg-white border border-slate-200 text-slate-700 hover:bg-slate-50'
              }`}
            >
              {c.name}
            </button>
          ))}
        </div>
      </div>

      {/* Course Grid */}
      {courses.length === 0 ? (
        <div className="text-center py-20 bg-white rounded-xl border border-slate-200">
          <BookOpen className="w-12 h-12 text-slate-300 mx-auto mb-3" />
          <h3 className="text-lg font-semibold text-slate-700">No courses match your filter</h3>
          <p className="text-sm text-slate-400 mt-1">Try searching with different terms or reset category.</p>
        </div>
      ) : (
        <div className="grid grid-cols-1 md:grid-cols-2 lg:grid-cols-3 gap-8">
          {courses.map((course) => (
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
                  <span>{course.level}</span>
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
      )}
    </div>
  );
};
