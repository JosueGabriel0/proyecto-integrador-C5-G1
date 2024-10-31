package upeu.edu.pe.mscarrera.dto;

import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;

@Data
public class PlanificacionAcademica {
    private Long idPlanificacionAcademica;

    private String nombrePlanEstudio;
    private String codigoPlanEstudio;
    private String versionPlanEstudio;
    private LocalDateTime fechaCreacionPlanificacionAcademica;
    private LocalDateTime fechaModificacionPlanificacionAcademica;

    private EstadoPlanificacion estado; //Activo, Inactivo o En revision

    private String descripcionGeneral;

    // Lista de IDs de cursos programados (referencia al microservicio de Curso)
    private List<Long> cursosProgramadosIds;

    // Lista de IDs de profesores asignados (referencia al microservicio de Profesor)
    private List<Long> profesoresIds;

    // Lista de IDs de eventos importantes del calendario académico (referencia al microservicio de Calendario Académico)
    private List<Long> calendarioAcademicoIds;
}
