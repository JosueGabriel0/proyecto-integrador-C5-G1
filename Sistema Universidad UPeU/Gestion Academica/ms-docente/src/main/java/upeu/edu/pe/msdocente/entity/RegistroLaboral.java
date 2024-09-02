package upeu.edu.pe.msdocente.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Data;

import java.time.LocalDate;

@Entity
@Data

public class RegistroLaboral {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String puesto;
    private String departamento;
    private LocalDate fechaInicio;
    private LocalDate fechaFin;
    private String descripcion;  // Descripción del rol o actividades realizadas
}
