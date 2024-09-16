package upeu.edu.pe.msdocente.feign;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import upeu.edu.pe.msdocente.dto.Curso;

import java.util.List;

@FeignClient(name = "ms-curso-service", path = "/curso")
public interface CursoFeign {

    @GetMapping("/{id}")
    public ResponseEntity<Curso> listarCursoDtoPorId(@PathVariable(required = true) Long id);

    @GetMapping
    public ResponseEntity<List<Curso>> listarCursosDto();
}
