import { useEffect, useState } from "react"
import { Link } from "react-router-dom"
import PersonaAdminService from "../../../services/administradorServices/persona/PersonaAdminService";
function ListPersonaComponent(){
    const [personas, setPersonas] = useState([]);

    useEffect(() => {
        listarPersonas();
    },[])

    function listarPersonas(){
        PersonaAdminService.getAllPersonas().then(response => {
            setPersonas(response.data);
            console.log(response.data);
        }).catch(error => {
            console.log(error);
        })
    }


    return(
        <div className="container">
            <Link to="dashboard-administrador"></Link>
            <h1>Gestion de Personas</h1>
            <table>
                <thead>
                    <th>ID</th>
                    <th>Nombres</th>
                    <th>Apellido Paterno</th>
                    <th>Apellido Materno</th>
                    <th>Fecha de Nacimiento</th>
                    <th>Genero</th>
                    <th>Nacionalidad</th>
                    <th>Tipo de documento</th>
                    <th>Numero de documento</th>
                    <th>Direccion</th>
                    <th>Ciudad</th>
                    <th>Departamento</th>
                    <th>País</th>
                    <th>Provincia</th>
                    <th>Telefono</th>
                    <th>Email</th>
                    <th>Estado Civil</th>
                    <th>Foto de Perfil</th>
                    <th>Tipo de sangre</th>
                    <th>Nombre del contacto de emergencia</th>
                    <th>Telefono del contacto de emergencia</th>
                    <th>Email del contacto de emergencia</th>
                    <th>Direcion del contacto de emergencia</th>
                    <th>Ciudad del contacto de emergencia</th>
                    <th>Parentesco del contacto de emergencia</th>
                    <th>Usuario</th>
                </thead>
                <tbody>
                    {
                        personas.map(
                            persona =>
                                <tr key={ persona.idPersona}>
                                    <td>{}</td>
                                    <td>{}</td>
                                    <td>{}</td>
                                    <td>{}</td>
                                    <td>{}</td>
                                    <td>{}</td>
                                    <td>{}</td>
                                    <td>{}</td>
                                    <td>{}</td>
                                    <td>{}</td>
                                    <td>{}</td>
                                    <td>{}</td>
                                    <td>{}</td>
                                    <td>{}</td>
                                    <td>{}</td>
                                    <td>{}</td>
                                    <td>{}</td>
                                    <td>{}</td>
                                    <td>{}</td>
                                    <td>{}</td>
                                    <td>{}</td>
                                    <td>{}</td>
                                    <td>{}</td>
                                    <td>{}</td>
                                    <td>{}</td>
                                    <td>{}</td>
                                </tr>
                        )
                    }
                </tbody>
            </table>
        </div>
    )
}

export default ListPersonaComponent;