/*package upeu.edu.pe.mspersona.feign;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import upeu.edu.pe.mspersona.dto.Rol;

@FeignClient(name = "ms-roles-service", path = "/rol")
public interface RolFeign {

    @GetMapping("/{id}")
    public ResponseEntity<Rol> listarRolDtoPorId(@PathVariable(required = true) Long id);
}
*/