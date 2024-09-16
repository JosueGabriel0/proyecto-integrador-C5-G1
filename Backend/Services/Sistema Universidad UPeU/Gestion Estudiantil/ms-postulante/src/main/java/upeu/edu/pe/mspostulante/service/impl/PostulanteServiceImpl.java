package upeu.edu.pe.mspostulante.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import upeu.edu.pe.mspostulante.dto.Persona;
import upeu.edu.pe.mspostulante.entity.Postulante;
import upeu.edu.pe.mspostulante.repository.PostulanteRepository;
import upeu.edu.pe.mspostulante.service.PostulanteService;

import java.util.List;@Service
public class PostulanteServiceImpl implements PostulanteService{
    @Autowired
    PostulanteRepository postulanteRepository;
    @Autowired
    Private PersonaFeign personaFeign;
    @Override
    public Postulante guardarPostulante(Postulante postulante) {
        return postulanteRepository.save(postulante);
    }

    @Override
    public List<Postulante> listarPostulante() {
        List<Postulante> docentes = postulanteRepository.findAll();

        // Recorremos cada docente y asignamos el Persona y detalles
        postulantes.forEach(postulante -> {
            Persona persona = PersonaFeign.listarPersonaDtoPorId(postulante.getPersonaId()).getBody();
            postulante.setPersona(persona);
        });

        // Recorremos cada docente y asignamos la persona
        postulante.forEach(postulante -> {
            Persona persona = personaFeign.listarPersonaDtoPorId(postulante.getIdPersona()).getBody();
            postulante.setPersona(persona);
        });

        return postulante;
    }

    @Override
    public Postulante buscarPostulantePorId(long id) {
        return postulanteRepository.findById(id).get();
    }

    @Override
    public Postulante editarPostulante(Postulante postulante) {
        return postulanteRepository.save(postulante);
    }

    @Override
    public void eliminarPostulante(long id) {
        postulanteRepository.deleteById(id);
    }
}


