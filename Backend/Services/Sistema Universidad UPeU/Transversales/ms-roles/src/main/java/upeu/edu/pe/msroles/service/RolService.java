package upeu.edu.pe.msroles.service;

import upeu.edu.pe.msroles.entity.Rol;

import java.util.List;

public interface RolService {

    public Rol guardarRol(Rol rol);

    public List<Rol> listarRol();

    public Rol buscarRolPorId(Long id);

    public Rol editarRol(Rol rol);

    public void eliminarRol(Long id);
}
