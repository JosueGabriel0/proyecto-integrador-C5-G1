package upeu.edu.pe.msadministrador.service.impl;

import feign.FeignException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import upeu.edu.pe.msadministrador.entity.Administrador;
import upeu.edu.pe.msadministrador.exception.ResourceNotFoundException;
import upeu.edu.pe.msadministrador.feign.PersonaFeign;
import upeu.edu.pe.msadministrador.service.AdministradorService;

import java.util.List;

@Service

public class AdministradorServiceImpl implements AdministradorService {

    @Autowired
    upeu.edu.pe.msadministrador.repository.administradorRepository administradorRepository;

    @Autowired
    private PersonaFeign personaFeign;

    @Override
    public Administrador guardarAdministrador(Administrador administrador) {
        return administradorRepository.save(administrador);
    }

    @Override
    public List<Administrador> listarAdministrador() {
        List<Administrador> Administradors = administradorRepository.findAll();

        // Recorremos cada Administrador y asignamos la persona
        Administradors.forEach(Administrador -> {
            try {
                ResponseEntity<Persona> personaResponse = personaFeign.listarPersonaDtoPorId(Administrador.getIdPersona());
                if (personaResponse.getBody() == null) {
                    // Manejar el caso en el que la persona no existe
                    throw new ResourceNotFoundException("Persona con ID " + Administrador.getIdPersona() + " no existe");
                }
                Administrador.setPersona(personaResponse.getBody());
            } catch (FeignException e) {
                // Manejar el error en el servidor de OpenFeign para personas
                throw new RuntimeException("Error al obtener la persona con ID " + Administrador.getIdPersona(), e);
            }
        });

        return Administradors;
    }

    @Override
    public Administrador buscarAdministradorPorId(Long id) {
        Administrador Administrador = administradorRepository.findById(id).get();

        Persona persona = personaFeign.listarPersonaDtoPorId(Administrador.getIdPersona()).getBody();

        Administrador.setPersona(persona);

        return Administrador;
    }

    @Override
    public Administrador editarAdministrador(Administrador administrador) {
        return administradorRepository.save(administrador);
    }

    @Override
    public void eliminarAdministrador(Long id) {
        administradorRepository.deleteById(id);
    }
}
