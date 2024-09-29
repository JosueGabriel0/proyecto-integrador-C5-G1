import { useEffect, useState } from "react";
import RolAdminService from "../../../services/administradorServices/rol/RolAdminService";
import { Link, useNavigate, useParams } from "react-router-dom";
import RolAdminService from "../../../services/administradorServices/rol/RolAdminService";

function AddRolComponent() {

    const [nombreRol,setNombreRol] = useState("");
    const [description,setDescription] = useState("");
    const navigate = useNavigate();
    const {id} =useParams();

    function saveRol(e){
        e.preventDefault();
        const rol = {nombreRol,description};
        console.log(rol);
        RolAdminService.createRol(rol).then((response) => {
            console.log(response.data);
            navigate("/roles");
        }).catch(error => {
            console.log(error)
        })
    }

    useEffect(()=>{
        RolAdminService.getRolById(id).then((response)=>{
            setNombreRol(response.data.nombreRol);
            setDescription(response.data.description);
        }).catch(error => {
            console.log(error)
        })
    })

    function title(){
        if(id){
            return <h2>Actualizar Rol</h2>
        }else{
            return <h2>Agregar Rol</h2>
        }
    }

    return(
        <div>
            <h1></h1>
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