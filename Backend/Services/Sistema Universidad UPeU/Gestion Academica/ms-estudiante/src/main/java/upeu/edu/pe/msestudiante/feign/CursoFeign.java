package upeu.edu.pe.msestudiante.feign;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import upeu.edu.pe.msestudiante.dto.Curso;
import upeu.edu.pe.msestudiante.dto.Persona;

import java.util.List;

@FeignClient(name = "ms-curso-service", path = "/curso")
public interface CursoFeign {
    @GetMapping("/{id}")

    public ResponseEntity<Curso> listarCursoDtoPorId(@PathVariable(required = true) Long id);

    @GetMapping
    public ResponseEntity<List<Curso>> listarCursosDto();
}
