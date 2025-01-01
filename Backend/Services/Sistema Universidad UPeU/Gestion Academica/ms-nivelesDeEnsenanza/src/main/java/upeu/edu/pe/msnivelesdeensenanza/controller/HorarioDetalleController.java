package upeu.edu.pe.msnivelesdeensenanza.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import upeu.edu.pe.msnivelesdeensenanza.entity.HorarioDetalle;
import upeu.edu.pe.msnivelesdeensenanza.service.HorarioDetalleService;

import java.util.List;

@RestController
@RequestMapping("/horarioDetalle")
public class HorarioDetalleController {

    @Autowired
    private HorarioDetalleService horarioDetalleService;

    @GetMapping
    public ResponseEntity<List<HorarioDetalle>> listarTodos() {
        return ResponseEntity.ok(horarioDetalleService.listarTodos());
    }

    @GetMapping("/{id}")
    public ResponseEntity<HorarioDetalle> obtenerPorId(@PathVariable Long id) {
        return ResponseEntity.ok(horarioDetalleService.obtenerPorId(id));
    }

    @PostMapping
    public ResponseEntity<HorarioDetalle> crear(@RequestBody HorarioDetalle horarioDetalle) {
        return ResponseEntity.ok(horarioDetalleService.crear(horarioDetalle));
    }

    @PutMapping("/{id}")
    public ResponseEntity<HorarioDetalle> actualizar(@PathVariable Long id, @RequestBody HorarioDetalle horarioDetalle) {
        horarioDetalle.setIdHorarioDetalle(id);
        return ResponseEntity.ok(horarioDetalleService.actualizar(horarioDetalle));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminar(@PathVariable Long id) {
        horarioDetalleService.eliminar(id);
        return ResponseEntity.noContent().build();
    }
}
