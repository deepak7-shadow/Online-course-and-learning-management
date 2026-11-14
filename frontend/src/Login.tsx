
import { useState } from "react";

function Login() {
  const [showPassword, setShowPassword] = useState(false);
  const [email, setEmail] = useState("");
  const [password, setPassword] = useState("");
  const [error, setError] = useState("");
  const [success, setSuccess] = useState("");

  const handleLogin = (event: React.FormEvent<HTMLFormElement>) => {
    event.preventDefault();

    setError("");
    setSuccess("");

    if (!email.trim() || !password.trim()) {
      setError("Please enter both email and password.");
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

    setSuccess(
      "Your details passed frontend validation. Account verification will be available after backend integration."
    );
  };

  return (
    <div className="login-page">
      <div className="login-card">

        <a href="/" className="logo">
          <span className="logo-icon">✦</span>
          LearnSpace<span className="logo-dot">.</span>
        </a>

        <h1>Welcome Back!</h1>

        <p className="login-subtitle">
          Sign in to continue your learning journey.
        </p>

        <form onSubmit={handleLogin}>
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

          <label>Password</label>

          <div className="password-field">
            <input
              type={showPassword ? "text" : "password"}
              placeholder="Enter your password"
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

          {error && (
            <p style={{ color: "red" }}>{error}</p>
          )}

          {success && (
            <p style={{ color: "green" }}>{success}</p>
          )}

          <button type="submit" className="login-submit">
            Log In →
          </button>
        </form>

        <p className="signup-text">
          Don't have an account? <a href="/signup">Sign up</a>
        </p>

        <a href="/" className="back-home">
          ← Back to Home
        </a>

      </div>
    </div>
  );
}

export default Login;
