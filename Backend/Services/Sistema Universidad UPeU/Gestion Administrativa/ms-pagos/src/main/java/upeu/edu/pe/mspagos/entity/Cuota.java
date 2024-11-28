package upeu.edu.pe.mspagos.entity;

import jakarta.persistence.*;
import lombok.Data;
import java.math.BigDecimal;
import java.time.LocalDate;

@Entity
@Table(name = "cuotas")
@Data
public class Cuota {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idCuota;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "idPlanPago", nullable = false)
    private PlanDePago planDePago;

    @Column(nullable = false)
    private Integer numeroCuota;

    @Column(nullable = false)
    private BigDecimal monto;

    @Column(nullable = false)
    private LocalDate fechaVencimiento;

    @Column(nullable = false, length = 20)
    private String estadoCuota;
}