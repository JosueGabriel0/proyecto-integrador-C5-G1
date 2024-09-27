import { useEffect, useState } from "react"
import RolService from "../../../services/administrador/rol/RolService";
import { Link } from "react-router-dom";

function ListRolComponent(){
    const [roles, setRoles] = useState([]);

    useEffect(() => {
        listarRoles()
    }, [])

    const listarRoles = () => {
        RolService.getAllRoles().then(response => {
            setRoles(response.data);
            console.log(response.data);
        }).catch(error => {
            console.log(error);
        })
    }

    const deleteRol = (idRol) => {
        RolService.deleteRol(idRol).then((response) => {
            listarRoles();
        }).catch(error => {
            console.log(error);
        })
    }
    return(
        <div className="container">
        <h2>Lista de Roles</h2>
        <Link to='/add-rol'>Agregar Rol</Link>
        <table>
            <thead>
                <th>ID</th>
                <th>Nombre del Rol</th>
                <th>Descipcion</th>
            </thead>
            <tbody>{
                roles.map(
                    rol =>
                    <tr key={ rol.idRol }>
                        <td>{ rol.idRol }</td>
                        <td>{ rol.nombreRol }</td>
                        <td>{ rol.description }</td>
                        <td>
                            <button onClick={() => deleteRol(rol.idRol)}>Eliminar</button>
                        </td>
                    </tr>
                )
            }
            </tbody>
        </table>
        </div>
    )
}

export default ListRolComponent;