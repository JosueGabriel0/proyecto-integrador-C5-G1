package upeu.edu.pe.msmatriculas.dto;

import lombok.Data;

import java.time.LocalDateTime;

@Data

public class Curso {
    private Long idCurso;

    private String nombre;
    private String codigo;
    private String descripcion;
    private int creditos;
    private String tipo;
    private String nivel;

    private Long idCarrera;
    private Carrera carrera;

    private Long idDocente;
    private Docente docente;

    private LocalDateTime fechaCreacionCurso;
    private LocalDateTime fechaModificacionCurso;
}
