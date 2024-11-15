import { useEffect, useState } from "react";
import { useNavigate, useParams, Link } from "react-router-dom";
import InscripcionConRolAdminService from "../../../../services/administradorServices/Inscripcion/InscripcionConRolAdminService";

function AddConRolAdministradorComponent() {

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
            rol: {
                nombreRol,
                description
            },
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
                navigate("/list-inscripcionConRol-administrador");
            } catch (error) {
                console.error(error);
            }
        } else {
            try {
                const response = await InscripcionConRolAdminService.postInscripcion(formData);
                console.log(response.data);
                navigate("/list-inscripcionConRol-administrador");
            } catch (error) {
                console.error(error);
            }
        }
    }

    useEffect(() => {
        if (id) {
            InscripcionConRolAdminService.getInscripcionById(id).then(response => {
                setNombreRol(response.data.rol.nombreRol);
                setDescription(response.data.rol.description);

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
    }, [])

    function title() {
        if (id) {
            return (
                <div>Actualizar Inscripcion Con Rol</div>
            )
        } else {
            return (
                <div>Agregar Inscripcion Con Rol</div>
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
        <div className="container">
            <h1>{title()}</h1>
            <form>
                {/*Rol*/}
                <div>
                    <label>Nombre del Rol</label>
                    <input required type="text" placeholder="Inserte el rol" name="nombreRol" value={nombreRol} onChange={(e) => setNombreRol(e.target.value)} />
                </div>

                <div>
                    <label>Descripcion</label>
                    <input required type="text" placeholder="Inserte la descripcion" name="description" value={description} onChange={(e) => setDescription(e.target.value)} />
                </div>


                {/*Usuario*/}
                <div>
                    <label>Nombre de Usuario</label>
                    <input required type="text" placeholder="Inserte el nombre de ususario" name="username" value={username} onChange={(e) => setUsername(e.target.value)} />
                </div>

                {/*<div>
                    <label>Contraseña</label>
                    <input type="text" placeholder="Inserte la contraseña" name="password" value={password} onChange={(e) => setPassword(e.target.value)}/>
                </div>*/}
                {PasswordInput()}

                <div>
                    <label>Email del Usuario</label>
                    <input
                        type="email"
                        placeholder="Ingrese el email del Usuario"
                        name="emailUsuario"
                        value={emailUsuario}
                        onChange={(e) => setEmailUsuario(e.target.value)}
                        required
                    />
                    {/* Mensaje de error opcional */}
                    <span className="error-message" style={{ display: emailUsuario && !/^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\.[a-zA-Z]{2,}$/.test(emailUsuario) ? 'block' : 'none' }}>
                        Por favor, ingrese un correo electrónico válido.
                    </span>
                </div>

                <div>
                    <label>Disponible</label>
                    <select required value={enabled} onChange={(e) => setEnabled(e.target.value === 'true')}>
                        <option value={true}>Disponible</option>
                        <option value={false}>No disponible</option>
                    </select>
                </div>

                <div>
                    <label>Ultimo Login</label>
                    <input required type="text" placeholder="Inserte el ultimo login" name="ultimoLogin" value={ultimoLogin} onChange={(e) => setUltimoLogin(e.target.value)} />
                </div>

                <div>
                    <label htmlFor="">Token de Recuperacion</label>
                    <input required type="text" placeholder="Inserte el token de recuperacion" name="tokenRecuperacion" value={tokenRecuperacion} onChange={(e) => setTokenRecuperacion(e.target.value)} />
                </div>

                <div>
                    <label htmlFor="">Expiracion del Token de Recuperacion</label>
                    <input required type="text" placeholder="Inserte la expiracion del token de recuperacion" name="tokenRecuperacionExpiracion" value={tokenRecuperacionExpiracion} onChange={(e) => setTokenRecuperacionExpiracion(e.target.value)} />
                </div>


                {/*Persona*/}
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
                        type="email"
                        placeholder="Ingrese su email"
                        name="email"
                        value={email}
                        onChange={(e) => setEmail(e.target.value)}
                        required
                    />
                    {/* Mensaje de error opcional */}
                    <span className="error-message" style={{ display: email && !/^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\.[a-zA-Z]{2,}$/.test(email) ? 'block' : 'none' }}>
                        Por favor, ingrese un correo electrónico válido.
                    </span>
                </div>

                <div>
                    <label>Estado Civil</label>
                    <select
                        name="estadoCivil"
                        value={estadoCivil}
                        onChange={(e) => setEstadoCivil(e.target.value)}
                    >
                        <option value="">Seleccione su estado civil</option>
                        <option value="soltero">Soltero/a</option>
                        <option value="casado">Casado/a</option>
                        <option value="divorciado">Divorciado/a</option>
                        <option value="viudo">Viudo/a</option>
                        <option value="separado">Separado/a</option>
                        <option value="union libre">Unión libre</option>
                        <option value="concubinato">Concubinato</option>
                    </select>
                </div>

                <div>
                    <label htmlFor="fotoPerfil">Foto de Perfil</label>
                    <input
                        type="file"
                        name="fotoPerfil"
                        id="fotoPerfil"
                        accept="image/*"
                        onChange={(e) => setFotoPerfil(e.target.files[0])}  // Obtener el archivo
                    />
                </div>

                <div>
                    <label>Tipo de Sangre</label>
                    <select
                        name="tipoSangre"
                        value={tipoSangre}
                        onChange={(e) => setTipoSangre(e.target.value)}
                        required
                    >
                        <option value="">Seleccione su tipo de sangre</option>
                        <option value="A+">A+</option>
                        <option value="A-">A-</option>
                        <option value="B+">B+</option>
                        <option value="B-">B-</option>
                        <option value="AB+">AB+</option>
                        <option value="AB-">AB-</option>
                        <option value="O+">O+</option>
                        <option value="O-">O-</option>
                    </select>
                </div>

                <div>
                    <label>Responsable Financiero</label>
                    <input type="text" placeholder="Ingrese el Nombre del Responsable Financiero" name="responsableFinanciero" value={responsableFinanciero} onChange={(e) => setResponsableFinanciero(e.target.value)} />
                </div>

                <div>
                    <label>Nombre del Contacto de Emergencia</label>
                    <input type="text" placeholder="Ingrese el nombre del contacto de emergencia" name="contactoEmergenciaNombre" value={contactoEmergenciaNombre} onChange={(e) => setContactoEmergenciaNombre(e.target.value)} />
                </div>

                <div>
                    <label>Telefono del Contacto de Emergencia</label>
                    <input
                        type="text" // Se mantiene como tipo texto
                        placeholder="Ingrese el telefono del contacto de emergencia"
                        name="contactoEmergenciaTelefono"
                        value={contactoEmergenciaTelefono}
                        onChange={(e) => {
                            const value = e.target.value;
                            // Permitir solo números y limitar a 9 dígitos
                            if (/^\d*$/.test(value) && value.length <= 9) {
                                setContactoEmergenciaTelefono(value);
                            }
                        }} />
                </div>

                <div>
                    <label>Email del Contacto de Emergencia</label>
                    <input
                        type="email"
                        placeholder="Ingrese el email del contacto de emergencia"
                        name="contactoEmergenciaEmail"
                        value={contactoEmergenciaEmail}
                        onChange={(e) => setContactoEmergenciaEmail(e.target.value)}
                        required
                    />
                    {/* Mensaje de error opcional para el contacto de emergencia */}
                    <span className="error-message" style={{ display: contactoEmergenciaEmail && !/^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\.[a-zA-Z]{2,}$/.test(contactoEmergenciaEmail) ? 'block' : 'none' }}>
                        Por favor, ingrese un correo electrónico válido.
                    </span>
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


                {/*Administrador*/}
                <div>
                    <label>Actividad Reciente</label>
                    <input type="text" placeholder="Ingrese la actividad reciente" name="actividadReciente" value={actividadReciente} onChange={(e) => setActividadReciente(e.target.value)} />
                </div>

                <div>
                    <label>Fecha Actividad</label>
                    <input type="date" placeholder="Ingrese la Fecha de Actividad" name="fechaActividad" value={fechaActividad} onChange={(e) => setFechaActividad(e.target.value)} />
                </div>

                <div>
                    <label>Estado Sistema</label>
                    <input type="text" placeholder="Ingrese el estado del Sistema" name="estadoSistema" value={estadoSistema} onChange={(e) => setEstadoSistema(e.target.value)} />
                </div>

                <div>
                    <label>Fecha Ultima Revision</label>
                    <input type="date" placeholder="Ingrese la Fecha de Ultima Revision" name="fechaUltimaRevision" value={fechaUltimaRevision} onChange={(e) => setFechaUltimaRevision(e.target.value)} />
                </div>

                <div>
                    <label>Permisos Especiales</label>
                    <input type="text" placeholder="Ingrese los Permisos Especiales" name="permisosEspeciales" value={permisosEspeciales} onChange={(e) => setPermisosEspeciales(e.target.value)} />
                </div>

                <div>
                    <label>Logs de Accesos</label>
                    <input type="text" placeholder="Ingrese los Logs de Accesos" name="logsAcceso" value={logsAcceso} onChange={(e) => setLogsAcceso(e.target.value)} />
                </div>

                <div>
                    <label>Cambios de Configuracion</label>
                    <input type="text" placeholder="Ingrese los cambios de Configuracion" name="cambiosConfiguracion" value={cambiosConfiguracion} onChange={(e) => setCambiosConfiguracion(e.target.value)} />
                </div>

                <div>
                    <button onClick={(e) => saveOrUpdateInscripcion(e)}>{botonAgregarOActualizar()}</button>
                    &nbsp;
                    &nbsp;
                    <Link to="/list-inscripcionConRol-administrador">Cancelar</Link>
                </div>
            </form>
        </div>
    )
}

export default AddConRolAdministradorComponent;