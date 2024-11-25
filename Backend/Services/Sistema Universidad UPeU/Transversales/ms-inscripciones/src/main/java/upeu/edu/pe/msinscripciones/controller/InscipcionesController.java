package upeu.edu.pe.msinscripciones.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import upeu.edu.pe.msinscripciones.dto.*;
import upeu.edu.pe.msinscripciones.entity.Inscripcion;
import upeu.edu.pe.msinscripciones.exception.PersonaCreationException;
import upeu.edu.pe.msinscripciones.service.InscripcionesService;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("/inscripcion")
public class InscipcionesController {
    @Autowired
    private InscripcionesService inscripcionesService;

    @Autowired
    private ObjectMapper objectMapper;

    //CRUD DE INSCRIPCION
    @PostMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<Inscripcion> crearInscripcion(
            @RequestPart("inscripcion") String inscripcionJson, // Recibe el JSON como String
            @RequestPart("file") MultipartFile fotoPerfil) {

        Logger logger = LoggerFactory.getLogger(this.getClass());

        // Imprimir el JSON recibido para depuración
        logger.info("JSON recibido: {}", inscripcionJson);

        // Convierte el JSON a objeto Inscripcion
        Inscripcion inscripcionDto;
        try {
            inscripcionDto = objectMapper.readValue(inscripcionJson, Inscripcion.class);
            // Imprimir el objeto Inscripcion convertido para depuración
            logger.info("Objeto Inscripcion convertido: {}", inscripcionDto);
        } catch (IOException e) {
            logger.error("Error al convertir JSON a objeto Inscripcion", e);
            return ResponseEntity.badRequest().body(null); // Maneja la excepción según lo necesites
        }

        Inscripcion nuevaInscripcionConRol = inscripcionesService.crearInscripcion(inscripcionDto, fotoPerfil);
        return new ResponseEntity<>(nuevaInscripcionConRol, HttpStatus.CREATED);
    }

    @PostMapping(value = "/crear-persona", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<String> crearPersona(
            @ModelAttribute Persona personaDTO,
            @RequestParam("file") MultipartFile fotoPerfil) {
        try {
            // Llama al servicio pasando directamente el MultipartFile
            inscripcionesService.crearPersonaConFoto(personaDTO, fotoPerfil);
            return new ResponseEntity<>("Persona creada exitosamente.", HttpStatus.CREATED);
        } catch (PersonaCreationException e) {
            // Manejar la excepción personalizada
            return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        } catch (Exception e) {
            // Manejar otras excepciones no esperadas
            return new ResponseEntity<>("Error inesperado al crear la persona: " + e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PutMapping(value = "/{idInscripcion}", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<Inscripcion> actualizarInscripcion(
            @PathVariable Long idInscripcion,
            @RequestPart("inscripcion") String inscripcionJson,
            @RequestPart("file") MultipartFile fotoPerfil) {

        Logger logger = LoggerFactory.getLogger(this.getClass());

        // Imprimir el JSON recibido para depuración
        logger.info("JSON recibido para actualización: {}", inscripcionJson);

        // Convertir el JSON a objeto Inscripcion
        Inscripcion inscripcionDto;
        try {
            inscripcionDto = objectMapper.readValue(inscripcionJson, Inscripcion.class);
            inscripcionDto.setIdInscripcion(idInscripcion);

            //Settear los ids de los objetos que se enviaran a sus respectivos microservicios
            Inscripcion inscripcionEncontrada = inscripcionesService.buscarInscripcionPorId(idInscripcion);

            inscripcionDto.getUsuario().setIdUsuario(inscripcionEncontrada.getUsuario().getIdUsuario());
            inscripcionDto.getPersona().setId(inscripcionEncontrada.getPersona().getId());

            if(inscripcionEncontrada.getAdministrador() != null && inscripcionEncontrada.getAdministrativo() == null && inscripcionEncontrada.getDocente() == null && inscripcionEncontrada.getEstudiante() == null){
                inscripcionDto.getAdministrador().setIdAdministrador(inscripcionEncontrada.getAdministrador().getIdAdministrador());
            }else if(inscripcionEncontrada.getIdAdministrativo() != null && inscripcionEncontrada.getAdministrador() == null && inscripcionEncontrada.getDocente() == null && inscripcionEncontrada.getEstudiante() == null){
                inscripcionDto.getAdministrativo().setIdAdministrativo(inscripcionEncontrada.getAdministrativo().getIdAdministrativo());
            }else if(inscripcionEncontrada.getDocente() != null && inscripcionEncontrada.getAdministrador() == null && inscripcionEncontrada.getAdministrativo() == null && inscripcionEncontrada.getEstudiante() == null){
                inscripcionDto.getDocente().setIdDocente(inscripcionEncontrada.getDocente().getIdDocente());
            }else if(inscripcionEncontrada.getEstudiante() != null &&inscripcionEncontrada.getAdministrador() == null && inscripcionEncontrada.getAdministrativo() == null && inscripcionEncontrada.getDocente() == null){
                inscripcionDto.getEstudiante().setIdEstudiante(inscripcionEncontrada.getEstudiante().getIdEstudiante());
            }

            // Imprimir el objeto Inscripcion convertido para depuración
            logger.info("Objeto Inscripcion convertido para actualización: {}", inscripcionDto);
        } catch (IOException e) {
            logger.error("Error al convertir JSON a objeto Inscripcion", e);
            return ResponseEntity.badRequest().body(null);
        }

        // Llamar al servicio para actualizar la inscripción
        try {
            Inscripcion inscripcionActualizada = inscripcionesService.editarInscripcion(idInscripcion, inscripcionDto, fotoPerfil);
            return new ResponseEntity<>(inscripcionActualizada, HttpStatus.OK);
        } catch (RuntimeException e) {
            logger.error("Error al actualizar la inscripción con rol", e);
            System.out.println("Estos son los datos mandados: "+inscripcionDto);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }


    @DeleteMapping("/{id}")
    public ResponseEntity<String> eliminarDatosInscripcion(@PathVariable Long id) {
        inscripcionesService.eliminarInscripcion(id);
        return new ResponseEntity<>("La inscripción Con Rol con ID " + id + " ha sido eliminada exitosamente.", HttpStatus.OK);
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

    @GetMapping("/{id}")
    public ResponseEntity<Inscripcion> listarInscripcionPorId(@PathVariable(required = true) Long id) {
        Inscripcion inscripcion = inscripcionesService.buscarInscripcionPorId(id);
        return new ResponseEntity<>(inscripcion, HttpStatus.OK);
    }

    @GetMapping("/usuario/{idUsuario}")
    public ResponseEntity<Inscripcion> buscarInscripcionPorIdUsuario(@PathVariable(required = true) Long idUsuario){
        Inscripcion inscripcion = inscripcionesService.buscarInscripcionPorIdUsuario(idUsuario);
        return new ResponseEntity<>(inscripcion, HttpStatus.OK);
    }
}