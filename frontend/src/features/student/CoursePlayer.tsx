import React, { useEffect, useState } from 'react';
import { useParams, Link } from 'react-router-dom';
import { courseApi } from '@/services/api/courseApi';
import { CourseDetail, Lesson } from '@/types/course';
import { Button } from '@/components/ui/Button';
import { Badge } from '@/components/ui/Badge';
import { CheckCircle2, Play, FileText, HelpCircle, ArrowLeft, ArrowRight, Award } from 'lucide-react';

export const CoursePlayer: React.FC = () => {
  const { courseId } = useParams<{ courseId: string }>();
  const [course, setCourse] = useState<CourseDetail | null>(null);
  const [activeLesson, setActiveLesson] = useState<Lesson | null>(null);
  const [completedLessons, setCompletedLessons] = useState<Record<string, boolean>>({});

  useEffect(() => {
    if (courseId) {
      courseApi.getCourseDetail(courseId).then((data) => {
        setCourse(data);
        if (data.modules.length > 0 && data.modules[0].lessons.length > 0) {
          setActiveLesson(data.modules[0].lessons[0]);
        }
      });
    }
  }, [courseId]);

  if (!course || !activeLesson) {
    return (
      <div className="min-h-[70vh] flex items-center justify-center">
        <div className="animate-spin rounded-full h-8 w-8 border-b-2 border-brand-600"></div>
      </div>
    );
  }

  const toggleComplete = async (lessonId: string) => {
    const isNow = !completedLessons[lessonId];
    setCompletedLessons(prev => ({ ...prev, [lessonId]: isNow }));
    await courseApi.updateLessonProgress(lessonId, isNow);
  };

  return (
    <div className="flex flex-col lg:flex-row min-h-[calc(100vh-4rem)] bg-slate-900 text-white">
      {/* Main Video & Content Stage */}
      <div className="flex-1 flex flex-col p-4 sm:p-6 lg:p-8 overflow-y-auto">
        <div className="flex items-center justify-between mb-4">
          <Link to="/student" className="inline-flex items-center gap-2 text-xs text-slate-400 hover:text-white transition-colors">
            <ArrowLeft className="w-4 h-4" /> Back to Dashboard
          </Link>
          <span className="text-xs text-slate-400 font-medium">{course.title}</span>
        </div>

        {/* Video Player or Content Block */}
        <div className="aspect-video w-full rounded-2xl overflow-hidden bg-black border border-slate-800 shadow-2xl relative mb-6">
          {activeLesson.contentType === 'VIDEO' ? (
            <video
              key={activeLesson.videoUrl}
              controls
              className="w-full h-full object-contain"
              src={activeLesson.videoUrl || 'https://commondatastorage.googleapis.com/gtv-videos-bucket/sample/BigBuckBunny.mp4'}
            />
          ) : activeLesson.contentType === 'QUIZ' ? (
            <div className="w-full h-full flex flex-col items-center justify-center p-8 text-center bg-slate-800/80">
              <HelpCircle className="w-16 h-16 text-brand-400 mb-4 animate-bounce" />
              <h3 className="text-2xl font-bold text-white mb-2">{activeLesson.title}</h3>
              <p className="text-sm text-slate-300 max-w-md mb-6">
                Test your knowledge on this module. Complete with passing score to earn your certification.
              </p>
              <Link to={`/learn/${course.id}/quiz/${activeLesson.id}`}>
                <Button size="lg" className="gap-2 cursor-pointer">
                  Start Quiz Assessment <ArrowRight className="w-4 h-4" />
                </Button>
              </Link>
            </div>
          ) : (
            <div className="w-full h-full p-8 overflow-y-auto bg-slate-800 text-slate-200">
              <h2 className="text-2xl font-bold text-white mb-4">{activeLesson.title}</h2>
              <div className="prose prose-invert max-w-none text-sm leading-relaxed">
                <p>{activeLesson.contentText || 'Read through the lesson guide to master key concepts.'}</p>
              </div>
            </div>
          )}
        </div>

        {/* Lesson Controls */}
        <div className="flex flex-col sm:flex-row items-start sm:items-center justify-between gap-4 py-4 border-t border-slate-800">
          <div>
            <h2 className="text-xl font-bold text-white">{activeLesson.title}</h2>
            <p className="text-xs text-slate-400 mt-1">Duration: {activeLesson.durationMinutes} mins</p>
          </div>

          <div className="flex items-center gap-3">
            <Button
              onClick={() => toggleComplete(activeLesson.id)}
              variant={completedLessons[activeLesson.id] ? 'secondary' : 'primary'}
              size="sm"
              className="gap-2 cursor-pointer"
            >
              <CheckCircle2 className={`w-4 h-4 ${completedLessons[activeLesson.id] ? 'text-emerald-400' : 'text-white'}`} />
              {completedLessons[activeLesson.id] ? 'Completed' : 'Mark as Complete'}
            </Button>
          </div>
        </div>
      </div>

      {/* Curriculum Sidebar */}
      <div className="w-full lg:w-96 bg-slate-950 border-t lg:border-t-0 lg:border-l border-slate-800 flex flex-col h-auto lg:h-[calc(100vh-4rem)] overflow-y-auto">
        <div className="p-4 border-b border-slate-800">
          <h3 className="font-bold text-white text-sm">Course Curriculum</h3>
          <p className="text-xs text-slate-400 mt-0.5">{course.modules.length} Modules</p>
        </div>

        <div className="flex-1 divide-y divide-slate-800/60">
          {course.modules.map((mod, modIdx) => (
            <div key={mod.id}>
              <div className="p-3 bg-slate-900/60 font-semibold text-xs text-slate-300">
                {mod.title}
              </div>
              <div>
                {mod.lessons.map((lesson) => {
                  const isActive = activeLesson.id === lesson.id;
                  const isDone = completedLessons[lesson.id] || lesson.isCompleted;

                  return (
                    <button
                      key={lesson.id}
                      onClick={() => setActiveLesson(lesson)}
                      className={`w-full p-3.5 flex items-start gap-3 text-left transition-colors cursor-pointer ${
                        isActive ? 'bg-brand-600/20 border-l-4 border-brand-500' : 'hover:bg-slate-900/40'
                      }`}
                    >
                      <div className="pt-0.5">
                        {isDone ? (
                          <CheckCircle2 className="w-4 h-4 text-emerald-400" />
                        ) : lesson.contentType === 'VIDEO' ? (
                          <Play className={`w-4 h-4 ${isActive ? 'text-brand-400' : 'text-slate-500'}`} />
                        ) : lesson.contentType === 'QUIZ' ? (
                          <HelpCircle className={`w-4 h-4 ${isActive ? 'text-amber-400' : 'text-slate-500'}`} />
                        ) : (
                          <FileText className={`w-4 h-4 ${isActive ? 'text-brand-400' : 'text-slate-500'}`} />
                        )}
                      </div>
                      <div className="flex-1">
                        <p className={`text-xs font-medium leading-snug ${isActive ? 'text-white font-bold' : 'text-slate-300'}`}>
                          {lesson.title}
                        </p>
                        <span className="text-[10px] text-slate-500">{lesson.durationMinutes}m</span>
                      </div>
                    </button>
                  );
                })}
              </div>
            </div>
          ))}
        </div>
      </div>
    </div>
  );
};
