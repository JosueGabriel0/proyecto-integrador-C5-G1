package upeu.edu.pe.msestudiante.feign;

import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import upeu.edu.pe.msestudiante.dto.Curso;

import java.util.ArrayList;
import java.util.List;

@FeignClient(name = "ms-curso-service", path = "/curso")
public interface CursoFeign {

    @GetMapping("/{id}")
    @CircuitBreaker(name = "cursoListarPorIdCB", fallbackMethod = "fallBackCursoListarPorId")
    public ResponseEntity<Curso> listarCursoDtoPorId(@PathVariable(required = true) Long id);

    @GetMapping
    @CircuitBreaker(name = "cursoListarTodosCB", fallbackMethod = "fallBackListarCursos")
    public ResponseEntity<List<Curso>> listarCursosDto();

    @PostMapping
    @CircuitBreaker(name = "cursoCrearCB", fallbackMethod = "fallBackCrearCurso")
    public Curso crearCursoDto(@RequestBody Curso curso);

    @PutMapping("/{id}")
    @CircuitBreaker(name = "cursoActualizarCB", fallbackMethod = "fallBackActualizarCurso")
    ResponseEntity<Curso> actualizarCursoDto(@PathVariable(required = true) Long id, @RequestBody Curso curso);

    @DeleteMapping("{id}")
    @CircuitBreaker(name = "cursoEliminarCB", fallbackMethod = "fallBackEliminarCurso")
    ResponseEntity<String> eliminarCursoDto(@PathVariable(required = true) Long id);


    //Metodos default fallback
    default ResponseEntity<Curso> fallBackCursoListarPorId(Long id, Exception e) {
        return ResponseEntity.ok(new Curso());
    }

    default ResponseEntity<List<Curso>> fallBackListarCursos(Exception e) {
        // Retorna una lista vacía o cualquier valor por defecto
        return ResponseEntity.ok(new ArrayList<>());
    }

    default Curso fallBackCrearCurso(Curso curso, Exception e) {
        // Retorna una Curso vacía o un mensaje de error controlado
        return new Curso();
    }

    default ResponseEntity<Curso> fallBackActualizarCurso(Long id, Curso curso, Exception e) {
        // Retorna una Curso por defecto o un estado de error
        return ResponseEntity.ok(new Curso());
    }

    default ResponseEntity<String> fallBackEliminarCurso(Long id, Exception e) {
        // Retorna un mensaje controlado o indica que no se pudo eliminar
        return ResponseEntity.ok("Error al eliminar la Curso. Fallback activado.");
    }
}
