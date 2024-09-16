package upeu.edu.pe.msresultadoevaluacion.entity;

import jakarta.persistence.*;
import lombok.Data;

import java.util.Date;

@Data
@Entity

public class ResultadoEvaluacion {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long idResultadoEvaluacion;

    @Column(nullable = false)
    private Long postulanteId;

    @Column(nullable = false)
    private Long evaluadorId;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(nullable = false)
    private Date fechaEvaluacion;

    @Column(nullable = false)
    private Integer puntuacion;

    @Enumerated(EnumType.STRING)
    @Column(length = 20)
    private ResultadoFinal resultadoFinal;

    @Column(length = 255)
    private String comentariosEvaluador;

    @Enumerated(EnumType.STRING)
    @Column(length = 20)
    private EstadoResultado estado;

    @Temporal(TemporalType.TIMESTAMP)
    private Date fechaPublicacion;

}
