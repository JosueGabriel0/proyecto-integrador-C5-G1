import React from 'react';
import { BrowserRouter as Router, Route, Routes, BrowserRouter } from 'react-router-dom';
import Login from './components/Login'; // Asegúrate de que la ruta sea correcta
import Dashboard from './components/Dashboard'; // Asegúrate de que la ruta sea correcta
import ProtectedRoute from './components/ProtectedRoute'; // Asegúrate de que la ruta sea correcta
import ListRolComponent from './components/administrador/rol/ListRolComponent';
import AddRolComponent from './components/administrador/rol/AddRolComponent';


const App = () => {
  return (
    <BrowserRouter>
    <div className='container'>
    <Routes>
    <Route exact path='/' element={<ListRolComponent />}></Route>
    <Route exact path='/roles' element={<ListRolComponent />}></Route>
    <Route exact path='/add-rol' element={<AddRolComponent />}></Route>
    </Routes>
    </div>
    </BrowserRouter>
  );
};

export default App;