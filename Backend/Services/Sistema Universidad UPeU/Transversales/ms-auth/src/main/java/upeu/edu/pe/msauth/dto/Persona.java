package upeu.edu.pe.msauth.dto;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class Persona {
    private Long id;

    private String nombres;
    private String apellido_paterno;
    private String apellido_materno;
    private String fecha_nacimiento;
    private String genero;
    private String nacionalidad;
    private String tipoDocumento;
    private String numeroDocumento;
    private String direccion;
    private String ciudad;
    private String departamento;
    private String pais;
    private String provincia;
    private String telefono;
    private String email;
    private String estadoCivil;
    private String fotoPerfil;
    private String tipoSangre;
    private String responsableFinanciero;
    private String contactoEmergenciaNombre;
    private String contactoEmergenciaTelefono;
    private String contactoEmergenciaEmail;
    private String contactoEmergenciaDireccion;
    private String contactoEmergenciaCiudad;
    private String contactoEmergenciaParentesco;
    private LocalDateTime fechaRegistro;

    private long idUsuario;
    private Usuario usuario;

    private LocalDateTime fechaCreacionPersona;
    private LocalDateTime fechaModificacionPersona;
}
