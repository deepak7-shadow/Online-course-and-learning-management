
const courseCertificates = {
  "web-development": { title: "Complete Web Development" },
  "python-programming": { title: "Python Programming" },
  "data-science": { title: "Data Science" },
  "ui-ux-design": { title: "UI/UX Design" },
  "java-programming": { title: "Java Programming" },
  "machine-learning": { title: "Machine Learning" },
};

type CourseSlug = keyof typeof courseCertificates;

function Certificate() {
  const params = new URLSearchParams(window.location.search);
  const requestedCourse = params.get("course");

  const courseSlug: CourseSlug =
    requestedCourse && requestedCourse in courseCertificates
      ? (requestedCourse as CourseSlug)
      : "web-development";

  const course = courseCertificates[courseSlug];

  const passedKey =
    courseSlug === "web-development"
      ? "webDevelopmentQuizPassed"
      : `learnspace_${courseSlug}_quizPassed`;

  const scoreKey =
    courseSlug === "web-development"
      ? "webDevelopmentQuizScore"
      : `learnspace_${courseSlug}_quizScore`;

  const hasPassed = localStorage.getItem(passedKey) === "true";
  const score = localStorage.getItem(scoreKey);

  const savedProfile = localStorage.getItem("learnSpaceProfile");

  let studentName = "LearnSpace Student";

  try {
    if (savedProfile) {
      const profile = JSON.parse(savedProfile);
      studentName = profile.name || studentName;
    }
  } catch {
    studentName = "LearnSpace Student";
  }

  const completionDate = new Date().toLocaleDateString("en-IN", {
    day: "numeric",
    month: "long",
    year: "numeric",
  });

  if (!hasPassed) {
    return (
      <div className="certificate-page certificate-locked">
        <div className="certificate-locked-card">
          <a href="/" className="profile-logo">LearnSpace.</a>

          <div className="certificate-lock-icon">🔒</div>

          <p className="enrolled-label">YOUR ACHIEVEMENT</p>
          <h1>Certificate Locked</h1>

          <p>
            Complete all course lessons and pass the final quiz to unlock
            your certificate.
          </p>

          <a
            className="certificate-primary-button"
            href={`/quiz-results?course=${courseSlug}`}
          >
            Go to Final Quiz →
          </a>

          <a className="certificate-back-link" href="/dashboard">
            Back to Dashboard
          </a>
        </div>
      </div>
    );
  }

  return (
    <div className="certificate-page">
      <div className="certificate-toolbar">
        <a href="/dashboard" className="certificate-back-link">
          ← Back to Dashboard
        </a>

        <button
          className="certificate-print-button"
          onClick={() => window.print()}
        >
          ↓ Download / Print Certificate
        </button>
      </div>

      <main className="certificate-content">
        <div className="certificate-border">
          <div className="certificate-inner">
            <div className="certificate-brand">
              <span className="certificate-brand-symbol">L</span>
              <span>LearnSpace.</span>
            </div>

            <p className="certificate-kicker">
              RECOGNITION OF ACHIEVEMENT
            </p>

            <div className="certificate-seal">★</div>

            <h1 className="certificate-title">
              Certificate
              <span>of Completion</span>
            </h1>

            <p className="certificate-recipient">
              This certificate is proudly presented to
            </p>

            <h2 className="certificate-student-name">
              {studentName}
            </h2>

            <div className="certificate-divider">
              <span />
              <span>◆</span>
              <span />
            </div>

            <p className="certificate-course-intro">
              For successfully completing the course
            </p>

            <h2 className="certificate-course-name">
              {course.title}
            </h2>

            <p className="certificate-congratulations">
              In recognition of your dedication, effort and commitment
              to continuous learning.
            </p>

            <div className="certificate-score">
              <span>FINAL QUIZ SCORE</span>
              <strong>{score}/5</strong>
            </div>

            <div className="certificate-footer">
              <div>
                <strong>{completionDate}</strong>
                <span>Date of Completion</span>
              </div>

              <div className="certificate-signature">
                <strong>LearnSpace</strong>
                <span>Learning Management System</span>
              </div>
            </div>

            <p className="certificate-id">
              LEARNSPACE · CERTIFICATE OF COMPLETION
            </p>
          </div>
        </div>
      </main>
    </div>
  );
}

export default Certificate;
