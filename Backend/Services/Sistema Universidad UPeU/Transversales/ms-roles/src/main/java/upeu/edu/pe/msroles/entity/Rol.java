package upeu.edu.pe.msroles.entity;

import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDateTime;

@Entity
@Data

public class Rol {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idRol;

    @Column(nullable = false, unique = true)
    private String nombreRol;

    @Column(length = 255)
    private String description;

    private LocalDateTime fechaCreacionRol;
    private LocalDateTime fechaModificacionRol;

    @PrePersist
    public void preCreate(){
        fechaCreacionRol = java.time.LocalDateTime.now();
    }

    @PreUpdate
    public void preUpdate(){
        fechaModificacionRol = java.time.LocalDateTime.now();
    }
}