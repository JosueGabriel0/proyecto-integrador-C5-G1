package upeu.edu.pe.mspersona.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import upeu.edu.pe.mspersona.exception.ResourceNotFoundException;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/persona")
public class PersonaController {
    @Autowired
    private upeu.edu.pe.mspersona.service.AdministradorService personaService;

    @PostMapping
    public ResponseEntity<upeu.edu.pe.mspersona.entity.Administrador> guardarPersonaResponseEntity(@RequestBody upeu.edu.pe.mspersona.entity.Administrador persona){
        return ResponseEntity.ok(personaService.guardarPersona(persona));
    }

    @GetMapping
    public ResponseEntity<List<upeu.edu.pe.mspersona.entity.Administrador>> listarPersonaResponseEntity(){
        try {
            List<upeu.edu.pe.mspersona.entity.Administrador> personas = personaService.listarPersona();
            return ResponseEntity.ok(personas);
        } catch (Exception e) {
            // Capturamos cualquier error inesperado y devolvemos una respuesta de error
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new ArrayList<>());
        }
    }

    /**
     * Manejo de la excepción ResourceNotFoundException.
     * Devuelve un 404 si no se encuentra el recurso solicitado.
     *
     * @param ex Excepción capturada.
     * @return Mensaje de error con un código de estado 404.
     */
    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<String> handleResourceNotFoundException(ResourceNotFoundException ex) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(ex.getMessage());
    }

    @GetMapping("/{id}")
    public ResponseEntity<upeu.edu.pe.mspersona.entity.Administrador> buscarPersonaPorIdResponseEntity(@PathVariable(required = true) Long id){
        return ResponseEntity.ok(personaService.buscarPersonaPorId(id));
    }

    @PutMapping("/{id}")
    public ResponseEntity<upeu.edu.pe.mspersona.entity.Administrador> editarPersonaResponseEntity(@PathVariable (required = true) Long id, @RequestBody upeu.edu.pe.mspersona.entity.Administrador persona){
        persona.setId(id);
        return ResponseEntity.ok(personaService.editarPersona(persona));
    }

    @DeleteMapping("{id}")
    public ResponseEntity<String> eliminarPersona(@PathVariable Long id) {
        try {
            // Lógica para eliminar la persona
            personaService.eliminarPersona(id);

            // Retornar código 200 OK con mensaje de éxito
            return ResponseEntity.ok("Persona eliminada exitosamente.");
        } catch (Exception e) {
            // En caso de error, retornar un código de error y mensaje apropiado
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Error al eliminar la persona: " + e.getMessage());
        }
    }
}
