
import { useState } from "react";

type Notification = {
  id: number;
  title: string;
  message: string;
  date: string;
  type: string;
  read: boolean;
};

const initialNotifications: Notification[] = [
  {
    id: 1,
    title: "Welcome to LearnSpace!",
    message: "Start exploring your courses and begin your learning journey.",
    date: "Today",
    type: "Announcement",
    read: false,
  },
  {
    id: 2,
    title: "Assignment Reminder",
    message: "Your HTML & CSS Mini Project assignment is waiting for submission.",
    date: "Today",
    type: "Assignment",
    read: false,
  },
  {
    id: 3,
    title: "New Course Available",
    message: "Explore our Machine Learning course and improve your skills.",
    date: "Yesterday",
    type: "Course",
    read: false,
  },
  {
    id: 4,
    title: "Quiz Reminder",
    message: "Complete your course lessons to unlock the quiz.",
    date: "Yesterday",
    type: "Quiz",
    read: true,
  },
  {
    id: 5,
    title: "Keep Learning!",
    message: "Continue your courses and track your learning progress.",
    date: "2 days ago",
    type: "Announcement",
    read: true,
  },
];

function Notifications() {
  const [notifications, setNotifications] = useState<Notification[]>(() => {
    const saved = localStorage.getItem("learnspaceNotifications");
    return saved ? JSON.parse(saved) : initialNotifications;
  });

  const [filter, setFilter] = useState("All");

  const unreadCount = notifications.filter(
    (notification) => !notification.read
  ).length;

  const saveNotifications = (updated: Notification[]) => {
    setNotifications(updated);
    localStorage.setItem(
      "learnspaceNotifications",
      JSON.stringify(updated)
    );
  };

  const markAsRead = (id: number) => {
    const updated = notifications.map((notification) =>
      notification.id === id
        ? { ...notification, read: true }
        : notification
    );

    saveNotifications(updated);
  };

  const markAllAsRead = () => {
    const updated = notifications.map((notification) => ({
      ...notification,
      read: true,
    }));

    saveNotifications(updated);
  };

  const filteredNotifications = notifications.filter((notification) => {
    if (filter === "Unread") return !notification.read;
    if (filter === "Read") return notification.read;
    return true;
  });

  return (
    <div className="lesson-page">
      <nav className="lesson-nav">
        <a href="/" className="profile-logo">LearnSpace.</a>
        <a href="/dashboard">← Back to Dashboard</a>
      </nav>

      <main className="lesson-container">
        <p className="enrolled-label">STAY UPDATED</p>

        <h1>Notifications</h1>

        <p className="enrolled-subtitle">
          Keep track of announcements, reminders and learning updates.
        </p>

        <section className="dashboard-stats">
          <div className="dashboard-stat-card">
            <span>🔔</span>
            <h2>{notifications.length}</h2>
            <p>Total Notifications</p>
          </div>

          <div className="dashboard-stat-card">
            <span>📩</span>
            <h2>{unreadCount}</h2>
            <p>Unread Notifications</p>
          </div>

          <div className="dashboard-stat-card">
            <span>✅</span>
            <h2>{notifications.length - unreadCount}</h2>
            <p>Read Notifications</p>
          </div>
        </section>

        <div className="dashboard-section-heading">
          <div>
            <span>YOUR UPDATES</span>
            <h2>Recent Notifications</h2>
          </div>

          <button
            onClick={markAllAsRead}
            disabled={unreadCount === 0}
          >
            Mark All as Read
          </button>
        </div>

        <div className="assignment-filters">
          {["All", "Unread", "Read"].map((item) => (
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
          {filteredNotifications.map((notification) => (
            <div className="assignment-card" key={notification.id}>
              <div>
                <span className="enrolled-label">
                  {notification.type}
                </span>

                <h3>
                  {notification.title}
                  {!notification.read && " •"}
                </h3>

                <p>{notification.message}</p>
                <p>{notification.date}</p>

                <span className="assignment-status">
                  {notification.read ? "Read" : "Unread"}
                </span>
              </div>

              {!notification.read && (
                <button onClick={() => markAsRead(notification.id)}>
                  Mark as Read
                </button>
              )}
            </div>
          ))}
        </div>

        {filteredNotifications.length === 0 && (
          <p>No notifications in this category.</p>
        )}
      </main>
    </div>
  );
}

export default Notifications;
