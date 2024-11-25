import React from "react";

// Función para obtener el color del avatar basado en el nombre del usuario
const getAvatarColor = (sender) => {
  const colors = ["#2196F3", "#32c787", "#00BCD4", "#ff5652", "#ffc107", "#ff85af", "#FF9800", "#39bbb0"];
  let hash = 0;
  for (let i = 0; i < sender.length; i++) {
    hash = 31 * hash + sender.charCodeAt(i);
  }
  const index = Math.abs(hash % colors.length);
  return colors[index];
};

const MessageListComponent = ({ messages }) => {
  return (
    <ul>
      {messages.map((message, index) => (
        <li key={index} className={message.type === "CHAT" ? "chat-message" : "event-message"}>
          {/* Mostrar mensaje de tipo JOIN/LEAVE */}
          {message.type !== "CHAT" ? (
            <p className="event-message-content">
              {message.type === "JOIN" ? `${message.sender} se unió al chat!` : `${message.sender} salió del chat!`}
            </p>
          ) : (
            <>
              {/* Avatar de usuario */}
              <i style={{ backgroundColor: getAvatarColor(message.sender) }}>
                {message.sender[0]}
              </i>
              {/* Nombre del usuario */}
              <span>{message.sender}</span>
              {/* Contenido del mensaje */}
              <p>{message.content}</p>
            </>
          )}
        </li>
      ))}
    </ul>
  );
};

export default MessageListComponent;