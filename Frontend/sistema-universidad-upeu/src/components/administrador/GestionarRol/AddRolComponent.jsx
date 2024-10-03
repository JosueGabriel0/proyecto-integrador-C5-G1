import { useEffect, useState } from "react";
import RolAdminService from "../../../services/administradorServices/rol/RolAdminService";
import { Link, useNavigate, useParams } from "react-router-dom";

function AddRolComponent() {

    const [nombreRol, setNombreRol] = useState("");
    const [description, setDescription] = useState("");
    const navigate = useNavigate();
    const { id } = useParams();

    function saveOrUpdateRol(e) {
        e.preventDefault();
        const rol = { nombreRol, description };
        console.log(rol);

        if (id) {
            RolAdminService.updateRol(id, rol).then((response) => {
                console.log(response.data);
                navigate("/roles");
            }).catch(error => {
                console.log(error)
            })
        } else {
            RolAdminService.createRol(rol).then((response) => {
                console.log(response.data);
                navigate("/roles");
            }).catch(error => {
                console.log(error)
            })
        }
    }

    useEffect(() => {
        if (id) {
            RolAdminService.getRolById(id).then((response) => {
                setNombreRol(response.data.nombreRol);
                setDescription(response.data.description);
            }).catch(error => {
                console.log(error)
            })
        }
    }, [id])

    function title() {
        if (id) {
            return <div>Actualizar Rol</div>
        } else {
            return <div>Agregar Rol</div>
        }
    }

    function botonGuardarOActualizar() {
        if (id) {
            return <div>Actualizar</div>;
        } else {
            return <div>Guardar</div>;
        }
    }

    return (
        <div>
            <h1>{title()}</h1>
            <form>

                <div>
                    <label>Nombre del Rol</label>
                    <input type="text" placeholder="Inserte el rol" name="nombreRol" value={nombreRol} onChange={(e) => setNombreRol(e.target.value)} />
                </div>

                <div>
                    <label>Descripcion</label>
                    <input type="text" placeholder="Inserte la descripcion" name="description" value={description} onChange={(e) => setDescription(e.target.value)} />
                </div>
                <button onClick={(e) => saveOrUpdateRol(e)}>{botonGuardarOActualizar()}</button>
                &nbsp;
                &nbsp;
                <Link to="/roles">Cancelar</Link>
            </form>
        </div>
    )
}

export default AddRolComponent;