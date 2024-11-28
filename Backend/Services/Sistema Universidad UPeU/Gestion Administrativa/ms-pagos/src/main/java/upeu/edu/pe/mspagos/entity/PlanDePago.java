package upeu.edu.pe.mspagos.entity;

import jakarta.persistence.*;
import lombok.Data;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "planes_pago")
@Data
public class PlanDePago {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idPlanPago;

    @Column(nullable = false, length = 50)
    private String nombre;

    @Column(length = 255)
    private String descripcion;

    @Column(nullable = false)
    private BigDecimal montoTotal;

    @OneToMany(mappedBy = "planDePago", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Cuota> cuotas = new ArrayList<>();

    @Column(nullable = false)
    private Integer numeroCuotas;

    @Column(nullable = false)
    private BigDecimal montoPorCuota;

    @Column(nullable = false)
    private LocalDate fechaInicio;

    @Column(nullable = false)
    private LocalDate fechaFin;
}