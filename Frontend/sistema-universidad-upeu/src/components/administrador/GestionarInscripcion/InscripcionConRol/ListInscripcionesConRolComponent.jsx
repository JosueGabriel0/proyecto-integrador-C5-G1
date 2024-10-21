import React, { useEffect, useState } from "react";
import InscripcionConRolAdminService from "../../../../services/administradorServices/Inscripcion/InscripcionConRolAdminService";
import { useNavigate } from "react-router-dom";
function ListInscripcionesConRolComponent() {
    const [inscripciones, setInscripciones] = useState([]);
    const navigate = useNavigate();

    function listarInscripciones() {
        InscripcionConRolAdminService.getAllInscripciones().then(response => {
            setInscripciones(response.data);
            console.log(response.data);
        }).catch(error => {
            console.log(error);
        });
    }

    function borrarInscripciones(idInscripcion) {
        InscripcionConRolAdminService.deleteInscripcion(idInscripcion).then(response => {
            listarInscripciones();
            navigate("/inscripciones");
        }).catch(error => {
            console.log(error);
        })
    }

    useEffect(() => {
        listarInscripciones();
    }, []);

    function encabezadoTipo() {
    }

    return (
        <div className="container">
            <h2>Lista de Inscripciones Con Rol</h2>
            <table>
                <thead>
                    <tr>
                        <th colSpan="2">Datos de Inscripcion</th>
                        <th colSpan="3">Datos de Rol</th>
                        <th colSpan="7">Datos de Usuario</th>
                        <th colSpan="28">Datos de Persona</th>
                        <th colSpan="9">Datos de Administrador</th>
                        <th colSpan="10">Datos de Administrativo</th>
                        <th colSpan="10">Datos de Docente</th>
                        <th colSpan="10">Datos de Estudiante</th>
                        <th colSpan="1">Acciones</th>
                    </tr>
                    <tr>
                        <th>ID</th>
                        <th>Tipo de Inscripcion</th>

                        <th>Id del Rol</th>
                        <th>Nombre del Rol</th>
                        <th>Descipcion</th>

                        <th>ID</th>
                        <th>Nombre de usuario</th>
                        <th>Contraseña</th>
                        <th>Email</th>
                        <th>Disponible</th>
                        <th>Rol</th>
                        <th>Ultimo Login</th>
                        <th>Token de Recuperacion</th>
                        <th>Expiracion del Token de Recuperacion</th>

                        <th>ID</th>
                        <th>Nombres</th>
                        <th>Apellido Paterno</th>
                        <th>Apellido Materno</th>
                        <th>Fecha de Nacimiento</th>
                        <th>Genero</th>
                        <th>Nacionalidad</th>
                        <th>Tipo de documento</th>
                        <th>Numero de documento</th>
                        <th>Direccion</th>
                        <th>Ciudad</th>
                        <th>Departamento</th>
                        <th>País</th>
                        <th>Provincia</th>
                        <th>Telefono</th>
                        <th>Email</th>
                        <th>Estado Civil</th>
                        <th>Foto de Perfil</th>
                        <th>Tipo de sangre</th>
                        <th>Nombre del contacto de emergencia</th>
                        <th>Telefono del contacto de emergencia</th>
                        <th>Email del contacto de emergencia</th>
                        <th>Direcion del contacto de emergencia</th>
                        <th>Ciudad del contacto de emergencia</th>
                        <th>Parentesco del contacto de emergencia</th>
                        <th>Usuario</th>

                        <th>ID</th>
                        <th>Actividad Reciente</th>
                        <th>Fecha de Actividad</th>
                        <th>Estado del Sistema</th>
                        <th>Fecha de Ultima Revision</th>
                        <th>Permisos Especiales</th>
                        <th>Logs Accesos</th>
                        <th>Cambios de la Configuracion</th>
                        <th>Persona</th>

                        <th>ID</th>
                        <th>Registro de Pagos</th>
                        <th>Monto Total Pagos</th>
                        <th>Fecha de Ultimo Pago</th>
                        <th>Gestion de Empleados</th>
                        <th>Fecha de Contratacion</th>
                        <th>Cargo de Empleado</th>
                        <th>Solicitudes Pendientes</th>
                        <th>Fecha de Solicitud</th>
                        <th>Persona</th>

                        <th>Actualizar/Eliminar</th>

                    </tr>
                </thead>

                {/*
                <thead>{encabezadoTipo()}</thead>
                */}


                <tbody>
                    {
                        inscripciones.map((inscripcion) => (
                            <tr key={inscripcion.idInscripcion}>
                                <td>{inscripcion.idInscripcion}</td>
                                <td>{}</td>
                            </tr>
                        ))
                    }
                </tbody>
            </table>

        </div>
    )
}

export default ListInscripcionesConRolComponent;