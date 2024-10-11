package upeu.edu.pe.msusuarios.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import upeu.edu.pe.msusuarios.entity.Usuario;
import upeu.edu.pe.msusuarios.service.UsuarioService;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("/usuario")
public class UsuarioController {
    @Autowired
    private UsuarioService usuarioService;

    @PostMapping("/{idUsuario}/generate-reset-token")
    public ResponseEntity<Map<String, String>> generarTokenRestablecimiento(@PathVariable Long idUsuario) {
        try {
            String token = usuarioService.generarTokenRestablecimiento(idUsuario);

            // Crear un objeto Map para devolver el token en formato JSON
            Map<String, String> response = new HashMap<>();
            response.put("token", token);

            return ResponseEntity.ok(response);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(Map.of("error", "Error al generar el token de restablecimiento: " + e.getMessage()));
        }
    }

    @PostMapping("/batch")
    public ResponseEntity<List<Usuario>> guardarUsuariosBatch(@RequestBody List<Usuario> usuarios) {
        List<Usuario> usuariosGuardados = usuarioService.guardarUsuariosBatch(usuarios);
        return ResponseEntity.ok(usuariosGuardados);
    }

    @GetMapping("/search")
    public ResponseEntity<Usuario> buscarUsuarioPorUsername(@RequestParam("username") String username) {
        Usuario usuario = usuarioService.buscarUsuarioPorUsername(username);
        return ResponseEntity.ok(usuario);
    }

    @GetMapping("/email/{email}")
    public ResponseEntity<Usuario> getUsuarioByEmail(@PathVariable String email) {
        Optional<Usuario> usuario = usuarioService.findByEmail(email);
        if (usuario.isPresent()) {
            return new ResponseEntity<>(usuario.get(), HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @PostMapping("/reset-password")
    public ResponseEntity<String> resetPassword(@RequestHeader("Authorization") String token,
                                                @RequestBody Map<String, String> request) {
        String newPassword = request.get("newPassword");

        try {
            // Eliminar el prefijo "Bearer " del token
            String cleanToken = token.replace("Bearer ", "");

            // Llamar al servicio para restablecer la contraseña
            usuarioService.resetPassword(cleanToken, newPassword);

            return ResponseEntity.ok("Contraseña actualizada con éxito.");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body("Error al actualizar la contraseña: " + e.getMessage());
        }
    }

    @PostMapping
    public ResponseEntity<Usuario> guardarUsuarioResponseEntity(@RequestBody Usuario usuario){
        return ResponseEntity.ok(usuarioService.guardarUsuario(usuario));
    }

    @GetMapping
    public ResponseEntity<List<Usuario>> listarUsuarioResponseEntity(){
        return ResponseEntity.ok(usuarioService.listarUsuario());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Usuario> buscarUsuarioPorIdResponseEntity(@PathVariable(required = true) Long id){
        return ResponseEntity.ok(usuarioService.buscarUsuarioPorId(id));
    }

    @PutMapping("/{id}")
    public ResponseEntity<Usuario> editarUsuarioResponseEntity(@PathVariable(required = true) Long id,@RequestBody Usuario Usuario){
        Usuario.setIdUsuario(id);
        return ResponseEntity.ok(usuarioService.editarUsuario(Usuario));
    }

    @DeleteMapping("{id}")
    public ResponseEntity<String> eliminarUsuario(@PathVariable(required = true) Long id) {
        try {
            // Lógica para eliminar el Usuario
            usuarioService.eliminarUsuario(id);

            // Retornar código 200 OK con mensaje de éxito
            return ResponseEntity.ok("Usuario eliminada exitosamente.");
        } catch (Exception e) {
            // En caso de error, retornar un código de error y mensaje apropiado
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Error al eliminar la Usuario: " + e.getMessage());
        }
    }
}
