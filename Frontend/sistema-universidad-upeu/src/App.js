import React from 'react';
import { BrowserRouter as BrowserRouter, Routes,Route } from 'react-router-dom';
import GeneralProtectedRouteComponent from './components/general/GeneralProtectedRouteComponent';
import ListRolComponent from './components/administrador/rol/ListRolComponent';
import AddRolComponent from './components/administrador/rol/AddRolComponent';
import GeneralInicioComponent from './components/general/GeneralInicioComponent';
import GeneralLoginComponent from './components/general/GeneralLoginComponent';
import AdministradorDashboardComponent from './components/administrador/AdministradorDashboardComponent';
import AddUsuarioComponent from './components/administrador/usuario/AddUsuarioComponent';
import ListUsuarioComponent from './components/administrador/usuario/ListUsuarioComponent';


const App = () => {
  return (
    <BrowserRouter>
    <div className='container'>
    <Routes>
    <Route exact path='/' element={<GeneralInicioComponent />}></Route>
    <Route path='/login' element={<GeneralLoginComponent />}></Route>
    <Route path='/dashboard-administrador' element={<GeneralProtectedRouteComponent><AdministradorDashboardComponent /></GeneralProtectedRouteComponent>}></Route>
    <Route path='/roles' element={<ListRolComponent />}></Route>
    <Route path='/add-rol' element={<AddRolComponent />}></Route>
    <Route path='/edit-rol/:id' element={<AddRolComponent />}></Route>
    <Route path='/usuarios' element={<ListUsuarioComponent/>}></Route>
    <Route path='/add-usuario' element={<AddUsuarioComponent/>}></Route>
    <Route path='/edit-usuario/:id' element={<AddUsuarioComponent/>}></Route>
    </Routes>
    </div>
    </BrowserRouter>
  );
};

export default App;