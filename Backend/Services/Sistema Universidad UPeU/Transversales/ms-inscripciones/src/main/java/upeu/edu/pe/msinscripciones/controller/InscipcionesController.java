package upeu.edu.pe.msinscripciones.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import upeu.edu.pe.msinscripciones.dto.Persona;
import upeu.edu.pe.msinscripciones.entity.Inscripcion;
import upeu.edu.pe.msinscripciones.feign.PersonaFeign;
import upeu.edu.pe.msinscripciones.repository.InscripcionesRepository;
import upeu.edu.pe.msinscripciones.service.InscripcionesService;

import java.util.List;

@RestController
@RequestMapping("/inscripcion")
public class InscipcionesController {
    @Autowired
    private InscripcionesService inscripcionesService;
    @Autowired
    private PersonaFeign personaFeign;
    @Autowired
    private InscripcionesRepository inscripcionesRepository;

    //CRUD DE INSCRIPCION
    @PostMapping
    public ResponseEntity<Inscripcion> crearInscripcion(@ModelAttribute Inscripcion inscripcionDTO, @RequestParam("file") MultipartFile fotoPerfil) {
        Inscripcion nuevaInscripcion = inscripcionesService.crearInscripcion(inscripcionDTO, fotoPerfil);
        return new ResponseEntity<>(nuevaInscripcion, HttpStatus.CREATED);
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
    @PostMapping(value = "/con-rol", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<Inscripcion> crearInscripcionConRol(@ModelAttribute Inscripcion inscripcion,@RequestParam("file") MultipartFile fotoPerfil) {

        ResponseEntity<Persona> personaResponse = personaFeign.crearPersonaDto(inscripcion.getPersona(),fotoPerfil);
        if (personaResponse.getBody() == null) {
            throw new RuntimeException("No se pudo crear la Persona.");
        }
        inscripcion.setIdPersona(personaResponse.getBody().getId());
        inscripcionesRepository.save(inscripcion);

        Inscripcion nuevaInscripcionConRol = inscripcionesService.crearInscripcionConRol(inscripcion);
        return new ResponseEntity<>(nuevaInscripcionConRol, HttpStatus.CREATED);
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