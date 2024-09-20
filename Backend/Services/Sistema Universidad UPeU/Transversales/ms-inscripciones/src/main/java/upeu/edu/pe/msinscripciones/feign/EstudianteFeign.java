package upeu.edu.pe.msinscripciones.feign;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import upeu.edu.pe.msinscripciones.dto.Estudiante;

import java.util.List;

@FeignClient(name = "ms-estudiante-service", path = "/estudiante")
public interface EstudianteFeign {

    @PostMapping
    public ResponseEntity<Estudiante> crearEstudianteDto(@RequestBody Estudiante Estudiante);

    @GetMapping
    public ResponseEntity<List<Estudiante>> listarEstudiantesDto();

    @GetMapping("/{id}")
    public ResponseEntity<Estudiante> listarEstudianteDtoPorId(@PathVariable(required = true) Long id);

    @PutMapping
    public ResponseEntity<Estudiante> actualizarEstudianteDto(@PathVariable(required = true) Long EstudianteId, @RequestBody Estudiante Estudiante);

    @DeleteMapping
    public ResponseEntity<String> eliminarEstudianteDto(@PathVariable(required = true) Long id);

}
