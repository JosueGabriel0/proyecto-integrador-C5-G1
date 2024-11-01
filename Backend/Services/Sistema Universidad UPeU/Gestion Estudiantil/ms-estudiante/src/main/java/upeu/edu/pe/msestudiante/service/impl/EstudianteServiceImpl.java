package upeu.edu.pe.msestudiante.service.impl;

import feign.FeignException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import upeu.edu.pe.msestudiante.dto.Persona;
import upeu.edu.pe.msestudiante.entity.Estudiante;
import upeu.edu.pe.msestudiante.entity.RegistroAcademico;
import upeu.edu.pe.msestudiante.exception.ResourceNotFoundException;
import upeu.edu.pe.msestudiante.feign.PersonaFeign;
import upeu.edu.pe.msestudiante.repository.EstudianteRepository;
import upeu.edu.pe.msestudiante.service.EstudianteService;

import java.util.List;

@Service

public class EstudianteServiceImpl implements EstudianteService {

    @Autowired
    EstudianteRepository estudianteRepository;

    @Autowired
    private PersonaFeign personaFeign;

    @Override
    public Estudiante guardarEstudiante(Estudiante estudiante) {
        // Asigna el estudiante a cada registro académico en el historial
        if (estudiante.getHistorialAcademico() != null) {
            for (RegistroAcademico registro : estudiante.getHistorialAcademico()) {
                registro.setEstudiante(estudiante);
            }
        }
        // Guarda el estudiante con el historial académico ya enlazado
        return estudianteRepository.save(estudiante);
    }


    @Override
    public List<Estudiante> listarEstudiante() {
        List<Estudiante> estudiantes = estudianteRepository.findAllWithHistorial();

        estudiantes.forEach(estudiante -> {
            try {
                ResponseEntity<Persona> personaResponse = personaFeign.listarPersonaDtoPorId(estudiante.getIdPersona());
                if (personaResponse.getBody() == null) {
                    throw new ResourceNotFoundException("Persona con ID " + estudiante.getIdPersona() + " no existe");
                }
                estudiante.setPersona(personaResponse.getBody());
            } catch (FeignException e) {
                throw new RuntimeException("Error al obtener la persona con ID " + estudiante.getIdPersona(), e);
            }
        });

        return estudiantes;
    }

    @Override
    public Estudiante buscarEstudiantePorId(Long id) {
        // Obtener el estudiante y validar su existencia
        Estudiante estudiante = estudianteRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Estudiante con ID " + id + " no encontrado"));

        try {

            // Obtener la persona asociada usando Feign
            Persona persona = personaFeign.listarPersonaDtoPorId(estudiante.getIdPersona()).getBody();
            if (persona == null) {
                throw new ResourceNotFoundException("Persona con ID " + estudiante.getIdPersona() + " no existe");
            }
            estudiante.setPersona(persona);

        } catch (FeignException e) {
            throw new RuntimeException("Error al comunicarse con el servicio externo", e);
        }

        return estudiante;
    }


    @Override
    public Estudiante editarEstudiante(Estudiante Estudiante) {
        return estudianteRepository.save(Estudiante);
    }

    @Override
    public void eliminarEstudiante(Long id) {
        estudianteRepository.deleteById(id);
    }
}
