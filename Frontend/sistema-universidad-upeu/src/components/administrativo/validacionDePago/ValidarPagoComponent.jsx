import React, { useState } from "react";

function ValidarPagoComponent() {
    // Estado para gestionar la selección entre boleta o factura
    const [seleccion, setSeleccion] = useState("");

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
                        <label htmlFor="descripcion-boleta">Nombre del Cliente</label>
                        <input id="descripcion-boleta" type="text" placeholder="Ingrese la descripción" />
                    </div>
                    <div>
                        <label htmlFor="monto-total-boleta">Tipo de documento</label>
                        <input id="monto-total-boleta" type="number" placeholder="Ingrese el monto total" />
                    </div>
                    <div>
                        <label htmlFor="monto-total-boleta">Documento de identidad</label>
                        <input id="monto-total-boleta" type="number" placeholder="Ingrese el monto total" />
                    </div>
                    <div>
                        <label htmlFor="monto-total-boleta">Direccion</label>
                        <input id="monto-total-boleta" type="number" placeholder="Ingrese el monto total" />
                    </div>
                    <div>
                        <label htmlFor="monto-total-boleta">Numero de boleta</label>
                        <input id="monto-total-boleta" type="number" placeholder="Ingrese el monto total" />
                    </div>
                    <div>
                        <label htmlFor="monto-total-boleta">Fecha de Emision</label>
                        <input id="monto-total-boleta" type="number" placeholder="Ingrese el monto total" />
                    </div>
                    <div>
                        <label htmlFor="monto-total-boleta">Descripcion de Boleta</label>
                        <input id="monto-total-boleta" type="number" placeholder="Ingrese el monto total" />
                    </div>
                    <div>
                        <label htmlFor="monto-total-boleta">Sucursal</label>
                        <input id="monto-total-boleta" type="number" placeholder="Ingrese el monto total" />
                    </div>
                    <div>
                        <label htmlFor="monto-total-boleta">Organizacion de ventas</label>
                        <input id="monto-total-boleta" type="number" placeholder="Ingrese el monto total" />
                    </div>
                    <div>
                        <label htmlFor="monto-total-boleta">Tipo de moneda</label>
                        <input id="monto-total-boleta" type="number" placeholder="Ingrese el monto total" />
                    </div>
                    <div>
                        <label htmlFor="monto-total-boleta">Codigo del producto o servicio</label>
                        <input id="monto-total-boleta" type="number" placeholder="Ingrese el monto total" />
                    </div>
                    <div>
                        <label htmlFor="monto-total-boleta">Descripcion del producto o servicio</label>
                        <input id="monto-total-boleta" type="number" placeholder="Ingrese el monto total" />
                    </div>
                    <div>
                        <label htmlFor="monto-total-boleta">Unidad de medida</label>
                        <input id="monto-total-boleta" type="number" placeholder="Ingrese el monto total" />
                    </div>
                    <div>
                        <label htmlFor="monto-total-boleta">Cantidad</label>
                        <input id="monto-total-boleta" type="number" placeholder="Ingrese el monto total" />
                    </div>
                    <div>
                        <label htmlFor="monto-total-boleta">Valor Unitario</label>
                        <input id="monto-total-boleta" type="number" placeholder="Ingrese el monto total" />
                    </div>
                    <div>
                        <label htmlFor="monto-total-boleta">Valor de descuento</label>
                        <input id="monto-total-boleta" type="number" placeholder="Ingrese el monto total" />
                    </div>
                    <div>
                        <label htmlFor="monto-total-boleta">Valor Total</label>
                        <input id="monto-total-boleta" type="number" placeholder="Ingrese el monto total" />
                    </div>
                    <div>
                        <label htmlFor="monto-total-boleta">Operacion Gravada</label>
                        <input id="monto-total-boleta" type="number" placeholder="Ingrese el monto total" />
                    </div>
                    <div>
                        <label htmlFor="monto-total-boleta">Operacion Inafecta</label>
                        <input id="monto-total-boleta" type="number" placeholder="Ingrese el monto total" />
                    </div>
                    <div>
                        <label htmlFor="monto-total-boleta">Operacion Exonerada</label>
                        <input id="monto-total-boleta" type="number" placeholder="Ingrese el monto total" />
                    </div>
                    <div>
                        <label htmlFor="monto-total-boleta">Operacion Gratuita</label>
                        <input id="monto-total-boleta" type="number" placeholder="Ingrese el monto total" />
                    </div>
                    <div>
                        <label htmlFor="monto-total-boleta">Descuentos Totales</label>
                        <input id="monto-total-boleta" type="number" placeholder="Ingrese el monto total" />
                    </div>
                    <div>
                        <label htmlFor="monto-total-boleta">I.G.V</label>
                        <input id="monto-total-boleta" type="number" placeholder="Ingrese el monto total" />
                    </div>
                    <div>
                        <label htmlFor="monto-total-boleta">Precio de venta total</label>
                        <input id="monto-total-boleta" type="number" placeholder="Ingrese el monto total" />
                    </div>
                    <div>
                        <h1>Boleta electronica Generada:</h1>
                    </div>
                    <button>Validar Pago con Boleta</button>
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
                        <label htmlFor="descripcion-boleta">Nombre del Cliente</label>
                        <input id="descripcion-boleta" type="text" placeholder="Ingrese la descripción" />
                    </div>
                    <div>
                        <label htmlFor="monto-total-boleta">Tipo de documento</label>
                        <input id="monto-total-boleta" type="number" placeholder="Ingrese el monto total" />
                    </div>
                    <div>
                        <label htmlFor="monto-total-boleta">Documento de identidad</label>
                        <input id="monto-total-boleta" type="number" placeholder="Ingrese el monto total" />
                    </div>
                    <div>
                        <label htmlFor="monto-total-boleta">Direccion</label>
                        <input id="monto-total-boleta" type="number" placeholder="Ingrese el monto total" />
                    </div>
                    <div>
                        <label htmlFor="monto-total-boleta">Numero de Factura</label>
                        <input id="monto-total-boleta" type="number" placeholder="Ingrese el monto total" />
                    </div>
                    <div>
                        <label htmlFor="monto-total-boleta">Fecha de Emision</label>
                        <input id="monto-total-boleta" type="number" placeholder="Ingrese el monto total" />
                    </div>
                    <div>
                        <label htmlFor="monto-total-boleta">Descripcion de Factura</label>
                        <input id="monto-total-boleta" type="number" placeholder="Ingrese el monto total" />
                    </div>
                    <div>
                        <label htmlFor="monto-total-boleta">Sucursal</label>
                        <input id="monto-total-boleta" type="number" placeholder="Ingrese el monto total" />
                    </div>
                    <div>
                        <label htmlFor="monto-total-boleta">Organizacion de ventas</label>
                        <input id="monto-total-boleta" type="number" placeholder="Ingrese el monto total" />
                    </div>
                    <div>
                        <label htmlFor="monto-total-boleta">Tipo de moneda</label>
                        <input id="monto-total-boleta" type="number" placeholder="Ingrese el monto total" />
                    </div>
                    <div>
                        <label htmlFor="monto-total-boleta">Estado de la Factura</label>
                        <input id="monto-total-boleta" type="number" placeholder="Ingrese el monto total" />
                    </div>
                    <div>
                        <label htmlFor="monto-total-boleta">Codigo del producto o servicio</label>
                        <input id="monto-total-boleta" type="number" placeholder="Ingrese el monto total" />
                    </div>
                    <div>
                        <label htmlFor="monto-total-boleta">Descripcion del producto o servicio</label>
                        <input id="monto-total-boleta" type="number" placeholder="Ingrese el monto total" />
                    </div>
                    <div>
                        <label htmlFor="monto-total-boleta">Unidad de medida</label>
                        <input id="monto-total-boleta" type="number" placeholder="Ingrese el monto total" />
                    </div>
                    <div>
                        <label htmlFor="monto-total-boleta">Cantidad</label>
                        <input id="monto-total-boleta" type="number" placeholder="Ingrese el monto total" />
                    </div>
                    <div>
                        <label htmlFor="monto-total-boleta">Valor Unitario</label>
                        <input id="monto-total-boleta" type="number" placeholder="Ingrese el monto total" />
                    </div>
                    <div>
                        <label htmlFor="monto-total-boleta">Valor de descuento</label>
                        <input id="monto-total-boleta" type="number" placeholder="Ingrese el monto total" />
                    </div>
                    <div>
                        <label htmlFor="monto-total-boleta">Valor Total</label>
                        <input id="monto-total-boleta" type="number" placeholder="Ingrese el monto total" />
                    </div>
                    <div>
                        <label htmlFor="monto-total-boleta">Operacion Gravada</label>
                        <input id="monto-total-boleta" type="number" placeholder="Ingrese el monto total" />
                    </div>
                    <div>
                        <label htmlFor="monto-total-boleta">Operacion Inafecta</label>
                        <input id="monto-total-boleta" type="number" placeholder="Ingrese el monto total" />
                    </div>
                    <div>
                        <label htmlFor="monto-total-boleta">Operacion Exonerada</label>
                        <input id="monto-total-boleta" type="number" placeholder="Ingrese el monto total" />
                    </div>
                    <div>
                        <label htmlFor="monto-total-boleta">Operacion Gratuita</label>
                        <input id="monto-total-boleta" type="number" placeholder="Ingrese el monto total" />
                    </div>
                    <div>
                        <label htmlFor="monto-total-boleta">Descuentos Totales</label>
                        <input id="monto-total-boleta" type="number" placeholder="Ingrese el monto total" />
                    </div>
                    <div>
                        <label htmlFor="monto-total-boleta">I.G.V</label>
                        <input id="monto-total-boleta" type="number" placeholder="Ingrese el monto total" />
                    </div>
                    <div>
                        <label htmlFor="monto-total-boleta">Precio de venta total</label>
                        <input id="monto-total-boleta" type="number" placeholder="Ingrese el monto total" />
                    </div>
                    <div>
                        <h1>Factura electronica Generada:</h1>
                    </div>
                    <button>Validar Pago con Factura</button>
                </form>
            </div>
        );
    }

    return (
        <div>
            <h2>Seleccione una solicitud pendiente</h2>
            <select name="solicitud" id="solicitud">
                <option value="">Seleccione una solicitud</option>
            </select>
            <h2>Solicitud seleccionada:</h2>


            <button>Validar Inicialmente</button>
            <button>Rechazar</button>

            <h2>Datos del Voucher para la validación del Pago a cuenta financiera universitaria</h2>
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