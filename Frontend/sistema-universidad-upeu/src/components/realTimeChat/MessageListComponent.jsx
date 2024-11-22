import React from "react";

const MessageList = ({ messages }) => {
  return (
    <ul>
      {messages.map((message, index) => (
        <li key={index} className={message.type === "CHAT" ? "chat-message" : "event-message"}>
          {message.type !== "CHAT" ? (
            <p>{message.content}</p>
          ) : (
            <>
              <i style={{ backgroundColor: getAvatarColor(message.sender) }}>
                {message.sender[0]}
              </i>
              <span>{message.sender}</span>
              <p>{message.content}</p>
            </>
          )}
        </li>
      ))}
    </ul>
  );
};

const getAvatarColor = (sender) => {
  const colors = ["#2196F3", "#32c787", "#00BCD4", "#ff5652", "#ffc107", "#ff85af", "#FF9800", "#39bbb0"];
  return colors[sender.length % colors.length];
};

export default MessageList;