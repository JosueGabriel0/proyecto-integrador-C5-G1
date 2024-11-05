import React, { useEffect, useState } from "react";
import InscripcionConRolAdminService from "../../../../services/administradorServices/Inscripcion/InscripcionConRolAdminService";
import { useNavigate } from "react-router-dom";
import RolAdminService from "../../../../services/administradorServices/rol/RolAdminService";
function ListConRolAdministradorComponent() {
    const [inscripciones, setInscripciones] = useState([]);
    const navigate = useNavigate();

    const [roles, setRoles] = useState([]);


    function listarInscripcionesConRolAdministrador() {
        InscripcionConRolAdminService.getAllInscripciones().then(response => {
            setInscripciones(response.data);
            console.log(response.data);
        }).catch(error => {
            console.log(error);
        })
    }

    function borrarInscripcionesConRolAdministrador(idInscripcion) {
        InscripcionConRolAdminService.deleteInscripcion(idInscripcion).then(response => {
            listarInscripcionesConRolAdministrador();
            console.log(response.data)
        }).catch(error => {
            console.log(error);
        })
    }

    useEffect(() => {
        listarInscripcionesConRolAdministrador();
        listarRoles();
    }, []);

    // Asumiendo que inscripcion.fechaCreacionInscripcion es el array de la fecha
    const formatFecha = (fechaArray) => {
        if (!fechaArray) return "Fecha no disponible";

        const [year, month, day, hour, minute, second, nanosecond] = fechaArray;
        // Crear el objeto Date
        const date = new Date(
            year,
            month - 1, // Mes en JavaScript va de 0 (enero) a 11 (diciembre)
            day,
            hour,
            minute,
            second,
            nanosecond / 1_000_000 // Convertir nanosegundos a milisegundos
        );

        // Formatear la fecha
        return date.toLocaleDateString("es-ES", {
            day: "2-digit",
            month: "2-digit",
            year: "numeric",
            hour: "2-digit",
            minute: "2-digit",
        });
    };

    // Función para convertir el tiempo Epoch en fecha legible
    const formatFechaDesdeEpoch = (epochTime) => {
        if (!epochTime) return "Fecha no disponible";

        // Convertir a milisegundos y crear el objeto Date
        const date = new Date(epochTime * 1000);

        // Formatear la fecha
        return date.toLocaleDateString("es-ES", {
            day: "2-digit",
            month: "2-digit",
            year: "numeric",
            hour: "2-digit",
            minute: "2-digit",
        });
    };

    function listarRoles() {
        RolAdminService.getAllRoles().then(response => {
            setRoles(response.data); // Guardar los roles en el estado
            console.log(response.data);
        }).catch(error => {
            console.log(error);
        });
    }

    function obtenerNombreRol(idRol) {
        const rolEncontrado = roles.find(rol => rol.idRol === idRol);
        return rolEncontrado ? rolEncontrado.nombreRol : "Desconocido"; // Devuelve el nombre del rol o "Desconocido"
    }

    return (
        <div className="container">
            <h2>Lista de Inscripciones Con Rol</h2>
            <table>
                <thead>
                    <tr>
                        <th colSpan="4">Datos de Inscripcion</th>
                        <th colSpan="5">Datos de Rol</th>
                        <th colSpan="11">Datos de Usuario</th>
                        <th colSpan="30">Datos de Persona</th>
                        <th colSpan="11">Datos de Administrador</th>
                        <th colSpan="1">Acciones</th>
                    </tr>
                    <tr>
                        <th>ID Inscripcion</th>
                        <th>Tipo de Inscripcion</th>
                        <th>Fecha de Creacion de la Inscripcion</th>
                        <th>Fecha de Modificacion de la Inscripcion</th>

                        <th>ID del Rol</th>
                        <th>Nombre del Rol</th>
                        <th>Descipcion</th>
                        <th>Fecha de Creacion del Rol</th>
                        <th>Fecha de Modificacion del Rol</th>

                        <th>ID del Usuario</th>
                        <th>Nombre de usuario</th>
                        <th>Contraseña</th>
                        <th>Email</th>
                        <th>Disponible</th>
                        <th>Nombre Rol</th>
                        <th>Ultimo Login</th>
                        <th>Token de Recuperacion</th>
                        <th>Expiracion del Token de Recuperacion</th>
                        <th>Fecha de Creacion del Usuario</th>
                        <th>Fecha de Modificacion del Usuario</th>

                        <th>ID de la Persona</th>
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
                        <th>Fecha de Creacion de la Persona</th>
                        <th>Fecha de Modificacion de la Persona</th>

                        <th>ID del Administrador</th>
                        <th>Actividad Reciente</th>
                        <th>Fecha de Actividad</th>
                        <th>Estado del Sistema</th>
                        <th>Fecha de Ultima Revision</th>
                        <th>Permisos Especiales</th>
                        <th>Logs Accesos</th>
                        <th>Cambios de la Configuracion</th>
                        <th>Persona</th>
                        <th>Fecha de Creacion del Administrador</th>
                        <th>Fecha de Modificacion Administrador</th>

                        <th>Acciones</th>

                    </tr>
                </thead>

                <tbody>
                    {
                        inscripciones.map((inscripcion) => (
                            <tr key={inscripcion.idInscripcion}>
                                <td>{inscripcion.idInscripcion}</td>
                                <td>{inscripcion.inscripcionRol}</td>
                                <td>{formatFecha(inscripcion.fechaCreacionInscripcion)}</td>
                                <td>{formatFecha(inscripcion.fechaModificacionInscripcion)}</td>

                                <td>{inscripcion.rol.idRol}</td>
                                <td>{inscripcion.rol.nombreRol}</td>
                                <td>{inscripcion.rol.description}</td>
                                <td>{formatFecha(inscripcion.rol.fechaCreacionRol)}</td>
                                <td>{formatFecha(inscripcion.rol.fechaModificacionRol)}</td>

                                <td>{inscripcion.usuario.idUsuario}</td>
                                <td>{inscripcion.usuario.username}</td>
                                <td>{inscripcion.usuario.password}</td>
                                <td>{inscripcion.usuario.email}</td>
                                <td>{inscripcion.usuario.enabled ? "Disponible" : "No disponible"}</td>
                                <td>{obtenerNombreRol(inscripcion.usuario.idRol)}</td>
                                <td>{formatFecha(inscripcion.usuario.ultimoLogin)}</td>
                                <td>{inscripcion.usuario.tokenRecuperacion}</td>
                                <td>{formatFechaDesdeEpoch(inscripcion.usuario.tokenRecuperacionExpiracion)}</td>
                                <td>{formatFecha(inscripcion.usuario.fechaCreacionUsuario)}</td>
                                <td>{formatFecha(inscripcion.usuario.fechaModificacionUsuario)}</td>

                                <td>{persona.id}</td>
                                <td>{persona.nombres}</td>
                                <td>{persona.apellido_paterno}</td>
                                <td>{persona.apellido_materno}</td>
                                <td>{format(new Date(persona.fecha_nacimiento), 'dd-MM-yyyy')}</td>
                                <td>{persona.genero}</td>
                                <td>{persona.nacionalidad}</td>
                                <td>{persona.tipoDocFumento}</td>
                                <td>{persona.numeroDocumento}</td>
                                <td>{persona.direccion}</td>
                                <td>{persona.ciudad}</td>
                                <td>{persona.departamento}</td>
                                <td>{persona.pais}</td>
                                <td>{persona.provincia}</td>
                                <td>{persona.telefono}</td>
                                <td>{persona.email}</td>
                                <td>{persona.estadoCivil}</td>
                                <td>
                                    {persona.imagenUrl ? (
                                        <img src={persona.imagenUrl} alt="Imagen de Persona" style={{ width: '50px', height: '50px' }} />
                                    ) : (
                                        <p>No disponible</p>
                                    )}
                                </td>
                                <td>{persona.tipoSangre}</td>
                                <td>{persona.responsableFinanciero}</td>
                                <td>{persona.contactoEmergenciaNombre}</td>
                                <td>{persona.contactoEmergenciaTelefono}</td>
                                <td>{persona.contactoEmergenciaEmail}</td>
                                <td>{persona.contactoEmergenciaDireccion}</td>
                                <td>{persona.contactoEmergenciaCiudad}</td>
                                <td>{persona.contactoEmergenciaParentesco}</td>
                                <td>{obtenerUsernameUsuario(persona.idUsuario)}</td>
                                <td>
                                    <Link to={`/edit-persona/${persona.id}`}>Actualizar</Link>
                                    <button onClick={() => deletePersona(persona.id)}>Eliminar</button>
                                </td>
                            </tr>
                        ))
                    }
                </tbody>
            </table>

        </div>
    )
}

export default ListConRolAdministradorComponent;