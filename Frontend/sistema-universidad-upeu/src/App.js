import React from 'react';
import { BrowserRouter as Router, Route, Routes } from 'react-router-dom';
import Login from './componentes/Login'; // Asegúrate de que la ruta sea correcta
import Dashboard from './componentes/Dashboard'; // Asegúrate de que la ruta sea correcta
import ProtectedRoute from './componentes/ProtectedRoute'; // Asegúrate de que la ruta sea correcta

const App = () => {
  return (
    <Router>
      <Routes>
        {/* Ruta pública para la página de inicio de sesión */}
        <Route path="/" element={<Login />} />

        {/* Ruta protegida para el dashboard */}
        <Route 
          path="/dashboard" 
          element={
            <ProtectedRoute>
              <Dashboard />
            </ProtectedRoute>
          } 
        />
      </Routes>
    </Router>
  );
};

export default App;