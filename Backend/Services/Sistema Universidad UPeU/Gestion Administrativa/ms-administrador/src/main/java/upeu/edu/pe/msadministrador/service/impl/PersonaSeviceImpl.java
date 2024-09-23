package upeu.edu.pe.msadministrador.service.impl;

import feign.FeignException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import upeu.edu.pe.msadministrador.dto.Persona;
import upeu.edu.pe.msadministrador.entity.Administrador;
import upeu.edu.pe.msadministrador.exception.ResourceNotFoundException;
//import upeu.edu.pe.msadministrador.feign.PersonaFeign;
import upeu.edu.pe.msadministrador.feign.PersonaFeign;
import upeu.edu.pe.msadministrador.repository.AdministradorRepository;

import java.util.List;

@Service
public class AdministradorSeviceImpl implements AdministradorService {
    //INYECCION DE DEPENDENCIAS PRINCIPALES PARA Administrador
    @Autowired
    private AdministradorRepository administradorRepository;

    //INYECCION DE DEPENDENCIAS SECUNDARIAS PARA USUARIO CON SU ROL
    @Autowired
    private PersonaFeign personaFeign;

    //METODOS PRINCIPALES DE Administrador
    @Override
    public Administrador guardarAdministrador(upeu.edu.pe.msAdministrador.entity.Administrador Administrador) {
        return administradorRepository.save(Administrador);
    }

    @Override
    public List<Administrador> listarAdministrador(){
        List<Administrador> Administradors = administradorRepository.findAll();

        Administradors.forEach(administrador -> {
            try {
                // Intentamos obtener el usuario correspondiente para la Administrador
                ResponseEntity<Persona> personaResponse = personaFeign.listarPersonaDtoPorId(administrador.getIdAdministrador());

                if(personaResponse.getBody() == null) {
                    throw new ResourceNotFoundException("Usuario con ID " + administrador.getIdAdministrador() + " no encontrado");
                }

                // Asignamos el usuario a la Administrador
                administrador.setUsuario(usuarioResponse.getBody());

            } catch (FeignException.NotFound e) {
                // Manejo específico si el usuario no es encontrado
                throw new ResourceNotFoundException("Usuario con ID " + administrador.getIdUsuario() + " no encontrado");

            } catch (FeignException e) {
                // Otros errores de Feign, por ejemplo, problemas de conexión o timeouts
                throw new RuntimeException("Error al obtener el Usuario con ID " + Administrador.getIdUsuario(), e);
            }
        });

        return Administradors;
    }

    @Override
    public upeu.edu.pe.msAdministrador.entity.Administrador buscarAdministradorPorId(Long id){
        upeu.edu.pe.msAdministrador.entity.Administrador Administrador = administradorRepository.findById(id).orElseThrow(() ->  new ResourceNotFoundException("La Administrador con ID "+id+" no existe"));
        try {
            ResponseEntity<Usuario> usuarioResponse = usuarioFeign.listarUsuarioDtoPorId(Administrador.getIdUsuario());
            if(usuarioResponse.getBody() == null){
                throw new ResourceNotFoundException("Usuario con ID "+Administrador.getIdUsuario()+" no encontrado");
            }
            Administrador.setUsuario(usuarioResponse.getBody());
        }catch (FeignException e){
            throw new RuntimeException("Error al obtener el Usuario con ID " + Administrador.getIdUsuario(),e);
        }

        return Administrador;
        /*
        // Obtener la Administrador por ID, o lanzar una excepción si no existe
Administrador Administrador = AdministradorRepository.findById(id)
        .orElseThrow(() -> new ResourceNotFoundException("La Administrador con ID " + id + " no existe"));

try {
    // Obtener el usuario asociado a la Administrador utilizando Feign
    ResponseEntity<Usuario> usuarioResponse = usuarioFeign.listarUsuarioDtoPorId(Administrador.getIdUsuario());
    if (usuarioResponse.getBody() == null) {
        throw new ResourceNotFoundException("Usuario con ID " + Administrador.getIdUsuario() + " no encontrado");
    }

    Usuario usuario = usuarioResponse.getBody();

    // Obtener el rol asociado al usuario utilizando Feign
    try {
        ResponseEntity<Rol> rolResponse = rolFeign.listarRolDtoPorId(usuario.getIdRol());
        if (rolResponse.getBody() == null) {
            throw new ResourceNotFoundException("Rol con ID " + usuario.getIdRol() + " no encontrado");
        }
        // Asignar el rol al usuario
        usuario.setRol(rolResponse.getBody());

    } catch (FeignException e) {
        // Manejo de error al obtener el rol a través de Feign
        throw new RuntimeException("Error al obtener el Rol con ID " + usuario.getIdRol(), e);
    }

    // Asignar el usuario a la Administrador
    Administrador.setUsuario(usuario);

} catch (FeignException e) {
    // Manejo de error al obtener el usuario a través de Feign
    throw new RuntimeException("Error al obtener el Usuario con ID " + Administrador.getIdUsuario(), e);
}

// Retornar la Administrador con el usuario y rol asignados
return Administrador;
         */
    }

    @Override
    public upeu.edu.pe.msAdministrador.entity.Administrador editarAdministrador(upeu.edu.pe.msAdministrador.entity.Administrador Administrador) {
        return administradorRepository.save(Administrador);
    }

    @Override
    public void eliminarAdministrador(Long id){
        administradorRepository.deleteById(id);
    }

    /*
    //INYECCION DE DEPENDENCIAS SECUNDARIAS PARA USUARIO CON SU ROL
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
     */
}
