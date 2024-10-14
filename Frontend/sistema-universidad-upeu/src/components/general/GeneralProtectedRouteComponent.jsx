// GeneralProtectedRouteComponent.js

import React from 'react';
import { Navigate } from 'react-router-dom';
import { getUserRole } from '../../services/authServices/authService'; // Importa la función para obtener el rol

const GeneralProtectedRouteComponent = ({ allowedRoles, children }) => {
  const isAuthenticated = !!localStorage.getItem('authToken'); // Verificar si está autenticado
  const userRole = getUserRole(); // Obtener el rol del usuario

  if (!isAuthenticated) {
    // Redirigir al login si no está autenticado
    return <Navigate to="/login" />;
  }

  if (allowedRoles && !allowedRoles.includes(userRole)) {
    // Redirigir a una página no autorizada si el rol no es permitido
    return <Navigate to="/unauthorized" />;
  }

  // Renderizar el componente protegido si está autenticado y el rol es permitido
  return children;
};

export default GeneralProtectedRouteComponent;