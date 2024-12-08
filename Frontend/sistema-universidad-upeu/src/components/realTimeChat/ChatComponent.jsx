import React, { useEffect, useState } from "react";
import { Client } from "@stomp/stompjs";
import SockJS from "sockjs-client";
import MessageListComponent from "./MessageListComponent";
import MessageFormComponent from "./MessageFormComponent";
import "../../style-sheets/realTimeChat/ChatComponent.css";

const ChatComponent = () => {
  const [stompClient, setStompClient] = useState(null);
  const [messages, setMessages] = useState([]);

  // Obtén el nombre completo desde localStorage
  const nombreCompleto = localStorage.getItem("nombre_completo");

  useEffect(() => {
    const client = new Client({
      webSocketFactory: () => new SockJS("http://localhost:8083/ws"), // Conexión usando SockJS
      onConnect: () => {
        console.log("Conectado al WebSocket");

        // Suscripción al canal público para recibir mensajes
        client.subscribe("/topic/public", (message) => {
          console.log("Mensaje recibido:", JSON.parse(message.body));
          const newMessage = JSON.parse(message.body);
          setMessages((prevMessages) => [...prevMessages, newMessage]);
        });

        // Enviar el mensaje de JOIN al servidor con el nombre completo
        client.publish({
          destination: "/app/chat.addUser",
          body: JSON.stringify({ sender: nombreCompleto, type: "JOIN" }),
        });
      },
      onStompError: (frame) => {
        console.error("STOMP error", frame.headers["message"]);
      },
    });

    client.activate();
    setStompClient(client);

    return () => {
      client.deactivate();
    };
  }, [nombreCompleto]);

  const sendMessage = (content) => {
    if (stompClient && stompClient.connected) {
      const message = {
        sender: nombreCompleto,  // Usa el nombre completo
        content,
        type: "CHAT",
      };
      stompClient.publish({
        destination: "/app/chat.sendMessage",
        body: JSON.stringify(message),
      });
    } else {
      console.error("STOMP client is not connected");
    }
  };

  return (
    <div id="chat-page">
      <div className="chat-container">
        <div className="chat-header">
          <h2>Bienvenido, {nombreCompleto}</h2>
        </div>
        <MessageListComponent messages={messages} />
        <MessageFormComponent onSendMessage={sendMessage} />
      </div>
    </div>
  );
};

export default ChatComponent;