package upeu.edu.pe.mspagos.entity;

import lombok.Data;

@Data
public class PagoBoletaRequest {
    private Pago pago;
    private Boleta boleta;
}
