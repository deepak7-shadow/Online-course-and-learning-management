
import { useState } from "react";

const courseData = {
  "web-development": {
    title: "Complete Web Development",
    subtitle: "Learn HTML, CSS and JavaScript step by step.",
    lessons: [
      {
        title: "Introduction to Web Development",
        explanation: "Web development is the process of creating websites and web applications. It involves designing the appearance of a website and developing its functionality.",
        example: "When you open an online shopping website, the product images, buttons and shopping cart are all parts of web development.",
        points: ["HTML creates the structure.", "CSS controls the design.", "JavaScript adds interactivity."]
      },
      {
        title: "HTML Fundamentals",
        explanation: "HTML stands for HyperText Markup Language. It is used to create the basic structure of a webpage using elements called tags.",
        example: "<h1>Welcome to LearnSpace</h1>\n<p>This is my first webpage.</p>",
        points: ["HTML uses tags and elements.", "Headings are created using h1 to h6.", "Paragraphs use the p tag."]
      },
      {
        title: "HTML Forms and Tables",
        explanation: "HTML forms collect information from users. Tables organize information into rows and columns.",
        example: '<form>\n  <input type="text" placeholder="Enter your name">\n  <button>Submit</button>\n</form>',
        points: ["Forms use the form tag.", "Input fields collect user data.", "Tables use table, tr, th and td."]
      },
      {
        title: "Introduction to CSS",
        explanation: "CSS stands for Cascading Style Sheets. It is used to style webpages by changing colors, fonts, backgrounds and spacing.",
        example: "h1 {\n  color: purple;\n  font-size: 30px;\n}",
        points: ["CSS controls webpage appearance.", "Selectors target HTML elements.", "Properties define styles."]
      },
      {
        title: "CSS Layout and Flexbox",
        explanation: "CSS layout techniques help arrange webpage elements. Flexbox is a layout system that makes it easier to align and distribute elements.",
        example: ".container {\n  display: flex;\n  justify-content: center;\n  align-items: center;\n}",
        points: ["Flexbox uses display: flex.", "justify-content aligns items horizontally.", "align-items aligns items vertically."]
      },
      {
        title: "JavaScript Basics",
        explanation: "JavaScript is a programming language that makes websites interactive. It can respond to clicks, validate forms and update webpage content.",
        example: "let name = 'Student';\nconsole.log('Hello ' + name);",
        points: ["JavaScript uses variables.", "Functions organize reusable code.", "Events respond to user actions."]
      }
    ]
  },

  "python-programming": {
    title: "Python Programming",
    subtitle: "Learn Python programming from the basics.",
    lessons: [
      {
        title: "Introduction to Python",
        explanation: "Python is a high-level, easy-to-read programming language. It is used in web development, automation, data science and artificial intelligence.",
        example: "print('Hello, World!')",
        points: ["Python has simple syntax.", "It is an interpreted language.", "It supports multiple programming styles."]
      },
      {
        title: "Variables and Data Types",
        explanation: "Variables store data in a program. Python automatically identifies the data type based on the value assigned.",
        example: "name = 'Alex'\nage = 20\nheight = 5.6\nis_student = True",
        points: ["int stores integers.", "float stores decimal numbers.", "str stores text and bool stores True or False."]
      },
      {
        title: "Operators and Expressions",
        explanation: "Operators perform operations on values and variables. Expressions combine values and operators to produce a result.",
        example: "a = 10\nb = 5\nprint(a + b)\nprint(a * b)",
        points: ["Arithmetic operators perform calculations.", "Comparison operators compare values.", "Logical operators combine conditions."]
      },
      {
        title: "Conditional Statements",
        explanation: "Conditional statements allow a program to make decisions. The if, elif and else statements execute different code depending on conditions.",
        example: "age = 18\nif age >= 18:\n    print('Eligible')\nelse:\n    print('Not eligible')",
        points: ["if checks a condition.", "elif checks another condition.", "else runs when previous conditions are false."]
      },
      {
        title: "Loops and Functions",
        explanation: "Loops repeat a block of code. Functions are reusable blocks of code that perform a particular task.",
        example: "for i in range(1, 4):\n    print(i)\n\ndef greet():\n    print('Hello')\n\ngreet()",
        points: ["for and while are loops.", "def creates a function.", "Functions reduce repeated code."]
      },
      {
        title: "Lists and Dictionaries",
        explanation: "Lists store multiple items in an ordered collection. Dictionaries store information as key-value pairs.",
        example: "fruits = ['Apple', 'Mango']\nstudent = {'name': 'Alex', 'age': 20}\nprint(fruits[0])\nprint(student['name'])",
        points: ["Lists use square brackets.", "List indexing starts at zero.", "Dictionaries store key-value pairs."]
      }
    ]
  },

  "data-science": {
    title: "Data Science",
    subtitle: "Explore data analysis and visualization.",
    lessons: [
      {
        title: "Introduction to Data Science",
        explanation: "Data Science is the field of collecting, processing and analyzing data to discover useful information and support decision-making.",
        example: "An online shopping company analyzes customer purchases to understand which products are popular.",
        points: ["Data Science uses statistics and programming.", "It helps discover patterns.", "It supports better decisions."]
      },
      {
        title: "Types of Data",
        explanation: "Data can be classified into different types. Numerical data contains numbers, while categorical data represents groups or labels.",
        example: "Age = 20 is numerical data. City = Bengaluru is categorical data.",
        points: ["Numerical data represents quantities.", "Categorical data represents groups.", "Data can be structured or unstructured."]
      },
      {
        title: "Data Collection",
        explanation: "Data collection is the process of gathering information from different sources for analysis.",
        example: "A college can collect student attendance through online forms or an attendance management system.",
        points: ["Surveys collect responses.", "Databases store information.", "Sensors can automatically collect data."]
      },
      {
        title: "Data Cleaning",
        explanation: "Data cleaning improves data quality by correcting errors, handling missing values and removing duplicate records.",
        example: "If a dataset contains the same student record twice, one duplicate can be removed.",
        points: ["Handle missing values.", "Remove duplicate records.", "Correct inconsistent data."]
      },
      {
        title: "Data Analysis and Visualization",
        explanation: "Data analysis examines information to identify patterns. Visualization presents results using charts, graphs and tables.",
        example: "A bar chart can compare the number of students enrolled in different courses.",
        points: ["Analysis identifies trends.", "Charts make results easier to understand.", "Python libraries can create visualizations."]
      },
      {
        title: "Introduction to Machine Learning",
        explanation: "Machine Learning is a branch of artificial intelligence that allows computers to learn patterns from data and make predictions.",
        example: "A machine learning model can use previous house prices to estimate the price of a new house.",
        points: ["Models learn from data.", "Training helps models identify patterns.", "Predictions are evaluated for accuracy."]
      }
    ]
  },

  "ui-ux-design": {
    title: "UI/UX Design",
    subtitle: "Learn to design beautiful user interfaces.",
    lessons: [
      {
        title: "Introduction to UI and UX",
        explanation: "UI means User Interface and focuses on how a product looks. UX means User Experience and focuses on how easy and enjoyable it is to use.",
        example: "A colorful login screen is UI. A simple and easy login process is UX.",
        points: ["UI focuses on appearance.", "UX focuses on user experience.", "Both work together to create good products."]
      },
      {
        title: "Design Principles",
        explanation: "Design principles help create clear and attractive interfaces. They include balance, contrast, alignment, consistency and visual hierarchy.",
        example: "A large heading above a smaller paragraph creates a clear visual hierarchy.",
        points: ["Maintain consistency.", "Use alignment properly.", "Create clear visual hierarchy."]
      },
      {
        title: "Colors and Typography",
        explanation: "Colors communicate meaning and create visual appeal. Typography involves selecting and arranging fonts to make text readable.",
        example: "A dark blue background with white text can create a professional appearance.",
        points: ["Choose readable fonts.", "Use a consistent color palette.", "Maintain sufficient text contrast."]
      },
      {
        title: "Layouts and Spacing",
        explanation: "Layouts organize elements on a screen. Spacing provides room between elements and improves readability.",
        example: "A website can use a navigation bar at the top, content in the middle and a footer at the bottom.",
        points: ["Use consistent spacing.", "Group related elements.", "Keep layouts organized."]
      },
      {
        title: "Wireframing",
        explanation: "A wireframe is a simple visual plan of a webpage or application. It shows where important elements will be placed before detailed design begins.",
        example: "A login wireframe may contain boxes for email, password and a login button.",
        points: ["Wireframes show page structure.", "They are usually simple.", "They help plan before development."]
      },
      {
        title: "Introduction to Prototyping",
        explanation: "A prototype is an interactive model of a design. It helps designers test navigation and user interactions before building the actual product.",
        example: "Clicking a Login button in a prototype can take the user to a sample dashboard screen.",
        points: ["Prototypes demonstrate interactions.", "They help test usability.", "Feedback improves the design."]
      }
    ]
  },

  "java-programming": {
    title: "Java Programming",
    subtitle: "Understand Java and object-oriented programming.",
    lessons: [
      {
        title: "Introduction to Java",
        explanation: "Java is a high-level, object-oriented programming language. Java programs run on the Java Virtual Machine (JVM).",
        example: 'class Main {\n  public static void main(String[] args) {\n    System.out.println("Hello World");\n  }\n}',
        points: ["Java is object-oriented.", "Java code is compiled into bytecode.", "The JVM executes Java bytecode."]
      },
      {
        title: "Variables and Data Types",
        explanation: "Variables store values in a Java program. Every variable has a declared data type that determines what kind of value it can hold.",
        example: 'int age = 20;\ndouble price = 99.5;\nchar grade = \'A\';\nboolean passed = true;',
        points: ["int stores whole numbers.", "double stores decimal numbers.", "char stores a single character."]
      },
      {
        title: "Operators and Conditions",
        explanation: "Java operators perform calculations and comparisons. Conditional statements help a program make decisions.",
        example: 'int age = 20;\nif (age >= 18) {\n  System.out.println("Adult");\n} else {\n  System.out.println("Minor");\n}',
        points: ["Arithmetic operators perform calculations.", "Comparison operators compare values.", "if and else control decisions."]
      },
      {
        title: "Loops and Arrays",
        explanation: "Loops repeat instructions. Arrays store multiple values of the same data type in a single variable.",
        example: "int[] numbers = {10, 20, 30};\nfor (int i = 0; i < numbers.length; i++) {\n  System.out.println(numbers[i]);\n}",
        points: ["for and while are loops.", "Array indexing starts at zero.", "Array length gives the number of elements."]
      },
      {
        title: "Classes and Objects",
        explanation: "A class is a blueprint for creating objects. An object is an instance of a class containing data and methods.",
        example: 'class Student {\n  String name = "Alex";\n}\n\nStudent s = new Student();',
        points: ["A class is a blueprint.", "An object is an instance of a class.", "Methods define object behavior."]
      },
      {
        title: "Inheritance and Polymorphism",
        explanation: "Inheritance allows one class to reuse features of another class. Polymorphism allows the same method or interface to have different behaviors.",
        example: 'class Animal {\n  void sound() {\n    System.out.println("Animal sound");\n  }\n}\n\nclass Dog extends Animal {\n}',
        points: ["Inheritance uses extends.", "It supports code reuse.", "Polymorphism allows different implementations."]
      }
    ]
  },

  "machine-learning": {
    title: "Machine Learning",
    subtitle: "Discover machine learning concepts and applications.",
    lessons: [
      {
        title: "Introduction to Machine Learning",
        explanation: "Machine Learning is a branch of Artificial Intelligence that enables systems to learn patterns from data and make predictions.",
        example: "An email service can learn to identify spam messages using examples of previous emails.",
        points: ["Machine Learning is part of AI.", "It learns patterns from data.", "It is used for prediction and classification."]
      },
      {
        title: "Types of Machine Learning",
        explanation: "The main types are supervised learning, unsupervised learning and reinforcement learning. Each uses a different approach to learning.",
        example: "Predicting house prices is supervised learning. Grouping customers by behavior is unsupervised learning.",
        points: ["Supervised learning uses labeled data.", "Unsupervised learning finds patterns.", "Reinforcement learning uses rewards and penalties."]
      },
      {
        title: "Understanding Datasets",
        explanation: "A dataset is a collection of information used to train and evaluate machine learning models. Rows usually represent records and columns represent features.",
        example: "A house dataset may contain area, number of bedrooms, location and price.",
        points: ["Rows represent observations.", "Columns represent features.", "Good data improves model performance."]
      },
      {
        title: "Data Preparation",
        explanation: "Data preparation transforms raw data into a suitable format for machine learning. It includes cleaning, selecting features and splitting datasets.",
        example: "A dataset can be divided into training data and testing data to evaluate a model.",
        points: ["Clean missing or incorrect data.", "Select useful features.", "Split data for training and testing."]
      },
      {
        title: "Machine Learning Algorithms",
        explanation: "Algorithms are methods used by machine learning systems to learn from data. Different algorithms are suitable for different problems.",
        example: "Linear Regression can predict a value such as house price. Decision Trees can classify records.",
        points: ["Linear Regression predicts numerical values.", "Decision Trees make decisions using conditions.", "Algorithm choice depends on the problem."]
      },
      {
        title: "Model Training and Evaluation",
        explanation: "Training is the process of teaching a model using data. Evaluation measures how well the trained model performs on data it has not seen before.",
        example: "A model trained on previous student marks can be tested to see how accurately it predicts new marks.",
        points: ["Training helps a model learn.", "Testing checks model performance.", "Accuracy is one evaluation measure."]
      }
    ]
  }
};

type CourseSlug = keyof typeof courseData;

function LessonPlayer() {
  const params = new URLSearchParams(window.location.search);
  const requestedCourse = params.get("course");

  const courseSlug: CourseSlug =
    requestedCourse && requestedCourse in courseData
      ? (requestedCourse as CourseSlug)
      : "web-development";

  const course = courseData[courseSlug];

  const progressKey =
    courseSlug === "web-development"
      ? "webDevelopmentCompletedLessons"
      : `learnspace_${courseSlug}_completedLessons`;

  const [completedLessons, setCompletedLessons] = useState<number[]>(() => {
    try {
      const saved = localStorage.getItem(progressKey);
      return saved ? JSON.parse(saved) : [];
    } catch {
      return [];
    }
  });

  const [selectedLesson, setSelectedLesson] = useState(0);

  const currentLesson = course.lessons[selectedLesson];

  const allLessonsCompleted = course.lessons.every((_, index) =>
    completedLessons.includes(index)
  );

  const progress = Math.round(
    (completedLessons.length / course.lessons.length) * 100
  );

  const markCompleted = () => {
    if (!completedLessons.includes(selectedLesson)) {
      const updated = [...completedLessons, selectedLesson];
      setCompletedLessons(updated);
      localStorage.setItem(progressKey, JSON.stringify(updated));
    }
  };

  const goToLesson = (index: number) => {
    if (index >= 0 && index < course.lessons.length) {
      setSelectedLesson(index);
      window.scrollTo({ top: 0, behavior: "smooth" });
    }
  };

  return (
    <div className="lesson-page">
      <nav className="lesson-nav">
        <a href="/" className="profile-logo">LearnSpace.</a>
        <a href="/enrolled-courses">← Back to My Courses</a>
      </nav>

      <main className="lesson-container">
        <header className="lesson-page-header">
          <p className="enrolled-label">YOUR LEARNING SPACE</p>
          <h1>{course.title}</h1>
          <p className="enrolled-subtitle">{course.subtitle}</p>
        </header>

        <section className="lesson-progress lesson-progress-card">
          <div className="course-progress-heading">
            <div>
              <strong>Course Progress</strong>
              <p>{completedLessons.length} of {course.lessons.length} lessons completed</p>
            </div>
            <strong className="lesson-progress-percent">{progress}%</strong>
          </div>

          <div className="progress-track">
            <div
              className="progress-fill"
              style={{ width: `${progress}%` }}
            />
          </div>
        </section>

        <div className="lesson-layout">
          <section className="lesson-video lesson-content-panel">
            <div className="lesson-video-placeholder">
              <div className="lesson-play-icon">▶</div>
              <h3>LearnSpace Video Classroom</h3>
              <p>Video lessons can be connected here when course videos are available.</p>
              <span className="lesson-video-tag">VIDEO LESSON AREA</span>
            </div>

            <div className="lesson-content-heading">
              <p className="enrolled-label">
                LESSON {selectedLesson + 1} OF {course.lessons.length}
              </p>
              <h2>{currentLesson.title}</h2>
            </div>

            <div className="lesson-detail-section">
              <h3><span>01</span> Lesson Explanation</h3>
              <p>{currentLesson.explanation}</p>
            </div>

            <div className="lesson-detail-section">
              <h3><span>02</span> Practical Example</h3>
              <pre className="lesson-example">
                <code>{currentLesson.example}</code>
              </pre>
            </div>

            <div className="lesson-detail-section">
              <h3><span>03</span> Key Takeaways</h3>
              <ul className="lesson-points">
                {currentLesson.points.map((point, index) => (
                  <li key={index}>
                    <span>✓</span>
                    {point}
                  </li>
                ))}
              </ul>
            </div>

            <div className="lesson-completion-area">
              <button
                className="lesson-complete-button"
                onClick={markCompleted}
                disabled={completedLessons.includes(selectedLesson)}
              >
                {completedLessons.includes(selectedLesson)
                  ? "✓ Lesson Completed"
                  : "✓ Mark Lesson as Completed"}
              </button>
            </div>

            <div className="lesson-navigation">
              <button
                className="lesson-previous-button"
                onClick={() => goToLesson(selectedLesson - 1)}
                disabled={selectedLesson === 0}
              >
                ← Previous Lesson
              </button>

              <button
                className="lesson-next-button"
                onClick={() => goToLesson(selectedLesson + 1)}
                disabled={selectedLesson === course.lessons.length - 1}
              >
                Next Lesson →
              </button>
            </div>
          </section>

          <aside className="lesson-list">
            <div className="lesson-list-heading">
              <p className="enrolled-label">YOUR CURRICULUM</p>
              <h2>Course Content</h2>
              <p>{completedLessons.length} / {course.lessons.length} completed</p>
            </div>

            <div className="lesson-list-items">
              {course.lessons.map((lesson, index) => {
                const isSelected = selectedLesson === index;
                const isCompleted = completedLessons.includes(index);

                return (
                  <button
                    type="button"
                    className={`lesson-item ${
                      isSelected ? "lesson-item-active" : ""
                    } ${isCompleted ? "lesson-item-completed" : ""}`}
                    key={index}
                    onClick={() => goToLesson(index)}
                    aria-current={isSelected ? "step" : undefined}
                  >
                    <span className="lesson-item-number">
                      {isCompleted ? "✓" : String(index + 1).padStart(2, "0")}
                    </span>

                    <span className="lesson-item-title">
                      {lesson.title}
                    </span>

                    <span className="lesson-item-status">
                      {isCompleted ? "✓" : isSelected ? "●" : "›"}
                    </span>
                  </button>
                );
              })}
            </div>
          </aside>
        </div>

        {allLessonsCompleted && (
          <section className="quiz-unlocked">
            <div className="quiz-unlocked-icon">🏆</div>
            <p className="enrolled-label">COURSE COMPLETED</p>
            <h2>Congratulations!</h2>
            <p>You have completed all six lessons. You are ready for the final assessment.</p>
            <a href={`/quiz-results?course=${courseSlug}`}>
              Take Final Quiz →
            </a>
          </section>
        )}
      </main>
    </div>
  );
}

export default LessonPlayer;
