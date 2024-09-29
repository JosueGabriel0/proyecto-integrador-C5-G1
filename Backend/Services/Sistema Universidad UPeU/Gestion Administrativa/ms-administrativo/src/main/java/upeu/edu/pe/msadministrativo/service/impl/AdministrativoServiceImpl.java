package upeu.edu.pe.msadministrativo.service.impl;

import feign.FeignException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import upeu.edu.pe.msadministrativo.entity.Administrativo;
import upeu.edu.pe.msadministrativo.exception.ResourceNotFoundException;
import upeu.edu.pe.msadministrativo.feign.PersonaFeign;
import upeu.edu.pe.msadministrativo.repository.AdministrativoRepository;
import upeu.edu.pe.msadministrativo.service.AdministrativoService;

import java.util.List;

@Service

public class AdministrativoServiceImpl implements AdministrativoService {

    @Autowired
    AdministrativoRepository administrativoRepository;

    @Autowired
    private PersonaFeign personaFeign;

    @Override
    public Administrativo guardarAdministrativo(Administrativo administrativo) {
        return administrativoRepository.save(administrativo);
    }

    @Override
    public List<Administrativo> listarAdministrativo() {
        List<Administrativo> Administrativos = administrativoRepository.findAll();

        // Recorremos cada Administrativo y asignamos la persona
        Administrativos.forEach(Administrativo -> {
            try {
                ResponseEntity<Persona> personaResponse = personaFeign.listarPersonaDtoPorId(Administrativo.getIdPersona());
                if (personaResponse.getBody() == null) {
                    // Manejar el caso en el que la persona no existe
                    throw new ResourceNotFoundException("Persona con ID " + Administrativo.getIdPersona() + " no existe");
                }
                Administrativo.setPersona(personaResponse.getBody());
            } catch (FeignException e) {
                // Manejar el error en el servidor de OpenFeign para personas
                throw new RuntimeException("Error al obtener la persona con ID " + Administrativo.getIdPersona(), e);
            }
        });

        return Administrativos;
    }

    @Override
    public Administrativo buscarAdministrativoPorId(Long id) {
        Administrativo Administrativo = administrativoRepository.findById(id).get();

        Persona persona = personaFeign.listarPersonaDtoPorId(Administrativo.getIdPersona()).getBody();

        Administrativo.setPersona(persona);

        return Administrativo;
    }

    @Override
    public Administrativo editarAdministrativo(Administrativo administrativo) {
        return administrativoRepository.save(administrativo);
    }

    @Override
    public void eliminarAdministrativo(Long id) {
        administrativoRepository.deleteById(id);
    }
}
