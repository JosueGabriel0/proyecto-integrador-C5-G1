import EstudianteAdminService from "../../../services/administradorServices/estudiante/EstudianteAdminService";
import { Navigate, Link, useParams, useNavigate } from "react-router-dom";
import { useEffect, useState } from "react";

function AddEstudianteComponent() {
    const [matricula] = useState("");
    const [cicloActual] = useState("");
    const [promedioGeneral] = useState("");
    const [fechaIngreso] = useState("");
    const [estado] = useState("");
    const [tipoEstudiante] = useState("");
    const [beca] = useState("");
    const [numeroMatricula] = useState("");
    const [carreraId] = useState("");
    const [asignaturasMatriculadas] = useState("");
    const [horario] = useState("");
    const [consejeroAcademico] = useState("");
    const [fechaGraduacion] = useState("");
    const [practicasRealizadas] = useState("");
    const [historialAcademico] = useState("");
    const [idCurso] = useState("");
    const [idPersona] = useState("");
    const [estudiantes, setEstudiantes] = useState();

    const { id } = useParams();
    const navigate = useNavigate();

    useEffect(() => {
        listarEstudiantes();
    }, [])

    function listarEstudiantes() {
        EstudianteAdminService.getAllEstudiantes().then(response => {
            setEstudiantes(response.data);
            console.log(response.data);
        }).catch(error => {
            console.log(error);
        })
    }

    function title() {
        if (id) {
            return <div>Actualizar</div>
        } else {
            return <div>Agregar</div>
        }
    }

    return (
        <div className="container">
            <h1>{title()}</h1>
        </div>
    );
}

export default AddEstudianteComponent;