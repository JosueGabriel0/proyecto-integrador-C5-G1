package upeu.edu.pe.mspersona.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import upeu.edu.pe.mspersona.entity.Persona;
import upeu.edu.pe.mspersona.exception.ResourceNotFoundException;
import upeu.edu.pe.mspersona.service.PersonaService;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/persona")
public class PersonaController {
    @Autowired
    private PersonaService personaService;

    @Value("${upload.dir}")
    private String uploadDir; // Accede a la ruta configurada

    @PostMapping("/uploadProfileImage/{id}")
    public ResponseEntity<String> uploadProfileImage(@PathVariable Long id, @RequestParam("file") MultipartFile file) {
        if (file.isEmpty()) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("El archivo está vacío");
        }

        String contentType = file.getContentType();
        if (contentType == null || !contentType.startsWith("image/")) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("El archivo no es una imagen");
        }

        try {
            String fileName = System.currentTimeMillis() + "_" + file.getOriginalFilename();
            Path path = Paths.get(uploadDir, fileName);
            Files.copy(file.getInputStream(), path);

            String imageUrl = "/uploads/" + fileName;

            Persona persona = personaService.buscarPersonaPorId(id);
            persona.setFotoPerfil(imageUrl);
            personaService.editarPersona(persona);

            return ResponseEntity.ok("{\"url\": \"" + imageUrl + "\"}");
        } catch (IOException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error al guardar la imagen");
        }
    }

    @PostMapping
    public ResponseEntity<Persona> guardarPersonaResponseEntity(@RequestBody Persona persona){
        return ResponseEntity.ok(personaService.guardarPersona(persona));
    }

    @GetMapping
    public ResponseEntity<List<Persona>> listarPersonaResponseEntity(){
        try {
            List<Persona> personas = personaService.listarPersona();
            return ResponseEntity.ok(personas);
        } catch (Exception e) {
            // Capturamos cualquier error inesperado y devolvemos una respuesta de error
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new ArrayList<>());
        }
    }

    /**
     * Manejo de la excepción ResourceNotFoundException.
     * Devuelve un 404 si no se encuentra el recurso solicitado.
     *
     * @param ex Excepción capturada.
     * @return Mensaje de error con un código de estado 404.
     */
    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<String> handleResourceNotFoundException(ResourceNotFoundException ex) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(ex.getMessage());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Persona> buscarPersonaPorIdResponseEntity(@PathVariable(required = true) Long id){
        return ResponseEntity.ok(personaService.buscarPersonaPorId(id));
    }

    @PutMapping("/{id}")
    public ResponseEntity<Persona> editarPersonaResponseEntity(@PathVariable (required = true) Long id,@RequestBody Persona persona){
        persona.setId(id);
        return ResponseEntity.ok(personaService.editarPersona(persona));
    }

    @DeleteMapping("{id}")
    public ResponseEntity<String> eliminarPersona(@PathVariable Long id) {
        try {
            // Lógica para eliminar la persona
            personaService.eliminarPersona(id);

            // Retornar código 200 OK con mensaje de éxito
            return ResponseEntity.ok("Persona eliminada exitosamente.");
        } catch (Exception e) {
            // En caso de error, retornar un código de error y mensaje apropiado
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Error al eliminar la persona: " + e.getMessage());
        }
    }
}
