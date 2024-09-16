package upeu.edu.pe.mscalendarioacademico.entity;

import jakarta.persistence.*;
import lombok.Data;

import java.util.List;

@Entity
@Data

public class CalendarioAcademico {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private int anioAcademico;

    private String periodo;

    @Enumerated(EnumType.STRING)
    private EstadoCalendario estado;

    @Lob
    private String descripcion;

    @OneToMany(mappedBy = "calendarioAcademico", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<FechaImportante> fechasImportantes;
}