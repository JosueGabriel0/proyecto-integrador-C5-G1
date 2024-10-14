package upeu.edu.pe.msauth.feign;

import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import upeu.edu.pe.msauth.dto.Usuario;

import java.util.List;

@FeignClient(name = "ms-usuarios-service", path = "/usuario")
public interface UsuarioFeign {

    @GetMapping("/email/{email}")
    @CircuitBreaker(name = "usuarioListarPorEmailCB", fallbackMethod = "fallBackUsuarioListarPorEmail")
    public ResponseEntity<Usuario> getUsuarioByEmail(@PathVariable String email);

    // Método para buscar usuario por username
    @GetMapping("/search") // Ruta para buscar usuario por username
    @CircuitBreaker(name = "usuarioListarPorUserNameCB", fallbackMethod = "fallBackUsuarioListarPorUserName")
    ResponseEntity<Usuario> buscarUsuarioPorUsername(@RequestParam("username") String username);

    // Método para buscar usuario por ID
    @GetMapping("/{id}") // Ruta para buscar usuario por ID
    @CircuitBreaker(name = "usuarioListarPorIdCB", fallbackMethod = "fallBackUsuarioListarPorId")
    ResponseEntity<Usuario> buscarUsuarioPorId(@PathVariable("id") Long id);

    @PutMapping("/{id}")
    @CircuitBreaker(name = "usuarioActualizarCB", fallbackMethod = "fallBackActualizarUsuario")
    public ResponseEntity<Usuario> editarUsuarioResponseEntity(@PathVariable(required = true) Long id,@RequestBody Usuario Usuario);

    //Metodos default fallback
    default ResponseEntity<Usuario> fallBackUsuarioListarPorEmail(String email, Exception e) {
        return ResponseEntity.ok(new Usuario());
    }

    default ResponseEntity<Usuario> fallBackUsuarioListarPorUserName(String username, Exception e) {
        return ResponseEntity.ok(new Usuario());
    }

    default ResponseEntity<Usuario> fallBackUsuarioListarPorId(Long id, Exception e) {
        return ResponseEntity.ok(new Usuario());
    }

    default ResponseEntity<Usuario> fallBackActualizarUsuario(Long id, Usuario usuario, Exception e) {
        return ResponseEntity.ok(new Usuario());
    }
}
