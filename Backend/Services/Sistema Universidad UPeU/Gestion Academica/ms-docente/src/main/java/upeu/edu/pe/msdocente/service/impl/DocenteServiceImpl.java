package upeu.edu.pe.msdocente.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import upeu.edu.pe.msdocente.dto.Curso;
import upeu.edu.pe.msdocente.dto.Persona;
import upeu.edu.pe.msdocente.entity.Docente;
import upeu.edu.pe.msdocente.feign.CursoFeign;
import upeu.edu.pe.msdocente.feign.PersonaFeign;
import upeu.edu.pe.msdocente.repository.DocenteRepository;
import upeu.edu.pe.msdocente.service.DocenteService;

import java.util.List;

@Service

public class DocenteServiceImpl implements DocenteService {

    @Autowired
    DocenteRepository docenteRepository;

    @Autowired
    private CursoFeign cursoFeign;

    @Autowired
    private PersonaFeign personaFeign;

    @Override
    public Docente guardarDocente(Docente docente) {
        return docenteRepository.save(docente);
    }

    @Override
    public List<Docente> listarDocente() {
        List<Docente> docentes = docenteRepository.findAll();

        // Recorremos cada docente y asignamos el curso y detalles
        docentes.forEach(docente -> {
            Curso curso = cursoFeign.listarCursoDtoPorId(docente.getCursoId()).getBody();
            docente.setCurso(curso);
        });

        // Recorremos cada docente y asignamos la persona
        docentes.forEach(docente -> {
            Persona persona = personaFeign.listarPersonaDtoPorId(docente.getIdPersona()).getBody();
            docente.setPersona(persona);
        });

        return docentes;
    }

    @Override
    public Docente buscarDocentePorId(Long id) {
        Docente docente = docenteRepository.findById(id).get();


        Curso curso = cursoFeign.listarCursoDtoPorId(docente.getCursoId()).getBody();

        Persona persona = personaFeign.listarPersonaDtoPorId(docente.getIdPersona()).getBody();

        docente.setPersona(persona);
        docente.setCurso(curso);

        return docente;
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
