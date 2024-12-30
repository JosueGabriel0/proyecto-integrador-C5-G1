package upeu.edu.pe.msnivelesdeensenanza.feign;

import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import upeu.edu.pe.msnivelesdeensenanza.dto.Carrera;

import java.util.ArrayList;
import java.util.List;

@FeignClient(name = "ms-carrera-service", path = "/carrera")
public interface CarreraFeign {

    @PostMapping
    @CircuitBreaker(name = "crearCarreraCB", fallbackMethod = "fallbackMethodCrearCarrera")
    ResponseEntity<?> crearCarreraDto(@RequestBody Carrera Carrera);

    @GetMapping
    @CircuitBreaker(name = "listarCarrerasCB", fallbackMethod = "fallbackMethodListarCarreras")
    ResponseEntity<List<Carrera>> listarCarrerasDto();

    @GetMapping("/{id}")
    @CircuitBreaker(name = "listarCarreraPorIdCB", fallbackMethod = "fallbackMethodListarCarreraPorId")
    ResponseEntity<Carrera> listarCarreraDtoPorId(@PathVariable Long id);

    @PutMapping("/{id}")  // Añadir la ruta con el ID
    @CircuitBreaker(name = "actualizarCarreraCB", fallbackMethod = "fallbackMethodActualizarCarrera")
    ResponseEntity<Carrera> actualizarCarreraDto(@PathVariable Long id, @RequestBody Carrera Carrera);

    @DeleteMapping("/{id}")  // Añadir la ruta con el ID
    @CircuitBreaker(name = "eliminarCarreraCB", fallbackMethod = "fallbackMethodEliminarCarrera")
    ResponseEntity<String> eliminarCarreraDto(@PathVariable Long id);

    default ResponseEntity<?> fallbackMethodCrearCarrera(Carrera carrera, Exception e){
        return ResponseEntity.ok(new Carrera());
    }

    default ResponseEntity<List<Carrera>> fallbackMethodListarCarreras(Exception e){
        return ResponseEntity.ok(new ArrayList<Carrera>());
    }

    default ResponseEntity<Carrera> fallbackMethodListarCarreraPorId(Long id, Exception e){
        return ResponseEntity.ok(new Carrera());
    }

    default ResponseEntity<Carrera> fallbackMethodActualizarCarrera(Long id, Carrera carrera, Exception e){
        return ResponseEntity.ok(new Carrera());
    }

    default ResponseEntity<String> fallbackMethodEliminarCarrera(Long id, Exception e){
        return ResponseEntity.ok("Error al eliminar la Carrera. Fallback activado");
    }
}