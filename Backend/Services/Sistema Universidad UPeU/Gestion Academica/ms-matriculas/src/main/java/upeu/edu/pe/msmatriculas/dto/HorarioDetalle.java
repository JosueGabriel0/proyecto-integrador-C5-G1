package upeu.edu.pe.msmatriculas.dto;

import lombok.Data;

import java.time.LocalDateTime;
import java.time.LocalTime;

@Data
public class HorarioDetalle {
    private Long idHorarioDetalle;

    // Relación con Horario
    private Horario horario;

    // Día específico
    private String dia; // Ejemplo: "Lunes", "Miércoles"

    // Hora de inicio y fin de ese día
    private LocalTime horaInicio;
    private LocalTime horaFin;

    private LocalDateTime fechaCreacionHorario;
    private LocalDateTime fechaModificacionHorario;
}