import React, { useEffect, useState } from "react";
import InscripcionConRolAdminService from "../../../../services/administradorServices/Inscripcion/InscripcionConRolAdminService";
import { useNavigate } from "react-router-dom";
function ListInscripcionesConRolComponent() {
    const [inscripciones, setInscripciones] = useState([]);
    const navigate = useNavigate();

    function listarInscripciones() {
        InscripcionConRolAdminService.getAllInscripciones().then(response => {
            setInscripciones(response.data);
            console.log(response.data);
        }).catch(error => {
            console.log(error);
        });
    }

    function borrarInscripciones(idInscripcion) {
        InscripcionConRolAdminService.deleteInscripcion(idInscripcion).then(response => {
            listarInscripciones();
            navigate("/inscripciones");
        }).catch(error => {
            console.log(error);
        })
    }

    useEffect(() => {
        listarInscripciones();
    }, []);

    function encabezadoTipo() {
    }

    return (
        <div className="container">
            <h2>Lista de Inscripciones Con Rol</h2>
            <table>
                <thead>
                    <tr>
                        <th colSpan="3">Datos de Inscripcion</th>
                        <th colSpan="3">Datos de Rol</th>
                        <th colSpan="3">Datos de Usuario</th>
                        <th colSpan="3">Datos de Persona</th>
                        <th colSpan="3">Datos de Administrador</th>
                    </tr>
                    <tr>
                        <th>ID</th>
                        <th>Tipo de Inscripcion</th>

                        <th>Id del Rol</th>
                        <th>Nombre del Rol</th>
                        <th>Descipcion</th>

                        <th>Nombre de Usuario</th>
                        <th>Contraseña</th>
                        <th>Email</th>
                        <th>Disponible</th>
                        <th>Ultimo Login</th>
                        <th>Token de Recuperacion</th>
                        <th>Expiracion del Token de Recuperacion</th>
                    </tr>
                </thead>

                {/*
                <thead>{encabezadoTipo()}</thead>
                */}


                <tbody>
                    {
                        inscripciones.map((inscripcion) => (
                            <tr key={inscripcion.idInscripcion}>
                                <td>{inscripcion.idInscripcion}</td>
                                <td>{ }</td>
                            </tr>
                        ))
                    }
                </tbody>
            </table>

        </div>
    )
}

export default ListInscripcionesConRolComponent;