package upeu.edu.pe.mspagos.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.Data;
import upeu.edu.pe.mspagos.dto.Estudiante;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

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

    private LocalDateTime fechaCreacionPago;
    private LocalDateTime fechaModificacionPago;

    @PrePersist
    public void onCreate(){
        fechaCreacionPago = java.time.LocalDateTime.now();
    }

    @PreUpdate
    public void onUpdate(){
        fechaModificacionPago = java.time.LocalDateTime.now();
    }
}