package upeu.edu.pe.msestudiante.controller;

import feign.FeignException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import upeu.edu.pe.msestudiante.dto.Curso;
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

    @PostMapping
    public ResponseEntity<?> guardarEstudianteResponseEntity(@RequestBody Estudiante estudiante) {
        try {
            // Verificar si la persona existe
            ResponseEntity<Persona> personaResponse = personaFeign.listarPersonaDtoPorId(estudiante.getIdPersona());
            if (personaResponse.getStatusCode() == HttpStatus.NOT_FOUND || personaResponse.getBody() == null) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body("No existe la persona con ID: " + estudiante.getIdPersona());
            }
            Persona persona = personaResponse.getBody();

            // Verificar si el curso existe
            ResponseEntity<Curso> cursoResponse = cursoFeign.listarCursoDtoPorId(estudiante.getIdCurso());
            if (cursoResponse.getStatusCode() == HttpStatus.NOT_FOUND || cursoResponse.getBody() == null) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body("No existe el curso con ID: " + estudiante.getIdCurso());
            }
            Curso curso = cursoResponse.getBody();

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

    @GetMapping
    public ResponseEntity<List<Estudiante>> listarEstudiantesResponseEntity() {
        List<Estudiante> estudiantes = estudianteService.listarEstudiantes();
        return ResponseEntity.ok(estudiantes);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Estudiante> buscarEstudiantePorIdResponseEntity(@PathVariable Long id) {
        Estudiante estudiante = estudianteService.buscarEstudiantePorId(id);
        return ResponseEntity.ok(estudiante);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Estudiante> editarEstudianteResponseEntity(@PathVariable Long id, @RequestBody Estudiante estudiante) {
        estudiante.setIdEstudiante(id);
        Estudiante estudianteActualizado = estudianteService.editarEstudiante(estudiante);
        return ResponseEntity.ok(estudianteActualizado);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> eliminarEstudianteResponseEntity(@PathVariable Long id) {
        estudianteService.eliminarEstudiante(id);
        return ResponseEntity.ok("Estudiante Eliminado");
    }
}