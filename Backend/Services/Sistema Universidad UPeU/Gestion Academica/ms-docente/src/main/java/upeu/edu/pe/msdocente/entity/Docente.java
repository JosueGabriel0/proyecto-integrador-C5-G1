package upeu.edu.pe.msdocente.entity;

import jakarta.persistence.*;
import lombok.Data;
import upeu.edu.pe.msdocente.dto.Persona;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Data

public class Docente {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idDocente;

    private String departamento;
    private String tituloAcatemico;
    private String especialidad;

    @ElementCollection
    @CollectionTable(name = "cursos_impartidos", joinColumns = @JoinColumn(name = "docente_id"))
    @Column(name = "cursosImpartidos")
    private List<String> cursosImpartidos;

    // Historial Laboral del Docente
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

    @ElementCollection
    @CollectionTable(name = "cursos_Ids", joinColumns = @JoinColumn(name = "docente_id"))
    @Column(name = "cursosIds")
    private List<Long> cursos = new ArrayList<>();

    private long idPersona;
    @Transient
    private Persona persona;

    private LocalDateTime fechaCreacionDocente;
    private LocalDateTime fechaModificacionDocente;

    @PrePersist
    public void onCreate(){
        fechaCreacionDocente = java.time.LocalDateTime.now();
    }

    @PreUpdate
    public void onUpdate(){
        fechaModificacionDocente = java.time.LocalDateTime.now();
    }

}
