package upeu.edu.pe.msdocente.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import upeu.edu.pe.msdocente.entity.Docente;
import upeu.edu.pe.msdocente.repository.DocenteRepository;
import upeu.edu.pe.msdocente.service.DocenteService;

import java.util.List;

@Service

public class DocenteServiceImpl implements DocenteService {

    @Autowired
    DocenteRepository docenteRepository;

    @Override
    public Docente guardarDocente(Docente docente) {
        return docenteRepository.save(docente);
    }

    @Override
    public List<Docente> listarDocente() {
        return docenteRepository.findAll();
    }

    @Override
    public Docente buscarDocentePorId(Long id) {
        return docenteRepository.findById(id).get();
    }

    @Override
    public Docente editarDocente(Docente docente) {
        return docenteRepository.save(docente);
    }

    @Override
    public void eliminarDocente(Long id) {
        docenteRepository.deleteById(id);
    }
}
