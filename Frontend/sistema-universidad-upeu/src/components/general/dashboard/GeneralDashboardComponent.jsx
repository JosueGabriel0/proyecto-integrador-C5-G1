import React from 'react';
import { Link, useNavigate } from "react-router-dom";

import Spline from '@splinetool/react-spline';
import GeneralProfileCardComponent from './GeneralProfileCardComponent';

import { getUserRole } from '../../../services/authServices/authService';

function GeneralDashboardComponent({ titulo = "Dshboard" }) {
  const nombreRol = getUserRole();
  function opcionesSegunRol() {
    if (nombreRol === "ADMINISTRADOR") {
      return (
        <div>
          <section style={{ marginTop: "20px" }}>
            <h2>Acciones</h2>
            <Link to="/inscripciones">Inscripciones</Link>&nbsp;&nbsp;
            <Link to="/roles">Roles</Link>&nbsp;&nbsp;
            <Link to="/usuarios">Usuarios</Link>&nbsp;&nbsp;
            <Link to="/personas">Personas</Link>&nbsp;&nbsp;
            <Link to="/administradores">Administradores</Link>&nbsp;&nbsp;
            <Link to="/administrativos">Administrativos</Link>&nbsp;&nbsp;
            <Link to="/docentes">Docentes</Link>&nbsp;&nbsp;
            <Link to="/estudiantes">Estudiantes</Link>&nbsp;&nbsp;
            <Link to="/login-real-time-chat">Chat en linea</Link>
          </section>
        </div>
      )
    } else if(nombreRol === "ADMINISTRATIVO"){
      return(
        <div>
          <section style={{ marginTop: "20px" }}>
            <h2>Acciones</h2>
            <Link to="/inscripciones">Inscripciones</Link>&nbsp;&nbsp;
            <Link to="/roles">Roles</Link>&nbsp;&nbsp;
            <Link to="/usuarios">Usuarios</Link>&nbsp;&nbsp;
            <Link to="/personas">Personas</Link>&nbsp;&nbsp;
            <Link to="/administradores">Administradores</Link>&nbsp;&nbsp;
            <Link to="/administrativos">Administrativos</Link>&nbsp;&nbsp;
            <Link to="/docentes">Docentes</Link>&nbsp;&nbsp;
            <Link to="/estudiantes">Estudiantes</Link>&nbsp;&nbsp;
            <Link to="/login-real-time-chat">Chat en linea</Link>
          </section>
        </div>
      )
    }else if(nombreRol === "DOCENTE"){
      return(
        <div>
          <section style={{ marginTop: "20px" }}>
            <h2>Acciones</h2>
            <Link to="/inscripciones">Inscripciones</Link>&nbsp;&nbsp;
            <Link to="/roles">Roles</Link>&nbsp;&nbsp;
            <Link to="/usuarios">Usuarios</Link>&nbsp;&nbsp;
            <Link to="/personas">Personas</Link>&nbsp;&nbsp;
            <Link to="/administradores">Administradores</Link>&nbsp;&nbsp;
            <Link to="/administrativos">Administrativos</Link>&nbsp;&nbsp;
            <Link to="/docentes">Docentes</Link>&nbsp;&nbsp;
            <Link to="/estudiantes">Estudiantes</Link>&nbsp;&nbsp;
            <Link to="/login-real-time-chat">Chat en linea</Link>
          </section>
        </div>
      )
    }else if(nombreRol === "ESTUDIANTE"){
      return(
        <div>
          <section style={{ marginTop: "20px" }}>
            <h2>Acciones</h2>
            <Link to="/inscripciones">PORTAL DEL ESTUDIANTE</Link>&nbsp;&nbsp;
            <Link to="/inicio-matricula-virtual-estudiante">MATRÍCULA</Link>&nbsp;&nbsp;
            <Link to="/usuarios">BIENESTAR UNIV.</Link>&nbsp;&nbsp;
            <Link to="/personas">B-LEARNING</Link>&nbsp;&nbsp;
            <Link to="/administradores">LAMB LEARNING</Link>&nbsp;&nbsp;
          </section>
        </div>
      )
    }
  }
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
        <h1>{titulo}</h1>
        <p>Bienvenido al panel principal de administración del sistema universitario UPeU.</p>

        <GeneralProfileCardComponent />

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
        {opcionesSegunRol()}
      </div>
    </div>

  );
};

export default GeneralDashboardComponent;