package upeu.edu.pe.msinscripciones.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import upeu.edu.pe.msinscripciones.entity.Inscripcion;
import upeu.edu.pe.msinscripciones.service.InscripcionesService;

import java.util.List;

@RestController
@RequestMapping("/inscripcion")
public class InscipcionesController {
    @Autowired
    private InscripcionesService inscripcionesService;
    /*
    @PostMapping
    public ResponseEntity<Inscripcion> crearInscripcion(@RequestBody Inscripcion inscripcion) {
        Inscripcion nuevaInscripcion = inscripcionesService.crearInscripcion(inscripcion);
        return ResponseEntity.status(HttpStatus.CREATED).body(nuevaInscripcion);
    }*/

    @PostMapping("/con-rol")
    public ResponseEntity<Inscripcion> crearInscripcionConRol(@RequestBody Inscripcion inscripcion) {
        Inscripcion nuevaInscripcionConRol = inscripcionesService.crearInscripcionConRol(inscripcion);
        return ResponseEntity.status(HttpStatus.CREATED).body(nuevaInscripcionConRol);
    }

    /*
    @GetMapping("/{id}")
    public ResponseEntity<Inscripcion> listarInscripcionPorId(@PathVariable Long id) {
        Inscripcion inscripcion = inscripcionesService.buscarInscripcionPorId(id);
        return ResponseEntity.ok(inscripcion);
    }

    @GetMapping
    public ResponseEntity<List<Inscripcion>> listarInscripciones() {
        List<Inscripcion> inscripciones = inscripcionesService.listarInscripcion();
        return ResponseEntity.ok(inscripciones);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Inscripcion> editarInscripcion(@PathVariable Long id, @RequestBody Inscripcion nuevaInscripcion) {
        Inscripcion inscripcionEditada = inscripcionesService.editarInscripcion(id, nuevaInscripcion);
        return ResponseEntity.ok(inscripcionEditada);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Inscripcion> eliminarDatosInscripcion(@PathVariable Long id) {
        Inscripcion inscripcionActualizada = inscripcionesService.eliminarInscripcion(id);
        return ResponseEntity.ok(inscripcionActualizada);
    }
     */
}
