package upeu.edu.pe.msmatriculas.dto;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class NivelEnsenanza {
    private Long idNivelEnsenanza;

    private String nombre;
    private String descripcion;

    private LocalDateTime fechaCreacionNivelEnsenanza;
    private LocalDateTime fechaModificacionNivelEnsenanza;

    // Getters y setters
}