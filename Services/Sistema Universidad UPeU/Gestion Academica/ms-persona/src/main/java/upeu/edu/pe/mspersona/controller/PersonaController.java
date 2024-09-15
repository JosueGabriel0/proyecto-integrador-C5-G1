package upeu.edu.pe.mspersona.controller;

import feign.FeignException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import upeu.edu.pe.mspersona.dto.Docente;
import upeu.edu.pe.mspersona.dto.Estudiante;
import upeu.edu.pe.mspersona.entity.Persona;
import upeu.edu.pe.mspersona.feign.DocenteFeign;
import upeu.edu.pe.mspersona.feign.EstudianteFeign;
import upeu.edu.pe.mspersona.service.PersonaService;

import java.util.List;

@RestController
@RequestMapping("/persona")
public class PersonaController {
    @Autowired
    private PersonaService personaService;

    @Autowired
    private EstudianteFeign estudianteFeign;

    @Autowired
    private DocenteFeign docenteFeign;

    @PostMapping
    public ResponseEntity<?> guardarPersonaResponseEntity(@RequestBody Persona persona){
        try {
            // Verificar si el curso existe
            ResponseEntity<Estudiante> estudianteResponse = estudianteFeign.listarEstudianteDtoPorId(persona.getIdEstudiante());
            if (estudianteResponse.getStatusCode() == HttpStatus.NOT_FOUND || estudianteResponse.getBody() == null) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body("No existe el estudiante");
            }
            Estudiante estudiante = estudianteResponse.getBody();

            // Verificar si el curso existe
            ResponseEntity<Docente> docenteResponse = docenteFeign.listarDocenteDtoPorId(persona.getIdDocente());
            if (estudianteResponse.getStatusCode() == HttpStatus.NOT_FOUND || estudianteResponse.getBody() == null) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body("No existe el docente");
            }
            Docente docente = docenteResponse.getBody();

            // Asignar el curso al docente
            persona.setDocente(docente);
            // Asignar el curso al docente
            persona.setEstudiante(estudiante);

            // Guardar el pedido si todas las validaciones pasaron
            Persona personaGuardado = personaService.guardarPersona(persona);

            // Retornar respuesta exitosa
            return ResponseEntity.status(HttpStatus.CREATED).body(personaGuardado);

        } catch (FeignException e) {
            // Imprimir los detalles del error que Feign está arrojando
            String errorMensaje = "Error al comunicarse con otro servicio: " + e.getMessage();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(errorMensaje);

        } catch (Exception e) {
            // Manejo de cualquier otro error inesperado
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error interno del servidor: " + e.getMessage());
        }
    }

    @GetMapping
    public ResponseEntity<List<Persona>> listarPersonaResponseEntity(){
        return ResponseEntity.ok(personaService.listarPersona());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Persona> buscarPersonaPorIdResponseEntity(@PathVariable(required = true) Long id){
        return ResponseEntity.ok(personaService.buscarPersonaPorId(id));
    }

    @PutMapping("/{id}")
    public ResponseEntity<Persona> editarPersonaResponseEntity(@PathVariable (required = true) Long id,@RequestBody Persona persona){
        persona.setId(id);
        return ResponseEntity.ok(personaService.editarPersona(persona));
    }

    @DeleteMapping("/{id}")
    public String eliminarPersonaResponseEntity(@PathVariable Long id){
        personaService.eliminarPersona(id);
        return "Persona eliminada";
    }
}
