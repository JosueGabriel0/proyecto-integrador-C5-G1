import { Link } from "react-router-dom";
import { useEffect, useState } from "react";
import UsuarioAdminService from "../../../services/administradorServices/usuario/UsuarioAdminService";
import RolAdminService from "../../../services/administradorServices/rol/RolAdminService";

function ListUsuarioComponent(){
    const [usuarios, setUsuarios] = useState([]);

    const [roles, setRoles] = useState([]);

    useEffect(() => {
        listarUsuarios();
        listarRoles(); // Cargar roles al inicio
    },[])

    function listarRoles() {
        RolAdminService.getAllRoles().then(response => {
            setRoles(response.data); // Guardar los roles en el estado
            console.log(response.data);
        }).catch(error => {
            console.log(error);
        });
    }

    function obtenerNombreRol(idRol) {
        const rolEncontrado = roles.find(rol => rol.idRol === idRol);
        return rolEncontrado ? rolEncontrado.nombreRol : "Desconocido"; // Devuelve el nombre del rol o "Desconocido"
    }

    function listarUsuarios(){
        UsuarioAdminService.getAllUsuarios().then(response => {
            setUsuarios(response.data);
            console.log(response.data);
        }).catch(error => {
            console.log(error);
        })
    }

    function eliminarUsuario(idUsuario){
        UsuarioAdminService.deleteUsuario(idUsuario).then(response => {
            listarUsuarios();
        }).catch(error => {
            console.log(error);
        })
    }
    return(
        <div className="container">
            <Link to="/dashboard-administrador">Retroceder</Link>
        <div>
            <h1>Listar Usuarios</h1>
            <Link to="/add-usuario">Agregar Usuario</Link>
            <table>
                <thead>
                    <th>ID</th>
                    <th>Nombre de usuario</th>
                    <th>Contraseña</th>
                    <th>Email</th>
                    <th>Disponible</th>
                    <th>Rol</th>
                    <th>Ultimo Login</th>
                    <th>Token de Recuperacion</th>
                    <th>Expiracion del Token de Recuperacion</th>
                    <th>Fecha de Creacion de Usuario</th>
                    <th>Fecha de Modificacion de Usuario</th>
                    <th>Acciones</th>
                </thead>
                <tbody>{
                    usuarios.map(
                        usuario =>
                            <tr key={usuario.idUsuario}>
                                <td>{usuario.idUsuario}</td>
                                <td>{usuario.username}</td>
                                <td>{usuario.password}</td>
                                <td>{usuario.email}</td>
                                <td>{usuario.enabled ? "Disponible" : "No disponible"}</td>
                                <td>{obtenerNombreRol(usuario.idRol)}</td>
                                <td>{usuario.ultimoLogin}</td>
                                <td>{usuario.tokenRecuperacion}</td>
                                <td>{usuario.tokenRecuperacionExpiracion}</td>
                                <td>{usuario.fechaCreacionUsuario}</td>
                                <td>{usuario.fechaModificacionUsuario}</td>
                                <td>
                                    <Link to={`/edit-usuario/${usuario.idUsuario}`}>Actualizar</Link>
                                    <button onClick={() => {eliminarUsuario(usuario.idUsuario)}}>Eliminar</button>
                                </td>
                            </tr>
                    )
                }
                </tbody>
            </table>
        </div>
        </div>
        
    )
}

export default ListUsuarioComponent;