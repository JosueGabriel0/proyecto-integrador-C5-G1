package upeu.edu.pe.msnivelesdeensenanza.dto;

import jakarta.persistence.*;
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

    private List<Ciclo> ciclos; // Lista de ciclos en la planificación académica

    // Lista de IDs de eventos importantes del calendario académico (referencia al microservicio de Calendario Académico)
    private List<Long> calendarioAcademicoIds;
}
