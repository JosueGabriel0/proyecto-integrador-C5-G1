package upeu.edu.pe.msrequisitosacademicos.dto;

import jakarta.persistence.Column;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class Rol {
    private Long idRol;

    @Column(nullable = false, unique = true)
    private String nombreRol;

    @Column(length = 255)
    private String description;

    private LocalDateTime fechaCreacionRol;
    private LocalDateTime fechaModificacionRol;
}
