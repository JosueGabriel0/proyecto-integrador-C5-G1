package upeu.edu.pe.msinscripciones.feign;

import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import upeu.edu.pe.msinscripciones.dto.Docente;

import java.util.ArrayList;
import java.util.List;

@FeignClient(name = "ms-docente-service", path = "/docente")
public interface DocenteFeign {

    @PostMapping
    @CircuitBreaker(name = "crearDocenteCB", fallbackMethod = "fallbackMethodCrearDocente")
    ResponseEntity<?> crearDocenteDto(@RequestBody Docente docente);

    @GetMapping
    @CircuitBreaker(name = "listarDocentesCB", fallbackMethod = "fallbackMethodListarDocentes")
    ResponseEntity<List<Docente>> listarDocentesDto();

    @GetMapping("/{id}")
    @CircuitBreaker(name = "listarDocentePorIdCB", fallbackMethod = "fallbackMethodListarDocentePorId")
    ResponseEntity<Docente> listarDocenteDtoPorId(@PathVariable Long id);

    @PutMapping("/{id}")  // Añadir la ruta con el ID
    @CircuitBreaker(name = "actualizarDocenteCB", fallbackMethod = "fallbackMethodActualizarDocente")
    ResponseEntity<Docente> actualizarDocenteDto(@PathVariable Long id, @RequestBody Docente docente);

    @DeleteMapping("/{id}")  // Añadir la ruta con el ID
    @CircuitBreaker(name = "eliminarDocenteCB", fallbackMethod = "fallbackMethodEliminarDocente")
    ResponseEntity<String> eliminarDocenteDto(@PathVariable Long id);

    default ResponseEntity<?> fallbackMethodCrearDocente(Docente docente, Exception e){
        return ResponseEntity.ok(new Docente());
    }

    default ResponseEntity<List<Docente>> fallbackMethodListarDocentes(Exception e){
        return ResponseEntity.ok(new ArrayList<Docente>());
    }

    default ResponseEntity<Docente> fallbackMethodListarDocentePorId(Long id, Exception e){
        return ResponseEntity.ok(new Docente());
    }

    default ResponseEntity<Docente> fallbackMethodActualizarDocente(Long id, Docente docente, Exception e){
        return ResponseEntity.ok(new Docente());
    }

    default ResponseEntity<String> fallbackMethodEliminarDocente(Long id, Exception e){
        return ResponseEntity.ok("Error al eliminar el docente. Fallback activado");
    }
}