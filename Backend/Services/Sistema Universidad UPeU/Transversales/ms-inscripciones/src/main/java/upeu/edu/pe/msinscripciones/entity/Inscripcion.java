package upeu.edu.pe.msinscripciones.entity;

import jakarta.persistence.*;
import lombok.Data;
import upeu.edu.pe.msinscripciones.dto.*;

import java.time.LocalDateTime;

@Entity
@Data

public class Inscripcion {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long inscripcionId;

    //Inscripcion
    private String inscripcionConRol = "Sin Rol";
    private String inscripcionTipo;
    private Long inscripcionTipoId;
    private LocalDateTime fechaInscripcion;
    private LocalDateTime fechaModificacionInscripcion;

    //ROL
    private Long rolId;
    @Transient
    private Rol rol;

    //USUARIO
    private Long usuarioId;
    @Transient
    private Usuario usuario;

    /*
    //PERSONA
    private Long personaId;
    @Transient
    private Persona persona;

    //ESTUDIANTE
    @Transient
    private Estudiante estudiante;

    //DOCENTE
    @Transient
    private Docente docente;


    @PrePersist
    public void onCreate(){
        fechaInscripcion = java.time.LocalDateTime.now();
    }

    @PreUpdate
    public void onUpdate(){
        fechaModificacionInscripcion = java.time.LocalDateTime.now();
    }
     */
}