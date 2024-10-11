package upeu.edu.pe.msusuarios.service;


import upeu.edu.pe.msusuarios.entity.Usuario;

import java.util.List;
import java.util.Optional;

public interface UsuarioService {

    public Usuario guardarUsuario(Usuario usuario);

    public List<Usuario> listarUsuario();

    public Usuario buscarUsuarioPorId(Long id);

    public Usuario buscarUsuarioPorUsername(String username);

    public Optional<Usuario> findByEmail(String email);

    public List<Usuario> guardarUsuariosBatch(List<Usuario> usuarios);

    public void resetPassword(String token, String newPassword) throws Exception;

    public String generarTokenRestablecimiento(Long idUsuario) throws Exception;

    public boolean validarTokenRestablecimiento(String token);

    public boolean isValidResetToken(String token);

    public Usuario editarUsuario(Usuario usuario);

    public void eliminarUsuario(Long id);
}
