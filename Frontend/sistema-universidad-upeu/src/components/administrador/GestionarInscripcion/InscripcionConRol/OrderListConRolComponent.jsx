import { Link } from "react-router-dom"

function OrderListConRolComponent(){
    return(
        <div className="container">
            <Link to="/dashboard-administrador">Retroceder</Link>
            &nbsp;
            &nbsp;
            <Link to="/list-inscripcionConRol-administrador">Inscripcion de Administrador</Link>
            &nbsp;
            &nbsp;
            <Link to="/list-inscripcionConRol-administrativo">Inscripcion de Administrativo</Link>
            &nbsp;
            &nbsp;
            <Link to="/list-inscripcionConRol-Docente">Inscripcion de Docente</Link>
            &nbsp;
            &nbsp;
            <Link to="/list-inscripcionConRol-Estudiante">Inscripcion de Estudiante</Link>
        </div>
    )
}

export default OrderListConRolComponent;