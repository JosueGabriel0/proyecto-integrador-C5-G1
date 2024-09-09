package upeu.edu.pe.msdocente.entity;

import jakarta.persistence.*;
import lombok.Data;

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

    @ElementCollection
    private List<String> cursosImpartidos;

    // Historial Académico del Estudiante
    @OneToMany(mappedBy = "docente", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<RegistroLaboral> historialLaboral;

    @Enumerated(EnumType.STRING)
    private EstadoLaboral estadoLaboral;

    @Enumerated(EnumType.STRING)
    private TipoDocente tipoDocente;

    private LocalDate fechaContratacion;
    private String tipoContrato;
    private String salario;
    private String horario;

    @ElementCollection
    @CollectionTable(name = "publicaciones_academicas", joinColumns = @JoinColumn(name = "docente_id"))
    @Column(name = "publicacion")
    private List<String> publicacionesAcademicas = new ArrayList<String>();

    @ElementCollection
    @CollectionTable(name = "proyectos_investigacion", joinColumns = @JoinColumn(name = "docente_id"))
    @Column(name = "proyectos")
    private List<String> proyectosInvestigacion = new ArrayList<String>();

    private String numeroOficina;
    private String extensionTelefonica;
    private String supervisor;

    @ElementCollection
    @CollectionTable(name = "logros_academicos", joinColumns = @JoinColumn(name = "docente_id"))
    @Column(name = "logros")
    private List<String> logrosAcademicos = new ArrayList<String>();

    private LocalDate fechaJubilacion;

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
