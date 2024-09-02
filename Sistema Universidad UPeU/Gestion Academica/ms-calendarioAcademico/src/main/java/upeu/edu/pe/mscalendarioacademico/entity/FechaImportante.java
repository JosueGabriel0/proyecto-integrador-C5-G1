package upeu.edu.pe.mscalendarioacademico.entity;

import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDate;
import java.time.LocalDateTime;

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
    private CalendarioAcademico calendarioAcademico;

    private LocalDateTime fechaCreacion;
    private LocalDateTime fechaModificacion;

    @PrePersist
    public void onCreate(){
        fechaCreacion = java.time.LocalDateTime.now();
    }

    @PreUpdate
    public void preUpdate(){
        fechaModificacion = java.time.LocalDateTime.now();
    }
}
