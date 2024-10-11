package upeu.edu.pe.msusuarios.entity;

import jakarta.persistence.*;
import lombok.Data;
import upeu.edu.pe.msusuarios.dto.Rol;

import java.time.Instant;
import java.time.LocalDateTime;
import java.util.List;

@Entity
@Data
public class Usuario {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idUsuario;

    private String username; // Nombre de usuario único
    private String password; // Contraseña del usuario (se debe cifrar)
    private String email; // Email asociado al usuario
    private boolean enabled; // Indica si el usuario está activo o no

    private long idRol;
    @Transient
    private Rol rol;

    private String resetToken; // Asegúrate de que esta propiedad exista

    private LocalDateTime ultimoLogin; // Última fecha de acceso del usuario

    private String tokenRecuperacion; // Token para la recuperación de contraseña
    private Instant tokenRecuperacionExpiracion; // Fecha de expiración del token de recuperación

    private LocalDateTime fechaCreacionUsuario;
    private LocalDateTime fechaModificacionUsuario;

    @PrePersist
    public void onCreate(){
        fechaCreacionUsuario = java.time.LocalDateTime.now();
    }

    @PreUpdate
    public void onUpdate(){
        fechaModificacionUsuario = java.time.LocalDateTime.now();
    }
}