package upeu.edu.pe.msinscripciones.feign;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import upeu.edu.pe.msinscripciones.dto.Usuario;

import java.util.List;

@FeignClient(name = "ms-usuarios-service", path = "/usuario")
public interface UsuarioFeign {

    @PostMapping
    ResponseEntity<Usuario> crearUsuarioDto(@RequestBody Usuario usuario);

    @GetMapping
    ResponseEntity<List<Usuario>> listarUsuariosDto();

    @GetMapping("/{id}")
    ResponseEntity<Usuario> listarUsuarioDtoPorId(@PathVariable Long id);

    @PutMapping("/{id}")  // Añadir la ruta con el ID
    ResponseEntity<Usuario> actualizarUsuarioDto(@PathVariable Long id, @RequestBody Usuario usuario);

    @DeleteMapping("/{id}")  // Añadir la ruta con el ID
    ResponseEntity<String> eliminarUsuarioDto(@PathVariable Long id);
}