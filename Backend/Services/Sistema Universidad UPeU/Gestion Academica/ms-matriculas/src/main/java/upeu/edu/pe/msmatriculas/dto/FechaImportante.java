package upeu.edu.pe.msmatriculas.dto;

import lombok.Data;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Data
public class FechaImportante {
    private Long idFechaImportante;

    private String descripcion;

    private LocalDate fecha;

    private CalendarioAcademico calendarioAcademico;

    private LocalDateTime fechaCreacionFechaImportante;
    private LocalDateTime fechaModificacionFechaImportante;
}
