
const courses = {
  "web-development": {
    title: "Complete Web Development Course",
    category: "DEVELOPMENT",
    description:
      "Learn to build beautiful, responsive websites from scratch using HTML, CSS and JavaScript. Start your journey into web development today!",
    duration: "12 Hours",
    level: "Beginner",
    icon: "💻",
    about:
      "This course introduces you to the fundamentals of web development. You will learn how websites are created and how to design interactive web pages using modern technologies.",
    learningPoints: [
      "Understand HTML structure",
      "Design websites using CSS",
      "Add interactivity using JavaScript",
      "Create responsive web pages",
      "Build your own mini website project",
      "Understand basic frontend development",
    ],
    lessons: [
      ["Introduction to Web Development", "What is web development?"],
      ["HTML Fundamentals", "Elements, tags and page structure"],
      ["HTML Forms and Tables", "Creating forms and structured data"],
      ["Introduction to CSS", "Colors, fonts and styling"],
      ["CSS Layout and Flexbox", "Designing responsive layouts"],
      ["JavaScript Basics", "Variables, functions and events"],
    ],
  },

  "python-programming": {
    title: "Complete Python Programming Course",
    category: "PROGRAMMING",
    description:
      "Learn Python programming from the basics. Understand programming concepts and start writing your own Python programs.",
    duration: "10 Hours",
    level: "Beginner",
    icon: "🐍",
    about:
      "This course helps beginners understand Python programming through simple explanations, examples and practical exercises.",
    learningPoints: [
      "Understand Python fundamentals",
      "Work with variables and data types",
      "Use conditional statements",
      "Understand loops and functions",
      "Work with lists and dictionaries",
      "Build simple Python programs",
    ],
    lessons: [
      ["Introduction to Python", "What is Python and how to use it"],
      ["Variables and Data Types", "Storing and working with data"],
      ["Operators and Expressions", "Performing calculations"],
      ["Conditional Statements", "Using if, elif and else"],
      ["Loops and Functions", "Repeating tasks and reusable code"],
      ["Lists and Dictionaries", "Working with collections"],
    ],
  },

  "data-science": {
    title: "Data Science Fundamentals",
    category: "DATA SCIENCE",
    description:
      "Explore data analysis, visualization and machine learning. Learn how to understand data and discover useful insights.",
    duration: "15 Hours",
    level: "Intermediate",
    icon: "📊",
    about:
      "Learn the foundations of data science, including data collection, cleaning, analysis and visualization.",
    learningPoints: [
      "Understand data science concepts",
      "Learn about different types of data",
      "Explore data cleaning techniques",
      "Understand data analysis",
      "Create data visualizations",
      "Discover machine learning basics",
    ],
    lessons: [
      ["Introduction to Data Science", "Understanding data science"],
      ["Types of Data", "Structured and unstructured data"],
      ["Data Collection", "Collecting useful datasets"],
      ["Data Cleaning", "Preparing data for analysis"],
      ["Data Analysis and Visualization", "Understanding data through charts"],
      ["Introduction to Machine Learning", "Basic machine learning concepts"],
    ],
  },

  "ui-ux-design": {
    title: "UI/UX Design Masterclass",
    category: "DESIGN",
    description:
      "Learn to design beautiful and user-friendly interfaces. Explore design principles and create better digital experiences.",
    duration: "8 Hours",
    level: "Beginner",
    icon: "🎨",
    about:
      "This course introduces UI and UX design principles. Learn how to create attractive interfaces that are simple and comfortable to use.",
    learningPoints: [
      "Understand UI and UX design",
      "Learn basic design principles",
      "Explore colors and typography",
      "Understand layouts and spacing",
      "Create wireframes",
      "Design simple user interfaces",
    ],
    lessons: [
      ["Introduction to UI and UX", "Understanding the difference"],
      ["Design Principles", "Balance, contrast and alignment"],
      ["Colors and Typography", "Choosing colors and fonts"],
      ["Layouts and Spacing", "Creating organized interfaces"],
      ["Wireframing", "Planning a screen design"],
      ["Introduction to Prototyping", "Creating interactive designs"],
    ],
  },

  "java-programming": {
    title: "Complete Java Programming Course",
    category: "PROGRAMMING",
    description:
      "Understand Java programming, object-oriented concepts and coding. Build a strong foundation in Java development.",
    duration: "14 Hours",
    level: "Intermediate",
    icon: "☕",
    about:
      "Learn Java programming step by step, from basic syntax to object-oriented programming and simple applications.",
    learningPoints: [
      "Understand Java fundamentals",
      "Learn variables and data types",
      "Use conditional statements and loops",
      "Understand arrays and strings",
      "Learn object-oriented programming",
      "Create simple Java programs",
    ],
    lessons: [
      ["Introduction to Java", "Java features and setup"],
      ["Variables and Data Types", "Understanding Java data"],
      ["Operators and Conditions", "Expressions and decision making"],
      ["Loops and Arrays", "Repeating tasks and storing values"],
      ["Classes and Objects", "Understanding object-oriented programming"],
      ["Inheritance and Polymorphism", "Core OOP concepts"],
    ],
  },

  "machine-learning": {
    title: "Machine Learning Fundamentals",
    category: "DATA SCIENCE",
    description:
      "Discover machine learning concepts and practical applications. Understand how computers learn from data.",
    duration: "18 Hours",
    level: "Advanced",
    icon: "🤖",
    about:
      "Explore the foundations of machine learning and understand how algorithms identify patterns and make predictions using data.",
    learningPoints: [
      "Understand artificial intelligence and ML",
      "Explore supervised learning",
      "Understand unsupervised learning",
      "Learn about training data",
      "Explore basic ML algorithms",
      "Understand model evaluation",
    ],
    lessons: [
      ["Introduction to Machine Learning", "What is machine learning?"],
      ["Types of Machine Learning", "Supervised and unsupervised learning"],
      ["Understanding Datasets", "Features and training data"],
      ["Data Preparation", "Preparing data for machine learning"],
      ["ML Algorithms", "Introduction to common algorithms"],
      ["Model Training and Evaluation", "Testing model performance"],
    ],
  },
};

type CourseSlug = keyof typeof courses;

function CourseDetails() {
  const params = new URLSearchParams(window.location.search);
  const courseSlug = params.get("course") as CourseSlug | null;

  const course =
    courseSlug && courseSlug in courses
      ? courses[courseSlug]
      : courses["web-development"];

  return (
    <div className="course-details-page">

      {/* NAVBAR */}
      <nav className="course-navbar">
        <a href="/" className="logo">
          <span className="logo-icon">✦</span>
          LearnSpace<span className="logo-dot">.</span>
        </a>

        <div>
          <a href="/">Home</a>
          <a href="/dashboard">My Dashboard</a>
        </div>
      </nav>

      {/* COURSE HERO */}
      <section className="course-hero">
        <div className="course-hero-content">
          <span className="course-tag">{course.category}</span>

          <h1>{course.title}</h1>

          <p>{course.description}</p>

          <div className="course-meta">
            <span>📚 {course.lessons.length} Lessons</span>
            <span>⏱️ {course.duration}</span>
            <span>🟢 {course.level}</span>
          </div>

          <a href="#curriculum" className="explore-btn">
            Explore Lessons ↓
          </a>
        </div>

        <div className="course-hero-icon">
          {course.icon}
        </div>
      </section>

      {/* COURSE CONTENT */}
      <main className="course-details-content">

        <section className="course-about">
          <span>ABOUT THIS COURSE</span>
          <h2>What You Will Learn</h2>

          <p>{course.about}</p>

          <div className="learning-points">
            {course.learningPoints.map((point, index) => (
              <div key={index}>✓ {point}</div>
            ))}
          </div>
        </section>

        {/* CURRICULUM */}
        <section className="curriculum" id="curriculum">
          <span>COURSE CONTENT</span>
          <h2>Course Curriculum</h2>
          <p>Explore the lessons included in this course.</p>

          {course.lessons.map((lesson, index) => (
            <div className="lesson-card" key={index}>
              <div>
                <strong>
                  {String(index + 1).padStart(2, "0")}. {lesson[0]}
                </strong>
                <small>{lesson[1]}</small>
              </div>
              <span>▶</span>
            </div>
          ))}
        </section>

        {/* INSTRUCTOR */}
        <section className="instructor-card">
          <div className="instructor-icon">👩🏻‍🏫</div>
          <div>
            <span>YOUR INSTRUCTOR</span>
            <h2>LearnSpace Teaching Team</h2>
            <p>
              Learn from structured lessons designed
              to make learning easy and interesting.
            </p>
          </div>
        </section>

        {/* ENROLL */}
        <section className="enroll-banner">
          <h2>Ready to Start Learning?</h2>
          <p>Take the first step towards building your skills.</p>
          <a href="/signup">Enroll Now ↗</a>
        </section>

      </main>

      <footer className="dashboard-footer">
        © 2026 LearnSpace. Keep learning, keep growing. 💜
      </footer>

    </div>
  );
}

export default CourseDetails;
