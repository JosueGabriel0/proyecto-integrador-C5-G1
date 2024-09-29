import { Link, useNavigate } from "react-router-dom";

function GeneralInicioComponent(){

    const navigate = useNavigate();

    return(
    <div>
        <h1>Inicio</h1>
        <Link to="/login">LogIn</Link>
    </div>
    )
}

export default GeneralInicioComponent;