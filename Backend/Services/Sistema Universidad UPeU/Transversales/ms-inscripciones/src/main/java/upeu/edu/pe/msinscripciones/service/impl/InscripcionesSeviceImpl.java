package upeu.edu.pe.msinscripciones.service.impl;

import feign.FeignException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import upeu.edu.pe.msinscripciones.dto.*;
import upeu.edu.pe.msinscripciones.entity.Inscripcion;
import upeu.edu.pe.msinscripciones.exception.ResourceNotFoundException;
import upeu.edu.pe.msinscripciones.feign.*;
import upeu.edu.pe.msinscripciones.repository.InscripcionesRepository;
import upeu.edu.pe.msinscripciones.service.InscripcionesService;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class InscripcionesSeviceImpl implements InscripcionesService {
    //INYECCION DE DEPENDENCIAS PRINCIPALES PARA INSCRIPCION
    @Autowired
    private InscripcionesRepository inscripcionesRepository;

    //INYECCION DE DEPENDENCIAS SECUNDARIAS
    @Autowired
    private RolFeign rolFeign;
    @Autowired
    private UsuarioFeign usuarioFeign;
    @Autowired
    private PersonaFeign personaFeign;
    @Autowired
    private EstudianteFeign estudianteFeign;
    @Autowired
    private DocenteFeign docenteFeign;

    //METODOS PRINCIPALES DE INSCRIPCION
    @Override
    public Inscripcion crearInscripcion(Inscripcion inscripcionDTO) {
        Inscripcion inscripcion = new Inscripcion();

    try {
        // Crear Usuario y asignar el ID
        ResponseEntity<Usuario> usuarioResponse = usuarioFeign.crearUsuarioDto(inscripcionDTO.getUsuario());
        if (usuarioResponse.getBody() == null) {
            throw new RuntimeException("No se pudo crear el Usuario.");
        }
        inscripcion.setIdUsuario(usuarioResponse.getBody().getIdUsuario());

    } catch (FeignException e) {
        throw new RuntimeException("Error al comunicarse con los microservicios: " + e.getMessage(), e);
    }

    // Guardar la inscripción en la base de datos como "Sin Rol"
    inscripcion.setInscripcionConRol("Sin Rol");
    inscripcionesRepository.save(inscripcion);

    return inscripcion;
    }

    @Override
    public Inscripcion crearInscripcionConRol(Inscripcion inscripcionDTO) {
        Inscripcion inscripcion = new Inscripcion();

        try {
            // Crear Rol y asignar el ID del rol
            ResponseEntity<Rol> rolResponse = rolFeign.crearRolDto(inscripcionDTO.getRol());
            if (rolResponse.getBody() == null) {
                throw new RuntimeException("No se pudo crear el Rol.");
            }
            inscripcion.setIdRol(rolResponse.getBody().getIdRol());

            // Crear Usuario y asignar el ID
            ResponseEntity<Usuario> usuarioResponse = usuarioFeign.crearUsuarioDto(inscripcionDTO.getUsuario());
            if (usuarioResponse.getBody() == null) {
                throw new RuntimeException("No se pudo crear el Usuario.");
            }
            inscripcion.setIdUsuario(usuarioResponse.getBody().getIdUsuario());

        } catch (FeignException e) {
            throw new RuntimeException("Error al comunicarse con los microservicios: " + e.getMessage(), e);
        }

// Guardar la inscripción en la base de datos si fuera necesario
        inscripcion.setInscripcionConRol("Con Rol");
        inscripcionesRepository.save(inscripcion);

        return inscripcion;
    }

    @Override
    public List<Inscripcion> listarInscripcion() {
        List<Inscripcion> inscripciones = inscripcionesRepository.findAll();

        inscripciones.forEach(inscripcion -> {
            // Identificamos si es una Inscripcion con Rol
            if (inscripcion.getIdRol() != null) {
                try {
                    ResponseEntity<Rol> rolResponse = rolFeign.listarRolDtoPorId(inscripcion.getIdRol());
                    if (rolResponse.getBody() != null) {
                        inscripcion.setRol(rolResponse.getBody());
                    }
                } catch (FeignException e) {
                    System.out.println("Error al obtener el Rol: " + e.getMessage());
                }
            }

            // Intentamos obtener la Inscripcion ya sea Con Rol o Sin Rol
            if (inscripcion.getIdUsuario() != null) {
                try {
                    ResponseEntity<Usuario> usuarioResponse = usuarioFeign.listarUsuarioDtoPorId(inscripcion.getIdUsuario());
                    if (usuarioResponse.getBody() != null) {
                        inscripcion.setUsuario(usuarioResponse.getBody());
                    }
                } catch (FeignException e) {
                    System.out.println("Error al obtener el Usuario: " + e.getMessage());
                }
            }
        });

        return inscripciones;
    }

    @Override
    public Inscripcion buscarInscripcionPorId(Long id) {
        // Buscar la inscripción por ID
    Optional<Inscripcion> inscripcionOpt = inscripcionesRepository.findById(id);
    if (!inscripcionOpt.isPresent()) {
        throw new RuntimeException("Inscripción no encontrada con ID: " + id);
    }

    Inscripcion inscripcion = inscripcionOpt.get();

    try {
        // Obtener el Rol relacionado si existe
        if (inscripcion.getIdRol() != null) {
            ResponseEntity<Rol> rolResponse = rolFeign.listarRolDtoPorId(inscripcion.getIdRol());
            if (rolResponse.getBody() != null) {
                inscripcion.setRol(rolResponse.getBody());
            } else {
                throw new RuntimeException("No se pudo obtener el Rol con ID: " + inscripcion.getIdRol());
            }
        }

        // Obtener el Usuario relacionado si existe
        if (inscripcion.getIdUsuario() != null) {
            ResponseEntity<Usuario> usuarioResponse = usuarioFeign.listarUsuarioDtoPorId(inscripcion.getIdUsuario());
            if (usuarioResponse.getBody() != null) {
                inscripcion.setUsuario(usuarioResponse.getBody());
            } else {
                throw new RuntimeException("No se pudo obtener el Usuario con ID: " + inscripcion.getIdUsuario());
            }
        }

    } catch (FeignException e) {
        throw new RuntimeException("Error al comunicarse con los microservicios: " + e.getMessage(), e);
    }

    return inscripcion;
    }

    @Override
    public Inscripcion editarInscripcion(Long id, Inscripcion inscripcionDTO) {
        // Buscar la inscripción por ID
        Optional<Inscripcion> inscripcionOpt = inscripcionesRepository.findById(id);
        if (!inscripcionOpt.isPresent()) {
            throw new RuntimeException("Inscripción no encontrada con ID: " + id);
        }

        Inscripcion inscripcion = inscripcionOpt.get();

        try {
            // Editar Rol si es necesario
            if (inscripcionDTO.getRol() != null) {
                ResponseEntity<Rol> rolResponse = rolFeign.actualizarRolDto(inscripcion.getIdRol(), inscripcionDTO.getRol());
                if (rolResponse.getBody() != null) {
                    inscripcion.setIdRol(rolResponse.getBody().getIdRol());
                } else {
                    throw new RuntimeException("No se pudo editar el Rol.");
                }
            }

            // Editar Usuario si es necesario
            if (inscripcionDTO.getUsuario() != null) {
                ResponseEntity<Usuario> usuarioResponse = usuarioFeign.actualizarUsuarioDto(inscripcion.getIdUsuario(), inscripcionDTO.getUsuario());
                if (usuarioResponse.getBody() != null) {
                    inscripcion.setIdUsuario(usuarioResponse.getBody().getIdUsuario());
                } else {
                    throw new RuntimeException("No se pudo editar el Usuario.");
                }
            }

        } catch (FeignException e) {
            throw new RuntimeException("Error al comunicarse con los microservicios: " + e.getMessage(), e);
        }

        // Actualizar campos propios de la inscripción si es necesario
        inscripcion.setInscripcionConRol(inscripcionDTO.getInscripcionConRol());

        // Guardar los cambios en la base de datos
        return inscripcionesRepository.save(inscripcion);
    }

    @Override
    public void eliminarInscripcion(Long id) {
        // Buscar la inscripción por ID
        Optional<Inscripcion> inscripcionOpt = inscripcionesRepository.findById(id);
        if (!inscripcionOpt.isPresent()) {
            throw new RuntimeException("Inscripción no encontrada con ID: " + id);
        }

        Inscripcion inscripcion = inscripcionOpt.get();

        try {
            // Eliminar Rol relacionado
            if (inscripcion.getIdRol() != null) {
                rolFeign.eliminarRolDto(inscripcion.getIdRol());
            }

            // Eliminar Usuario relacionado
            if (inscripcion.getIdUsuario() != null) {
                usuarioFeign.eliminarUsuarioDto(inscripcion.getIdUsuario());
            }

        } catch (FeignException e) {
            throw new RuntimeException("Error al comunicarse con los microservicios: " + e.getMessage(), e);
        }

        // Eliminar la inscripción de la base de datos
        inscripcionesRepository.delete(inscripcion);
    }
}
