package upeu.edu.pe.mspagos.entity;

import jakarta.persistence.*;
import lombok.Data;
import upeu.edu.pe.mspagos.dto.Estudiante;

import java.math.BigDecimal;
import java.time.LocalDate;

@Entity
@Data
public class Pago {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idPago;

    private BigDecimal monto;
    private String moneda;
    private String metodoPago; // Ejemplo: Tarjeta, Efectivo, Transferencia
    private String descripcion;
    private String estado; // Pagado, Pendiente, Fallido, etc.

    private Long idEstudiante;
    @Transient
    private Estudiante estudiante;

    private LocalDate fechaPago;

    // Relación con factura si es necesario
    @OneToOne(mappedBy = "pago")
    private Factura factura;

    // Relación con boleta
    @OneToOne(mappedBy = "pago")
    private Boleta boleta;


    // Getters y setters
}