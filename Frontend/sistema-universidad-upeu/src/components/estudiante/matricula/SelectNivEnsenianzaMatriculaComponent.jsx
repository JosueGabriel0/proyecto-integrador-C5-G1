import React from "react";
import { useEffect, useState } from "react";

import MatriculaService from "../../../services/matriculaServices/MatriculaService";
import { getInscripcionId } from "../../../services/authServices/authService";

function SelectNivEnsenianzaMatriculaComponent() {
    const idInscripcion = getInscripcionId();

    function componenteConValidacionExtra() {
        const [mensaje, setMensaje] = useState('');
        const [cargando, setCargando] = useState(true);

        useEffect(() => {
            // Llamada al backend para validar si es estudiante
            const validarEstudiante = async () => {
                try {
                    const response = await MatriculaService.getValidationEstudianteMatricula(idInscripcion);
                    setMensaje(response.data.mensaje); // Asume que el backend devuelve { mensaje: "..." }
                    console.log("Este es el mensaje del Backend: " + response.data.mensaje);
                } catch (error) {
                    console.error('Error al validar estudiante:', error);
                    setMensaje('Estudiante no encontrado');
                } finally {
                    setCargando(false); // Termina la carga
                }
            };

            validarEstudiante();
        }, [idInscripcion]);

        if (cargando) {
            return <div>Cargando...</div>;
        }

        if (mensaje === 'Estudiante validado') {
            return (
                <div>
                    <h1>Seleccione un nivel de enseñanza para su matricula.</h1>
                </div>
            );
        } else {
            return (
                <div>
                    <h2>No es un estudiante, no tiene permitido realizar esta acción</h2>
                </div>
            );
        }
    }

    return (
        <div className="container">
            {componenteConValidacionExtra()}
        </div>
    )
}

export default SelectNivEnsenianzaMatriculaComponent;