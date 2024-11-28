package upeu.edu.pe.mspagos.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Data

public class RegistroAcademico {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "estudiante_id")
    @JsonIgnore
    private Estudiante estudiante;

    @ElementCollection
    @CollectionTable(name = "cursos_Ids", joinColumns = @JoinColumn(name = "estudiante_id"))
    @Column(name = "cursosIds")
    private List<Long> cursos = new ArrayList<>();

    private String nombreCurso;

    private Double calificacion;

    private LocalDate fechaFinalizacion;

    private LocalDateTime fechaCreacion;
    private LocalDateTime fechaModificacion;

    @PrePersist
    public void onCreate(){
        fechaCreacion = LocalDateTime.now();
    }

    @PreUpdate
    public void onUpdate(){
        fechaModificacion = LocalDateTime.now();
    }
}
