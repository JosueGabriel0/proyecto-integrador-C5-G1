package upeu.edu.pe.msplanificacionacademica.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import upeu.edu.pe.msplanificacionacademica.entity.Ciclo;
import upeu.edu.pe.msplanificacionacademica.service.CicloService;

import java.util.List;

@RestController
@RequestMapping("/ciclo")
public class CicloController {
    @Autowired
    private CicloService cicloService;

    @GetMapping
    public ResponseEntity<List<Ciclo>> listarTodosLosCiclos(){
        return ResponseEntity.ok(cicloService.listarTodosLosCiclos());
    }

    @GetMapping("/{idCiclo}")
    public ResponseEntity<Ciclo> listarCicloPorId(@PathVariable(required = true) Long idCiclo){
        return ResponseEntity.ok(cicloService.listarCicloPorId(idCiclo));
    }

    @PostMapping
    public ResponseEntity<Ciclo> crearCiclo(@RequestBody Ciclo ciclo) {
        return ResponseEntity.ok(cicloService.crearCiclo(ciclo));
    }

    @PutMapping("/{idCiclo}")
    public ResponseEntity<Ciclo> actualizarCiclo(@PathVariable(required = true) Long idCiclo, @RequestBody Ciclo ciclo) {
        ciclo.setIdCiclo(idCiclo);
        return ResponseEntity.ok(cicloService.modificarCiclo(ciclo));
    }

    @DeleteMapping("/{idCiclo}")
    public ResponseEntity<Void> eliminarCiclo(@PathVariable(required = true) Long idCiclo) {
        cicloService.eliminarCiclo(idCiclo);
        return ResponseEntity.noContent().build();
    }
}
