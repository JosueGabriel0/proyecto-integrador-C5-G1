package upeu.edu.pe.msdocente.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import upeu.edu.pe.msdocente.entity.Docente;
import upeu.edu.pe.msdocente.service.DocenteService;

import java.util.List;

@RestController
@RequestMapping("/docente")

public class DocenteController {
    @Autowired
    private DocenteService docenteService;

    @PostMapping
    public ResponseEntity<Docente> guardarDocenteResponseEntity(@RequestBody Docente docente){
        return ResponseEntity.ok(docenteService.guardarDocente(docente));
    }

    @GetMapping
    public ResponseEntity<List<Docente>> listarDocentesResponseEntity(){
        return ResponseEntity.ok(docenteService.listarDocente());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Docente> buscarDocentePorIdResponseEntity(@PathVariable( required = true) Long id){
        return ResponseEntity.ok(docenteService.buscarDocentePorId(id));
    }

    @PutMapping("/{id}")
    public ResponseEntity<Docente> editarDocenteResponseEntity(@PathVariable( required = true) Long id, @RequestBody Docente docente){
        docente.setIdDocente(id);
        return ResponseEntity.ok(docenteService.editarDocente(docente));
    }

    @DeleteMapping
    public String eliminarDocenteResponseEntity(@PathVariable( required = true) Long id){
        docenteService.eliminarDocente(id);
        return "Docente eliminado";
    }
}
