package upeu.edu.pe.msmatriculas.dto;

import lombok.Data;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Data
public class Administrativo {
    private Long idAdministrativo;

    // Gestión de pagos y facturación
    private String registroPagos;  // Ej: "Pago de matrículas"
    private Double montoTotalPagos;
    private String fechaUltimoPago = LocalDate.now().format(DateTimeFormatter.ISO_LOCAL_DATE);

    // Gestión de recursos humanos
    private String gestionEmpleados;  // Ej: "Contratación", "Desvinculación"
    private String fechaContratacion = LocalDate.now().format(DateTimeFormatter.ISO_LOCAL_DATE);
    private String cargoEmpleado;  // Ej: "Docente", "Auxiliar administrativo"

    // Seguimiento de solicitudes administrativas
    private String solicitudesPendientes;  // Ej: "Solicitud de material de oficina"
    private String fechaSolicitud = LocalDate.now().format(DateTimeFormatter.ISO_LOCAL_DATE);

    private long idPersona;
    private Persona persona;

    private LocalDateTime fechaCreacionAministrativo;
    private LocalDateTime fechaModificacionAministrativo;
}