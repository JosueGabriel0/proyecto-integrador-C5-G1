package upeu.edu.pe.msusuarios.entity;

import jakarta.persistence.*;
import lombok.Data;

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

    @ElementCollection(fetch = FetchType.EAGER)
    private List<String> roles; // Roles o permisos del usuario (por ejemplo, ADMIN, USER)

    private LocalDateTime fechaCreacion; // Fecha de creación del usuario
    private LocalDateTime fechaModificacion; // Fecha de la última modificación
    private LocalDateTime ultimoLogin; // Última fecha de acceso del usuario

    private String tokenRecuperacion; // Token para la recuperación de contraseña
    private LocalDateTime tokenRecuperacionExpiracion; // Fecha de expiración del token de recuperación
}