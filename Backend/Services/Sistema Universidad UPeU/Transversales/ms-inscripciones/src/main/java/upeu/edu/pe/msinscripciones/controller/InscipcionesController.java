package upeu.edu.pe.msinscripciones.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import upeu.edu.pe.msinscripciones.entity.Inscripcion;
import upeu.edu.pe.msinscripciones.service.InscripcionesService;

import java.util.List;

@RestController
@RequestMapping("/Inscripcion")
public class InscipcionesController {
    @Autowired
    private InscripcionesService inscripcionesService;

    @PostMapping
    public ResponseEntity<Inscripcion> guardarInscripcionResponseEntity(@RequestBody Inscripcion Inscripcion){
        return ResponseEntity.ok(inscripcionesService.guardarInscripcion(Inscripcion));
    }

    @GetMapping
    public ResponseEntity<List<Inscripcion>> listarInscripcionResponseEntity(){
        return ResponseEntity.ok(inscripcionesService.listarInscripcion());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Inscripcion> buscarInscripcionPorIdResponseEntity(@PathVariable(required = true) Long id){
        return ResponseEntity.ok(inscripcionesService.buscarInscripcionPorId(id));
    }

    @PutMapping("/{id}")
    public ResponseEntity<Inscripcion> editarInscripcionResponseEntity(@PathVariable (required = true) Long id,@RequestBody Inscripcion Inscripcion){
        Inscripcion.setId(id);
        return ResponseEntity.ok(inscripcionesService.editarInscripcion(Inscripcion));
    }

    @DeleteMapping("{id}")
    public ResponseEntity<String> eliminarInscripcion(@PathVariable Long id) {
        try {
            // Lógica para eliminar la Inscripcion
            inscripcionesService.eliminarInscripcion(id);

            // Retornar código 200 OK con mensaje de éxito
            return ResponseEntity.ok("Inscripcion eliminada exitosamente.");
        } catch (Exception e) {
            // En caso de error, retornar un código de error y mensaje apropiado
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Error al eliminar la Inscripcion: " + e.getMessage());
        }
    }
}
