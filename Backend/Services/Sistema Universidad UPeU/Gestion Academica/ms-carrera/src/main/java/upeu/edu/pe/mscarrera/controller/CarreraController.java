package upeu.edu.pe.mscarrera.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import upeu.edu.pe.mscarrera.entity.Carrera;
import upeu.edu.pe.mscarrera.service.CarreraService;

import java.util.List;

@RestController
@RequestMapping("/carrera")
public class CarreraController {
    @Autowired
    private CarreraService carreraService;

    @PostMapping
    public ResponseEntity<Carrera> guardarCarreraResponseEntity(@RequestBody Carrera Carrera){
        return ResponseEntity.ok(carreraService.guardarCarrera(Carrera));
    }

    @GetMapping
    public ResponseEntity<List<Carrera>> listarCarreraResponseEntity(){
        return ResponseEntity.ok(carreraService.listarCarrera());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Carrera> buscarCarreraPorIdResponseEntity(@PathVariable(required = true) Long id){
        return ResponseEntity.ok(carreraService.buscarCarreraPorId(id));
    }

    @PutMapping("/{id}")
    public ResponseEntity<Carrera> editarCarreraResponseEntity(@PathVariable (required = true) Long id,@RequestBody Carrera Carrera){
        Carrera.setIdCarrera(id);
        return ResponseEntity.ok(carreraService.editarCarrera(Carrera));
    }

    @DeleteMapping("/{id}")
    public String eliminarCarreraResponseEntity(@PathVariable Long id){
        carreraService.eliminarCarrera(id);
        return "Carrera eliminada";
    }
}
