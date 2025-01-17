package upeu.edu.pe.mspagos.entity;

import lombok.Data;

@Data
public class PagoRequest {
    private Pago pago;
    private Boleta boleta;
    private Factura factura;
}
