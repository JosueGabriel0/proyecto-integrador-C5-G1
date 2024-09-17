package upeu.edu.pe.msusuarios.entity;

import jakarta.persistence.*;
import lombok.Data;
import upeu.edu.pe.msusuarios.dto.Rol;

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

    private LocalDateTime fechaCreacion; // Fecha de creación del usuario
    private LocalDateTime fechaModificacion; // Fecha de la última modificación
    private LocalDateTime ultimoLogin; // Última fecha de acceso del usuario

    private String tokenRecuperacion; // Token para la recuperación de contraseña
    private LocalDateTime tokenRecuperacionExpiracion; // Fecha de expiración del token de recuperación
}