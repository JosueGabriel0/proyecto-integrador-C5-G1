package upeu.edu.pe.msdocente.feign;

import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import upeu.edu.pe.msdocente.dto.Curso;
import upeu.edu.pe.msdocente.dto.Persona;
import upeu.edu.pe.msdocente.entity.Docente;

import java.util.List;

@FeignClient(name = "ms-persona-service", path = "/persona")
public interface PersonaFeign {

    @GetMapping("/{id}")
    @CircuitBreaker(name = "personaListarPorIdCB", fallbackMethod = "fallBackPersonaListarPorId")
    public ResponseEntity<Persona> listarPersonaDtoPorId(@PathVariable(required = true) Long id);

    default ResponseEntity<Persona> fallBackPersonaListarPorId(Long id, Exception e) {
        return ResponseEntity.ok(new Persona());
    }

    @GetMapping
    public ResponseEntity<List<Persona>> listarPersonasDto();


}
