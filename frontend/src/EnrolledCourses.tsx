
import { useState } from "react";

function EnrolledCourses() {
  const [courses] = useState(() => {
    const courseList = [
      {
        id: "web-development",
        title: "Complete Web Development",
        category: "Development",
        icon: "💻",
        totalLessons: 6,
        progressKey: "webDevelopmentCompletedLessons",
        quizKey: "webDevelopmentQuizPassed",
      },
      {
        id: "python-programming",
        title: "Python Programming",
        category: "Programming",
        icon: "🐍",
        totalLessons: 6,
        progressKey: "learnspace_python-programming_completedLessons",
        quizKey: "learnspace_python-programming_quizPassed",
      },
      {
        id: "data-science",
        title: "Data Science Fundamentals",
        category: "Data Science",
        icon: "📊",
        totalLessons: 6,
        progressKey: "learnspace_data-science_completedLessons",
        quizKey: "learnspace_data-science_quizPassed",
      },
      {
        id: "ui-ux-design",
        title: "UI/UX Design Masterclass",
        category: "Design",
        icon: "🎨",
        totalLessons: 6,
        progressKey: "learnspace_ui-ux-design_completedLessons",
        quizKey: "learnspace_ui-ux-design_quizPassed",
      },
      {
        id: "java-programming",
        title: "Java Programming",
        category: "Programming",
        icon: "☕",
        totalLessons: 6,
        progressKey: "learnspace_java-programming_completedLessons",
        quizKey: "learnspace_java-programming_quizPassed",
      },
      {
        id: "machine-learning",
        title: "Machine Learning",
        category: "Data Science",
        icon: "🤖",
        totalLessons: 6,
        progressKey: "learnspace_machine-learning_completedLessons",
        quizKey: "learnspace_machine-learning_quizPassed",
      },
    ];

    return courseList.map((course) => {
      const saved = localStorage.getItem(course.progressKey);

      const completedLessons: number[] = saved
        ? JSON.parse(saved)
        : [];

      const completedCount = completedLessons.filter(
        (lesson) =>
          Number.isInteger(lesson) &&
          lesson >= 0 &&
          lesson < course.totalLessons
      ).length;

      return {
        ...course,
        completedCount,
        progress: Math.round(
          (completedCount / course.totalLessons) * 100
        ),
        quizPassed: localStorage.getItem(course.quizKey) === "true",
      };
    });
  });

  return (
    <div className="enrolled-page">
      <nav className="enrolled-nav">
        <a href="/" className="profile-logo">LearnSpace.</a>
        <a href="/dashboard">← Back to Dashboard</a>
      </nav>

      <main className="enrolled-container">
        <p className="enrolled-label">MY LEARNING</p>

        <h1>My Enrolled Courses</h1>

        <p className="enrolled-subtitle">
          Continue learning and track your course progress.
        </p>

        <div className="enrolled-grid">
          {courses.map((course) => (
            <div className="enrolled-card" key={course.id}>
              <div className="enrolled-icon">{course.icon}</div>

              <span className="enrolled-category">
                {course.category}
              </span>

              <h2>{course.title}</h2>

              <p>
                {course.completedCount}/{course.totalLessons} lessons completed
              </p>

              <div className="enrolled-progress">
                <div style={{ width: `${course.progress}%` }} />
              </div>

              <div className="enrolled-progress-info">
                <span>Progress</span>
                <strong>{course.progress}%</strong>
              </div>

              <a href={`/lesson-player?course=${course.id}`}>
                Continue Learning →
              </a>

              {course.quizPassed && (
                <p>
                  <a href={`/certificate?course=${course.id}`}>
                    🏆 View Certificate
                  </a>
                </p>
              )}
            </div>
          ))}
        </div>
      </main>
    </div>
  );
}

export default EnrolledCourses;
