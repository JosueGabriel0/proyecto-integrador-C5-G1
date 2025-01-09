import React, { useState } from "react";

function ValidarPagoComponent() {
    // Estado para gestionar la selección entre boleta o factura
    const [seleccion, setSeleccion] = useState("");
    const [estadoFiltrar, setEstadoFiltrar] = useState("");

    // Función que se ejecutará cuando el usuario cambie la selección
    const handleSelectChange = (e) => {
        setSeleccion(e.target.value);
    };

    function boletaForm() {
        return (
            <div>
                <h3>Datos de Boleta</h3>
                <form action="">
                    <div>
                        <label>Nombre del Cliente</label>
                        <input id="descripcion-boleta" type="text" placeholder="Ingrese la descripción" />
                    </div>
                    <div>
                        <label>Tipo de documento</label>
                        <input id="monto-total-boleta" type="number" placeholder="Ingrese el monto total" />
                    </div>
                    <div>
                        <label>Documento de identidad</label>
                        <input id="monto-total-boleta" type="number" placeholder="Ingrese el monto total" />
                    </div>
                    <div>
                        <label>Direccion</label>
                        <input id="monto-total-boleta" type="number" placeholder="Ingrese el monto total" />
                    </div>
                    <div>
                        <label>Numero de boleta</label>
                        <input id="monto-total-boleta" type="number" placeholder="Ingrese el monto total" />
                    </div>
                    <div>
                        <label>Fecha de Emision</label>
                        <input id="monto-total-boleta" type="number" placeholder="Ingrese el monto total" />
                    </div>
                    <div>
                        <label>Descripcion de Boleta</label>
                        <input id="monto-total-boleta" type="number" placeholder="Ingrese el monto total" />
                    </div>
                    <div>
                        <label>Sucursal</label>
                        <input id="monto-total-boleta" type="number" placeholder="Ingrese el monto total" />
                    </div>
                    <div>
                        <label>Organizacion de ventas</label>
                        <input id="monto-total-boleta" type="number" placeholder="Ingrese el monto total" />
                    </div>
                    <div>
                        <label>Tipo de moneda</label>
                        <input id="monto-total-boleta" type="number" placeholder="Ingrese el monto total" />
                    </div>
                    <div>
                        <label>Codigo del producto o servicio</label>
                        <input id="monto-total-boleta" type="number" placeholder="Ingrese el monto total" />
                    </div>
                    <div>
                        <label>Descripcion del producto o servicio</label>
                        <input id="monto-total-boleta" type="number" placeholder="Ingrese el monto total" />
                    </div>
                    <div>
                        <label>Unidad de medida</label>
                        <input id="monto-total-boleta" type="number" placeholder="Ingrese el monto total" />
                    </div>
                    <div>
                        <label>Cantidad</label>
                        <input id="monto-total-boleta" type="number" placeholder="Ingrese el monto total" />
                    </div>
                    <div>
                        <label>Valor Unitario</label>
                        <input id="monto-total-boleta" type="number" placeholder="Ingrese el monto total" />
                    </div>
                    <div>
                        <label>Valor de descuento</label>
                        <input id="monto-total-boleta" type="number" placeholder="Ingrese el monto total" />
                    </div>
                    <div>
                        <label>Valor Total</label>
                        <input id="monto-total-boleta" type="number" placeholder="Ingrese el monto total" />
                    </div>
                    <div>
                        <label>Operacion Gravada</label>
                        <input id="monto-total-boleta" type="number" placeholder="Ingrese el monto total" />
                    </div>
                    <div>
                        <label>Operacion Inafecta</label>
                        <input id="monto-total-boleta" type="number" placeholder="Ingrese el monto total" />
                    </div>
                    <div>
                        <label>Operacion Exonerada</label>
                        <input id="monto-total-boleta" type="number" placeholder="Ingrese el monto total" />
                    </div>
                    <div>
                        <label>Operacion Gratuita</label>
                        <input id="monto-total-boleta" type="number" placeholder="Ingrese el monto total" />
                    </div>
                    <div>
                        <label>Descuentos Totales</label>
                        <input id="monto-total-boleta" type="number" placeholder="Ingrese el monto total" />
                    </div>
                    <div>
                        <label>I.G.V</label>
                        <input id="monto-total-boleta" type="number" placeholder="Ingrese el monto total" />
                    </div>
                    <div>
                        <label>Precio de venta total</label>
                        <input id="monto-total-boleta" type="number" placeholder="Ingrese el monto total" />
                    </div>
                    <div>
                        <label>Boleta electronica Generada:</label>
                        <img src="" alt="" />
                    </div>

                    <button>Validar voucher con boleta (Procesado)</button>
                    <button>Validar Inicialmente (Verificado)</button>
                    <button>Rechazar(Rechazado)</button>
                </form>
            </div>
        );
    }

    function facturaForm() {
        return (
            <div>
                <h3>Datos de Factura</h3>
                <form action="">
                    <div>
                        <label>Nombre del Cliente</label>
                        <input type="text" placeholder="Ingrese el nombre del cliente" />
                    </div>
                    <div>
                        <label>Tipo de documento</label>
                        <input type="number" placeholder="Ingrese el monto total" />
                    </div>
                    <div>
                        <label>Documento de identidad</label>
                        <input type="number" placeholder="Ingrese el monto total" />
                    </div>
                    <div>
                        <label>Direccion</label>
                        <input type="number" placeholder="Ingrese el monto total" />
                    </div>
                    <div>
                        <label>Numero de Factura</label>
                        <input type="number" placeholder="Ingrese el monto total" />
                    </div>
                    <div>
                        <label>Fecha de Emision</label>
                        <input type="number" placeholder="Ingrese el monto total" />
                    </div>
                    <div>
                        <label>Descripcion de Factura</label>
                        <input type="number" placeholder="Ingrese el monto total" />
                    </div>
                    <div>
                        <label>Sucursal</label>
                        <input type="number" placeholder="Ingrese el monto total" />
                    </div>
                    <div>
                        <label>Organizacion de ventas</label>
                        <input type="number" placeholder="Ingrese el monto total" />
                    </div>
                    <div>
                        <label>Tipo de moneda</label>
                        <input type="number" placeholder="Ingrese el monto total" />
                    </div>
                    <div>
                        <label>Estado de la Factura</label>
                        <input type="number" placeholder="Ingrese el monto total" />
                    </div>
                    <div>
                        <label>Codigo del producto o servicio</label>
                        <input type="number" placeholder="Ingrese el monto total" />
                    </div>
                    <div>
                        <label>Descripcion del producto o servicio</label>
                        <input type="number" placeholder="Ingrese el monto total" />
                    </div>
                    <div>
                        <label>Unidad de medida</label>
                        <input type="number" placeholder="Ingrese el monto total" />
                    </div>
                    <div>
                        <label>Cantidad</label>
                        <input type="number" placeholder="Ingrese el monto total" />
                    </div>
                    <div>
                        <label>Valor Unitario</label>
                        <input type="number" placeholder="Ingrese el monto total" />
                    </div>
                    <div>
                        <label>Valor de descuento</label>
                        <input type="number" placeholder="Ingrese el monto total" />
                    </div>
                    <div>
                        <label>Valor Total</label>
                        <input type="number" placeholder="Ingrese el monto total" />
                    </div>
                    <div>
                        <label>Operacion Gravada</label>
                        <input type="number" placeholder="Ingrese el monto total" />
                    </div>
                    <div>
                        <label>Operacion Inafecta</label>
                        <input type="number" placeholder="Ingrese el monto total" />
                    </div>
                    <div>
                        <label>Operacion Exonerada</label>
                        <input type="number" placeholder="Ingrese el monto total" />
                    </div>
                    <div>
                        <label>Operacion Gratuita</label>
                        <input type="number" placeholder="Ingrese el monto total" />
                    </div>
                    <div>
                        <label>Descuentos Totales</label>
                        <input type="number" placeholder="Ingrese el monto total" />
                    </div>
                    <div>
                        <label>I.G.V</label>
                        <input type="number" placeholder="Ingrese el monto total" />
                    </div>
                    <div>
                        <label>Precio de venta total</label>
                        <input type="number" placeholder="Ingrese el monto total" />
                    </div>
                    <div>
                        <label>Factura electronica Generada:</label>
                        <img src="" alt="" />
                    </div>

                    <button>Validar voucher con boleta (Procesado)</button>
                    <button>Validar Inicialmente (Verificado)</button>
                    <button>Rechazar(Rechazado)</button>
                </form>
            </div>
        );
    }

    async function handleFiltrar() {
        await new Promise((resolve) => {
        })
    }

    return (
        <div>
            <div className="Filtrar">
                <div>
                    <label><b>Seleccione el estado de los vouchers:</b></label>
                    <select name="estadoFiltrar" value={estadoFiltrar} onChange={(e) => { setEstadoFiltrar(e.target.value) }}>
                        <option value="">Seleccione un estado</option>
                        <option value="REGISTRADO">Registrados</option>
                        <option value="VERIFICADO">Verificados</option>
                        <option value="PROCESADO">Procesados</option>
                        <option value="RECHAZADO">Rechazados</option>
                    </select>
                </div>

                <div>
                    <label><b>Seleccione un voucher</b></label>
                    <select name="solicitud">
                        <option value="">Seleccione un voucher</option>
                        { }
                    </select>
                </div>

                <button onClick={handleFiltrar()}>Filtrar</button>
            </div>

            <h2>Proceso de validacion de voucher y creacion de pago</h2>
            <h3>Voucher seleccionado:</h3>
            <div>
                <label>Nombre del Banco:&nbsp;<b></b></label>
                &nbsp;
                &nbsp;
                <label>Numero de Operacion:&nbsp;<b></b></label>
                &nbsp;
                &nbsp;
                <label>Fecha de Operacion:&nbsp;<b></b></label>
                &nbsp;
                &nbsp;
                <label>Importe:&nbsp;<b></b></label>
                &nbsp;
                &nbsp;
                <label>Estado:&nbsp;<b></b></label>
                &nbsp;
                &nbsp;
                <label>Imagen del Voucher:</label>
                &nbsp;
                <img src="" alt="" />
            </div>

            <h3>Procesar datos</h3>
            <form action="">
                <h3>Datos de Pago</h3>
                <div>
                    <label htmlFor="monto-total">Monto Total</label>
                    <input id="monto-total" type="number" placeholder="Ingrese el monto total" />
                </div>
                <div>
                    <label htmlFor="metodo-pago">Método de pago</label>
                    <input id="metodo-pago" type="text" placeholder="Ingrese el método de pago" />
                </div>
                <div>
                    <label htmlFor="medio-pago">Medio de pago</label>
                    <input id="medio-pago" type="text" placeholder="Ingrese el medio de pago" />
                </div>
                <div>
                    <label htmlFor="estudiante">Estudiante</label>
                    <input id="estudiante" type="text" placeholder="Ingrese el estudiante" />
                </div>
                <div>
                    <label htmlFor="tipo-documento">Seleccione boleta o factura</label>
                    <select id="tipo-documento" onChange={handleSelectChange}>
                        <option value="">Seleccione...</option>
                        <option value="boleta">Boleta</option>
                        <option value="factura">Factura</option>
                    </select>
                </div>
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