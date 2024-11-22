import React, { useState } from "react";
import { useNavigate } from "react-router-dom";

const LoginComponent = () => {
  const [username, setUsername] = useState("");
  const navigate = useNavigate();

  const handleLogin = (e) => {
    e.preventDefault();
    if (username.trim()) {
      localStorage.setItem("username", username); // Guardar el nombre en localStorage
      navigate("/chat-real-time-chat");
    }
  };

  return (
    <div className="username-page-container">
      <form onSubmit={handleLogin}>
        <h1>Chat App</h1>
        <div className="form-group">
          <input
            type="text"
            placeholder="Enter your username"
            className="form-control"
            value={username}
            onChange={(e) => setUsername(e.target.value)}
          />
        </div>
        <button type="submit" className="primary">Join Chat</button>
      </form>
    </div>
  );
};

export default LoginComponent;