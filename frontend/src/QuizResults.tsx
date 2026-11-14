
import { useState } from "react";

const courseQuizzes = {
  "web-development": {
    title: "Web Development Final Quiz",
    lessonsCount: 6,
    questions: [
      { question: "What does HTML stand for?", options: ["Hyper Text Markup Language", "High Text Machine Language", "Hyper Tool Multi Language", "Home Text Markup Language"], answer: 0 },
      { question: "Which language is used to style web pages?", options: ["Python", "CSS", "Java", "C"], answer: 1 },
      { question: "Which language makes web pages interactive?", options: ["HTML", "CSS", "JavaScript", "SQL"], answer: 2 },
      { question: "Which HTML tag creates a link?", options: ["<link>", "<a>", "<p>", "<h1>"], answer: 1 },
      { question: "Which symbol is used for an ID selector in CSS?", options: [".", "#", "*", "&"], answer: 1 },
    ],
  },
  "python-programming": {
    title: "Python Programming Final Quiz",
    lessonsCount: 6,
    questions: [
      { question: "Which keyword is used to define a function in Python?", options: ["function", "def", "fun", "define"], answer: 1 },
      { question: "Which symbol is used for comments in Python?", options: ["//", "/*", "#", "<!--"], answer: 2 },
      { question: "Which data type stores True or False?", options: ["int", "str", "bool", "float"], answer: 2 },
      { question: "Which loop is used to iterate over a sequence?", options: ["for", "switch", "case", "repeat"], answer: 0 },
      { question: "Which method adds an item to a list?", options: ["add()", "insertEnd()", "append()", "push()"], answer: 2 },
    ],
  },
  "data-science": {
    title: "Data Science Final Quiz",
    lessonsCount: 6,
    questions: [
      { question: "What is data science mainly used for?", options: ["Analyzing data and finding insights", "Only designing websites", "Playing games", "Creating hardware"], answer: 0 },
      { question: "Which language is commonly used in data science?", options: ["HTML", "Python", "CSS", "XML"], answer: 1 },
      { question: "What is data cleaning?", options: ["Deleting all data", "Making data colorful", "Correcting and preparing data", "Printing data"], answer: 2 },
      { question: "Which chart is useful for comparing categories?", options: ["Bar chart", "Text file", "Audio file", "Folder"], answer: 0 },
      { question: "What does a dataset contain?", options: ["Only images", "A collection of data", "Only programs", "Only videos"], answer: 1 },
    ],
  },
  "ui-ux-design": {
    title: "UI/UX Design Final Quiz",
    lessonsCount: 6,
    questions: [
      { question: "What does UI stand for?", options: ["User Interface", "Universal Internet", "User Information", "Unique Interaction"], answer: 0 },
      { question: "What does UX stand for?", options: ["User Extension", "User Experience", "Universal Experience", "Unique Example"], answer: 1 },
      { question: "What is a wireframe?", options: ["A final website", "A programming language", "A basic layout plan", "A database"], answer: 2 },
      { question: "Which principle helps make text easy to read?", options: ["Typography", "Encryption", "Networking", "Debugging"], answer: 0 },
      { question: "What is a prototype?", options: ["A database table", "An early interactive model", "A computer virus", "A text document"], answer: 1 },
    ],
  },
  "java-programming": {
    title: "Java Programming Final Quiz",
    lessonsCount: 6,
    questions: [
      { question: "Which keyword is used to create a class in Java?", options: ["function", "class", "define", "struct"], answer: 1 },
      { question: "Which method is the starting point of a Java program?", options: ["start()", "run()", "main()", "begin()"], answer: 2 },
      { question: "Which data type stores whole numbers?", options: ["int", "String", "boolean", "char"], answer: 0 },
      { question: "Which keyword is used for inheritance in Java?", options: ["implements", "inherits", "extends", "superclass"], answer: 2 },
      { question: "Which symbol ends a Java statement?", options: [".", ":", ";", ","], answer: 2 },
    ],
  },
  "machine-learning": {
    title: "Machine Learning Final Quiz",
    lessonsCount: 6,
    questions: [
      { question: "What is machine learning?", options: ["A type of computer hardware", "A system that learns patterns from data", "A text editor", "A website design tool"], answer: 1 },
      { question: "Which is a type of machine learning?", options: ["Supervised learning", "HTML learning", "Manual typing", "Web designing"], answer: 0 },
      { question: "What is training data?", options: ["Data used to train a model", "A computer game", "A programming language", "A design template"], answer: 0 },
      { question: "Which task predicts a numerical value?", options: ["Classification", "Regression", "Sorting", "Formatting"], answer: 1 },
      { question: "Why do we evaluate a machine learning model?", options: ["To change its color", "To delete the dataset", "To measure its performance", "To create a website"], answer: 2 },
    ],
  },
};

type CourseSlug = keyof typeof courseQuizzes;

function QuizResults() {
  const params = new URLSearchParams(window.location.search);
  const requestedCourse = params.get("course");

  const courseSlug: CourseSlug =
    requestedCourse && requestedCourse in courseQuizzes
      ? (requestedCourse as CourseSlug)
      : "web-development";

  const course = courseQuizzes[courseSlug];

  const progressKey =
    courseSlug === "web-development"
      ? "webDevelopmentCompletedLessons"
      : `learnspace_${courseSlug}_completedLessons`;

  const quizPassedKey =
    courseSlug === "web-development"
      ? "webDevelopmentQuizPassed"
      : `learnspace_${courseSlug}_quizPassed`;

  const quizScoreKey =
    courseSlug === "web-development"
      ? "webDevelopmentQuizScore"
      : `learnspace_${courseSlug}_quizScore`;

  const savedLessons = localStorage.getItem(progressKey);
  const completedLessons: number[] = savedLessons
    ? JSON.parse(savedLessons)
    : [];

  const allLessonsCompleted = Array.from(
    { length: course.lessonsCount },
    (_, index) => index
  ).every((index) => completedLessons.includes(index));

  const [answers, setAnswers] = useState<number[]>([]);
  const [score, setScore] = useState<number | null>(null);

  const answeredCount = answers.filter(
    (answer) => answer !== undefined
  ).length;

  const handleAnswer = (questionIndex: number, optionIndex: number) => {
    const updatedAnswers = [...answers];
    updatedAnswers[questionIndex] = optionIndex;
    setAnswers(updatedAnswers);
  };

  const submitQuiz = () => {
    if (answeredCount !== course.questions.length) return;

    const totalScore = course.questions.reduce(
      (total, question, index) =>
        total + (answers[index] === question.answer ? 1 : 0),
      0
    );

    setScore(totalScore);

    if (totalScore >= 3) {
      localStorage.setItem(quizPassedKey, "true");
      localStorage.setItem(quizScoreKey, String(totalScore));
    } else {
      localStorage.removeItem(quizPassedKey);
      localStorage.removeItem(quizScoreKey);
    }
  };

  const tryAgain = () => {
    setAnswers([]);
    setScore(null);
  };

  if (!allLessonsCompleted) {
    return (
      <div className="quiz-page">
        <nav className="lesson-nav">
          <a href="/" className="profile-logo">LearnSpace.</a>
          <a href="/enrolled-courses">← Back to My Courses</a>
        </nav>

        <main className="quiz-container quiz-locked">
          <div className="quiz-lock-icon">🔒</div>
          <p className="enrolled-label">COURSE REQUIREMENT</p>
          <h1>Final Quiz Locked</h1>
          <p>
            Complete all {course.lessonsCount} lessons before attempting
            your final assessment.
          </p>

          <div className="quiz-lock-progress">
            <div className="course-progress-heading">
              <strong>Lesson Progress</strong>
              <strong>{completedLessons.length}/{course.lessonsCount}</strong>
            </div>
            <div className="progress-track">
              <div
                className="progress-fill"
                style={{
                  width: `${(completedLessons.length / course.lessonsCount) * 100}%`,
                }}
              />
            </div>
          </div>

          <a
            className="quiz-primary-link"
            href={`/lesson-player?course=${courseSlug}`}
          >
            Continue Learning →
          </a>
        </main>
      </div>
    );
  }

  return (
    <div className="quiz-page">
      <nav className="lesson-nav">
        <a href="/" className="profile-logo">LearnSpace.</a>
        <a href="/enrolled-courses">← Back to My Courses</a>
      </nav>

      <main className="quiz-container">
        <header className="quiz-header">
          <p className="enrolled-label">FINAL ASSESSMENT</p>
          <h1>{course.title}</h1>
          <p>
            Take your time and choose the best answer for each question.
          </p>

          <div className="quiz-info-row">
            <span>📝 5 Questions</span>
            <span>⏱ Self-paced</span>
            <span>🎯 Pass mark: 3/5</span>
          </div>
        </header>

        {score === null ? (
          <>
            <section className="quiz-progress-card">
              <div className="course-progress-heading">
                <strong>Your Progress</strong>
                <strong>{answeredCount}/{course.questions.length} answered</strong>
              </div>
              <div className="progress-track">
                <div
                  className="progress-fill"
                  style={{
                    width: `${(answeredCount / course.questions.length) * 100}%`,
                  }}
                />
              </div>
            </section>

            <section className="quiz-questions-list">
              {course.questions.map((question, index) => (
                <article className="quiz-question" key={index}>
                  <div className="quiz-question-heading">
                    <span className="quiz-question-number">
                      QUESTION {String(index + 1).padStart(2, "0")}
                    </span>
                    <span className="quiz-question-count">
                      {index + 1} of {course.questions.length}
                    </span>
                  </div>

                  <h3>{question.question}</h3>

                  <div className="quiz-options-list">
                    {question.options.map((option, optionIndex) => (
                      <label
                        key={optionIndex}
                        className={`quiz-option ${
                          answers[index] === optionIndex
                            ? "quiz-option-selected"
                            : ""
                        }`}
                      >
                        <input
                          type="radio"
                          name={`question-${index}`}
                          checked={answers[index] === optionIndex}
                          onChange={() =>
                            handleAnswer(index, optionIndex)
                          }
                        />
                        <span className="quiz-option-letter">
                          {String.fromCharCode(65 + optionIndex)}
                        </span>
                        <span className="quiz-option-text">{option}</span>
                      </label>
                    ))}
                  </div>
                </article>
              ))}
            </section>

            <div className="quiz-submit-section">
              <p>
                {answeredCount === course.questions.length
                  ? "All questions answered. You can submit your quiz."
                  : `Please answer ${course.questions.length - answeredCount} more question(s).`}
              </p>
              <button
                className="quiz-submit-button"
                onClick={submitQuiz}
                disabled={answeredCount !== course.questions.length}
              >
                Submit Final Quiz →
              </button>
            </div>
          </>
        ) : (
          <section className="quiz-result">
            <div className="quiz-result-icon">
              {score >= 3 ? "🏆" : "📚"}
            </div>

            <p className="enrolled-label">ASSESSMENT COMPLETED</p>
            <h2>{score >= 3 ? "Congratulations!" : "Keep Practicing!"}</h2>
            <p>
              {score >= 3
                ? "You have successfully passed your final assessment."
                : "You haven't reached the passing score yet. Review your lessons and try again."}
            </p>

            <div className="quiz-score-circle">
              <strong>{score}/5</strong>
              <span>Your Score</span>
            </div>

            <p className="quiz-result-status">
              {score >= 3 ? "✓ PASSED" : "NOT PASSED"}
            </p>

            <p className="quiz-result-note">
              Passing score: 3 out of 5
            </p>

            {score >= 3 ? (
              <div className="quiz-result-actions">
                <a
                  className="quiz-primary-link"
                  href={`/certificate?course=${courseSlug}`}
                >
                  View My Certificate →
                </a>
                <a href="/dashboard" className="quiz-secondary-link">
                  Back to Dashboard
                </a>
              </div>
            ) : (
              <div className="quiz-result-actions">
                <button
                  className="quiz-submit-button"
                  onClick={tryAgain}
                >
                  Try Again
                </button>
                <a
                  href={`/lesson-player?course=${courseSlug}`}
                  className="quiz-secondary-link"
                >
                  Review Lessons
                </a>
              </div>
            )}
          </section>
        )}
      </main>
    </div>
  );
}

export default QuizResults;
