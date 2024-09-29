package upeu.edu.pe.msadministrador.service;

import upeu.edu.pe.msadministrador.entity.Administrador;

import java.util.List;

public interface AdministradorService {

    public Administrador guardarAdministrador(Administrador administrador);

    public List<Administrador> listarAdministrador();

    public Administrador buscarAdministradorPorId(Long id);

    public Administrador editarAdministrador(Administrador administrador);

    public void eliminarAdministrador(Long id);
}
