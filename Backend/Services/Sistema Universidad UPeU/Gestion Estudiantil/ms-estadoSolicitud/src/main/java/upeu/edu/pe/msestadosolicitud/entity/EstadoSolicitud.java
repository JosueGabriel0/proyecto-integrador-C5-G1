package upeu.edu.pe.msestadosolicitud.entity;

import jakarta.persistence.*;
import lombok.Data;

import java.util.Date;
import java.util.List;
@Entity
@Data
public class EstadoSolicitud {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long idEstadoSolicitud;

    @Column(nullable = false)
    private Long postulanteId;

    @Column(nullable = false, length = 50)
    private String tipoSolicitud;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 20)
    private Estado estadoActual;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(nullable = false)
    private Date fechaCreacion;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(nullable = false)
    private Date fechaUltimaActualizacion;

    @Column(length = 255)
    private String motivoRechazo;

    @Column(length = 255)
    private String comentariosAdicionales;
}
