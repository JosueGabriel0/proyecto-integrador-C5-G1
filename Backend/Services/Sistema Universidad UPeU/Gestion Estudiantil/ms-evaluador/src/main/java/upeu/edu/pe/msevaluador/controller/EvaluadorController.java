package upeu.edu.pe.msevaluador.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import upeu.edu.pe.msevaluador.entity.Evaluador;
import upeu.edu.pe.msevaluador.service.EvaluadorService;

import java.util.List;

@RestController("/Evaluador")
@RequestMapping
public class EvaluadorController {
    @Autowired
    private EvaluadorService evaluadorService;

    @PostMapping
    public ResponseEntity<Evaluador> guardarEvaluadorResponseEntity(@RequestBody Evaluador evaluador) {
        return ResponseEntity.status(HttpStatus.CREATED).body(evaluadorService.guardarEvaluador(evaluador));
    }
    @GetMapping
    public ResponseEntity<List<Evaluador>> listarEvaluadorsResponseEntity() {
        return ResponseEntity.status(HttpStatus.OK).body(evaluadorService.listarEvaluadors());
    }
    @GetMapping("/{id}")
    public ResponseEntity<Evaluador> listarEvaluadors(@PathVariable(required = true) long id) {
        return ResponseEntity.status(HttpStatus.OK).body(evaluadorService.buscarEvaluadorPorId(id));
    }
    @PutMapping("/{id}")
    public ResponseEntity<Evaluador> editarEvaluador(@PathVariable(required = true) long id, @RequestBody Evaluador
            evaluador) {
        evaluador.setIdEvaluador(id);
        return ResponseEntity.status(HttpStatus.OK).body(evaluadorService.editarEvaluador(evaluador));
    }
    @DeleteMapping("/{id}")
    public String eliminarEvaluador(@PathVariable(required = true) long id) {
        evaluadorService.eliminarEvaluador(id);
        return "Evaluador eliminado";
    }

}
