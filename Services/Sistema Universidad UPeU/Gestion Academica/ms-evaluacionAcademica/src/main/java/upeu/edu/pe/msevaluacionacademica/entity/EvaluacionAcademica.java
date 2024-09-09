package upeu.edu.pe.msevaluacionacademica.entity;

import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@Data
public class EvaluacionAcademica {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    private long cursoId;

    private long estudianteId;

    private String tipoEvaluacion;  // Ejemplo: "Examen", "Tarea", "Proyecto"
    private LocalDate fechaEvaluacion;
    private double calificacion;
    private String observaciones;

    private LocalDateTime fechaCreacion;
    private LocalDateTime fechaModificacion;

    @PrePersist
    public void onCreate(){
        fechaCreacion = java.time.LocalDateTime.now();
    }

    @PreUpdate
    public void onUpdate(){
        fechaModificacion = java.time.LocalDateTime.now();
    }
}
