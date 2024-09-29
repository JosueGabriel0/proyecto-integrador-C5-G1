package upeu.edu.pe.msestudiante.controller;

import feign.FeignException;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
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
    private CursoFeign cursoFeign;
    @Autowired
    private PersonaFeign personaFeign;

    @PostMapping
    public ResponseEntity<?> guardarEstudianteResponseEntity(@RequestBody Estudiante estudiante){
        try {
            // Verificar si el curso existe
            ResponseEntity<Persona> personaResponse = personaFeign.listarPersonaDtoPorId(estudiante.getIdPersona());
            if (personaResponse.getStatusCode() == HttpStatus.NOT_FOUND || personaResponse.getBody() == null) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body("No existe la persona");
            }
            Persona persona = personaResponse.getBody();

            // Verificar si el curso existe
            ResponseEntity<Curso> cursoResponse = cursoFeign.listarCursoDtoPorId(estudiante.getIdCurso());
            if (cursoResponse.getStatusCode() == HttpStatus.NOT_FOUND || cursoResponse.getBody() == null) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body("No existe el curso");
            }
            Curso curso = cursoResponse.getBody();



            // Verificar si cada producto en los detalles existe antes de procesarlos
            /*
            for (PedidoDetalle pedidoDetalle : pedido.getDetalle()) {
                ResponseEntity<Producto> productoResponse = productoFeign.listarProductoDtoPorId(pedidoDetalle.getProductoId());
                if (productoResponse.getStatusCode() == HttpStatus.NOT_FOUND || productoResponse.getBody() == null) {
                    return ResponseEntity.status(HttpStatus.NOT_FOUND).body("No existe el producto");
                }
                    Producto producto = productoResponse.getBody();
                    // Asignar el producto al detalle
                    pedidoDetalle.setProducto(producto);

            }

            // Asignar los detalles actualizados
            pedido.setDetalle(pedido.getDetalle());
            */

            // Asignar el curso al Estudiante
            estudiante.setPersona(persona);
            // Asignar el curso al Estudiante
            estudiante.setCurso(curso);

            // Guardar el pedido si todas las validaciones pasaron
            Estudiante EstudianteGuardado = estudianteService.guardarEstudiante(estudiante);

            // Retornar respuesta exitosa
            return ResponseEntity.status(HttpStatus.CREATED).body(EstudianteGuardado);

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
    public ResponseEntity<List<Estudiante>> listarEstudiantesResponseEntity(){
        return ResponseEntity.ok(estudianteService.listarEstudiante());
    }

    @CircuitBreaker(name = "EstudianteListarPorIdCB", fallbackMethod = "fallbackEstudiante")
    @GetMapping("/{id}")
    public ResponseEntity<Estudiante> buscarEstudiantePorIdResponseEntity(@PathVariable( required = true) Long id){
        return ResponseEntity.ok(estudianteService.buscarEstudiantePorId(id));
    }

    @PutMapping("/{id}")
    public ResponseEntity<Estudiante> editarEstudianteResponseEntity(@PathVariable( required = true) Long id, @RequestBody Estudiante estudiante){
        estudiante.setIdEstudiante(id);
        return ResponseEntity.ok(estudianteService.editarEstudiante(estudiante));
    }

    @DeleteMapping("/{id}")
    public String eliminarEstudianteResponseEntity(@PathVariable( required = true) Long id){
        estudianteService.eliminarEstudiante(id);
        return "Estudiante eliminado";
    }
}
