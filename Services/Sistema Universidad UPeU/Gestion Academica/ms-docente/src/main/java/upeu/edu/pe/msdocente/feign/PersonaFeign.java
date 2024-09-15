package upeu.edu.pe.msdocente.feign;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import upeu.edu.pe.msdocente.dto.Curso;
import upeu.edu.pe.msdocente.dto.Persona;

import java.util.List;

@FeignClient(name = "ms-persona-service", path = "/persona")
public interface PersonaFeign {

    @GetMapping("/{id}")
    public ResponseEntity<Persona> listarPersonaDtoPorId(@PathVariable(required = true) Long id);

    @GetMapping
    public ResponseEntity<List<Persona>> listarPersonasDto();
}
