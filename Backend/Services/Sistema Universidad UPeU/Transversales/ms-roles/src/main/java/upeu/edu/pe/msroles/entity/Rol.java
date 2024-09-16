package upeu.edu.pe.msroles.entity;

import jakarta.persistence.*;
import lombok.Data;

import java.util.Set;

@Entity
@Data

public class Rol {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true)
    private String nombreRol;

    @Column(length = 255)
    private String description;
}