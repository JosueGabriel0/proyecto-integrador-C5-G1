package upeu.edu.pe.mscarrera.entity;

import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Data

public class Carrera {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    private String codigo;
    private String nombre;
    private String descripcion;
    private int duracion; //Duracion en años

    @ElementCollection
    @CollectionTable(name = "asignaturas", joinColumns = @JoinColumn(name = "carrera_id"))
    @Column(name = "asignatura")
    private List<String> asignaturas = new ArrayList<>();

    private LocalDateTime fechaCreacion;
    private LocalDateTime fechaModificacion;

    @PrePersist
    public void onCreate(){
        fechaCreacion = java.time.LocalDateTime.now();
    }

    @PreUpdate
    public void onUpdate(){
        fechaModificacion = java.time.LocalDateTime.now();
    }
}
