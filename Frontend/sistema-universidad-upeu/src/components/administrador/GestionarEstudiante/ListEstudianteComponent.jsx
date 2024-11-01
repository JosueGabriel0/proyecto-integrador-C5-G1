import { useEffect, useState } from "react";
import EstudianteAdminService from "../../../services/administradorServices/estudiante/EstudianteAdminService";
import { Link } from "react-router-dom";

function ListEstudianteComponent() {
    const [estudiantes, setEstudiantes] = useState([]);

    useEffect(() => {
        listarEstudiantes();
    }, [])

    function listarEstudiantes() {
        EstudianteAdminService.getAllEstudiantes()
            .then(response => {
                setEstudiantes(response.data);
                console.log("Estos son los datos del backend:", response.data); // Usa coma en lugar de concatenar
            })
            .catch(error => {
                console.log("Error al obtener los datos:", error);
            });
    }

    function borrarEstudiante(idEstudiante) {
        EstudianteAdminService.deleteEstudiante(idEstudiante).then(response => {
            listarEstudiantes();
            console.log("Estudiante con id " + idEstudiante + " fue eliminado correctamente");
        }).catch(error => {
            console.log(error);
        })
    }

    return (
        <div className="container">
            <Link to="/dashboard-administrador">Retroceder</Link>
            <h1>Gestionar Estudiante</h1>
            <Link to="/add-estudiante">Agregar Estudiante</Link>

            <table>
                <thead>
                    <tr>
                        <th>ID</th>
                        <th>Matricula</th>
                        <th>Ciclo Actual</th>
                        <th>Promedio General</th>
                        <th>Fecha Ingreso</th>
                        <th>Estado</th>
                        <th>Tipo de Estudiante</th>
                        <th>Beca</th>
                        <th>Numero de Matricula</th>
                        <th>Careras Ingresadas</th>
                        <th>Asignaturas Matriculadas</th>
                        <th>Horario</th>
                        <th>Consejero Academico</th>
                        <th>Fecha de Graduacion</th>
                        <th>Practicas Realizadas</th>
                        <th>Historial Academico</th>
                        <th>Id Persona</th>
                        <th>Fecha de creacion de Estudiante</th>
                        <th>Fecha de modificacion de Estudiante</th>
                        <th>Acciones</th>
                    </tr>
                </thead>
                <tbody>
                    {
                        estudiantes.map(estudiante => (
                            <tr key={estudiante.idEstudiante}>
                                <td>{estudiante.idEstudiante}</td>
                                <td>{estudiante.matricula}</td>
                                <td>{estudiante.cicloActual}</td>
                                <td>{estudiante.promedioGeneral}</td>
                                <td>{estudiante.fechaIngreso}</td>
                                <td>{estudiante.estado}</td>
                                <td>{estudiante.tipoEstudiante}</td>
                                <td>{estudiante.beca}</td>
                                <td>{estudiante.numeroMatricula}</td>
                                <td>{estudiante.carrerasIngresadas?.join(", ")}</td>
                                <td>{estudiante.asignaturasMatriculadas?.join(", ")}</td>
                                <td>{estudiante.horario}</td>
                                <td>{estudiante.consejeroAcademico}</td>
                                <td>{estudiante.fechaGraduacion}</td>
                                <td>{estudiante.practicasRealizadas?.join(", ")}</td>
                                <td>
                                    {/* Renderizar historial académico */}
                                    <ul>
                                        {estudiante.historialAcademico?.map(registro => (
                                            <li key={registro.id}>
                                                <b>Curso:</b> {registro.nombreCurso} <br /> <b>- Calificación:</b> {registro.calificacion} <br /> <b>- Fecha Finalización:</b> {registro.fechaFinalizacion}
                                            </li>
                                        ))}
                                    </ul>
                                </td>
                                <td>{estudiante.idPersona}</td>
                                <td>{estudiante.fechaCreacionEstudiante}</td>
                                <td>{estudiante.fechaModificacionEstudiante}</td>

                                <td>
                                    <Link to={`/edit-estudiante/${estudiante.idEstudiante}`}>Actualizar</Link>
                                    <button onClick={() => borrarEstudiante(estudiante.idEstudiante)}>Borrar</button>
                                </td>
                            </tr>
                        ))

                    }


                </tbody>
            </table>
        </div>
    );
}

export default ListEstudianteComponent;