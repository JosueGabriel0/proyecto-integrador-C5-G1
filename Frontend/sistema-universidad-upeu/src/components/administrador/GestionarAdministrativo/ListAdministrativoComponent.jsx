import { useEffect, useState } from "react";
import { Link } from "react-router-dom";
import AdministrativoAdminService from "../../../services/administradorServices/administrativo/AdministrativoAdminService";
import PersonaAdminService from "../../../services/administradorServices/persona/PersonaAdminService";
function ListAdministrativoComponent() {
    const [administrativos, setAdministrativos] = useState([]);
    const [personas, setPersonas] = useState([]);

    useEffect(() => {
        listarAdministrativos();
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

    function obtenerNombrePersona(idPersona){
        const personaEncontrada = personas.find(persona => persona.id === idPersona);
        return personaEncontrada ? personaEncontrada.nombres : "Desconocido";
    }

    function listarAdministrativos() {
        AdministrativoAdminService.getAllAdministrativos().then(response => {
            setAdministrativos(response.data);
            console.log(response.data);
        }).catch(error => {
            console.log(error);
        })
    }
    function borrarAdministrativo(idAdministrativo) {
        AdministrativoAdminService.deleteAdministrativo(idAdministrativo).then(response => {
            listarAdministrativos();
        }).catch(error => {
            console.log(error);
        })
    }

    return (
        <div className="container">
            <Link to="/dashboard-administrador">Retroceder</Link>
            <h1>Gestion de Administrativos</h1>
            <Link to="/add-administrativo">Agregar Administrativo</Link>

            <table>
                <thead>
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
                    <th>Fecha de Creacion del Administrativo</th>
                    <th>Fecha de Modificacion del Administrativo</th>
                    <th>Acciones</th>
                </thead>
                <tbody>
                    {
                        administrativos.map(administrativo => (
                            <tr key={administrativo.idAdministrativo}>
                                <td>{administrativo.idAdministrativo}</td>
                                <td>{administrativo.registroPagos}</td>
                                <td>{administrativo.montoTotalPagos}</td>
                                <td>{administrativo.fechaUltimoPago}</td>
                                <td>{administrativo.gestionEmpleados}</td>
                                <td>{administrativo.fechaContratacion}</td>
                                <td>{administrativo.cargoEmpleado}</td>
                                <td>{administrativo.solicitudesPendientes}</td>
                                <td>{administrativo.fechaSolicitud}</td>
                                <td>{obtenerNombrePersona(administrativo.idPersona)}</td>
                                <td>{administrativo.fechaCreacionAministrativo}</td>
                                <td>{administrativo.fechaModificacionAministrativo}</td>
                                <td>
                                    <Link to={`/edit-administrativo/${administrativo.idAdministrativo}`}>Actualizar</Link>
                                    <button onClick={() => borrarAdministrativo(administrativo.idAdministrativo)}>Eliminar</button>
                                </td>
                            </tr>
                        ))
                    }
                </tbody>
            </table>
        </div>
    )
}

export default ListAdministrativoComponent;