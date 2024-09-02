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

    @Column(nullable = false)
    private Long cursoId;  // Referencia a otro microservicio

    @Column(nullable = false)
    private String nombreCurso;

    @Column(nullable = false)
    private Double calificacion;

    @Column(nullable = false)
    private LocalDate fechaFinalizacion;
}
