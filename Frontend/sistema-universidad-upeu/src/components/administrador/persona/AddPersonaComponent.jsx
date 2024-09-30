import { useEffect, useState } from "react";
import { Link, useNavigate, useParams } from "react-router-dom";
import PersonaAdminService from "../../../services/administradorServices/persona/PersonaAdminService";
import UsuarioAdminService from "../../../services/administradorServices/usuario/UsuarioAdminService";

function AddPersonaComponent() {
    const [nombres, setNombres] = useState("");
    const [apellido_paterno, setApellido_paterno] = useState("");
    const [apellido_materno, setApellido_materno] = useState("");
    const [fecha_nacimiento, setFecha_nacimiento] = useState("");
    const [genero, setGenero] = useState("");
    const [nacionalidad, setNacionalidad] = useState("");
    const [tipoDocumento, setTipoDocumento] = useState("");
    const [numeroDocumento, setNumeroDocumento] = useState("");
    const [direccion, setDireccion] = useState("");
    const [ciudad, setCiudad] = useState("");
    const [departamento, setDepartamento] = useState("");
    const [pais, setPais] = useState("");
    const [provincia, setProvincia] = useState("");
    const [telefono, setTelefono] = useState("");
    const [email, setEmail] = useState("");
    const [estadoCivil, setEstadoCivil] = useState("");
    const [fotoPerfil, setFotoPerfil] = useState("");
    const [tipoSangre, setTipoSangre] = useState("");
    const [contactoEmergenciaNombre, setContactoEmergenciaNombre] = useState("");
    const [contactoEmergenciaTelefono, setContactoEmergenciaTelefono] = useState("");
    const [contactoEmergenciaEmail, setContactoEmergenciaEmail] = useState("");
    const [contactoEmergenciaDireccion, setContactoEmergenciaDireccion] = useState("");
    const [contactoEmergenciaCiudad, setContactoEmergenciaCiudad] = useState("");
    const [contactoEmergenciaParentesco, setContactoEmergenciaParentesco] = useState("");
    const [idUsuario, setIdUsuario] = useState("");

    const [usuarios, setUsuarios] = useState([]);

    const navigate = useNavigate();
    const { id } = useParams();

    function saveOrUpdatePersona(e) {
        e.preventDefault();
        const persona = { nombres, apellido_paterno, apellido_materno, fecha_nacimiento, genero, nacionalidad, tipoDocumento, numeroDocumento, direccion, ciudad, departamento, pais, provincia, telefono, email, estadoCivil, fotoPerfil, tipoSangre, contactoEmergenciaNombre, contactoEmergenciaTelefono, contactoEmergenciaEmail, contactoEmergenciaDireccion, contactoEmergenciaCiudad, contactoEmergenciaParentesco, idUsuario };
        console.log(persona);

        if (id) {
            PersonaAdminService.updatePersona(id, persona).then(response => {
                console.log(response.data);
                navigate("/personas");
            }).catch(error => {
                console.log(error);
            })
        } else {
            PersonaAdminService.createPersona(persona).then(response => {
                console.log(response.data);
                navigate("/personas");
            }).catch(error => {
                console.log(error);
            })
        }
    }

    useEffect(() => {
        UsuarioAdminService.getAllUsuarios().then(response => {
            setUsuarios(response.data);
        }).catch(error => {
            console.log(error);
        });

        if (id) {
            PersonaAdminService.getPersonaById(id).then(response => {
                setNombres(response.data.nombres);
                setApellido_paterno(response.data.apellido_paterno);
                setApellido_materno(response.data.apellido_materno);
                setFecha_nacimiento(response.data.fecha_nacimiento);
                setGenero(response.data.genero);
                setNacionalidad(response.data.nacionalidad);
                setTipoDocumento(response.data.tipoDocumento);
                setNumeroDocumento(response.data.numeroDocumento);
                setDireccion(response.data.direccion);
                setCiudad(response.data.ciudad);
                setDepartamento(response.data.departamento);
                setPais(response.data.pais);
                setProvincia(response.data.provincia);
                setTelefono(response.data.telefono);
                setEmail(response.data.email);
                setEstadoCivil(response.data.estadoCivil);
                setFotoPerfil(response.data.fotoPerfil);
                setTipoSangre(response.data.tipoSangre);
                setContactoEmergenciaNombre(response.data.contactoEmergenciaNombre);
                setContactoEmergenciaTelefono(response.data.contactoEmergenciaTelefono);
                setContactoEmergenciaEmail(response.data.contactoEmergenciaEmail);
                setContactoEmergenciaDireccion(response.data.contactoEmergenciaDireccion);
                setContactoEmergenciaCiudad(response.data.contactoEmergenciaCiudad);
                setContactoEmergenciaParentesco(response.data.contactoEmergenciaParentesco);
                setIdUsuario(response.data.idUsuario);
            }).catch(error => {
                console.log(error);
            });
        }
    }, [id])

    function title() {
        if (id) {
            return <h1>Actualizar Persona</h1>
        } else {
            return <h1>Agregar Persona</h1>
        }
    }

    function botonAgregarOActualizar() {
        if (id) {
            return <div>Actualizar</div>
        } else {
            return <div>Agregar</div>
        }
    }

    return (
        <div className="container">
            <h1>{title()}</h1>
            <form>
                <div>
                    <label>Nombres</label>
                    <input type="text" placeholder="Ingrese sus nombres" name="nombres" value={nombres} onChange={(e) => setNombres(e.target.value)} />
                </div>

                <div>
                    <label>Apellido Paterno</label>
                    <input type="text" placeholder="Ingrese su apellido paterno" name="apellido_paterno" value={apellido_paterno} onChange={(e) => setApellido_paterno(e.target.value)} />
                </div>

                <div>
                    <label>Apellido Materno</label>
                    <input type="text" placeholder="Ingrese su apellido materno" name="apellido_paterno" value={apellido_materno} onChange={(e) => setApellido_materno(e.target.value)} />
                </div>

                <div>
                    <label>Fecha de Nacimiento</label>
                    <input
                        type="date"
                        placeholder="Ingrese su fecha de nacimiento"
                        name="fecha_nacimiento"
                        value={fecha_nacimiento}
                        onChange={(e) => setFecha_nacimiento(e.target.value)}
                    />
                </div>

                <div>
                    <label>Género</label>
                    <select name="genero" value={genero} onChange={(e) => setGenero(e.target.value)}>
                        <option value="">Seleccione su género</option>
                        <option value="Masculino">Masculino</option>
                        <option value="Femenino">Femenino</option>
                        <option value="Prefiero no decirlo">Prefiero no decirlo</option>
                    </select>
                </div>

                <div>
                    <label>Nacionalidad</label>
                    <input type="text" placeholder="Ingrese su nacionalidad" name="nacionalidad" value={nacionalidad} onChange={(e) => setNacionalidad(e.target.value)} />
                </div>

                <div>
                    <label>Tipo de Documento</label>
                    <input type="text" placeholder="Ingrese su tipo de documento" name="tipoDocumento" value={tipoDocumento} onChange={(e) => setTipoDocumento(e.target.value)} />
                </div>

                <div>
                    <label>Número de Documento</label>
                    <input
                        type="number"
                        placeholder="Ingrese su número de documento"
                        name="numeroDocumento"
                        value={numeroDocumento}
                        onChange={(e) => setNumeroDocumento(e.target.value)}
                        min="0" // Asegura que no se puedan ingresar números negativos
                    />
                </div>

                <div>
                    <label>Direccion</label>
                    <input type="text" placeholder="Ingrese su direccion" name="direccion" value={direccion} onChange={(e) => setDireccion(e.target.value)} />
                </div>

                <div>
                    <label>Ciudad</label>
                    <input type="text" placeholder="Ingrese su ciudad" name="ciudad" value={ciudad} onChange={(e) => setCiudad(e.target.value)} />
                </div>

                <div>
                    <label>Departamento</label>
                    <input type="text" placeholder="Ingrese su departamento" name="departamento" value={departamento} onChange={(e) => setDepartamento(e.target.value)} />
                </div>

                <div>
                    <label>País</label>
                    <input type="text" placeholder="Ingrese su pais" name="pais" value={pais} onChange={(e) => setPais(e.target.value)} />
                </div>

                <div>
                    <label>Provincia</label>
                    <input type="text" placeholder="Ingrese su provincia" name="provincia" value={provincia} onChange={(e) => setProvincia(e.target.value)} />
                </div>

                <div>
                    <label>Teléfono</label>
                    <input
                        type="text" // Se mantiene como tipo texto
                        placeholder="Ingrese su teléfono"
                        name="telefono"
                        value={telefono}
                        onChange={(e) => {
                            const value = e.target.value;
                            // Permitir solo números y limitar a 9 dígitos
                            if (/^\d*$/.test(value) && value.length <= 9) {
                                setTelefono(value);
                            }
                        }}
                    />
                </div>

                <div>
                    <label>Email</label>
                    <input
                        type="email" // Cambiado a tipo "email" para validaciones automáticas
                        placeholder="Ingrese su email"
                        name="email"
                        value={email}
                        onChange={(e) => setEmail(e.target.value)}
                        required // Asegura que el campo sea obligatorio
                        pattern="^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\.[a-zA-Z]{2,}$" // Expresión regular para validar el formato de email
                    />
                    {/* Mensaje de error opcional */}
                    <span className="error-message" style={{ display: email && !/^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\.[a-zA-Z]{2,}$/.test(email) ? 'block' : 'none' }}>
                        Por favor, ingrese un correo electrónico válido.
                    </span>
                </div>

                <div>
                    <label>Estado Civil</label>
                    <input type="text" placeholder="Ingrese su estado civil" name="estadoCivil" value={estadoCivil} onChange={(e) => setEstadoCivil(e.target.value)} />
                </div>

                <div>
                    <label>Foto de Perfil</label>
                    <input type="text" placeholder="Ingrese su foto de perfil" name="fotoPerfil" value={fotoPerfil} onChange={(e) => setFotoPerfil(e.target.value)} />
                </div>

                <div>
                    <label>Tipo de Sangre</label>
                    <input type="text" placeholder="Ingrese su tipo de sangre" name="tipoSangre" value={tipoSangre} onChange={(e) => setTipoSangre(e.target.value)} />
                </div>

                <div>
                    <label>Nombre del Contacto de Emergencia</label>
                    <input type="text" placeholder="Ingrese el nombre del contacto de emergencia" name="contactoEmergenciaNombre" value={contactoEmergenciaNombre} onChange={(e) => setContactoEmergenciaNombre(e.target.value)} />
                </div>

                <div>
                    <label>Telefono del Contacto de Emergencia</label>
                    <input type="text" placeholder="Ingrese el telefono del contacto de emergencia" name="contactoEmergenciaTelefono" value={contactoEmergenciaTelefono} onChange={(e) => setContactoEmergenciaTelefono(e.target.value)} />
                </div>

                <div>
                    <label>Email del Contacto de Emergencia</label>
                    <input type="text" placeholder="Ingrese el email del contacto de emergencia" name="contactoEmergenciaEmail" value={contactoEmergenciaEmail} onChange={(e) => setContactoEmergenciaEmail(e.target.value)} />
                </div>

                <div>
                    <label>Direccion del Contacto de Emergencia</label>
                    <input type="text" placeholder="Ingrese la direccion del contacto de emergencia" name="contactoEmergenciaDireccion" value={contactoEmergenciaDireccion} onChange={(e) => setContactoEmergenciaDireccion(e.target.value)} />
                </div>

                <div>
                    <label>Ciudad del Contacto de Emergencia</label>
                    <input type="text" placeholder="Ingrese la ciudad del contacto de emergencia" name="contactoEmergenciaCiudad" value={contactoEmergenciaCiudad} onChange={(e) => setContactoEmergenciaCiudad(e.target.value)} />
                </div>

                <div>
                    <label>Parentesco del Contacto de Emergencia</label>
                    <input type="text" placeholder="Ingrese el parentesco del contacto de emergencia" name="contactoEmergenciaParentesco" value={contactoEmergenciaParentesco} onChange={(e) => setContactoEmergenciaParentesco(e.target.value)} />
                </div>

                <div>
                    <label>Usuario</label>
                    <select value={idUsuario} onChange={(e) => setIdUsuario(e.target.value)}>
                        <option value="">Seleccione un usuario</option>
                        {usuarios.map((usuario) => (
                            <option key={usuario.idUsuario} value={usuario.idUsuario}>
                                {usuario.username}
                            </option>
                        ))}
                    </select>
                </div>

                <button onClick={(e) => saveOrUpdatePersona(e)}>{botonAgregarOActualizar()}</button>
                &nbsp;
                &nbsp;
                <Link to="/personas">Cancelar</Link>
            </form>
        </div>
    )
}

export default AddPersonaComponent;