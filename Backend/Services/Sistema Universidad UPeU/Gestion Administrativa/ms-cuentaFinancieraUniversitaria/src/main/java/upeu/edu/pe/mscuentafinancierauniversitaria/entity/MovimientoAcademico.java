package upeu.edu.pe.mscuentafinancierauniversitaria.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Data;

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

    private Long idBoleta;
    private Long idFactura;
}
