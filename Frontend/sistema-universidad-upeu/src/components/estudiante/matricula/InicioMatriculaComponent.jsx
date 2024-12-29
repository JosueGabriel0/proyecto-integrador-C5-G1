import React from "react";
import { Link } from "react-router-dom";

import { getInscripcionId } from "../../../services/authServices/authService";
import MatriculaService from "../../../services/matriculaServices/MatriculaService";

function InicioMatriculaComponent() {
    const idInscripcion = getInscripcionId();

    function componenteConValidacionExtra() {
        if (MatriculaService.getValidationEstudianteMatricula(idInscripcion) === "Estudiante validado") {
            return (
                <div>
                    <h1>Matricula virtual</h1>
                    <h4>Clic para iniciar el proceso de matricula virtual.</h4>
                    <Link to="/select-matricula-virtual-estudiante">Empezar</Link>
                </div>
            )
        } else {
            return (
                <div>
                    <h2>No es un estudiante, no tiene permitido realizar esta acción</h2>
                </div>
            )
        }
    }

    return (
        <div className="container">
            {componenteConValidacionExtra()}
        </div>
    )
}

export default InicioMatriculaComponent;