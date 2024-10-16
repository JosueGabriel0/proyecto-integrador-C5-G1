package upeu.edu.pe.msinscripciones.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import upeu.edu.pe.msinscripciones.dto.*;
import upeu.edu.pe.msinscripciones.entity.Inscripcion;
import upeu.edu.pe.msinscripciones.feign.*;
import upeu.edu.pe.msinscripciones.repository.InscripcionesRepository;
import upeu.edu.pe.msinscripciones.service.InscripcionesService;

import java.util.List;

@RestController
@RequestMapping("/inscripcion")
public class InscipcionesController {
    @Autowired
    private InscripcionesService inscripcionesService;

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

    //CRUD DE INSCRIPCION
    @PostMapping
    public ResponseEntity<?> crearInscripcion(@ModelAttribute Inscripcion inscripcionDTO, @RequestParam("file") MultipartFile fotoPerfil) {
        Rol rolDto = rolFeign.listarRolDtoPorId(inscripcionDTO.getIdRol()).getBody();

        if(rolDto == null || rolDto.getIdRol() == null){
            String ErrorMessage = "Error: Rol no encontrado";
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ErrorResponseDto(ErrorMessage));
        }


        Usuario usuarioDto = usuarioFeign.listarUsuarioDtoPorId(inscripcionDTO.getIdUsuario()).getBody();

        if(usuarioDto == null || usuarioDto.getIdUsuario() == null){
            String ErrorMessage = "Error: Usuario no encontrado";
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ErrorResponseDto(ErrorMessage));
        }


        Persona personaDto = personaFeign.listarPersonaDtoPorId(inscripcionDTO.getIdPersona()).getBody();

        if(personaDto == null || personaDto.getId() == null){
            String ErrorMessage = "Error: Persona no encontrada";
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ErrorResponseDto(ErrorMessage));
        }


        Administrador administrador = administradorFeign.listarAdministradorDtoPorId(inscripcionDTO.getIdAdministrador()).getBody();

        if(administrador == null || administrador.getIdAdministrador() == null){
            String ErrorMessage = "Error: Administrador no encontrado";
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ErrorResponseDto(ErrorMessage));
        }


        Administrativo administrativoDto = administrativoFeign.listarAdministrativoDtoPorId(inscripcionDTO.getIdAdministrativo()).getBody();

        if(administrativoDto == null || administrativoDto.getIdAdministrativo() == null){
            String ErrorMessage = "Error: Administrativo no encontrado";
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ErrorResponseDto(ErrorMessage));
        }


        Docente docenteDto = docenteFeign.listarDocenteDtoPorId(inscripcionDTO.getIdDocente()).getBody();

        if(docenteDto == null || docenteDto.getIdDocente() == null){
            String ErrorMessage = "Error: Docente no encontrado";
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ErrorResponseDto(ErrorMessage));
        }


        Estudiante estudianteDto = estudianteFeign.listarEstudianteDtoPorId(inscripcionDTO.getIdEstudiante()).getBody();

        if(estudianteDto == null || estudianteDto.getIdEstudiante() == null){
            String ErrorMessage = "Error: Estudiante no encontrado";
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ErrorResponseDto(ErrorMessage));
        }

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
    @PostMapping(value = "/con-rol")
    public ResponseEntity<Inscripcion> crearInscripcionConRol(@RequestBody Inscripcion inscripcion) {
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