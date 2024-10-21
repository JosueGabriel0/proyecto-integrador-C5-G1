package upeu.edu.pe.msinscripciones.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.http.*;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.multipart.MultipartFile;
import upeu.edu.pe.msinscripciones.dto.*;
import upeu.edu.pe.msinscripciones.entity.Inscripcion;
import upeu.edu.pe.msinscripciones.exception.PersonaCreationException;
import upeu.edu.pe.msinscripciones.feign.*;
import upeu.edu.pe.msinscripciones.repository.InscripcionesRepository;
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
    @Autowired
    private RolFeign rolFeign;
    @Autowired
    private UsuarioFeign usuarioFeign;
    @Autowired
    private PersonaFeign personaFeign;
    @Autowired
    private AdministradorFeign administradorFeign;
    @Autowired
    private AdministrativoFeign administrativoFeign;
    @Autowired
    private DocenteFeign docenteFeign;
    @Autowired
    private EstudianteFeign estudianteFeign;
    @Autowired
    private RestTemplate restTemplate;

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

        Inscripcion nuevaInscripcion = inscripcionesService.crearInscripcion(inscripcionDto, fotoPerfil);
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
    public ResponseEntity<Inscripcion> crearInscripcionConRol(
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

        Inscripcion nuevaInscripcionConRol = inscripcionesService.crearInscripcionConRol(inscripcionDto, fotoPerfil);
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