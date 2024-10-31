package upeu.edu.pe.mscurso.entity;

import jakarta.persistence.*;
import lombok.Data;
import upeu.edu.pe.mscurso.dto.Carrera;
import upeu.edu.pe.mscurso.dto.Docente;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

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
        fechaCreacionCurso = java.time.LocalDateTime.now();
    }

    @PreUpdate
    public void onUpdate(){
        fechaModificacionCurso = java.time.LocalDateTime.now();
    }

}
