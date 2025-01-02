package upeu.edu.pe.msestudiante.feign;

import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import upeu.edu.pe.msestudiante.dto.CuentaFinanciera;

import java.util.ArrayList;
import java.util.List;

@FeignClient(name = "ms-cuentaFinancieraUniversitaria-service", path = "/cuentaFinanciera")
public interface CuentaFinancieraFeign {

    @PostMapping
    @CircuitBreaker(name = "crearCuentaFinancieraCB", fallbackMethod = "fallbackMethodCrearCuentaFinanciera")
    ResponseEntity<CuentaFinanciera> crearCuentaFinancieraDto(@RequestBody CuentaFinanciera cuentaFinanciera);

    @GetMapping
    @CircuitBreaker(name = "listarCuentasFinancierasCB", fallbackMethod = "fallbackMethodListarCuentasFinancieras")
    ResponseEntity<List<CuentaFinanciera>> listarCuentasFinancierasDto();

    @GetMapping("/{id}")
    @CircuitBreaker(name = "listarCuentaFinancieraPorIdCB", fallbackMethod = "fallbackMethodListarCuentaFinancieraPorId")
    ResponseEntity<CuentaFinanciera> listarCuentaFinancieraDtoPorId(@PathVariable(required = true) Long id);

    @PutMapping("/{id}")  // Añadir la ruta con el ID
    @CircuitBreaker(name = "actualizarCuentaFinancieraCB", fallbackMethod = "fallbackMethodActualizarCuentaFinanciera")
    ResponseEntity<CuentaFinanciera> actualizarCuentaFinancieraDto(@PathVariable(required = true) Long id, @RequestBody CuentaFinanciera cuentaFinanciera);

    @DeleteMapping("/{id}")  // Añadir la ruta con el ID
    @CircuitBreaker(name = "eliminarCuentaFinancieraCB", fallbackMethod = "fallbackMethodEliminarCuentaFinanciera")
    ResponseEntity<Void> eliminarCuentaFinancieraDto(@PathVariable Long id);

    default ResponseEntity<CuentaFinanciera> fallbackMethodCrearCuentaFinanciera(CuentaFinanciera cuentaFinanciera, Exception e){
        return ResponseEntity.ok(new CuentaFinanciera());
    }

    default ResponseEntity<List<CuentaFinanciera>> fallbackMethodListarCuentasFinancieras(Exception e){
        return ResponseEntity.ok(new ArrayList<CuentaFinanciera>());
    }

    default ResponseEntity<CuentaFinanciera> fallbackMethodListarCuentaFinancieraPorId(Long id, Exception e){
        return ResponseEntity.ok(new CuentaFinanciera());
    }

    default ResponseEntity<CuentaFinanciera> fallbackMethodActualizarCuentaFinanciera(Long id, CuentaFinanciera cuentaFinanciera, Exception e){
        return ResponseEntity.ok(new CuentaFinanciera());
    }

    default ResponseEntity<String> fallbackMethodEliminarCuentaFinanciera(Long id, Exception e){
        return ResponseEntity.ok("Error al eliminar el CuentaFinanciera. Fallback activado");
    }
}