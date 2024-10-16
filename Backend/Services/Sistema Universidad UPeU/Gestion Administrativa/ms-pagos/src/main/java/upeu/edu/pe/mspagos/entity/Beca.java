package upeu.edu.pe.mspagos.entity;

import jakarta.persistence.*;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDate;

@Entity
@Data
public class Beca {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String tipoBeca; // Ejemplo: Beca completa, media beca
    private BigDecimal montoDescuento;
    private LocalDate fechaInicio;
    private LocalDate fechaFin;

    private Long idEstudiante;
    @Transient
    private Estudiante estudiante;
    // Getters y setters
}