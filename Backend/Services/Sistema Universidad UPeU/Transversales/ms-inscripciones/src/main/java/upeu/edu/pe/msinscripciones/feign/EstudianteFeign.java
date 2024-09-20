package upeu.edu.pe.msinscripciones.feign;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import upeu.edu.pe.msinscripciones.dto.Estudiante;

import java.util.List;

@FeignClient(name = "ms-estudiante-service", path = "/estudiante")
public interface EstudianteFeign {

    @PostMapping
    ResponseEntity<Estudiante> crearEstudianteDto(@RequestBody Estudiante estudiante);

    @GetMapping
    ResponseEntity<List<Estudiante>> listarEstudiantesDto();

    @GetMapping("/{id}")
    ResponseEntity<Estudiante> listarEstudianteDtoPorId(@PathVariable Long id);

    @PutMapping("/{id}")  // Añadir la ruta con el ID
    ResponseEntity<Estudiante> actualizarEstudianteDto(@PathVariable Long id, @RequestBody Estudiante estudiante);

    @DeleteMapping("/{id}")  // Añadir la ruta con el ID
    ResponseEntity<String> eliminarEstudianteDto(@PathVariable Long id);
}
