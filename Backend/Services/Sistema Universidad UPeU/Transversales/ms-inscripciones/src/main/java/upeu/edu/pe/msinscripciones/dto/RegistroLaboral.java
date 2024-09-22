package upeu.edu.pe.msinscripciones.dto;

import lombok.Data;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Data
public class RegistroLaboral {
    private Long id;

    private Docente docente;

    private String puesto;
    private String departamento;
    private LocalDate fechaInicio;
    private LocalDate fechaFin;
    private String descripcion;  // Descripción del rol o actividades realizadas
}
