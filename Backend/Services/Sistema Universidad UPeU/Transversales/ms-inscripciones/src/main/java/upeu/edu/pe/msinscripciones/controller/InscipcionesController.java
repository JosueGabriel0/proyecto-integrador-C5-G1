package upeu.edu.pe.msinscripciones.controller;

    import org.bouncycastle.jce.spec.ECNamedCurveGenParameterSpec;
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

    //CRUD DE INSCRIPCION
    @PostMapping
    public ResponseEntity<Inscripcion> crearInscripcion(@RequestBody Inscripcion inscripcionDTO) {
        Inscripcion inscripcion = inscripcionesService.crearInscripcion(inscripcionDTO);
        return new ResponseEntity<>(inscripcion, HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Inscripcion> editarInscripcion(@PathVariable Long id, @RequestBody Inscripcion inscripcionDTO) {
        Inscripcion inscripcion = inscripcionesService.editarInscripcion(id, inscripcionDTO);
        return new ResponseEntity<>(inscripcion, HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> eliminarInscripcion(@PathVariable Long id) {
        inscripcionesService.eliminarInscripcion(id);
        return new ResponseEntity<>("Los datos de la inscripción con ID " + id + " fueron eliminados, excepto el rol.", HttpStatus.OK);
    }

    //CUD DE INSCRIPCION CON ROL
    @PostMapping("/con-rol")
    public ResponseEntity<Inscripcion> crearInscripcionConRol(@RequestBody Inscripcion inscripcion) {
        Inscripcion nuevaInscripcionConRol = inscripcionesService.crearInscripcionConRol(inscripcion);
        return ResponseEntity.status(HttpStatus.CREATED).body(nuevaInscripcionConRol);
    }

    @PutMapping("/con-rol/{id}")
    public ResponseEntity<Inscripcion> editarInscripcionConRol(@PathVariable Long id, @RequestBody Inscripcion inscripcionDTO) {
        Inscripcion inscripcion = inscripcionesService.editarInscripcionConRol(id, inscripcionDTO);
        return new ResponseEntity<>(inscripcion, HttpStatus.OK);
    }

    @DeleteMapping("/con-rol/{id}")
    public ResponseEntity<String> eliminarDatosInscripcionConRol(@PathVariable Long id) {
        inscripcionesService.eliminarInscripcionConRol(id);
        return new ResponseEntity<>("La inscripción Con Rol con ID " + id + " ha sido eliminada exitosamente.", HttpStatus.OK);
    }

    //R INSCRIPCION GENERAL
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

    @GetMapping("/{id}")
    public ResponseEntity<Inscripcion> listarInscripcionPorId(@PathVariable Long id) {
        Inscripcion inscripcion = inscripcionesService.buscarInscripcionPorId(id);
        return new ResponseEntity<>(inscripcion, HttpStatus.OK);
    }
}
