import { useState } from "react";
import RolService from "../../../services/administrador/rol/RolService";
import { Link, useNavigate } from "react-router-dom";

function AddRolComponent() {

    const [nombreRol,setNombreRol] = useState("");
    const [description,setDescription] = useState("");
    const navigate = useNavigate();

    function saveRol(e){
        e.preventDefault();
        const rol = {nombreRol,description};
        console.log(rol);
        RolService.createRol(rol).then((response) => {
            console.log(response.data);
            navigate("/roles");
        }).catch(error => {
            console.log(error)
        })
    }

    return(
        <div>
            <h1>Registro de Roles</h1>
            <form>

                <div>
                    <label>Nombre del Rol</label>
                    <input type="text" placeholder="Inserte el rol" name="nombreRol" value={nombreRol} onChange={(e) => setNombreRol(e.target.value)}/>
                </div>

                <div>
                    <label>Descripcion</label>
                    <input type="text" placeholder="Inserte la descripcion" name="description" value={description} onChange={(e) => setDescription(e.target.value)}/>
                </div>
                <button onClick={ (e) => saveRol(e) }>Guardar</button>
                &nbsp;
                &nbsp;
                <Link to="/roles">Cancelar</Link>
            </form>
        </div>
    )
}

export default AddRolComponent;