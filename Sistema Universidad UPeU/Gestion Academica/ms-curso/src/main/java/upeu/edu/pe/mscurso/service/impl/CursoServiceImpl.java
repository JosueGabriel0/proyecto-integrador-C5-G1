package upeu.edu.pe.mscurso.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import upeu.edu.pe.mscurso.entity.Curso;
import upeu.edu.pe.mscurso.repository.CursoRepository;
import upeu.edu.pe.mscurso.service.CursoService;

import java.util.List;

@Service

public class CursoServiceImpl implements CursoService {

    @Autowired
    private CursoRepository cursoRepository;

    @Override
    public Curso crearCurso(Curso curso) {
        return cursoRepository.save(curso);
    }

    @Override
    public List<Curso> listarCursos() {
        return cursoRepository.findAll();
    }

    @Override
    public Curso listarCursosPorId(Long id) {
        return cursoRepository.findById(id).get();
    }

    @Override
    public Curso editarCurso(Curso curso) {
        return cursoRepository.save(curso);
    }

    @Override
    public void eliminarCurso(Long id) {
        cursoRepository.deleteById(id);
    }
}
