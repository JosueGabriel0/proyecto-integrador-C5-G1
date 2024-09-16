package upeu.edu.pe.msdocumentopostulante.entity;

import jakarta.persistence.*;
import lombok.Data;

import java.util.Date;

@Entity
@Data
public class DocumentoPostulante {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idDocumentoPostulante;

    @Column(nullable = false)
    private Long postulanteId;

    @Column(nullable = false, length = 50)
    private String tipoDocumento;

    @Column(nullable = false, length = 100)
    private String nombreDocumento;

    @Column(nullable = false, length = 255)
    private String rutaDocumento;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(nullable = false)
    private Date fechaSubida;

    @Enumerated(EnumType.STRING)
    @Column(length = 20)
    private EstadoDocumento estado;

    @Column(length = 255)
    private String comentariosValidacion;

    @Temporal(TemporalType.TIMESTAMP)
    private Date fechaValidacion;
}

