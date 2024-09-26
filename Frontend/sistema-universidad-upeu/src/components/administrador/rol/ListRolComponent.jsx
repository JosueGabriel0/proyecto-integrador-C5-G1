import { useEffect, useState } from "react"
import RolService from "../../../services/administrador/rol/RolService";

export const ListRolComponent = () => {
    const [roles, setRoles] = useState([]);

    useEffect(() => {
        RolService.getAllRoles().then(response =>{
            setRoles(response.data);
            console.log(response.data);
        }).catch(error => {
            console.log(error);
        })
    }, [])

    return(
        <div className="container">
        <h2>Lista de Roles</h2>
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
                    </tr>
                )
            }
            </tbody>
        </table>

        </div>
    )
}

export default ListRolComponent;