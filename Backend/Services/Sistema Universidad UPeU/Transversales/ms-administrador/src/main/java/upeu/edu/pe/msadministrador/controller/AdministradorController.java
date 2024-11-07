package upeu.edu.pe.msadministrador.controller;

import feign.FeignException;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import upeu.edu.pe.msadministrador.dto.ErrorResponseDto;
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
            Persona personaDto = personaFeign.listarPersonaDtoPorId(administrador.getIdPersona()).getBody();
            if (personaDto == null || personaDto.getId() == null) {
                String ErrorMessage = "Error: Persona no encontrada";
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ErrorResponseDto(ErrorMessage));
            }

            administrador.setPersona(personaDto);

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
    public ResponseEntity<?> editarAdministradorResponseEntity(@PathVariable(required = true) Long id,@RequestBody Administrador administrador){
        administrador.setIdAdministrador(id);
        try {
            // Verificar si el curso existe
            Persona personaDto = personaFeign.listarPersonaDtoPorId(administrador.getIdPersona()).getBody();
            if (personaDto == null || personaDto.getId() == null) {
                String ErrorMessage = "Error: Persona no encontrada";
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ErrorResponseDto(ErrorMessage));
            }

            administrador.setPersona(personaDto);

            Administrador AdministradorEditado = administradorService.editarAdministrador(administrador);

            // Retornar respuesta exitosa
            return ResponseEntity.status(HttpStatus.CREATED).body(AdministradorEditado);

        } catch (FeignException e) {
            // Imprimir los detalles del error que Feign está arrojando
            String errorMensaje = "Error al comunicarse con otro servicio: " + e.getMessage();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(errorMensaje);

        } catch (Exception e) {
            // Manejo de cualquier otro error inesperado
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error interno del servidor: " + e.getMessage());
        }
    }

    @DeleteMapping("/{id}")
    public String eliminarAdministradorResponseEntity(@PathVariable( required = true) Long id){
        administradorService.eliminarAdministrador(id);
        return "Administrador eliminado";
    }
}
