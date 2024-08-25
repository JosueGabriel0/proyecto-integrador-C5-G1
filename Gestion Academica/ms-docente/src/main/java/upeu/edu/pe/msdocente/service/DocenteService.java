package upeu.edu.pe.msdocente.service;

import upeu.edu.pe.msdocente.entity.Docente;

import java.util.List;

public interface DocenteService {

    public Docente guardarDocente(Docente docente);

    public List<Docente> listarDocente();

    public Docente buscarDocentePorId(Long id);

    public Docente editarDocente(Docente docente);

    public void eliminarDocente(Long id);
}
