import React from 'react';
import { Link, useNavigate } from "react-router-dom";
import { logout } from '../../services/authServices/authService';

import GeneralDashboardComponent from '../general/dashboard/GeneralDashboardComponent';

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

function EstudianteDashboardComponent() {
  return (
    <div className="container">
      <GeneralDashboardComponent titulo="Dasboard Estudiante"/>
    </div>

  );
};

export default EstudianteDashboardComponent;