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

    @PostMapping
    public ResponseEntity<Inscripcion> crearInscripcion(@RequestBody Inscripcion inscripcionDTO) {
        Inscripcion inscripcion = inscripcionesService.crearInscripcion(inscripcionDTO);
        return new ResponseEntity<>(inscripcion, HttpStatus.CREATED);
    }

    @PostMapping("/con-rol")
    public ResponseEntity<Inscripcion> crearInscripcionConRol(@RequestBody Inscripcion inscripcion) {
        Inscripcion nuevaInscripcionConRol = inscripcionesService.crearInscripcionConRol(inscripcion);
        return ResponseEntity.status(HttpStatus.CREATED).body(nuevaInscripcionConRol);
    }


    @GetMapping("/{id}")
    public ResponseEntity<Inscripcion> listarInscripcionPorId(@PathVariable Long id) {
        Inscripcion inscripcion = inscripcionesService.buscarInscripcionPorId(id);
        return new ResponseEntity<>(inscripcion, HttpStatus.OK);
    }


    @GetMapping
    public ResponseEntity<List<Inscripcion>> listarInscripciones() {
        try {
            // Llamamos al servicio para obtener la lista de inscripciones
            List<Inscripcion> inscripciones = inscripcionesService.listarInscripcion();

            // Verificamos si la lista está vacía
            if (inscripciones.isEmpty()) {
                return new ResponseEntity<>(HttpStatus.NO_CONTENT);
            }

            // Retornamos la lista de inscripciones con estado HTTP 200 (OK)
            return new ResponseEntity<>(inscripciones, HttpStatus.OK);

        } catch (Exception e) {
            // Si ocurre algún error, lo manejamos aquí
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<Inscripcion> editarInscripcion(@PathVariable Long id, @RequestBody Inscripcion inscripcionDTO) {
        Inscripcion inscripcion = inscripcionesService.editarInscripcion(id, inscripcionDTO);
        return new ResponseEntity<>(inscripcion, HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> eliminarDatosInscripcion(@PathVariable Long id) {
        inscripcionesService.eliminarInscripcion(id);
        return new ResponseEntity<>("La inscripción con ID " + id + " ha sido eliminada exitosamente.", HttpStatus.OK);
    }
}
