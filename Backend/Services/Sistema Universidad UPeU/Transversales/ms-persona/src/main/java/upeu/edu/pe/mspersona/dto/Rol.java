package upeu.edu.pe.mspersona.dto;

import jakarta.persistence.Column;

public class Rol {
    private Long id;

    @Column(nullable = false, unique = true)
    private String nombreRol;

    @Column(length = 255)
    private String description;
}
