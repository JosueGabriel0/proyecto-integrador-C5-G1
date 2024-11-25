import React, { useEffect, useState } from "react";
import "../../../../style-sheets/general/dashboard/opcionesCuenta/GeneralViewProfileComponent.css";
import { useNavigate } from "react-router-dom";
import { getInscripcionId } from "../../../../services/authServices/authService";
import InscripcionService from "../../../../services/inscripcionServices/InscripcionService";
import PersonaService from "../../../../services/personaServices/PersonaService";

const GeneralViewProfileComponent = () => {

  const [loading, setLoading] = useState(true);
  const navigate = useNavigate();
  const inscripcionId = getInscripcionId();

  //Datos generales

  //Rol
  const [nombreRol, setNombreRol] = useState("");
  const [description, setDescription] = useState("");
  //Usuario
  const [username, setUsername] = useState("");
  const [password, setPassword] = useState("");
  const [emailUsuario, setEmailUsuario] = useState("");
  const [enabled, setEnabled] = useState("");
  const [ultimoLogin, setUltimoLogin] = useState("");
  //Persona
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

  const [imagendePersona, setImagenDePersona] = useState("")

  //Datos de Administrador
  const [actividadReciente, setActividadReciente] = useState("");
  const [fechaActividad, setFechaActividad] = useState("");
  const [estadoSistema, setEstadoSistema] = useState("");
  const [fechaUltimaRevision, setFechaUltimaRevision] = useState("");
  const [permisosEspeciales, setPermisosEspeciales] = useState("");
  const [logsAcceso, setLogsAcceso] = useState("");
  const [cambiosConfiguracion, setCambiosConfiguracion] = useState("");

  //Datos de Administrativo
  const [registroPagos, setRegistroPagos] = useState("");
  const [montoTotalPagos, setMontoTotalPagos] = useState("");
  const [fechaUltimoPago, setFechaUltimoPago] = useState("");
  const [gestionEmpleados, setGestionEmpleados] = useState("");
  const [fechaContratacion, setFechaContratacion] = useState("");
  const [cargoEmpleado, setCargoEmpleado] = useState("");
  const [solicitudesPendientes, setSolicitudesPendientes] = useState("");
  const [fechaSolicitud, setFechaSolicitud] = useState("");

  //Datos de Docente
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
  const [fechaContratacionDocente, setFechaContratacionDocente] = useState("");
  const [tipoContrato, setTipoContrato] = useState("");
  const [salario, setSalario] = useState("");
  const [horarioDocente, setHorarioDocente] = useState("");

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

  const [cursosDocente, setCursosDocente] = useState([]);
  const [nuevoCurso, setNuevoCurso] = useState("");

  const [cursosService, setCursosService] = useState([]);

  //Datos de Estudiante
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
  const [horarioEstudiante, setHorarioEstudiante] = useState("");
  const [consejeroAcademico, setConsejeroAcademico] = useState("");
  const [fechaGraduacion, setFechaGraduacion] = useState("");
  const [practicasRealizadas, setPracticasRealizadas] = useState([]);
  const [nuevaPractica, setNuevaPractica] = useState("");

  const [historialAcademico, setHistorialAcademico] = useState([]);

  const [nombreCurso, setNombreCurso] = useState("");
  const [calificacion, setCalificacion] = useState("");
  const [fechaFinalizacion, setFechaFinalizacion] = useState("");

  const [carreras, setCarreras] = useState([]);
  const [cursosEstudiante, setCursosEstudiante] = useState([]);

  const datosDelPerfil = () => {
    InscripcionService.getInscripcionById(inscripcionId).then(async (response) => {
      //Rol
      setNombreRol(response.data.rol.nombreRol);
      setDescription(response.data.rol.description);

      //Usuario
      setUsername(response.data.usuario.username);
      setPassword(response.data.usuario.password);
      setEmailUsuario(response.data.usuario.email);
      setEnabled(response.data.usuario.enabled);
      setUltimoLogin(response.data.usuario.ultimoLogin);

      //Persona
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

      if (response.data.persona.fotoPerfil) {
        const imagenUrl = await PersonaService.getPersonaImagen(response.data.persona.fotoPerfil);
        console.log("URL de la imagen de Persona:", imagenUrl);
        setImagenDePersona(imagenUrl);
      } else {
        console.warn("La Persona no tiene una foto de Perfil definida.")
      }

      setTipoSangre(response.data.persona.tipoSangre);
      setResponsableFinanciero(response.data.persona.responsableFinanciero);
      setContactoEmergenciaNombre(response.data.persona.contactoEmergenciaNombre);
      setContactoEmergenciaTelefono(response.data.persona.contactoEmergenciaTelefono);
      setContactoEmergenciaEmail(response.data.persona.contactoEmergenciaEmail);
      setContactoEmergenciaDireccion(response.data.persona.contactoEmergenciaDireccion);
      setContactoEmergenciaCiudad(response.data.persona.contactoEmergenciaCiudad);
      setContactoEmergenciaParentesco(response.data.persona.contactoEmergenciaParentesco);

      if (response.data.rol.nombreRol === "ADMINISTRADOR") {

        setActividadReciente(response.data.administrador.actividadReciente);
        setFechaActividad(response.data.administrador.fechaActividad);
        setEstadoSistema(response.data.administrador.estadoSistema);
        setFechaUltimaRevision(response.data.administrador.fechaUltimaRevision);
        setPermisosEspeciales(response.data.administrador.permisosEspeciales);
        setLogsAcceso(response.data.administrador.logsAcceso);
        setCambiosConfiguracion(response.data.administrador.cambiosConfiguracion);

      } else if (response.data.rol.nombreRol === "ADMINISTRATIVO") {

        setRegistroPagos(response.data.administrativo.registroPagos);
        setMontoTotalPagos(response.data.administrativo.montoTotalPagos);
        setFechaUltimoPago(response.data.administrativo.fechaUltimoPago);
        setGestionEmpleados(response.data.administrativo.gestionEmpleados);
        setFechaContratacion(response.data.administrativo.fechaContratacion);
        setCargoEmpleado(response.data.administrativo.cargoEmpleado);
        setSolicitudesPendientes(response.data.administrativo.solicitudesPendientes);
        setFechaSolicitud(response.data.administrativo.fechaSolicitud);

      } else if (response.data.rol.nombreRol === "DOCENTE") {
        setDepartamento(response.data.departamento);
        setTituloAcademico(response.data.tituloAcatemico);
        setEspecialidad(response.data.especialidad);
        setCursosImpartidos(response.data.cursosImpartidos);
        setHistorialLaboral(response.data.historialLaboral);
        setEstadoLaboral(response.data.estadoLaboral);
        setTipoDocente(response.data.tipoDocente);
        setFechaContratacion(response.data.fechaContratacion);
        setTipoContrato(response.data.tipoContrato);
        setSalario(response.data.salario);
        setHorarioDocente(response.data.horario);
        setPublicacionesAcademicas(response.data.publicacionesAcademicas);
        setProyectosInvestigacion(response.data.proyectosInvestigacion);
        setNumeroOficina(response.data.numeroOficina);
        setExtensionTelefonica(response.data.extensionTelefonica);
        setSupervisor(response.data.supervisor);
        setLogrosAcademicos(response.data.logrosAcademicos);
        setFechaJubilacion(response.data.fechaJubilacion);
        setCursosDocente(response.data.cursos);

      } else if (response.data.rol.nombreRol === "ESTUDIANTE") {

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
        setHorarioEstudiante(response.data.horario);
        setConsejeroAcademico(response.data.consejeroAcademico);
        setFechaGraduacion(response.data.fechaGraduacion);
        setPracticasRealizadas(response.data.practicasRealizadas);
        setHistorialAcademico(response.data.historialAcademico);

      }

      setLoading(false);
    }).catch((error) => {
      setLoading(false);
      console.log(error);
    })
  }

  const mostrarAdministrador = () => {
    return (
      <div className="container">
        <h4>Información del Administrador</h4>
        <p>
          <strong>Actividad Reciente:</strong> {actividadReciente}
        </p>
        <p>
          <strong>Fecha Actividad:</strong> {fechaActividad}
        </p>
        <p>
          <strong>Estado del Sistema:</strong> {estadoSistema}
        </p>
        <p>
          <strong>Fecha de Ultima Revision:</strong> {fechaUltimaRevision}
        </p>
        <p>
          <strong>Permisos Especiales:</strong> {permisosEspeciales}
        </p>
        <p>
          <strong>Logs Accesos:</strong> {logsAcceso}
        </p>
        <p>
          <strong>Cambios de la Configuracion:</strong> {cambiosConfiguracion}
        </p>
      </div>
    )
  }

  const mostrarAdministrativo = () => {
    return (
      <div>
      </div>
    )
  }

  const handleBack = () => {
    navigate(`/dashboard-${nombreRol.toLowerCase()}`);
  };

  useEffect(() => {
    datosDelPerfil();
  }, []);

  if (loading) {
    return <div className="loading">Cargando datos del perfil...</div>;
  }

  return (
    <div className="view-profile">
      <div className="view-profile-header">
        <h2>Perfil de Usuario {nombreRol}</h2>
      </div>
      <div className="view-profile-content">
        {/* Sección de Foto*/}

        <div className="view-profile-image-container">
          {imagendePersona ? (
            <img
              src={imagendePersona}
              alt="Foto de perfil"
              className="view-profile-image"
            />
          ) : (
            <div className="view-placeholder-image">Sin foto</div>
          )}
        </div>


        <div className="view-profile-scroll">

          {/* Información del Usuario */}
          <div className="view-profile-info">
            <h3>{nombres} {apellido_paterno} {apellido_materno}</h3>

            <h4>Información del Rol</h4>
            <p>
              <strong>Nombre del Rol:</strong> {nombreRol}
            </p>
            <p>
              <strong>Descripcion:</strong> {description}
            </p>

            <h4>Información del Usuario</h4>
            <p>
              <strong>Nombre del Usuario:</strong> {username}
            </p>
            <p>
              <strong>Contraseña:</strong> {"•".repeat(password.length)}
            </p>
            <p>
              <strong>Email del Usuario:</strong> {emailUsuario}
            </p>
            <p>
              <strong>Disponibilidad:</strong> {enabled ? "Disponible" : "No disponible"}
            </p>
            <p>
              <strong>Ultimo Login:</strong> {ultimoLogin}
            </p>

            <h4>Información de la Persona</h4>
            <p>
              <strong>Nombres Completos:</strong> {nombres}
            </p>
            <p>
              <strong>Apellido Paterno:</strong> {apellido_paterno}
            </p>
            <p>
              <strong>Apellido Materno:</strong> {apellido_materno}
            </p>
            <p>
              <strong>Fecha de nacimiento:</strong> {fecha_nacimiento}
            </p>
            <p>
              <strong>Genero:</strong> {genero}
            </p>
            <p>
              <strong>Nacionalidad:</strong> {nacionalidad}
            </p>
            <p>
              <strong>Tipo de documento:</strong> {tipoDocumento}
            </p>
            <p>
              <strong>Direccion:</strong> {direccion}
            </p>
            <p>
              <strong>Ciudad:</strong> {ciudad}
            </p>
            <p>
              <strong>Departamento:</strong> {departamento}
            </p>
            <p>
              <strong>País:</strong> {pais}
            </p>
            <p>
              <strong>Provincia:</strong> {provincia}
            </p>
            <p>
              <strong>Telefono:</strong> {telefono}
            </p>
            <p>
              <strong>Email Personal:</strong> {email}
            </p>
            <p>
              <strong>Estado Civil:</strong> {estadoCivil}
            </p>
            <p>
              <strong>Tipo de Sangre:</strong> {tipoSangre}
            </p>
            <p>
              <strong>Responsable Financiero:</strong> {responsableFinanciero}
            </p>
            <p>
              <strong>Nombre del Contacto de Emergencia:</strong> {contactoEmergenciaNombre}
            </p>
            <p>
              <strong>Telefono del Contacto de Emergencia:</strong> {contactoEmergenciaTelefono}
            </p>
            <p>
              <strong>Email del Contacto de Emergencia:</strong> {contactoEmergenciaEmail}
            </p>
            <p>
              <strong>Direccion del Contacto de Emergencia:</strong> {contactoEmergenciaDireccion}
            </p>
            <p>
              <strong>Ciudad del Contacto de Emergencia:</strong> {contactoEmergenciaCiudad}
            </p>
            <p>
              <strong>Parentesco del Contacto de Emergencia:</strong> {contactoEmergenciaParentesco}
            </p>

          </div>
          {mostrarAdministrador()}
          <h4>Información del Administrativo</h4>
          <h4>Información del Docente</h4>
          <h4>Información del Estudiante</h4>

        </div>
      </div>

      {/* Botón de Regresar */}
      <div className="view-profile-actions">
        <button onClick={handleBack} className="view-back-button">
          Regresar
        </button>
      </div>
    </div>
  );
};

export default GeneralViewProfileComponent;