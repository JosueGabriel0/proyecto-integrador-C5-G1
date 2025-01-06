import React from "react"
import { Link } from "react-router-dom";

function PortalDelEstudianteComponent(){
    return (
        <div className="container">
            <h1>Bienvenido al Portal Del Estudiante</h1>
            <Link to="/plan-academico">Plan Academico</Link>&nbsp;
            <Link to="/estado-financiero">Mi estado financiero</Link>
        </div>
    )
}

export default PortalDelEstudianteComponent;