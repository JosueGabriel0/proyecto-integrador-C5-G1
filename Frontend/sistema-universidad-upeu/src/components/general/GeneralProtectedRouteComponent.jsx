// GeneralProtectedRouteComponent.js

import React from 'react';
import { Navigate } from 'react-router-dom';

const GeneralProtectedRouteComponent = ({ isAuthenticated, children }) => {
  if (!isAuthenticated) {
    // Redirigir al login si no está autenticado
    return <Navigate to="/login" />;
  }

  // Renderizar el componente protegido si está autenticado
  return children;
};

export default GeneralProtectedRouteComponent;