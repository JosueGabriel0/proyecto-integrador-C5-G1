package upeu.edu.pe.mspagos.entity;

import jakarta.persistence.*;
import lombok.Data;
import upeu.edu.pe.mspagos.dto.Estudiante;

import java.math.BigDecimal;
import java.time.LocalDate;

@Entity
@Table(name = "deudas")
@Data
public class Deuda {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idDeuda;

    @Column(nullable = false)
    private Long idEstudiante;
    @Transient
    private Estudiante estudiante;

    @Column(nullable = false)
    private BigDecimal montoPendiente;

    @Column(nullable = false)
    private LocalDate fechaLimite;

    @Column(nullable = false, length = 20)
    private String estadoDeuda;
}