import React, { useEffect, useState } from "react";
import { useNavigate } from "react-router-dom";
import UsuarioService from "../../services/usuarioServices/UsuarioService"
import { getUserId } from "../../services/authServices/authService";

const LoginComponent = () => {
  const [username, setUsername] = useState("");
  const navigate = useNavigate();
  const usuarioId = getUserId();

  useEffect(() => {
    obtenerNombreUsuario();
  }, [usuarioId])

  const obtenerNombreUsuario = () => {
    UsuarioService.getUsuarioById(usuarioId).then((response) => {
      setUsername(response.data.username);
      console.log(response.data.username);
    }).catch((error) => {
      console.log(error);
    })
  }

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
        <h1>Chat en linea</h1>
        <div className="form-group">
          <input
            type="text"
            placeholder="Enter your username"
            className="form-control"
            value={username}
            onChange={(e) => setUsername(e.target.value)}
          />
        </div>
        <button type="submit" className="primary">Unirse al Chat</button>
      </form>
    </div>
  );
};

export default LoginComponent;