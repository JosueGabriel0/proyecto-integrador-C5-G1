package upeu.edu.pe.msnivelesdeensenanza.dto;

import lombok.Data;

import java.util.List;

@Data
public class Ciclo {

    private Long idCiclo;

    private int numeroCiclo; // Por ejemplo, 1, 2, ..., 10

    private EstadoCiclo estado; // Activo o Cerrado (opcional)

    private PlanificacionAcademica planificacionAcademica;

    private List<Long> cursosIds;

    // Lista de docentes asociados al ciclo
    private List<Long> docentesIds;
}