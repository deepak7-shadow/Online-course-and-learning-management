
import { useState } from "react";

function Signup() {
  const [showPassword, setShowPassword] = useState(false);
  const [showConfirmPassword, setShowConfirmPassword] = useState(false);

  const [fullName, setFullName] = useState("");
  const [email, setEmail] = useState("");
  const [studentId, setStudentId] = useState("");
  const [password, setPassword] = useState("");
  const [confirmPassword, setConfirmPassword] = useState("");

  const [error, setError] = useState("");
  const [success, setSuccess] = useState("");

  const handleSignup = (event: React.FormEvent<HTMLFormElement>) => {
    event.preventDefault();

    setError("");
    setSuccess("");

    if (
      !fullName.trim() ||
      !email.trim() ||
      !studentId.trim() ||
      !password ||
      !confirmPassword
    ) {
      setError("Please fill in all the fields.");
      return;
    }

    const emailPattern = /^[^\s@]+@[^\s@]+\.[^\s@]+$/;

    if (!emailPattern.test(email)) {
      setError("Please enter a valid email address.");
      return;
    }

    if (password.length < 6) {
      setError("Password must contain at least 6 characters.");
      return;
    }

    if (password !== confirmPassword) {
      setError("Password and Confirm Password do not match.");
      return;
    }

    setSuccess(
      "All details are valid! Your account can be created after backend authentication is connected."
    );
  };

  return (
    <div className="login-page">
      <div className="login-card">

        <a href="/" className="logo">
          <span className="logo-icon">✦</span>
          LearnSpace<span className="logo-dot">.</span>
        </a>

        <h1>Create Account</h1>

        <p className="login-subtitle">
          Join LearnSpace and start your learning journey.
        </p>

        <form onSubmit={handleSignup}>
          <label>Full Name</label>

          <input
            type="text"
            placeholder="Enter your full name"
            value={fullName}
            onChange={(event) => {
              setFullName(event.target.value);
              setError("");
              setSuccess("");
            }}
          />

          <label>Email Address</label>

          <input
            type="email"
            placeholder="Enter your email"
            value={email}
            onChange={(event) => {
              setEmail(event.target.value);
              setError("");
              setSuccess("");
            }}
          />

          <label>Student ID</label>

          <input
            type="text"
            placeholder="Enter your student ID"
            value={studentId}
            onChange={(event) => {
              setStudentId(event.target.value);
              setError("");
              setSuccess("");
            }}
          />

          <label>Password</label>

          <div className="password-field">
            <input
              type={showPassword ? "text" : "password"}
              placeholder="Create a password"
              value={password}
              onChange={(event) => {
                setPassword(event.target.value);
                setError("");
                setSuccess("");
              }}
            />

            <button
              type="button"
              onClick={() => setShowPassword(!showPassword)}
            >
              {showPassword ? "Hide" : "Show"}
            </button>
          </div>

          <label>Confirm Password</label>

          <div className="password-field">
            <input
              type={showConfirmPassword ? "text" : "password"}
              placeholder="Confirm your password"
              value={confirmPassword}
              onChange={(event) => {
                setConfirmPassword(event.target.value);
                setError("");
                setSuccess("");
              }}
            />

            <button
              type="button"
              onClick={() =>
                setShowConfirmPassword(!showConfirmPassword)
              }
            >
              {showConfirmPassword ? "Hide" : "Show"}
            </button>
          </div>

          {error && (
            <p style={{ color: "red" }}>{error}</p>
          )}

          {success && (
            <p style={{ color: "green" }}>{success}</p>
          )}

          <button type="submit" className="login-submit">
            Create Account →
          </button>
        </form>

        <p className="signup-text">
          Already have an account? <a href="/login">Log in</a>
        </p>

        <a href="/" className="back-home">
          ← Back to Home
        </a>

      </div>
    </div>
  );
}

export default Signup;
