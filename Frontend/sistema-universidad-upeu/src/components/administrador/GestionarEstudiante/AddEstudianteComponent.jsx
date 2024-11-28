import EstudianteAdminService from "../../../services/administradorServices/estudiante/EstudianteAdminService";
import { Navigate, Link, useParams, useNavigate } from "react-router-dom";
import { useEffect, useState } from "react";
import PersonaAdminService from "../../../services/administradorServices/persona/PersonaAdminService";
import CarreraAdminService from "../../../services/administradorServices/carrera/CarreraAdminService";
import CursoAdminService from "../../../services/administradorServices/curso/CursoAdminService";

function AddEstudianteComponent() {
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

    const [idPersona, setIdPersona] = useState("");

    const [personas, setPersonas] = useState([]);
    const [carreras, setCarreras] = useState([]);
    const [cursos, setCursos] = useState([]);

    const { id } = useParams();
    const navigate = useNavigate();

    function saveOrUpdateEstudiante(e) {
        e.preventDefault();
        const estudiante = {
            matricula, cicloActual, promedioGeneral, fechaIngreso, estado, tipoEstudiante, beca, numeroMatricula, carrerasIngresadas, asignaturasMatriculadas, horario, consejeroAcademico, fechaGraduacion, practicasRealizadas, historialAcademico: historialAcademico.map(registro => ({
                nombreCurso: registro.nombreCurso,
                calificacion: parseFloat(registro.calificacion),
                fechaFinalizacion: registro.fechaFinalizacion,
            })), idPersona
        }
        console.log("Estos son los datos enviados al Backend:", JSON.stringify(estudiante, null, 2)); // Agregar esta línea
        if (id) {
            EstudianteAdminService.updateEstudiante(id, estudiante).then(response => {
                console.log(response.data);
                navigate("/estudiantes");
            }).catch(error => {
                console.log(error);
            })
        } else {
            EstudianteAdminService.createEstudiante(estudiante).then(response => {
                console.log(response.data);
                navigate("/estudiantes");
            })
        }
    }

    function title() {
        if (id) {
            return <div>Actualizar Estudiante</div>
        } else {
            return <div>Agregar Estudiante</div>
        }
    }

    function botonAgregarOActualizar() {
        if (id) {
            return <div>Actualizar</div>
        } else {
            return <div>Agregar</div>
        }
    }

    useEffect(() => {
        listarPersonas();
        listarCarreras();
        listarCursos();
        if (id) {
            EstudianteAdminService.getEstudianteById(id).then(response => {
                setMatricula(response.data.matricula);
                setCicloActual(response.data.cicloActual);
                setPromedioGeneral(response.data.promedioGeneral);
                setFechaIngreso(response.data.fechaIngreso);
                setEstado(response.data.estado);
                setTipoEstudiante(response.data.tipoEstudiante);
                setBeca(response.data.beca);
                setNumeroMatricula(response.data.numeroMatricula);
                setCarrerasIngresadas(response.data.carrerasIngresadas);
                setAsignaturasMatriculadas(response.data.asignaturasMatriculadas);
                setHorario(response.data.horario);
                setConsejeroAcademico(response.data.consejeroAcademico);
                setFechaGraduacion(response.data.fechaGraduacion);
                setPracticasRealizadas(response.data.practicasRealizadas);
                setHistorialAcademico(response.data.historialAcademico);
                setIdPersona(response.data.idPersona);
            })
        }
    }, [id])

    // Agregar una lista de estados posibles
    const estados = ["ACTIVO", "SUSPENDIDO", "GRADUADO", "RETIRADO", "MATRICULADO"]; // Correspondientes a tu enum

    function listarPersonas() {
        PersonaAdminService.getAllPersonas().then(response => {
            setPersonas(response.data);
            console.log(response.data);
        }).catch(error => {
            console.log(error);
        })
    }

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
                    <label>Nombre de la Persona</label>
                    <select value={idPersona} onChange={(e) => setIdPersona(e.target.value)}>
                        <option value="">Seleccione una Persona</option>
                        {personas.map(persona => (
                            <option key={persona.id} value={persona.id}>
                                {persona.nombres}
                            </option>
                        ))
                        }
                    </select>
                </div>

                <div>
                    <button onClick={(e) => saveOrUpdateEstudiante(e)}>{botonAgregarOActualizar()}</button>
                    &nbsp;
                    &nbsp;
                    <Link to="/estudiantes">Cancelar</Link>
                </div>
            </form>
        </div>
    );
}

export default AddEstudianteComponent;