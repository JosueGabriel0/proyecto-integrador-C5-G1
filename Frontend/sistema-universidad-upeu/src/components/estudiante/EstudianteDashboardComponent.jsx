import React from 'react';
import { Link, useNavigate } from "react-router-dom";
import { logout } from '../../services/authServices/authService';

import GeneralDashboardComponent from '../general/dashboard/GeneralDashboardComponent';

function EstudianteDashboardComponent() {
  return (
    <div className="container">
      <GeneralDashboardComponent titulo="Dasboard Estudiante"/>
    </div>

  );
};

export default EstudianteDashboardComponent;