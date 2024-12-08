import React from "react";

// Función para obtener el color del avatar basado en las iniciales del nombre y apellido
const getAvatarColor = (sender) => {
  const colors = ["#2196F3", "#32c787", "#00BCD4", "#ff5652", "#ffc107", "#ff85af", "#FF9800", "#39bbb0"];
  // Obtenemos las iniciales del nombre y apellido (suponiendo que `sender` es un nombre completo)
  const initials = sender
    .split(" ")
    .map((name) => name.charAt(0).toUpperCase())  // Tomamos la primera letra de cada palabra
    .join(""); // Unimos las iniciales

  let hash = 0;
  for (let i = 0; i < initials.length; i++) {
    hash = 31 * hash + initials.charCodeAt(i); // Calculamos el hash con las iniciales
  }
  const index = Math.abs(hash % colors.length); // Calculamos el índice para el color
  return colors[index]; // Devolvemos el color correspondiente
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