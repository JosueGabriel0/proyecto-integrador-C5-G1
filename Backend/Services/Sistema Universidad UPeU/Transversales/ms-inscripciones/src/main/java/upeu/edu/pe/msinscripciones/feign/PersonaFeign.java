package upeu.edu.pe.msinscripciones.feign;

import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.HttpStatus;
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

    // Método para actualizar una persona por ID
    @PutMapping(value = "/{id}", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    @CircuitBreaker(name = "editarPersonaCB", fallbackMethod = "fallbackMethodEditarPersona")
    ResponseEntity<?> editarPersonaDto(
            @PathVariable("id") Long id,
            @RequestPart("persona") Persona persona,
            @RequestPart("file") MultipartFile fotoPerfil);

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

    // Método de fallback para el editar
    default ResponseEntity<?> fallbackMethodEditarPersona(Long id, Persona persona, MultipartFile file, Throwable throwable) {
        // Lógica del método de respaldo en caso de fallo de la actualización
        return ResponseEntity.status(HttpStatus.SERVICE_UNAVAILABLE).body("No se pudo actualizar la persona en este momento.");
    }

    default ResponseEntity<String> fallbackMethodEliminarPersona(Long id, Exception e){
        return ResponseEntity.ok("Error al eliminar la Persona. Fallback activado");
    }
}
