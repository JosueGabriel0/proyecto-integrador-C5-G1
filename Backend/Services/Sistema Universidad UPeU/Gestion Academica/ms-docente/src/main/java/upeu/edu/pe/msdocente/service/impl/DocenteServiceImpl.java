package upeu.edu.pe.msdocente.service.impl;

import feign.FeignException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import upeu.edu.pe.msdocente.dto.Curso;
import upeu.edu.pe.msdocente.dto.Persona;
import upeu.edu.pe.msdocente.entity.Docente;
import upeu.edu.pe.msdocente.exception.ResourceNotFoundException;
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
            try {
                ResponseEntity<Curso> cursoResponse = cursoFeign.listarCursoDtoPorId(docente.getIdCurso());
                if (cursoResponse.getBody() == null) {
                    // Manejar el caso en el que el curso no existe
                    throw new ResourceNotFoundException("Curso con ID " + docente.getIdCurso() + " no existe");
                }
                docente.setCurso(cursoResponse.getBody());
            } catch (FeignException e) {
                // Manejar el error en el servidor de OpenFeign para cursos
                throw new RuntimeException("Error al obtener el curso con ID " + docente.getIdCurso(), e);
            }
        });

        // Recorremos cada docente y asignamos la persona
        docentes.forEach(docente -> {
            try {
                ResponseEntity<Persona> personaResponse = personaFeign.listarPersonaDtoPorId(docente.getIdPersona());
                if (personaResponse.getBody() == null) {
                    // Manejar el caso en el que la persona no existe
                    throw new ResourceNotFoundException("Persona con ID " + docente.getIdPersona() + " no existe");
                }
                docente.setPersona(personaResponse.getBody());
            } catch (FeignException e) {
                // Manejar el error en el servidor de OpenFeign para personas
                throw new RuntimeException("Error al obtener la persona con ID " + docente.getIdPersona(), e);
            }
        });

        return docentes;
    }

    @Override
    public Docente buscarDocentePorId(Long id) {
        Docente docente = docenteRepository.findById(id).get();


        Curso curso = cursoFeign.listarCursoDtoPorId(docente.getIdCurso()).getBody();

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
