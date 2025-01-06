package upeu.edu.pe.mspagos.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.Data;
import upeu.edu.pe.mspagos.dto.Estudiante;

import java.math.BigDecimal;
import java.time.LocalDate;

@Entity
@Data
public class Pago {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idPago;

    private BigDecimal montoTotal;
    private String metodoDePago;
    private String medioDePago;
    private String estado;
    private String descripcion;

    private Long idEstudiante;
    @Transient
    private Estudiante estudiante;

    @OneToOne(mappedBy = "pago")
    @JsonManagedReference
    private Factura factura;

    @OneToOne(mappedBy = "pago")
    @JsonManagedReference
    private Boleta boleta;

    private LocalDate fechaPago;
}