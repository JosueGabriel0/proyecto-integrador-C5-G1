package upeu.edu.pe.msrequisitosacademicos.dto;

import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDateTime;

@Entity
@Data

public class Curso {

    @Id
    @GeneratedValue( strategy = GenerationType.IDENTITY)
    private Long idCurso;

    private String nombre;
    private String codigo;
    private String descripcion;
    private int creditos;
    private String tipo;
    private String nivel;

    private Long idCarrera;
    @Transient
    private Carrera carrera;

    private Long idDocente;
    @Transient
    private Docente docente;

    private LocalDateTime fechaCreacionCurso;
    private LocalDateTime fechaModificacionCurso;

    @PrePersist
    public void onCreate(){
        fechaCreacionCurso = LocalDateTime.now();
    }

    @PreUpdate
    public void onUpdate(){
        fechaModificacionCurso = LocalDateTime.now();
    }

}
