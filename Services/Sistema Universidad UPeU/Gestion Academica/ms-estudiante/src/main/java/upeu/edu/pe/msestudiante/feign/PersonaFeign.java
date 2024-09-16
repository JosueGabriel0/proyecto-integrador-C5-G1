package upeu.edu.pe.msestudiante.feign;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import upeu.edu.pe.msestudiante.dto.Persona;

import java.util.List;

@FeignClient(name = "ms-persona-service", path = "/persona")
public interface PersonaFeign {

    @GetMapping("/{id}")
    public ResponseEntity<Persona> listarPersonaDtoPorId(@PathVariable(required = true) Long id);

    @GetMapping
    public ResponseEntity<List<Persona>> listarPersonasDto();

    @PostMapping
    public Persona crearPersonaDto(@RequestBody Persona persona);

    @PutMapping("/{id}")
    ResponseEntity<Persona> actualizarPersonaDto(@PathVariable("id") Long id, @RequestBody Persona persona);

    @DeleteMapping("/{id}")
    ResponseEntity<Void> eliminarPersonaDto(@PathVariable("id") Long id);
}
