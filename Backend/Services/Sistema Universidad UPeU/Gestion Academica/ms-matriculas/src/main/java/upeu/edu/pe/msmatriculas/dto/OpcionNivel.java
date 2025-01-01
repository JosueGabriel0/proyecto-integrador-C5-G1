package upeu.edu.pe.msmatriculas.dto;

import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;

@Data
public class OpcionNivel {
    private Long idOpcionNivel;

    private NivelEnsenanza nivelEnsenanza;

    private String tipoEstudio;
    private String semestre;
    private String campus;

    private Long idPLanificacionAcademica;
    private PlanificacionAcademica planificacionAcademica;

    private Long idCarrera;
    private Carrera carrera;

    private String modalidad;
    private String estado;

    private List<CicloDetalle> cicloDetalle;

    private double costoDeMatricula;
    private double costoPorCredito;

    private boolean consentimientoInformado;

    private LocalDateTime fechaCreacionOpcionNivel;
    private LocalDateTime fechaModificacionOpcionNivel;

    // Getters y setters
}