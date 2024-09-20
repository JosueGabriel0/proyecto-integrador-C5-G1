package upeu.edu.pe.msinscripciones.feign;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import upeu.edu.pe.msinscripciones.dto.Docente;

import java.util.List;

@FeignClient(name = "ms-docente-service", path = "/docente")
public interface DocenteFeign {

    @PostMapping
    ResponseEntity<Docente> crearDocenteDto(@RequestBody Docente docente);

    @GetMapping
    ResponseEntity<List<Docente>> listarDocentesDto();

    @GetMapping("/{id}")
    ResponseEntity<Docente> listarDocenteDtoPorId(@PathVariable Long id);

    @PutMapping("/{id}")  // Añadir la ruta con el ID
    ResponseEntity<Docente> actualizarDocenteDto(@PathVariable Long id, @RequestBody Docente docente);

    @DeleteMapping("/{id}")  // Añadir la ruta con el ID
    ResponseEntity<String> eliminarDocenteDto(@PathVariable Long id);
}