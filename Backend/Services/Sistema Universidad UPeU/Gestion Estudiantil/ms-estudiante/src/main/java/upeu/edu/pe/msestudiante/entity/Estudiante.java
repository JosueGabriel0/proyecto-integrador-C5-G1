package upeu.edu.pe.msestudiante.entity;

import jakarta.persistence.*;
import lombok.Data;
import upeu.edu.pe.msestudiante.dto.Curso;
import upeu.edu.pe.msestudiante.dto.Persona;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Data

public class Estudiante {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long idEstudiante;


    private String matricula;
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

    @ElementCollection
    @CollectionTable(name = "asignaturas_matriculas", joinColumns = @JoinColumn(name = "docente_id"))
    @Column(name = "asignaturas")
    private List<String> asignaturasMatriculadas = new ArrayList<String>();

    @Lob
    private String horario;

    private String consejeroAcademico;
    private String fechaGraduacion;

    @ElementCollection
    @CollectionTable(name = "practicas_realizadas", joinColumns = @JoinColumn(name = "docente_id"))
    @Column(name = "practicas")
    private List<String> practicasRealizadas = new ArrayList<String>();

    // Historial Académico del Estudiante
    @OneToMany(mappedBy = "estudiante", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<RegistroAcademico> historialAcademico;

    private long idCurso;
    @Transient
    private Curso curso;

    private long idPersona;
    @Transient
    private Persona persona;

    private LocalDateTime fechaCreacionEstudiante;
    private LocalDateTime fechaModificacionEstudiante;

    @PrePersist
    public void onCreate(){
        fechaCreacionEstudiante = java.time.LocalDateTime.now();
    }

    @PreUpdate
    public void preUpdate(){
        fechaModificacionEstudiante = java.time.LocalDateTime.now();
    }
}