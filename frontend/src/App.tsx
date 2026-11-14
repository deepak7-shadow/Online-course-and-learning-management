
import Login from "./Login";
import Signup from "./Signup";
import Dashboard from "./Dashboard";
import CourseDetails from "./CourseDetails";
import CourseCatalogue from "./CourseCatalogue";
import StudentProfile from "./StudentProfile";
import EnrolledCourses from "./EnrolledCourses";
import LessonPlayer from "./LessonPlayer";
import Certificate from "./Certificate";
import QuizResults from "./QuizResults";
import Assignments from "./Assignments";
import Notifications from "./Notifications";

function App() {
  if (window.location.pathname === "/login") {
    return <Login />;
  }

  if (window.location.pathname === "/signup") {
    return <Signup />;
  }

  if (window.location.pathname === "/dashboard") {
    return <Dashboard />;
  }

  if (window.location.pathname === "/course-details") {
    return <CourseDetails />;
  }

  if (window.location.pathname === "/courses") {
    return <CourseCatalogue />;
  }

  if (window.location.pathname === "/profile") {
    return <StudentProfile />;
  }

  if (window.location.pathname === "/enrolled-courses") {
    return <EnrolledCourses />;
  }

  if (window.location.pathname === "/lesson-player") {
    return <LessonPlayer />;
  }

  if (window.location.pathname === "/certificate") {
    return <Certificate />;
  }

  if (window.location.pathname === "/quiz-results") {
    return <QuizResults />;
  }

  if (window.location.pathname === "/assignments") {
    return <Assignments />;
  }

  if (window.location.pathname === "/notifications") {
    return <Notifications />;
  }

  return (
    <div className="website">

      {/* NAVBAR */}
      <nav className="navbar">
        <a href="#" className="logo">
          <span className="logo-icon">✦</span>
          LearnSpace<span className="logo-dot">.</span>
        </a>

        <div className="nav-links">
          <a href="#">Home</a>
          <a href="#courses">Explore Courses</a>
          <a href="#features">Why LearnSpace</a>
          <a href="#about">About</a>
        </div>

        <div className="nav-actions">
          <a href="/login" className="login-btn">Log in</a>
          <a href="/signup" className="signup-btn">Get Started ↗</a>
        </div>
      </nav>

      {/* HERO */}
      <section className="hero">
        <div className="hero-content">

          <div className="hero-label">
            ✨ THE FUTURE OF LEARNING STARTS HERE
          </div>

          <h1>
            Unlock Your
            <span> Potential.</span>
            <br />
            Learn Without
            <span> Limits.</span>
          </h1>

          <p>
            Discover knowledge, master new skills and
            transform your future with learning experiences
            designed around you.
          </p>

          <div className="hero-buttons">
            <a href="#courses" className="explore-btn">
              Explore Courses ↗
            </a>
            <a href="#about" className="watch-btn">
              Discover More →
            </a>
          </div>

          <div className="hero-proof">
            <div className="avatar-stack">
              <span>👩🏻</span>
              <span>👨🏻</span>
              <span>👩🏽</span>
            </div>
            <div>
              <strong>Learn. Grow. Achieve.</strong>
              <small>Your journey begins here.</small>
            </div>
          </div>
        </div>

        {/* DASHBOARD VISUAL */}
        <div className="hero-visual">
          <div className="glow-circle"></div>

          <div className="learning-card">
            <div className="dashboard-top">
              <div>
                <small>YOUR LEARNING SPACE</small>
                <h3>Welcome back, Learner!</h3>
              </div>
              <div className="profile-icon">👩🏻‍💻</div>
            </div>

            <div className="progress-box">
              <div className="progress-heading">
                <span>Weekly Progress</span>
                <strong>78%</strong>
              </div>
              <div className="progress-track">
                <div className="progress-fill"></div>
              </div>
              <small>You're making great progress!</small>
            </div>

            <div className="dashboard-course">
              <div className="mini-icon">💻</div>
              <div className="mini-details">
                <strong>Web Development</strong>
                <small>Chapter 06 · Frontend Basics</small>
              </div>
              <span className="course-check">✓</span>
            </div>

            <div className="dashboard-course">
              <div className="mini-icon purple-icon">🐍</div>
              <div className="mini-details">
                <strong>Python Programming</strong>
                <small>Chapter 03 · Fundamentals</small>
              </div>
              <span className="course-check">↗</span>
            </div>

            <div className="dashboard-bottom">
              <span>🔥 Keep your learning streak alive!</span>
            </div>
          </div>

          <div className="floating-badge badge-one">
            <span>🏆</span>
            <div>
              <strong>Keep Growing</strong>
              <small>Every day counts</small>
            </div>
          </div>

          <div className="floating-badge badge-two">
            <span>⭐</span>
            <div>
              <strong>Learn Smarter</strong>
              <small>One skill at a time</small>
            </div>
          </div>
        </div>
      </section>

      {/* STATISTICS */}
      <section className="stats">
        <div>
          <h2>Learn</h2>
          <p>At your own pace</p>
        </div>
        <div>
          <h2>Grow</h2>
          <p>With expert knowledge</p>
        </div>
        <div>
          <h2>Achieve</h2>
          <p>With every new skill</p>
        </div>
        <div>
          <h2>Repeat</h2>
          <p>Keep moving forward</p>
        </div>
      </section>

      {/* COURSES */}
      <section className="courses" id="courses">
        <div className="section-heading">
          <span>LEARN SOMETHING NEW</span>
          <h2>Explore Our <span>Courses</span></h2>
          <p>
            Find the right course to build your skills
            and take your next step.
          </p>
        </div>

        <div className="course-container">

          <div className="course-card">
            <div className="course-icon icon-blue">💻</div>
            <span className="course-tag">DEVELOPMENT</span>
            <h3>Web Development</h3>
            <p>Build beautiful websites and learn modern frontend technologies.</p>
            <div className="course-info">
              <span>📚 Beginner friendly</span>
              <a href="/course-details">↗ Explore</a>
            </div>
          </div>

          <div className="course-card">
            <div className="course-icon icon-purple">🐍</div>
            <span className="course-tag">PROGRAMMING</span>
            <h3>Python Programming</h3>
            <p>Learn coding fundamentals and create your own applications.</p>
            <div className="course-info">
              <span>📚 Beginner friendly</span>
              <a href="/course-details">↗ Explore</a>
            </div>
          </div>

          <div className="course-card">
            <div className="course-icon icon-pink">📊</div>
            <span className="course-tag">TECHNOLOGY</span>
            <h3>Data Science</h3>
            <p>Discover data analysis, visualization and machine learning.</p>
            <div className="course-info">
              <span>📚 Intermediate</span>
              <a href="/course-details">↗ Explore</a>
            </div>
          </div>
        </div>
      </section>

      {/* FEATURES */}
      <section className="features" id="features">
        <div className="section-heading">
          <span>WHY LEARNSPACE?</span>
          <h2>Learning Made <span>Better.</span></h2>
          <p>Everything you need to make your learning journey meaningful.</p>
        </div>

        <div className="feature-grid">
          <div className="feature-item">
            <span>🎯</span>
            <h3>Learn Your Way</h3>
            <p>Study at your own pace and explore topics that interest you.</p>
          </div>

          <div className="feature-item">
            <span>🚀</span>
            <h3>Grow Your Skills</h3>
            <p>Build practical knowledge for your academic and career goals.</p>
          </div>

          <div className="feature-item">
            <span>🌐</span>
            <h3>Learn Anywhere</h3>
            <p>Access your learning resources whenever you need them.</p>
          </div>

          <div className="feature-item">
            <span>🏅</span>
            <h3>Track Progress</h3>
            <p>Keep track of your learning journey and achievements.</p>
          </div>
        </div>
      </section>

      {/* ABOUT */}
      <section className="about" id="about">
        <div className="about-content">
          <span>YOUR FUTURE, YOUR JOURNEY</span>
          <h2>
            Small Steps Today.
            <br />
            <span>Big Achievements Tomorrow.</span>
          </h2>
          <p>
            LearnSpace is designed to make education
            accessible, engaging and meaningful.
            We believe everyone deserves the opportunity
            to learn, grow and achieve more.
          </p>
          <a href="#courses" className="explore-btn">
            Start Exploring ↗
          </a>
        </div>
        <div className="about-symbol">✦</div>
      </section>

      {/* FOOTER */}
      <footer id="contact">
        <div className="footer-top">
          <a href="#" className="logo">
            <span className="logo-icon">✦</span>
            LearnSpace<span className="logo-dot">.</span>
          </a>
          <p>Learn something new. Become something more.</p>
        </div>
        <div className="footer-bottom">
          <span>© 2026 LearnSpace. All rights reserved.</span>
          <span>Designed for curious minds. 💜</span>
        </div>
      </footer>

    </div>
  );
}

export default App;
