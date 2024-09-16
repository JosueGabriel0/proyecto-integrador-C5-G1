package upeu.edu.pe.msevaluador.entity;

import jakarta.persistence.*;
import lombok.Data;

import java.util.Date;
import java.util.List;

@Entity
@Data
public class Evaluador {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idEvaluador;

    @Column(nullable = false, length = 100)
    private String nombre;

    @Column(nullable = false, unique = true, length = 100)
    private String correoElectronico;

    @Column(length = 20)
    private String telefono;

    @Column(length = 50)
    private String especialidad;

    @Enumerated(EnumType.STRING)
    @Column(length = 20)
    private EstadoEvaluador estado;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(nullable = false)
    private Date fechaAsignacion;

    @OneToMany(mappedBy = "evaluador", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Calificacion> calificacionesAsignadas;

    @ElementCollection
    @CollectionTable(name = "comentarios_evaluador", joinColumns = @JoinColumn(name = "evaluador_id"))
    @Column(name = "comentario")
    private List<String> comentarios;

    @OneToMany(mappedBy = "evaluador", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<EvaluacionHistorial> historialEvaluaciones;
}


