package upeu.edu.pe.msmatriculas.feign;

import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import upeu.edu.pe.msmatriculas.dto.Pago;

import java.util.ArrayList;
import java.util.List;

@FeignClient(name = "ms-pago-service", path = "/pago")
public interface PagoFeign {

    @PostMapping
    @CircuitBreaker(name = "crearPagoCB", fallbackMethod = "fallbackMethodCrearPago")
    ResponseEntity<?> crearPagoDto(@RequestBody Pago pago);

    @GetMapping
    @CircuitBreaker(name = "listarPagosCB", fallbackMethod = "fallbackMethodListarPagos")
    ResponseEntity<List<Pago>> listarPagosDto();

    @GetMapping("/{id}")
    @CircuitBreaker(name = "listarPagoPorIdCB", fallbackMethod = "fallbackMethodListarPagoPorId")
    ResponseEntity<Pago> listarPagoDtoPorId(@PathVariable Long id);

    @PutMapping("/{id}")  // Añadir la ruta con el ID
    @CircuitBreaker(name = "actualizarPagoCB", fallbackMethod = "fallbackMethodActualizarPago")
    ResponseEntity<Pago> actualizarPagoDto(@PathVariable Long id, @RequestBody Pago pago);

    @DeleteMapping("/{id}")  // Añadir la ruta con el ID
    @CircuitBreaker(name = "eliminarPagoCB", fallbackMethod = "fallbackMethodEliminarPago")
    ResponseEntity<String> eliminarPagoDto(@PathVariable Long id);

    default ResponseEntity<?> fallbackMethodCrearPago(Pago pago, Exception e){
        return ResponseEntity.ok(new Pago());
    }

    default ResponseEntity<List<Pago>> fallbackMethodListarPagos(Exception e){
        return ResponseEntity.ok(new ArrayList<Pago>());
    }

    default ResponseEntity<Pago> fallbackMethodListarPagoPorId(Long id, Exception e){
        return ResponseEntity.ok(new Pago());
    }

    default ResponseEntity<Pago> fallbackMethodActualizarPago(Long id, Pago pago, Exception e){
        return ResponseEntity.ok(new Pago());
    }

    default ResponseEntity<String> fallbackMethodEliminarPago(Long id, Exception e){
        return ResponseEntity.ok("Error al eliminar el Pago. Fallback activado");
    }
}