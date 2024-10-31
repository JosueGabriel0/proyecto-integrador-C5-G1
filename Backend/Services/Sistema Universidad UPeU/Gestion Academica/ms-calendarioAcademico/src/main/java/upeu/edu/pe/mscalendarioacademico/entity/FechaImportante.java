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

    // Relación con la entidad Calendario Académico
    @ManyToOne
    @JoinColumn(name = "calendarioAcademico_id")
    private CalendarioAcademico calendarioAcademico;

    private LocalDateTime fechaCreacionFechaImportante;
    private LocalDateTime fechaModificacionFechaImportante;

    @PrePersist
    public void onCreate(){
        fechaCreacionFechaImportante = java.time.LocalDateTime.now();
    }

    @PreUpdate
    public void onUpdate(){
        fechaModificacionFechaImportante = java.time.LocalDateTime.now();
    }
}
