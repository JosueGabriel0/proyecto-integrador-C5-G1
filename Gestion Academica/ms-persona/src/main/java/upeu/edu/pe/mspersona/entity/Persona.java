package upeu.edu.pe.mspersona.entity;

import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@Data

public class Persona {
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

    private LocalDateTime fechaCreacion;
    private LocalDateTime fechaModificacion;

    @PrePersist
    public void onCreate(){
        fechaCreacion = java.time.LocalDateTime.now();
        fechaRegistro = java.time.LocalDateTime.now();
    }

    @PreUpdate
    public void onUpdate(){
        fechaModificacion = java.time.LocalDateTime.now();
    }

}
