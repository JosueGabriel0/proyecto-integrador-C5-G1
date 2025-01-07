package upeu.edu.pe.mspagos.entity;

import lombok.Data;

@Data
public class PagoFacturaRequest {
    private Pago pago;
    private Factura factura;
}
