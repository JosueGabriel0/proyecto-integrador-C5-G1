package upeu.edu.pe.msestudiante.entity;

import jakarta.persistence.*;
import lombok.Data;
import upeu.edu.pe.msestudiante.dto.CuentaFinanciera;
import upeu.edu.pe.msestudiante.dto.MovimientoAcademico;
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
    private Long idEstudiante;


    private String matricula;
    private int cicloActual;
    private double promedioGeneral;
    private LocalDate fechaIngreso;

    @Enumerated(EnumType.STRING)
    private EstadoEstudiante estado;

    private String tipoEstudiante;
    private String beca;
    private String numeroMatricula;

    private Long idCuentaFinanciera;
    @Transient
    private CuentaFinanciera cuentaFinanciera;

    private Long idMovimientoAcademico;
    @Transient
    private MovimientoAcademico movimientoAcademico;

    @ElementCollection
    @CollectionTable(name = "carreras_ingresadas", joinColumns = @JoinColumn(name = "estudiante_id"))
    @Column(name = "carreras")
    private List<String> carrerasIngresadas = new ArrayList<String>();

    @ElementCollection
    @CollectionTable(name = "asignaturas_matriculadas", joinColumns = @JoinColumn(name = "estudiante_id"))
    @Column(name = "asignaturas")
    private List<String> asignaturasMatriculadas = new ArrayList<String>();

    @Lob
    private String horario;

    private String consejeroAcademico;
    private LocalDate fechaGraduacion;

    @ElementCollection
    @CollectionTable(name = "practicas_realizadas", joinColumns = @JoinColumn(name = "estudiante_id"))
    @Column(name = "practicas")
    private List<String> practicasRealizadas = new ArrayList<String>();

    // Historial Académico del Estudiante
    @OneToMany(mappedBy = "estudiante", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private List<RegistroAcademico> historialAcademico;

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