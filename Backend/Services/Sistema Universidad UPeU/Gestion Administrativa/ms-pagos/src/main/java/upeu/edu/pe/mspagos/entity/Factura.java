package upeu.edu.pe.mspagos.entity;

import jakarta.persistence.*;
import lombok.Data;
import upeu.edu.pe.mspagos.dto.Estudiante;

import java.math.BigDecimal;
import java.time.LocalDate;

@Entity
@Data
public class Factura {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idFactura;

    @OneToOne
    @JoinColumn(name = "pago_id")
    private Pago pago; // Relación con el pago

    private String numeroFactura;
    private LocalDate fechaEmision;
    private String descripcion;
    private BigDecimal impuestos;
    private BigDecimal subtotal;
    private BigDecimal total;
    private String estadoFactura;

    private Long idEstudiante;
    @Transient
    private Estudiante estudiante;

    // Getters y setters
}