package upeu.edu.pe.mspersona.feign;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import upeu.edu.pe.mspersona.dto.Estudiante;

import java.util.List;

@FeignClient(name = "ms-estudiante-service", path = "/estudiante")
public interface EstudianteFeign {
    @GetMapping("/{id}")
    public ResponseEntity<Estudiante> listarEstudianteDtoPorId(@PathVariable(required = true) Long id);

    @GetMapping
    public ResponseEntity<List<Estudiante>> listarEstudiantesDto();
}
