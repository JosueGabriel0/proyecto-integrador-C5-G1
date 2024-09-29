package upeu.edu.pe.msadministrativo.entity;

import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDateTime;

@Entity
@Data
public class Administrativo {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idAdministrativo;

    // Gestión de pagos y facturación
    private String registroPagos;  // Ej: "Pago de matrículas"
    private Double montoTotalPagos;
    private LocalDateTime fechaUltimoPago;

    // Gestión de recursos humanos
    private String gestionEmpleados;  // Ej: "Contratación", "Desvinculación"
    private LocalDateTime fechaContratacion;
    private String cargoEmpleado;  // Ej: "Docente", "Auxiliar administrativo"

    // Seguimiento de solicitudes administrativas
    private String solicitudesPendientes;  // Ej: "Solicitud de material de oficina"
    private LocalDateTime fechaSolicitud;

    private long idPersona;
    @Transient
    private Persona persona;

    private LocalDateTime fechaCreacionAministrativo;
    private LocalDateTime fechaModificacionAministrativo;

    @PrePersist
    public void onCreate(){
        fechaCreacionAministrativo = java.time.LocalDateTime.now();
    }

    @PreUpdate
    public void onUpdate(){
        fechaModificacionAministrativo = java.time.LocalDateTime.now();
    }
}