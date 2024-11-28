package upeu.edu.pe.msmatriculas.dto;

import lombok.Data;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Data
public class Docente {
    private Long idDocente;

    private String departamento;
    private String tituloAcatemico;
    private String especialidad;

    private List<String> cursosImpartidos;

    // Historial Laboral del Docente
    private List<RegistroLaboral> historialLaboral;

    private EstadoLaboral estadoLaboral;

    private TipoDocente tipoDocente;

    private LocalDate fechaContratacion;
    private String tipoContrato;
    private String salario;
    private String horario;

    private List<String> publicacionesAcademicas = new ArrayList<String>();

    private List<String> proyectosInvestigacion = new ArrayList<String>();

    private String numeroOficina;
    private String extensionTelefonica;
    private String supervisor;

    private List<String> logrosAcademicos = new ArrayList<String>();

    private LocalDate fechaJubilacion;

    private List<Long> cursos = new ArrayList<>();

    private long idPersona;

    private Persona persona;

    private LocalDateTime fechaCreacionDocente;
    private LocalDateTime fechaModificacionDocente;
}
