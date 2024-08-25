package upeu.edu.pe.mspersona.entity;

import jakarta.persistence.*;
import lombok.Data;
import org.springframework.cglib.core.Local;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Date;

@Entity
@Data

public class Persona {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    private String nombres;
    private String apellido_paterno;
    private String apellido_materno;
    private String email;
    private String telefono;
    private String direccion;
    private String ciudad;
    private String provincia;
    private String estado;
    private String pais;
    private String categoria;
    private fecha_nacimiento;
    private LocalDateTime fecha_creacion;
    private LocalDateTime fecha_modificacion;

    @PrePersist
    public void onCreate(){
        fecha_creacion = java.time.LocalDateTime.now();
    }

    @PreUpdate
    public void onUpdate(){
        fecha_modificacion = java.time.LocalDateTime.now();
    }

}
