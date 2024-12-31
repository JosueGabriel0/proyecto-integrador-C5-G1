package upeu.edu.pe.msmatriculas.feign;

import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import upeu.edu.pe.msmatriculas.dto.Administrativo;
import upeu.edu.pe.msmatriculas.dto.NivelEnsenanza;

import java.util.ArrayList;
import java.util.List;

@FeignClient(name = "ms-nivelesDeEnsenanza-service", path = "/niveles-ensenanza")
public interface NivelEnsenanzaFeign {

    @PostMapping
    @CircuitBreaker(name = "crearNivelEnsenanzaCB", fallbackMethod = "fallbackMethodCrearNivelEnsenanza")
    ResponseEntity<NivelEnsenanza> crearNivelDeEnsenanzaDto(@RequestBody NivelEnsenanza nivelEnsenanza);

    @GetMapping
    @CircuitBreaker(name = "listarNivelesDeEnsenanzaCB", fallbackMethod = "fallbackMethodListarNivelesDeEnsenanza")
    ResponseEntity<List<NivelEnsenanza>> listarNivelDeEnsenanzaDto();

    @GetMapping("/{id}")
    @CircuitBreaker(name = "listarNivelEnsenanzaPorIdCB", fallbackMethod = "fallbackMethodListarNivelEnsenanzaPorId")
    ResponseEntity<NivelEnsenanza> listarNivelDeEnsenanzaDtoPorId(@PathVariable Long id);

    @PutMapping("/{id}")  // Añadir la ruta con el ID
    @CircuitBreaker(name = "actualizarNivelEnsenanzaCB", fallbackMethod = "fallbackMethodActualizarNivelEnsenanza")
    ResponseEntity<NivelEnsenanza> actualizarNivelDeEnsenanzaDto(@PathVariable Long id, @RequestBody NivelEnsenanza nivelEnsenanza);

    @DeleteMapping("/{id}")  // Añadir la ruta con el ID
    @CircuitBreaker(name = "eliminarNivelEnsenanzaCB", fallbackMethod = "fallbackMethodEliminarNivelEnsenanza")
    ResponseEntity<Void> eliminarNivelDeEnsenanzaDto(@PathVariable(required = true) Long id);

    default ResponseEntity<NivelEnsenanza> fallbackMethodCrearNivelEnsenanza(NivelEnsenanza nivelEnsenanza, Exception e){
        return ResponseEntity.ok(new NivelEnsenanza());
    }

    default ResponseEntity<List<NivelEnsenanza>> fallbackMethodListarNivelesDeEnsenanza(Exception e) {
        return ResponseEntity.ok(new ArrayList<NivelEnsenanza>());
    }

    default ResponseEntity<NivelEnsenanza> fallbackMethodListarNivelEnsenanzaPorId(Long id, Exception e){
        return ResponseEntity.ok(new NivelEnsenanza());
    }

    default ResponseEntity<NivelEnsenanza> fallbackMethodActualizarNivelEnsenanza(Long id, NivelEnsenanza nivelEnsenanza, Exception e){
        return ResponseEntity.ok(new NivelEnsenanza());
    }

    default ResponseEntity<String> fallbackMethodEliminarNivelEnsenanza(Long id, Exception e){
        return ResponseEntity.ok("Error al eliminar el Nivel de Ensenanza. Fallback activado");
    }
}