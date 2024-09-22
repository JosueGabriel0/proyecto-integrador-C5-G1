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
    /*
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
            inscripcion.setUsuarioId(usuarioResponse.getBody().getIdUsuario());

            // Crear Persona y asignar el ID
            ResponseEntity<Persona> personaResponse = personaFeign.crearPersonaDto(inscripcionDTO.getPersona());
            if (personaResponse.getBody() == null) {
                throw new RuntimeException("No se pudo crear la Persona.");
            }
            inscripcion.setPersonaId(personaResponse.getBody().getId());

        } catch (FeignException e) {
            throw new RuntimeException("Error al comunicarse con los microservicios: " + e.getMessage(), e);
        }

// Aquí podrías guardar la inscripción en la base de datos si fuese necesario
        inscripcionesRepository.save(inscripcion);

        return inscripcion;
    }
    */
    @Override
    public Inscripcion crearInscripcionConRol(Inscripcion inscripcionDTO) {
        Inscripcion inscripcion = new Inscripcion();

        try {
            // Crear Rol y asignar el ID del rol
            ResponseEntity<Rol> rolResponse = rolFeign.crearRolDto(inscripcionDTO.getRol());
            if (rolResponse.getBody() == null) {
                throw new RuntimeException("No se pudo crear el Rol.");
            }
            inscripcion.setIdRol(rolResponse.getBody().getId());

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
            // Intentamos obtener el Rol
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

            // Intentamos obtener el Usuario
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
/*
    @Override
    public Inscripcion buscarInscripcionPorId(Long id) {
        Optional<Inscripcion> inscripcionOpt = inscripcionesRepository.findById(id);
        if (!inscripcionOpt.isPresent()) {
            throw new ResourceNotFoundException("Inscripción con ID " + id + " no encontrada");
        }

        Inscripcion inscripcion = inscripcionOpt.get();

        // Intentamos obtener el Rol
        if (inscripcion.getRolId() != null) {
            try {
                ResponseEntity<Rol> rolResponse = rolFeign.listarRolDtoPorId(inscripcion.getRolId());
                if (rolResponse.getBody() != null) {
                    inscripcion.setRol(rolResponse.getBody());
                }
            } catch (FeignException e) {
                System.out.println("Error al obtener el Rol: " + e.getMessage());
            }
        }

        // Intentamos obtener el Usuario
        if (inscripcion.getUsuarioId() != null) {
            try {
                ResponseEntity<Usuario> usuarioResponse = usuarioFeign.listarUsuarioDtoPorId(inscripcion.getUsuarioId());
                if (usuarioResponse.getBody() != null) {
                    inscripcion.setUsuario(usuarioResponse.getBody());
                }
            } catch (FeignException e) {
                System.out.println("Error al obtener el Usuario: " + e.getMessage());
            }
        }

        // Intentamos obtener la Persona
        if (inscripcion.getPersonaId() != null) {
            try {
                ResponseEntity<Persona> personaResponse = personaFeign.listarPersonaDtoPorId(inscripcion.getPersonaId());
                if (personaResponse.getBody() != null) {
                    inscripcion.setPersona(personaResponse.getBody());
                }
            } catch (FeignException e) {
                System.out.println("Error al obtener la Persona: " + e.getMessage());
            }
        }

        // Intentamos obtener el Estudiante
        if (inscripcion.getEstudiante() != null) {
            try {
                ResponseEntity<Estudiante> estudianteResponse = estudianteFeign.listarEstudianteDtoPorId(inscripcion.getInscripcionTipoId());
                if (estudianteResponse.getBody() != null) {
                    inscripcion.setEstudiante(estudianteResponse.getBody());
                }
            } catch (FeignException e) {
                System.out.println("Error al obtener el Estudiante: " + e.getMessage());
            }
        }

        // Intentamos obtener el Docente
        if (inscripcion.getDocente() != null) {
            try {
                ResponseEntity<Docente> docenteResponse = docenteFeign.listarDocenteDtoPorId(inscripcion.getInscripcionTipoId());
                if (docenteResponse.getBody() != null) {
                    inscripcion.setDocente(docenteResponse.getBody());
                }
            } catch (FeignException e) {
                System.out.println("Error al obtener el Docente: " + e.getMessage());
            }
        }

        return inscripcion;
    }

    @Override
    public Inscripcion editarInscripcion(Long id, Inscripcion nuevaInscripcionDTO) {
        Optional<Inscripcion> inscripcionOpt = inscripcionesRepository.findById(id);
        if (!inscripcionOpt.isPresent()) {
            throw new ResourceNotFoundException("Inscripción con ID " + id + " no encontrada");
        }

        Inscripcion inscripcionExistente = inscripcionOpt.get();

        // Actualizar el rolId si es diferente
        if (nuevaInscripcionDTO.getRolId() != null) {
            inscripcionExistente.setRolId(nuevaInscripcionDTO.getRolId());
        }

        // Actualizar el usuarioId si es diferente
        if (nuevaInscripcionDTO.getUsuarioId() != null) {
            inscripcionExistente.setUsuarioId(nuevaInscripcionDTO.getUsuarioId());
        }

        // Actualizar el personaId si es diferente
        if (nuevaInscripcionDTO.getPersonaId() != null) {
            inscripcionExistente.setPersonaId(nuevaInscripcionDTO.getPersonaId());
        }

        // Actualizar el estudianteId si es diferente
        if (nuevaInscripcionDTO.getInscripcionTipoId() != null) {
            inscripcionExistente.setInscripcionTipoId(nuevaInscripcionDTO.getInscripcionTipoId());
        }

        // Actualizar el docenteId si es diferente
        if (nuevaInscripcionDTO.getInscripcionTipoId() != null) {
            inscripcionExistente.setInscripcionTipoId(nuevaInscripcionDTO.getInscripcionTipoId());
        }

        // Actualizar las fechas de modificación
        inscripcionExistente.setFechaModificacionInscripcion(LocalDateTime.now());

        return inscripcionesRepository.save(inscripcionExistente);
    }

    @Override
    public Inscripcion eliminarInscripcion(Long id) {
        Optional<Inscripcion> inscripcionOpt = inscripcionesRepository.findById(id);
        if (!inscripcionOpt.isPresent()) {
            throw new ResourceNotFoundException("Inscripción con ID " + id + " no encontrada");
        }

        Inscripcion inscripcionExistente = inscripcionOpt.get();

        // Eliminar datos de Usuario
        if (inscripcionExistente.getUsuarioId() != null) {
            try {
                usuarioFeign.eliminarUsuarioDto(inscripcionExistente.getUsuarioId());
                inscripcionExistente.setUsuarioId(null);
            } catch (FeignException e) {
                throw new RuntimeException("Error al eliminar el Usuario con ID " + inscripcionExistente.getUsuarioId(), e);
            }
        }

        // Eliminar datos de Persona
        if (inscripcionExistente.getPersonaId() != null) {
            try {
                personaFeign.eliminarPersonaDto(inscripcionExistente.getPersonaId());
                inscripcionExistente.setPersonaId(null);
            } catch (FeignException e) {
                throw new RuntimeException("Error al eliminar la Persona con ID " + inscripcionExistente.getPersonaId(), e);
            }
        }

        // Eliminar datos de Estudiante
        if (inscripcionExistente.getInscripcionTipoId() != null) {
            try {
                estudianteFeign.eliminarEstudianteDto(inscripcionExistente.getInscripcionTipoId());
                inscripcionExistente.setInscripcionTipoId(null);
            } catch (FeignException e) {
                throw new RuntimeException("Error al eliminar el Estudiante con ID " + inscripcionExistente.getInscripcionTipoId(), e);
            }
        }

        // Eliminar datos de Docente
        if (inscripcionExistente.getInscripcionTipoId() != null) {
            try {
                docenteFeign.eliminarDocenteDto(inscripcionExistente.getInscripcionTipoId());
                inscripcionExistente.setInscripcionTipoId(null);
            } catch (FeignException e) {
                throw new RuntimeException("Error al eliminar el Docente con ID " + inscripcionExistente.getInscripcionTipoId(), e);
            }
        }

        // Guardar cambios sin modificar el rol
        return inscripcionesRepository.save(inscripcionExistente);
    }
*/
}
