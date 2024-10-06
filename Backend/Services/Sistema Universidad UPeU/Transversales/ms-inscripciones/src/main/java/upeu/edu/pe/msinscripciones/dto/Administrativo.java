package upeu.edu.pe.msinscripciones.dto;

import jakarta.persistence.Transient;
import lombok.Data;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Data
public class Administrativo {
    private Long idAdministrativo;

    // Gestión de pagos y facturación
    private String registroPagos;  // Ej: "Pago de matrículas"
    private Double montoTotalPagos;
    private LocalDate fechaUltimoPago;

    // Gestión de recursos humanos
    private String gestionEmpleados;  // Ej: "Contratación", "Desvinculación"
    private LocalDate fechaContratacion;
    private String cargoEmpleado;  // Ej: "Docente", "Auxiliar administrativo"

    // Seguimiento de solicitudes administrativas
    private String solicitudesPendientes;  // Ej: "Solicitud de material de oficina"
    private LocalDate fechaSolicitud;

    private long idPersona;
    @Transient
    private Persona persona;

    private LocalDateTime fechaCreacionAministrativo;
    private LocalDateTime fechaModificacionAministrativo;
}
