package upeu.edu.pe.msplanificacionacademica.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import upeu.edu.pe.msplanificacionacademica.entity.PlanificacionAcademica;
import upeu.edu.pe.msplanificacionacademica.service.PlanificacionAcademicaService;

import java.util.List;

@RestController
@RequestMapping
public class PlanificacionAcademicaController {
    @Autowired
    private PlanificacionAcademicaService planificacionAcademicaService;

    @PostMapping
    public ResponseEntity<PlanificacionAcademica> guardarPlanificacionAcademicaResponseEntity(@RequestBody PlanificacionAcademica planificacionAcademica){
        return ResponseEntity.ok(planificacionAcademicaService.guardarPlanificacionAcademica(planificacionAcademica));
    }

    @GetMapping
    public ResponseEntity<List<PlanificacionAcademica>> listarPlanificacionAcademicaResponseEntity() {
        return ResponseEntity.ok(planificacionAcademicaService.listarPlanificacionAcademica());
    }

    @GetMapping("/{id}")
    public ResponseEntity<PlanificacionAcademica> buscarPlanificacionAcademicaPorIdResponseEntity(@PathVariable(required = true) Long id){
        return ResponseEntity.ok(planificacionAcademicaService.buscarPlanificacionAcademicaPorId(id));
    }

    @PutMapping("/{id}")
    public ResponseEntity<PlanificacionAcademica> editarPlanificacionAcademicaResponseEntity(@PathVariable(required = true) Long id, @RequestBody PlanificacionAcademica planificacionAcademica){
        planificacionAcademica.setIdPlanificacionAcademica(id);
        return ResponseEntity.ok(planificacionAcademicaService.editarPlanificacionAcademica(planificacionAcademica));
    }

    @DeleteMapping("/{id}")
    public String eliminarPlanificacionAcademicaResponseEntity(@PathVariable(required = true) Long id){
        planificacionAcademicaService.eliminarPlanificacionAcademica(id);
        return "Planificacion Academica Eliminado Correctamente";
    }
}
