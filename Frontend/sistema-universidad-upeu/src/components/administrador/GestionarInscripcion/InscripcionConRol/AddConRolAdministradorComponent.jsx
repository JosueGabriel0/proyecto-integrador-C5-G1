import { useState } from "react";
import { useNavigate, useParams } from "react-router-dom";

function AddConRolAdministradorComponent(){
    const [inscripcionRol, setInscripcionRol] = useState([]);
    const [nombreRol, setNombreRol] = useState("");
    const [description, setDescription] = useState("");

    const [username, setUsername] = useState("");
    const [password, setPassword] = useState("");
    const [emailRol, setEmailRol] = useState("");
    const [enabled, setEnabled] = useState("");
    const [idRol, setIdRol] = useState("");
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
    const [idUsuario, setIdUsuario] = useState("");

    const [actividadReciente, setActividadReciente] = useState("");
    const [fechaActividad, setFechaActividad] = useState("");
    const [estadoSistema, setEstadoSistema] = useState("");
    const [fechaUltimaRevision, setFechaUltimaRevision] = useState("");
    const [permisosEspeciales, setPermisosEspeciales] = useState("");
    const [logsAcceso, setLogsAcceso] = useState("");
    const [cambiosConfiguracion, setCambiosConfiguracion] = useState("");
    const [idPersona, setIdPersona] = useState("");

    const navigate = useNavigate();
    const {id} = useParams();

    function saveOrUpdateInscripcion(){}
    function title(){
        if(id){
            return(
                <div>Actualizar</div>
            )
        }else{
            return(
                <div>Agregar</div>
            )
        }
    }
    return(
        <div className="container">
            <h1>{title()}</h1>
        </div>
    )
}

export default AddConRolAdministradorComponent;