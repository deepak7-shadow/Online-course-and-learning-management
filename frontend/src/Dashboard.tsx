
const courses = [
  {
    id: "web-development",
    title: "Web Development",
    category: "DEVELOPMENT",
    description: "Learn HTML, CSS and JavaScript.",
    icon: "💻",
    progressKey: "webDevelopmentCompletedLessons",
    quizKey: "webDevelopmentQuizPassed",
    certificate: "webDevelopment",
  },
  {
    id: "python-programming",
    title: "Python Programming",
    category: "PROGRAMMING",
    description: "Master programming fundamentals.",
    icon: "🐍",
    progressKey: "learnspace_python-programming_completedLessons",
    quizKey: "learnspace_python-programming_quizPassed",
    certificate: "python-programming",
  },
  {
    id: "data-science",
    title: "Data Science",
    category: "TECHNOLOGY",
    description: "Explore data and machine learning.",
    icon: "📊",
    progressKey: "learnspace_data-science_completedLessons",
    quizKey: "learnspace_data-science_quizPassed",
    certificate: "data-science",
  },
  {
    id: "ui-ux-design",
    title: "UI/UX Design",
    category: "DESIGN",
    description: "Learn user interface and user experience design.",
    icon: "🎨",
    progressKey: "learnspace_ui-ux-design_completedLessons",
    quizKey: "learnspace_ui-ux-design_quizPassed",
    certificate: "ui-ux-design",
  },
  {
    id: "java-programming",
    title: "Java Programming",
    category: "PROGRAMMING",
    description: "Understand Java programming and OOP concepts.",
    icon: "☕",
    progressKey: "learnspace_java-programming_completedLessons",
    quizKey: "learnspace_java-programming_quizPassed",
    certificate: "java-programming",
  },
  {
    id: "machine-learning",
    title: "Machine Learning",
    category: "TECHNOLOGY",
    description: "Discover the fundamentals of machine learning.",
    icon: "🤖",
    progressKey: "learnspace_machine-learning_completedLessons",
    quizKey: "learnspace_machine-learning_quizPassed",
    certificate: "machine-learning",
  },
];

function Dashboard() {
  const savedProfile = localStorage.getItem("learnSpaceProfile");

  const studentName = savedProfile
    ? JSON.parse(savedProfile).name
    : "Student";

  const courseProgress = courses.map((course) => {
    const savedLessons = localStorage.getItem(course.progressKey);

    const completedLessons: number[] = savedLessons
      ? JSON.parse(savedLessons)
      : [];

    const progress = Math.round(
      (completedLessons.length / 6) * 100
    );

    const quizPassed =
      localStorage.getItem(course.quizKey) === "true";

    return {
      ...course,
      completedCount: completedLessons.length,
      progress,
      quizPassed,
    };
  });

  const totalCompletedLessons = courseProgress.reduce(
    (total, course) => total + course.completedCount,
    0
  );

  const totalLessons = courses.length * 6;

  const overallProgress = Math.round(
    (totalCompletedLessons / totalLessons) * 100
  );

  const passedQuizzes = courseProgress.filter(
    (course) => course.quizPassed
  ).length;

  const firstIncompleteCourse =
    courseProgress.find((course) => course.completedCount < 6) ||
    courseProgress[0];

  return (
    <div className="student-dashboard">

      {/* SIDEBAR */}
      <aside className="dashboard-sidebar">
        <a href="/" className="logo">
          <span className="logo-icon">✦</span>
          LearnSpace<span className="logo-dot">.</span>
        </a>

        <p className="sidebar-heading">MENU</p>

        <a href="/dashboard" className="sidebar-link active">
          🏠 Dashboard
        </a>

        <a href="/enrolled-courses" className="sidebar-link">
          📚 My Courses
        </a>

        <a href="/assignments" className="sidebar-link">
          📝 Assignments
        </a>

        <a href="/notifications" className="sidebar-link">
          🔔 Notifications
        </a>

        <a href="/quiz-results" className="sidebar-link">
          🧠 Quiz & Results
        </a>

        <a href="#progress" className="sidebar-link">
          📊 My Progress
        </a>

        <a href="/profile" className="sidebar-link">
          👤 My Profile
        </a>

        <div className="sidebar-bottom">
          <a href="/" className="sidebar-link">
            ↩ Back to Website
          </a>

          <a href="/login" className="sidebar-link">
            🚪 Log Out
          </a>
        </div>
      </aside>

      {/* MAIN CONTENT */}
      <main className="dashboard-main">

        <header className="dashboard-header">
          <div>
            <h1>Student Dashboard</h1>
            <p>Welcome back! Ready to learn something new today?</p>
          </div>

          <div className="dashboard-user">
            <span>👩🏻‍🎓</span>
            <div>
              <strong>{studentName}</strong>
              <small>Learning Member</small>
            </div>
          </div>
        </header>

        {/* WELCOME BANNER */}
        <section className="dashboard-welcome">
          <div>
            <span>YOUR LEARNING JOURNEY</span>
            <h2>
              Keep Learning.
              <br />
              Keep Growing! 🚀
            </h2>
            <p>
              Every lesson brings you one step closer to your goals.
            </p>

            <a href={`/lesson-player?course=${firstIncompleteCourse.id}`}>
              Continue Learning →
            </a>
          </div>

          <div className="welcome-emoji">🎓</div>
        </section>

        {/* STATISTICS */}
        <section className="dashboard-stats" id="progress">

          <div className="dashboard-stat-card">
            <span>📚</span>
            <h2>{courses.length.toString().padStart(2, "0")}</h2>
            <p>Enrolled Courses</p>
          </div>

          <div className="dashboard-stat-card">
            <span>✅</span>
            <h2>{totalCompletedLessons}</h2>
            <p>Lessons Completed</p>
          </div>

          <div className="dashboard-stat-card">
            <span>🧠</span>
            <h2>{passedQuizzes}/{courses.length}</h2>
            <p>Quizzes Passed</p>
          </div>

          <div className="dashboard-stat-card">
            <span>🏆</span>
            <h2>{overallProgress}%</h2>
            <p>Overall Progress</p>
          </div>

        </section>

        {/* MY COURSES */}
        <section className="dashboard-section" id="my-courses">

          <div className="dashboard-section-heading">
            <div>
              <span>YOUR CLASSROOM</span>
              <h2>My Courses</h2>
            </div>

            <a href="/courses">Explore More →</a>
          </div>

          <div className="enrolled-courses">

            {courseProgress.map((course) => (
              <div className="enrolled-course-card" key={course.id}>

                <div className="enrolled-course-icon">
                  {course.icon}
                </div>

                <span className="course-tag">
                  {course.category}
                </span>

                <h3>{course.title}</h3>

                <p>{course.description}</p>

                <div className="course-progress-heading">
                  <span>
                    Lessons: {course.completedCount}/6
                  </span>

                  <strong>{course.progress}%</strong>
                </div>

                <div className="progress-track">
                  <div
                    className="progress-fill"
                    style={{ width: `${course.progress}%` }}
                  ></div>
                </div>

                <p>
                  Quiz Status: {course.quizPassed ? "Passed ✅" : "Pending"}
                </p>

                <a href={`/lesson-player?course=${course.id}`}>
                  Continue Course →
                </a>

                {course.quizPassed && (
                  <p>
                    <a href={`/certificate?course=${course.id}`}>
                      🏆 View Certificate →
                    </a>
                  </p>
                )}

              </div>
            ))}

          </div>
        </section>

        {/* CERTIFICATES */}
        <section className="dashboard-section">

          <div className="dashboard-section-heading">
            <div>
              <span>YOUR ACHIEVEMENTS</span>
              <h2>Course Certificates</h2>
            </div>
          </div>

          {courseProgress.some((course) => course.quizPassed) ? (

            courseProgress
              .filter((course) => course.quizPassed)
              .map((course) => (
                <div className="assignment-card" key={course.id}>
                  <div>
                    <strong>{course.title} Certificate 🏆</strong>
                    <p>Congratulations! You passed the final quiz.</p>
                  </div>

                  <a href={`/certificate?course=${course.id}`}>
                    View Certificate →
                  </a>
                </div>
              ))

          ) : (

            <div className="assignment-card">
              <div>
                <strong>Certificates Locked 🔒</strong>
                <p>
                  Complete all lessons and pass a final quiz to unlock
                  your certificate.
                </p>
              </div>

              <a href={`/lesson-player?course=${firstIncompleteCourse.id}`}>
                Continue Learning →
              </a>
            </div>

          )}

        </section>

        {/* ASSIGNMENTS */}
        <section className="dashboard-section" id="assignments">

          <div className="dashboard-section-heading">
            <div>
              <span>STAY ON TRACK</span>
              <h2>Upcoming Assignments</h2>
            </div>

            <a href="/assignments">View All →</a>
          </div>

          <div className="assignment-card">
            <div>
              <strong>HTML & CSS Mini Project</strong>
              <p>Web Development · Practice Task</p>
            </div>

            <span className="assignment-status">Pending</span>
          </div>

          <div className="assignment-card">
            <div>
              <strong>Python Basics Exercise</strong>
              <p>Python Programming · Coding Task</p>
            </div>

            <span className="assignment-status">Pending</span>
          </div>

        </section>

        <footer className="dashboard-footer">
          © 2026 LearnSpace. Keep learning, keep growing. 💜
        </footer>

      </main>
    </div>
  );
}

export default Dashboard;
