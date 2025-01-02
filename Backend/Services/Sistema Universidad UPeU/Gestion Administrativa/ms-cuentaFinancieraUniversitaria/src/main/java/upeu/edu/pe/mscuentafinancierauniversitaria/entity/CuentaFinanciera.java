package upeu.edu.pe.mscuentafinancierauniversitaria.entity;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDate;
import java.util.List;

@Entity
@Data
public class CuentaFinanciera {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idCuentaFinanciera;

    private String entidad;
    private String departamento;
    private LocalDate anio;
    private double sumasDebito;
    private double sumasCredito;
    private double saldoFinalDebito;
    private double saldoFinalCredito;
    private double saldoAfavor;

    @OneToMany(mappedBy = "cuentaFinanciera", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonManagedReference
    private List<MovimientoAcademico> movimientosAcademicos;
}
