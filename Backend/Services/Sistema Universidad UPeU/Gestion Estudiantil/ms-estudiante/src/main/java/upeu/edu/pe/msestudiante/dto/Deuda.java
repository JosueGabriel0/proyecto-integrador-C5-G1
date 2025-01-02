package upeu.edu.pe.msestudiante.dto;

import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDate;

@Data
public class Deuda {

    private Long idDeuda;

    private Long idEstudiante;
    private Estudiante estudiante;

    private BigDecimal montoPendiente;

    private LocalDate fechaLimite;

    private String estadoDeuda;
}