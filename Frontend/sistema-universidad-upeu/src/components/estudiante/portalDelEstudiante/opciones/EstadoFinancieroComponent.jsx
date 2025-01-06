import React, { useEffect, useState } from "react";
import CuentaFinancieraService from "../../../../services/cuentasfinancierasServices.jsx/CuentaFinancieraService";
import EstudianteService from "../../../../services/estudianteServices/estudiante/EstudianteService";
import { getInscripcionId } from "../../../../services/authServices/authService";
import InscripcionService from "../../../../services/inscripcionServices/InscripcionService";
import PersonaService from "../../../../services/personaServices/PersonaService";

function EstadoFinancieroComponent() {
    const [imagendePersona, setImagenDePersona] = useState("")
    const [cuentaFinanciera, setCuentaFinanciera] = useState(null)

    const [entidad, setEntidad] = useState("");
    const [departamento, setDepartamento] = useState("");
    const [anio, setAnio] = useState("");
    const [sumasDebito, setSumasDebito] = useState("");
    const [sumasCredito, setSumasCredito] = useState("");
    const [saldoFinalDebito, setSaldoFinalDebito] = useState("");
    const [saldoFinalCredito, setSaldoFinalCredito] = useState("");
    const [saldoAfavor, setSaldoAfavor] = useState("");

    const [fecha, setFecha] = useState("");
    const [voucher, setVoucher] = useState("");
    const [lote, setLote] = useState("");
    const [documento, setDocumento] = useState("");
    const [movimiento, setMovimiento] = useState("");
    const [descripcion, setDescripcion] = useState("");
    const [debito, setDebito] = useState("");
    const [credito, setCredito] = useState("");
    const [idPago, setIdPago] = useState("");

    const [nombreCompleto, setNombreCompleto] = useState("");
    const [codigoUniversitario, setCodigoUniversitario] = useState("");
    const [email, setEmail] = useState("");
    const [telefono, setTelefono] = useState("");
    const [responsableFinancieroNombres, setResponsableFinancieroNombres] = useState("");
    const [responsableFinancieroTelefono, setResponsableFinancieroTelefono] = useState("");
    const [responsableFinancieroTipoDocumento, setResponsableFinancieroTipoDocumento] = useState("");
    const [responsableFinancieroNumeroDocumento, setResponsableFinancieroNumeroDocumento] = useState("");


    const idInscripcion = getInscripcionId();

    function obtenerFotoPersona() {
        InscripcionService.getInscripcionById(idInscripcion).then(async (response) => {
            if (response.data.persona.fotoPerfil) {
                const imagenUrl = await PersonaService.getPersonaImagen(response.data.persona.fotoPerfil);
                console.log("URL de la imagen de Persona:", imagenUrl);
                setImagenDePersona(imagenUrl);
            } else {
                console.warn("La Persona no tiene una foto de Perfil definida.")
            }
        })
    }

    useEffect(() => {
        const obtenerDatosCuentaFinanciera = async () => {
            try {
                const inscripcion = await InscripcionService.getInscripcionById(idInscripcion);
                const persona = await PersonaService.getPersonaById(inscripcion.data.idPersona);
                const estudiante = await EstudianteService.getEstudianteById(inscripcion.data.idEstudiante);

                const idPersona = persona.data.id;
                const idEstudiante = estudiante.data.idEstudiante;
                const idCuentaFinanciera = estudiante.data.idCuentaFinanciera;

                console.log("Este es el id de la inscripcion: " + idInscripcion);
                console.log("Este es el id de la persona: " + idPersona);
                console.log("Este es el id del estudiante: " + idEstudiante);
                console.log("Este es el id de la cuenta financiera: " + idCuentaFinanciera);

                if (idPersona) {
                    setNombreCompleto(persona.data.nombres + " " + persona.data.apellido_paterno + " " + persona.data.apellido_materno);
                    setEmail(persona.data.email);
                    setTelefono(persona.data.telefono);
                } else {
                    console.error("ID de Persona no disponible.");
                }

                if (idEstudiante) {
                    setCodigoUniversitario(estudiante.data.codigoUniversitario);
                    setResponsableFinancieroNombres(estudiante.data.responsableFinanciero.nombres + " " + estudiante.data.responsableFinanciero.apellido_paterno + " " + estudiante.data.responsableFinanciero.apellido_materno);
                    setResponsableFinancieroTelefono(estudiante.data.responsableFinanciero.celular);
                    setResponsableFinancieroTipoDocumento(estudiante.data.responsableFinanciero.tipoDocumento);
                    setResponsableFinancieroNumeroDocumento(estudiante.data.responsableFinanciero.numeroDocumento);
                } else {
                    console.error("ID del estudiante no disponible.");
                }

                if (idCuentaFinanciera) {
                    const response = await CuentaFinancieraService.getCuentaFinancieraById(idCuentaFinanciera);

                    // Actualizamos los estados con los datos obtenidos
                    if (response && response.data) {
                        setCuentaFinanciera({
                            ...response.data,
                            movimientosAcademicos: response.data.movimientosAcademicos || [],
                        });
                        console.log("este es el response " + response)
                    } else {
                        console.error("Datos de la cuenta financiera no disponibles.");
                    }
                    
                    setEntidad(response.data.entidad);
                    setDepartamento(response.data.departamento);
                    setAnio(response.data.anio);
                    setSumasDebito(response.data.sumasDebito);
                    setSumasCredito(response.data.sumasCredito);
                    setSaldoFinalDebito(response.data.saldoFinalDebito);
                    setSaldoFinalCredito(response.data.saldoFinalCredito);
                    setSaldoAfavor(response.data.saldoAfavor);

                    const movimientos = response.data.movimientosAcademicos || {};
                    setFecha(movimientos.fecha);
                    setVoucher(movimientos.voucher);
                    setLote(movimientos.lote);
                    setDocumento(movimientos.documento);
                    setMovimiento(movimientos.movimiento);
                    setDescripcion(movimientos.descripcion);
                    setDebito(movimientos.debito);
                    setCredito(movimientos.credito);
                    setIdPago(movimientos.idPago);
                } else {
                    console.error("ID de cuenta financiera no disponible.");
                }
            } catch (error) {
                console.error("Error al obtener algunos datos", error);
            }
        };

        obtenerDatosCuentaFinanciera();
        obtenerFotoPersona();
    }, []);

    return (
        <div className="container">
            <h2>Bienvenido al Estado Financiero</h2>
            <h2>Universidad Peruana Unión</h2>
            <h3>Carret. Central Km 19.5 Ñaña. Telef. 6816300</h3>
            <h3>Fax. 618-6339 Castilla 3564 Lima 1, Perú.</h3>
            <h2>Estado Financiero</h2>

            <div>
                <div>
                    <label htmlFor="">Entidad:</label>
                    <select name="entidad" value={entidad}>
                        <option value="">Seleccione una entidad</option>
                        <option value={entidad}>{entidad}</option>
                    </select>
                </div>

                <div>
                    <label htmlFor="">Departamento:</label>
                    <select name="departamento" value={departamento}>
                        <option value="">Seleccione un departamento</option>
                        <option value={departamento}>{departamento}</option>
                    </select>
                </div>

                <div>
                    <label htmlFor="">Año:</label>
                    <select name="anio" value={anio}>
                        <option value="">Seleccione un año</option>
                        <option value={anio}>{anio}</option>
                    </select>
                </div>

                <button>Filtrar</button>

                <div>
                    <label htmlFor="">De:</label>
                    <select disabled>
                        <option value="">Enero</option>
                        <option value="">Seleccione un desde que mes</option>
                    </select>
                </div>

                <div>
                    <label htmlFor="">Hasta:</label>
                    <select disabled>
                        <option value="">Diciembre</option>
                        <option value="">Seleccione hasta que mes</option>
                    </select>
                </div>
            </div>

            <div>
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
                <div>
                    <label htmlFor=""><b>Alumno:</b>&nbsp;{nombreCompleto}</label>
                    <label htmlFor=""><b>Codigo:</b>&nbsp;{codigoUniversitario}</label>
                    <label htmlFor=""><b>EAP:</b>&nbsp;</label>
                    <label htmlFor=""><b>e-mail:</b>&nbsp;{email}</label>
                    <label htmlFor=""><b>AluTelf/Cel:</b>&nbsp;{telefono}</label>
                    <label htmlFor=""><b>Resp. Finan:</b>&nbsp;{responsableFinancieroNombres}</label>
                    <label htmlFor=""><b>Tel R.F:</b>&nbsp;{responsableFinancieroTelefono}</label>
                    <label htmlFor=""><b>{responsableFinancieroTipoDocumento} R.F:</b>&nbsp;{responsableFinancieroNumeroDocumento}</label>
                </div>
            </div>

            <div>
                <h4>Depositar:</h4>
                <button>DEPOSITE AQUI</button>
                <button>VOUCHER DE DEPOSITO</button>
            </div>

            <div>
                <div>
                    <h4>Mis depositos: </h4>
                    <button>Ver</button>
                </div>
                <div>
                    <table>
                        <thead>
                            <tr>
                                <th>#</th>
                                <th>Fecha</th>
                                <th>Voucher</th>
                                <th>Lote</th>
                                <th>Documento</th>
                                <th>Mov</th>
                                <th>Glosa</th>
                                <th>Débito</th>
                                <th>Crédito</th>
                                <th>PDF</th>
                            </tr>
                        </thead>
                        <tbody>
                            {cuentaFinanciera && cuentaFinanciera.movimientosAcademicos ? (
                                cuentaFinanciera.movimientosAcademicos.map((movimiento) => (
                                    <tr key={movimiento.idMovimientoAcademico}>
                                        <td>{movimiento.idMovimientoAcademico}</td>
                                        <td>{movimiento.fecha}</td>
                                        <td>{movimiento.voucher}</td>
                                        <td>{movimiento.lote}</td>
                                        <td>{movimiento.documento}</td>
                                        <td>{movimiento.movimiento}</td>
                                        <td>{movimiento.descripcion}</td>
                                        <td>{movimiento.debito}</td>
                                        <td>{movimiento.credito}</td>
                                        <td>{movimiento.idPago}</td>
                                    </tr>
                                ))
                            ) : (
                                <tr>
                                    <td colSpan="10">No hay movimientos disponibles</td>
                                </tr>
                            )}
                        </tbody>
                    </table>
                </div>
            </div>
        </div>
    );
}

export default EstadoFinancieroComponent;