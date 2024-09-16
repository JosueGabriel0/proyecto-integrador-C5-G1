package upeu.edu.pe.msusuarios.service;


import upeu.edu.pe.msusuarios.entity.Usuario;

import java.util.List;

public interface UsuarioService {

    public Usuario guardarUsuario(Usuario Usuario);

    public List<Usuario> listarUsuario();

    public Usuario buscarUsuarioPorId(Long id);

    public Usuario editarUsuario(Usuario Usuario);

    public void eliminarUsuario(Long id);
}
