import { useEffect, useState } from "react";
import { useNavigate, useParams, Link } from "react-router-dom";
import DocenteAdminService from "../../../services/administradorServices/docente/DocenteAdminService";
import PersonaAdminService from "../../../services/administradorServices/persona/PersonaAdminService";
import CursoAdminService from "../../../services/administradorServices/curso/CursoAdminService";

function AddDocenteComponent() {
    const [departamento, setDepartamento] = useState("");
    const [tituloAcademico, setTituloAcademico] = useState("");
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

    const [idPersona, setIdPersona] = useState("");

    const [personas, setPersonas] = useState([]);
    const [cursosService, setCursosService] = useState([]);

    const { id } = useParams();
    const navigate = useNavigate();

    function saveOrUpdateDocente(e) {
        e.preventDefault();
        const docente = {
            departamento, tituloAcademico, especialidad, cursosImpartidos, historialLaboral: historialLaboral.map(registro => ({
                puesto: registro.puesto,
                departamento: registro.departamento,
                fechaInicio: registro.fechaInicio,
                fechaFin: registro.fechaFin,
                descripcion: registro.descripcion,
            })), estadoLaboral, tipoDocente, fechaContratacion, tipoContrato, salario, horario, publicacionesAcademicas, proyectosInvestigacion, numeroOficina, extensionTelefonica, supervisor, logrosAcademicos, fechaJubilacion, cursos, idPersona
        }
        console.log("Estos son los datos enviados al Backend:", JSON.stringify(docente, null, 2)); // Agregar esta línea
        if (id) {
            DocenteAdminService.putDocente(id, docente).then(response => {
                console.log(response.data);
                navigate("/docentes");
            }).catch(error => {
                console.log(error);
            })
        } else {
            DocenteAdminService.postDocente(docente).then(response => {
                console.log(response.data);
                navigate("/docentes");
            }).catch(error => {
                console.log(error);
            })
        }
    }

    useEffect(() => {
        listarPersonas();
        listarCursos();
        if (id) {
            DocenteAdminService.getDocenteById(id).then(response => {
                setDepartamento(response.data.departamento);
                setTituloAcademico(response.data.tituloAcademico);
                setEspecialidad(response.data.especialidad);
                setCursosImpartidos(response.data.cursosImpartidos);
                setHistorialLaboral(response.data.historialLaboral);
                setEstadoLaboral(response.data.estadoLaboral);
                setTipoDocente(response.data.tipoDocente);
                setFechaContratacion(response.data.fechaContratacion);
                setTipoContrato(response.data.tipoContrato);
                setSalario(response.data.salario);
                setHorario(response.data.horario);
                setPublicacionesAcademicas(response.data.publicacionesAcademicas);
                setProyectosInvestigacion(response.data.proyectosInvestigacion);
                setNumeroOficina(response.data.numeroOficina);
                setExtensionTelefonica(response.data.extensionTelefonica);
                setSupervisor(response.data.supervisor);
                setLogrosAcademicos(response.data.logrosAcademicos);
                setFechaJubilacion(response.data.fechaJubilacion);
                setCursos(response.data.cursos);
                setIdPersona(response.data.idPersona);
            })
        }
    }, [id])

    function title() {
        if (id) {
            return <div>Actualizar Docente</div>
        } else {
            return <div>Agregar Docente</div>
        }
    }

    function botonAgregarOActualizar() {
        if (id) {
            return <div>Actualizar</div>
        } else {
            return <div>Agregar</div>
        }
    }

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

    function listarPersonas() {
        PersonaAdminService.getAllPersonas().then(response => {
            setPersonas(response.data);
            console.log(response.data);
        }).catch(error => {
            console.log(error);
        })
    }

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
        <div className="">
            <h1>{title()}</h1>
            <form>
                <div>
                    <label>Departamento</label>
                    <input type="text" placeholder="Ingrese el Departamento" name="departamento" value={departamento} onChange={e => setDepartamento(e.target.value)} />
                </div>

                <div>
                    <label>Titulo Academico</label>
                    <input type="text" placeholder="Ingrese el Titulo Academico" name="tituloAcademico" value={tituloAcademico} onChange={e => setTituloAcademico(e.target.value)} />
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
                    <label>Nombre de la Persona</label>
                    <select value={idPersona} onChange={e => setIdPersona(e.target.value)}>
                        <option>Seleccione Una Persona</option>
                        {personas.map(persona => (
                            <option key={persona.id} value={persona.id}>
                                {persona.nombres}
                            </option>
                        ))
                        }
                    </select>
                </div>

                <div>
                    <button onClick={(e) => saveOrUpdateDocente(e)}>{botonAgregarOActualizar()}</button>
                    &nbsp;
                    &nbsp;
                    <Link to="/docentes">Cancelar</Link>
                </div>

            </form>
        </div>
    )
}

export default AddDocenteComponent;