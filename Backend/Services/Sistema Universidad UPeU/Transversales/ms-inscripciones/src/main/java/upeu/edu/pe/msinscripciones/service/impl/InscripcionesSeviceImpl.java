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

    //CUD DE INSCRIPCION
    @Override
    public Inscripcion crearInscripcion(Inscripcion inscripcionDTO) {
        Inscripcion inscripcion = new Inscripcion();

        try {
            // Verificar que el Rol existe antes de continuar
            ResponseEntity<Rol> rolResponse = rolFeign.listarRolDtoPorId(inscripcionDTO.getIdRol());
            if (rolResponse.getBody() == null) {
                throw new RuntimeException("No se pudo encontrar el Rol con ID: " + inscripcionDTO.getIdRol());
            }

            // Asignar el ID del Rol existente al Usuario
            inscripcionDTO.getUsuario().setIdRol(inscripcionDTO.getIdRol());

            // Crear Usuario y asignar el ID
            ResponseEntity<Usuario> usuarioResponse = usuarioFeign.crearUsuarioDto(inscripcionDTO.getUsuario());
            if (usuarioResponse.getBody() == null) {
                throw new RuntimeException("No se pudo crear el Usuario.");
            }
            // Asignar el ID del Usuario recién creado
            inscripcion.setIdUsuario(usuarioResponse.getBody().getIdUsuario());

            // Crear Persona y asignar el ID del Usuario recién creado
            inscripcionDTO.getPersona().setIdUsuario(usuarioResponse.getBody().getIdUsuario());

            ResponseEntity<Persona> personaResponse = personaFeign.crearPersonaDto(inscripcionDTO.getPersona());
            if (personaResponse.getBody() == null) {
                throw new RuntimeException("No se pudo crear la Persona.");
            }
            // Asignar el ID de la Persona recién creada
            inscripcion.setIdPersona(personaResponse.getBody().getId());

        } catch (FeignException e) {
            throw new RuntimeException("Error al comunicarse con los microservicios: " + e.getMessage(), e);
        }

        // Asignar el ID del Rol a la inscripción
        inscripcion.setIdRol(inscripcionDTO.getIdRol());

        // Guardar la inscripción en la base de datos como "Sin Rol" (ya que no se crea uno nuevo, solo se asigna)
        inscripcion.setInscripcionRol("Sin Rol");
        inscripcionesRepository.save(inscripcion);

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

        // No se edita el Rol, pero se actualizan los demás campos
        try {
            // Actualizar los datos del Usuario si es necesario
            if (inscripcionDTO.getUsuario() != null) {
                ResponseEntity<Usuario> usuarioResponse = usuarioFeign.actualizarUsuarioDto(inscripcion.getIdUsuario(), inscripcionDTO.getUsuario());
                if (usuarioResponse.getBody() != null) {
                    inscripcion.setUsuario(usuarioResponse.getBody());
                } else {
                    throw new RuntimeException("No se pudo actualizar el Usuario con ID: " + inscripcion.getIdUsuario());
                }
            }

            // Actualizar otros campos de la inscripción (excepto el Rol)
            if (inscripcionDTO.getFechaModificacionInscripcion() != null) {
                inscripcion.setFechaModificacionInscripcion(inscripcionDTO.getFechaModificacionInscripcion());
            }

            // Aquí puedes agregar cualquier otro campo que quieras permitir editar:
            if (inscripcionDTO.getInscripcionRol() != null) {
                inscripcion.setInscripcionRol(inscripcionDTO.getInscripcionRol());
            }

            // Guardar los cambios en la base de datos
            inscripcionesRepository.save(inscripcion);

        } catch (FeignException e) {
            throw new RuntimeException("Error al comunicarse con los microservicios: " + e.getMessage(), e);
        }

        return inscripcion;
    }

    @Override
    public void eliminarInscripcion(Long idInscripcion) {
        // Verificar si la inscripción existe antes de eliminar
        if (!inscripcionesRepository.existsById(idInscripcion)) {
            throw new RuntimeException("Inscripción no encontrada con ID: " + idInscripcion);
        }

        Inscripcion inscripcion = inscripcionesRepository.findById(idInscripcion).orElse(null);

        // Eliminar la inscripción pero mantener el rol
        try {
            // Eliminar el usuario relacionado si existe
            if (inscripcion != null && inscripcion.getIdUsuario() != null) {
                usuarioFeign.eliminarUsuarioDto(inscripcion.getIdUsuario());
            }

            // Limpiar otros datos de la inscripción (excepto el rol)
            inscripcion.setUsuario(null);
            inscripcion.setFechaModificacionInscripcion(null);
            // Otros campos que deban eliminarse

            // Guardar la inscripción vacía (sin datos)
            inscripcionesRepository.save(inscripcion);

            // Finalmente, eliminar la inscripción
            inscripcionesRepository.deleteById(idInscripcion);

        } catch (FeignException e) {
            throw new RuntimeException("Error al comunicarse con los microservicios: " + e.getMessage(), e);
        }
    }

    //CUD DE INSCRIPCION CON ROL
    @Override
    public Inscripcion crearInscripcionConRol(Inscripcion inscripcionDTO) {
        Inscripcion inscripcion = new Inscripcion();

        try {
            // Crear el Rol y obtener el ID
            ResponseEntity<Rol> rolResponse = rolFeign.crearRolDto(inscripcionDTO.getRol());
            if (rolResponse.getBody() == null) {
                throw new RuntimeException("No se pudo crear el Rol.");
            }
            Long idRolCreado = rolResponse.getBody().getIdRol();  // Obtener el ID del rol creado
            inscripcion.setIdRol(idRolCreado);

            // Asignar el ID del rol al Usuario antes de crear el Usuario
            inscripcionDTO.getUsuario().setIdRol(idRolCreado);

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
        inscripcion.setInscripcionRol("Con Rol");
        inscripcionesRepository.save(inscripcion);

        return inscripcion;
    }

    @Override
    public Inscripcion editarInscripcionConRol(Long id, Inscripcion inscripcionDTO) {
        // Buscar la inscripción por ID
        Optional<Inscripcion> inscripcionOpt = inscripcionesRepository.findById(id);
        if (!inscripcionOpt.isPresent()) {
            throw new RuntimeException("Inscripción no encontrada con ID: " + id);
        }

        Inscripcion inscripcion = inscripcionOpt.get();

        try {
            // Editar el Rol si se proporciona un nuevo rol
            if (inscripcionDTO.getRol() != null) {
                ResponseEntity<Rol> rolResponse = rolFeign.actualizarRolDto(inscripcion.getIdRol(), inscripcionDTO.getRol());
                if (rolResponse.getBody() != null) {
                    Long idRolActualizado = rolResponse.getBody().getIdRol();
                    inscripcion.setIdRol(idRolActualizado);

                    // Asignar el ID del rol actualizado al Usuario
                    inscripcionDTO.getUsuario().setIdRol(idRolActualizado);
                } else {
                    throw new RuntimeException("No se pudo editar el Rol.");
                }
            }

            // Editar el Usuario si se proporciona un nuevo usuario
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
        inscripcion.setInscripcionRol(inscripcionDTO.getInscripcionRol());

        // Guardar los cambios en la base de datos
        return inscripcionesRepository.save(inscripcion);
    }

    @Override
    public void eliminarInscripcionConRol(Long id) {
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

    //R INSCRIPCION GENERAL
    @Override
    public List<Inscripcion> listarInscripcion() {
        List<Inscripcion> inscripciones = inscripcionesRepository.findAll();

        inscripciones.forEach(inscripcion -> {
            // Obtener el Rol si existe
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

            // Obtener el Usuario si existe
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

            // Obtener la Persona si existe
            if (inscripcion.getIdPersona() != null) {
                try {
                    ResponseEntity<Persona> personaResponse = personaFeign.listarPersonaDtoPorId(inscripcion.getIdPersona());
                    if (personaResponse.getBody() != null) {
                        inscripcion.setPersona(personaResponse.getBody());
                    }
                } catch (FeignException e) {
                    System.out.println("Error al obtener la Persona: " + e.getMessage());
                }
            }

            // Obtener el Docente si existe
            if (inscripcion.getIdDocente() != null) {
                try {
                    ResponseEntity<Docente> docenteResponse = docenteFeign.listarDocenteDtoPorId(inscripcion.getIdDocente());
                    if (docenteResponse.getBody() != null) {
                        inscripcion.setDocente(docenteResponse.getBody());
                    }
                } catch (FeignException e) {
                    System.out.println("Error al obtener el Docente: " + e.getMessage());
                }
            }
        });

        return inscripciones;
    }

    @Override
    public Inscripcion buscarInscripcionPorId(Long id) {
        // Buscar la inscripción por idInscripcion
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
}
