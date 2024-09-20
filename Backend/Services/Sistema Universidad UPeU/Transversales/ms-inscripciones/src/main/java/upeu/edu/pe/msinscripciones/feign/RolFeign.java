package upeu.edu.pe.msinscripciones.feign;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import upeu.edu.pe.msinscripciones.dto.Rol;

import java.util.List;

@FeignClient(name = "ms-roles-service", path = "/rol")
public interface RolFeign {

    @PostMapping
    ResponseEntity<Rol> crearRolDto(@RequestBody Rol rol);

    @GetMapping
    ResponseEntity<List<Rol>> listarRolesDto();

    @GetMapping("/{id}")
    ResponseEntity<Rol> listarRolDtoPorId(@PathVariable Long id);

    @PutMapping("/{id}")  // Añadir la ruta con el ID
    ResponseEntity<Rol> actualizarRolDto(@PathVariable Long id, @RequestBody Rol rol);

    @DeleteMapping("/{id}")  // Añadir la ruta con el ID
    ResponseEntity<String> eliminarRolDto(@PathVariable Long id);
}
