package upeu.edu.pe.msinscripciones.dto;

import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
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
}
