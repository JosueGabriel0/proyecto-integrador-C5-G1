package upeu.edu.pe.msnivelesdeensenanza.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import upeu.edu.pe.msnivelesdeensenanza.entity.CicloDetalle;
import upeu.edu.pe.msnivelesdeensenanza.service.CicloDetalleService;

import java.util.List;

@RestController
@RequestMapping("/cicloDetalle")
public class CicloDetalleController {

    @Autowired
    private CicloDetalleService cicloDetalleService;

    @GetMapping
    public ResponseEntity<List<CicloDetalle>> listarTodos() {
        return ResponseEntity.ok(cicloDetalleService.listarTodos());
    }

    @GetMapping("/{id}")
    public ResponseEntity<CicloDetalle> obtenerPorId(@PathVariable Long id) {
        return ResponseEntity.ok(cicloDetalleService.obtenerPorId(id));
    }

    @PostMapping
    public ResponseEntity<CicloDetalle> crear(@RequestBody CicloDetalle cicloDetalle) {
        return ResponseEntity.ok(cicloDetalleService.crear(cicloDetalle));
    }

    @PutMapping("/{id}")
    public ResponseEntity<CicloDetalle> actualizar(@PathVariable Long id, @RequestBody CicloDetalle cicloDetalle) {
        cicloDetalle.setIdCicloDetalle(id);
        return ResponseEntity.ok(cicloDetalleService.actualizar(cicloDetalle));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminar(@PathVariable Long id) {
        cicloDetalleService.eliminar(id);
        return ResponseEntity.noContent().build();
    }
}
