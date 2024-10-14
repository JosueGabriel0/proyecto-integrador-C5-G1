package upeu.edu.pe.msinscripciones.feign;

import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import upeu.edu.pe.msinscripciones.dto.Docente;
import upeu.edu.pe.msinscripciones.dto.Rol;

import java.util.ArrayList;
import java.util.List;

@FeignClient(name = "ms-roles-service", path = "/rol")
public interface RolFeign {

    @PostMapping
    @CircuitBreaker(name = "crearDocenteCB", fallbackMethod = "fallbackMethodCrearDocente")
    ResponseEntity<Rol> crearRolDto(@RequestBody Rol rol);

    @GetMapping
    @CircuitBreaker(name = "crearDocenteCB", fallbackMethod = "fallbackMethodCrearDocente")
    ResponseEntity<List<Rol>> listarRolesDto();

    @GetMapping("/{id}")
    @CircuitBreaker(name = "crearDocenteCB", fallbackMethod = "fallbackMethodCrearDocente")
    ResponseEntity<Rol> listarRolDtoPorId(@PathVariable Long id);

    @PutMapping("/{id}")  // Añadir la ruta con el ID
    @CircuitBreaker(name = "crearDocenteCB", fallbackMethod = "fallbackMethodCrearDocente")
    ResponseEntity<Rol> actualizarRolDto(@PathVariable Long id, @RequestBody Rol rol);

    @DeleteMapping("/{id}")  // Añadir la ruta con el ID
    @CircuitBreaker(name = "crearDocenteCB", fallbackMethod = "fallbackMethodCrearDocente")
    ResponseEntity<String> eliminarRolDto(@PathVariable Long id);

    default ResponseEntity<Docente> fallbackMethodCrearDocente(Docente docente, Exception e){
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
