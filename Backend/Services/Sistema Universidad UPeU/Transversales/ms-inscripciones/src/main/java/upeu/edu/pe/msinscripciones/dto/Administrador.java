package upeu.edu.pe.msinscripciones.dto;

import jakarta.persistence.Transient;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class Administrador {
    private Long idAdministrador;

    // Registro y control de actividades realizadas por el administrador
    private String actividadReciente;  // Ej: Modificación de roles, permisos
    private LocalDateTime fechaActividad;

    // Monitoreo de los microservicios y sistema
    private String estadoSistema;  // Ej: "Activo", "Mantenimiento"
    private LocalDateTime fechaUltimaRevision;

    // Asignación de roles y permisos
    private String permisosEspeciales;  // Ej: "Gestión de usuarios avanzados"

    // Información relacionada con la gestión de auditoría
    private String logsAcceso;  // Ej: Registros de acceso al sistema

    // Información de los cambios realizados a la configuración global del sistema
    private String cambiosConfiguracion;

    private long idPersona;
    @Transient
    private Persona persona;

    private LocalDateTime fechaCreacionAministrador;
    private LocalDateTime fechaModificacionAministrador;
}
