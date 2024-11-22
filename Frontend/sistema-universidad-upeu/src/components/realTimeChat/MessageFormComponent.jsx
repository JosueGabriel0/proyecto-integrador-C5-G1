import React, { useState } from "react";

const MessageFormComponent = ({ onSendMessage }) => {
  const [message, setMessage] = useState("");

  const handleSubmit = (e) => {
    e.preventDefault();
    if (message.trim()) {
      onSendMessage(message);
      setMessage("");
    }
  };

  return (
    <form id="messageForm" onSubmit={handleSubmit}>
      <div className="input-group">
        <input
          type="text"
          placeholder="Enter a message..."
          value={message}
          onChange={(e) => setMessage(e.target.value)}
        />
        <button type="submit" className="primary">Send</button>
      </div>
    </form>
  );
};

export default MessageFormComponent;