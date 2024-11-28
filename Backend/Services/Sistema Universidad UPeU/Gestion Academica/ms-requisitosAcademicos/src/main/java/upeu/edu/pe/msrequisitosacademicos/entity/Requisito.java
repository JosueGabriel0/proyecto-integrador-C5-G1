package upeu.edu.pe.msrequisitosacademicos.entity;

import jakarta.persistence.*;
import lombok.Data;
import upeu.edu.pe.msrequisitosacademicos.dto.Curso;

@Entity
@Table(name = "requisitos")
@Data
public class Requisito {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idRequisito;

    private Long idCurso;
    @Transient
    private Curso curso;

    private String cursoPrincipal;

    private String cursoRequisito;

    @Column(nullable = false, length = 50)
    private String tipoRequisito; // Ejemplo: "Obligatorio", "Opcional"
}