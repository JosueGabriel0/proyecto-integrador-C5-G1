import React from "react";
import MatriculaService from "../../../services/matriculaServices/MatriculaService";
import { getInscripcionId } from "../../../services/authServices/authService";

function SelectNivEnsenianzaMatriculaComponent() {
    const idInscripcion = getInscripcionId();

    function componenteConValidacionExtra() {
        if (MatriculaService.getValidationEstudianteMatricula(idInscripcion) === "Estudiante validado") {
            return (
                <div>
                    <h1>Seleccione un nivel de enseñanza para su matricula.</h1>
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

export default SelectNivEnsenianzaMatriculaComponent;