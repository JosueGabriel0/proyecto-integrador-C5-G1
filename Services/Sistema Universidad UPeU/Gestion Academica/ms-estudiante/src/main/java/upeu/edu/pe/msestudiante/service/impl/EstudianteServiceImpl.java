package upeu.edu.pe.msestudiante.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import upeu.edu.pe.msestudiante.dto.Curso;
import upeu.edu.pe.msestudiante.dto.Persona;
import upeu.edu.pe.msestudiante.entity.Estudiante;
import upeu.edu.pe.msestudiante.feign.CursoFeign;
import upeu.edu.pe.msestudiante.feign.PersonaFeign;
import upeu.edu.pe.msestudiante.repository.EstudianteRepository;
import upeu.edu.pe.msestudiante.service.EstudianteService;

import java.util.List;

@Service

public class EstudianteServiceImpl implements EstudianteService {

    @Autowired
    private EstudianteRepository estudianteRepository;

    @Autowired
    private PersonaFeign personaFeign;

    @Autowired
    private CursoFeign cursoFeign;

    @Override
    public Estudiante guardarEstudiante(Estudiante estudiante) {
        return estudianteRepository.save(estudiante);
    }

    @Override
    public List<Estudiante> listarEstudiantes() {
        List<Estudiante> estudiantes = estudianteRepository.findAll();

        // Recorremos cada docente y asignamos el curso y detalles
        estudiantes.forEach(estudiante -> {
            Persona persona = personaFeign.listarPersonaDtoPorId(estudiante.getIdPersona()).getBody();
            estudiante.setPersona(persona);
        });

        // Recorremos cada docente y asignamos el curso y detalles
        estudiantes.forEach(estudiante -> {
            Curso curso = cursoFeign.listarCursoDtoPorId(estudiante.getIdPersona()).getBody();
            estudiante.setCurso(curso);
        });

        return estudiantes;
    }

    @Override
    public Estudiante buscarEstudiantePorId(Long id) {
        Estudiante estudiante = estudianteRepository.findById(id).get();

        Curso curso = cursoFeign.listarCursoDtoPorId(estudiante.getIdCurso()).getBody();

        Persona persona = personaFeign.listarPersonaDtoPorId(estudiante.getIdPersona()).getBody();

        estudiante.setPersona(persona);
        estudiante.setCurso(curso);

        return estudiante;
    }

    @Override
    public Estudiante editarEstudiante(Estudiante estudiante) {
        return estudianteRepository.save(estudiante);
    }

    @Override
    public void eliminarEstudiante(Long id) {
        estudianteRepository.deleteById(id);
    }
}
