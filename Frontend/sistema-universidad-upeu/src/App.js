import React from 'react';
import { BrowserRouter as Router, Route, Routes } from 'react-router-dom';
import Login from './components/Login'; // Asegúrate de que la ruta sea correcta
import Dashboard from './components/Dashboard'; // Asegúrate de que la ruta sea correcta
import ProtectedRoute from './components/ProtectedRoute'; // Asegúrate de que la ruta sea correcta
import ListRolComponent from './components/administrador/rol/ListRolComponent';

const App = () => {
  return (
    <ListRolComponent />
  );
};

export default App;