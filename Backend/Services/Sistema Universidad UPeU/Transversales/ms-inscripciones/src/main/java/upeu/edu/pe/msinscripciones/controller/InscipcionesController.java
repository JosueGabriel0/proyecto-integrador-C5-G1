package upeu.edu.pe.msinscripciones.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import upeu.edu.pe.msinscripciones.entity.Inscripcion;
import upeu.edu.pe.msinscripciones.service.InscripcionesService;

import java.util.List;

@RestController
@RequestMapping("/Inscripcion")
public class InscipcionesController {
    @Autowired
    private InscripcionesService inscripcionesService;

    // Método para crear una inscripción sin crear un rol (solo asignando el ID del rol)
    @PostMapping
    public ResponseEntity<Inscripcion> crearInscripcion(@RequestBody Inscripcion inscripcion) {
        Inscripcion nuevaInscripcion = inscripcionesService.crearInscripcion(inscripcion);
        return ResponseEntity.status(HttpStatus.CREATED).body(nuevaInscripcion);
    }

    // Método para crear una inscripción y crear un rol
    @PostMapping("/con-rol")
    public ResponseEntity<Inscripcion> crearInscripcionConRol(@RequestBody Inscripcion inscripcion) {
        Inscripcion nuevaInscripcionConRol = inscripcionesService.crearInscripcionConRol(inscripcion);
        return ResponseEntity.status(HttpStatus.CREATED).body(nuevaInscripcionConRol);
    }

    // Método para listar una inscripción por ID
    @GetMapping("/{id}")
    public ResponseEntity<Inscripcion> listarInscripcionPorId(@PathVariable Long id) {
        Inscripcion inscripcion = inscripcionesService.buscarInscripcionPorId(id);
        return ResponseEntity.ok(inscripcion);
    }

    // Método para listar todas las inscripciones
    @GetMapping
    public ResponseEntity<List<Inscripcion>> listarInscripciones() {
        List<Inscripcion> inscripciones = inscripcionesService.listarInscripcion();
        return ResponseEntity.ok(inscripciones);
    }

    // Método para editar una inscripción por ID
    @PutMapping("/{id}")
    public ResponseEntity<Inscripcion> editarInscripcion(@PathVariable Long id, @RequestBody Inscripcion nuevaInscripcion) {
        Inscripcion inscripcionEditada = inscripcionesService.editarInscripcion(id, nuevaInscripcion);
        return ResponseEntity.ok(inscripcionEditada);
    }

    // Método para eliminar los datos de usuario, persona, estudiante o docente pero no el rol
    @DeleteMapping("/{id}")
    public ResponseEntity<Inscripcion> eliminarDatosInscripcion(@PathVariable Long id) {
        Inscripcion inscripcionActualizada = inscripcionesService.eliminarInscripcion(id);
        return ResponseEntity.ok(inscripcionActualizada);
    }
}
