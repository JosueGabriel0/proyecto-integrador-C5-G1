import React from 'react';
import { Link, useNavigate } from "react-router-dom";
import { logout } from '../../services/authServices/authService';

import Spline from '@splinetool/react-spline';

const LogoutButton = () => {
  const navigate = useNavigate();

  const handleLogout = () => {
    logout(); // Llama a la función logout para eliminar los tokens
    navigate('/login'); // Redirige al usuario a la página de inicio de sesión
  };

  return (
    <button onClick={handleLogout}>
      Logout
    </button>
  );
};

function AdministradorDashboardComponent() {
  return (
    <div style={{ position: "relative", width: "100%", height: "100vh", overflow: "hidden" }}>
      {/* Fondo Spline */}
      <div
        style={{
          width: "100%",
          height: "100vh",
          overflow: "hidden",
        }}
      >
        {/*<Spline scene="https://prod.spline.design/MwtxV8UARi6sJ67B/scene.splinecode" />
        <Spline scene="https://prod.spline.design/qYVvRyxsQv9ZTLJD/scene.splinecode" />
        */}
        <Spline scene="https://prod.spline.design/WyaY6g5euT7Rs7lf/scene.splinecode" />
      </div>

      {/* Contenido encima del fondo */}
      <div
        style={{
          position: "absolute",
          top: 0,
          left: 0,
          width: "100%",
          height: "100%",
          display: "flex",
          flexDirection: "column",
          justifyContent: "center",
          alignItems: "center",
          color: "white",
          textAlign: "center",
          padding: "20px",
        }}
      >
        <h1>Dashboard</h1>
        <p>Bienvenido al panel principal de administración del sistema universitario UPeU.</p>

        {/* Botón de Logout */}
        {LogoutButton()}

        {/* Resumen rápido */}
        <section style={{ marginTop: "20px" }}>
          <h2>Resumen rápido</h2>
          <ul>
            <li>Estudiantes registrados: 1200</li>
            <li>Cursos disponibles: 45</li>
            <li>Profesores activos: 75</li>
          </ul>
        </section>

        {/* Acciones rápidas */}
        <section style={{ marginTop: "20px" }}>
          <h2>Acciones rápidas</h2>
          <Link to="/inscripciones">Inscripciones</Link>&nbsp;&nbsp;
          <Link to="/roles">Roles</Link>&nbsp;&nbsp;
          <Link to="/usuarios">Usuarios</Link>&nbsp;&nbsp;
          <Link to="/personas">Personas</Link>&nbsp;&nbsp;
          <Link to="/administradores">Administradores</Link>&nbsp;&nbsp;
          <Link to="/administrativos">Administrativos</Link>&nbsp;&nbsp;
          <Link to="/docentes">Docentes</Link>&nbsp;&nbsp;
          <Link to="/estudiantes">Estudiantes</Link>
        </section>
      </div>
    </div>

  );
};

export default AdministradorDashboardComponent;