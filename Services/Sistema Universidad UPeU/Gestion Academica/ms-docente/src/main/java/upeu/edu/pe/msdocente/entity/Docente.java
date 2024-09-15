package upeu.edu.pe.msdocente.entity;

import jakarta.persistence.*;
import lombok.Data;
import upeu.edu.pe.msdocente.dto.Curso;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Data

public class Docente {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long idDocente;

    private long idPersona;
    private String departamento;
    private String tituloAcatemico;
    private String especialidad;



    @Enumerated(EnumType.STRING)
    private EstadoLaboral estadoLaboral;

    @Enumerated(EnumType.STRING)
    private TipoDocente tipoDocente;

    private LocalDate fechaContratacion;
    private String tipoContrato;
    private String salario;
    private String horario;

    private long cursoId;
    @Transient
    private Curso curso;

    private LocalDateTime fechaCreacion;
    private LocalDateTime fechaModificacion;

    @PrePersist
    public void onCreate(){
        fechaCreacion = java.time.LocalDateTime.now();
    }

    @PreUpdate
    public void onUpdate(){
        fechaModificacion = java.time.LocalDateTime.now();
    }

}
