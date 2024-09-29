package upeu.edu.pe.msadministrador.controller;

import feign.FeignException;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import upeu.edu.pe.msadministrador.dto.Persona;
import upeu.edu.pe.msadministrador.entity.Administrador;
import upeu.edu.pe.msadministrador.feign.PersonaFeign;
import upeu.edu.pe.msadministrador.service.AdministradorService;

import java.util.List;

@RestController
@RequestMapping("/administrador")

public class AdministradorController {
    @Autowired
    private AdministradorService administradorService;

    @Autowired
    private PersonaFeign personaFeign;

    @PostMapping
    public ResponseEntity<?> guardarAdministradorResponseEntity(@RequestBody Administrador administrador){
        try {
            // Verificar si el curso existe
            ResponseEntity<Persona> personaResponse = personaFeign.listarPersonaDtoPorId(administrador.getIdPersona());
            if (personaResponse.getStatusCode() == HttpStatus.NOT_FOUND || personaResponse.getBody() == null) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body("No existe la persona");
            }
            Persona persona = personaResponse.getBody();


            administrador.setPersona(persona);

            // Guardar el pedido si todas las validaciones pasaron
            Administrador AdministradorGuardado = administradorService.guardarAdministrador(administrador);

            // Retornar respuesta exitosa
            return ResponseEntity.status(HttpStatus.CREATED).body(AdministradorGuardado);

        } catch (FeignException e) {
            // Imprimir los detalles del error que Feign está arrojando
            String errorMensaje = "Error al comunicarse con otro servicio: " + e.getMessage();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(errorMensaje);

        } catch (Exception e) {
            // Manejo de cualquier otro error inesperado
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error interno del servidor: " + e.getMessage());
        }
    }

    @GetMapping
    public ResponseEntity<List<Administrador>> listarAdministradorsResponseEntity(){
        return ResponseEntity.ok(administradorService.listarAdministrador());
    }

    @CircuitBreaker(name = "AdministradorListarPorIdCB", fallbackMethod = "fallbackAdministrador")
    @GetMapping("/{id}")
    public ResponseEntity<Administrador> buscarAdministradorPorIdResponseEntity(@PathVariable( required = true) Long id){
        return ResponseEntity.ok(administradorService.buscarAdministradorPorId(id));
    }

    @PutMapping("/{id}")
    public ResponseEntity<Administrador> editarAdministradorResponseEntity(@PathVariable( required = true) Long id, @RequestBody Administrador administrador){
        administrador.setIdAdministrador(id);
        return ResponseEntity.ok(administradorService.editarAdministrador(administrador));
    }

    @DeleteMapping("/{id}")
    public String eliminarAdministradorResponseEntity(@PathVariable( required = true) Long id){
        administradorService.eliminarAdministrador(id);
        return "Administrador eliminado";
    }
}
