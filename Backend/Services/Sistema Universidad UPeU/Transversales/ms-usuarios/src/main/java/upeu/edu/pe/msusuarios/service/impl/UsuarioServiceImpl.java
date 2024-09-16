package upeu.edu.pe.msusuarios.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import upeu.edu.pe.msusuarios.entity.Usuario;
import upeu.edu.pe.msusuarios.repository.UsuarioRepository;
import upeu.edu.pe.msusuarios.service.UsuarioService;

import java.util.List;

@Service
public class UsuarioServiceImpl implements UsuarioService {
    @Autowired
    private UsuarioRepository usuarioRepository;

    @Override
    public Usuario guardarUsuario(Usuario Usuario) {
        return usuarioRepository.save(Usuario);
    }

    @Override
    public List<Usuario> listarUsuario(){
        return usuarioRepository.findAll();
    }

    @Override
    public Usuario buscarUsuarioPorId(Long id){
        return usuarioRepository.findById(id).get();
    }

    @Override
    public Usuario editarUsuario(Usuario Usuario) {
        return usuarioRepository.save(Usuario);
    }

    @Override
    public void eliminarUsuario(Long id){
        usuarioRepository.deleteById(id);
    }

}
