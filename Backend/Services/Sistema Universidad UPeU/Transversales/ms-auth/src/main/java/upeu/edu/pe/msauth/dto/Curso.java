package upeu.edu.pe.msauth.dto;

import lombok.Data;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Data
public class Curso {
    private Long idCurso;

    private String nombre;
    private String codigo;
    private String descripcion;
    private int creditos;
    private String tipo;
    private String nivel;

    private List<String> asignaturas = new ArrayList<>();

    private LocalDateTime fechaCreacionCurso;
    private LocalDateTime fechaModificacionCurso;
}
