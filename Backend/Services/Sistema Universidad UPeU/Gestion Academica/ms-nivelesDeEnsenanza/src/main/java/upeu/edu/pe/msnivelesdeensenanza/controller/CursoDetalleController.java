package upeu.edu.pe.msnivelesdeensenanza.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import upeu.edu.pe.msnivelesdeensenanza.entity.CursoDetalle;
import upeu.edu.pe.msnivelesdeensenanza.service.CursoDetalleService;

import java.util.List;

@RestController
@RequestMapping("/cursoDetalle")
public class CursoDetalleController {

    @Autowired
    private CursoDetalleService cursoDetalleService;

    @GetMapping
    public ResponseEntity<List<CursoDetalle>> listarTodos() {
        return ResponseEntity.ok(cursoDetalleService.listarTodos());
    }

    @GetMapping("/{id}")
    public ResponseEntity<CursoDetalle> obtenerPorId(@PathVariable Long id) {
        return ResponseEntity.ok(cursoDetalleService.obtenerPorId(id));
    }

    @PostMapping
    public ResponseEntity<CursoDetalle> crear(@RequestBody CursoDetalle cursoDetalle) {
        return ResponseEntity.ok(cursoDetalleService.crear(cursoDetalle));
    }

    @PutMapping("/{id}")
    public ResponseEntity<CursoDetalle> actualizar(@PathVariable Long id, @RequestBody CursoDetalle cursoDetalle) {
        cursoDetalle.setIdCursoDetalle(id);
        return ResponseEntity.ok(cursoDetalleService.actualizar(cursoDetalle));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminar(@PathVariable Long id) {
        cursoDetalleService.eliminar(id);
        return ResponseEntity.noContent().build();
    }
}