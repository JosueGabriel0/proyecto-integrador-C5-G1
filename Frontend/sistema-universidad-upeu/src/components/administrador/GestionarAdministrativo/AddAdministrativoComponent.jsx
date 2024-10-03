import { useEffect, useState } from "react"
import { useNavigate, useParams } from "react-router-dom";
import AdministrativoAdminService from "../../../services/administradorServices/administrativo/AdministrativoAdminService";
import PersonaAdminService from "../../../services/administradorServices/persona/PersonaAdminService";
import { Link } from "react-router-dom";

function AddAdmistrativoComponent() {

    const { id } = useParams();
    const [registroPagos, setRegistroPagos] = useState("");
    const [montoTotalPagos, setMontoTotalPagos] = useState("");
    const [fechaUltimoPago, setFechaUltimoPago] = useState("");
    const [gestionEmpleados, setGestionEmpleados] = useState("");
    const [fechaContratacion, setFechaContratacion] = useState("");
    const [cargoEmpleado, setCargoEmpleado] = useState("");
    const [solicitudesPendientes, setSolicitudesPendientes] = useState("");
    const [fechaSolicitud, setFechaSolicitud] = useState("");
    const [idPersona, setIdPersona] = useState("");

    const [personas, setPersonas] = useState([]);

    const navigate = useNavigate();

    function listarPersonas(){
        PersonaAdminService.getAllPersonas().then(response => {
            setPersonas(response.data);
            console.log(response.data);
        }).catch(error => {
            console.log(error);
        });
    }

    function saveOrUpdateAdministrativo(e) {
        e.preventDefault();
        const administrativos = { registroPagos, montoTotalPagos, fechaUltimoPago, gestionEmpleados, fechaContratacion, cargoEmpleado, solicitudesPendientes, fechaSolicitud, idPersona }
        console.log("Datos enviados al backend: ", administrativos)
        if (id) {
            AdministrativoAdminService.updateAdministrativo(id, administrativos).then(response => {
                console.log(response.data);
                navigate("/administrativos");
            }).catch(error => {
                console.log(error);
            })
        } else {
            AdministrativoAdminService.createAdministrativo(administrativos).then(response => {
                console.log(response.data);
                navigate("/administrativos")
            }).catch(error => {
                console.log(error);
            })
        }
    }

    function title() {
        if (id) {
            return <div>Actualizar Administrativo</div>
        } else {
            return <div>Crear Administrativo</div>
        }
    }

    function botonAgregarOActualizar() {
        if (id) {
            return <div>Actualizar</div>
        } else {
            return <div>Agregar</div>
        }
    }

    // Función para evitar la entrada de caracteres no válidos como letras
    function preventInvalidInput(e) {
        if (["e", "E", "+", "-"].includes(e.key)) {
            e.preventDefault();
        }
    }

    // Función para manejar el cambio de valor, asegurando solo 2 decimales
    function handleMontoTotalPagosChange(e) {
        const value = e.target.value;
        const regex = /^\d*(\.\d{0,2})?$/;

        // Solo permite números con hasta 2 decimales
        if (regex.test(value)) {
            setMontoTotalPagos(value);
        }
    }

    useEffect(() => {
        listarPersonas();
        if (id) {
            AdministrativoAdminService.getAdministrativoById(id).then(response => {
                setRegistroPagos(response.data.registroPagos);
                setMontoTotalPagos(response.data.montoTotalPagos);
                setFechaUltimoPago(response.data.fechaUltimoPago);
                setGestionEmpleados(response.data.gestionEmpleados);
                setFechaContratacion(response.data.fechaContratacion);
                setCargoEmpleado(response.data.cargoEmpleado);
                setSolicitudesPendientes(response.data.solicitudesPendientes);
                setFechaSolicitud(response.data.fechaSolicitud);
                setIdPersona(response.data.idPersona);
            }).catch(error => {
                console.log(error);
            })
        }
    }, [id])

    return (
        <div className="container">
            <h1>{title()}</h1>
            <form>
                <div>
                    <label>Registro de Pagos</label>
                    <input type="text" placeholder="Ingrese el Registro de Pagos" name="registroPagos" value={registroPagos} onChange={(e) => setRegistroPagos(e.target.value)} />
                </div>

                <div>
                    <label>Monto Total de Pagos</label>
                    <input
                        type="number"
                        step="0.01"
                        min="0"
                        placeholder="Ingrese el Monto Total de Pagos"
                        name="montoTotalPagos"
                        value={montoTotalPagos}
                        onChange={(e) => handleMontoTotalPagosChange(e)}
                        onKeyDown={(e) => preventInvalidInput(e)}
                    />
                </div>

                <div>
                    <label>Fecha de Ultimo Pago</label>
                    <input type="date" placeholder="Ingrese la fecha de Ultimo Pago" name="fechaUltimoPago" value={fechaUltimoPago} onChange={(e) => setFechaUltimoPago(e.target.value)} />
                </div>

                <div>
                    <label>Gestion de Empleados</label>
                    <input type="text" placeholder="Ingrese la Gestion de Empleados" name="gestionEmpleados" value={gestionEmpleados} onChange={(e) => setGestionEmpleados(e.target.value)} />
                </div>

                <div>
                    <label>Fecha de Contratacion</label>
                    <input type="date" placeholder="Ingrese la fecha de contratacion" name="fechaContratacion" value={fechaContratacion} onChange={(e) => setFechaContratacion(e.target.value)} />
                </div>

                <div>
                    <label>Cargo de Empleado</label>
                    <input type="text" placeholder="Ingrese el Cargo de Empleado" name="cargoEmpleado" value={cargoEmpleado} onChange={(e) => setCargoEmpleado(e.target.value)} />
                </div>

                <div>
                    <label>Solicitudes Pendientes</label>
                    <input type="text" placeholder="Ingrese las Solicitudes Pendientes" name="solicitudesPendientes" value={solicitudesPendientes} onChange={(e) => setSolicitudesPendientes(e.target.value)} />
                </div>

                <div>
                    <label>Fecha Solicitud</label>
                    <input type="date" placeholder="Ingrese la Fecha de Solicitud" name="fechaSolicitud" value={fechaSolicitud} onChange={(e) => setFechaSolicitud(e.target.value)} />
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
                    <button onClick={(e) => saveOrUpdateAdministrativo(e)}>{botonAgregarOActualizar()}</button>
                    &nbsp;
                    &nbsp;
                    <Link to="/administrativos">Cancelar</Link>
                </div>
            </form>
        </div>
    )
}

export default AddAdmistrativoComponent;