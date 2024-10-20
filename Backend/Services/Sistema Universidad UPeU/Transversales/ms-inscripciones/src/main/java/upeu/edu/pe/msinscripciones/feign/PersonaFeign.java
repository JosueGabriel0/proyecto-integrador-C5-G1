package upeu.edu.pe.msinscripciones.feign;

import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import upeu.edu.pe.msinscripciones.dto.Persona;
import upeu.edu.pe.msinscripciones.dto.Persona;

import java.util.ArrayList;
import java.util.List;

@FeignClient(name = "ms-persona-service", path = "/persona")
public interface PersonaFeign {

    @PostMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    @CircuitBreaker(name = "crearPersonaCB", fallbackMethod = "fallbackMethodCrearPersona")
    ResponseEntity<?> crearPersonaDto(@RequestPart("persona") Persona persona, @RequestPart("file") MultipartFile fotoPerfil);

    @GetMapping
    @CircuitBreaker(name = "listarPersonasCB", fallbackMethod = "fallbackMethodListarPersonas")
    ResponseEntity<List<Persona>> listarPersonasDto();

    @GetMapping("/{id}")
    @CircuitBreaker(name = "listarPersonaPorIdCB", fallbackMethod = "fallbackMethodListarPersonaPorId")
    ResponseEntity<Persona> listarPersonaDtoPorId(@PathVariable Long id);

    @PutMapping("/{id}")  // Añadir la ruta con el ID
    @CircuitBreaker(name = "actualizarPersonaCB", fallbackMethod = "fallbackMethodActualizarPersona")
    ResponseEntity<Persona> actualizarPersonaDto(@PathVariable Long id, @RequestBody Persona persona);

    @DeleteMapping("/{id}")  // Añadir la ruta con el ID
    @CircuitBreaker(name = "eliminarPersonaCB", fallbackMethod = "fallbackMethodEliminarPersona")
    ResponseEntity<String> eliminarPersonaDto(@PathVariable Long id);

    default ResponseEntity<?> fallbackMethodCrearPersona(Persona Persona, MultipartFile fotoPerfil, Exception e){
        return ResponseEntity.ok(new Persona());
    }

    default ResponseEntity<List<Persona>> fallbackMethodListarPersonas(Exception e){
        return ResponseEntity.ok(new ArrayList<Persona>());
    }

    default ResponseEntity<Persona> fallbackMethodListarPersonaPorId(Long id, Exception e){
        return ResponseEntity.ok(new Persona());
    }

    default ResponseEntity<Persona> fallbackMethodActualizarPersona(Long id, Persona Persona, Exception e){
        return ResponseEntity.ok(new Persona());
    }

    default ResponseEntity<String> fallbackMethodEliminarPersona(Long id, Exception e){
        return ResponseEntity.ok("Error al eliminar la Persona. Fallback activado");
    }
}
