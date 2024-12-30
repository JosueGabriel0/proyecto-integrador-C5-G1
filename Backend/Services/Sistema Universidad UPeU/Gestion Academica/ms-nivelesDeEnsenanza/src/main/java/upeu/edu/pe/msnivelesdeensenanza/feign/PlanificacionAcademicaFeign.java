package upeu.edu.pe.msnivelesdeensenanza.feign;

import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import upeu.edu.pe.msnivelesdeensenanza.dto.PlanificacionAcademica;

import java.util.ArrayList;
import java.util.List;

@FeignClient(name = "ms-planificacionAcademica-service", path = "/planificacionAcademica")
public interface PlanificacionAcademicaFeign {

    @PostMapping
    @CircuitBreaker(name = "crearPlanificacionAcademicaCB", fallbackMethod = "fallbackMethodCrearPlanificacionAcademica")
    ResponseEntity<PlanificacionAcademica> crearPlanificacionAcademicaDto(@RequestBody PlanificacionAcademica planificacionAcademica);

    @GetMapping
    @CircuitBreaker(name = "listarPlanificacionesAcademicasCB", fallbackMethod = "fallbackMethodListarPlanificacionesAcademicas")
    ResponseEntity<List<PlanificacionAcademica>> listarPlanificacionesAcademicasDto();

    @GetMapping("/{id}")
    @CircuitBreaker(name = "listarPlanificacionAcademicaPorIdCB", fallbackMethod = "fallbackMethodListarPlanificacionAcademicaPorId")
    ResponseEntity<PlanificacionAcademica> listarPlanificacionAcademicaDtoPorId(@PathVariable(required = true) Long id);

    @PutMapping("/{id}")  // Añadir la ruta con el ID
    @CircuitBreaker(name = "actualizarPlanificacionAcademicaCB", fallbackMethod = "fallbackMethodActualizarPlanificacionAcademica")
    ResponseEntity<PlanificacionAcademica> actualizarPlanificacionAcademicaDto(@PathVariable(required = true) Long id, @RequestBody PlanificacionAcademica planificacionAcademica);

    @DeleteMapping("/{id}")  // Añadir la ruta con el ID
    @CircuitBreaker(name = "eliminarPlanificacionAcademicaCB", fallbackMethod = "fallbackMethodEliminarPlanificacionAcademica")
    ResponseEntity<String> eliminarPlanificacionAcademicaDto(@PathVariable(required = true) Long id);

    default ResponseEntity<PlanificacionAcademica> fallbackMethodCrearPlanificacionAcademica(PlanificacionAcademica planificacionAcademica, Exception e){
        return ResponseEntity.ok(new PlanificacionAcademica());
    }

    default ResponseEntity<List<PlanificacionAcademica>> fallbackMethodListarPlanificacionesAcademicas(Exception e){
        return ResponseEntity.ok(new ArrayList<PlanificacionAcademica>());
    }

    default ResponseEntity<PlanificacionAcademica> fallbackMethodListarPlanificacionAcademicaPorId(Long id, Exception e){
        return ResponseEntity.ok(new PlanificacionAcademica());
    }

    default ResponseEntity<PlanificacionAcademica> fallbackMethodActualizarPlanificacionAcademica(Long id, PlanificacionAcademica planificacionAcademica, Exception e){
        return ResponseEntity.ok(new PlanificacionAcademica());
    }

    default ResponseEntity<String> fallbackMethodEliminarPlanificacionAcademica(Long id, Exception e){
        return ResponseEntity.ok("Error al eliminar la PlanificacionAcademica. Fallback activado");
    }
}
