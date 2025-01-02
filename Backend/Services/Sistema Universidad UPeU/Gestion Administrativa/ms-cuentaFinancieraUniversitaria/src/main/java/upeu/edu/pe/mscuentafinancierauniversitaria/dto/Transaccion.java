package upeu.edu.pe.mscuentafinancierauniversitaria.dto;

import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDateTime;

@Entity
@Data
public class Transaccion {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "pago_id")
    private Pago pago;

    private String estadoTransaccion; // Completado, En proceso, Fallido, etc.
    private LocalDateTime fechaTransaccion;

    // Getters y setters
}