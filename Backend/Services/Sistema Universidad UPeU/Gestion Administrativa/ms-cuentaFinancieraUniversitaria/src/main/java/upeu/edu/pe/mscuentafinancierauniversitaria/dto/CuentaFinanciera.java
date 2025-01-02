package upeu.edu.pe.mscuentafinancierauniversitaria.dto;

import lombok.Data;

import java.time.LocalDate;

@Data
public class CuentaFinanciera {
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
