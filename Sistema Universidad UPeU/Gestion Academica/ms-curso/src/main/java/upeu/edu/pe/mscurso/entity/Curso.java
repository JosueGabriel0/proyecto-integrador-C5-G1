package upeu.edu.pe.mscurso.entity;

import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Data

public class Curso {

    @Id
    @GeneratedValue( strategy = GenerationType.IDENTITY)
    private long idCurso;

    private String nombre;
    private String codigo;
    private String descripcion;
    private int creditos;
    private String tipo;
    private String nivel;

    @ElementCollection
    @CollectionTable(name = "asignaturas", joinColumns = @JoinColumn(name = "curso_id"))
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
