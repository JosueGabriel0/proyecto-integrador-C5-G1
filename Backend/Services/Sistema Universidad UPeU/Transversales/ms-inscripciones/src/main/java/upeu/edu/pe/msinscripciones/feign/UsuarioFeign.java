package upeu.edu.pe.msinscripciones.feign;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import upeu.edu.pe.msinscripciones.dto.Usuario;

import java.util.List;

@FeignClient(name = "ms-usuarios-service", path = "/usuario")
public interface UsuarioFeign {

    @PostMapping
    public ResponseEntity<Usuario> crearUsuarioDto(@RequestBody Usuario usuario);

    @GetMapping
    public ResponseEntity<List<Usuario>> listarUsuariosDto();

    @GetMapping("/{id}")
    public ResponseEntity<Usuario> listarUsuarioDtoPorId(@PathVariable(required = true) Long id);

    @PutMapping
    public ResponseEntity<Usuario> actualizarUsuarioDto(@PathVariable(required = true) Long usuarioId, @RequestBody Usuario usuario);

    @DeleteMapping
    public ResponseEntity<String> eliminarUsuarioDto(@PathVariable(required = true) Long id);

}
