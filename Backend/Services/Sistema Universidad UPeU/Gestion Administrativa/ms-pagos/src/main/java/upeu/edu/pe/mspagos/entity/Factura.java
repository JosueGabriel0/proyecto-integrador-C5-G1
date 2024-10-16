package upeu.edu.pe.mspagos.entity;

import jakarta.persistence.*;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDate;

@Entity
@Data
public class Factura {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne
    @JoinColumn(name = "pago_id")
    private Pago pago; // Relación con el pago

    private String numeroFactura;
    private LocalDate fechaEmision;
    private String descripcion;
    private BigDecimal total;

    // Getters y setters
}