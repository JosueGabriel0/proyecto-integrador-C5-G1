package upeu.edu.pe.mspostulante.feign;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import upeu.edu.pe.mspostulante.dto.Persona;

@FeignClient(name="ms-postulante-service",path="/persona")
public interface PersonaFeign {
    @GetMapping("/{id}")
    public ResponseEntity<Persona> listarPersonaDtoPorId(@PathVariable(required = true) Long id);
}

