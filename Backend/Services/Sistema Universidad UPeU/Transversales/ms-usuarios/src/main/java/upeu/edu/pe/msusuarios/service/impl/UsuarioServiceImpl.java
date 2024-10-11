package upeu.edu.pe.msusuarios.service.impl;

import feign.FeignException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder; // Importa PasswordEncoder
import org.springframework.stereotype.Service;
import upeu.edu.pe.msusuarios.dto.Rol;
import upeu.edu.pe.msusuarios.entity.ResetToken;
import upeu.edu.pe.msusuarios.entity.Usuario;
import upeu.edu.pe.msusuarios.exception.ResourceNotFoundException;
import upeu.edu.pe.msusuarios.feign.RolFeign;
import upeu.edu.pe.msusuarios.repository.ResetTokenRepository;
import upeu.edu.pe.msusuarios.repository.UsuarioRepository;
import upeu.edu.pe.msusuarios.service.UsuarioService;

import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class UsuarioServiceImpl implements UsuarioService {
    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private RolFeign rolFeign;

    @Autowired
    private PasswordEncoder passwordEncoder; // Inyección del PasswordEncoder
    @Autowired
    private ResetTokenRepository resetTokenRepository;

    @Override
    public Usuario guardarUsuario(Usuario usuario) {
        // Cifrar la contraseña antes de guardar
        String passwordCifrada = passwordEncoder.encode(usuario.getPassword());
        usuario.setPassword(passwordCifrada);
        return usuarioRepository.save(usuario);
    }

    @Override
    public boolean isValidResetToken(String token) {
        Optional<Usuario> usuarioOpt = usuarioRepository.findByTokenRecuperacion(token);
        if (usuarioOpt.isPresent()) {
            Usuario usuario = usuarioOpt.get();
            // Aquí podrías agregar lógica para verificar si el token ha caducado
            return true; // Si se encuentra el usuario con el token
        }
        return false; // No se encontró un usuario con ese token
    }

    @Override
    public Usuario buscarUsuarioPorUsername(String username) {
        return usuarioRepository.findByUsername(username)
                .orElseThrow(() -> new ResourceNotFoundException("Usuario con username " + username + " no existe"));
    }

    @Override
    public Optional<Usuario> findByEmail(String email) {
        return usuarioRepository.findByEmail(email);
    }

    @Override
    public List<Usuario> listarUsuario() {
        List<Usuario> usuarios = usuarioRepository.findAll();

        // Recorremos cada usuario y asignamos el rol
        usuarios.forEach(usuario -> {
            try {
                ResponseEntity<Rol> rolResponse = rolFeign.listarRolDtoPorId(usuario.getIdRol());
                if (rolResponse.getBody() == null) {
                    // Manejar el caso en el que el rol no existe
                    throw new ResourceNotFoundException("Rol con ID " + usuario.getIdRol() + " no existe");
                }
                usuario.setRol(rolResponse.getBody());
            } catch (FeignException e) {
                // Manejar el error en el servidor de OpenFeign para rol
                throw new RuntimeException("Error al obtener el Rol con ID " + usuario.getIdRol(), e);
            }
        });

        return usuarios;
    }

    @Override
    public Usuario buscarUsuarioPorId(Long id) {
        // Buscar el usuario por ID en el repositorio
        Usuario usuario = usuarioRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Usuario con ID " + id + " no existe"));

        try {
            // Obtener el rol del usuario usando Feign
            ResponseEntity<Rol> rolResponse = rolFeign.listarRolDtoPorId(usuario.getIdRol());
            if (rolResponse.getBody() == null) {
                // Manejar el caso en el que el rol no existe
                throw new ResourceNotFoundException("Rol con ID " + usuario.getIdRol() + " no existe");
            }
            usuario.setRol(rolResponse.getBody());
        } catch (FeignException e) {
            // Manejar el error en el servidor de OpenFeign para rol
            throw new RuntimeException("Error al obtener el Rol con ID " + usuario.getIdRol(), e);
        }

        return usuario;
    }

    @Override
    public void resetPassword(String token, String newPassword) throws Exception {
        // Buscar al usuario por su token de recuperación
        Optional<Usuario> usuarioOptional = usuarioRepository.findByTokenRecuperacion(token);

        if (!usuarioOptional.isPresent()) {
            throw new Exception("Token inválido.");
        }

        Usuario usuario = usuarioOptional.get();

        // Verificar si el token ha expirado
        if (usuario.getTokenRecuperacionExpiracion().isBefore(Instant.now())) {
            throw new Exception("El token ha expirado.");
        }

        // Cifrar la nueva contraseña
        String passwordCifrada = passwordEncoder.encode(newPassword);
        usuario.setPassword(passwordCifrada);

        // Eliminar el token de recuperación ya que no es necesario después del cambio
        usuario.setTokenRecuperacion(null);
        usuario.setTokenRecuperacionExpiracion(null);

        // Guardar el usuario actualizado
        usuarioRepository.save(usuario);
    }

    @Override
    public List<Usuario> guardarUsuariosBatch(List<Usuario> usuarios) {
        return usuarioRepository.saveAll(usuarios);
    }

    @Override
    public String generarTokenRestablecimiento(Long idUsuario) throws Exception {
        Optional<Usuario> usuarioOptional = usuarioRepository.findById(idUsuario);

        if (usuarioOptional.isPresent()) {
            Usuario usuario = usuarioOptional.get();
            String resetToken = UUID.randomUUID().toString(); // Genera un token único

            usuario.setTokenRecuperacion(resetToken); // Asume que el Usuario tiene un campo tokenRecuperacion
            usuario.setTokenRecuperacionExpiracion(Instant.now().plus(1, ChronoUnit.HOURS)); // Expira en 1 hora
            usuarioRepository.save(usuario);

            return resetToken;
        } else {
            throw new Exception("Usuario no encontrado con ID: " + idUsuario);
        }
    }

    @Override
    public boolean validarTokenRestablecimiento(String token) {
        // Lógica para verificar si el token existe y es válido
        // Aquí se asume que tienes una entidad o modelo de datos que maneja los tokens
        Optional<ResetToken> resetToken = resetTokenRepository.findByToken(token);

        // Comprueba si el token existe y no ha expirado
        return resetToken.isPresent() && !resetToken.get().isExpired();
    }

    @Override
    public Usuario editarUsuario(Usuario usuario) {
        // Si se edita la contraseña, cifrarla antes de guardar
        if (usuario.getPassword() != null) {
            String passwordCifrada = passwordEncoder.encode(usuario.getPassword());
            usuario.setPassword(passwordCifrada);
        }
        return usuarioRepository.save(usuario);
    }

    @Override
    public void eliminarUsuario(Long id) {
        usuarioRepository.deleteById(id);
    }
}