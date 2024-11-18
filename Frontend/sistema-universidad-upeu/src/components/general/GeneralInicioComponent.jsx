import { Link, useNavigate } from "react-router-dom";

function GeneralInicioComponent() {
    const navigate = useNavigate();
    const ADMINISTRADOR_BASE_REST_API_URL = `${process.env.REACT_APP_API_BASE_URL}/administrador`

    function imprimir(){
        return(
            ADMINISTRADOR_BASE_REST_API_URL
        )
    }
    return (
        <div>
            <h1>Inicio</h1>
            <Link to="/login">LogIn</Link>
            <h1>{imprimir()}</h1>
        </div>
    )
}

export default GeneralInicioComponent;