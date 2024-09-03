package upeu.edu.pe.mscalendarioacademico.entity;

import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDate;

@Entity
@Data

public class FechaImportante {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idFechaImportante;

    @ManyToOne
    @JoinColumn(name = "calendarioAcademico_id")
    private CalendarioAcademico calendarioAcademico;

    private String descripcion;

    private LocalDate fecha;

    // Relación con la entidad Planificación Académica
    @ManyToOne
    @JoinColumn(name = "planificacion_academica_id")
    private CalendarioAcademico planificacionAcademica;
}
