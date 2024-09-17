package upeu.edu.pe.mspersona.service.impl;

import feign.FeignException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import upeu.edu.pe.mspersona.dto.Rol;
import upeu.edu.pe.mspersona.dto.Usuario;
import upeu.edu.pe.mspersona.entity.Persona;
import upeu.edu.pe.mspersona.exception.ResourceNotFoundException;
import upeu.edu.pe.mspersona.feign.RolFeign;
import upeu.edu.pe.mspersona.feign.UsuarioFeign;
import upeu.edu.pe.mspersona.repository.PersonaRepository;
import upeu.edu.pe.mspersona.service.PersonaService;

import java.util.List;

@Service
public class PersonaSeviceImpl implements PersonaService {
    //INYECCION DE DEPENDENCIAS PRINCIPALES PARA PERSONA
    @Autowired
    private PersonaRepository personaRepository;

    //METODOS PRINCIPALES DE PERSONA
    @Override
    public Persona guardarPersona(Persona persona) {
        return personaRepository.save(persona);
    }

    @Override
    public List<Persona> listarPersona(){
        List<Persona> personas = personaRepository.findAll();

        personas.forEach(persona -> {
            try {
                ResponseEntity<Usuario> usuarioResponse = usuarioFeign.listarUsuarioDtoPorId(persona.getIdUsuario());
                if(usuarioResponse.getBody() == null){
                    throw new ResourceNotFoundException("Usuario con ID "+persona.getIdUsuario()+" no encontrado");
                }
                persona.setUsuario(usuarioResponse.getBody());
            }catch (FeignException e){
                throw new RuntimeException("Error al obtener el Usuario con ID " + persona.getIdUsuario(),e);
            }
        });

        return personas;
    }

    @Override
    public Persona buscarPersonaPorId(Long id){
        Persona persona = personaRepository.findById(id).orElseThrow(() ->  new ResourceNotFoundException("La Persona con ID "+id+" no existe"));
        try {
            ResponseEntity<Usuario> usuarioResponse = usuarioFeign.listarUsuarioDtoPorId(persona.getIdUsuario());
            if(usuarioResponse.getBody() == null){
                throw new ResourceNotFoundException("Usuario con ID "+persona.getIdUsuario()+" no encontrado");
            }
        }catch (FeignException e){
            throw new RuntimeException("Error al obtener el Usuario con ID " + persona.getIdUsuario(),e);
        }

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

    //INYECCION DE DEPENDENCIAS SECUNDARIOS PARA USUARIO CON SU ROL
    @Autowired
    private UsuarioFeign usuarioFeign;

    @Autowired
    private RolFeign rolFeign;

    //METODOS SECUNDARIOS PARA USUARIO CON SU ROL
    public Usuario buscarUsuarioPorId(Long idUsuario) {
        try {
            ResponseEntity<Usuario> usuarioResponse = usuarioFeign.listarUsuarioDtoPorId(idUsuario);
            if (usuarioResponse.getBody() == null) {
                throw new ResourceNotFoundException("Usuario con ID " + idUsuario + " no existe");
            }
            return usuarioResponse.getBody();
        } catch (FeignException e) {
            throw new RuntimeException("Error al obtener el Usuario con ID " + idUsuario, e);
        }
    }

    public Rol buscarRolPorId(Long idRol) {
        try {
            ResponseEntity<Rol> rolResponse = rolFeign.listarRolDtoPorId(idRol);
            if (rolResponse.getBody() == null) {
                throw new ResourceNotFoundException("Rol con ID " + idRol + " no existe");
            }
            return rolResponse.getBody();
        } catch (FeignException e) {
            throw new RuntimeException("Error al obtener el Rol con ID " + idRol, e);
        }
    }
}
