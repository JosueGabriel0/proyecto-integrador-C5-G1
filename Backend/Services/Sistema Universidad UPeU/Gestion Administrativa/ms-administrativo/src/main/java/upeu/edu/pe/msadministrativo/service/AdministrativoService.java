package upeu.edu.pe.msadministrativo.service;

import upeu.edu.pe.msadministrativo.entity.Administrativo;

import java.util.List;

public interface AdministrativoService {

    public Administrativo guardarAdministrativo(Administrativo administrativo);

    public List<Administrativo> listarAdministrativo();

    public Administrativo buscarAdministrativoPorId(Long id);

    public Administrativo editarAdministrativo(Administrativo administrativo);

    public void eliminarAdministrativo(Long id);
}
