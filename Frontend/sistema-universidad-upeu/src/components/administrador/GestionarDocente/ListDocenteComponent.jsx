import { useEffect, useState } from "react";
import DocenteAdminService from "../../../services/administradorServices/docente/DocenteAdminService";
import { Link } from "react-router-dom";
import PersonaAdminService from "../../../services/administradorServices/persona/PersonaAdminService";

function ListDocenteComponent() {

    const [docentes, setDocentes] = useState([]);
    const [personas, setPersonas] = useState([]);

    function listarPersonas(){
        PersonaAdminService.getAllPersonas().then(response => {
            setPersonas(response.data);
            console.log(response.data);
        }).catch(error => {
            console.log(error);
        })
    }

    function obtenerNombrePersona(idPersona){
        const personaEncontrada = personas.find(persona => persona.id === idPersona);
        return personaEncontrada? personaEncontrada.nombres : "Desconocido";
    }

    function listarDocentes() {
        DocenteAdminService.getAllDocentes().then(response => {
            setDocentes(response.data);
            console.log(response.data);
        }).catch(error => {
            console.log(error);
        })
    }

    function borrarDocente(idDocente) {
        DocenteAdminService.deleteDocente(idDocente).then(response => {
            listarDocentes();
        }).catch(error => {
            console.log(error);
        })
    }

    useEffect(() => {
        listarPersonas();
        listarDocentes();
    }, [])

    return (
        <div className="container">
            <Link to="/dashboard-administrador">Retroceder</Link>
            <h1>Gestionar Docentes</h1>
            <Link to="/add-docente">Agregar Docente</Link>

            <table>
                <thead>
                    <tr>
                        <th>ID</th>
                        <th>Departamento</th>
                        <th>Titulo Academico</th>
                        <th>Especialidad</th>
                        <th>Cursos Impartidos</th>
                        <th>Historial Laboral</th>
                        <th>Estado Laboral</th>
                        <th>Tipo de Docente</th>
                        <th>Fecha de Contratacion</th>
                        <th>Tipo Contrato</th>
                        <th>Salario</th>
                        <th>Horario</th>
                        <th>Publicaciones Academicas</th>
                        <th>Proyectos de Investigacion</th>
                        <th>Numero Oficina</th>
                        <th>Extencion Telefonica</th>
                        <th>Supervisor</th>
                        <th>Logros Academicos</th>
                        <th>Fecha de Jubilacion</th>
                        <th>Todos los Cursos Impartidos actualmente y anteriormente</th>
                        <th>Nombre de la Persona</th>
                        <th>Fecha Creacion de Docente</th>
                        <th>Fecha Modificacion de Docente</th>
                        <th>Acciones</th>
                    </tr>
                </thead>
                <tbody>
                    {
                        docentes.map(docente => (
                            <tr key={docente.idDocente}>
                                <td>{docente.idDocente}</td>
                                <td>{docente.departamento}</td>
                                <td>{docente.tituloAcatemico}</td>
                                <td>{docente.especialidad}</td>
                                <td>{docente.cursosImpartidos?.join(", ")}</td>
                                <td>
                                    {/* Renderizar historial laboral */}
                                    <ul>
                                        {docente.historialLaboral?.map(historial => (
                                            <li key={historial.id}>
                                                <b>Puesto:</b> {historial.puesto} <br /> <b>- Departamento:</b> {historial.departamento} <br /> <b>- Fecha Inicio:</b> {historial.fechaInicio} <br /> <b>- Fecha Fin:</b> {historial.fechaFin} <br /> <b>- Descripcion:</b> {historial.descripcion}
                                            </li>
                                        ))}
                                    </ul>
                                </td>
                                <td>{docente.estadoLaboral}</td>
                                <td>{docente.tipoDocente}</td>
                                <td>{docente.fechaContratacion}</td>
                                <td>{docente.tipoContrato}</td>
                                <td>{docente.salario}</td>
                                <td>{docente.horario}</td>
                                <td>{docente.publicacionesAcademicas?.join(", ")}</td>
                                <td>{docente.proyectosInvestigacion?.join(", ")}</td>
                                <td>{docente.numeroOficina}</td>
                                <td>{docente.extensionTelefonica}</td>
                                <td>{docente.supervisor}</td>
                                <td>{docente.logrosAcademicos?.join(", ")}</td>
                                <td>{docente.fechaJubilacion}</td>
                                <td>{docente.cursos?.join(", ")}</td>
                                <td>{obtenerNombrePersona(docente.idPersona)}</td>
                                <td>{docente.fechaCreacionDocente}</td>
                                <td>{docente.fechaModificacionDocente}</td>
                                <td>
                                    <Link to={`/edit-docente/${docente.idDocente}`}>Actualizar</Link>
                                    <button onClick={() => borrarDocente(docente.idDocente)}>Eliminar</button>
                                </td>
                            </tr>
                        ))
                    }
                </tbody>
            </table>
        </div>
    )
}

export default ListDocenteComponent;