package upeu.edu.pe.msauth.dto;

import javax.persistence.Column;
import java.time.LocalDateTime;

public class Rol {
    private Long idRol;

    @Column(nullable = false, unique = true)
    private String nombreRol;

    @Column(length = 255)
    private String description;

    private LocalDateTime fechaCreacionRol;
    private LocalDateTime fechaModificacionRol;
}
