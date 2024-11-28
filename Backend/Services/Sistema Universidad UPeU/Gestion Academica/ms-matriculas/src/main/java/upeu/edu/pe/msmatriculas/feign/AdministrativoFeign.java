package upeu.edu.pe.msmatriculas.feign;

import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import upeu.edu.pe.msmatriculas.dto.Administrativo;

import java.util.ArrayList;
import java.util.List;

@FeignClient(name = "ms-administrativo-service", path = "/administrativo")
public interface AdministrativoFeign {

    @PostMapping
    @CircuitBreaker(name = "crearAdministrativoCB", fallbackMethod = "fallbackMethodCrearAdministrativo")
    ResponseEntity<?> crearAdministrativoDto(@RequestBody Administrativo administrativo);

    @GetMapping
    @CircuitBreaker(name = "listarAdministrativosCB", fallbackMethod = "fallbackMethodListarAdministrativos")
    ResponseEntity<List<Administrativo>> listarAdministrativosDto();

    @GetMapping("/{id}")
    @CircuitBreaker(name = "listarAdministrativoPorIdCB", fallbackMethod = "fallbackMethodListarAdministrativoPorId")
    ResponseEntity<Administrativo> listarAdministrativoDtoPorId(@PathVariable Long id);

    @PutMapping("/{id}")  // Añadir la ruta con el ID
    @CircuitBreaker(name = "actualizarAdministrativoCB", fallbackMethod = "fallbackMethodActualizarAdministrativo")
    ResponseEntity<Administrativo> actualizarAdministrativoDto(@PathVariable Long id, @RequestBody Administrativo administrativo);

    @DeleteMapping("/{id}")  // Añadir la ruta con el ID
    @CircuitBreaker(name = "eliminarAdministrativoCB", fallbackMethod = "fallbackMethodEliminarAdministrativo")
    ResponseEntity<String> eliminarAdministrativoDto(@PathVariable Long id);

    default ResponseEntity<?> fallbackMethodCrearAdministrativo(Administrativo administrativo, Exception e){
        return ResponseEntity.ok(new Administrativo());
    }

    default ResponseEntity<List<Administrativo>> fallbackMethodListarAdministrativos(Exception e) {
        return ResponseEntity.ok(new ArrayList<Administrativo>());
    }

    default ResponseEntity<Administrativo> fallbackMethodListarAdministrativoPorId(Long id, Exception e){
        return ResponseEntity.ok(new Administrativo());
    }

    default ResponseEntity<Administrativo> fallbackMethodActualizarAdministrativo(Long id, Administrativo administrativo, Exception e){
        return ResponseEntity.ok(new Administrativo());
    }

    default ResponseEntity<String> fallbackMethodEliminarAdministrativo(Long id, Exception e){
        return ResponseEntity.ok("Error al eliminar el administrativo. Fallback activado");
    }
}