package upeu.edu.pe.msauth.feign;

import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import upeu.edu.pe.msauth.dto.Rol;

@FeignClient(name = "ms-roles-service", path = "/rol")
public interface RolFeign {

    @GetMapping("/{id}")
    @CircuitBreaker(name = "rolListarPorIdCB", fallbackMethod = "fallBackRolListarPorId")
    public ResponseEntity<Rol> listarRolDtoPorId(@PathVariable(required = true) Long id);

    //Metodos default fallback
    default ResponseEntity<Rol> fallBackRolListarPorId(Long id, Exception e) {
        return ResponseEntity.ok(new Rol());
    }
}