package upeu.edu.pe.mscuentafinancierauniversitaria.dto;

import jakarta.persistence.Column;
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
