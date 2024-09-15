package upeu.edu.pe.mspersona.dto;

import lombok.Data;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Data

public class RegistroAcademico {

    private Long id;

    private Estudiante estudiante;

    private Long cursoId;  // Referencia a otro microservicio

    private String nombreCurso;

    private Double calificacion;

    private LocalDate fechaFinalizacion;

    private LocalDateTime fechaCreacion;
    private LocalDateTime fechaModificacion;
}
