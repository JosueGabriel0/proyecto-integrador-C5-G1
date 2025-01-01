package upeu.edu.pe.mscuentafinancierauniversitaria.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import upeu.edu.pe.mscuentafinancierauniversitaria.entity.MovimientoAcademico;
import upeu.edu.pe.mscuentafinancierauniversitaria.service.MovimientoAcademicoService;

import java.util.List;

@RestController
@RequestMapping("/movimientoAcademico")
public class MovimientoAcademicoController {
    @Autowired
    private MovimientoAcademicoService movimientoAcademicoService;

    @GetMapping
    public ResponseEntity<List<MovimientoAcademico>> listarTodos() {
        return ResponseEntity.ok(movimientoAcademicoService.listarTodos());
    }

    @GetMapping("/{id}")
    public ResponseEntity<MovimientoAcademico> obtenerPorId(@PathVariable Long id) {
        return ResponseEntity.ok(movimientoAcademicoService.obtenerPorId(id));
    }

    @PostMapping
    public ResponseEntity<MovimientoAcademico> crear(@RequestBody MovimientoAcademico movimientoAcademico) {
        return ResponseEntity.ok(movimientoAcademicoService.crear(movimientoAcademico));
    }

    @PutMapping("/{id}")
    public ResponseEntity<MovimientoAcademico> actualizar(@PathVariable Long id, @RequestBody MovimientoAcademico movimientoAcademico) {
        movimientoAcademico.setIdMovimientoAcademico(id);
        return ResponseEntity.ok(movimientoAcademicoService.actualizar(movimientoAcademico));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminar(@PathVariable Long id) {
        movimientoAcademicoService.eliminar(id);
        return ResponseEntity.noContent().build();
    }
}