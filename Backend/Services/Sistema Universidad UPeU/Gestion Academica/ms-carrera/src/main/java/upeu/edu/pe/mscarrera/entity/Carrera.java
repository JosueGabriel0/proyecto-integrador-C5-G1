package upeu.edu.pe.mscarrera.entity;

import jakarta.persistence.*;
import lombok.Data;
import upeu.edu.pe.mscarrera.dto.PlanificacionAcademica;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Data

public class Carrera {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idCarrera;

    private String codigo;
    private String nombre;
    private String descripcion;
    private int duracion; //Duracion en años

    @ElementCollection
    @CollectionTable(name = "cursos_Ids", joinColumns = @JoinColumn(name = "carrera_id"))
    @Column(name = "cursosIds")
    private List<Long> cursos = new ArrayList<>();

    @ElementCollection
    @CollectionTable(name = "planificaciones_Ids", joinColumns = @JoinColumn(name = "carrera_id"))
    @Column(name = "planificacionesIds")
    private List<Long> planificacionesAcademicas =  new ArrayList<>();

    private LocalDateTime fechaCreacionCarrera;
    private LocalDateTime fechaModificacionCarrera;

    @PrePersist
    public void onCreate(){
        fechaCreacionCarrera = java.time.LocalDateTime.now();
    }

    @PreUpdate
    public void onUpdate(){
        fechaModificacionCarrera = java.time.LocalDateTime.now();
    }
}
