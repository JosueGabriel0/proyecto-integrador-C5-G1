package upeu.edu.pe.msestudiante.dto;

import jakarta.persistence.CollectionTable;
import jakarta.persistence.Column;
import jakarta.persistence.ElementCollection;
import jakarta.persistence.JoinColumn;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Data
public class Curso {
    private long idCurso;

    private String nombre;
    private String codigo;
    private String descripcion;
    private int creditos;
    private String tipo;
    private String nivel;

    private List<String> asignaturas = new ArrayList<>();

    private LocalDateTime fechaCreacion;

    private LocalDateTime fechaModificacion;
}
