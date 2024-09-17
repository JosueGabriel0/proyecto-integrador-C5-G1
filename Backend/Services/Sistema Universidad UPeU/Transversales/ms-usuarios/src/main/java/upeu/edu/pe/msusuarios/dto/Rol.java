package upeu.edu.pe.msusuarios.dto;

import jakarta.persistence.Column;
import lombok.Data;

@Data
public class Rol {
    private Long id;

    @Column(nullable = false, unique = true)
    private String nombreRol;

    @Column(length = 255)
    private String description;
}
