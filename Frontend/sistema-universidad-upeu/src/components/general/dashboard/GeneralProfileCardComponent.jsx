import React, { useEffect, useState } from "react";
import "../../../style-sheets/general/dashboard/GeneralProfileCardComponent.css";
import { getInscripcionId } from "../../../services/authServices/authService";
import InscripcionService from "../../../services/inscripcionServices/InscripcionService";
import { useNavigate } from "react-router-dom";
import { logout } from '../../../services/authServices/authService';
import PersonaService from "../../../services/personaServices/PersonaService";

const GeneralProfileCardComponent = () => {
  const [inscripcionRolNombre, setInscripcionRolNombre] = useState("");
  const [inscripcionPersonaNombres, setInscripcionPersonaNombres] = useState("");
  const [inscripcionPersonaApellidoPaterno, setinscripcionPersonaApellidoPaterno] = useState("");
  const [imagenDePersona, setImagenDePersona] = useState("");
  const [menuVisible, setMenuVisible] = useState(false); // Estado para controlar la visibilidad del menú

  const inscripcionId = getInscripcionId();

  const navigate = useNavigate();

  async function obtenerDatosDePersonDeInscripcion() {
    try {
      const response = await InscripcionService.getInscripcionById(inscripcionId);

      console.log("Nombre del Rol: " + response.data.rol.nombreRol);
      setInscripcionRolNombre(response.data.rol.nombreRol);

      console.log("Nombres de la Persona: " + response.data.persona.nombres);
      setInscripcionPersonaNombres(response.data.persona.nombres);

      console.log("Apellido Paterno de la Persona: " + response.data.persona.apellido_paterno);
      setinscripcionPersonaApellidoPaterno(response.data.persona.apellido_paterno);

      // Lógica para obtener la URL de la imagen
      if (response.data.persona.fotoPerfil) {
        const imagenUrl = await PersonaService.getPersonaImagen(response.data.persona.fotoPerfil);
        console.log("URL de la imagen de Persona:", imagenUrl);
        setImagenDePersona(imagenUrl);
      } else {
        console.warn("La persona no tiene una foto de perfil definida.");
      }
    } catch (error) {
      console.error("Error obteniendo datos de inscripción o imagen de persona:", error);
    }
  }

  const handleImageClick = () => {
    setMenuVisible(!menuVisible); // Cambia el estado del menú (muestra/oculta)
  };

  const LogoutFuncional = () => {
    
    const handleLogout = () => {
      logout(); // Llama a la función logout para eliminar los tokens
      navigate('/login'); // Redirige al usuario a la página de inicio de sesión
    };

    return (
      <div onClick={handleLogout}>
        Logout
      </div>
    );
  };

  const PerfilFuncional = () => {

    const handlePerfil = () => {
      navigate("/ver-perfil");
    }
    return(
      <div onClick={handlePerfil}>
        Perfil
      </div>
    )
  }

  useEffect(() => {
    obtenerDatosDePersonDeInscripcion();
  }, []); // Se ejecuta al montar el componente

  return (
    <div className="profile-card">
      <div className="profile-card-info">
        <span className="name-card">{inscripcionPersonaNombres} {inscripcionPersonaApellidoPaterno}</span>
        <span className="role-card">{inscripcionRolNombre}</span>
      </div>
      <div className="profile-card-image-container">
        {imagenDePersona ? (
          <img src={imagenDePersona} alt="User Avatar" className="profile-card-image" onClick={handleImageClick} />
        ) : (
          <p>No disponible</p>
        )}

        {/* Menú desplegable */}
        {menuVisible && (
          <div className="dropdown-card-menu">
            <ul>
              <li>{PerfilFuncional()}</li>
              <li>{LogoutFuncional()}</li>
            </ul>
          </div>
        )}
      </div>
    </div>
  );
};

export default GeneralProfileCardComponent;