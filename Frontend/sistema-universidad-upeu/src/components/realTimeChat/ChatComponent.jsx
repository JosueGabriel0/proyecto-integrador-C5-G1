import React, { useEffect, useState } from "react";
import { Client } from "@stomp/stompjs";
import SockJS from "sockjs-client";
import MessageListComponent from "./MessageListComponent";
import MessageFormComponent from "./MessageFormComponent";
import "../../style-sheets/realTimeChat/ChatComponent.css";

const ChatComponent = () => {
  const [stompClient, setStompClient] = useState(null);
  const [messages, setMessages] = useState([]);
  const username = localStorage.getItem("username");

  useEffect(() => {
    const client = new Client({
      webSocketFactory: () => new SockJS("http://localhost:9090/ws"), // Conexión usando SockJS
      onConnect: () => {
        console.log("Conectado al WebSocket");
        client.subscribe("/topic/public", (message) => {
          console.log("Mensaje recibido:", JSON.parse(message.body));
          const newMessage = JSON.parse(message.body);
          setMessages((prevMessages) => [...prevMessages, newMessage]);
        });

        client.publish({
          destination: "/app/chat.addUser",
          body: JSON.stringify({ sender: username, type: "JOIN" }),
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
  }, [username]);


  const sendMessage = (content) => {
    const message = {
      sender: username,
      content,
      type: "CHAT",
    };
    stompClient.send("/app/chat.sendMessage", {}, JSON.stringify(message));
  };

  return (
    <div id="chat-page">
      <div className="chat-container">
        <div className="chat-header">
          <h2>Welcome, {username}</h2>
        </div>
        <MessageListComponent messages={messages} />
        <MessageFormComponent onSendMessage={sendMessage} />
      </div>
    </div>
  );
};

export default ChatComponent;