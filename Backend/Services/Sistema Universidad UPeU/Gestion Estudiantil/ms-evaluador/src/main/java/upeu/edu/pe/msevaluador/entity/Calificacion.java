package upeu.edu.pe.msevaluador.entity;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Data
class Calificacion {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "evaluador_id")
    private Evaluador evaluador;

    @Column(nullable = false)
    private Long postulanteId;

    @Column(nullable = false)
    private Integer puntuacion;

    @Column(length = 255)
    private String comentarios;

}