package upeu.edu.pe.mspagos.dto;

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
