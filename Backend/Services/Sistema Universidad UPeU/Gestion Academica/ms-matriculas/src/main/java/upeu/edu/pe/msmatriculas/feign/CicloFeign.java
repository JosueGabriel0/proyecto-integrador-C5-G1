package upeu.edu.pe.msmatriculas.feign;

import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import upeu.edu.pe.msmatriculas.dto.Ciclo;

import java.util.ArrayList;
import java.util.List;

@FeignClient(name = "ms-planificacionAcademica-service", path = "/ciclo")
public interface CicloFeign {

    @PostMapping
    @CircuitBreaker(name = "crearCicloCB", fallbackMethod = "fallbackMethodCrearCiclo")
    ResponseEntity<Ciclo> crearCicloDto(@RequestBody Ciclo ciclo);

    @GetMapping
    @CircuitBreaker(name = "listarCiclosCB", fallbackMethod = "fallbackMethodListarCiclos")
    ResponseEntity<List<Ciclo>> listarCiclosDto();

    @GetMapping("/{idCiclo}")
    @CircuitBreaker(name = "listarCicloPorIdCB", fallbackMethod = "fallbackMethodListarCicloPorId")
    ResponseEntity<Ciclo> listarCicloDtoPorId(@PathVariable(required = true) Long idCiclo);

    @PutMapping("/{idCiclo}")  // Añadir la ruta con el ID
    @CircuitBreaker(name = "actualizarCicloCB", fallbackMethod = "fallbackMethodActualizarCiclo")
    ResponseEntity<Ciclo> actualizarCicloDto(@PathVariable(required = true) Long idCiclo, @RequestBody Ciclo ciclo);

    @DeleteMapping("/{idCiclo}")  // Añadir la ruta con el ID
    @CircuitBreaker(name = "eliminarCicloCB", fallbackMethod = "fallbackMethodEliminarCiclo")
    ResponseEntity<Void> eliminarCicloDto(@PathVariable(required = true) Long idCiclo);

    default ResponseEntity<Ciclo> fallbackMethodCrearCiclo(Ciclo ciclo, Exception e){
        return ResponseEntity.ok(new Ciclo());
    }

    default ResponseEntity<List<Ciclo>> fallbackMethodListarCiclos(Exception e){
        return ResponseEntity.ok(new ArrayList<Ciclo>());
    }

    default ResponseEntity<Ciclo> fallbackMethodListarCicloPorId(Long idCiclo, Exception e){
        return ResponseEntity.ok(new Ciclo());
    }

    default ResponseEntity<Ciclo> fallbackMethodActualizarCiclo(Long idCiclo, Ciclo ciclo, Exception e){
        return ResponseEntity.ok(new Ciclo());
    }

    default ResponseEntity<String> fallbackMethodEliminarCiclo(Long idCiclo, Exception e){
        return ResponseEntity.ok("Error al eliminar el Ciclo. Fallback activado");
    }
}
