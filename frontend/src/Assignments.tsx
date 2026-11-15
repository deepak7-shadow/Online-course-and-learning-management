
import { useState } from "react";

const initialAssignments = [
  {
    id: 1,
    title: "HTML & CSS Mini Project",
    course: "Web Development",
    description: "Create a simple personal portfolio webpage using HTML and CSS.",
    deadline: "30 November 2026",
  },
  {
    id: 2,
    title: "Python Basics Exercise",
    course: "Python Programming",
    description: "Write Python programs using variables, conditions and loops.",
    deadline: "3 December 2026",
  },
  {
    id: 3,
    title: "Data Visualization Task",
    course: "Data Science",
    description: "Create a simple chart to represent a sample dataset.",
    deadline: "6 December 2026",
  },
  {
    id: 4,
    title: "UI Design Assignment",
    course: "UI/UX Design",
    description: "Design a simple login page using basic UI principles.",
    deadline: "10 December 2026",
  },
  {
    id: 5,
    title: "Java Programming Exercise",
    course: "Java Programming",
    description: "Write programs using classes, objects and inheritance.",
    deadline: "14 December 2026",
  },
  {
    id: 6,
    title: "Machine Learning Task",
    course: "Machine Learning",
    description: "Explain the difference between supervised and unsupervised learning.",
    deadline: "18 December 2026",
  },
];

type Assignment = typeof initialAssignments[number];

function Assignments() {
  const [assignments, setAssignments] = useState<Assignment[]>(() => {
    const saved = localStorage.getItem("learnspaceAssignments");
    const submittedIds: number[] = saved ? JSON.parse(saved) : [];

    return initialAssignments.map((assignment) => ({
      ...assignment,
      ...(submittedIds.includes(assignment.id) ? { submitted: true } : {}),
    }));
  });

  const [filter, setFilter] = useState("All");

  const submittedCount = assignments.filter(
    (assignment) => "submitted" in assignment && assignment.submitted
  ).length;

  const filteredAssignments = assignments.filter((assignment) => {
    if (filter === "Submitted") {
      return "submitted" in assignment && assignment.submitted;
    }

    if (filter === "Pending") {
      return !("submitted" in assignment && assignment.submitted);
    }

    return true;
  });

  const submitAssignment = (id: number) => {
    const updated = assignments.map((assignment) =>
      assignment.id === id
        ? { ...assignment, submitted: true }
        : assignment
    );

    setAssignments(updated);

    const submittedIds = updated
      .filter((assignment) => "submitted" in assignment && assignment.submitted)
      .map((assignment) => assignment.id);

    localStorage.setItem(
      "learnspaceAssignments",
      JSON.stringify(submittedIds)
    );
  };

  return (
    <div className="lesson-page">
      <nav className="lesson-nav">
        <a href="/" className="profile-logo">LearnSpace.</a>
        <a href="/dashboard">← Back to Dashboard</a>
      </nav>

      <main className="lesson-container">

        <p className="enrolled-label">YOUR CLASSROOM</p>
        <h1>My Assignments</h1>
        <p className="enrolled-subtitle">
          View your tasks and keep track of your learning activities.
        </p>

        <section className="dashboard-stats">
          <div className="dashboard-stat-card">
            <span>📝</span>
            <h2>{assignments.length}</h2>
            <p>Total Assignments</p>
          </div>

          <div className="dashboard-stat-card">
            <span>⏳</span>
            <h2>{assignments.length - submittedCount}</h2>
            <p>Pending</p>
          </div>

          <div className="dashboard-stat-card">
            <span>✅</span>
            <h2>{submittedCount}</h2>
            <p>Submitted</p>
          </div>
        </section>

        <div className="dashboard-section-heading">
          <div>
            <span>YOUR TASKS</span>
            <h2>Assignment List</h2>
          </div>
        </div>

        <div className="assignment-filters">
          {["All", "Pending", "Submitted"].map((item) => (
            <button
              key={item}
              onClick={() => setFilter(item)}
              className={filter === item ? "active-filter" : ""}
            >
              {item}
            </button>
          ))}
        </div>

        <div className="assignment-list">
          {filteredAssignments.map((assignment) => {
            const isSubmitted =
              "submitted" in assignment && assignment.submitted;

            return (
              <div className="assignment-card" key={assignment.id}>
                <div>
                  <span className="enrolled-label">
                    {assignment.course}
                  </span>

                  <h3>{assignment.title}</h3>

                  <p>{assignment.description}</p>

                  <p>
                    <strong>Deadline:</strong> {assignment.deadline}
                  </p>

                  <span className="assignment-status">
                    {isSubmitted ? "Submitted ✓" : "Pending"}
                  </span>
                </div>

                <button
                  onClick={() => submitAssignment(assignment.id)}
                  disabled={Boolean(isSubmitted)}
                >
                  {isSubmitted ? "Submitted" : "Mark as Submitted"}
                </button>
              </div>
            );
          })}
        </div>

        {filteredAssignments.length === 0 && (
          <p>No assignments found in this category.</p>
        )}

      </main>
    </div>
  );
}

export default Assignments;
