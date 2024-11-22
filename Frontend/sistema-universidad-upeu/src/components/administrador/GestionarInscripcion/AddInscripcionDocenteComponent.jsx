import { useEffect, useState } from "react";
import { useNavigate, useParams, Link } from "react-router-dom";
import InscripcionConRolAdminService from "../../../services/administradorServices/Inscripcion/InscripcionAdminService";
import CursoAdminService from "../../../services/administradorServices/curso/CursoAdminService";

function AddInscripcionDocenteComponent() {

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

    const [departamentoDocente, setDepartamentoDocente] = useState("");
    const [tituloAcatemico, setTituloAcademico] = useState("");
    const [especialidad, setEspecialidad] = useState("");

    const [cursosImpartidos, setCursosImpartidos] = useState([]);
    const [nuevoCursoImpartido, setNuevoCursoImpartido] = useState("");

    const [historialLaboral, setHistorialLaboral] = useState([]);

    const [puesto, setPuesto] = useState("");
    const [departamentoHistorialLaboral, setDepartamentoHistorialLaboral] = useState("");
    const [fechaInicio, setFechaInicio] = useState("");
    const [fechaFin, setFechaFin] = useState("");
    const [descripcion, setDescripcion] = useState("");

    const [estadoLaboral, setEstadoLaboral] = useState("");
    const [tipoDocente, setTipoDocente] = useState("");
    const [fechaContratacion, setFechaContratacion] = useState("");
    const [tipoContrato, setTipoContrato] = useState("");
    const [salario, setSalario] = useState("");
    const [horario, setHorario] = useState("");

    const [publicacionesAcademicas, setPublicacionesAcademicas] = useState([]);
    const [nuevaPublicacionAcademica, setNuevaPublicacionAcademica] = useState("");

    const [proyectosInvestigacion, setProyectosInvestigacion] = useState([]);
    const [nuevoProyectoInvestigacion, setNuevoProyectoInvestigacion] = useState("");

    const [numeroOficina, setNumeroOficina] = useState("");
    const [extensionTelefonica, setExtensionTelefonica] = useState("");
    const [supervisor, setSupervisor] = useState("");

    const [logrosAcademicos, setLogrosAcademicos] = useState([]);
    const [nuevoLogroAcademico, setNuevoLogroAcademico] = useState("");

    const [fechaJubilacion, setFechaJubilacion] = useState("");

    const [cursos, setCursos] = useState([]);
    const [nuevoCurso, setNuevoCurso] = useState("");

    const [cursosService, setCursosService] = useState([]);

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
            docente: {
                departamento: departamentoDocente, tituloAcatemico, especialidad, cursosImpartidos, historialLaboral: historialLaboral.map(registro => ({
                    puesto: registro.puesto,
                    departamento: registro.departamento,
                    fechaInicio: registro.fechaInicio,
                    fechaFin: registro.fechaFin,
                    descripcion: registro.descripcion,
                })), estadoLaboral, tipoDocente, fechaContratacion, tipoContrato, salario, horario, publicacionesAcademicas, proyectosInvestigacion, numeroOficina, extensionTelefonica, supervisor, logrosAcademicos, fechaJubilacion, cursos
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
                navigate("/list-inscripcion-docente");
            } catch (error) {
                console.error(error);
            }
        } else {
            try {
                const response = await InscripcionConRolAdminService.postInscripcion(formData);
                console.log(response.data);
                navigate("/list-inscripcion-docente");
            } catch (error) {
                console.error(error);
            }
        }
    }

    useEffect(() => {
        listarCursos();
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

                setDepartamentoDocente(response.data.docente.departamento);
                setTituloAcademico(response.data.docente.tituloAcatemico);
                setEspecialidad(response.data.docente.especialidad);
                setCursosImpartidos(response.data.docente.cursosImpartidos);
                setHistorialLaboral(response.data.docente.historialLaboral);
                setEstadoLaboral(response.data.docente.estadoLaboral);
                setTipoDocente(response.data.docente.tipoDocente);
                setFechaContratacion(response.data.docente.fechaContratacion);
                setTipoContrato(response.data.docente.tipoContrato);
                setSalario(response.data.docente.salario);
                setHorario(response.data.docente.horario);
                setPublicacionesAcademicas(response.data.docente.publicacionesAcademicas);
                setProyectosInvestigacion(response.data.docente.proyectosInvestigacion);
                setNumeroOficina(response.data.docente.numeroOficina);
                setExtensionTelefonica(response.data.docente.extensionTelefonica);
                setSupervisor(response.data.docente.supervisor);
                setLogrosAcademicos(response.data.docente.logrosAcademicos);
                setFechaJubilacion(response.data.docente.fechaJubilacion);
                setCursos(response.data.docente.cursos);
            }
            )
        }
    }, [id])

    function title() {
        if (id) {
            return (
                <div>Actualizar Inscripcion Con Rol de Docente</div>
            )
        } else {
            return (
                <div>Agregar Inscripcion Con Rol de Docente</div>
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

    {/*Funciones para inputs de Administrativo*/ }

    const estadosLaborales = ["ACTIVO",
        "INACTIVO",
        "LICENCIA",
        "JUBILADO",
        "BAJA_TEMPORAL",
        "BAJA_PERMANENTE"]

    const tiposDocentes = ["TIEMPO_COMPLETO",
        "MEDIO_TIEMPO",
        "VISITANTE",
        "ADJUNTO",
        "TITULAR",
        "EMERITO"]

    function listarCursos() {
        CursoAdminService.getAllCursos().then(response => {
            setCursosService(response.data);
            console.log(response.data);
        }).catch(error => {
            console.log(error);
        })
    }

    function obtenerNombreCurso(idCurso) {
        const cursoEncontrado = cursosService.find(cursoService => cursoService.idCurso === idCurso);
        return cursoEncontrado ? cursoEncontrado.nombre : "Desconocido";
    }

    function agregarCursoImpartido() {
        if (nuevoCursoImpartido.trim() !== '') {
            setCursosImpartidos([...cursosImpartidos, nuevoCursoImpartido]);
            setNuevoCursoImpartido('');
        }
    }

    function eliminarCursoImpartido(index) {
        setCursosImpartidos(cursosImpartidos.filter((_, i) => i !== index));
    }

    function agregarRegistro() {
        if (puesto && departamentoHistorialLaboral && fechaInicio && fechaFin && descripcion) {
            const nuevoRegistro = {
                puesto,
                departamento: departamentoHistorialLaboral,
                fechaInicio,
                fechaFin,
                descripcion
            };
            setHistorialLaboral([...historialLaboral, nuevoRegistro])
            setPuesto("");
            setDepartamentoHistorialLaboral("");
            setFechaInicio("");
            setFechaFin("");
            setDescripcion("");
        }
    }

    function eliminarRegistro(index) {
        setHistorialLaboral(historialLaboral.filter((_, i) => i !== index));
    }

    function agregarPublicacionAcademica() {
        if (nuevaPublicacionAcademica.trim() !== '') {
            setPublicacionesAcademicas([...publicacionesAcademicas, nuevaPublicacionAcademica]);
            setNuevaPublicacionAcademica('');
        }
    }

    function eliminarPublicacionAcademica(index) {
        setPublicacionesAcademicas(publicacionesAcademicas.filter((_, i) => i !== index));
    }

    function agregarProyectoDeInvestigacion() {
        if (nuevoProyectoInvestigacion.trim() !== '') {
            setProyectosInvestigacion([...proyectosInvestigacion, nuevoProyectoInvestigacion]);
            setNuevoProyectoInvestigacion('');
        }
    }

    function eliminarProyectoDeInvestigacion(index) {
        setProyectosInvestigacion(proyectosInvestigacion.filter((_, i) => i !== index));
    }

    function agregarLogroAcademico() {
        if (nuevoLogroAcademico.trim() !== '') {
            setLogrosAcademicos([...logrosAcademicos, nuevoLogroAcademico]);
            setNuevoLogroAcademico('');
        }
    }

    function eliminarLogroAcademico(index) {
        setLogrosAcademicos(logrosAcademicos.filter((_, i) => i !== index));
    }

    function agregarCurso() {
        if (nuevoCurso && !cursos.includes(nuevoCurso)) { // Verifica si ya fue seleccionado
            setCursos([...cursos, nuevoCurso]);
            setNuevoCurso('');
        }
    }

    function eliminarCurso(index) {
        setCursos(cursos.filter((_, i) => i !== index));
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
                        <option value={null}>Seleccione la Disponibilidad</option>
                        <option value={true}>Disponible</option>
                        <option value={false}>No disponible</option>
                    </select>
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


                {/*Docente*/}
                <div>
                    <label>Departamento en que trabaja el docente</label>
                    <input type="text" placeholder="Ingrese el Departamento en que trabaja el docente" name="departamentoDocente" value={departamentoDocente} onChange={e => setDepartamentoDocente(e.target.value)} />
                </div>

                <div>
                    <label>Titulo Academico</label>
                    <input type="text" placeholder="Ingrese el Titulo Academico" name="tituloAcatemico" value={tituloAcatemico} onChange={e => setTituloAcademico(e.target.value)} />
                </div>

                <div>
                    <label>Especialidad</label>
                    <input type="text" placeholder="Ingrese el Especialidad" name="especialidad" value={especialidad} onChange={e => setEspecialidad(e.target.value)} />
                </div>

                <div>
                    <div>
                        <label>Cursos Impartidos:</label>
                    </div>

                    {/* Selector para elegir cursos existentes */}
                    <div>
                        <label>Seleccionar Curso Existente</label>
                        <select
                            onChange={e => setNuevoCursoImpartido(e.target.value)}
                            value={nuevoCursoImpartido}
                        >
                            <option value="">Seleccione un curso</option>
                            {cursosService.map(curso => (
                                <option key={curso.idCurso} value={curso.nombre}>
                                    {curso.nombre}
                                </option>
                            ))}
                        </select>
                        <button type="button" onClick={agregarCursoImpartido}>Agregar Curso Existente</button>
                    </div>

                    <ul>
                        {cursosImpartidos.map((cursoImpartido, index) => (
                            <li key={index}>
                                {cursoImpartido}
                                <button type="button" onClick={() => eliminarCursoImpartido(index)}>Eliminar</button>
                            </li>
                        ))}
                    </ul>
                </div>

                <div>
                    <label>Historial Laboral</label>
                    <div>
                        <input type="text" placeholder="Ingrese el Puesto" value={puesto} onChange={(e) => setPuesto(e.target.value)} />
                        <input type="text" placeholder="Ingrese el Departamento" value={departamentoHistorialLaboral} onChange={(e) => setDepartamentoHistorialLaboral(e.target.value)} />
                        <input type="date" placeholder="Ingrese la Fecha de Inicio" value={fechaInicio} onChange={(e) => setFechaInicio(e.target.value)} />
                        <input type="date" placeholder="Ingrese la Fecha de Fin" value={fechaFin} onChange={(e) => setFechaFin(e.target.value)} />
                        <input type="text" placeholder="Ingrese la descripcion" value={descripcion} onChange={(e) => setDescripcion(e.target.value)} />
                        <button type="button" onClick={agregarRegistro}>Agregar Registro</button>
                    </div>
                    <ul>
                        {historialLaboral.map((registro, index) => (
                            <li key={index}>
                                <strong>Puesto:</strong> {registro.puesto}
                                <strong> Departamento:</strong> {registro.departamento}
                                <strong> Fecha de Inicio:</strong> {registro.fechaInicio}
                                <strong> Fecha de Fin:</strong> {registro.fechaFin}
                                <strong> Descripcion:</strong> {registro.descripcion}
                                <button type="button" onClick={() => eliminarRegistro(index)}>Eliminar</button>
                            </li>
                        ))}
                    </ul>
                </div>

                <div>
                    <label>Estado Laboral</label>
                    <select name="estadoLaboral" value={estadoLaboral} onChange={(e) => setEstadoLaboral(e.target.value)}>
                        <option value="">Seleccione un Estado Laboral</option>
                        {estadosLaborales.map((estadoLaboralValue, index) => (
                            <option key={index} value={estadoLaboralValue}>{estadoLaboralValue}</option>
                        ))}
                    </select>
                </div>

                <div>
                    <label>Tipo de Docente</label>
                    <select name="tipoDocente" value={tipoDocente} onChange={(e) => setTipoDocente(e.target.value)}>
                        <option value="">Seleccione un Tipo de Docente</option>
                        {tiposDocentes.map((tipoDocenteValue, index) => (
                            <option key={index} value={tipoDocenteValue}>{tipoDocenteValue}</option>
                        ))}
                    </select>
                </div>

                <div>
                    <label>Fecha de Contratacion</label>
                    <input type="date" placeholder="Ingrese la Fecha de Contratacion" name="fechaContratacion" value={fechaContratacion} onChange={e => setFechaContratacion(e.target.value)} />
                </div>

                <div>
                    <label>Tipo Contrato</label>
                    <input type="text" placeholder="Ingrese el Tipo de Contrato" name="tipoContrato" value={tipoContrato} onChange={e => setTipoContrato(e.target.value)} />
                </div>

                <div>
                    <label>Salario</label>
                    <input type="text" placeholder="Ingrese el Salario" name="salario" value={salario} onChange={e => setSalario(e.target.value)} />
                </div>

                <div>
                    <label>Horario</label>
                    <input type="text" placeholder="Ingrese el Horario" name="horario" value={horario} onChange={e => setHorario(e.target.value)} />
                </div>

                <div>
                    <div>
                        <label>Publicaciones Academicas</label>
                        <input type="text" placeholder="Ingrese las Publicaciones Academicas" value={nuevaPublicacionAcademica} onChange={e => setNuevaPublicacionAcademica(e.target.value)} />
                        <button type="button" onClick={agregarPublicacionAcademica}>Agregar Publicacion Academica</button>
                    </div>
                    <ul>
                        {publicacionesAcademicas.map((publicacionAcademica, index) => (
                            <li key={index}>
                                {publicacionAcademica}
                                <button type="button" onClick={() => eliminarPublicacionAcademica(index)}>Eliminar</button>
                            </li>
                        ))}
                    </ul>
                </div>

                <div>
                    <div>
                        <label>Proyectos de Investigacion</label>
                        <input type="text" placeholder="Ingrese los Proyectos de Investigacion" value={nuevoProyectoInvestigacion} onChange={e => setNuevoProyectoInvestigacion(e.target.value)} />
                        <button type="button" onClick={agregarProyectoDeInvestigacion}>Agregar Proyecto de Investigacion</button>
                    </div>
                    <ul>
                        {proyectosInvestigacion.map((proyectoInvestigacion, index) => (
                            <li key={index}>
                                {proyectoInvestigacion}
                                <button type="button" onClick={() => eliminarProyectoDeInvestigacion(index)}>Eliminar</button>
                            </li>
                        ))}
                    </ul>
                </div>

                <div>
                    <label>Numero de Oficina</label>
                    <input type="number" placeholder="Ingrese el Numero de Oficina" name="numeroOficina" value={numeroOficina} onChange={e => setNumeroOficina(e.target.value)} />
                </div>

                <div>
                    <label>Extencion Telefonica</label>
                    <input
                        type="text"
                        placeholder="Ingrese la Extencion Telefonica"
                        name="extensionTelefonica"
                        value={extensionTelefonica}
                        onChange={(e) => {
                            const value = e.target.value;
                            // Permitir solo números y limitar a 9 dígitos
                            if (/^\d*$/.test(value) && value.length <= 9) {
                                setExtensionTelefonica(value);
                            }
                        }}
                    />
                </div>

                <div>
                    <label>Supervisor</label>
                    <input type="text" placeholder="Ingrese el Supervisor" name="supervisor" value={supervisor} onChange={e => setSupervisor(e.target.value)} />
                </div>

                <div>
                    <div>
                        <label>Logros Academicos</label>
                        <input type="text" placeholder="Ingrese los Logros Academicos" value={nuevoLogroAcademico} onChange={e => setNuevoLogroAcademico(e.target.value)} />
                        <button type="button" onClick={agregarLogroAcademico}>Agregar Logro Academico</button>
                    </div>
                    <ul>
                        {logrosAcademicos.map((logroAcademico, index) => (
                            <li key={index}>
                                {logroAcademico}
                                <button type="button" onClick={() => eliminarLogroAcademico(index)}>Eliminar</button>
                            </li>
                        ))}
                    </ul>
                </div>

                <div>
                    <label>Fecha de Jubilacion</label>
                    <input type="date" placeholder="Ingrese la Fecha de Jubilacion" name="fechaJubilacion" value={fechaJubilacion} onChange={e => setFechaJubilacion(e.target.value)} />
                </div>

                <div>
                    <div>
                        <label>Todos los Cursos:</label>
                    </div>

                    {/* Selector para elegir cursos existentes */}
                    <div>
                        <label>Seleccionar Curso Existente</label>
                        <select
                            onChange={e => setNuevoCurso(e.target.value)}
                            value={nuevoCurso}
                        >
                            <option value="">Seleccione un curso</option>
                            {cursosService.map(curso => (
                                <option key={curso.idCurso} value={curso.idCurso}>
                                    {curso.nombre}
                                </option>
                            ))}
                        </select>
                        <button type="button" onClick={agregarCurso}>Agregar Curso Existente</button>
                    </div>

                    <ul>
                        {cursos.map((idCurso, index) => {
                            console.log("idCurso:", idCurso)
                            return (
                                <li key={index}>
                                    {obtenerNombreCurso(Number(idCurso))}
                                    <button type="button" onClick={() => eliminarCurso(index)}>Eliminar</button>
                                </li>
                            );
                        })}
                    </ul>
                </div>

                <div>
                    <button onClick={(e) => saveOrUpdateInscripcion(e)}>{botonAgregarOActualizar()}</button>
                    &nbsp;
                    &nbsp;
                    <Link to="/list-inscripcion-docente">Cancelar</Link>
                </div>
            </form>
        </div>
    )
}

export default AddInscripcionDocenteComponent;