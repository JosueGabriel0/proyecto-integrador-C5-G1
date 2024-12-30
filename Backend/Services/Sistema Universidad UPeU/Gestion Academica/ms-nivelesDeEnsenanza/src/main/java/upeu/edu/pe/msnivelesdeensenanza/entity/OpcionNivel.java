package upeu.edu.pe.msnivelesdeensenanza.entity;

import jakarta.persistence.*;
import lombok.Data;
import upeu.edu.pe.msnivelesdeensenanza.dto.Carrera;
import upeu.edu.pe.msnivelesdeensenanza.dto.PlanificacionAcademica;

@Entity
@Table(name = "opcion_nivel")
@Data
public class OpcionNivel {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "nivel_id")
    private NivelEnsenanza nivelEnsenanza;

    private String tipoEstudio;
    private String semestre;
    private String campus;

    private Long idPLanificacionAcademica;
    @Transient
    private PlanificacionAcademica planificacionAcademica;

    private Long idCarrera;
    @Transient
    private Carrera carrera;

    private String modalidad;
    private String estado;

    // Getters y setters
}