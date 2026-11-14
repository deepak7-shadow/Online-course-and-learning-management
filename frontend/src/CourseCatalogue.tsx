
import { useState } from "react";

const courses = [
  {
    title: "Web Development",
    slug: "web-development",
    category: "Programming",
    level: "Beginner",
    duration: "12 hours",
    description: "Learn HTML, CSS and JavaScript to build modern websites.",
    icon: "💻",
  },
  {
    title: "Python Programming",
    slug: "python-programming",
    category: "Programming",
    level: "Beginner",
    duration: "10 hours",
    description: "Learn Python fundamentals and write your first programs.",
    icon: "🐍",
  },
  {
    title: "Data Science",
    slug: "data-science",
    category: "Data Science",
    level: "Intermediate",
    duration: "15 hours",
    description: "Explore data analysis, visualization and machine learning.",
    icon: "📊",
  },
  {
    title: "UI/UX Design",
    slug: "ui-ux-design",
    category: "Design",
    level: "Beginner",
    duration: "8 hours",
    description: "Learn to design beautiful and user-friendly interfaces.",
    icon: "🎨",
  },
  {
    title: "Java Programming",
    slug: "java-programming",
    category: "Programming",
    level: "Intermediate",
    duration: "14 hours",
    description: "Understand Java, object-oriented programming and coding.",
    icon: "☕",
  },
  {
    title: "Machine Learning",
    slug: "machine-learning",
    category: "Data Science",
    level: "Advanced",
    duration: "18 hours",
    description: "Discover machine learning concepts and practical applications.",
    icon: "🤖",
  },
];

function CourseCatalogue() {
  const [search, setSearch] = useState("");
  const [category, setCategory] = useState("All");

  const filteredCourses = courses.filter((course) => {
    const matchesSearch =
      course.title.toLowerCase().includes(search.toLowerCase());

    const matchesCategory =
      category === "All" || course.category === category;

    return matchesSearch && matchesCategory;
  });

  return (
    <div className="catalogue-page">
      <nav className="catalogue-nav">
        <a href="/" className="catalogue-logo">LearnSpace.</a>
        <a href="/">Home</a>
      </nav>

      <section className="catalogue-header">
        <p>LEARN SOMETHING NEW</p>
        <h1>Explore Our Courses</h1>
        <span>
          Discover courses and build skills for your future.
        </span>

        <input
          type="text"
          placeholder="Search courses..."
          value={search}
          onChange={(e) => setSearch(e.target.value)}
        />
      </section>

      <section className="catalogue-content">
        <div className="category-filters">
          {["All", "Programming", "Data Science", "Design"].map((item) => (
            <button
              key={item}
              className={category === item ? "active" : ""}
              onClick={() => setCategory(item)}
            >
              {item}
            </button>
          ))}
        </div>

        <div className="catalogue-grid">
          {filteredCourses.length > 0 ? (
            filteredCourses.map((course) => (
              <div className="catalogue-card" key={course.slug}>
                <div className="catalogue-icon">{course.icon}</div>
                <p>{course.category}</p>
                <h2>{course.title}</h2>
                <span>{course.description}</span>

                <div className="catalogue-info">
                  <span>{course.level}</span>
                  <span>{course.duration}</span>
                </div>

                <a href={`/course-details?course=${course.slug}`}>
                  View Course →
                </a>
              </div>
            ))
          ) : (
            <p>No courses found. Try another search.</p>
          )}
        </div>
      </section>
    </div>
  );
}

export default CourseCatalogue;
