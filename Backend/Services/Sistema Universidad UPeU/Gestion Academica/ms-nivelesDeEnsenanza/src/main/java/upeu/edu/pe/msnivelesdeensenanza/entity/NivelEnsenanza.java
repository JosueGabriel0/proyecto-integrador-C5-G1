package upeu.edu.pe.msnivelesdeensenanza.entity;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Table(name = "nivel_ensenanza")
@Data
public class NivelEnsenanza {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String nombre;
    private String descripcion;

    // Getters y setters
}