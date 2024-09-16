package upeu.edu.pe.msestudiante.controller;

import feign.FeignException;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import upeu.edu.pe.msestudiante.dto.Curso;
import upeu.edu.pe.msestudiante.dto.EstudianteRequest;
import upeu.edu.pe.msestudiante.dto.Persona;
import upeu.edu.pe.msestudiante.entity.Estudiante;
import upeu.edu.pe.msestudiante.feign.CursoFeign;
import upeu.edu.pe.msestudiante.feign.PersonaFeign;
import upeu.edu.pe.msestudiante.service.EstudianteService;

import java.util.List;

@RestController
@RequestMapping("/estudiante")
public class EstudianteController {

    @Autowired
    private EstudianteService estudianteService;

    @Autowired
    private PersonaFeign personaFeign;

    @Autowired
    private CursoFeign cursoFeign;

    // Endpoint para crear un estudiante con datos de persona
    @PostMapping("/con-persona")
    public ResponseEntity<?> crearEstudianteConPersona(@RequestBody EstudianteRequest estudianteRequest) {
        try {
            // Crear el estudiante junto con la persona
            Estudiante estudianteGuardado = estudianteService.crearEstudianteConPersona(estudianteRequest);

            // Retornar la respuesta exitosa con el estudiante creado
            return ResponseEntity.status(HttpStatus.CREATED).body(estudianteGuardado);
        } catch (Exception e) {
            // Capturar cualquier error y retornar una respuesta de error
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Error al crear el estudiante: " + e.getMessage());
        }
    }

    @PostMapping("/solo-estudiante")
    public ResponseEntity<?> guardarEstudianteResponseEntity(@RequestBody Estudiante estudiante) {
        try {
            // Verificar si la persona existe
            ResponseEntity<Persona> personaResponse = personaFeign.listarPersonaDtoPorId(estudiante.getIdPersona());
            if (personaResponse.getStatusCode() == HttpStatus.NOT_FOUND || personaResponse.getBody() == null) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body("No existe la persona con ID: " + estudiante.getIdPersona());
            }
            Persona persona = personaResponse.getBody();  // Extraer el cuerpo de la respuesta

            // Verificar si el curso existe
            ResponseEntity<Curso> cursoResponse = cursoFeign.listarCursoDtoPorId(estudiante.getIdCurso());
            if (cursoResponse.getStatusCode() == HttpStatus.NOT_FOUND || cursoResponse.getBody() == null) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body("No existe el curso con ID: " + estudiante.getIdCurso());
            }
            Curso curso = cursoResponse.getBody();  // Extraer el cuerpo de la respuesta

            // Asignar la persona y curso al estudiante
            estudiante.setPersona(persona);
            estudiante.setCurso(curso);

            // Guardar el estudiante si todas las validaciones pasaron
            Estudiante estudianteGuardado = estudianteService.guardarEstudiante(estudiante);

            // Retornar respuesta exitosa
            return ResponseEntity.status(HttpStatus.CREATED).body(estudianteGuardado);

        } catch (FeignException e) {
            // Capturar errores de Feign con más detalle
            String errorMensaje = "Error al comunicarse con otro servicio: " + e.getMessage();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(errorMensaje);

        } catch (Exception e) {
            // Manejo de cualquier otro error inesperado
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error interno del servidor: " + e.getMessage());
        }
    }

    // Endpoint para listar estudiantes con persona
    @GetMapping("/con-persona")
    public ResponseEntity<List<Estudiante>> listarEstudiantesConPersona() {
        try {
            List<Estudiante> estudiantes = estudianteService.listarEstudiantesConPersona();
            return ResponseEntity.ok(estudiantes);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(null);
        }
    }

    // Endpoint para listar solo estudiantes
    @GetMapping
    public ResponseEntity<List<Estudiante>> listarEstudiantes() {
        try {
            List<Estudiante> estudiantes = estudianteService.listarEstudiantes();
            return ResponseEntity.ok(estudiantes);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(null);
        }
    }

    @GetMapping("/con-persona/{id}")
    public ResponseEntity<?> listarEstudianteConPersonaPorId(@PathVariable Long id) {
        try {
            // Llamada al servicio para obtener el estudiante con la información de persona incluida
            Estudiante estudiante = estudianteService.listarEstudianteConPersonaPorId(id);
            return ResponseEntity.ok(estudiante);
        } catch (EntityNotFoundException e) {
            // Retornar error 404 si el estudiante no se encuentra
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Estudiante no encontrado: " + e.getMessage());
        } catch (FeignException e) {
            // Manejo de errores de comunicación con el microservicio de Persona
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error al comunicarse con el servicio de Persona: " + e.getMessage());
        } catch (Exception e) {
            // Manejo de cualquier otro error inesperado
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error interno del servidor: " + e.getMessage());
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<Estudiante> buscarEstudiantePorIdResponseEntity(@PathVariable Long id) {
        Estudiante estudiante = estudianteService.listarEstudiantePorId(id);
        return ResponseEntity.ok(estudiante);
    }

    @PutMapping("/con-persona/{id}")
    public ResponseEntity<?> editarEstudianteConPersona(@PathVariable Long id, @RequestBody EstudianteRequest estudianteRequest) {
        try {
            // Actualizar el estudiante junto con los datos de la persona
            Estudiante estudianteActualizado = estudianteService.editarEstudianteConPersona(id, estudianteRequest);

            // Retornar la respuesta exitosa con el estudiante actualizado
            return ResponseEntity.ok(estudianteActualizado);
        } catch (EntityNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Estudiante o Persona no encontrados: " + e.getMessage());
        } catch (FeignException e) {
            String errorMensaje = "Error al comunicarse con el microservicio Persona: " + e.getMessage();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(errorMensaje);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error al actualizar el estudiante: " + e.getMessage());
        }
    }
    // Endpoint para editar solo la información del estudiante
    @PutMapping("/{id}")
    public ResponseEntity<?> editarSoloEstudiante(@PathVariable Long id, @RequestBody Estudiante estudiante) {
        try {
            // Actualizar el estudiante
            Estudiante estudianteActualizado = estudianteService.editarSoloEstudiante(id, estudiante);

            // Retornar la respuesta exitosa con el estudiante actualizado
            return ResponseEntity.ok(estudianteActualizado);
        } catch (EntityNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Estudiante no encontrado: " + e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error al actualizar el estudiante: " + e.getMessage());
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> eliminarEstudianteResponseEntity(@PathVariable Long id) {
        estudianteService.eliminarEstudiante(id);
        return ResponseEntity.ok("Estudiante Eliminado");
    }
}


