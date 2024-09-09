package upeu.edu.pe.msestudiante.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import upeu.edu.pe.msestudiante.entity.Estudiante;
import upeu.edu.pe.msestudiante.service.EstudianteService;

import java.util.List;

@RestController
@RequestMapping("/estudiante")

public class EstudianteController {

    @Autowired
    private EstudianteService estudianteService;

    @PostMapping
    public ResponseEntity<Estudiante> guardarEstudianteResponseEntity(@RequestBody Estudiante estudiante){
        return ResponseEntity.ok(estudianteService.guardarEstudiante(estudiante));
    }

    @GetMapping
    public ResponseEntity<List<Estudiante>> listarEstudiantesResponseEntity(){
        return  ResponseEntity.ok(estudianteService.listarEstudiantes());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Estudiante> buscarEstudiantePorIdResponseEntity(@PathVariable(required = true) Long id){
        return ResponseEntity.ok(estudianteService.buscarEstudiantePorId(id));
    }

    @PutMapping("/{id}")
    public ResponseEntity<Estudiante> editarEstudianteResponseEntity(@PathVariable(required = true) Long id, @RequestBody Estudiante estudiante){
        estudiante.setIdEstudiante(id);
        return ResponseEntity.ok(estudianteService.editarEstudiante(estudiante));
    }

    @DeleteMapping("/{id}")
    public String eliminarEstudianteResponseEntity(@PathVariable(required = true) Long id){
        estudianteService.eliminarEstudiante(id);
        return "Estudiante Eliminado";
    }


}
