import { Link } from "react-router-dom"

function GestionarInscripcionesComponent(){
    return(
        <div className="container">
            <Link to="/dashboard-administrador">Retroceder</Link>
            &nbsp;
            &nbsp;
            <Link to="/list-inscripcion-administrador">Inscripcion de Administrador</Link>
            &nbsp;
            &nbsp;
            <Link to="/list-inscripcion-administrativo">Inscripcion de Administrativo</Link>
            &nbsp;
            &nbsp;
            <Link to="/list-inscripcion-Docente">Inscripcion de Docente</Link>
            &nbsp;
            &nbsp;
            <Link to="/list-inscripcion-Estudiante">Inscripcion de Estudiante</Link>
            &nbsp;
            &nbsp;
            <Link to="/list-inscripcion-nuevo-rol">Inscripcion de Persona con un Nuevo Rol Asignando Permisos</Link>
        </div>
    )
}

export default GestionarInscripcionesComponent;