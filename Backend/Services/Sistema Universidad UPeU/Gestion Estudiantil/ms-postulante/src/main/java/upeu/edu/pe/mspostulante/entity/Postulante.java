package upeu.edu.pe.mspostulante.entity;

import jakarta.persistence.*;
import lombok.Data;

import java.util.List;

@Entity
@Data

public class Postulante {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long idPostulante;
    @Enumerated(EnumType.STRING)
    private BecaSolicitada becaSolicitada;
    @Enumerated(EnumType.STRING)
    private TipoIngreso tipoIngreso;
    private double puntajeExamenAdmision;
    private String recomendaciones;

    @ElementCollection
    private List<String> interesExtracurricular;
    @Enumerated(EnumType.STRING)
    private Prioridad prioridad;

}
