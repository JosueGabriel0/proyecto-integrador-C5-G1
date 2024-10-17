package upeu.edu.pe.mspagos.entity;

import jakarta.persistence.*;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDate;

@Entity
@Data
public class Pago {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private BigDecimal monto;
    private String metodoPago; // Ejemplo: Tarjeta, Efectivo, Transferencia
    private LocalDate fechaPago;
    private String estado; // Pagado, Pendiente, Fallido, etc.

    // Relación con factura si es necesario
    @OneToOne(mappedBy = "pago")
    private Factura factura;

    // Relación con boleta
    @OneToOne(mappedBy = "pago")
    private Boleta boleta;


    // Getters y setters
}