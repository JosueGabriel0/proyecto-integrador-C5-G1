package upeu.edu.pe.msmatriculas.dto;

import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDateTime;

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

    private LocalDateTime fechaCreacionOpcionNivel;
    private LocalDateTime fechaModificacionOpcionNivel;

    // Getters y setters
}