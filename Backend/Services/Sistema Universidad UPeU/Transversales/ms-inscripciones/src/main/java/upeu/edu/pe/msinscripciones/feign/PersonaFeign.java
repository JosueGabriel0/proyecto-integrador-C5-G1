package upeu.edu.pe.msinscripciones.feign;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import upeu.edu.pe.msinscripciones.dto.Persona;

import java.util.List;

@FeignClient(name = "ms-persona-service", path = "/persona")
public interface PersonaFeign {

    @PostMapping
    public ResponseEntity<Persona> crearPersonaDto(@RequestBody Persona Persona);

    @GetMapping
    public ResponseEntity<List<Persona>> listarPersonasDto();

    @GetMapping("/{id}")
    public ResponseEntity<Persona> listarPersonaDtoPorId(@PathVariable(required = true) Long id);

    @PutMapping("/{id}")
    public ResponseEntity<Persona> actualizarPersonaDto(@PathVariable(required = true) Long id, @RequestBody Persona Persona);

    @DeleteMapping("/{id}")
    public ResponseEntity<String> eliminarPersonaDto(@PathVariable(required = true) Long id);

}
