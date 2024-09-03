package upeu.edu.pe.msevaluacionacademica.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import upeu.edu.pe.msevaluacionacademica.entity.EvaluacionAcademica;
import upeu.edu.pe.msevaluacionacademica.service.EvaluacionAcademicaService;

import java.util.List;

@RestController
@RequestMapping("/evaluacionAcademica")
public class EvaluacionAcademicaController {
    @Autowired
    private EvaluacionAcademicaService evaluacionAcademicaService;

    @PostMapping
    public ResponseEntity<EvaluacionAcademica> guardarEvaluacionAcademicaResponseEntity(@RequestBody EvaluacionAcademica EvaluacionAcademica){
        return ResponseEntity.ok(evaluacionAcademicaService.guardarEvaluacionAcademica(EvaluacionAcademica));
    }

    @GetMapping
    public ResponseEntity<List<EvaluacionAcademica>> listarEvaluacionAcademicaResponseEntity(){
        return ResponseEntity.ok(evaluacionAcademicaService.listarEvaluacionAcademica());
    }

    @GetMapping("/{id}")
    public ResponseEntity<EvaluacionAcademica> buscarEvaluacionAcademicaPorIdResponseEntity(@PathVariable(required = true) Long id){
        return ResponseEntity.ok(evaluacionAcademicaService.buscarEvaluacionAcademicaPorId(id));
    }

    @PutMapping("/{id}")
    public ResponseEntity<EvaluacionAcademica> editarEvaluacionAcademicaResponseEntity(@PathVariable (required = true) Long id,@RequestBody EvaluacionAcademica EvaluacionAcademica){
        EvaluacionAcademica.setId(id);
        return ResponseEntity.ok(evaluacionAcademicaService.editarEvaluacionAcademica(EvaluacionAcademica));
    }

    @DeleteMapping("/{id}")
    public String eliminarEvaluacionAcademicaResponseEntity(@PathVariable Long id){
        evaluacionAcademicaService.eliminarEvaluacionAcademica(id);
        return "EvaluacionAcademica eliminada";
    }
}
