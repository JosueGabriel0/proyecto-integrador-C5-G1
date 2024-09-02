package upeu.edu.pe.msplanificacionacademica.entity;

import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDate;

@Entity
@Data
public class FechaImportante {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idFechaImportante;

    @Column(nullable = false)
    private String descripcion;

    @Column(nullable = false)
    private LocalDate fecha;

    // Relación con la entidad Planificación Académica
    @ManyToOne
    @JoinColumn(name = "planificacion_academica_id")
    private PlanificacionAcademica planificacionAcademica;
}
