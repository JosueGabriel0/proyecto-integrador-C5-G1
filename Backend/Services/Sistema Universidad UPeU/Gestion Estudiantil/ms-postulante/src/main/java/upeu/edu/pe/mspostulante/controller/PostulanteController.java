package upeu.edu.pe.mspostulante.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import upeu.edu.pe.mspostulante.dto.Persona;
import upeu.edu.pe.mspostulante.entity.Postulante;
import upeu.edu.pe.mspostulante.service.PostulanteService;

import java.util.List;

@RestController("/postulante")
@RequestMapping
public class PostulanteController {
    @Autowired
    private PostulanteService postulanteService;

    @PostMapping
    public ResponseEntity<Postulante> guardarPostulanteResponseEntity(@RequestBody Postulante postulante) {
        return ResponseEntity.status(HttpStatus.CREATED).body(postulanteService.guardarPostulante(postulante));
    }
    @GetMapping
    public ResponseEntity<List<Postulante>> listarPostulantesResponseEntity() {
        return ResponseEntity.status(HttpStatus.OK).body(postulanteService.listarPostulantes());
    }
    @GetMapping("/{id}")
    public ResponseEntity<Postulante> listarPostulantes(@PathVariable(required = true) long id) {
        return ResponseEntity.status(HttpStatus.OK).body(postulanteService.buscarPostulantePorId(id));
        public ResponseEntity<Persona> listarPersonaDtoPorId(@PathVariable(required = true) Long id);
    }
    @PutMapping("/{id}")
    public ResponseEntity<Postulante> editarPostulante(@PathVariable(required = true) long id, @RequestBody Postulante
            postulante) {
        postulante.setIdPostulante(id);
        return ResponseEntity.status(HttpStatus.OK).body(postulanteService.editarPostulante(postulante));
    }
    @DeleteMapping("/{id}")
    public String eliminarPostulante(@PathVariable(required = true) long id) {
        postulanteService.eliminarPostulante(id);
        return "Postulante eliminado";
    }

}
