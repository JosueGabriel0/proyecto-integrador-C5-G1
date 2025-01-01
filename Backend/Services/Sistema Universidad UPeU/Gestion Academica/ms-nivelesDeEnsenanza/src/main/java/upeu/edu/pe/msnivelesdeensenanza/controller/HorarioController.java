package upeu.edu.pe.msnivelesdeensenanza.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import upeu.edu.pe.msnivelesdeensenanza.entity.Horario;
import upeu.edu.pe.msnivelesdeensenanza.service.HorarioService;

import java.util.List;

@RestController
@RequestMapping("/horario")
public class HorarioController {

    @Autowired
    private HorarioService horarioService;

    @GetMapping
    public ResponseEntity<List<Horario>> listarTodos() {
        return ResponseEntity.ok(horarioService.listarTodos());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Horario> obtenerPorId(@PathVariable Long id) {
        return ResponseEntity.ok(horarioService.obtenerPorId(id));
    }

    @PostMapping
    public ResponseEntity<Horario> crear(@RequestBody Horario horario) {
        return ResponseEntity.ok(horarioService.crear(horario));
    }

    @PutMapping("/{id}")
    public ResponseEntity<Horario> actualizar(@PathVariable Long id, @RequestBody Horario horario) {
        horario.setIdHorario(id);
        return ResponseEntity.ok(horarioService.actualizar(horario));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminar(@PathVariable Long id) {
        horarioService.eliminar(id);
        return ResponseEntity.noContent().build();
    }
}
