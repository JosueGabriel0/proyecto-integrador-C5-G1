package upeu.edu.pe.mscuentafinancierauniversitaria.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.Data;
import upeu.edu.pe.mscuentafinancierauniversitaria.dto.Pago;

import java.time.LocalDate;

@Entity
@Data
public class MovimientoAcademico {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idMovimientoAcademico;

    private LocalDate fecha;
    private int voucher;
    private String lote;
    private String documento;
    private String movimiento;
    private String descripcion;
    private String debito;
    private String credito;

    private Long idPago;
    @Transient
    private Pago pago;

    @ManyToOne
    @JoinColumn(name = "cuenta_financiera_id")
    @JsonBackReference
    private CuentaFinanciera cuentaFinanciera;
}
