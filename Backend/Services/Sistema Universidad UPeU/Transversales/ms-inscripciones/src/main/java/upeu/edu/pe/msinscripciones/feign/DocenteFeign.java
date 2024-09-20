package upeu.edu.pe.msinscripciones.feign;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import upeu.edu.pe.msinscripciones.dto.Docente;

import java.util.List;

@FeignClient(name = "ms-docente-service", path = "/docente")
public interface DocenteFeign {

    @PostMapping
    public ResponseEntity<Docente> crearDocenteDto(@RequestBody Docente Docente);

    @GetMapping
    public ResponseEntity<List<Docente>> listarDocentesDto();

    @GetMapping("/{id}")
    public ResponseEntity<Docente> listarDocenteDtoPorId(@PathVariable(required = true) Long id);

    @PutMapping
    public ResponseEntity<Docente> actualizarDocenteDto(@PathVariable(required = true) Long DocenteId, @RequestBody Docente Docente);

    @DeleteMapping
    public ResponseEntity<String> eliminarDocenteDto(@PathVariable(required = true) Long id);

}
