package upeu.edu.pe.msmatriculas.dto;

import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;

@Data
public class CursoDetalle {
    private Long idCursoDetalle;

    private CicloDetalle cicloDetalle; // Relación: Uno a Muchos con CicloDetalle

    private Long idCurso;
    private Curso curso;

    private String grupo;

    private List<Long> idsDocentes;

    private Horario horario;

    private LocalDateTime fechaCreacionCursoDetalle;
    private LocalDateTime fechaModificacionCursoDetalle;
}
