import { useEffect, useState } from "react";
import EstudianteAdminService from "../../../services/administradorServices/estudiante/EstudianteAdminService";

function ListEstudianteComponent() {
    const [estudiantes, setEstudiantes] = useState([]);

    function listarEstudiantes() {
        EstudianteAdminService.getAllEstudiantes().then(response => {
            setEstudiantes(response.data);
            console.log(response.data);
        }).catch(error => {
            console.log(error);
        })
    }

    function borrarEstudiante(idEstudiante) {
        EstudianteAdminService.deleteEstudiante(idEstudiante).then(response => {
            listarEstudiantes();
            console.log("Estudiante con id " + idEstudiante + " fue eliminado correctamente");
        }).catch(error => {
            console.log(error);
        })
    }

    function title(){
        if(id){
            return <div>Actualizar Estudiante</div>
        }else{
            return <div>Agregar Estudiante</div>
        }
    }
    return (
        <div className="container">
            <h1>Gestionar Estudiante</h1>
            <table>
                <thead>
                    <th>ID</th>
                    <th>Matricula</th>
                    <th>Ciclo Actual</th>
                    <th>Promedio General</th>
                    <th>Fecha Ingreso</th>
                    <th>Estado</th>
                    <th>Tipo de Estudiante</th>
                    <th>Beca</th>
                    <th>Numero de Matricula</th>
                    <th>Id de la Carrerra</th>
                    <th>Asignaturas Matriculadas</th>
                    <th>Horario</th>
                    <th>Consejero Academico</th>
                    <th>Fecha de Graduacion</th>
                    <th>Practicas Realizadas</th>
                    <th>Historial Academico</th>
                    <th>Id del Curso</th>
                    <th>Id Persona</th>
                    <th>Fecha de creacion de Estudiante</th>
                    <th>Fecha de modificacion de Estudiante</th>
                </thead>
            </table>
        </div>
    );
}

export default ListEstudianteComponent;