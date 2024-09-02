package upeu.edu.pe.msplanificacionacademica.entity;

import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDate;
import java.util.List;

@Entity
@Data
public class PlanificacionAcademica {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long idPlanificacionAcademica;

    private String nombrePlanEstudio;
    private String codigoPlanEstudio;
    private String versionPlanEstudio;
    private LocalDate fechaCreacion;
    private LocalDate fechaModificacion;

    @Enumerated(EnumType.STRING)
    private EstadoPlanificacion estado; //Activo, Inactivo o En revision

    @Lob
    private String descripcionGeneral;

    // Lista de IDs de cursos programados (referencia al microservicio de Curso)
    @ElementCollection
    @CollectionTable(name = "planificacion_cursos", joinColumns = @JoinColumn(name = "planificacion_academica_id"))
    @Column(name = "curso_programado_id")
    private List<Long> cursosProgramadosIds;

    // Lista de IDs de profesores asignados (referencia al microservicio de Profesor)
    @ElementCollection
    @CollectionTable(name = "planificacion_profesores", joinColumns = @JoinColumn(name = "planificacion_academica_id"))
    @Column(name = "profesor_id")
    private List<Long> profesoresIds;

    // Lista de IDs de eventos importantes del calendario académico (referencia al microservicio de Calendario Académico)
    @ElementCollection
    @CollectionTable(name = "planificacion_calendario", joinColumns = @JoinColumn(name = "planificacion_academica_id"))
    @Column(name = "evento_calendario_id")
    private List<Long> calendarioAcademicoIds;

    @OneToMany(mappedBy = "planificacionAcademica", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<FechaImportante> fechasImportantes;
}
