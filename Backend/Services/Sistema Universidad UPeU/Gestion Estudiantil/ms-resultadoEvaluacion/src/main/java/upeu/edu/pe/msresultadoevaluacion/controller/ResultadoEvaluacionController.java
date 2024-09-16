package upeu.edu.pe.msresultadoevaluacion.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import upeu.edu.pe.msresultadoevaluacion.entity.ResultadoEvaluacion;
import upeu.edu.pe.msresultadoevaluacion.service.ResultadoEvaluacionService;

import java.util.List;

@RestController("/resultadoEvaluacion")
@RequestMapping
public class ResultadoEvaluacionController {
    @Autowired
    private ResultadoEvaluacionService resultadoEvaluacionService;

    @PostMapping
    public ResponseEntity<ResultadoEvaluacion> guardarResultadoEvaluacionResponseEntity(@RequestBody ResultadoEvaluacion resultadoEvaluacion) {
        return ResponseEntity.status(HttpStatus.CREATED).body(resultadoEvaluacionService.guardarResultadoEvaluacion(resultadoEvaluacion));
    }
    @GetMapping
    public ResponseEntity<List<ResultadoEvaluacion>> listarResultadoEvaluacionsResponseEntity() {
        return ResponseEntity.status(HttpStatus.OK).body(resultadoEvaluacionService.listarResultadoEvaluacions());
    }
    @GetMapping("/{id}")
    public ResponseEntity<ResultadoEvaluacion> listarResultadoEvaluacions(@PathVariable(required = true) long id) {
        return ResponseEntity.status(HttpStatus.OK).body(resultadoEvaluacionService.buscarResultadoEvaluacionPorId(id));
    }
    @PutMapping("/{id}")
    public ResponseEntity<ResultadoEvaluacion> editarResultadoEvaluacion(@PathVariable(required = true) long id, @RequestBody ResultadoEvaluacion
            resultadoEvaluacion) {
        resultadoEvaluacion.setIdResultadoEvaluacion(id);
        return ResponseEntity.status(HttpStatus.OK).body(resultadoEvaluacionService.editarResultadoEvaluacion(resultadoEvaluacion));
    }
    @DeleteMapping("/{id}")
    public String eliminarResultadoEvaluacion(@PathVariable(required = true) long id) {
        resultadoEvaluacionService.eliminarResultadoEvaluacion(id);
        return "ResultadoEvaluacion eliminado";
    }

}
