package upeu.edu.pe.msnivelesdeensenanza.entity;

import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDateTime;

@Entity
@Table(name = "nivel_ensenanza")
@Data
public class NivelEnsenanza {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idNivelEnsenanza;

    private String nombre;
    private String descripcion;

    private LocalDateTime fechaCreacionNivelEnsenanza;
    private LocalDateTime fechaModificacionNivelEnsenanza;

    @PrePersist
    public void onCreate(){
        fechaCreacionNivelEnsenanza = java.time.LocalDateTime.now();
    }

    @PreUpdate
    public void onUpdate(){
        fechaModificacionNivelEnsenanza = java.time.LocalDateTime.now();
    }

    // Getters y setters
}