import React from 'react';
import { Link, useNavigate } from "react-router-dom";
import { logout } from '../../services/authServices/authService';

import GeneralDashboardComponent from '../general/dashboard/GeneralDashboardComponent';

function AdministrativoDashboardComponent() {
  return (
    <div className="container">
      <GeneralDashboardComponent titulo="Dashboard Administrativo"/>
    </div>
  );
};

export default AdministrativoDashboardComponent;