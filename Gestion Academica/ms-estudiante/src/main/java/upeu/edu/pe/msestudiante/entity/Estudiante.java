package upeu.edu.pe.msestudiante.entity;

import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Data

public class Estudiante {
    @Id
    private long idEstudiante;

    private long personaId;
    private String matricula;
    private String carrera;
    private int cicloActual;
    private double promedioGeneral;
    private LocalDate fechaIngreso;
    private String estado;
    private String tipoEstudiante;
    private String beca;

    @ElementCollection
    @CollectionTable(name = "asignaturas_matriculas", joinColumns = @JoinColumn(name = "docente_id"))
    @Column(name = "asignaturas")
    private List<String> asignaturasMatriculadas = new ArrayList<String>();

    private String historialAcademico;
    private String horario;
    private String consejeroAcademico;
    private String fechaGraduacion;

    @ElementCollection
    @CollectionTable(name = "practicas_realizadas", joinColumns = @JoinColumn(name = "docente_id"))
    @Column(name = "practicas")
    private List<String> practicasRealizadas = new ArrayList<String>();

    private LocalDateTime fechaCreacion;
    private LocalDateTime fechaModificacion;

    @PrePersist
    public void onCreate(){
        fechaCreacion = java.time.LocalDateTime.now();
    }

    @PreUpdate
    public void preUpdate(){
        fechaModificacion = java.time.LocalDateTime.now();
    }
}
