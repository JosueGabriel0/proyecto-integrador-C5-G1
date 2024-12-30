package upeu.edu.pe.msmatriculas.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import upeu.edu.pe.msmatriculas.dto.*;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "matricula")
public class Matricula {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_matricula")
    private Long idMatricula;

    private Long idNivelEnsenanza;
    @Transient
    private NivelEnsenanza nivelEnsenanza;

    private Long idEstudiante;
    @Transient
    private Estudiante estudiante;

    private Long idCarrera;
    @Transient
    private Carrera carrera;

    private Long idCalendarioAcademico;
    @Transient
    private CalendarioAcademico calendarioAcademico;

    private Long IdPago;
    @Transient
    private Pago pago;

    private Long idRequisito;
    @Transient
    private Requisito requisito;

    private Long idAdministrativo;
    @Transient
    private Administrativo administrativo;

    private Long idDocente;
    @Transient
    private Docente docente;

    @ElementCollection
    @CollectionTable(name = "cursos_Ids", joinColumns = @JoinColumn(name = "matricula_id"))
    @Column(name = "cursosIds")
    private List<Long> cursos = new ArrayList<>();

    // Estado de la matrícula (pendiente, pagado, completado, cancelado)
    @Enumerated(EnumType.STRING)
    @Column(name = "estado", nullable = false)
    private EstadoMatricula estado;

    // Fecha y hora de la matrícula
    @Column(name = "fecha_matricula", nullable = false)
    private LocalDateTime fechaMatricula;

    // Observaciones o notas adicionales sobre la matrícula
    @Column(name = "observaciones")
    private String observaciones;
}