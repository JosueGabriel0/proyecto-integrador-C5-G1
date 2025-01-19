import React, { useEffect, useState } from "react";
import VoucherService from '../../../services/cuentaFinancieraServices/VoucherService';
import EstudianteService from "../../../services/estudianteServices/estudiante/EstudianteService";
import CuentaFinancieraService from "../../../services/cuentaFinancieraServices/CuentaFinancieraService";
import PersonaService from "../../../services/personaServices/PersonaService";
import PagoService from "../../../services/pagoServices/PagoService";
import { useNavigate } from "react-router-dom";

function ValidarPagoComponent() {
    // Estado para gestionar la selección entre boleta o factura
    const [seleccion, setSeleccion] = useState("");
    const [estadoFiltrar, setEstadoFiltrar] = useState("");
    const [vouchers, setVouchers] = useState([]);
    const [idVoucher, setIdVoucher] = useState("");

    //Datos del voucher
    const [nombreBanco, setNombreBanco] = useState("");
    const [numeroDeOperacion, setNumeroDeOperacion] = useState("");
    const [fechaDeOperacion, setFechaDeOperacion] = useState("");
    const [importe, setImporte] = useState("");
    const [estado, setEstado] = useState("");
    const [voucherURL, setVoucherURL] = useState("");

    const [imagenUrl, setImagenUrl] = useState(null);
    const [error, setError] = useState(null);

    //Datos de Pago
    const [montoTotal, setMontoTotal] = useState("");
    const [metodoDePago, setMetodoDePago] = useState("");
    const [medioDePago, setMedioDePago] = useState("");
    const [estadoPago, setEstadoPago] = useState("");
    const [descripcion, setDescripcion] = useState("");
    const [idEstudiante, setIdEstudiante] = useState("");
    const [estudiantePago, setEstudiantePago] = useState("");
    const fechaActualParaPago = new Date().toISOString().split("T")[0];
    const [fechaPago, setFechaPago] = useState(fechaActualParaPago);

    //Datos de Boleta
    const [nombreClienteBoleta, setNombreClienteBoleta] = useState("");
    const [documentoDeIdentidadBoleta, setDocumentoDeIdentidadBoleta] = useState("");
    const [direccionBoleta, setDireccionBoleta] = useState("");
    const [numeroBoleta, setNumeroBoleta] = useState("");
    const [fechaEmisionBoleta, setFechaEmisionBoleta] = useState("");
    const [descripcionBoleta, setDescripcionBoleta] = useState("");
    const [tipoDocumentoBoleta, setTipoDocumentoBoleta] = useState("");
    const [sucursalBoleta, setSucursalBoleta] = useState("");
    const [organizacionDeVentasBoleta, setOrganizacionDeVentasBoleta] = useState("");
    const [tipoMonedaBoleta, setTipoMonedaBoleta] = useState("");
    const [codigoProductoServicioBoleta, setCodigoProductoServicioBoleta] = useState("");
    const [descripcionProductoServicioBoleta, setDescripcionProductoServicioBoleta] = useState("");
    const [unidadDeMedidaBoleta, setUnidadDeMedidaBoleta] = useState("");
    const [cantidadBoleta, setCantidadBoleta] = useState("");
    const [valorUnitarioBoleta, setValorUnitarioBoleta] = useState("");
    const [valorDescuentoBoleta, setValorDescuentoBoleta] = useState("");
    const [valorTotalBoleta, setValorTotalBoleta] = useState("");
    const [operacionGravadaBoleta, setOperacionGravadaBoleta] = useState("");
    const [operacionInafectaBoleta, setOperacionInafectaBoleta] = useState("");
    const [operacionExoneradaBoleta, setOperacionExoneradaBoleta] = useState("");
    const [operacionGratuitaBoleta, setOperacionGratuitaBoleta] = useState("");
    const [descuentosTotalesBoleta, setDescuentosTotalesBoleta] = useState("");
    const [igvBoleta, setIgvBoleta] = useState("");
    const [precioVentaTotalBoleta, setPrecioVentaTotalBoleta] = useState("");
    const [boletaUrl, setBoletaUrl] = useState("");

    //Datos de Factura
    const [nombreClienteFactura, setNombreClienteFactura] = useState("");
    const [documentoDeIdentidadFactura, setDocumentoDeIdentidadFactura] = useState("");
    const [direccionFactura, setDireccionFactura] = useState("");
    const [numeroFactura, setNumeroFactura] = useState("");
    const [fechaEmisionFactura, setFechaEmisionFactura] = useState("");
    const [descripcionFactura, setDescripcionFactura] = useState("");
    const [tipoDocumentoFactura, setTipoDocumentoFactura] = useState("");
    const [sucursalFactura, setSucursalFactura] = useState("");
    const [organizacionDeVentasFactura, setOrganizacionDeVentasFactura] = useState("");
    const [tipoMonedaFactura, setTipoMonedaFactura] = useState("");
    const [estadoFactura, setEstadoFactura] = useState("");
    const [codigoProductoServicioFactura, setCodigoProductoServicioFactura] = useState("");
    const [descripcionProductoServicioFactura, setDescripcionProductoServicioFactura] = useState("");
    const [unidadDeMedidaFactura, setUnidadDeMedidaFactura] = useState("");
    const [cantidadFactura, setCantidadFactura] = useState("");
    const [valorUnitarioFactura, setValorUnitarioFactura] = useState("");
    const [valorDescuentoFactura, setValorDescuentoFactura] = useState("");
    const [valorTotalFactura, setValorTotalFactura] = useState("");
    const [operacionGravadaFactura, setOperacionGravadaFactura] = useState("");
    const [operacionInafectaFactura, setOperacionInafectaFactura] = useState("");
    const [operacionExoneradaFactura, setOperacionExoneradaFactura] = useState("");
    const [operacionGratuitaFactura, setOperacionGratuitaFactura] = useState("");
    const [descuentosTotalesFactura, setDescuentosTotalesFactura] = useState("");
    const [igvFactura, setIgvFactura] = useState("");
    const [precioVentaTotalFactura, setPrecioVentaTotalFactura] = useState("");
    const [facturaUrl, setFacturaUrl] = useState("");

    //React Router Dom
    const navigate = useNavigate();

    // Función que se ejecutará cuando el usuario cambie la selección
    const handleSelectChange = (e) => {
        setSeleccion(e.target.value);
    };

    async function cambiarDeEstadoVoucher(estadoVoucher) {
        const formData = new FormData();

        formData.append("nombreBanco", nombreBanco);
        formData.append("numeroDeOperacion", numeroDeOperacion);
        formData.append("fechaDeOperacion", fechaDeOperacion);
        formData.append("importe", importe);
        formData.append("estado", estadoVoucher);

        try {
            const objectURL = await VoucherService.getVoucherImagen(voucherURL);
            // Obtener la imagen como Blob usando fetch
            const response = await fetch(objectURL); // voucherURL ahora es un Object URL

            // Convertir la respuesta en un Blob
            const imagenBlob = await response.blob();

            // Crear un archivo a partir del Blob
            const imagenFile = new File([imagenBlob], voucherURL, {
                type: imagenBlob.type, // Mantener el tipo MIME original
            });

            // Agregar el archivo al FormData
            formData.append("file", imagenFile);
        } catch (error) {
            console.error("Error al obtener o procesar la imagen:", error);
            return; // Salir si hubo un error
        }

        // Enviar el FormData al backend
        try {
            const result = await VoucherService.putVoucher(idVoucher, formData);
            console.log(result.data);
        } catch (error) {
            console.error("Error al enviar el formulario:", error);
        }
    }

    async function voucherProcesadoConBoleta(e) {
        e.preventDefault();

        await cambiarDeEstadoVoucher("PROCESADO");

        const pagoConBoleta = {
            pago: {
                montoTotal,
                metodoDePago,
                medioDePago,
                estado: estadoPago,
                descripcion,
                idEstudiante,
                fechaPago
            },
            boleta: {
                nombreCliente: nombreClienteBoleta,
                documentoDeIdentidad: documentoDeIdentidadBoleta,
                direccion: direccionBoleta,
                numeroBoleta,
                fechaEmision: fechaEmisionBoleta,
                descripcionBoleta,
                tipoDocumento: tipoDocumentoBoleta,
                sucursal: sucursalBoleta,
                organizacionDeVentas: organizacionDeVentasBoleta,
                tipoMoneda: tipoMonedaBoleta,
                codigoProductoServicio: codigoProductoServicioBoleta,
                descripcionProductoServicio: descripcionProductoServicioBoleta,
                unidadDeMedida: unidadDeMedidaBoleta,
                cantidad: cantidadBoleta,
                valorUnitario: valorUnitarioBoleta,
                valorDescuento: valorDescuentoBoleta,
                operacionGravada: operacionGravadaBoleta,
                operacionInafecta: operacionInafectaBoleta,
                operacionExonerada: operacionExoneradaBoleta,
                operacionGratuita: operacionGratuitaBoleta
            }
        }

        await PagoService.postPagoConBoleta(pagoConBoleta).then((response) => {
            console.log(response.data);
        }).catch((error) => {
            console.log(error);
        })

        window.location.reload();
    }

    async function voucherProcesadoConFactura(e) {
        e.preventDefault();

        await cambiarDeEstadoVoucher("PROCESADO");

        const pagoConFactura = {
            pago: {
                montoTotal,
                metodoDePago,
                medioDePago,
                estado: estadoPago,
                descripcion,
                idEstudiante,
                fechaPago
            },
            factura: {
                nombreCliente: nombreClienteFactura,
                documentoDeIdentidad: documentoDeIdentidadFactura,
                direccion: direccionFactura,
                numeroFactura,
                fechaEmision: fechaEmisionFactura,
                descripcionFactura,
                tipoDocumento: tipoDocumentoFactura,
                sucursal: sucursalFactura,
                organizacionDeVentas: organizacionDeVentasFactura,
                tipoMoneda: tipoMonedaFactura,
                estadoFactura,
                codigoProductoServicio: codigoProductoServicioFactura,
                descripcionProductoServicio: descripcionProductoServicioFactura,
                unidadDeMedida: unidadDeMedidaFactura,
                cantidad: cantidadFactura,
                valorUnitario: valorUnitarioFactura,
                valorDescuento: valorDescuentoFactura,
                operacionGravada: operacionGravadaFactura,
                operacionInafecta: operacionInafectaFactura,
                operacionExonerada: operacionExoneradaFactura,
                operacionGratuita: operacionGratuitaFactura
            }
        }

        await PagoService.postPagoConFactura(pagoConFactura).then((response) => {
            console.log(response.data);
        }).catch((error) => {
            console.log(error);
        })

        window.location.reload();
    }

    async function voucherInicialmenteProcesado(e) {
        e.preventDefault();
        await cambiarDeEstadoVoucher("VERIFICADO");
        window.location.reload();
    }

    async function voucherRechazado(e) {
        e.preventDefault();
        await cambiarDeEstadoVoucher("RECHAZADO")
        window.location.reload();
    }

    function boletaForm() {
        return (
            <div>
                <h3>Datos de Boleta</h3>
                <form>
                    <div>
                        <label>Nombre del Cliente</label>
                        <input type="text" placeholder="Ingrese el nombre del cliente" name="nombreClienteBoleta" value={nombreClienteBoleta} onChange={(e) => { setNombreClienteBoleta(e.target.value) }} />
                    </div>
                    <div>
                        <label>Tipo de documento</label>
                        <input type="text" placeholder="Ingrese el tipo de documento" name="tipoDocumentoBoleta" value={tipoDocumentoBoleta} onChange={(e) => { setTipoDocumentoBoleta(e.target.value) }} />
                    </div>
                    <div>
                        <label>Documento de identidad</label>
                        <input type="text" placeholder="Ingrese el documento de identidad" name="documentoDeIdentidadBoleta" value={documentoDeIdentidadBoleta} onChange={(e) => { setDocumentoDeIdentidadBoleta(e.target.value) }} />
                    </div>
                    <div>
                        <label>Direccion</label>
                        <input type="text" placeholder="Ingrese la direccion" name="direccionBoleta" value={direccionBoleta} onChange={(e) => { setDireccionBoleta(e.target.value) }} />
                    </div>
                    <div>
                        <label>Numero de boleta</label>
                        <input type="text" disabled placeholder="Ingrese el numero de boleta" name="numeroBoleta" value={numeroBoleta} onChange={(e) => { setNumeroBoleta(e.target.value) }} />
                        <span style={{ fontSize: "12px", color: "#555" }}>(Número de boleta generado por la SUNAT)</span>
                    </div>
                    <div>
                        <label>Fecha de Emision</label>
                        <input type="date" placeholder="Ingrese la fecha de emision" name="fechaEmisionBoleta" value={fechaEmisionBoleta} onChange={(e) => { setFechaEmisionBoleta(e.target.value) }} />
                    </div>
                    <div>
                        <label>Descripcion de Boleta</label>
                        <input type="text" placeholder="Ingrese la descripcion" name="descripcionBoleta" value={descripcionBoleta} onChange={(e) => { setDescripcionBoleta(e.target.value) }} />
                    </div>
                    <div>
                        <label>Sucursal</label>
                        <input type="text" placeholder="Ingrese la sucursal" name="sucursalBoleta" value={sucursalBoleta} onChange={(e) => { setSucursalBoleta(e.target.value) }} />
                    </div>
                    <div>
                        <label>Organizacion de ventas</label>
                        <input type="text" placeholder="Ingrese la organizacion de ventas" name="organizacionDeVentasBoleta" value={organizacionDeVentasBoleta} onChange={(e) => { setOrganizacionDeVentasBoleta(e.target.value) }} />
                    </div>
                    <div>
                        <label>Tipo de moneda</label>
                        <input type="text" placeholder="Ingrese el tipo de moneda" name="tipoMonedaBoleta" value={tipoMonedaBoleta} onChange={(e) => { setTipoMonedaBoleta(e.target.value) }} />
                    </div>
                    <div>
                        <label>Codigo del producto o servicio</label>
                        <input type="text" placeholder="Ingrese el codigo del producto o servicio" name="codigoProductoServicioBoleta" value={codigoProductoServicioBoleta} onChange={(e) => { setCodigoProductoServicioBoleta(e.target.value) }} />
                    </div>
                    <div>
                        <label>Descripcion del producto o servicio</label>
                        <input type="text" placeholder="Ingrese la descripcion del producto o servicio" name="descripcionProductoServicioBoleta" value={descripcionProductoServicioBoleta} onChange={(e) => { setDescripcionProductoServicioBoleta(e.target.value) }} />
                    </div>
                    <div>
                        <label>Unidad de medida</label>
                        <input type="text" placeholder="Ingrese la unidad de medida" name="unidadDeMedidaBoleta" value={unidadDeMedidaBoleta} onChange={(e) => { setUnidadDeMedidaBoleta(e.target.value) }} />
                    </div>
                    <div>
                        <label>Cantidad</label>
                        <input type="number" placeholder="Ingrese la cantidad" name="cantidadBoleta" value={cantidadBoleta} onChange={(e) => { setCantidadBoleta(e.target.value) }} />
                    </div>
                    <div>
                        <label>Valor Unitario</label>
                        <input type="number" placeholder="Ingrese el valor unitario" name="valorUnitarioBoleta" value={valorUnitarioBoleta} onChange={(e) => { setValorUnitarioBoleta(e.target.value) }} />
                    </div>
                    <div>
                        <label>Valor de descuento</label>
                        <input type="number" placeholder="Ingrese el valor de descuento" name="valorDescuentoBoleta" value={valorDescuentoBoleta} onChange={(e) => { setValorDescuentoBoleta(e.target.value) }} />
                    </div>
                    <div>
                        <label>Valor Total</label>
                        <input type="number" placeholder="Ingrese el valor total" name="valorTotalBoleta" value={valorTotalBoleta} onChange={(e) => { setValorTotalBoleta(e.target.value) }} />
                    </div>
                    <div>
                        <label>Operacion Gravada</label>
                        <input type="number" placeholder="Ingrese la operacion gravada" name="operacionGravadaBoleta" value={operacionGravadaBoleta} onChange={(e) => { setOperacionGravadaBoleta(e.target.value) }} />
                    </div>
                    <div>
                        <label>Operacion Inafecta</label>
                        <input type="number" placeholder="Ingrese la operacion inafecta" name="operacionInafectaBoleta" value={operacionInafectaBoleta} onChange={(e) => { setOperacionInafectaBoleta(e.target.value) }} />
                    </div>
                    <div>
                        <label>Operacion Exonerada</label>
                        <input type="number" placeholder="Ingrese la operacion exonerada" name="operacionExoneradaBoleta" value={operacionExoneradaBoleta} onChange={(e) => { setOperacionExoneradaBoleta(e.target.value) }} />
                    </div>
                    <div>
                        <label>Operaciones Gratuitas</label>
                        <input type="number" placeholder="Ingrese la operaciones gratuitas" name="operacionGratuitaBoleta" value={operacionGratuitaBoleta} onChange={(e) => { setOperacionGratuitaBoleta(e.target.value) }} />
                    </div>
                    <div>
                        <label>Descuentos Totales</label>
                        <input type="number" placeholder="Ingrese los descuentos totales" name="descuentosTotalesBoleta" value={descuentosTotalesBoleta} onChange={(e) => { setDescuentosTotalesBoleta(e.target.value) }} />
                    </div>
                    <div>
                        <label>I.G.V</label>
                        <input type="number" placeholder="Ingrese el I.G.V" name="igvBoleta" value={igvBoleta} onChange={(e) => { setIgvBoleta(e.target.value) }} />
                    </div>
                    <div>
                        <label>Precio de venta total</label>
                        <input type="number" placeholder="Ingrese el precio de venta total" name="precioVentaTotalBoleta" value={precioVentaTotalBoleta} onChange={(e) => { setPrecioVentaTotalBoleta(e.target.value) }} />
                    </div>
                    <div>
                        <label>Boleta electronica Generada:</label>
                        <img src="" alt="" />
                    </div>

                    <button onClick={(e) => voucherProcesadoConBoleta(e)}>Validar voucher con boleta (Procesado)</button>
                </form>
            </div>
        );
    }

    function facturaForm() {
        return (
            <div>
                <h3>Datos de Factura</h3>
                <form>
                    <div>
                        <label>Nombre del Cliente</label>
                        <input type="text" placeholder="Ingrese el nombre del cliente" name="nombreClienteFactura" value={nombreClienteFactura} onChange={(e) => { nombreClienteFactura(e.target.value) }} />
                    </div>
                    <div>
                        <label>Tipo de documento</label>
                        <input type="text" placeholder="Ingrese el tipo de documento" name="tipoDocumentoFactura" value={tipoDocumentoFactura} onChange={(e) => { setTipoDocumentoFactura(e.target.value) }} />
                    </div>
                    <div>
                        <label>Documento de identidad</label>
                        <input type="text" placeholder="Ingrese el documento de identidad" name="documentoDeIdentidadFactura" value={documentoDeIdentidadFactura} onChange={(e) => { setDocumentoDeIdentidadFactura(e.target.value) }} />
                    </div>
                    <div>
                        <label>Direccion</label>
                        <input type="text" placeholder="Ingrese la direccion" name="direccionFactura" value={direccionFactura} onChange={(e) => { setDireccionFactura(e.target.value) }} />
                    </div>
                    <div>
                        <label>Numero de factura</label>
                        <input type="text" disabled placeholder="Ingrese el numero de factura" name="numeroFactura" value={numeroFactura} onChange={(e) => { setNumeroFactura(e.target.value) }} />
                        <span style={{ fontSize: "12px", color: "#555" }}>(Número de factura generado por la SUNAT)</span>
                    </div>
                    <div>
                        <label>Fecha de Emision</label>
                        <input type="date" placeholder="Ingrese la fecha de emision" name="fechaEmisionFactura" value={fechaEmisionFactura} onChange={(e) => { setFechaEmisionFactura(e.target.value) }} />
                    </div>
                    <div>
                        <label>Descripcion de Factura</label>
                        <input type="text" placeholder="Ingrese la descripcion" name="descripcionFactura" value={descripcionFactura} onChange={(e) => { setDescripcionFactura(e.target.value) }} />
                    </div>
                    <div>
                        <label>Sucursal</label>
                        <input type="text" placeholder="Ingrese la sucursal" name="sucursalFactura" value={sucursalFactura} onChange={(e) => { setSucursalFactura(e.target.value) }} />
                    </div>
                    <div>
                        <label>Organizacion de ventas</label>
                        <input type="text" placeholder="Ingrese la organizacion de ventas" name="organizacionDeVentasFactura" value={organizacionDeVentasFactura} onChange={(e) => { setOrganizacionDeVentasFactura(e.target.value) }} />
                    </div>
                    <div>
                        <label>Tipo de moneda</label>
                        <input type="text" placeholder="Ingrese el tipo de moneda" name="tipoMonedaFactura" value={tipoMonedaFactura} onChange={(e) => { setTipoMonedaFactura(e.target.value) }} />
                    </div>
                    <div>
                        <label>Estado Factura</label>
                        <input type="text" placeholder="Ingrese el estado de la factura" name="estadoFactura" value={estadoFactura} onChange={(e) => { setEstadoFactura(e.target.value) }} />
                    </div>
                    <div>
                        <label>Codigo del producto o servicio</label>
                        <input type="text" placeholder="Ingrese el codigo del producto o servicio" name="codigoProductoServicioFactura" value={codigoProductoServicioFactura} onChange={(e) => { setCodigoProductoServicioFactura(e.target.value) }} />
                    </div>
                    <div>
                        <label>Descripcion del producto o servicio</label>
                        <input type="text" placeholder="Ingrese la descripcion del producto o servicio" name="descripcionProductoServicioFactura" value={descripcionProductoServicioFactura} onChange={(e) => { setDescripcionProductoServicioFactura(e.target.value) }} />
                    </div>
                    <div>
                        <label>Unidad de medida</label>
                        <input type="text" placeholder="Ingrese la unidad de medida" name="unidadDeMedidaFactura" value={unidadDeMedidaFactura} onChange={(e) => { setUnidadDeMedidaFactura(e.target.value) }} />
                    </div>
                    <div>
                        <label>Cantidad</label>
                        <input type="number" placeholder="Ingrese la cantidad" name="cantidadFactura" value={cantidadFactura} onChange={(e) => { setCantidadFactura(e.target.value) }} />
                    </div>
                    <div>
                        <label>Valor Unitario</label>
                        <input type="number" placeholder="Ingrese el valor unitario" name="valorUnitarioFactura" value={valorUnitarioFactura} onChange={(e) => { setValorUnitarioFactura(e.target.value) }} />
                    </div>
                    <div>
                        <label>Valor de descuento</label>
                        <input type="number" placeholder="Ingrese el valor de descuento" name="valorDescuentoFactura" value={valorDescuentoFactura} onChange={(e) => { setValorDescuentoFactura(e.target.value) }} />
                    </div>
                    <div>
                        <label>Valor Total</label>
                        <input type="number" placeholder="Ingrese el valor total" name="valorTotalFactura" value={valorTotalFactura} onChange={(e) => { setValorTotalFactura(e.target.value) }} />
                    </div>
                    <div>
                        <label>Operacion Gravada</label>
                        <input type="number" placeholder="Ingrese la operacion gravada" name="operacionGravadaFactura" value={operacionGravadaFactura} onChange={(e) => { setOperacionGravadaFactura(e.target.value) }} />
                    </div>
                    <div>
                        <label>Operacion Inafecta</label>
                        <input type="number" placeholder="Ingrese la operacion inafecta" name="operacionInafectaFactura" value={operacionInafectaFactura} onChange={(e) => { setOperacionInafectaFactura(e.target.value) }} />
                    </div>
                    <div>
                        <label>Operacion Exonerada</label>
                        <input type="number" placeholder="Ingrese la operacion exonerada" name="operacionExoneradaFactura" value={operacionExoneradaFactura} onChange={(e) => { setOperacionExoneradaFactura(e.target.value) }} />
                    </div>
                    <div>
                        <label>Operaciones Gratuitas</label>
                        <input type="number" placeholder="Ingrese las operaciones gratuitas" name="operacionGratuitaFactura" value={operacionGratuitaFactura} onChange={(e) => { setOperacionGratuitaFactura(e.target.value) }} />
                    </div>
                    <div>
                        <label>Descuentos Totales</label>
                        <input type="number" placeholder="Ingrese los descuentos totales" name="descuentosTotalesFactura" value={descuentosTotalesFactura} onChange={(e) => { setDescuentosTotalesFactura(e.target.value) }} />
                    </div>
                    <div>
                        <label>I.G.V</label>
                        <input type="number" placeholder="Ingrese el I.G.V" name="igvFactura" value={igvFactura} onChange={(e) => { setIgvFactura(e.target.value) }} />
                    </div>
                    <div>
                        <label>Precio de venta total</label>
                        <input type="number" placeholder="Ingrese el precio de venta total" name="precioVentaTotalFactura" value={precioVentaTotalFactura} onChange={(e) => { setPrecioVentaTotalFactura(e.target.value) }} />
                    </div>
                    <div>
                        <label>Factura electronica Generada:</label>
                        <img src="" alt="" />
                    </div>

                    <button onClick={(e) => voucherProcesadoConFactura(e)}>Validar voucher con factura (Procesado)</button>
                </form>
            </div>
        );
    }

    const handleButtonClick = async () => {
        await handleFiltrar();
    };

    async function handleFiltrar() {
        console.log("Este es el id del voucher: " + idVoucher)
        VoucherService.getVoucherById(idVoucher).then((response) => {
            setNombreBanco(response.data.nombreBanco);
            setNumeroDeOperacion(response.data.numeroDeOperacion);
            setFechaDeOperacion(response.data.fechaDeOperacion);
            setImporte(response.data.importe);
            setEstado(response.data.estado);
            setVoucherURL(response.data.voucherURL);

            setMetodoDePago("Transferencia bancaria");
            setMontoTotal(response.data.importe);
            setMedioDePago(response.data.nombreBanco);
            setCodigoProductoServicioBoleta("1");
            setCodigoProductoServicioFactura("1");
            setNumeroBoleta("B000-00000000(ejemplo)")
            setNumeroFactura("F000-00000000(ejemplo)")
            const fechaActual = new Date().toISOString().split("T")[0];
            setFechaEmisionBoleta(fechaActual);
            setFechaEmisionFactura(fechaActual);

            // Obtén el voucherURL de la respuesta
            const voucherUrlObtenido = response.data.voucherURL;
            setVoucherURL(voucherUrlObtenido); // Actualiza el estado
            fetchImagen(voucherUrlObtenido); // Llama a fetchImagen con el valor actualizado
        })
    }

    async function fetchImagen(voucherUrl) {
        try {
            console.log("Este es la URL del Voucher: " + voucherUrl);
            const imagenObtenida = await VoucherService.getVoucherImagen(voucherUrl);

            if (imagenObtenida) {
                setImagenUrl(imagenObtenida);
                console.log("Esta es la imagen obtenida: " + imagenObtenida);
            } else {
                setError("No se pudo Obtener la imagen.");
            }
        } catch (e) {
            setError("Hubo un error al cargar la imagen.");
            console.error(e);
        }
    }

    function handleEstadoVoucher(estado) {
        if (estado) {
            console.log("Este es el estado para pasar al controller: " + estado)
            VoucherService.getVoucherByEstado(estado)
                .then((response) => {
                    setVouchers(response.data);
                    console.log(response.data);
                })
                .catch((error) => {
                    console.log(error);
                });
        }
    }

    function handleChange(e) {
        const estadoSeleccionado = e.target.value;
        console.log("Este es el estado: " + estadoSeleccionado)
        setEstadoFiltrar(estadoSeleccionado);
        handleEstadoVoucher(estadoSeleccionado); // Filtrar al cambiar el estado
    }

    function buscarDatosDeLaPersona(idVoucher) {
        console.log("Este es el id del Voucherr: " + idVoucher)
        CuentaFinancieraService.getCuentaFinancieraByVoucher(idVoucher).then((cuenta) => {
            console.log("Este es el id de cuenta financiera: " + cuenta.data.idCuentaFinanciera)
            EstudianteService.getEstudianteByCuentaFinanciera(cuenta.data.idCuentaFinanciera).then((estudiante) => {
                setIdEstudiante(estudiante.data.idEstudiante);
                PersonaService.getPersonaById(estudiante.data.idPersona).then((persona) => {
                    const nombreCompletoPersona = persona.data.nombres + " " + persona.data.apellido_paterno + " " + persona.data.apellido_materno;
                    setNombreClienteBoleta(nombreCompletoPersona);
                    setNombreClienteFactura(nombreCompletoPersona);
                    setEstudiantePago(nombreCompletoPersona);
                    setTipoDocumentoBoleta(persona.data.tipoDocumento);
                    setTipoDocumentoFactura(persona.data.tipoDocumento);
                    setDocumentoDeIdentidadBoleta(persona.data.numeroDocumento);
                    setDocumentoDeIdentidadFactura(persona.data.numeroDocumento);
                    setDireccionBoleta(persona.data.direccion);
                    setDireccionFactura(persona.data.direccion);
                    console.log(nombreCompletoPersona);
                }).catch((error) => {
                    console.log(error);
                })
            }).catch((error) => {
                console.log(error);
            })
        }).catch((error) => {
            console.log(error);
        })
    }



    useEffect(() => {
        buscarDatosDeLaPersona(idVoucher);
    }, [voucherURL, importe])


    return (
        <div>
            <div className="Filtrar">
                <div>
                    <label><b>Seleccione el estado de los vouchers:</b></label>
                    <select name="estadoFiltrar" value={estadoFiltrar} onChange={(e) => { handleChange(e) }}>
                        <option value="">Seleccione un estado</option>
                        <option value="REGISTRADO">Registrados</option>
                        <option value="VERIFICADO">Verificados</option>
                        <option value="PROCESADO">Procesados</option>
                        <option value="RECHAZADO">Rechazados</option>
                    </select>
                </div>

                <div>
                    <label><b>Seleccione un voucher</b></label>
                    <select name="idVoucher" value={idVoucher} onChange={(e) => { setIdVoucher(e.target.value) }}>
                        <option value="">Seleccione un voucher</option>
                        {vouchers.map((voucher) => (
                            <option key={voucher.idVoucher} value={voucher.idVoucher}>
                                {voucher.nombreBanco} - {voucher.numeroDeOperacion}
                            </option>
                        ))}
                    </select>
                </div>

                <button onClick={handleButtonClick}>Filtrar</button>
            </div>

            <h2>Proceso de validacion de voucher y creacion de pago</h2>
            <h3>Voucher seleccionado:</h3>
            <div>
                <label>Nombre del Banco:&nbsp;<b>{nombreBanco}</b></label>
                &nbsp;
                &nbsp;
                <label>Numero de Operacion:&nbsp;<b>{numeroDeOperacion}</b></label>
                &nbsp;
                &nbsp;
                <label>Fecha de Operacion:&nbsp;<b>{fechaDeOperacion}</b></label>
                &nbsp;
                &nbsp;
                <label>Importe:&nbsp;<b></b>{importe}</label>
                &nbsp;
                &nbsp;
                <label>Estado:&nbsp;<b>{estado}</b></label>
                &nbsp;
                &nbsp;
                <br />
                <label>Imagen del Voucher:</label>
                &nbsp;
                {error ? (
                    <p style={{ color: "red" }}>{error}</p>
                ) : imagenUrl ? (
                    <img src={imagenUrl} alt="Imagen del Voucher" style={{ width: "300px", height: "300px" }} />
                ) : (
                    <p>Cargando imagen...</p>
                )}
            </div>

            <h3>Procesar datos</h3>
            <form action="">
                <h3>Datos de Pago</h3>
                <div>
                    <label>Monto Total:</label>
                    <input type="number" placeholder="Ingrese el monto total" name="montoTotal" value={montoTotal} onChange={(e) => { setMontoTotal(e.target.value) }} />
                </div>
                <div>
                    <label >Método de pago:</label>
                    <input type="text" placeholder="Ingrese el método de pago" name="metodoDePago" value={metodoDePago} onChange={(e) => { setMetodoDePago(e.target.value) }} />
                </div>
                <div>
                    <label>Medio de pago:</label>
                    <input type="text" placeholder="Ingrese el medio de pago" name="medioDePago" value={medioDePago} onChange={(e) => { setMedioDePago(e.target.value) }} />
                </div>
                <div>
                    <label>Estado:</label>
                    <input type="text" placeholder="Ingrese el estado" name="estadoPago" value={estadoPago} onChange={(e) => { setEstadoPago(e.target.value) }} />
                </div>
                <div>
                    <label>Descripcion:</label>
                    <input type="text" placeholder="Ingrese el descripcion" name="descripcion" value={descripcion} onChange={(e) => { setDescripcion(e.target.value) }} />
                </div>
                <div>
                    <label>Estudiante:</label>
                    <input type="text" placeholder="Ingrese el estudiante" name="estudiantePago" value={estudiantePago} readOnly />
                </div>
                <div>
                    <label>Seleccione boleta o factura:</label>
                    <select onChange={handleSelectChange}>
                        <option value="">Seleccione...</option>
                        <option value="boleta">Boleta</option>
                        <option value="factura">Factura</option>
                    </select>
                </div>

                <button onClick={(e) => voucherInicialmenteProcesado(e)}>Validar Inicialmente (Verificado)</button>
                <button onClick={(e) => voucherRechazado(e)}>Rechazar(Rechazado)</button>
            </form>

            {/* Renderizar el formulario según la selección */}
            <div>
                {seleccion === "boleta" && boletaForm()}
                {seleccion === "factura" && facturaForm()}
            </div>
        </div>
    );
}

export default ValidarPagoComponent;