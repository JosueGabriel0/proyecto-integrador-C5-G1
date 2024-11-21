import React, { useEffect, useState } from "react";
import InscripcionConRolAdminService from "../../../../services/administradorServices/Inscripcion/InscripcionAdminService";
import { Link } from "react-router-dom";
import RolAdminService from "../../../../services/administradorServices/rol/RolAdminService";
import UsuarioAdminService from "../../../../services/administradorServices/usuario/UsuarioAdminService";
import PersonaAdminService from "../../../../services/administradorServices/persona/PersonaAdminService";
function ListConRolAdministrativoComponent(data) {
    const [inscripciones, setInscripciones] = useState([]);

    const [roles, setRoles] = useState([]);
    const [usuarios, setUsuarios] = useState([]);
    const [personas, setPersonas] = useState([]);

    function listarInscripcionesConRolAdministrativo() {
        InscripcionConRolAdminService.getAllInscripciones().then(response => {
            const inscripciones = response.data;

            if (inscripciones && inscripciones.length > 0) {
                const inscripcionesConAdministrativo = inscripciones.filter(
                    (inscripcionVerificar) =>
                        inscripcionVerificar.administrativo &&
                        inscripcionVerificar.administrativo.idAdministrativo
                );

                if (inscripcionesConAdministrativo.length > 0) {
                    setInscripciones(inscripcionesConAdministrativo);
                    console.log(inscripcionesConAdministrativo);
                } else {
                    setInscripciones([]);
                    console.log("No hay inscripciones de Administrativos");
                }
            } else {
                setInscripciones([]);
                console.log("No hay inscripciones");
            }
        }).catch(error => {
            console.log(error);
        })
    }

    function borrarInscripcionesConRolAdministrativo(idInscripcion) {
        InscripcionConRolAdminService.deleteInscripcion(idInscripcion).then(response => {
            listarInscripcionesConRolAdministrativo();
            console.log(response.data)
        }).catch(error => {
            console.log(error);
        })
    }

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

    function listarUsuarios() {
        UsuarioAdminService.getAllUsuarios().then(response => {
            setUsuarios(response.data);
            console.log(response.data);
        }).catch(error => {
            console.log(error);
        })
    }

    function obtenerUsernameUsuario(idUsuario) {
        const usuarioEncontrado = usuarios.find(usuario => usuario.idUsuario === idUsuario);
        return usuarioEncontrado ? usuarioEncontrado.username : "Desconocido";
    }

    async function listarPersonas() {
        const response = await PersonaAdminService.getAllPersonas();
        const personasConImagenes = await Promise.all(
            response.data.map(async (persona) => {
                console.log("Foto de Perfil:", persona.fotoPerfil); // Verificar el valor de fotoPerfil
                const imagenUrl = await PersonaAdminService.getPersonaImagen(persona.fotoPerfil);
                return { ...persona, imagenUrl };
            })
        );
        setPersonas(personasConImagenes);
    }

    function obtenerNombrePersona(idPersona) {
        const personaEncontrada = personas.find(persona => persona.id === idPersona);
        return personaEncontrada ? personaEncontrada.nombres : "Desconocido";
    }

    function obtenerImagenPersona(idPersona) {
        const personaEncontrada = personas.find((persona) => persona.id === idPersona);
        return personaEncontrada && personaEncontrada.imagenUrl ? personaEncontrada.imagenUrl : null;
    }

    useEffect(() => {
        listarInscripcionesConRolAdministrativo();
        listarRoles();
        listarUsuarios();
        listarPersonas();
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

    return (
        <div className="container">
            <Link to="/inscripcionesConRol">Retroceder</Link>
            <h2>Lista de Inscripciones Con Rol de Administrativos</h2>
            <Link to="/add-inscripcionConRol-administrativo">Agregar</Link>
            <table>
                <thead>
                    <tr>
                        <th colSpan="4">Datos de Inscripcion</th>
                        <th colSpan="5">Datos de Rol</th>
                        <th colSpan="11">Datos de Usuario</th>
                        <th colSpan="29">Datos de Persona</th>
                        <th colSpan="12">Datos de Administrativo</th>
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
                        <th>Responsable Financiero</th>
                        <th>Nombre del contacto de emergencia</th>
                        <th>Telefono del contacto de emergencia</th>
                        <th>Email del contacto de emergencia</th>
                        <th>Direcion del contacto de emergencia</th>
                        <th>Ciudad del contacto de emergencia</th>
                        <th>Parentesco del contacto de emergencia</th>
                        <th>Usuario</th>
                        <th>Fecha de Creacion de la Persona</th>
                        <th>Fecha de Modificacion de la Persona</th>

                        <th>ID del Administrativo</th>
                        <th>Registro de Pagos</th>
                        <th>Monto Total Pagos</th>
                        <th>Fecha de Ultimo Pago</th>
                        <th>Gestion de Empleados</th>
                        <th>Fecha de Contratacion</th>
                        <th>Cargo de Empleado</th>
                        <th>Solicitudes Pendientes</th>
                        <th>Fecha de Solicitud</th>
                        <th>Persona</th>
                        <th>Fecha de Creacion del Administrativo</th>
                        <th>Fecha de Modificacion del Administrativo</th>

                        <th>Acciones</th>

                    </tr>
                </thead>

                <tbody>
                    {inscripciones && Array.isArray(inscripciones) && inscripciones.length > 0 ? (
                        inscripciones.map(
                            inscripcion =>
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

                                    <td>{inscripcion.persona.id}</td>
                                    <td>{inscripcion.persona.nombres}</td>
                                    <td>{inscripcion.persona.apellido_paterno}</td>
                                    <td>{inscripcion.persona.apellido_materno}</td>
                                    <td>{inscripcion.persona.fecha_nacimiento}</td>
                                    <td>{inscripcion.persona.genero}</td>
                                    <td>{inscripcion.persona.nacionalidad}</td>
                                    <td>{inscripcion.persona.tipoDocumento}</td>
                                    <td>{inscripcion.persona.numeroDocumento}</td>
                                    <td>{inscripcion.persona.direccion}</td>
                                    <td>{inscripcion.persona.ciudad}</td>
                                    <td>{inscripcion.persona.departamento}</td>
                                    <td>{inscripcion.persona.pais}</td>
                                    <td>{inscripcion.persona.provincia}</td>
                                    <td>{inscripcion.persona.telefono}</td>
                                    <td>{inscripcion.persona.email}</td>
                                    <td>{inscripcion.persona.estadoCivil}</td>
                                    <td>
                                        {obtenerImagenPersona(inscripcion.idPersona) ? (
                                            <img
                                                src={obtenerImagenPersona(inscripcion.idPersona)}
                                                alt="Imagen de Persona"
                                                style={{ width: "50px", height: "50px" }}
                                            />
                                        ) : (
                                            <p>No disponible</p>
                                        )}
                                    </td>
                                    <td>{inscripcion.persona.tipoSangre}</td>
                                    <td>{inscripcion.persona.responsableFinanciero}</td>
                                    <td>{inscripcion.persona.contactoEmergenciaNombre}</td>
                                    <td>{inscripcion.persona.contactoEmergenciaTelefono}</td>
                                    <td>{inscripcion.persona.contactoEmergenciaEmail}</td>
                                    <td>{inscripcion.persona.contactoEmergenciaDireccion}</td>
                                    <td>{inscripcion.persona.contactoEmergenciaCiudad}</td>
                                    <td>{inscripcion.persona.contactoEmergenciaParentesco}</td>
                                    <td>{obtenerUsernameUsuario(inscripcion.persona.idUsuario)}</td>
                                    <td>{inscripcion.persona.fechaCreacionPersona}</td>
                                    <td>{inscripcion.persona.fechaModificacionPersona}</td>

                                    <td>{inscripcion.administrativo.idAdministrativo}</td>
                                    <td>{inscripcion.administrativo.registroPagos}</td>
                                    <td>{inscripcion.administrativo.montoTotalPagos}</td>
                                    <td>{inscripcion.administrativo.fechaUltimoPago}</td>
                                    <td>{inscripcion.administrativo.gestionEmpleados}</td>
                                    <td>{inscripcion.administrativo.fechaContratacion}</td>
                                    <td>{inscripcion.administrativo.cargoEmpleado}</td>
                                    <td>{inscripcion.administrativo.solicitudesPendientes}</td>
                                    <td>{inscripcion.administrativo.fechaSolicitud}</td>
                                    <td>{obtenerNombrePersona(inscripcion.administrativo.idPersona)}</td>
                                    <td>{inscripcion.administrativo.fechaCreacionAministrativo}</td>
                                    <td>{inscripcion.administrativo.fechaModificacionAministrativo}</td>
                                    <td>
                                        <Link to={`/edit-inscripcionConRol-administrativo/${inscripcion.idInscripcion}`}>Actualizar</Link>
                                        <button onClick={(e) => borrarInscripcionesConRolAdministrativo(inscripcion.idInscripcion)}>Eliminar</button>
                                    </td>

                                </tr>

                        )
                    ) : (
                        <tr>
                            <td colSpan="56" style={{ textAlign: 'center' }}>No hay inscripciones de Administrativos</td>
                        </tr>
                    )}
                </tbody>
            </table>

        </div>
    )
}

export default ListConRolAdministrativoComponent;