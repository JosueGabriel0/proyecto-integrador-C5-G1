import { useEffect, useState } from "react"
import RolAdminService from "../../../services/administradorServices/rol/RolAdminService";
import { Link } from "react-router-dom";

function ListRolComponent() {
    const [roles, setRoles] = useState([]);

    useEffect(() => {
        listarRoles()
    }, [])

    const listarRoles = () => {
        RolAdminService.getAllRoles().then(response => {
            setRoles(response.data);
            console.log(response.data);
        }).catch(error => {
            console.log(error);
        })
    }

    const deleteRol = (idRol) => {
        RolAdminService.deleteRol(idRol).then((response) => {
            listarRoles();
        }).catch(error => {
            console.log(error);
        })
    }
    return (
        <div className="container">
            <Link to="/dashboard-administrador">Retroceder</Link>
            <h2>Lista de Roles</h2>
            <Link to='/add-rol'>Agregar Rol</Link>
            <table>
                <thead>
                    <tr>
                        <th>ID</th>
                        <th>Nombre del Rol</th>
                        <th>Descipcion</th>
                        <th>Acciones</th>
                    </tr>
                </thead>
                <tbody>{
                    roles.map(
                        rol =>
                            <tr key={rol.idRol}>
                                <td>{rol.idRol}</td>
                                <td>{rol.nombreRol}</td>
                                <td>{rol.description}</td>
                                <td>
                                    <Link to={`/edit-rol/${rol.idRol}`}>Actualizar</Link>
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