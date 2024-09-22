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
    private Long idInscripcion;

    //Inscripcion
    private String inscripcionConRol = "Sin Rol";
    private LocalDateTime fechaCreacionInscripcion;
    private LocalDateTime fechaModificacionInscripcion;

    //ROL
    private Long idRol;
    @Transient
    private Rol rol;

    //USUARIO
    private Long idUsuario;
    @Transient
    private Usuario usuario;

    /*
    //PERSONA
    private Long idPersona;
    @Transient
    private Persona persona;

    //ESTUDIANTE
    private Long idEstudiante;
    @Transient
    private Estudiante estudiante;

    //DOCENTE
    private Long idDocente;
    @Transient
    private Docente docente;


    @PrePersist
    public void onCreate(){
        fechaCreacionInscripcion = java.time.LocalDateTime.now();
    }

    @PreUpdate
    public void onUpdate(){
        fechaModificacionInscripcion = java.time.LocalDateTime.now();
    }
     */
}