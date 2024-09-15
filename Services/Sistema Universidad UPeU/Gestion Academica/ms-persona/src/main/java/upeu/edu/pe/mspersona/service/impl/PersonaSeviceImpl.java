package upeu.edu.pe.mspersona.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import upeu.edu.pe.mspersona.dto.Docente;
import upeu.edu.pe.mspersona.dto.Estudiante;
import upeu.edu.pe.mspersona.entity.Persona;
import upeu.edu.pe.mspersona.feign.DocenteFeign;
import upeu.edu.pe.mspersona.feign.EstudianteFeign;
import upeu.edu.pe.mspersona.repository.PersonaRepository;
import upeu.edu.pe.mspersona.service.PersonaService;

import java.util.List;

@Service
public class PersonaSeviceImpl implements PersonaService {
    @Autowired
    private PersonaRepository personaRepository;

    @Autowired
    private DocenteFeign docenteFeign;

    @Autowired
    private EstudianteFeign estudianteFeign;

    @Override
    public Persona guardarPersona(Persona persona) {
        return personaRepository.save(persona);
    }

    @Override
    public List<Persona> listarPersona(){
        List<Persona> personas = personaRepository.findAll();

        // Recorremos cada persona y asignamos el docente y detalles
        personas.forEach(persona -> {
            Docente docente = docenteFeign.listarDocenteDtoPorId(persona.getIdDocente()).getBody();
            persona.setDocente(docente);
        });

        // Recorremos cada persona y asignamos la persona
        personas.forEach(persona -> {
            Estudiante estudiante = estudianteFeign.listarEstudianteDtoPorId(persona.getIdEstudiante()).getBody();
            persona.setEstudiante(estudiante);
        });

        return personas;
    }

    @Override
    public Persona buscarPersonaPorId(Long id){
        Persona persona = personaRepository.findById(id).get();

        
        Docente docente = docenteFeign.listarDocenteDtoPorId(persona.getIdDocente()).getBody();

        Estudiante estudiante = estudianteFeign.listarEstudianteDtoPorId(docente.getIdPersona()).getBody();

        persona.setDocente(docente);
        persona.setEstudiante(estudiante);

        return persona;
    }

    @Override
    public Persona editarPersona(Persona persona) {
        return personaRepository.save(persona);
    }

    @Override
    public void eliminarPersona(Long id){
        personaRepository.deleteById(id);
    }

}
