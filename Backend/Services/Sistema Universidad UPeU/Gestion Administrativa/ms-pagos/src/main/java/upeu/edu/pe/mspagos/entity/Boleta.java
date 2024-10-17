package upeu.edu.pe.mspagos.entity;

import jakarta.persistence.*;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDate;

@Entity
@Data
public class Boleta {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne
    @JoinColumn(name = "pago_id")
    private Pago pago; // Relación con el pago asociado

    private String numeroBoleta;
    private LocalDate fechaEmision;
    private String descripcion;
    private BigDecimal total;
    private String tipoDocumento; // DNI, RUC, etc.
    private String datosCliente; // Información del estudiante

    // Getters y setters
}