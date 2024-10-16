package upeu.edu.pe.mspagos.dto;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class Estudiante {

    private Long idEstudiante;

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


    private List<String> asignaturasMatriculadas = new ArrayList<>();


    private String horario;

    private String consejeroAcademico;
    private String fechaGraduacion;


    private List<String> practicasRealizadas = new ArrayList<String>();

    // Historial Académico del Estudiante

    private List<RegistroAcademico> historialAcademico;

    private long idCurso;

    private Curso curso;

    private long idPersona;

    private Persona persona;

    private LocalDateTime fechaCreacionEstudiante;
    private LocalDateTime fechaModificacionEstudiante;

}
