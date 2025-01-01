package upeu.edu.pe.msplanificacionacademica.entity;

import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;

@Entity
@Data
public class Ciclo {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idCiclo;

    private int numeroCiclo; // Por ejemplo, 1, 2, ..., 10

    @Enumerated(EnumType.STRING)
    private EstadoCiclo estado; // Activo o Cerrado (opcional)

    @ManyToOne
    @JoinColumn(name = "id_planificacion_academica")
    private PlanificacionAcademica planificacionAcademica;

    // Lista de cursos asociados al ciclo
    @ElementCollection
    @CollectionTable(name = "ciclo_cursos", joinColumns = @JoinColumn(name = "ciclo_id"))
    @Column(name = "curso_id")
    private List<Long> cursosIds;

    private LocalDateTime fechaCreacionCiclo;
    private LocalDateTime fechaModificacionCiclo;

    @PrePersist
    public void onCreate() {
        fechaCreacionCiclo = java.time.LocalDateTime.now();
    }

    @PreUpdate
    public void onUpdate() {
        fechaModificacionCiclo = java.time.LocalDateTime.now();
    }
}