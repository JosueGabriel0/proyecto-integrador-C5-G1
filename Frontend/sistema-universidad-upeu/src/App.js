import React from 'react';
import { BrowserRouter as BrowserRouter, Routes, Route } from 'react-router-dom';
import GeneralProtectedRouteComponent from './components/general/GeneralProtectedRouteComponent';
import ListRolComponent from './components/administrador/GestionarRol/ListRolComponent';
import AddRolComponent from './components/administrador/GestionarRol/AddRolComponent';
import AddUsuarioComponent from './components/administrador/GestionarUsuario/AddUsuarioComponent';
import ListUsuarioComponent from './components/administrador/GestionarUsuario/ListUsuarioComponent';
import AddPersonaComponent from './components/administrador/GestionarPersona/AddPersonaComponent';
import ListPersonaComponent from './components/administrador/GestionarPersona/ListPersonaComponent';
import ListAdministradorComponent from './components/administrador/GestionarAdministrador/ListAdministradorComponent';
import AddAdministradorComponent from './components/administrador/GestionarAdministrador/AddAdministradorComponent';
import ListAdministrativoComponent from './components/administrador/GestionarAdministrativo/ListAdministrativoComponent';
import AddAdministrativoComponent from './components/administrador/GestionarAdministrativo/AddAdministrativoComponent';
import ListInscripcionesConRolComponent from './components/administrador/GestionarInscripcion/InscripcionConRol/ListInscripcionesConRolComponent';
import AddInscripcionesConRolComponent from './components/administrador/GestionarInscripcion/InscripcionConRol/AddIncripcionConRolComponent';

import GeneralInicioComponent from './components/general/GeneralInicioComponent';
import GeneralLoginComponent from './components/general/GeneralLoginComponent';
import AdministradorDashboardComponent from './components/administrador/AdministradorDashboardComponent';
import { isAuthenticated } from './services/authServices/authService'; // Importa la función de verificación de autenticación
import GeneralEmailComponent from './components/general/GeneralEmailComponent';

const App = () => {
  return (
    <BrowserRouter>
      <div className='container'>
        <Routes>
          <Route exact path='/' element={<GeneralInicioComponent />} />

          {/* Ruta de login pública */}
          <Route path='/login' element={<GeneralLoginComponent />} />

          <Route path='/email' element={<GeneralEmailComponent />}/>

          {/* Ruta protegida para el dashboard */}
          <Route
            path='/dashboard-administrador'
            element={
              <GeneralProtectedRouteComponent isAuthenticated={isAuthenticated()}>
                <AdministradorDashboardComponent />
              </GeneralProtectedRouteComponent>
            }
          />

          {/* Rutas protegidas*/}
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

          <Route
            path='/personas'
            element={
              <GeneralProtectedRouteComponent isAuthenticated={isAuthenticated()}>
                <ListPersonaComponent />
              </GeneralProtectedRouteComponent>
            }
          />
          <Route
            path='/add-persona'
            element={
              <GeneralProtectedRouteComponent isAuthenticated={isAuthenticated()}>
                <AddPersonaComponent />
              </GeneralProtectedRouteComponent>
            }
          />
          <Route
            path='/edit-persona/:id'
            element={
              <GeneralProtectedRouteComponent isAuthenticated={isAuthenticated()}>
                <AddPersonaComponent />
              </GeneralProtectedRouteComponent>
            }
          />

          <Route
            path='/administradores'
            element={
              <GeneralProtectedRouteComponent isAuthenticated={isAuthenticated()}>
                <ListAdministradorComponent />
              </GeneralProtectedRouteComponent>
            }
          />
          <Route
            path='/add-administrador'
            element={
              <GeneralProtectedRouteComponent isAuthenticated={isAuthenticated()}>
                <AddAdministradorComponent />
              </GeneralProtectedRouteComponent>
            }
          />
          <Route
            path='/edit-administrador/:id'
            element={
              <GeneralProtectedRouteComponent isAuthenticated={isAuthenticated()}>
                <AddAdministradorComponent />
              </GeneralProtectedRouteComponent>
            }
          />

          <Route
            path='/administrativos'
            element={
              <GeneralProtectedRouteComponent isAuthenticated={isAuthenticated()}>
                <ListAdministrativoComponent />
              </GeneralProtectedRouteComponent>
            }
          />
          <Route
            path='/add-administrativo'
            element={
              <GeneralProtectedRouteComponent isAuthenticated={isAuthenticated()}>
                <AddAdministrativoComponent />
              </GeneralProtectedRouteComponent>
            }
          />
          <Route
            path='/edit-administrativo/:id'
            element={
              <GeneralProtectedRouteComponent isAuthenticated={isAuthenticated()}>
                <AddAdministrativoComponent />
              </GeneralProtectedRouteComponent>
            }
          />

          <Route
            path='/inscripcionesConRol'
            element={
              <GeneralProtectedRouteComponent isAuthenticated={isAuthenticated()}>
                <ListInscripcionesConRolComponent />
              </GeneralProtectedRouteComponent>
            }
          />
          <Route
            path='/add-inscripcionConRol'
            element={
              <GeneralProtectedRouteComponent isAuthenticated={isAuthenticated()}>
                <AddInscripcionesConRolComponent />
              </GeneralProtectedRouteComponent>
            }
          />
          <Route
            path='/edit-inscripcionConRol/:id'
            element={
              <GeneralProtectedRouteComponent isAuthenticated={isAuthenticated()}>
                <AddInscripcionesConRolComponent />
              </GeneralProtectedRouteComponent>
            }
          />

        </Routes>
      </div>
    </BrowserRouter>
  );
};

export default App;