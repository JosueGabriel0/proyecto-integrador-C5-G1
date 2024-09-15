package upeu.edu.pe.mspersona.dto;

import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import lombok.Data;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Data

public class Estudiante {
    private long idEstudiante;

    private long idPersona;
    private String matricula;
    private String carrera;
    private int cicloActual;
    private double promedioGeneral;
    private LocalDate fechaIngreso;

    @Enumerated(EnumType.STRING)
    private EstadoEstudiante estado;

    private String tipoEstudiante;
    private String beca;
    private String numeroMatricula;

    //Referencia a otro microservicio
    private Long carreraId;

    private List<String> asignaturasMatriculadas = new ArrayList<>();
    private String horario;
    private String consejeroAcademico;
    private String fechaGraduacion;
    private List<String> practicasRealizadas = new ArrayList<>();

    // Historial Académico del Estudiante
    private List<RegistroAcademico> historialAcademico;

    private LocalDateTime fechaCreacion;
    private LocalDateTime fechaModificacion;
}
