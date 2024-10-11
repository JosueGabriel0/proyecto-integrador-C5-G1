package upeu.edu.pe.msusuarios.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Data;

import java.time.LocalDateTime;

@Entity
@Data
public class ResetToken {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String token;
    private LocalDateTime expirationDate;

    // Otros campos y relaciones con la entidad Usuario

    public boolean isExpired() {
        return LocalDateTime.now().isAfter(expirationDate);
    }

    // Getters y Setters
}