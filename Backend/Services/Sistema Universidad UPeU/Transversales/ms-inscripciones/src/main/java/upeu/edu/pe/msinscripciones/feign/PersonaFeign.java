package upeu.edu.pe.msinscripciones.feign;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import upeu.edu.pe.msinscripciones.config.FeignConfig;
import upeu.edu.pe.msinscripciones.dto.Persona;

import java.util.List;

@FeignClient(name = "ms-persona-service", path = "/persona", configuration = FeignConfig.class)
public interface PersonaFeign {

    @PostMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    ResponseEntity<Persona> crearPersonaDto(@ModelAttribute Persona persona, @RequestParam("file") MultipartFile fotoPerfil);

    @GetMapping
    ResponseEntity<List<Persona>> listarPersonasDto();

    @GetMapping("/{id}")
    ResponseEntity<Persona> listarPersonaDtoPorId(@PathVariable Long id);

    @PutMapping("/{id}")  // Añadir la ruta con el ID
    ResponseEntity<Persona> actualizarPersonaDto(@PathVariable Long id, @RequestBody Persona persona);

    @DeleteMapping("/{id}")  // Añadir la ruta con el ID
    ResponseEntity<String> eliminarPersonaDto(@PathVariable Long id);
}
