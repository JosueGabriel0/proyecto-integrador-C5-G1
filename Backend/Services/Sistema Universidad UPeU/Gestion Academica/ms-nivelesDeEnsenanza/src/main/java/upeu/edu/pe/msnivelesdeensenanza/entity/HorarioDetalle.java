package upeu.edu.pe.msnivelesdeensenanza.entity;

import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDateTime;
import java.time.LocalTime;

@Entity
@Table(name = "detalle_horario")
@Data
public class HorarioDetalle {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idHorarioDetalle;

    // Relación con Horario
    @ManyToOne
    @JoinColumn(name = "horario_id")
    private Horario horario;

    // Día específico
    private String dia; // Ejemplo: "Lunes", "Miércoles"

    // Hora de inicio y fin de ese día
    private LocalTime horaInicio;
    private LocalTime horaFin;

    private LocalDateTime fechaCreacionHorario;
    private LocalDateTime fechaModificacionHorario;

    @PrePersist
    public void onCreate(){
        fechaCreacionHorario = java.time.LocalDateTime.now();
    }

    @PreUpdate
    public void onUpdate(){
        fechaModificacionHorario = java.time.LocalDateTime.now();
    }
}