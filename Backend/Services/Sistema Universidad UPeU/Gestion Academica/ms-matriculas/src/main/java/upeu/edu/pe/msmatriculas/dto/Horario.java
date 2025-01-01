package upeu.edu.pe.msmatriculas.dto;

import lombok.Data;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Data
public class Horario {
    private Long idHorario;

    // Fecha de inicio y fin del curso
    private LocalDate fechaInicio;
    private LocalDate fechaFin;

    private CursoDetalle cursoDetalle;

    // Relación con los detalles de horario
    private List<HorarioDetalle> horarioDetalles;

    private LocalDateTime fechaCreacionHorario;
    private LocalDateTime fechaModificacionHorario;
}