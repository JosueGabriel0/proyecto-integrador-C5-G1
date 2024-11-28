package upeu.edu.pe.msrequisitosacademicos.feign;

import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import upeu.edu.pe.msrequisitosacademicos.dto.Curso;

import java.util.ArrayList;
import java.util.List;

@FeignClient(name = "ms-curso-service", path = "/curso")
public interface CursoFeign {

    @PostMapping
    @CircuitBreaker(name = "crearCursoCB", fallbackMethod = "fallbackMethodCrearCurso")
    ResponseEntity<?> crearCursoDto(@RequestBody Curso curso);

    @GetMapping
    @CircuitBreaker(name = "listarCursosCB", fallbackMethod = "fallbackMethodListarCursos")
    ResponseEntity<List<Curso>> listarCursosDto();

    @GetMapping("/{id}")
    @CircuitBreaker(name = "listarCursoPorIdCB", fallbackMethod = "fallbackMethodListarCursoPorId")
    ResponseEntity<Curso> listarCursoDtoPorId(@PathVariable Long id);

    @PutMapping("/{id}")  // Añadir la ruta con el ID
    @CircuitBreaker(name = "actualizarCursoCB", fallbackMethod = "fallbackMethodActualizarCurso")
    ResponseEntity<Curso> actualizarCursoDto(@PathVariable Long id, @RequestBody Curso curso);

    @DeleteMapping("/{id}")  // Añadir la ruta con el ID
    @CircuitBreaker(name = "eliminarCursoCB", fallbackMethod = "fallbackMethodEliminarCurso")
    ResponseEntity<String> eliminarCursoDto(@PathVariable Long id);

    default ResponseEntity<?> fallbackMethodCrearCurso(Curso curso, Exception e){
        return ResponseEntity.ok(new Curso());
    }

    default ResponseEntity<List<Curso>> fallbackMethodListarCursos(Exception e){
        return ResponseEntity.ok(new ArrayList<Curso>());
    }

    default ResponseEntity<Curso> fallbackMethodListarCursoPorId(Long id, Exception e){
        return ResponseEntity.ok(new Curso());
    }

    default ResponseEntity<Curso> fallbackMethodActualizarCurso(Long id, Curso curso, Exception e){
        return ResponseEntity.ok(new Curso());
    }

    default ResponseEntity<String> fallbackMethodEliminarCurso(Long id, Exception e){
        return ResponseEntity.ok("Error al eliminar el Curso. Fallback activado");
    }
}