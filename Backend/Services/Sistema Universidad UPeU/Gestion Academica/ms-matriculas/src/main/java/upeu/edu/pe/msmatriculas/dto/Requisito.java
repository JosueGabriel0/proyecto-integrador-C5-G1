package upeu.edu.pe.msmatriculas.dto;

import lombok.Data;

@Data
public class Requisito {
    private Long idRequisito;

    private Long idCurso;
    private Curso curso;

    private String cursoPrincipal;

    private String cursoRequisito;

    private String tipoRequisito; // Ejemplo: "Obligatorio", "Opcional"
}