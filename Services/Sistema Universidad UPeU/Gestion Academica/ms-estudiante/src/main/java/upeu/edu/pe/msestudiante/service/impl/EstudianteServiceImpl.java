package upeu.edu.pe.msestudiante.service.impl;

import org.springframework.transaction.annotation.Transactional;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Service;
import upeu.edu.pe.msestudiante.dto.Curso;
import upeu.edu.pe.msestudiante.dto.EstudianteRequest;
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

    @Bean
    public ModelMapper modelMapper() {
        return new ModelMapper();
    }

    @Autowired
    private ModelMapper modelMapper;

    @Override
    @Transactional
    public Estudiante crearEstudianteConPersona(EstudianteRequest estudianteRequest) {
        // 1. Crear Persona en el microservicio Persona usando Feign
        Persona personaCreada = personaFeign.crearPersonaDto(estudianteRequest.getPersona());

        // 2. Mapear directamente desde el DTO al objeto Estudiante
        Estudiante estudiante = modelMapper.map(estudianteRequest, Estudiante.class);

        // 3. Establecer el idPersona del estudiante (ya que esto viene de la respuesta de Persona)
        estudiante.setIdPersona(personaCreada.getId());

        // 4. Guardar el estudiante en la base de datos local
        Estudiante estudianteGuardado = estudianteRepository.save(estudiante);

        return estudianteGuardado;
    }


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
