import React, { useEffect, useState } from "react";
import InscripcionService from "../../../services/administrador/Inscripcion/InscripcionService";

export const ListInscripcionesComponent = () => {
    const [inscripciones, setInscripciones] = useState([]);

    useEffect(() => {
        InscripcionService.getAllInscripciones().then(response =>{
            setInscripciones(response.data);
            console.log(response.data);
        }).catch(error => {
            console.log(error);
        })
    }, [])

    return (
        <div className="container">
            <h2>Lista de Inscripciones</h2>
            <table>
                <thead>
                    <th>ID</th>
                    <th>Nombre del Rol</th>
                    <th>Descipcion</th>
                </thead>
                <tbody>
                    inscripciones.map(
                        inscripcion
                    )
                </tbody>
            </table>

        </div>
    )
}