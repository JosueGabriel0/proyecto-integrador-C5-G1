import React from 'react';
import { BrowserRouter as BrowserRouter, Routes, Route } from 'react-router-dom';
import GeneralProtectedRouteComponent from './components/general/GeneralProtectedRouteComponent';
import ListRolComponent from './components/administrador/rol/ListRolComponent';
import AddRolComponent from './components/administrador/rol/AddRolComponent';
import GeneralInicioComponent from './components/general/GeneralInicioComponent';
import GeneralLoginComponent from './components/general/GeneralLoginComponent';
import AdministradorDashboardComponent from './components/administrador/AdministradorDashboardComponent';
import AddUsuarioComponent from './components/administrador/usuario/AddUsuarioComponent';
import ListUsuarioComponent from './components/administrador/usuario/ListUsuarioComponent';
import { isAuthenticated } from './services/authServices/authService'; // Importa la función de verificación de autenticación

const App = () => {
  return (
    <BrowserRouter>
      <div className='container'>
        <Routes>
          <Route exact path='/' element={<GeneralInicioComponent />} />

          {/* Ruta de login pública */}
          <Route path='/login' element={<GeneralLoginComponent />} />

          {/* Ruta protegida para el dashboard */}
          <Route
            path='/dashboard-administrador'
            element={
              <GeneralProtectedRouteComponent isAuthenticated={isAuthenticated()}>
                <AdministradorDashboardComponent />
              </GeneralProtectedRouteComponent>
            }
          />

          {/* Rutas protegidas para roles y usuarios */}
          <Route
            path='/roles'
            element={
              <GeneralProtectedRouteComponent isAuthenticated={isAuthenticated()}>
                <ListRolComponent />
              </GeneralProtectedRouteComponent>
            }
          />
          <Route
            path='/add-rol'
            element={
              <GeneralProtectedRouteComponent isAuthenticated={isAuthenticated()}>
                <AddRolComponent />
              </GeneralProtectedRouteComponent>
            }
          />
          <Route
            path='/edit-rol/:id'
            element={
              <GeneralProtectedRouteComponent isAuthenticated={isAuthenticated()}>
                <AddRolComponent />
              </GeneralProtectedRouteComponent>
            }
          />
          <Route
            path='/usuarios'
            element={
              <GeneralProtectedRouteComponent isAuthenticated={isAuthenticated()}>
                <ListUsuarioComponent />
              </GeneralProtectedRouteComponent>
            }
          />
          <Route
            path='/add-usuario'
            element={
              <GeneralProtectedRouteComponent isAuthenticated={isAuthenticated()}>
                <AddUsuarioComponent />
              </GeneralProtectedRouteComponent>
            }
          />
          <Route
            path='/edit-usuario/:id'
            element={
              <GeneralProtectedRouteComponent isAuthenticated={isAuthenticated()}>
                <AddUsuarioComponent />
              </GeneralProtectedRouteComponent>
            }
          />
        </Routes>
      </div>
    </BrowserRouter>
  );
};

export default App;