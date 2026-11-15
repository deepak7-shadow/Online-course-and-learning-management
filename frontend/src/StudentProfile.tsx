
import { useState } from "react";

function StudentProfile() {
  const [editing, setEditing] = useState(false);

  const [name, setName] = useState(() => {
    const saved = localStorage.getItem("learnSpaceProfile");
    return saved ? JSON.parse(saved).name : "Alex Johnson";
  });

  const [email, setEmail] = useState(() => {
    const saved = localStorage.getItem("learnSpaceProfile");
    return saved ? JSON.parse(saved).email : "alex@example.com";
  });

  const [phone, setPhone] = useState(() => {
    const saved = localStorage.getItem("learnSpaceProfile");
    return saved ? JSON.parse(saved).phone : "+91 9876543210";
  });

  const [bio, setBio] = useState(() => {
    const saved = localStorage.getItem("learnSpaceProfile");
    return saved
      ? JSON.parse(saved).bio
      : "Passionate learner interested in technology and programming.";
  });

  const saveProfile = () => {
    const profile = {
      name,
      email,
      phone,
      bio,
    };

    localStorage.setItem(
      "learnSpaceProfile",
      JSON.stringify(profile)
    );

    setEditing(false);
  };

  return (
    <div className="profile-page">
      <nav className="profile-nav">
        <a href="/" className="profile-logo">LearnSpace.</a>
        <a href="/dashboard">← Back to Dashboard</a>
      </nav>

      <div className="profile-container">
        <div className="profile-heading">
          <p>MY ACCOUNT</p>
          <h1>Student Profile</h1>
          <span>Manage your personal information and account details.</span>
        </div>

        <div className="profile-layout">
          <div className="profile-card">
            <div className="profile-avatar">👨‍🎓</div>
            <h2>{name}</h2>
            <p>{email}</p>
            <span className="profile-badge">Student Account</span>

            <div className="profile-stats">
              <div>
                <h3>06</h3>
                <span>Courses</span>
              </div>
              <div>
                <h3>12</h3>
                <span>Lessons</span>
              </div>
              <div>
                <h3>02</h3>
                <span>Certificates</span>
              </div>
            </div>
          </div>

          <div className="profile-information">
            <div className="profile-info-heading">
              <div>
                <h2>Personal Information</h2>
                <p>Update your personal details here.</p>
              </div>

              <button onClick={() => setEditing(!editing)}>
                {editing ? "Cancel" : "Edit Profile"}
              </button>
            </div>

            <div className="profile-form">
              <label>Full Name</label>
              <input
                value={name}
                disabled={!editing}
                onChange={(e) => setName(e.target.value)}
              />

              <label>Email Address</label>
              <input
                type="email"
                value={email}
                disabled={!editing}
                onChange={(e) => setEmail(e.target.value)}
              />

              <label>Phone Number</label>
              <input
                value={phone}
                disabled={!editing}
                onChange={(e) => setPhone(e.target.value)}
              />

              <label>About Me</label>
              <textarea
                value={bio}
                disabled={!editing}
                onChange={(e) => setBio(e.target.value)}
                rows={4}
              />

              {editing && (
                <button
                  className="profile-save"
                  onClick={saveProfile}
                >
                  Save Changes
                </button>
              )}
            </div>
          </div>
        </div>
      </div>
    </div>
  );
}

export default StudentProfile;
