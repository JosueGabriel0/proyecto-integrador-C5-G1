package upeu.edu.pe.msauth.dto;

import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDateTime;

@Data

public class Inscripcion {
    private Long idInscripcion;

    //Inscripcion
    private String inscripcionRol;
    private LocalDateTime fechaCreacionInscripcion;
    private LocalDateTime fechaModificacionInscripcion;

    //ROL
    private Long idRol;
    private Rol rol;

    //USUARIO
    private Long idUsuario;
    private Usuario usuario;

    //PERSONA
    private Long idPersona;
    private Persona persona;

    //ADMINISTRADOR
    private Long idAdministrador;
    private Administrador administrador;

    //ADMINISTRATIVO
    private Long idAdministrativo;
    private Administrativo administrativo;

    //ESTUDIANTE
    private Long idEstudiante;
    private Estudiante estudiante;

    //DOCENTE
    private Long idDocente;
    private Docente docente;
}