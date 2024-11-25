import { useEffect, useState } from "react";
import { useNavigate, useParams, Link } from "react-router-dom";
import InscripcionConRolAdminService from "../../../services/administradorServices/Inscripcion/InscripcionAdminService";
import RolAdminService from "../../../services/administradorServices/rol/RolAdminService";
import "../../../style-sheets/administrador/inscripcion/GestionarInscripcion/AddInscripcionAdministradorComponent.css"

function AddInscripcionAdministradorComponent() {

    const [nombreRol, setNombreRol] = useState("");
    const [description, setDescription] = useState("");

    const [username, setUsername] = useState("");
    const [password, setPassword] = useState("");
    const [emailUsuario, setEmailUsuario] = useState("");
    const [enabled, setEnabled] = useState("");
    const [ultimoLogin, setUltimoLogin] = useState("");
    const [tokenRecuperacion, setTokenRecuperacion] = useState("");
    const [tokenRecuperacionExpiracion, setTokenRecuperacionExpiracion] = useState("");

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
    const [fotoPerfil, setFotoPerfil] = useState(null);
    const [tipoSangre, setTipoSangre] = useState("");
    const [responsableFinanciero, setResponsableFinanciero] = useState("");
    const [contactoEmergenciaNombre, setContactoEmergenciaNombre] = useState("");
    const [contactoEmergenciaTelefono, setContactoEmergenciaTelefono] = useState("");
    const [contactoEmergenciaEmail, setContactoEmergenciaEmail] = useState("");
    const [contactoEmergenciaDireccion, setContactoEmergenciaDireccion] = useState("");
    const [contactoEmergenciaCiudad, setContactoEmergenciaCiudad] = useState("");
    const [contactoEmergenciaParentesco, setContactoEmergenciaParentesco] = useState("");

    const [actividadReciente, setActividadReciente] = useState("");
    const [fechaActividad, setFechaActividad] = useState("");
    const [estadoSistema, setEstadoSistema] = useState("");
    const [fechaUltimaRevision, setFechaUltimaRevision] = useState("");
    const [permisosEspeciales, setPermisosEspeciales] = useState("");
    const [logsAcceso, setLogsAcceso] = useState("");
    const [cambiosConfiguracion, setCambiosConfiguracion] = useState("");

    const navigate = useNavigate();
    const { id } = useParams();

    async function saveOrUpdateInscripcion(e) {
        e.preventDefault();
        const inscripcion = {
            idRol: 1,
            usuario: {
                username,
                password,
                email: emailUsuario,
                enabled,
                ultimoLogin,
                tokenRecuperacion,
                tokenRecuperacionExpiracion
            },
            persona: {
                nombres,
                apellido_paterno,
                apellido_materno,
                fecha_nacimiento,
                genero,
                nacionalidad,
                tipoDocumento,
                numeroDocumento,
                direccion,
                ciudad,
                departamento,
                pais,
                provincia,
                telefono,
                email,
                estadoCivil,
                tipoSangre,
                responsableFinanciero,
                contactoEmergenciaNombre,
                contactoEmergenciaTelefono,
                contactoEmergenciaEmail,
                contactoEmergenciaDireccion,
                contactoEmergenciaCiudad,
                contactoEmergenciaParentesco
            },
            administrador: {
                actividadReciente,
                fechaActividad,
                estadoSistema,
                fechaUltimaRevision,
                permisosEspeciales,
                logsAcceso,
                cambiosConfiguracion
            }
        };

        const formData = new FormData();
        formData.append("inscripcion", JSON.stringify(inscripcion));
        // Si se ha seleccionado una foto de perfil, agregarla al FormData
        if (fotoPerfil) {
            // Asignar solo el nombre del archivo al objeto inscripcion
            //inscripcion.persona.fotoPerfil = fotoPerfil.name;
            formData.append("file", fotoPerfil);  // El nombre "file" debe coincidir con el @RequestParam del backend
        }

        // Mostrar el contenido de FormData en la consola
        console.log("Datos enviados al Backend:");
        formData.forEach((value, key) => {
            console.log(key, value);
        });

        const inscripcionData = {};
        formData.forEach((value, key) => {
            inscripcionData[key] = value;
        });
        console.log("Estos son los datos enviados al Backend:", JSON.stringify(inscripcionData, null, 2));

        // Inspeccionar el contenido del FormData
        for (let [key, value] of formData.entries()) {
            console.log(`Clave: ${key}, Valor:`, value);
            if (value instanceof File) {
                console.log("El valor es un archivo:", value.name);
            } else {
                console.log("El valor es texto:", value);
            }
        }

        if (id) {
            try {
                const response = await InscripcionConRolAdminService.putInscripcion(id, formData);
                console.log(response.data);
                navigate("/list-inscripcion-administrador");
            } catch (error) {
                console.error(error);
            }
        } else {
            try {
                const response = await InscripcionConRolAdminService.postInscripcion(formData);
                console.log(response.data);
                navigate("/list-inscripcion-administrador");
            } catch (error) {
                console.error(error);
            }
        }
    }

    const datosAdministrador = () => {
        RolAdminService.getRolById(1).then((response) => {
            setNombreRol(response.data.nombreRol);
            setDescription(response.data.description);
            console.log("El nombre del Rol es: " + response.data.nombreRol);
            console.log("La descripcion del Rol el: " + response.data.description);
        }).catch((error) => {
            console.log(error);
        })
    }

    useEffect(() => {
        datosAdministrador();
        if (id) {
            InscripcionConRolAdminService.getInscripcionById(id).then(response => {
                setUsername(response.data.usuario.username);
                setPassword(response.data.usuario.password);
                setEmailUsuario(response.data.usuario.email);
                setEnabled(response.data.usuario.enabled);
                setUltimoLogin(response.data.usuario.ultimoLogin);
                setTokenRecuperacion(response.data.usuario.tokenRecuperacion);
                setTokenRecuperacionExpiracion(response.data.usuario.tokenRecuperacionExpiracion);

                setNombres(response.data.persona.nombres);
                setApellido_paterno(response.data.persona.apellido_paterno);
                setApellido_materno(response.data.persona.apellido_materno);
                setFecha_nacimiento(response.data.persona.fecha_nacimiento);
                setGenero(response.data.persona.genero);
                setNacionalidad(response.data.persona.nacionalidad);
                setTipoDocumento(response.data.persona.tipoDocumento);
                setNumeroDocumento(response.data.persona.numeroDocumento);
                setDireccion(response.data.persona.direccion);
                setCiudad(response.data.persona.ciudad);
                setDepartamento(response.data.persona.departamento);
                setPais(response.data.persona.pais);
                setProvincia(response.data.persona.provincia);
                setTelefono(response.data.persona.telefono);
                setEmail(response.data.persona.email);
                setEstadoCivil(response.data.persona.estadoCivil);
                setFotoPerfil(response.data.persona.fotoPerfil);
                setTipoSangre(response.data.persona.tipoSangre);
                setResponsableFinanciero(response.data.persona.responsableFinanciero);
                setContactoEmergenciaNombre(response.data.persona.contactoEmergenciaNombre);
                setContactoEmergenciaTelefono(response.data.persona.contactoEmergenciaTelefono);
                setContactoEmergenciaEmail(response.data.persona.contactoEmergenciaEmail);
                setContactoEmergenciaDireccion(response.data.persona.contactoEmergenciaDireccion);
                setContactoEmergenciaCiudad(response.data.persona.contactoEmergenciaCiudad);
                setContactoEmergenciaParentesco(response.data.persona.contactoEmergenciaParentesco);

                setActividadReciente(response.data.administrador.actividadReciente);
                setEstadoSistema(response.data.administrador.estadoSistema);
                setFechaUltimaRevision(response.data.administrador.fechaUltimaRevision);
                setPermisosEspeciales(response.data.administrador.permisosEspeciales);
                setLogsAcceso(response.data.administrador.logsAcceso);
                setCambiosConfiguracion(response.data.administrador.cambiosConfiguracion);
            }
            )
        }
    }, [id])

    function title() {
        if (id) {
            return (
                <div>Actualizar Inscripcion Con Rol de Administrador</div>
            )
        } else {
            return (
                <div>Agregar Inscripcion Con Rol de Administrador</div>
            )
        }
    }

    function botonAgregarOActualizar() {
        if (id) {
            return (
                <div>Actualizar</div>
            )
        } else {
            return (
                <div>Agregar</div>
            )
        }
    }

    function PasswordInput() {
        const [showPassword, setShowPassword] = useState(false);

        return (
            <div>
                <label>Contraseña</label>
                <input
                    type={showPassword ? "text" : "password"}
                    placeholder="Inserte la contraseña"
                    name="password"
                    value={password}
                    onChange={(e) => setPassword(e.target.value)}
                    autoComplete="off"
                    minLength={8} // longitud mínima recomendada
                    required
                />
                <button
                    type="button"
                    onClick={() => setShowPassword(!showPassword)}
                >
                    {showPassword ? "Ocultar" : "Mostrar"}
                </button>
            </div>
        );
    }

    return (
        <div className="form-container">
            <h1 className="form-title">{title()}</h1>
            <form className="form-content">

                <h4 className="form-section-title">Información del Rol</h4>
                <p className="form-field">
                    <strong>Nombre del Rol:</strong> {nombreRol}
                </p>
                <p className="form-field">
                    <strong>Descripcion:</strong> {description}
                </p>

                {/* Usuario */}
                <div className="form-group">
                    <label className="form-label">Nombre de Usuario</label>
                    <input
                        className="form-input"
                        required
                        type="text"
                        placeholder="Inserte el nombre de usuario"
                        name="username"
                        value={username}
                        onChange={(e) => setUsername(e.target.value)}
                    />
                </div>

                {PasswordInput()}

                <div className="form-group">
                    <label className="form-label">Email del Usuario</label>
                    <input
                        className="form-input"
                        type="email"
                        placeholder="Ingrese el email del Usuario"
                        name="emailUsuario"
                        value={emailUsuario}
                        onChange={(e) => setEmailUsuario(e.target.value)}
                        required
                    />
                    <span
                        className="error-message"
                        style={{
                            display: emailUsuario && !/^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\.[a-zA-Z]{2,}$/.test(emailUsuario)
                                ? 'block'
                                : 'none',
                        }}
                    >
                        Por favor, ingrese un correo electrónico válido.
                    </span>
                </div>

                <div className="form-group">
                    <label className="form-label">Disponible</label>
                    <select
                        className="form-select"
                        required
                        value={enabled}
                        onChange={(e) => setEnabled(e.target.value === 'true')}
                    >
                        <option value={null}>Seleccione la Disponibilidad</option>
                        <option value={true}>Disponible</option>
                        <option value={false}>No disponible</option>
                    </select>
                </div>

                {/* Persona */}
                <div className="form-group">
                    <label className="form-label">Nombres</label>
                    <input
                        className="form-input"
                        type="text"
                        placeholder="Ingrese sus nombres"
                        name="nombres"
                        value={nombres}
                        onChange={(e) => setNombres(e.target.value)}
                    />
                </div>

                <div className="form-group">
                    <label className="form-label">Apellido Paterno</label>
                    <input
                        className="form-input"
                        type="text"
                        placeholder="Ingrese su apellido paterno"
                        name="apellido_paterno"
                        value={apellido_paterno}
                        onChange={(e) => setApellido_paterno(e.target.value)}
                    />
                </div>

                <div className="form-group">
                    <label className="form-label">Apellido Materno</label>
                    <input
                        className="form-input"
                        type="text"
                        placeholder="Ingrese su apellido materno"
                        name="apellido_materno"
                        value={apellido_materno}
                        onChange={(e) => setApellido_materno(e.target.value)}
                    />
                </div>

                {/* Añade los demás campos aquí con las mismas clases */}

                <div className="form-group">
                    <button className="form-submit-button" type="submit">Enviar</button>
                </div>
            </form>
        </div>
    );

}

export default AddInscripcionAdministradorComponent;