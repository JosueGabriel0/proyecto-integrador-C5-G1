package upeu.edu.pe.msrequisitosacademicos.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import upeu.edu.pe.msrequisitosacademicos.entity.Requisito;
import upeu.edu.pe.msrequisitosacademicos.service.RequisitoService;

import java.util.List;

@RestController
@RequestMapping("/requisito")
public class RequisitoController {
    @Autowired
    private RequisitoService requisitoService;

    @PostMapping
    public ResponseEntity<Requisito> guardarRequisitoResponseEntity(@RequestBody Requisito requisito){
        return ResponseEntity.ok(requisitoService.guardarRequisito(requisito));
    }

    @GetMapping
    public ResponseEntity<List<Requisito>> listarRequisitoResponseEntity(){
        return ResponseEntity.ok(requisitoService.listarRequisitos());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Requisito> buscarRequisitoPorIdResponseEntity(@PathVariable(required = true) Long id){
        return ResponseEntity.ok(requisitoService.buscarRequisitoPorId(id));
    }

    @PutMapping("/{id}")
    public ResponseEntity<Requisito> editarRequisitoResponseEntity(@PathVariable (required = true) Long id,@RequestBody Requisito requisito){
        requisito.setIdRequisito(id);
        return ResponseEntity.ok(requisitoService.editarRequisito(requisito));
    }

    @DeleteMapping("{id}")
    public ResponseEntity<String> eliminarRequisito(@PathVariable Long id) {
        try {
            // Lógica para eliminar la Requisito
            requisitoService.eliminarRequisito(id);

            // Retornar código 200 OK con mensaje de éxito
            return ResponseEntity.ok("Requisito eliminado exitosamente.");
        } catch (Exception e) {
            // En caso de error, retornar un código de error y mensaje apropiado
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Error al eliminar el Requisito: " + e.getMessage());
        }
    }

    /**
     * Validar si un estudiante cumple con los requisitos académicos para una carrera.
     * @param idEstudiante ID del estudiante.
     * @param idCarrera ID de la carrera.
     * @return Respuesta indicando si los requisitos están cumplidos.
     */
    @GetMapping("/validar")
    public ResponseEntity<Boolean> validarRequisitos(
            @RequestParam Long idEstudiante,
            @RequestParam Long idCarrera) {
        boolean requisitosCumplidos = requisitoService.validarRequisitos(idEstudiante, idCarrera);
        return ResponseEntity.ok(requisitosCumplidos);
    }
}
