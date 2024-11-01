package upeu.edu.pe.msdocente.service.impl;

import feign.FeignException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import upeu.edu.pe.msdocente.dto.Persona;
import upeu.edu.pe.msdocente.entity.Docente;
import upeu.edu.pe.msdocente.exception.ResourceNotFoundException;
import upeu.edu.pe.msdocente.feign.PersonaFeign;
import upeu.edu.pe.msdocente.repository.DocenteRepository;
import upeu.edu.pe.msdocente.service.DocenteService;

import java.util.List;

@Service

public class DocenteServiceImpl implements DocenteService {

    @Autowired
    DocenteRepository docenteRepository;

    @Autowired
    private PersonaFeign personaFeign;

    @Override
    public Docente guardarDocente(Docente docente) {
        return docenteRepository.save(docente);
    }

    @Override
    public List<Docente> listarDocente() {
        List<Docente> docentes = docenteRepository.findAll();

        // Recorremos cada docente y asignamos la persona
        docentes.forEach(docente -> {
            try {
                ResponseEntity<Persona> personaResponse = personaFeign.listarPersonaDtoPorId(docente.getIdPersona());
                if (personaResponse.getBody() == null) {
                    // Manejar el caso en el que la persona no existe
                    throw new ResourceNotFoundException("Persona con ID " + docente.getIdPersona() + " no existe");
                }
                docente.setPersona(personaResponse.getBody());
            } catch (FeignException e) {
                // Manejar el error en el servidor de OpenFeign para personas
                throw new RuntimeException("Error al obtener la persona con ID " + docente.getIdPersona(), e);
            }
        });

        return docentes;
    }

    @Override
    public Docente buscarDocentePorId(Long id) {
        Docente docente = docenteRepository.findById(id).get();

        Persona persona = personaFeign.listarPersonaDtoPorId(docente.getIdPersona()).getBody();

        docente.setPersona(persona);

        return docente;
    }

    @Override
    public Docente editarDocente(Docente docente) {
        return docenteRepository.save(docente);
    }

    @Override
    public void eliminarDocente(Long id) {
        docenteRepository.deleteById(id);
    }
}
