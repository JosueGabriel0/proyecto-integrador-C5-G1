package upeu.edu.pe.msestudiante.service.impl;

import feign.FeignException;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
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
import java.util.Optional;

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
        estudiante.setPersona(personaCreada);

        // 4. Guardar el estudiante en la base de datos local
        return estudianteRepository.save(estudiante);
    }

    @Override
    public Estudiante guardarEstudiante(Estudiante estudiante) {
        return estudianteRepository.save(estudiante);
    }

    @Override
    public List<Estudiante> listarEstudiantesConPersona() {
        // 1. Obtener todos los estudiantes desde la base de datos local
        List<Estudiante> estudiantes = estudianteRepository.findAll();

        // 2. Para cada estudiante, obtener la información de Persona desde el microservicio de Persona usando Feign
        estudiantes.forEach(estudiante -> {
            // Llamada Feign para obtener la Persona por ID
            ResponseEntity<Persona> responseEntity = personaFeign.listarPersonaDtoPorId(estudiante.getIdPersona());

            // Verificar si la respuesta tiene éxito y la entidad Persona no es nula
            if (responseEntity.getStatusCode().is2xxSuccessful() && responseEntity.getBody() != null) {
                Persona persona = responseEntity.getBody();
                // Establecer la información de la persona en el objeto Estudiante
                estudiante.setPersona(persona);
            }
        });

        // 3. Retornar la lista de estudiantes con la información de persona incluida
        return estudiantes;
    }

    @Override
    public List<Estudiante> listarEstudiantes() {
        // 1. Obtener todos los estudiantes desde la base de datos local
        return estudianteRepository.findAll();
    }

    @Override
    public Estudiante listarEstudianteConPersonaPorId(Long id) {
        // 1. Buscar el estudiante en la base de datos por su ID
        Optional<Estudiante> estudianteOptional = estudianteRepository.findById(id);

        // 2. Verificar si el estudiante existe
        if (estudianteOptional.isPresent()) {
            Estudiante estudiante = estudianteOptional.get();

            // 3. Obtener la información de Persona del microservicio de Persona usando Feign
            ResponseEntity<Persona> responseEntity = personaFeign.listarPersonaDtoPorId(estudiante.getIdPersona());

            // 4. Verificar si la respuesta tiene éxito y la entidad Persona no es nula
            if (responseEntity.getStatusCode().is2xxSuccessful() && responseEntity.getBody() != null) {
                Persona persona = responseEntity.getBody();
                // 5. Asignar la persona al estudiante
                estudiante.setPersona(persona);
            }

            // 6. Retornar el estudiante con la información de la persona
            return estudiante;
        } else {
            // Si no se encuentra el estudiante, lanzar una excepción o devolver null
            throw new EntityNotFoundException("Estudiante con ID " + id + " no encontrado");
        }
    }

    @Override
    public Estudiante listarEstudiantePorId(Long id) {
        // 1. Buscar el estudiante en la base de datos por su ID
        Optional<Estudiante> estudianteOptional = estudianteRepository.findById(id);

        // 2. Verificar si el estudiante existe y devolverlo
        return estudianteOptional.orElseThrow(() ->
                new EntityNotFoundException("Estudiante con ID " + id + " no encontrado")
        );
    }

    @Override
    @Transactional
    public Estudiante editarEstudianteConPersona(Long id, EstudianteRequest estudianteRequest) {
        // 1. Buscar el estudiante por su ID
        Estudiante estudianteExistente = estudianteRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Estudiante con ID " + id + " no encontrado"));

        // 2. Actualizar la Persona asociada en el microservicio de Persona
        ResponseEntity<Persona> responseEntity = personaFeign.actualizarPersonaDto(estudianteExistente.getIdPersona(), estudianteRequest.getPersona());

        // 3. Verificar si la respuesta es exitosa (status code 200)
        if (responseEntity.getStatusCode().is2xxSuccessful()) {
            // Obtener el cuerpo de la respuesta que contiene el objeto Persona actualizado
            Persona personaActualizada = responseEntity.getBody();

            // 4. Mapear los datos actualizados de estudiante
            estudianteExistente.setMatricula(estudianteRequest.getMatricula());
            estudianteExistente.setCicloActual(estudianteRequest.getCicloActual());
            estudianteExistente.setPromedioGeneral(estudianteRequest.getPromedioGeneral());
            // Otros campos del estudiante a actualizar...

            // 5. Actualizar la persona en el estudiante
            estudianteExistente.setPersona(personaActualizada);

            // 6. Guardar los cambios en el repositorio
            return estudianteRepository.save(estudianteExistente);
        } else {
            throw new RuntimeException("Error al actualizar la persona. Código de estado: " + responseEntity.getStatusCode());
        }
    }

    @Override
    @Transactional
    public Estudiante editarSoloEstudiante(Long id, Estudiante estudianteRequest) {
        // 1. Buscar el estudiante por su ID
        Estudiante estudianteExistente = estudianteRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Estudiante con ID " + id + " no encontrado"));

        // 2. Actualizar los datos del estudiante
        estudianteExistente.setMatricula(estudianteRequest.getMatricula());
        estudianteExistente.setCicloActual(estudianteRequest.getCicloActual());
        estudianteExistente.setPromedioGeneral(estudianteRequest.getPromedioGeneral());
        // Otros campos del estudiante a actualizar...

        // 3. Guardar los cambios en el repositorio
        return estudianteRepository.save(estudianteExistente);
    }

    @Override
    @Transactional
    public void eliminarEstudianteConPersona(Long id) {
        // 1. Buscar el estudiante por su ID
        Estudiante estudiante = estudianteRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Estudiante con ID " + id + " no encontrado"));

        // 2. Obtener el ID de la persona asociada
        Long idPersona = estudiante.getIdPersona();

        // 3. Eliminar el estudiante de la base de datos
        estudianteRepository.deleteById(id);

        // 4. Eliminar la persona asociada utilizando Feign
        try {
            personaFeign.eliminarPersonaDto(idPersona);
        } catch (FeignException e) {
            // Manejar errores de Feign (opcional: podrías registrar el error o tomar otra acción)
            throw new RuntimeException("Error al eliminar la persona asociada: " + e.getMessage(), e);
        }
    }

    @Override
    public void eliminarEstudiante(Long id) {
        estudianteRepository.deleteById(id);
    }
}