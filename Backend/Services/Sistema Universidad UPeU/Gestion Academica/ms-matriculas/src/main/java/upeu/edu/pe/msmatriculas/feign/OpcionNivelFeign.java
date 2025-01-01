package upeu.edu.pe.msmatriculas.feign;

import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import upeu.edu.pe.msmatriculas.dto.OpcionNivel;

import java.util.ArrayList;
import java.util.List;

@FeignClient(name = "ms-nivelesDeEnsenanza-service", path = "/opciones-nivel")
public interface OpcionNivelFeign {

    @GetMapping("/{nivelId}/opciones")
    @CircuitBreaker(name = "listarOpcionesPorNivelCB", fallbackMethod = "fallbackMethodListarOpcionesPorNivel")
    ResponseEntity<List<OpcionNivel>> listarOpcionesPorNivelDto(@PathVariable Long nivelId);

    @PostMapping
    @CircuitBreaker(name = "crearOpcionNivelCB", fallbackMethod = "fallbackMethodCrearOpcionNivel")
    ResponseEntity<OpcionNivel> crearOpcionNivelDto(@RequestBody OpcionNivel opcionNivel);

    @GetMapping
    @CircuitBreaker(name = "listarOpcionesDeNivelCB", fallbackMethod = "fallbackMethodListarOpcionesDeNivel")
    ResponseEntity<List<OpcionNivel>> listarOpcionesDeNivelDto();

    @GetMapping("/{id}")
    @CircuitBreaker(name = "listarOpcionNivelPorIdCB", fallbackMethod = "fallbackMethodListarOpcionNivelPorId")
    ResponseEntity<OpcionNivel> listarOpcionNivelDtoPorId(@PathVariable Long id);

    @PutMapping("/{id}")  // Añadir la ruta con el ID
    @CircuitBreaker(name = "actualizarOpcionNivelCB", fallbackMethod = "fallbackMethodActualizarOpcionNivel")
    ResponseEntity<OpcionNivel> actualizarOpcionNivelDto(@PathVariable Long id, @RequestBody OpcionNivel opcionNivel);

    @DeleteMapping("/{id}")  // Añadir la ruta con el ID
    @CircuitBreaker(name = "eliminarOpcionNivelCB", fallbackMethod = "fallbackMethodEliminarOpcionNivel")
    ResponseEntity<Void> eliminarOpcionNivelDto(@PathVariable Long id);

    default ResponseEntity<List<OpcionNivel>> fallbackMethodListarOpcionesPorNivel(Long nivelId){
        return ResponseEntity.ok(new ArrayList<OpcionNivel>());
    }

    default ResponseEntity<OpcionNivel> fallbackMethodCrearOpcionNivel(OpcionNivel opcionNivel, Exception e){
        return ResponseEntity.ok(new OpcionNivel());
    }

    default ResponseEntity<List<OpcionNivel>> fallbackMethodListarOpcionesDeNivel(Exception e) {
        return ResponseEntity.ok(new ArrayList<OpcionNivel>());
    }

    default ResponseEntity<OpcionNivel> fallbackMethodListarOpcionNivelPorId(Long id, Exception e){
        return ResponseEntity.ok(new OpcionNivel());
    }

    default ResponseEntity<OpcionNivel> fallbackMethodActualizarOpcionNivel(Long id, OpcionNivel opcionNivel, Exception e){
        return ResponseEntity.ok(new OpcionNivel());
    }

    default ResponseEntity<String> fallbackMethodEliminarOpcionNivel(Long id, Exception e){
        return ResponseEntity.ok("Error al eliminar el Nivel de Ensenanza. Fallback activado");
    }
}