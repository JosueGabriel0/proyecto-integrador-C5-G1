package upeu.edu.pe.mscuentafinancierauniversitaria.dto;

import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDate;

@Data
public class Pago {
    private Long idPago;

    private BigDecimal montoTotal;
    private String metodoDePago;
    private String medioDePago;
    private String estado;
    private String descripcion;

    private Long idEstudiante;
    private Estudiante estudiante;

    private Factura factura;

    private Boleta boleta;

    private LocalDate fechaPago;
}