package upeu.edu.pe.msusuarios.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import upeu.edu.pe.msusuarios.entity.Usuario;
import upeu.edu.pe.msusuarios.service.UsuarioService;

import java.util.List;

@RestController
@RequestMapping("/usuario")
public class UsuarioController {
    @Autowired
    private UsuarioService usuarioService;

    @PostMapping
    public ResponseEntity<Usuario> guardarUsuarioResponseEntity(@RequestBody Usuario Usuario){
        return ResponseEntity.ok(usuarioService.guardarUsuario(Usuario));
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
