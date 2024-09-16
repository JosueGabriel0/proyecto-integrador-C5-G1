package upeu.edu.pe.mscurso.controller;

import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import upeu.edu.pe.mscurso.entity.Curso;
import upeu.edu.pe.mscurso.service.CursoService;

import java.util.List;

@RestController
@RequestMapping("/curso")

public class CursoController {

    @Autowired
    private CursoService cursoService;

    @PostMapping
    public ResponseEntity<Curso> crearCursoResponseEntity(@RequestBody Curso curso){
        return ResponseEntity.ok(cursoService.crearCurso(curso));
    }

    @GetMapping
    public ResponseEntity<List<Curso>> listarCursosResponseEntity(){
        return ResponseEntity.ok(cursoService.listarCursos());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Curso> listarCursoPorIdResponseEntity(@PathVariable(required = true) Long id){
        return ResponseEntity.ok(cursoService.listarCursosPorId(id));
    }

    @PutMapping("/{id}")
    public ResponseEntity<Curso> editarCursoResponseEntity(@PathVariable(required = true) Long id, @RequestBody Curso curso){
        curso.setIdCurso(id);
        return ResponseEntity.ok(cursoService.editarCurso(curso));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> eliminarCursoResponseEntity(@PathVariable(required = true) Long id){
        cursoService.eliminarCurso(id);
        return ResponseEntity.ok("El curso fue eliminado");
    }
}
