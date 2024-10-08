package upeu.edu.pe.msauth.feign;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import upeu.edu.pe.msauth.dto.Usuario;

import java.util.List;

@FeignClient(name = "ms-usuarios-service", path = "/usuario")
public interface UsuarioFeign {

    // Método para buscar usuario por username
    @GetMapping("/search") // Ruta para buscar usuario por username
    ResponseEntity<Usuario> buscarUsuarioPorUsername(@RequestParam("username") String username);

    // Método para buscar usuario por ID
    @GetMapping("/{id}") // Ruta para buscar usuario por ID
    ResponseEntity<Usuario> buscarUsuarioPorId(@PathVariable("id") Long id);
}
