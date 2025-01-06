package upeu.edu.pe.msnivelesdeensenanza.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Entity
@Table(name = "horario")
@Data
public class Horario {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idHorario;

    // Fecha de inicio y fin del curso
    private LocalDate fechaInicio;
    private LocalDate fechaFin;

    @OneToOne(mappedBy = "horario")
    @JsonBackReference
    private CursoDetalle cursoDetalle;

    // Relación con los detalles de horario
    @OneToMany(mappedBy = "horario", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonManagedReference
    private List<HorarioDetalle> horarioDetalles;

    private LocalDateTime fechaCreacionHorario;
    private LocalDateTime fechaModificacionHorario;

    @PrePersist
    public void onCreate() {
        fechaCreacionHorario = java.time.LocalDateTime.now();
    }

    @PreUpdate
    public void onUpdate() {
        fechaModificacionHorario = java.time.LocalDateTime.now();
    }
}