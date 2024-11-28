package upeu.edu.pe.msmatriculas.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import upeu.edu.pe.msmatriculas.entity.Matricula;
import upeu.edu.pe.msmatriculas.service.MatriculaService;

import java.util.List;

@RestController
@RequestMapping("/matriculas")
@RequiredArgsConstructor
public class MatriculaController {

    private final MatriculaService matriculaService;

    // Crear nueva matrícula
    @PostMapping
    public ResponseEntity<Matricula> crearMatricula(@RequestBody Matricula matricula) {
        Matricula nuevaMatricula = matriculaService.crearMatricula(matricula);
        return ResponseEntity.ok(nuevaMatricula);
    }

    // Obtener matrícula por ID
    @GetMapping("/{id}")
    public ResponseEntity<Matricula> obtenerMatriculaPorId(@PathVariable Long id) {
        Matricula matricula = matriculaService.obtenerMatriculaPorId(id);
        return ResponseEntity.ok(matricula);
    }

    // Listar todas las matrículas
    @GetMapping
    public ResponseEntity<List<Matricula>> listarMatriculas() {
        List<Matricula> matriculas = matriculaService.obtenerMatriculas();
        return ResponseEntity.ok(matriculas);
    }

    // Actualizar matrícula
    @PutMapping("/{id}")
    public ResponseEntity<Matricula> actualizarMatricula(@PathVariable Long id, @RequestBody Matricula nuevaMatricula) {
        Matricula matriculaActualizada = matriculaService.actualizarMatricula(id, nuevaMatricula);
        return ResponseEntity.ok(matriculaActualizada);
    }

    // Eliminar matrícula
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminarMatricula(@PathVariable Long id) {
        matriculaService.eliminarMatricula(id);
        return ResponseEntity.noContent().build();
    }
}