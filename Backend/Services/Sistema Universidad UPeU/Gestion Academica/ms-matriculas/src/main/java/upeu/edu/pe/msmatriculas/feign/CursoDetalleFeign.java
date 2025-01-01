package upeu.edu.pe.msmatriculas.feign;

import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import upeu.edu.pe.msmatriculas.dto.CursoDetalle;

import java.util.ArrayList;
import java.util.List;

@FeignClient(name = "ms-nivelesDeEnsenanza-service", path = "/cursoDetalle")
public interface CursoDetalleFeign {

    @GetMapping
    @CircuitBreaker(name = "listarCursosDetalleCB", fallbackMethod = "fallbackMethodListarCursosDetalle")
    ResponseEntity<List<CursoDetalle>> listarCursosDetalle();

    @GetMapping("/{id}")
    @CircuitBreaker(name = "listarCursoDetallePorIdCB", fallbackMethod = "fallbackMethodListarCursoDetallePorId")
    ResponseEntity<CursoDetalle> listarCursoDetallePorId(@PathVariable Long id);

    @PostMapping
    @CircuitBreaker(name = "crearCursoDetalleCB", fallbackMethod = "fallbackMethodCrearCursoDetalle")
    ResponseEntity<CursoDetalle> crearCursoDetalle(@RequestBody CursoDetalle cursoDetalle);

    @PutMapping("/{id}")
    @CircuitBreaker(name = "actualizarCursoDetalleCB", fallbackMethod = "fallbackMethodActualizarCursoDetalle")
    ResponseEntity<CursoDetalle> actualizarCursoDetalle(@PathVariable Long id, @RequestBody CursoDetalle cursoDetalle);

    @DeleteMapping("/{id}")
    @CircuitBreaker(name = "eliminarCursoDetalleCB", fallbackMethod = "fallbackMethodEliminarCursoDetalle")
    ResponseEntity<Void> eliminarCursoDetalle(@PathVariable Long id);

    default ResponseEntity<List<CursoDetalle>> fallbackMethodListarCursosDetalle(Exception e) {
        return ResponseEntity.ok(new ArrayList<CursoDetalle>());
    }

    default ResponseEntity<CursoDetalle> fallbackMethodListarCursoDetallePorId(Long id, Exception e) {
        return ResponseEntity.ok(new CursoDetalle());
    }

    default ResponseEntity<CursoDetalle> fallbackMethodCrearCursoDetalle(CursoDetalle cursoDetalle, Exception e) {
        return ResponseEntity.ok(new CursoDetalle());
    }

    default ResponseEntity<CursoDetalle> fallbackMethodActualizarCursoDetalle(Long id, CursoDetalle cursoDetalle, Exception e) {
        return ResponseEntity.ok(new CursoDetalle());
    }

    default ResponseEntity<String> fallbackMethodEliminarCursoDetalle(Long id, Exception e) {
        return ResponseEntity.ok("Error al eliminar el CursoDetalle. Fallback activado");
    }
}