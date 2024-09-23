package upeu.edu.pe.msadministrador.entity;

import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDate;

@Entity
@Data
public class Administrador {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long idAdministrador;

    private Integer nivelAcceso;

    private String estado;

    private long idPersona;

    private LocalDate fechaCreacionAdministrador;
    private LocalDate fechaModificacionAdministrador;

}
