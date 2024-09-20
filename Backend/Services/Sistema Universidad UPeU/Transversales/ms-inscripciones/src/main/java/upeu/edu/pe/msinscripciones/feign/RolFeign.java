package upeu.edu.pe.msinscripciones.feign;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import upeu.edu.pe.msinscripciones.dto.Rol;

import java.util.List;

@FeignClient(name = "ms-roles-service", path = "/rol")
public interface RolFeign {

    @PostMapping
    public ResponseEntity<Rol> crearRolDto(@RequestBody Rol Rol);

    @GetMapping
    public ResponseEntity<List<Rol>> listarRolesDto();

    @GetMapping("/{id}")
    public ResponseEntity<Rol> listarRolDtoPorId(@PathVariable(required = true) Long id);

    @PutMapping
    public ResponseEntity<Rol> actualizarRolDto(@PathVariable(required = true) Long RolId, @RequestBody Rol Rol);

    @DeleteMapping
    public ResponseEntity<String> eliminarRolDto(@PathVariable(required = true) Long id);

}
