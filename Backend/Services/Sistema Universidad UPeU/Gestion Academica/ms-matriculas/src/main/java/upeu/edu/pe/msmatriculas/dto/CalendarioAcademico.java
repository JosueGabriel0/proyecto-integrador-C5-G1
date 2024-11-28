package upeu.edu.pe.msmatriculas.dto;

import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;

@Data
public class CalendarioAcademico {
    private Long id;

    private int anioAcademico;

    private String periodo;

    private EstadoCalendario estado;

    private String descripcion;

    private List<FechaImportante> fechasImportantes;

    private LocalDateTime fechaCreacionCalendarioAcademico;
    private LocalDateTime fechaModificacionCalendarioAcademico;
}
