import { useEffect, useState } from "react";
import { useNavigate, useParams } from "react-router-dom";
import AdministradorAdminService from "../../../services/administradorServices/administrador/AdministradorAdminService";
import PersonaAdminService from "../../../services/administradorServices/persona/PersonaAdminService";
import { Link } from "react-router-dom";


function AddAdministradorComponent() {

    const [actividadReciente, setActividadReciente] = useState("");
    const [fechaActividad, setFechaActividad] = useState("");
    const [estadoSistema, setEstadoSistema] = useState("");
    const [fechaUltimaRevision, setFechaUltimaRevision] = useState("");
    const [permisosEspeciales, setPermisosEspeciales] = useState("");
    const [logsAcceso, setLogsAcceso] = useState("");
    const [cambiosConfiguracion, setCambiosConfiguracion] = useState("");
    const [idPersona, setIdPersona] = useState("");

    const [personas, setPersonas] = useState([]);
    const navigate = useNavigate();
    const { id } = useParams();

    function saveOrUpdateAdministrador(e) {
        e.preventDefault();
        const administrador = { actividadReciente, fechaActividad, estadoSistema, fechaUltimaRevision, permisosEspeciales, logsAcceso, cambiosConfiguracion, idPersona}
        console.log("Datos enviados al backend", administrador);
        if (id) {
            AdministradorAdminService.updateAdministrador(id, administrador).then(response => {
                console.log(response.data);
                navigate("/administradores");
            }).catch(error => {
                console.log(error);
            })
        } else {
            AdministradorAdminService.createAdministrador(administrador).then(response => {
                console.log(response.data);
                navigate("/administradores")
            }).catch(error => {
                console.log(error);
            })
        }
    }

    useEffect(() => {
        PersonaAdminService.getAllPersonas().then(response => {
            setPersonas(response.data);
        }).catch(error => {
            console.log(error);
        });

        if (id) {
            AdministradorAdminService.getAdministradorById(id).then(response => {
                setActividadReciente(response.data.actividadReciente);
                setEstadoSistema(response.data.estadoSistema);
                setFechaUltimaRevision(response.data.fechaUltimaRevision);
                setPermisosEspeciales(response.data.permisosEspeciales);
                setLogsAcceso(response.data.logsAcceso);
                setCambiosConfiguracion(response.data.cambiosConfiguracion);
                setIdPersona(response.data.idPersona);
            }).catch(error => {
                console.log(error);
            });
        }
    }, [id]);

    function title() {
        if (id) {
            return <div>Actualizar Administrador</div>
        } else {
            return <div>Agregar Administrador</div>
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
                    <label>Actividad Reciente</label>
                    <input type="text" placeholder="Ingrese la actividad reciente" name="actividadReciente" value={actividadReciente} onChange={(e) => setActividadReciente(e.target.value)}/>
                </div>

                <div>
                    <label>Fecha Actividad</label>
                    <input type="date" placeholder="Ingrese la Fecha de Actividad" name="fechaActividad" value={fechaActividad} onChange={(e) => setFechaActividad(e.target.value)}/>
                </div>

                <div>
                    <label>Estado Sistema</label>
                    <input type="text" placeholder="Ingrese el estado del Sistema" name="estadoSistema" value={estadoSistema} onChange={(e) => setEstadoSistema(e.target.value)} />
                </div>

                <div>
                    <label>Fecha Ultima Revision</label>
                    <input type="date" placeholder="Ingrese la Fecha de Ultima Revision" name="fechaUltimaRevision" value={fechaUltimaRevision} onChange={(e) => setFechaUltimaRevision(e.target.value)}/>
                </div>

                <div>
                    <label>Permisos Especiales</label>
                    <input type="text" placeholder="Ingrese los Permisos Especiales" name="permisosEspeciales" value={permisosEspeciales} onChange={(e) => setPermisosEspeciales(e.target.value)}/>
                </div>

                <div>
                    <label>Logs de Accesos</label>
                    <input type="text" placeholder="Ingrese los Logs de Accesos" name="logsAcceso" value={logsAcceso} onChange={(e) => setLogsAcceso(e.target.value)}/>
                </div>

                <div>
                    <label>Cambios de Configuracion</label>
                    <input type="text" placeholder="Ingrese los cambios de Configuracion" name="cambiosConfiguracion" value={cambiosConfiguracion} onChange={(e) => setCambiosConfiguracion(e.target.value)}/>
                </div>

                <div>
                    <label>Persona</label>
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
                    <button onClick={(e) => saveOrUpdateAdministrador(e)}>{botonAgregarOActualizar()}</button>
                    &nbsp;
                    &nbsp;
                    <Link to="/administradores">Cancelar</Link>
                </div>
            </form>
        </div>
    )
}

export default AddAdministradorComponent;