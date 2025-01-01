package upeu.edu.pe.mscuentafinancierauniversitaria.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Data;

import java.time.LocalDate;

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
}
