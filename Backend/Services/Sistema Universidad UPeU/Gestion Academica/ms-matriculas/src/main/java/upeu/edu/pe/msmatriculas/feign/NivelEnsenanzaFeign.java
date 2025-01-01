package upeu.edu.pe.msmatriculas.feign;

import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import upeu.edu.pe.msmatriculas.dto.Ciclo;
import upeu.edu.pe.msmatriculas.dto.CursoDetalle;
import upeu.edu.pe.msmatriculas.dto.NivelEnsenanza;
import upeu.edu.pe.msmatriculas.dto.OpcionNivel;

import java.util.ArrayList;
import java.util.List;

@FeignClient(name = "ms-nivelesDeEnsenanza-service")
public interface NivelEnsenanzaFeign {

    @PostMapping("/niveles-ensenanza")
    @CircuitBreaker(name = "crearNivelEnsenanzaCB", fallbackMethod = "fallbackMethodCrearNivelEnsenanza")
    ResponseEntity<NivelEnsenanza> crearNivelDeEnsenanzaDto(@RequestBody NivelEnsenanza nivelEnsenanza);

    @GetMapping("/niveles-ensenanza")
    @CircuitBreaker(name = "listarNivelesDeEnsenanzaCB", fallbackMethod = "fallbackMethodListarNivelesDeEnsenanza")
    ResponseEntity<List<NivelEnsenanza>> listarNivelDeEnsenanzaDto();

    @GetMapping("/niveles-ensenanza/{id}")
    @CircuitBreaker(name = "listarNivelEnsenanzaPorIdCB", fallbackMethod = "fallbackMethodListarNivelEnsenanzaPorId")
    ResponseEntity<NivelEnsenanza> listarNivelDeEnsenanzaDtoPorId(@PathVariable Long id);

    @PutMapping("/niveles-ensenanza/{id}")  // Añadir la ruta con el ID
    @CircuitBreaker(name = "actualizarNivelEnsenanzaCB", fallbackMethod = "fallbackMethodActualizarNivelEnsenanza")
    ResponseEntity<NivelEnsenanza> actualizarNivelDeEnsenanzaDto(@PathVariable Long id, @RequestBody NivelEnsenanza nivelEnsenanza);

    @DeleteMapping("/niveles-ensenanza/{id}")  // Añadir la ruta con el ID
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

    @PostMapping("/ciclo")
    @CircuitBreaker(name = "crearCicloCB", fallbackMethod = "fallbackMethodCrearCiclo")
    ResponseEntity<Ciclo> crearCicloDto(@RequestBody Ciclo ciclo);

    @GetMapping("/ciclo")
    @CircuitBreaker(name = "listarCiclosCB", fallbackMethod = "fallbackMethodListarCiclos")
    ResponseEntity<List<Ciclo>> listarCiclosDto();

    @GetMapping("/ciclo/{idCiclo}")
    @CircuitBreaker(name = "listarCicloPorIdCB", fallbackMethod = "fallbackMethodListarCicloPorId")
    ResponseEntity<Ciclo> listarCicloDtoPorId(@PathVariable(required = true) Long idCiclo);

    @PutMapping("/ciclo/{idCiclo}")  // Añadir la ruta con el ID
    @CircuitBreaker(name = "actualizarCicloCB", fallbackMethod = "fallbackMethodActualizarCiclo")
    ResponseEntity<Ciclo> actualizarCicloDto(@PathVariable(required = true) Long idCiclo, @RequestBody Ciclo ciclo);

    @DeleteMapping("/ciclo/{idCiclo}")  // Añadir la ruta con el ID
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

    @GetMapping("/opciones-nivel/{nivelId}/opciones")
    @CircuitBreaker(name = "listarOpcionesPorNivelCB", fallbackMethod = "fallbackMethodListarOpcionesPorNivel")
    ResponseEntity<List<OpcionNivel>> listarOpcionesPorNivelDto(@PathVariable Long nivelId);

    @PostMapping("/opciones-nivel")
    @CircuitBreaker(name = "crearOpcionNivelCB", fallbackMethod = "fallbackMethodCrearOpcionNivel")
    ResponseEntity<OpcionNivel> crearOpcionNivelDto(@RequestBody OpcionNivel opcionNivel);

    @GetMapping("/opciones-nivel")
    @CircuitBreaker(name = "listarOpcionesDeNivelCB", fallbackMethod = "fallbackMethodListarOpcionesDeNivel")
    ResponseEntity<List<OpcionNivel>> listarOpcionesDeNivelDto();

    @GetMapping("/opciones-nivel/{id}")
    @CircuitBreaker(name = "listarOpcionNivelPorIdCB", fallbackMethod = "fallbackMethodListarOpcionNivelPorId")
    ResponseEntity<OpcionNivel> listarOpcionNivelDtoPorId(@PathVariable Long id);

    @PutMapping("/opciones-nivel/{id}")  // Añadir la ruta con el ID
    @CircuitBreaker(name = "actualizarOpcionNivelCB", fallbackMethod = "fallbackMethodActualizarOpcionNivel")
    ResponseEntity<OpcionNivel> actualizarOpcionNivelDto(@PathVariable Long id, @RequestBody OpcionNivel opcionNivel);

    @DeleteMapping("/opciones-nivel/{id}")  // Añadir la ruta con el ID
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

    @GetMapping("/cursoDetalle")
    @CircuitBreaker(name = "listarCursosDetalleCB", fallbackMethod = "fallbackMethodListarCursosDetalle")
    ResponseEntity<List<CursoDetalle>> listarCursosDetalle();

    @GetMapping("/cursoDetalle/{id}")
    @CircuitBreaker(name = "listarCursoDetallePorIdCB", fallbackMethod = "fallbackMethodListarCursoDetallePorId")
    ResponseEntity<CursoDetalle> listarCursoDetallePorId(@PathVariable Long id);

    @PostMapping("/cursoDetalle")
    @CircuitBreaker(name = "crearCursoDetalleCB", fallbackMethod = "fallbackMethodCrearCursoDetalle")
    ResponseEntity<CursoDetalle> crearCursoDetalle(@RequestBody CursoDetalle cursoDetalle);

    @PutMapping("/cursoDetalle/{id}")
    @CircuitBreaker(name = "actualizarCursoDetalleCB", fallbackMethod = "fallbackMethodActualizarCursoDetalle")
    ResponseEntity<CursoDetalle> actualizarCursoDetalle(@PathVariable Long id, @RequestBody CursoDetalle cursoDetalle);

    @DeleteMapping("/cursoDetalle/{id}")
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