package upeu.edu.pe.msdocente.feign;

import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import upeu.edu.pe.msdocente.dto.Curso;
import upeu.edu.pe.msdocente.dto.Persona;

import java.util.List;

@FeignClient(name = "ms-curso-service", path = "/curso")
public interface CursoFeign {

    @GetMapping("/{id}")
    @CircuitBreaker(name = "cursoListarPorIdCB", fallbackMethod = "fallBackCursoListarPorId")
    public ResponseEntity<Curso> listarCursoDtoPorId(@PathVariable(required = true) Long id);


    default ResponseEntity<Curso> fallBackCursoListarPorId(Long id, Exception e) {
        return ResponseEntity.ok(new Curso());
    }

    @GetMapping
    public ResponseEntity<List<Curso>> listarCursosDto();

}
