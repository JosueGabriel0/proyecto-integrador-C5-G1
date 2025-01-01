package upeu.edu.pe.msmatriculas.dto;

import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;

@Data
public class CicloDetalle {
    private Long idCicloDetalle;

    private OpcionNivel opcionNivel; // Relación: Uno a Muchos con OpcionNivel

    private Long idCiclo;
    private Ciclo ciclo;

    private int numeroDeGrupos;

    private List<CursoDetalle> cursoDetalles; // Relación: Uno a Muchos con CursoDetalle

    private LocalDateTime fechaCreacionCicloDetalle;
    private LocalDateTime fechaModificacionCicloDetalle;
}
