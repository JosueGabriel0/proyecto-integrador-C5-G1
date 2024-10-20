package upeu.edu.pe.msinscripciones.feign;

import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import upeu.edu.pe.msinscripciones.dto.Rol;
import upeu.edu.pe.msinscripciones.dto.Rol;

import java.util.ArrayList;
import java.util.List;

@FeignClient(name = "ms-roles-service", path = "/rol")
public interface RolFeign {

    @PostMapping
    //@CircuitBreaker(name = "crearRolCB", fallbackMethod = "fallbackMethodCrearRol")
    ResponseEntity<Rol> crearRolDto(@RequestBody Rol rol);

    @GetMapping
    @CircuitBreaker(name = "listarRolesCB", fallbackMethod = "fallbackMethodListarRoles")
    ResponseEntity<List<Rol>> listarRolesDto();

    @GetMapping("/{id}")
    @CircuitBreaker(name = "listarRolPorIdCB", fallbackMethod = "fallbackMethodListarRolPorId")
    ResponseEntity<Rol> listarRolDtoPorId(@PathVariable Long id);

    @PutMapping("/{id}")  // Añadir la ruta con el ID
    @CircuitBreaker(name = "actualizarRolCB", fallbackMethod = "fallbackMethodActualizarRol")
    ResponseEntity<Rol> actualizarRolDto(@PathVariable Long id, @RequestBody Rol rol);

    @DeleteMapping("/{id}")  // Añadir la ruta con el ID
    @CircuitBreaker(name = "eliminarRolCB", fallbackMethod = "fallbackMethodEliminarRol")
    ResponseEntity<String> eliminarRolDto(@PathVariable Long id);

    default ResponseEntity<Rol> fallbackMethodCrearRol(Rol Rol, Exception e){
        return ResponseEntity.ok(new Rol());
    }

    default ResponseEntity<List<Rol>> fallbackMethodListarRoles(Exception e){
        return ResponseEntity.ok(new ArrayList<Rol>());
    }

    default ResponseEntity<Rol> fallbackMethodListarRolPorId(Long id, Exception e){
        return ResponseEntity.ok(new Rol());
    }

    default ResponseEntity<Rol> fallbackMethodActualizarRol(Long id, Rol Rol, Exception e){
        return ResponseEntity.ok(new Rol());
    }

    default ResponseEntity<String> fallbackMethodEliminarRol(Long id, Exception e){
        return ResponseEntity.ok("Error al eliminar el Rol. Fallback activado");
    }
}
