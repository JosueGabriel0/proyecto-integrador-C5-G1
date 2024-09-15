package upeu.edu.pe.msestudiante.service.impl;

import jakarta.transaction.Transactional;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
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
        return estudianteRepository.save(estudiante);
    }

    @Override
    public Estudiante guardarEstudiante(Estudiante estudiante) {
        return estudianteRepository.save(estudiante);
    }

    @Override
    public List<Estudiante> listarEstudiantes() {
        List<Estudiante> estudiantes = estudianteRepository.findAll();

        // Recorrer la lista de estudiantes para asignar los detalles de Persona y Curso
        estudiantes.forEach(estudiante -> {
            // Llamar a PersonaFeign para obtener los detalles de la Persona
            Persona persona = personaFeign.listarPersonaDtoPorId(estudiante.getIdPersona()).getBody();
            estudiante.setPersona(persona);

            // Llamar a CursoFeign para obtener los detalles del Curso
            Curso curso = cursoFeign.listarCursoDtoPorId(estudiante.getIdCurso()).getBody();
            estudiante.setCurso(curso);
        });

        return estudiantes;
    }

    @Override
    public Estudiante buscarEstudiantePorId(Long id) {
        Estudiante estudiante = estudianteRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Estudiante no encontrado"));

        // Llamar a PersonaFeign y CursoFeign para obtener los detalles
        Persona persona = personaFeign.listarPersonaDtoPorId(estudiante.getIdPersona()).getBody();
        Curso curso = cursoFeign.listarCursoDtoPorId(estudiante.getIdCurso()).getBody();

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