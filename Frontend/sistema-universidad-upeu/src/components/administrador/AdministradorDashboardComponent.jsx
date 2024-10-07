import React from 'react';
import { Link, useNavigate } from "react-router-dom";

function AdministradorDashboardComponent() {
  return (
    <div>
      <h1>Dashboard</h1>
      <p>Bienvenido al panel principal de administración del sistema universitario UPeU.</p>
      {/* Aquí puedes añadir más secciones, gráficos, o enlaces a diferentes funcionalidades */}

      <section>
        <h2>Resumen rápido</h2>
        <ul>
          <li>Estudiantes registrados: 1200</li>
          <li>Cursos disponibles: 45</li>
          <li>Profesores activos: 75</li>
          {/* Puedes obtener estos datos desde el backend o agregarlos dinámicamente */}
        </ul>
      </section>

      <section>
        <h2>Acciones rápidas</h2>
        <Link to="/inscripcionesConRol">Inscripciones Con Rol</Link>
        &nbsp;
        &nbsp;
        <Link to="/roles">Inscripciones Con Asignacion de Rol</Link>
        &nbsp;
        &nbsp;
        <Link to="/roles">Roles</Link>
        &nbsp;
        &nbsp;
        <Link to="/usuarios">Usuarios</Link>
        &nbsp;
        &nbsp;
        <Link to="/personas">Personas</Link>
        &nbsp;
        &nbsp;
        <Link to="/administradores">Administradores</Link>
        &nbsp;
        &nbsp;
        <Link to="/administrativos">Administrativos</Link>
        <button onClick={() => alert('Ir a la gestión de estudiantes')}>Gestión de Estudiantes</button>
        <button onClick={() => alert('Ir a la gestión de cursos')}>Gestión de Cursos</button>
        <button onClick={() => alert('Ir a la gestión de profesores')}>Gestión de Profesores</button>
      </section>
    </div>
  );
};

export default AdministradorDashboardComponent;