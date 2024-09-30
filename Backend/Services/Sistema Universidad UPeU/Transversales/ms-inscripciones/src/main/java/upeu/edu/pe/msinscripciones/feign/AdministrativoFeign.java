package upeu.edu.pe.msinscripciones.feign;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import upeu.edu.pe.msinscripciones.dto.Administrativo;

import java.util.List;

@FeignClient(name = "ms-administrativo-service", path = "/administrativo")
public interface AdministrativoFeign {

    @PostMapping
    ResponseEntity<Administrativo> crearAdministrativoDto(@RequestBody Administrativo administrativo);

    @GetMapping
    ResponseEntity<List<Administrativo>> listarAdministrativosDto();

    @GetMapping("/{id}")
    ResponseEntity<Administrativo> listarAdministrativoDtoPorId(@PathVariable Long id);

    @PutMapping("/{id}")  // Añadir la ruta con el ID
    ResponseEntity<Administrativo> actualizarAdministrativoDto(@PathVariable Long id, @RequestBody Administrativo administrativo);

    @DeleteMapping("/{id}")  // Añadir la ruta con el ID
    ResponseEntity<String> eliminarAdministrativoDto(@PathVariable Long id);
}