package upeu.edu.pe.mscuentafinancierauniversitaria.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@Data
public class Voucher {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idVoucher;

    private String nombreBanco;
    private String numeroDeOperacion;
    private LocalDate fechaDeOperacion;
    private BigDecimal importe;
    private String voucherURL;

    @ManyToOne
    @JoinColumn(name = "cuenta_financiera_id")
    @JsonBackReference
    private CuentaFinanciera cuentaFinanciera;

    private LocalDateTime fechaCreacionVoucher;
    private LocalDateTime fechaModificacionVoucher;

    @PrePersist
    public void onCreate(){
        fechaCreacionVoucher = java.time.LocalDateTime.now();
    }

    @PreUpdate
    public void onUpdate(){
        fechaModificacionVoucher = java.time.LocalDateTime.now();
    }
}
