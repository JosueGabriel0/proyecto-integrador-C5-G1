package upeu.edu.pe.msinscripciones.feign;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import upeu.edu.pe.msinscripciones.dto.Persona;

import java.util.List;

@FeignClient(name = "ms-persona-service", path = "/persona")
public interface PersonaFeign {

    @PostMapping
    ResponseEntity<Persona> crearPersonaDto(@RequestBody Persona persona);

    @GetMapping
    ResponseEntity<List<Persona>> listarPersonasDto();

    @GetMapping("/{id}")
    ResponseEntity<Persona> listarPersonaDtoPorId(@PathVariable Long id);

    @PutMapping("/{id}")  // Añadir la ruta con el ID
    ResponseEntity<Persona> actualizarPersonaDto(@PathVariable Long id, @RequestBody Persona persona);

    @DeleteMapping("/{id}")  // Añadir la ruta con el ID
    ResponseEntity<String> eliminarPersonaDto(@PathVariable Long id);
}
