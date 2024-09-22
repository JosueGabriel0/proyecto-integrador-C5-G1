package upeu.edu.pe.msinscripciones.dto;

import jakarta.persistence.*;
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
    private int cicloActual;
    private double promedioGeneral;
    private LocalDate fechaIngreso;

    private EstadoEstudiante estado;

    private String tipoEstudiante;
    private String beca;
    private String numeroMatricula;

    //Referencia a otro microservicio
    private Long carreraId;

    private List<String> asignaturasMatriculadas = new ArrayList<String>();

    private String horario;

    private String consejeroAcademico;
    private String fechaGraduacion;

    private List<String> practicasRealizadas = new ArrayList<String>();

    private List<RegistroAcademico> historialAcademico;

    private long idCurso;

    @Transient
    private Persona persona;

    @Transient
    private Curso curso;

}
