package upeu.edu.pe.msmatriculas.feign;

import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import upeu.edu.pe.msmatriculas.dto.Requisito;

import java.util.ArrayList;
import java.util.List;

@FeignClient(name = "ms-requisitosAcademicos-service", path = "/requisito")
public interface RequisitoFeign {

    @PostMapping
    @CircuitBreaker(name = "crearRequisitoCB", fallbackMethod = "fallbackMethodCrearRequisito")
    ResponseEntity<?> crearRequisitoDto(@RequestBody Requisito requisito);

    @GetMapping
    @CircuitBreaker(name = "listarRequisitosCB", fallbackMethod = "fallbackMethodListarRequisitos")
    ResponseEntity<List<Requisito>> listarRequisitosDto();

    @GetMapping("/{id}")
    @CircuitBreaker(name = "listarRequisitoPorIdCB", fallbackMethod = "fallbackMethodListarRequisitoPorId")
    ResponseEntity<Requisito> listarRequisitoDtoPorId(@PathVariable Long id);

    @PutMapping("/{id}")  // Añadir la ruta con el ID
    @CircuitBreaker(name = "actualizarRequisitoCB", fallbackMethod = "fallbackMethodActualizarRequisito")
    ResponseEntity<Requisito> actualizarRequisitoDto(@PathVariable Long id, @RequestBody Requisito requisito);

    @DeleteMapping("/{id}")  // Añadir la ruta con el ID
    @CircuitBreaker(name = "eliminarRequisitoCB", fallbackMethod = "fallbackMethodEliminarRequisito")
    ResponseEntity<String> eliminarRequisitoDto(@PathVariable Long id);

    @GetMapping("/validar")
    @CircuitBreaker(name = "validarRequisitoCB", fallbackMethod = "fallbackMethodValidarRequisito")
    ResponseEntity<Boolean> validarRequisitos(@RequestParam Long idEstudiante, @RequestParam Long idCarrera);

    default ResponseEntity<?> fallbackMethodCrearRequisito(Requisito requisito, Exception e){
        return ResponseEntity.ok(new Requisito());
    }

    default ResponseEntity<List<Requisito>> fallbackMethodListarRequisitos(Exception e){
        return ResponseEntity.ok(new ArrayList<Requisito>());
    }

    default ResponseEntity<Requisito> fallbackMethodListarRequisitoPorId(Long id, Exception e){
        return ResponseEntity.ok(new Requisito());
    }

    default ResponseEntity<Requisito> fallbackMethodActualizarRequisito(Long id, Requisito requisito, Exception e){
        return ResponseEntity.ok(new Requisito());
    }

    default ResponseEntity<String> fallbackMethodEliminarRequisito(Long id, Exception e){
        return ResponseEntity.ok("Error al eliminar el Requisito. Fallback activado");
    }
}