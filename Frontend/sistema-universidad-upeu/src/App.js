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
import ListDocenteComponent from './components/administrador/GestionarDocente/ListDocenteComponent';
import AddDocenteComponent from './components/administrador/GestionarDocente/AddDocenteComponent';
import ListEstudianteComponent from './components/administrador/GestionarEstudiante/ListEstudianteComponent';
import AddEstudianteComponent from './components/administrador/GestionarEstudiante/AddEstudianteComponent';

import GeneralInicioComponent from './components/general/GeneralInicioComponent';
import GeneralLoginComponent from './components/general/GeneralLoginComponent';
import AdministradorDashboardComponent from './components/administrador/AdministradorDashboardComponent';
import { isAuthenticated } from './services/authServices/authService'; // Importa la función de verificación de autenticación
import GeneralEmailComponent from './components/general/GeneralEmailComponent';
import GeneralRestablecerContraseniaComponent from './components/general/GeneralRestablecerContraseniaComponent';
import GeneralCambiarContraseniaComponent from './components/general/GeneralCambiarContraseniaComponent';
import Unauthorized from './components/general/GeneralUnauthorizedComponent'; // Importa tu componente de no autorizado

const App = () => {
  return (
    <BrowserRouter>
      <div className='container'>
        <Routes>
          <Route exact path='/' element={<GeneralInicioComponent />} />

          {/* Ruta de login pública */}
          <Route path='/login' element={<GeneralLoginComponent />} />
          <Route path='/email' element={<GeneralEmailComponent />} />
          <Route path='/restablecimiento-contrasenia' element={<GeneralRestablecerContraseniaComponent />} />
          <Route path='/cambiar-contrasenia/:token' element={<GeneralCambiarContraseniaComponent />} />

          {/* Ruta protegida para el dashboard */}
          <Route
            path='/dashboard-administrador'
            element={
              <GeneralProtectedRouteComponent allowedRoles={['ROLE_ADMIN']}>
                <AdministradorDashboardComponent />
              </GeneralProtectedRouteComponent>
            }
          />

          {/* Rutas protegidas */}
          <Route
            path='/roles'
            element={
              <GeneralProtectedRouteComponent allowedRoles={['ROLE_ADMIN']}>
                <ListRolComponent />
              </GeneralProtectedRouteComponent>
            }
          />
          <Route
            path='/add-rol'
            element={
              <GeneralProtectedRouteComponent allowedRoles={['ROLE_ADMIN']}>
                <AddRolComponent />
              </GeneralProtectedRouteComponent>
            }
          />
          <Route
            path='/edit-rol/:id'
            element={
              <GeneralProtectedRouteComponent allowedRoles={['ROLE_ADMIN']}>
                <AddRolComponent />
              </GeneralProtectedRouteComponent>
            }
          />

          <Route
            path='/usuarios'
            element={
              <GeneralProtectedRouteComponent allowedRoles={['ROLE_ADMIN']}>
                <ListUsuarioComponent />
              </GeneralProtectedRouteComponent>
            }
          />
          <Route
            path='/add-usuario'
            element={
              <GeneralProtectedRouteComponent allowedRoles={['ROLE_ADMIN']}>
                <AddUsuarioComponent />
              </GeneralProtectedRouteComponent>
            }
          />
          <Route
            path='/edit-usuario/:id'
            element={
              <GeneralProtectedRouteComponent allowedRoles={['ROLE_ADMIN']}>
                <AddUsuarioComponent />
              </GeneralProtectedRouteComponent>
            }
          />

          <Route
            path='/personas'
            element={
              <GeneralProtectedRouteComponent allowedRoles={['ROLE_ADMIN']}>
                <ListPersonaComponent />
              </GeneralProtectedRouteComponent>
            }
          />
          <Route
            path='/add-persona'
            element={
              <GeneralProtectedRouteComponent allowedRoles={['ROLE_ADMIN']}>
                <AddPersonaComponent />
              </GeneralProtectedRouteComponent>
            }
          />
          <Route
            path='/edit-persona/:id'
            element={
              <GeneralProtectedRouteComponent allowedRoles={['ROLE_ADMIN']}>
                <AddPersonaComponent />
              </GeneralProtectedRouteComponent>
            }
          />

          <Route
            path='/administradores'
            element={
              <GeneralProtectedRouteComponent allowedRoles={['ROLE_ADMIN']}>
                <ListAdministradorComponent />
              </GeneralProtectedRouteComponent>
            }
          />
          <Route
            path='/add-administrador'
            element={
              <GeneralProtectedRouteComponent allowedRoles={['ROLE_ADMIN']}>
                <AddAdministradorComponent />
              </GeneralProtectedRouteComponent>
            }
          />
          <Route
            path='/edit-administrador/:id'
            element={
              <GeneralProtectedRouteComponent allowedRoles={['ROLE_ADMIN']}>
                <AddAdministradorComponent />
              </GeneralProtectedRouteComponent>
            }
          />

          <Route
            path='/administrativos'
            element={
              <GeneralProtectedRouteComponent allowedRoles={['ROLE_ADMIN']}>
                <ListAdministrativoComponent />
              </GeneralProtectedRouteComponent>
            }
          />
          <Route
            path='/add-administrativo'
            element={
              <GeneralProtectedRouteComponent allowedRoles={['ROLE_ADMIN']}>
                <AddAdministrativoComponent />
              </GeneralProtectedRouteComponent>
            }
          />
          <Route
            path='/edit-administrativo/:id'
            element={
              <GeneralProtectedRouteComponent allowedRoles={['ROLE_ADMIN']}>
                <AddAdministrativoComponent />
              </GeneralProtectedRouteComponent>
            }
          />

          <Route
            path='/docentes'
            element={
              <GeneralProtectedRouteComponent allowedRoles={['ROLE_ADMIN']}>
                <ListDocenteComponent />
              </GeneralProtectedRouteComponent>
            }
          />
          <Route
            path='/add-docente'
            element={
              <GeneralProtectedRouteComponent allowedRoles={['ROLE_ADMIN']}>
                <AddDocenteComponent />
              </GeneralProtectedRouteComponent>
            }
          />

          <Route
            path='/edit-docente/:id'
            element={
              <GeneralProtectedRouteComponent allowedRoles={['ROLE_ADMIN']}>
                <AddDocenteComponent />
              </GeneralProtectedRouteComponent>
            }
          />

          <Route
            path='/estudiantes'
            element={
              <GeneralProtectedRouteComponent allowedRoles={['ROLE_ADMIN']}>
                <ListEstudianteComponent />
              </GeneralProtectedRouteComponent>
            }
          />
          <Route
            path='/add-estudiante'
            element={
              <GeneralProtectedRouteComponent allowedRoles={['ROLE_ADMIN']}>
                <AddEstudianteComponent />
              </GeneralProtectedRouteComponent>
            }
          />

          <Route
            path='/edit-estudiante/:id'
            element={
              <GeneralProtectedRouteComponent allowedRoles={['ROLE_ADMIN']}>
                <AddEstudianteComponent />
              </GeneralProtectedRouteComponent>
            }
          />

          <Route
            path='/inscripcionesConRol'
            element={
              <GeneralProtectedRouteComponent allowedRoles={['ROLE_ADMIN']}>
                <ListInscripcionesConRolComponent />
              </GeneralProtectedRouteComponent>
            }
          />
          <Route
            path='/add-inscripcionConRol'
            element={
              <GeneralProtectedRouteComponent allowedRoles={['ROLE_ADMIN']}>
                <AddInscripcionesConRolComponent />
              </GeneralProtectedRouteComponent>
            }
          />
          <Route
            path='/edit-inscripcionConRol/:id'
            element={
              <GeneralProtectedRouteComponent allowedRoles={['ROLE_ADMIN']}>
                <AddInscripcionesConRolComponent />
              </GeneralProtectedRouteComponent>
            }
          />

          <Route path='/unauthorized' element={<Unauthorized />} /> {/* Ruta para no autorizado */}
          <Route path='*' element={<h2>404 - Página no encontrada</h2>} />
        </Routes>
      </div>
    </BrowserRouter>
  );
};

export default App;