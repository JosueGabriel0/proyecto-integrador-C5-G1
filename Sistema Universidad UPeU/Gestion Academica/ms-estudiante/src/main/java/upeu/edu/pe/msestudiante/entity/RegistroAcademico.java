package upeu.edu.pe.msestudiante.entity;

import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDate;

@Entity
@Data

public class RegistroAcademico {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "estudiante_id")
    private Estudiante estudiante;

    private Long cursoId;  // Referencia a otro microservicio

    private String nombreCurso;

    private Double calificacion;

    private LocalDate fechaFinalizacion;
}
