package upeu.edu.pe.msadministrador.entity;

import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDateTime;

@Entity
@Data
public class Administrador {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
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

    @PrePersist
    public void onCreate(){
        fechaCreacionAministrador = java.time.LocalDateTime.now();
    }

    @PreUpdate
    public void onUpdate(){
        fechaModificacionAministrador = java.time.LocalDateTime.now();
    }
}