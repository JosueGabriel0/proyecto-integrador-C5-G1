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
import OrderListConRolComponent from './components/administrador/GestionarInscripcion/InscripcionConRol/OrderListConRolComponent';

import ListConRolAdministradorComponent from './components/administrador/GestionarInscripcion/InscripcionConRol/ListConRolAdministradorComponent';
import ListConRolAdministrativoComponent from './components/administrador/GestionarInscripcion/InscripcionConRol/ListConRolAdministrativoComponent';
import ListConRolDocenteComponent from './components/administrador/GestionarInscripcion/InscripcionConRol/ListConRolDocenteComponent';
import ListConRolEstudianteComponent from './components/administrador/GestionarInscripcion/InscripcionConRol/ListConRolEstudianteComponent';

import AddConRolAdministradorComponent from './components/administrador/GestionarInscripcion/InscripcionConRol/AddConRolAdministradorComponent';
import AddConRolAdministrativoComponent from './components/administrador/GestionarInscripcion/InscripcionConRol/AddConRolAdministrativoComponent';
import AddConRolDocenteComponent from './components/administrador/GestionarInscripcion/InscripcionConRol/AddConRolDocenteComponent';
import AddConRolEstudianteComponent from './components/administrador/GestionarInscripcion/InscripcionConRol/AddConRolEstudianteComponent';

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
              <GeneralProtectedRouteComponent allowedRoles={['ADMINISTRADOR']}>
                <AdministradorDashboardComponent />
              </GeneralProtectedRouteComponent>
            }
          />

          {/* Rutas protegidas */}
          <Route
            path='/roles'
            element={
              <GeneralProtectedRouteComponent allowedRoles={['ADMINISTRADOR']}>
                <ListRolComponent />
              </GeneralProtectedRouteComponent>
            }
          />
          <Route
            path='/add-rol'
            element={
              <GeneralProtectedRouteComponent allowedRoles={['ADMINISTRADOR']}>
                <AddRolComponent />
              </GeneralProtectedRouteComponent>
            }
          />
          <Route
            path='/edit-rol/:id'
            element={
              <GeneralProtectedRouteComponent allowedRoles={['ADMINISTRADOR']}>
                <AddRolComponent />
              </GeneralProtectedRouteComponent>
            }
          />

          <Route
            path='/usuarios'
            element={
              <GeneralProtectedRouteComponent allowedRoles={['ADMINISTRADOR']}>
                <ListUsuarioComponent />
              </GeneralProtectedRouteComponent>
            }
          />
          <Route
            path='/add-usuario'
            element={
              <GeneralProtectedRouteComponent allowedRoles={['ADMINISTRADOR']}>
                <AddUsuarioComponent />
              </GeneralProtectedRouteComponent>
            }
          />
          <Route
            path='/edit-usuario/:id'
            element={
              <GeneralProtectedRouteComponent allowedRoles={['ADMINISTRADOR']}>
                <AddUsuarioComponent />
              </GeneralProtectedRouteComponent>
            }
          />

          <Route
            path='/personas'
            element={
              <GeneralProtectedRouteComponent allowedRoles={['ADMINISTRADOR']}>
                <ListPersonaComponent />
              </GeneralProtectedRouteComponent>
            }
          />
          <Route
            path='/add-persona'
            element={
              <GeneralProtectedRouteComponent allowedRoles={['ADMINISTRADOR']}>
                <AddPersonaComponent />
              </GeneralProtectedRouteComponent>
            }
          />
          <Route
            path='/edit-persona/:id'
            element={
              <GeneralProtectedRouteComponent allowedRoles={['ADMINISTRADOR']}>
                <AddPersonaComponent />
              </GeneralProtectedRouteComponent>
            }
          />

          <Route
            path='/administradores'
            element={
              <GeneralProtectedRouteComponent allowedRoles={['ADMINISTRADOR']}>
                <ListAdministradorComponent />
              </GeneralProtectedRouteComponent>
            }
          />
          <Route
            path='/add-administrador'
            element={
              <GeneralProtectedRouteComponent allowedRoles={['ADMINISTRADOR']}>
                <AddAdministradorComponent />
              </GeneralProtectedRouteComponent>
            }
          />
          <Route
            path='/edit-administrador/:id'
            element={
              <GeneralProtectedRouteComponent allowedRoles={['ADMINISTRADOR']}>
                <AddAdministradorComponent />
              </GeneralProtectedRouteComponent>
            }
          />

          <Route
            path='/administrativos'
            element={
              <GeneralProtectedRouteComponent allowedRoles={['ADMINISTRADOR']}>
                <ListAdministrativoComponent />
              </GeneralProtectedRouteComponent>
            }
          />
          <Route
            path='/add-administrativo'
            element={
              <GeneralProtectedRouteComponent allowedRoles={['ADMINISTRADOR']}>
                <AddAdministrativoComponent />
              </GeneralProtectedRouteComponent>
            }
          />
          <Route
            path='/edit-administrativo/:id'
            element={
              <GeneralProtectedRouteComponent allowedRoles={['ADMINISTRADOR']}>
                <AddAdministrativoComponent />
              </GeneralProtectedRouteComponent>
            }
          />

          <Route
            path='/docentes'
            element={
              <GeneralProtectedRouteComponent allowedRoles={['ADMINISTRADOR']}>
                <ListDocenteComponent />
              </GeneralProtectedRouteComponent>
            }
          />
          <Route
            path='/add-docente'
            element={
              <GeneralProtectedRouteComponent allowedRoles={['ADMINISTRADOR']}>
                <AddDocenteComponent />
              </GeneralProtectedRouteComponent>
            }
          />

          <Route
            path='/edit-docente/:id'
            element={
              <GeneralProtectedRouteComponent allowedRoles={['ADMINISTRADOR']}>
                <AddDocenteComponent />
              </GeneralProtectedRouteComponent>
            }
          />

          <Route
            path='/estudiantes'
            element={
              <GeneralProtectedRouteComponent allowedRoles={['ADMINISTRADOR']}>
                <ListEstudianteComponent />
              </GeneralProtectedRouteComponent>
            }
          />
          <Route
            path='/add-estudiante'
            element={
              <GeneralProtectedRouteComponent allowedRoles={['ADMINISTRADOR']}>
                <AddEstudianteComponent />
              </GeneralProtectedRouteComponent>
            }
          />

          <Route
            path='/edit-estudiante/:id'
            element={
              <GeneralProtectedRouteComponent allowedRoles={['ADMINISTRADOR']}>
                <AddEstudianteComponent />
              </GeneralProtectedRouteComponent>
            }
          />

          <Route
            path='/inscripcionesConRol'
            element={
              <GeneralProtectedRouteComponent allowedRoles={['ADMINISTRADOR']}>
                <OrderListConRolComponent />
              </GeneralProtectedRouteComponent>
            }
          />

          <Route
            path='/list-inscripcionConRol-administrador'
            element={
              <GeneralProtectedRouteComponent>
                <ListConRolAdministradorComponent />
              </GeneralProtectedRouteComponent>
            }
          />

          <Route
            path='/list-inscripcionConRol-administrativo'
            element={
              <GeneralProtectedRouteComponent>
                <ListConRolAdministrativoComponent />
              </GeneralProtectedRouteComponent>
            }
          />

          <Route
            path='/list-inscripcionConRol-docente'
            element={
              <GeneralProtectedRouteComponent>
                <ListConRolDocenteComponent />
              </GeneralProtectedRouteComponent>
            }
          />

          <Route
            path='/list-inscripcionConRol-estudiante'
            element={
              <GeneralProtectedRouteComponent>
                <ListConRolEstudianteComponent />
              </GeneralProtectedRouteComponent>
            }
          />

          <Route
            path='/add-inscripcionConRol-administrador'
            element={
              <GeneralProtectedRouteComponent allowedRoles={['ADMINISTRADOR']}>
                <AddConRolAdministradorComponent />
              </GeneralProtectedRouteComponent>
            }
          />

          <Route
            path='/add-inscripcionConRol-administrativo'
            element={
              <GeneralProtectedRouteComponent allowedRoles={['ADMINISTRADOR']}>
                <AddConRolAdministrativoComponent />
              </GeneralProtectedRouteComponent>
            }
          />

          <Route
            path='/add-inscripcionConRol-docente'
            element={
              <GeneralProtectedRouteComponent allowedRoles={['ADMINISTRADOR']}>
                <AddConRolDocenteComponent />
              </GeneralProtectedRouteComponent>
            }
          />

          <Route
            path='/add-inscripcionConRol-estudiante'
            element={
              <GeneralProtectedRouteComponent allowedRoles={['ADMINISTRADOR']}>
                <AddConRolEstudianteComponent />
              </GeneralProtectedRouteComponent>
            }
          />

          <Route
            path='/edit-inscripcionConRol-administrador/:id'
            element={
              <GeneralProtectedRouteComponent allowedRoles={['ADMINISTRADOR']}>
                <AddConRolAdministradorComponent />
              </GeneralProtectedRouteComponent>
            }
          />

          <Route
            path='/edit-inscripcionConRol-administrativo/:id'
            element={
              <GeneralProtectedRouteComponent allowedRoles={['ADMINISTRADOR']}>
                <AddConRolAdministrativoComponent />
              </GeneralProtectedRouteComponent>
            }
          />

          <Route
            path='/edit-inscripcionConRol-docente/:id'
            element={
              <GeneralProtectedRouteComponent allowedRoles={['ADMINISTRADOR']}>
                <AddConRolDocenteComponent />
              </GeneralProtectedRouteComponent>
            }
          />

          <Route
            path='/edit-inscripcionConRol-estudiante/:id'
            element={
              <GeneralProtectedRouteComponent allowedRoles={['ADMINISTRADOR']}>
                <AddConRolEstudianteComponent />
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