package upeu.edu.pe.mspersona.feign;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import upeu.edu.pe.mspersona.dto.Usuario;

@FeignClient(name = "ms-usuarios-service", path = "/usuario")
public interface UsuarioFeign {

    @GetMapping("/{id}")
    public ResponseEntity<Usuario> listarUsuarioDtoPorId(@PathVariable(required = true) Long id);
}
