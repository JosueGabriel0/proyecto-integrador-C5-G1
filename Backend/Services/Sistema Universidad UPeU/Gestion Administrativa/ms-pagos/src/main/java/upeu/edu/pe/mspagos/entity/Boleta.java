package upeu.edu.pe.mspagos.entity;

import jakarta.persistence.*;
import lombok.Data;
import upeu.edu.pe.mspagos.dto.Estudiante;

import java.math.BigDecimal;
import java.time.LocalDate;

@Entity
@Data
public class Boleta {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idBoleta;

    @OneToOne
    @JoinColumn(name = "pago_id")
    private Pago pago; // Relación con el pago asociado

    private String numeroBoleta;
    private LocalDate fechaEmision;
    private String descripcion;
    private BigDecimal impuestos;
    private BigDecimal subtotal;
    private BigDecimal total;
    private String tipoDocumento; // DNI, RUC, etc.

    private Long idEstudiante;
    @Transient
    private Estudiante estudiante;

    // Getters y setters
}