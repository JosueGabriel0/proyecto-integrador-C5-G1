package upeu.edu.pe.msestudiante.dto;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class Rol {
    private Long idRol;

    private String nombreRol;

    private String description;

    private LocalDateTime fechaCreacionRol;
    private LocalDateTime fechaModificacionRol;
}
