import { useEffect, useState } from "react"
import { Link } from "react-router-dom"
import PersonaAdminService from "../../../services/administradorServices/persona/PersonaAdminService";

import { format } from 'date-fns';
import "../../../style-sheets/administrador/persona/ListPersonaComponent.css"
import UsuarioAdminService from "../../../services/administradorServices/usuario/UsuarioAdminService";
function ListPersonaComponent() {
    const [personas, setPersonas] = useState([]);

    const [usuarios, setUsuarios] = useState([]);

    useEffect(() => {
        listarPersonas();
        listarUsuarios();
    }, [])

    function listarUsuarios() {
        UsuarioAdminService.getAllUsuarios().then(response => {
            setUsuarios(response.data);
            console.log(response.data);
        }).catch(error => {
            console.log(error);
        })
    }

    function obtenerUsernameUsuario(idUsuario) {
        const usuarioEncontrado = usuarios.find(usuario => usuario.idUsuario === idUsuario);
        return usuarioEncontrado ? usuarioEncontrado.username : "Desconocido";
    }

    async function listarPersonas() {
        const response = await PersonaAdminService.getAllPersonas();
        const personasConImagenes = await Promise.all(
            response.data.map(async (persona) => {
                console.log("Foto de Perfil:", persona.fotoPerfil); // Verificar el valor de fotoPerfil
                const imagenUrl = await PersonaAdminService.getPersonaImagen(persona.fotoPerfil);
                return { ...persona, imagenUrl };
            })
        );
        setPersonas(personasConImagenes);
    }

    function deletePersona(id) {
        PersonaAdminService.deletePersona(id).then(response => {
            listarPersonas();
        }).catch(error => {
            console.log(error);
        })
    }


    return (
        <div className="container">
            <Link to="/dashboard-administrador">Retroceder</Link>
            <h1>Gestion de Personas</h1>
            <Link to="/add-persona">Agregar Persona</Link>
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
                    <th>Responsable Financiero</th>
                    <th>Nombre del contacto de emergencia</th>
                    <th>Telefono del contacto de emergencia</th>
                    <th>Email del contacto de emergencia</th>
                    <th>Direcion del contacto de emergencia</th>
                    <th>Ciudad del contacto de emergencia</th>
                    <th>Parentesco del contacto de emergencia</th>
                    <th>Usuario</th>
                    <th>Acciones</th>
                </thead>
                <tbody>
                    {
                        personas.map(
                            persona =>
                                <tr key={persona.id}>
                                    <td>{persona.id}</td>
                                    <td>{persona.nombres}</td>
                                    <td>{persona.apellido_paterno}</td>
                                    <td>{persona.apellido_materno}</td>
                                    <td>{format(new Date(persona.fecha_nacimiento), 'dd-MM-yyyy')}</td>
                                    <td>{persona.genero}</td>
                                    <td>{persona.nacionalidad}</td>
                                    <td>{persona.tipoDocumento}</td>
                                    <td>{persona.numeroDocumento}</td>
                                    <td>{persona.direccion}</td>
                                    <td>{persona.ciudad}</td>
                                    <td>{persona.departamento}</td>
                                    <td>{persona.pais}</td>
                                    <td>{persona.provincia}</td>
                                    <td>{persona.telefono}</td>
                                    <td>{persona.email}</td>
                                    <td>{persona.estadoCivil}</td>
                                    <td>
                                        {persona.imagenUrl ? (
                                            <img src={persona.imagenUrl} alt="Imagen de Persona" style={{ width: '50px', height: '50px' }} />
                                        ) : (
                                            <p>No disponible</p>
                                        )}
                                    </td>
                                    <td>{persona.tipoSangre}</td>
                                    <td>{persona.responsableFinanciero}</td>
                                    <td>{persona.contactoEmergenciaNombre}</td>
                                    <td>{persona.contactoEmergenciaTelefono}</td>
                                    <td>{persona.contactoEmergenciaEmail}</td>
                                    <td>{persona.contactoEmergenciaDireccion}</td>
                                    <td>{persona.contactoEmergenciaCiudad}</td>
                                    <td>{persona.contactoEmergenciaParentesco}</td>
                                    <td>{obtenerUsernameUsuario(persona.idUsuario)}</td>
                                    <td>
                                        <Link to={`/edit-persona/${persona.id}`}>Actualizar</Link>
                                        <button onClick={() => deletePersona(persona.id)}>Eliminar</button>
                                    </td>
                                </tr>
                        )
                    }
                </tbody>
            </table>
        </div>
    )
}

export default ListPersonaComponent;