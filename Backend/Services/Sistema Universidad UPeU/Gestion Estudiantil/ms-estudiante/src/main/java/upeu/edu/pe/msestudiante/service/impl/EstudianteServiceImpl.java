package upeu.edu.pe.msestudiante.service.impl;

import feign.FeignException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import upeu.edu.pe.msestudiante.dto.Curso;
import upeu.edu.pe.msestudiante.dto.Persona;
import upeu.edu.pe.msestudiante.entity.Estudiante;
import upeu.edu.pe.msestudiante.exception.ResourceNotFoundException;
import upeu.edu.pe.msestudiante.feign.CursoFeign;
import upeu.edu.pe.msestudiante.feign.PersonaFeign;
import upeu.edu.pe.msestudiante.repository.EstudianteRepository;
import upeu.edu.pe.msestudiante.service.EstudianteService;

import java.util.List;

@Service

public class EstudianteServiceImpl implements EstudianteService {

    @Autowired
    EstudianteRepository EstudianteRepository;

    @Autowired
    private CursoFeign cursoFeign;

    @Autowired
    private PersonaFeign personaFeign;

    @Override
    public Estudiante guardarEstudiante(Estudiante Estudiante) {
        return EstudianteRepository.save(Estudiante);
    }

    @Override
    public List<Estudiante> listarEstudiante() {
        List<Estudiante> Estudiantes = EstudianteRepository.findAll();

        // Recorremos cada Estudiante y asignamos el curso y detalles
        Estudiantes.forEach(Estudiante -> {
            try {
                ResponseEntity<Curso> cursoResponse = cursoFeign.listarCursoDtoPorId(Estudiante.getIdCurso());
                if (cursoResponse.getBody() == null) {
                    // Manejar el caso en el que el curso no existe
                    throw new ResourceNotFoundException("Curso con ID " + Estudiante.getIdCurso() + " no existe");
                }
                Estudiante.setCurso(cursoResponse.getBody());
            } catch (FeignException e) {
                // Manejar el error en el servidor de OpenFeign para cursos
                throw new RuntimeException("Error al obtener el curso con ID " + Estudiante.getIdCurso(), e);
            }
        });

        // Recorremos cada Estudiante y asignamos la persona
        Estudiantes.forEach(Estudiante -> {
            try {
                ResponseEntity<Persona> personaResponse = personaFeign.listarPersonaDtoPorId(Estudiante.getIdPersona());
                if (personaResponse.getBody() == null) {
                    // Manejar el caso en el que la persona no existe
                    throw new ResourceNotFoundException("Persona con ID " + Estudiante.getIdPersona() + " no existe");
                }
                Estudiante.setPersona(personaResponse.getBody());
            } catch (FeignException e) {
                // Manejar el error en el servidor de OpenFeign para personas
                throw new RuntimeException("Error al obtener la persona con ID " + Estudiante.getIdPersona(), e);
            }
        });

        return Estudiantes;
    }

    @Override
    public Estudiante buscarEstudiantePorId(Long id) {
        Estudiante Estudiante = EstudianteRepository.findById(id).get();


        Curso curso = cursoFeign.listarCursoDtoPorId(Estudiante.getIdCurso()).getBody();

        Persona persona = personaFeign.listarPersonaDtoPorId(Estudiante.getIdPersona()).getBody();

        Estudiante.setPersona(persona);
        Estudiante.setCurso(curso);

        return Estudiante;
    }

    @Override
    public Estudiante editarEstudiante(Estudiante Estudiante) {
        return EstudianteRepository.save(Estudiante);
    }

    @Override
    public void eliminarEstudiante(Long id) {
        EstudianteRepository.deleteById(id);
    }
}
