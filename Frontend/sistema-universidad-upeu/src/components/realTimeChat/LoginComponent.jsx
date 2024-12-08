import React, { useEffect, useState } from "react";
import { useNavigate } from "react-router-dom";
import InscripcionService from "../../services/inscripcionServices/InscripcionService";
import { getInscripcionId } from "../../services/authServices/authService";

const LoginComponent = () => {
  const [nombres, setNombres] = useState("");
  const [apellidoPaterno, setApellidoPaterno] = useState("");
  const [apellidoMaterno, setApellidoMaterno] = useState("");
  const [error, setError] = useState(null); // Para manejar errores visuales
  const navigate = useNavigate();
  const inscripcionId = getInscripcionId();

  // Obtener nombres y apellidos al montar el componente
  useEffect(() => {
    if (inscripcionId) {
      obtenerNombresApellidos();
    }
  }, [inscripcionId]);

  // Función para obtener los datos de la persona
  const obtenerNombresApellidos = async () => {
    try {
      const response = await InscripcionService.getInscripcionById(inscripcionId);
      const persona = response.data?.persona;

      if (persona) {
        // Actualiza el estado con los datos obtenidos
        setNombres(persona.nombres || "Nombre no disponible");
        setApellidoPaterno(persona.apellido_paterno || "Apellido paterno no disponible");
        setApellidoMaterno(persona.apellido_materno || "Apellido materno no disponible");

        // Guarda los datos en localStorage
        localStorage.setItem("nombres", persona.nombres || "Nombre no disponible");
        localStorage.setItem("apellido_paterno", persona.apellido_paterno || "Apellido paterno no disponible");
        localStorage.setItem("apellido_materno", persona.apellido_materno || "Apellido materno no disponible");

        console.log("Datos del usuario guardados en localStorage.");
      } else {
        console.log("Los datos de la persona no están disponibles.");
      }
    } catch (error) {
      console.error("Error al obtener los datos del usuario:", error.message || error);
      setError("No se pudieron cargar los datos del usuario. Intente nuevamente más tarde.");
    }
  };

  // Manejo del login
  const handleLogin = async (e) => {
    e.preventDefault();

    if (!nombres.trim() || !apellidoPaterno.trim() || !apellidoMaterno.trim()) {
      setError("No se pudieron obtener los datos del usuario. Intente nuevamente.");
      return;
    }

    // Guarda los datos completos en localStorage como referencia
    const nombreCompleto = `${nombres} ${apellidoPaterno} ${apellidoMaterno}`;
    localStorage.setItem("nombre_completo", nombreCompleto);

    console.log("Nombre completo del usuario:", nombreCompleto);

    // Redirigir al chat
    navigate("/chat-real-time-chat");
  };

  return (
    <div className="username-page-container">
      <form onSubmit={handleLogin}>
        <h1>Chat en línea</h1>
        {error && <p style={{ color: "red" }}>{error}</p>} {/* Mensaje de error */}
        <div className="form-group">
          <p><strong>Nombre completo:</strong></p>
          <p>{`${nombres} ${apellidoPaterno} ${apellidoMaterno}`}</p> {/* Muestra los datos */}
        </div>
        <button type="submit" className="primary">Unirse al Chat</button>
      </form>
    </div>
  );
};

export default LoginComponent;