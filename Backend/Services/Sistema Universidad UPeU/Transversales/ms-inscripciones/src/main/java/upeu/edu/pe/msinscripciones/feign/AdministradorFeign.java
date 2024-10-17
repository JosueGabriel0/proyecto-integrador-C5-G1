package upeu.edu.pe.msinscripciones.feign;

import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import upeu.edu.pe.msinscripciones.dto.Administrador;

import java.util.ArrayList;
import java.util.List;

@FeignClient(name = "ms-administrador-service", path = "/administrador")
public interface AdministradorFeign {

    @PostMapping
    @CircuitBreaker(name = "crearAdministradorCB", fallbackMethod = "fallbackMethodCrearAdministrador")
    ResponseEntity<?> crearAdministradorDto(@RequestBody Administrador administrador);

    @GetMapping
    @CircuitBreaker(name = "listarAdminstradoresCB", fallbackMethod = "fallbackMethodListarAdministradores")
    ResponseEntity<List<Administrador>> listarAdministradorsDto();

    @GetMapping("/{id}")
    @CircuitBreaker(name = "listarAdministradorPorIdCB", fallbackMethod = "fallbackMethodListarAdministradorPorId")
    ResponseEntity<Administrador> listarAdministradorDtoPorId(@PathVariable Long id);

    @PutMapping("/{id}")  // Añadir la ruta con el ID
    @CircuitBreaker(name = "actualizarAdministradorCB", fallbackMethod = "fallbackMethodActualizarAdministrador")
    ResponseEntity<Administrador> actualizarAdministradorDto(@PathVariable Long id, @RequestBody Administrador administrador);

    @DeleteMapping("/{id}")  // Añadir la ruta con el ID
    @CircuitBreaker(name = "eliminarAdministradorCB", fallbackMethod = "fallbackMethodEliminarAdministrador")
    ResponseEntity<String> eliminarAdministradorDto(@PathVariable Long id);

    default ResponseEntity<?> fallbackMethodCrearAdministrador(Administrador administrador, Exception e){
        return ResponseEntity.ok(new Administrador());
    }

    default ResponseEntity<List<Administrador>> fallbackMethodListarAdministradores(Exception e){
        return ResponseEntity.ok(new ArrayList<Administrador>());
    }

    default ResponseEntity<Administrador> fallbackMethodListarAdministradorPorId(Long id, Exception e){
        return ResponseEntity.ok(new Administrador());
    }

    default ResponseEntity<Administrador> fallbackMethodActualizarAdministrador(Long id, Administrador administrador, Exception e){
        return ResponseEntity.ok(new Administrador());
    }

    default ResponseEntity<String> fallbackMethodEliminarAdministrador(Long id, Exception e){
        return ResponseEntity.ok("Error al eliminar el administrador. Fallback activado.");
    }
}