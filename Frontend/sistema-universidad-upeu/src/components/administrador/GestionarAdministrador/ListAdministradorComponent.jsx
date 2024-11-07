import { useEffect, useState } from "react";
import { Link } from "react-router-dom";
import AdministradorAdminService from "../../../services/administradorServices/administrador/AdministradorAdminService";
import PersonaAdminService from "../../../services/administradorServices/persona/PersonaAdminService";

function ListPersonaComponent() {
    const [administradores, setAdministradores] = useState([]);
    const [personas, setPersonas] = useState([]);

    useEffect(() => {
        listarAdministradores();
        listarPersonas();
    }, [])

    function listarPersonas() {
        PersonaAdminService.getAllPersonas().then(response => {
            setPersonas(response.data);
            console.log(response.data);
        }).catch(error => {
            console.log(error);
        })
    }

    function obtenerNombrePersona(idPersona) {
        const personaEncontrada = personas.find(persona => persona.id === idPersona);
        return personaEncontrada ? personaEncontrada.nombres : "Desconocido";
    }

    function listarAdministradores() {
        AdministradorAdminService.getAllAdministradores().then(response => {
            setAdministradores(response.data);
            console.log(response.data);
        }).catch(error => {
            console.log(error);
        });
    }

    function eliminarAdministrador(idAdministrador) {
        AdministradorAdminService.deleteAdministrador(idAdministrador).then(response => {
            listarAdministradores();
        }).catch(error => {
            console.log(error);
        })
    }

    return (
        <div className="container">
            <Link to="/dashboard-administrador">Retroceder</Link>
            <h1>Gestion de Administradores</h1>
            <Link to="/add-administrador">Agregar Administrador</Link>
            <table>
                <thead>
                    <tr>
                        <th>ID</th>
                        <th>Actividad Reciente</th>
                        <th>Fecha de Actividad</th>
                        <th>Estado del Sistema</th>
                        <th>Fecha de Ultima Revision</th>
                        <th>Permisos Especiales</th>
                        <th>Logs Accesos</th>
                        <th>Cambios de la Configuracion</th>
                        <th>Persona</th>
                        <th>Fecha de Creacion del Administrador</th>
                        <th>Fecha de Modificacion del Administrador</th>
                        <th>Acciones</th>
                    </tr>
                </thead>
                <tbody>
                    {
                        administradores.map(
                            administrador => {
                                return (
                                    <tr key={administrador.idAdministrador}>
                                        <td>{administrador.idAdministrador}</td>
                                        <td>{administrador.actividadReciente}</td>
                                        <td>{administrador.fechaActividad}</td>
                                        <td>{administrador.estadoSistema}</td>
                                        <td>{administrador.fechaUltimaRevision}</td>
                                        <td>{administrador.permisosEspeciales}</td>
                                        <td>{administrador.logsAcceso}</td>
                                        <td>{administrador.cambiosConfiguracion}</td>
                                        <td>{obtenerNombrePersona(administrador.idPersona)}</td>
                                        <td>{administrador.fechaCreacionAministrador}</td>
                                        <td>{administrador.fechaModificacionAministrador}</td>
                                        <td>
                                            <Link to={`/edit-administrador/${administrador.idAdministrador}`}>Actualizar</Link>
                                            <button onClick={() => eliminarAdministrador(administrador.idAdministrador)}>Eliminar</button>
                                        </td>
                                    </tr>
                                );
                            }
                        )
                    }
                </tbody>
            </table>
        </div>
    )
}

export default ListPersonaComponent;