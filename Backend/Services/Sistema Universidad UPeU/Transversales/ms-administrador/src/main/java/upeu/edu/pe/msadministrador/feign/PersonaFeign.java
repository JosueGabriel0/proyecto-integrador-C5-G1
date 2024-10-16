package upeu.edu.pe.msadministrador.feign;

import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import upeu.edu.pe.msadministrador.dto.Persona;

import java.util.ArrayList;
import java.util.List;

@FeignClient(name = "ms-persona-service", path = "/persona")
public interface PersonaFeign {

    @GetMapping("/{id}")
    @CircuitBreaker(name = "personaListarPorIdCB", fallbackMethod = "fallBackPersonaListarPorId")
    public ResponseEntity<Persona> listarPersonaDtoPorId(@PathVariable(required = true) Long id);

    @GetMapping
    @CircuitBreaker(name = "personaListarTodasCB", fallbackMethod = "fallBackListarPersonas")
    public ResponseEntity<List<Persona>> listarPersonasDto();

    @PostMapping
    @CircuitBreaker(name = "personaCrearCB", fallbackMethod = "fallBackCrearPersona")
    public ResponseEntity<?> crearPersonaDto(@ModelAttribute Persona persona, @RequestParam("file")MultipartFile fotoPerfil);

    @PutMapping("/{id}")
    @CircuitBreaker(name = "personaActualizarCB", fallbackMethod = "fallBackActualizarPersona")
    ResponseEntity<Persona> actualizarPersonaDto(@PathVariable("id") Long id, @RequestBody Persona persona);

    @DeleteMapping("{id}")
    @CircuitBreaker(name = "personaEliminarCB", fallbackMethod = "fallBackEliminarPersona")
    ResponseEntity<String> eliminarPersonaDto(@PathVariable("id") Long id);


    //Metodos default fallback
    default ResponseEntity<Persona> fallBackPersonaListarPorId(Long id, Exception e) {
        return ResponseEntity.ok(new Persona());
    }

    default ResponseEntity<List<Persona>> fallBackListarPersonas(Exception e) {
        // Retorna una lista vacía o cualquier valor por defecto
        return ResponseEntity.ok(new ArrayList<>());
    }

    default ResponseEntity<?> fallBackCrearPersona(Persona persona, MultipartFile fotoPerfil, Exception e) {
        // Retorna una Persona vacía o un mensaje de error controlado
        return ResponseEntity.ok(new Persona());
    }

    default ResponseEntity<Persona> fallBackActualizarPersona(Long id, Persona persona, Exception e) {
        // Retorna una Persona por defecto o un estado de error
        return ResponseEntity.ok(new Persona());
    }

    default ResponseEntity<String> fallBackEliminarPersona(Long id, Exception e) {
        // Retorna un mensaje controlado o indica que no se pudo eliminar
        return ResponseEntity.ok("Error al eliminar la persona. Fallback activado.");
    }
}
