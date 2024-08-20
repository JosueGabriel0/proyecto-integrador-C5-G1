package upeu.edu.pe.mspersona.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import upeu.edu.pe.mspersona.entity.Persona;
import upeu.edu.pe.mspersona.service.PersonaService;

import java.util.List;

@RestController
@RequestMapping("/persona")
public class PersonaController {
    @Autowired
    private PersonaService personaService;

    @PostMapping
    public ResponseEntity<Persona> guardarPersonaResponseEntity(@RequestBody Persona persona){
        return ResponseEntity.ok(personaService.guardarPersona(persona));
    }

    @GetMapping
    public ResponseEntity<List<Persona>> listarPersonaResponseEntity(){
        return ResponseEntity.ok(personaService.listarPersona());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Persona> buscarPersonaPorIdResponseEntity(@PathVariable(required = true) Long id){
        return ResponseEntity.ok(personaService.buscarPersonaPorId(id));
    }

    @PutMapping
    public ResponseEntity<Persona> editarPersonaResponseEntity(@PathVariable (required = true) Long id,@RequestBody Persona persona){
        persona.setId(id);
        return ResponseEntity.ok(personaService.editarPersona(persona));
    }

    @DeleteMapping
    public String eliminarPersonaResponseEntity(@PathVariable Long id){
        personaService.eliminarPersona(id);
        return "Persona eliminada";
    }
}
