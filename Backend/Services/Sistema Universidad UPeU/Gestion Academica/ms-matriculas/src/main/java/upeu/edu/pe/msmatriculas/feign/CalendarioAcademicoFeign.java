package upeu.edu.pe.msmatriculas.feign;

import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import upeu.edu.pe.msmatriculas.dto.CalendarioAcademico;

import java.util.ArrayList;
import java.util.List;

@FeignClient(name = "ms-calendarioAcademico-service", path = "/calendarioAcademico")
public interface CalendarioAcademicoFeign {
    @PostMapping
    @CircuitBreaker(name = "crearCalendarioAcademicoCB", fallbackMethod = "fallbackMethodCrearCalendarioAcademico")
    ResponseEntity<?> crearCalendarioAcademicoDto(@RequestBody CalendarioAcademico CalendarioAcademico);

    @GetMapping
    @CircuitBreaker(name = "listarCalendarioAcademicosCB", fallbackMethod = "fallbackMethodListarCalendarioAcademicos")
    ResponseEntity<List<CalendarioAcademico>> listarCalendarioAcademicosDto();

    @GetMapping("/{id}")
    @CircuitBreaker(name = "listarCalendarioAcademicoPorIdCB", fallbackMethod = "fallbackMethodListarCalendarioAcademicoPorId")
    ResponseEntity<CalendarioAcademico> listarCalendarioAcademicoDtoPorId(@PathVariable Long id);

    @PutMapping("/{id}")  // Añadir la ruta con el ID
    @CircuitBreaker(name = "actualizarCalendarioAcademicoCB", fallbackMethod = "fallbackMethodActualizarCalendarioAcademico")
    ResponseEntity<CalendarioAcademico> actualizarCalendarioAcademicoDto(@PathVariable Long id, @RequestBody CalendarioAcademico CalendarioAcademico);

    @DeleteMapping("/{id}")  // Añadir la ruta con el ID
    @CircuitBreaker(name = "eliminarCalendarioAcademicoCB", fallbackMethod = "fallbackMethodEliminarCalendarioAcademico")
    ResponseEntity<String> eliminarCalendarioAcademicoDto(@PathVariable Long id);

    default ResponseEntity<?> fallbackMethodCrearCalendarioAcademico(CalendarioAcademico CalendarioAcademico, Exception e){
        return ResponseEntity.ok(new CalendarioAcademico());
    }

    default ResponseEntity<List<CalendarioAcademico>> fallbackMethodListarCalendarioAcademicos(Exception e) {
        return ResponseEntity.ok(new ArrayList<CalendarioAcademico>());
    }

    default ResponseEntity<CalendarioAcademico> fallbackMethodListarCalendarioAcademicoPorId(Long id, Exception e){
        return ResponseEntity.ok(new CalendarioAcademico());
    }

    default ResponseEntity<CalendarioAcademico> fallbackMethodActualizarCalendarioAcademico(Long id, CalendarioAcademico CalendarioAcademico, Exception e){
        return ResponseEntity.ok(new CalendarioAcademico());
    }

    default ResponseEntity<String> fallbackMethodEliminarCalendarioAcademico(Long id, Exception e){
        return ResponseEntity.ok("Error al eliminar el CalendarioAcademico. Fallback activado");
    }
}
