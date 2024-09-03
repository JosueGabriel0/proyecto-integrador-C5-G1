package upeu.edu.pe.mscalendarioacademico.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import upeu.edu.pe.mscalendarioacademico.entity.CalendarioAcademico;
import upeu.edu.pe.mscalendarioacademico.service.CalendarioAcademicoService;

import java.util.List;

@RestController
@RequestMapping("/calendarioAcademico")
public class CalendarioAcademicoController {
    @Autowired
    private CalendarioAcademicoService calendarioAcademicoService;

    @PostMapping
    public ResponseEntity<CalendarioAcademico> guardarCalendarioAcademicoResponseEntity(@RequestBody CalendarioAcademico CalendarioAcademico){
        return ResponseEntity.ok(calendarioAcademicoService.guardarCalendarioAcademico(CalendarioAcademico));
    }

    @GetMapping
    public ResponseEntity<List<CalendarioAcademico>> listarCalendarioAcademicoResponseEntity(){
        return ResponseEntity.ok(calendarioAcademicoService.listarCalendarioAcademico());
    }

    @GetMapping("/{id}")
    public ResponseEntity<CalendarioAcademico> buscarCalendarioAcademicoPorIdResponseEntity(@PathVariable(required = true) Long id){
        return ResponseEntity.ok(calendarioAcademicoService.buscarCalendarioAcademicoPorId(id));
    }

    @PutMapping("/{id}")
    public ResponseEntity<CalendarioAcademico> editarCalendarioAcademicoResponseEntity(@PathVariable (required = true) Long id,@RequestBody CalendarioAcademico CalendarioAcademico){
        CalendarioAcademico.setId(id);
        return ResponseEntity.ok(calendarioAcademicoService.editarCalendarioAcademico(CalendarioAcademico));
    }

    @DeleteMapping("/{id}")
    public String eliminarCalendarioAcademicoResponseEntity(@PathVariable Long id){
        calendarioAcademicoService.eliminarCalendarioAcademico(id);
        return "CalendarioAcademico eliminada";
    }
}
