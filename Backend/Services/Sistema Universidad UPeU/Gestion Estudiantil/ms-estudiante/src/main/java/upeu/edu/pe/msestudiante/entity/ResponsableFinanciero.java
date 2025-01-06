package upeu.edu.pe.msestudiante.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDateTime;

@Entity
@Data
public class ResponsableFinanciero {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idResponsableFinanciero;

    private String nombres;
    private String apellido_paterno;
    private String apellido_materno;
    private String correoElectronico;
    private String celular;
    private String direccion;
    private String parentesco;
    private String nacionalidad;
    private String tipoDocumento;
    private String numeroDocumento;

    @OneToOne(mappedBy = "responsableFinanciero")
    @JsonBackReference
    private Estudiante estudiante;

    private LocalDateTime fechaCreacionResponsableFinanciero;
    private LocalDateTime fechaModificacionResponsableFinanciero;

    @PrePersist
    public void onCreate() {
        fechaCreacionResponsableFinanciero = java.time.LocalDateTime.now();
    }

    @PreUpdate
    public void onUpdate() {
        fechaModificacionResponsableFinanciero = java.time.LocalDateTime.now();
    }
}
