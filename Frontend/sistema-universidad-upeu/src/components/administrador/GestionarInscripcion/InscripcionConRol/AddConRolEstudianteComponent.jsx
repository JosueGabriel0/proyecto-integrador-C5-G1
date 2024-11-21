import { useEffect, useState } from "react";
import { useNavigate, useParams, Link } from "react-router-dom";
import InscripcionConRolAdminService from "../../../../services/administradorServices/Inscripcion/InscripcionAdminService";
import CarreraAdminService from "../../../../services/administradorServices/carrera/CarreraAdminService";
import CursoAdminService from "../../../../services/administradorServices/curso/CursoAdminService";

function AddConRolEstudianteComponent() {

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

    const [matricula, setMatricula] = useState("");
    const [cicloActual, setCicloActual] = useState("");
    const [promedioGeneral, setPromedioGeneral] = useState("");
    const [fechaIngreso, setFechaIngreso] = useState("");
    const [estado, setEstado] = useState("");
    const [tipoEstudiante, setTipoEstudiante] = useState("");
    const [beca, setBeca] = useState("");
    const [numeroMatricula, setNumeroMatricula] = useState("");
    const [carrerasIngresadas, setCarrerasIngresadas] = useState([]);
    const [nuevaCarrera, setNuevaCarrera] = useState(""); // Carrera a agregar
    const [asignaturasMatriculadas, setAsignaturasMatriculadas] = useState([]);
    const [nuevaAsignatura, setNuevaAsignatura] = useState("");
    const [horario, setHorario] = useState("");
    const [consejeroAcademico, setConsejeroAcademico] = useState("");
    const [fechaGraduacion, setFechaGraduacion] = useState("");
    const [practicasRealizadas, setPracticasRealizadas] = useState([]);
    const [nuevaPractica, setNuevaPractica] = useState("");

    const [historialAcademico, setHistorialAcademico] = useState([]);

    const [nombreCurso, setNombreCurso] = useState("");
    const [calificacion, setCalificacion] = useState("");
    const [fechaFinalizacion, setFechaFinalizacion] = useState("");

    const [carreras, setCarreras] = useState([]);
    const [cursos, setCursos] = useState([]);

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
            estudiante: {
                matricula, cicloActual, promedioGeneral, fechaIngreso, estado, tipoEstudiante, beca, numeroMatricula, carrerasIngresadas, asignaturasMatriculadas, horario, consejeroAcademico, fechaGraduacion, practicasRealizadas, historialAcademico: historialAcademico.map(registro => ({
                    nombreCurso: registro.nombreCurso,
                    calificacion: parseFloat(registro.calificacion),
                    fechaFinalizacion: registro.fechaFinalizacion,
                }))
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
                navigate("/list-inscripcionConRol-estudiante");
            } catch (error) {
                console.error(error);
            }
        } else {
            try {
                const response = await InscripcionConRolAdminService.postInscripcion(formData);
                console.log(response.data);
                navigate("/list-inscripcionConRol-estudiante");
            } catch (error) {
                console.error(error);
            }
        }
    }

    useEffect(() => {
        listarCarreras();
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

                setMatricula(response.data.estudiante.matricula);
                setCicloActual(response.data.estudiante.cicloActual);
                setPromedioGeneral(response.data.estudiante.promedioGeneral);
                setFechaIngreso(response.data.estudiante.fechaIngreso);
                setEstado(response.data.estudiante.estado);
                setTipoEstudiante(response.data.estudiante.tipoEstudiante);
                setBeca(response.data.estudiante.beca);
                setNumeroMatricula(response.data.estudiante.numeroMatricula);
                setCarrerasIngresadas(response.data.estudiante.carrerasIngresadas);
                setAsignaturasMatriculadas(response.data.estudiante.asignaturasMatriculadas);
                setHorario(response.data.estudiante.horario);
                setConsejeroAcademico(response.data.estudiante.consejeroAcademico);
                setFechaGraduacion(response.data.estudiante.fechaGraduacion);
                setPracticasRealizadas(response.data.estudiante.practicasRealizadas);
                setHistorialAcademico(response.data.estudiante.historialAcademico);
            }
            )
        }
    }, [id])

    function title() {
        if (id) {
            return (
                <div>Actualizar Inscripcion Con Rol de Estudiante</div>
            )
        } else {
            return (
                <div>Agregar Inscripcion Con Rol de Estudiante</div>
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

    {/*Funciones para inputs de Estudiante*/ }

    // Agregar una lista de estados posibles
    const estados = ["ACTIVO", "SUSPENDIDO", "GRADUADO", "RETIRADO"]; // Correspondientes a tu enum

    function listarCarreras() {
        CarreraAdminService.getAllCarreras().then(response => {
            setCarreras(response.data);
            console.log(response.data)
        }).catch(error => {
            console.log(error);
        })
    }

    function listarCursos() {
        CursoAdminService.getAllCursos().then(response => {
            setCursos(response.data);
            console.log(response.data);
        }).catch(error => {
            console.log(error);
        })
    }

    // Agrega una nueva carrera a la lista
    const agregarCarrera = () => {
        if (nuevaCarrera.trim() !== '') {
            setCarrerasIngresadas([...carrerasIngresadas, nuevaCarrera]);
            setNuevaCarrera(''); // Limpiar el campo
        }
    };

    // Elimina una carrera de la lista
    const eliminarCarrera = (index) => {
        setCarrerasIngresadas(carrerasIngresadas.filter((_, i) => i !== index));
    };

    const agregarAsignatura = () => {
        if (nuevaAsignatura.trim() !== '') {
            setAsignaturasMatriculadas([...asignaturasMatriculadas, nuevaAsignatura]);
            setNuevaAsignatura(''); // Limpiar el campo después de agregar
        }
    };

    const eliminarAsignatura = (index) => {
        setAsignaturasMatriculadas(asignaturasMatriculadas.filter((_, i) => i !== index));
    };

    const agregarPractica = () => {
        if (nuevaPractica.trim() !== '') {
            setPracticasRealizadas([...practicasRealizadas, nuevaPractica]);
            setNuevaPractica(''); // Limpiar el campo después de agregar
        }
    };

    const eliminarPractica = (index) => {
        setPracticasRealizadas(practicasRealizadas.filter((_, i) => i !== index));
    };

    const agregarRegistro = () => {
        if (nombreCurso && calificacion && fechaFinalizacion) {
            const nuevoRegistro = {
                nombreCurso,
                calificacion: parseFloat(calificacion),
                fechaFinalizacion
            };
            setHistorialAcademico([...historialAcademico, nuevoRegistro]);
            // Limpiar campos después de agregar
            setNombreCurso("");
            setCalificacion("");
            setFechaFinalizacion("");
        }
    };

    const eliminarRegistro = (index) => {
        setHistorialAcademico(historialAcademico.filter((_, i) => i !== index));
    };

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


                {/*Estudiante*/}
                <div>
                    <label>Matricula</label>
                    <input type="text" placeholder="Ingrese la Matricula" name="matricula" value={matricula} onChange={(e) => setMatricula(e.target.value)} />
                </div>

                <div>
                    <label>Ciclo Actual</label>
                    <input
                        type="number"
                        placeholder="Ingrese el ciclo actual"
                        name="cicloActual"
                        value={cicloActual}
                        onChange={(e) => {
                            // Permitir solo números enteros positivos
                            const value = e.target.value.replace(/[^0-9]/g, '');
                            setCicloActual(value);
                        }}
                    />
                </div>


                <div>
                    <label>Promedio General</label>
                    <input type="number" placeholder="Ingrese el Promedio General" name="promedioGeneral" value={promedioGeneral} onChange={(e) => setPromedioGeneral(e.target.value)} />
                </div>

                <div>
                    <label>Fecha Ingreso</label>
                    <input type="date" placeholder="Ingrese la Fecha de Ingreso" name="fechaIngreso" value={fechaIngreso} onChange={(e) => setFechaIngreso(e.target.value)} />
                </div>

                <div>
                    <label>Estado</label>
                    <select name="estado" value={estado} onChange={(e) => setEstado(e.target.value)}>
                        <option value="">Seleccione un estado</option>
                        {estados.map((estadoValue, index) => (
                            <option key={index} value={estadoValue}>{estadoValue}</option>
                        ))}
                    </select>
                </div>

                <div>
                    <label>Tipo Estudiante</label>
                    <input type="text" placeholder="Ingrese el tipo de estudiante" name="tipoEstudiante" value={tipoEstudiante} onChange={(e) => setTipoEstudiante(e.target.value)} />
                </div>

                <div>
                    <label>Beca</label>
                    <input type="text" placeholder="Ingrese si tiene Beca" name="beca" value={beca} onChange={(e) => setBeca(e.target.value)} />
                </div>

                <div>
                    <label>Numero de Matricula</label>
                    <input type="text" placeholder="Ingrese el numero de Matricula" name="numeroMatricula" value={numeroMatricula} onChange={(e) => setNumeroMatricula(e.target.value)} />
                </div>

                <div>
                    <div>
                        <label>Carreras Ingresadas:</label>
                    </div>

                    {/* Selector para elegir cursos existentes */}
                    <div>
                        <label>Seleccionar Carrera Existente</label>
                        <select
                            onChange={e => setNuevaCarrera(e.target.value)}
                            value={nuevaCarrera}
                        >
                            <option value="">Seleccione una Carrera</option>
                            {carreras.map(carrera => (
                                <option key={carrera.idCarrera} value={carrera.nombre}>
                                    {carrera.nombre}
                                </option>
                            ))}
                        </select>
                        <button type="button" onClick={agregarCarrera}>Agregar Carrera Existente</button>
                    </div>

                    <ul>
                        {carrerasIngresadas.map((carreraIngresada, index) => (
                            <li key={index}>
                                {carreraIngresada}
                                <button type="button" onClick={() => eliminarCarrera(index)}>Eliminar</button>
                            </li>
                        ))}
                    </ul>
                </div>

                <div>
                    <div>
                        <label>Asignaturas Matriculadas:</label>
                    </div>

                    {/* Selector para elegir cursos existentes */}
                    <div>
                        <label>Seleccionar Asignatura Existente</label>
                        <select
                            onChange={e => setNuevaAsignatura(e.target.value)}
                            value={nuevaAsignatura}
                        >
                            <option value="">Seleccione una Asignatura</option>
                            {cursos.map(curso => (
                                <option key={curso.idCurso} value={curso.nombre}>
                                    {curso.nombre}
                                </option>
                            ))}
                        </select>
                        <button type="button" onClick={agregarAsignatura}>Agregar Curso Existente</button>
                    </div>

                    <ul>
                        {asignaturasMatriculadas.map((asignaturaMatriculada, index) => (
                            <li key={index}>
                                {asignaturaMatriculada}
                                <button type="button" onClick={() => eliminarAsignatura(index)}>Eliminar</button>
                            </li>
                        ))}
                    </ul>
                </div>

                <div>
                    <label>Horario</label>
                    <input type="text" placeholder="Ingrese el Horario" name="horario" value={horario} onChange={(e) => setHorario(e.target.value)} />
                </div>

                <div>
                    <label>Consejero Academico</label>
                    <input type="text" placeholder="Ingrese el Consejero Academico" name="consejeroAcademico" value={consejeroAcademico} onChange={(e) => setConsejeroAcademico(e.target.value)} />
                </div>

                <div>
                    <label>Fecha de Graduacion</label>
                    <input type="date" placeholder="Ingrese la fecha de graduacion" name="fechaGraduacion" value={fechaGraduacion} onChange={(e) => setFechaGraduacion(e.target.value)} />
                </div>

                <div>
                    <label>Prácticas Realizadas</label>
                    <div>
                        <input
                            type="text"
                            placeholder="Ingrese la práctica"
                            value={nuevaPractica}
                            onChange={(e) => setNuevaPractica(e.target.value)}
                        />
                        <button type="button" onClick={agregarPractica}>Agregar Práctica</button>
                    </div>
                    <ul>
                        {practicasRealizadas.map((practica, index) => (
                            <li key={index}>
                                {practica}
                                <button type="button" onClick={() => eliminarPractica(index)}>Eliminar</button>
                            </li>
                        ))}
                    </ul>
                </div>

                <div>
                    <label>Historial Académico</label>
                    <div>
                        <input
                            type="text"
                            placeholder="Ingrese el Nombre del Curso"
                            value={nombreCurso}
                            onChange={(e) => setNombreCurso(e.target.value)}
                        />
                        <input
                            type="number"
                            placeholder="Ingrese la Calificación"
                            value={calificacion}
                            onChange={(e) => setCalificacion(e.target.value)}
                        />
                        <input
                            type="date"
                            placeholder="Ingrese la Fecha de Finalización"
                            value={fechaFinalizacion}
                            onChange={(e) => setFechaFinalizacion(e.target.value)}
                        />
                        <button type="button" onClick={agregarRegistro}>Agregar Registro</button>
                    </div>
                    <ul>
                        {historialAcademico.map((registro, index) => (
                            <li key={index}>
                                <strong>Curso:</strong> {registro.nombreCurso},
                                <strong> Calificación:</strong> {registro.calificacion},
                                <strong> Fecha Finalización:</strong> {registro.fechaFinalizacion}
                                <button type="button" onClick={() => eliminarRegistro(index)}>Eliminar</button>
                            </li>
                        ))}
                    </ul>
                </div>

                <div>
                    <button onClick={(e) => saveOrUpdateInscripcion(e)}>{botonAgregarOActualizar()}</button>
                    &nbsp;
                    &nbsp;
                    <Link to="/list-inscripcionConRol-estudiante">Cancelar</Link>
                </div>
            </form>
        </div>
    )
}

export default AddConRolEstudianteComponent;