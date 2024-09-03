package upeu.edu.pe.msdocente.entity;

import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDate;

@Entity
@Data

public class RegistroLaboral {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "estudiante_id")
    private Docente docente;

    private String puesto;
    private String departamento;
    private LocalDate fechaInicio;
    private LocalDate fechaFin;
    private String descripcion;  // Descripción del rol o actividades realizadas
}
