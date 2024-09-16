package upeu.edu.pe.msevaluador.entity;

import jakarta.persistence.*;
import lombok.Data;

import java.util.Date;
@Data
@Entity
class EvaluacionHistorial {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "evaluador_id")
    private Evaluador evaluador;

    @Column(nullable = false)
    private Long postulanteId;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(nullable = false)
    private Date fechaEvaluacion;

    @Column(length = 50)
    private String resultado;

    // Getters y Setters
    // ...
}
