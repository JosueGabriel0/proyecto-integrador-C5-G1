package upeu.edu.pe.msinscripciones.entity;

import jakarta.persistence.*;
import lombok.Data;
import upeu.edu.pe.msinscripciones.dto.Usuario;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@Data

public class Inscripcion {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    private String nombres;
    private String apellido_paterno;
    private String apellido_materno;
    private LocalDate fecha_nacimiento;
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
    private String contactoEmergenciaNombre;
    private String contactoEmergenciaTelefono;
    private String contactoEmergenciaEmail;
    private String contactoEmergenciaDireccion;
    private String contactoEmergenciaCiudad;
    private String contactoEmergenciaParentesco;
    private LocalDateTime fechaRegistro;

    private long idUsuario;

    @Transient
    private Usuario usuario;

    private LocalDateTime fechaCreacionInscripcion;
    private LocalDateTime fechaModificacionInscripcion;

    @PrePersist
    public void onCreate(){
        fechaCreacionInscripcion = java.time.LocalDateTime.now();
        fechaRegistro = java.time.LocalDateTime.now();
    }

    @PreUpdate
    public void onUpdate(){
        fechaModificacionInscripcion = java.time.LocalDateTime.now();
    }

}
