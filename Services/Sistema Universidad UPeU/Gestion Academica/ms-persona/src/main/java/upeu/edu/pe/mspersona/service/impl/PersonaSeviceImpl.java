package upeu.edu.pe.mspersona.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import upeu.edu.pe.mspersona.entity.Persona;
import upeu.edu.pe.mspersona.repository.PersonaRepository;
import upeu.edu.pe.mspersona.service.PersonaService;

import java.util.List;

@Service
public class PersonaSeviceImpl implements PersonaService {
    @Autowired
    private PersonaRepository personaRepository;

    @Override
    public Persona guardarPersona(Persona persona) {
        return personaRepository.save(persona);
    }

    @Override
    public List<Persona> listarPersona(){
        return personaRepository.findAll();
    }

    @Override
    public Persona buscarPersonaPorId(Long id){
        return personaRepository.findById(id).get();
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
