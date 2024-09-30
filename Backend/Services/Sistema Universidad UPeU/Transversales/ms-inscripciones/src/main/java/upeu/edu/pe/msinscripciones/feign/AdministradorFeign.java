package upeu.edu.pe.msinscripciones.feign;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import upeu.edu.pe.msinscripciones.dto.Administrador;

import java.util.List;

@FeignClient(name = "ms-administrador-service", path = "/administrador")
public interface AdministradorFeign {

    @PostMapping
    ResponseEntity<Administrador> crearAdministradorDto(@RequestBody Administrador administrador);

    @GetMapping
    ResponseEntity<List<Administrador>> listarAdministradorsDto();

    @GetMapping("/{id}")
    ResponseEntity<Administrador> listarAdministradorDtoPorId(@PathVariable Long id);

    @PutMapping("/{id}")  // Añadir la ruta con el ID
    ResponseEntity<Administrador> actualizarAdministradorDto(@PathVariable Long id, @RequestBody Administrador administrador);

    @DeleteMapping("/{id}")  // Añadir la ruta con el ID
    ResponseEntity<String> eliminarAdministradorDto(@PathVariable Long id);
}