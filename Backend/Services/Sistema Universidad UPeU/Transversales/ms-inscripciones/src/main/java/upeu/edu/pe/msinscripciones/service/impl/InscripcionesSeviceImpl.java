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
    public Inscripcion crearInscripcion(Inscripcion inscripcionDTO){
        Inscripcion inscripcion = new Inscripcion();

        try {
            // Verificar y asignar el Rol
            ResponseEntity<Rol> rolResponse = rolFeign.listarRolDtoPorId(inscripcionDTO.getIdRol());
            if (rolResponse.getBody() == null) {
                throw new RuntimeException("No se pudo encontrar el Rol con ID: " + inscripcionDTO.getIdRol());
            }
            inscripcionDTO.getUsuario().setIdRol(inscripcionDTO.getIdRol());

            // Crear Usuario
            ResponseEntity<Usuario> usuarioResponse = usuarioFeign.crearUsuarioDto(inscripcionDTO.getUsuario());
            if (usuarioResponse.getBody() == null) {
                throw new RuntimeException("No se pudo crear el Usuario.");
            }
            inscripcion.setIdUsuario(usuarioResponse.getBody().getIdUsuario());

            // Crear Persona
            inscripcionDTO.getPersona().setIdUsuario(usuarioResponse.getBody().getIdUsuario());
            ResponseEntity<Persona> personaResponse = personaFeign.crearPersonaDto(inscripcionDTO.getPersona());
            if (personaResponse.getBody() == null) {
                throw new RuntimeException("No se pudo crear la Persona.");
            }
            inscripcion.setIdPersona(personaResponse.getBody().getId());

            // Verificar si se crea un Estudiante o un Docente, no ambos
            if (inscripcionDTO.getEstudiante() != null && inscripcionDTO.getDocente() == null) {
                // Crear Estudiante y obtener el ID
                Estudiante estudiante = inscripcionDTO.getEstudiante();
                estudiante.setIdPersona(inscripcion.getIdPersona()); // Asignar el ID de la Persona al Estudiante
                ResponseEntity<Estudiante> estudianteResponse = estudianteFeign.crearEstudianteDto(estudiante);
                if (estudianteResponse.getBody() == null) {
                    throw new RuntimeException("No se pudo crear el Estudiante.");
                }
                inscripcion.setIdEstudiante(estudianteResponse.getBody().getIdEstudiante());

            } else if (inscripcionDTO.getDocente() != null && inscripcionDTO.getEstudiante() == null) {
                // Crear Docente y obtener el ID
                Docente docente = inscripcionDTO.getDocente();
                docente.setIdPersona(inscripcion.getIdPersona()); // Asignar el ID de la Persona al Docente
                ResponseEntity<Docente> docenteResponse = docenteFeign.crearDocenteDto(docente);
                if (docenteResponse.getBody() == null) {
                    throw new RuntimeException("No se pudo crear el Docente.");
                }
                inscripcion.setIdDocente(docenteResponse.getBody().getIdDocente());

            } else {
                throw new RuntimeException("Debe proveerse solo un Estudiante o un Docente, no ambos.");
            }

        } catch (FeignException e) {
            throw new RuntimeException("Error al comunicarse con los microservicios: " + e.getMessage(), e);
        }

// Asignar ID del Rol y guardar la inscripción
        inscripcion.setIdRol(inscripcionDTO.getIdRol());
        inscripcion.setInscripcionRol("Sin Rol");
        inscripcionesRepository.save(inscripcion);

        return inscripcion;
    }

    @Override
    public Inscripcion editarInscripcion(Long id, Inscripcion inscripcionDTO) {
        Optional<Inscripcion> optionalInscripcion = inscripcionesRepository.findById(id);

        if (!optionalInscripcion.isPresent()) {
            throw new RuntimeException("No se pudo encontrar la inscripción con ID: " + id);
        }

        Inscripcion inscripcion = optionalInscripcion.get();

        try {
            // Verificar y asignar el Rol
            ResponseEntity<Rol> rolResponse = rolFeign.listarRolDtoPorId(inscripcionDTO.getIdRol());
            if (rolResponse.getBody() == null) {
                throw new RuntimeException("No se pudo encontrar el Rol con ID: " + inscripcionDTO.getIdRol());
            }
            inscripcion.setIdRol(inscripcionDTO.getIdRol());
            inscripcionDTO.getUsuario().setIdRol(inscripcionDTO.getIdRol());

            // Actualizar Usuario
            inscripcionDTO.getUsuario().setIdUsuario(inscripcion.getIdUsuario());
            ResponseEntity<Usuario> usuarioResponse = usuarioFeign.actualizarUsuarioDto(inscripcion.getIdUsuario(), inscripcionDTO.getUsuario());
            if (usuarioResponse.getBody() == null) {
                throw new RuntimeException("No se pudo actualizar el Usuario.");
            }

            // Actualizar Persona
            inscripcionDTO.getPersona().setIdUsuario(inscripcion.getIdUsuario());
            ResponseEntity<Persona> personaResponse = personaFeign.actualizarPersonaDto(inscripcion.getIdPersona(), inscripcionDTO.getPersona());
            if (personaResponse.getBody() == null) {
                throw new RuntimeException("No se pudo actualizar la Persona.");
            }

            // Verificar si se actualiza un Estudiante o un Docente, no ambos
            if (inscripcionDTO.getEstudiante() != null && inscripcionDTO.getDocente() == null) {
                // Actualizar Estudiante
                Estudiante estudiante = inscripcionDTO.getEstudiante();
                estudiante.setIdPersona(inscripcion.getIdPersona());
                ResponseEntity<Estudiante> estudianteResponse = estudianteFeign.actualizarEstudianteDto(inscripcion.getIdEstudiante(), estudiante);
                if (estudianteResponse.getBody() == null) {
                    throw new RuntimeException("No se pudo actualizar el Estudiante.");
                }
                inscripcion.setIdEstudiante(estudianteResponse.getBody().getIdEstudiante());

                // Limpiar ID de Docente si existía
                inscripcion.setIdDocente(null);

            } else if (inscripcionDTO.getDocente() != null && inscripcionDTO.getEstudiante() == null) {
                // Actualizar Docente
                Docente docente = inscripcionDTO.getDocente();
                docente.setIdPersona(inscripcion.getIdPersona());
                ResponseEntity<Docente> docenteResponse = docenteFeign.actualizarDocenteDto(inscripcion.getIdDocente(), docente);
                if (docenteResponse.getBody() == null) {
                    throw new RuntimeException("No se pudo actualizar el Docente.");
                }
                inscripcion.setIdDocente(docenteResponse.getBody().getIdDocente());

                // Limpiar ID de Estudiante si existía
                inscripcion.setIdEstudiante(null);
            } else {
                throw new RuntimeException("Debe proveerse solo un Estudiante o un Docente, no ambos.");
            }

        } catch (FeignException e) {
            throw new RuntimeException("Error al comunicarse con los microservicios: " + e.getMessage(), e);
        }

        inscripcionesRepository.save(inscripcion);
        return inscripcion;
    }

    @Override
    public void eliminarInscripcion(Long idInscripcion) {
        Optional<Inscripcion> optionalInscripcion = inscripcionesRepository.findById(idInscripcion);

        if (!optionalInscripcion.isPresent()) {
            throw new RuntimeException("No se pudo encontrar la inscripción con ID: " + idInscripcion);
        }

        Inscripcion inscripcion = optionalInscripcion.get();

        try {
            // Eliminar Estudiante si existe
            if (inscripcion.getIdEstudiante() != null) {
                ResponseEntity<String> estudianteResponse = estudianteFeign.eliminarEstudianteDto(inscripcion.getIdEstudiante());
                if (estudianteResponse.getStatusCode().isError()) {
                    throw new RuntimeException("No se pudo eliminar el Estudiante con ID: " + inscripcion.getIdEstudiante());
                }
            }

            // Eliminar Docente si existe
            if (inscripcion.getIdDocente() != null) {
                ResponseEntity<String> docenteResponse = docenteFeign.eliminarDocenteDto(inscripcion.getIdDocente());
                if (docenteResponse.getStatusCode().isError()) {
                    throw new RuntimeException("No se pudo eliminar el Docente con ID: " + inscripcion.getIdDocente());
                }
            }

            // Eliminar Persona
            ResponseEntity<String> personaResponse = personaFeign.eliminarPersonaDto(inscripcion.getIdPersona());
            if (personaResponse.getStatusCode().isError()) {
                throw new RuntimeException("No se pudo eliminar la Persona con ID: " + inscripcion.getIdPersona());
            }

            // Eliminar Usuario
            ResponseEntity<String> usuarioResponse = usuarioFeign.eliminarUsuarioDto(inscripcion.getIdUsuario());
            if (usuarioResponse.getStatusCode().isError()) {
                throw new RuntimeException("No se pudo eliminar el Usuario con ID: " + inscripcion.getIdUsuario());
            }

            // Finalmente, eliminar la inscripción de la base de datos
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
            Long idRolCreado = rolResponse.getBody().getIdRol();  // Obtener el ID del Rol creado
            inscripcion.setIdRol(idRolCreado);
            inscripcionDTO.getUsuario().setIdRol(idRolCreado);  // Asignar el Rol al Usuario

            // Crear Usuario
            ResponseEntity<Usuario> usuarioResponse = usuarioFeign.crearUsuarioDto(inscripcionDTO.getUsuario());
            if (usuarioResponse.getBody() == null) {
                throw new RuntimeException("No se pudo crear el Usuario.");
            }
            inscripcion.setIdUsuario(usuarioResponse.getBody().getIdUsuario());

            // Crear Persona
            inscripcionDTO.getPersona().setIdUsuario(usuarioResponse.getBody().getIdUsuario());
            ResponseEntity<Persona> personaResponse = personaFeign.crearPersonaDto(inscripcionDTO.getPersona());
            if (personaResponse.getBody() == null) {
                throw new RuntimeException("No se pudo crear la Persona.");
            }
            inscripcion.setIdPersona(personaResponse.getBody().getId());

            // Verificar si se crea un Estudiante o un Docente, no ambos
            if (inscripcionDTO.getEstudiante() != null && inscripcionDTO.getDocente() == null) {
                // Crear Estudiante y obtener el ID
                Estudiante estudiante = inscripcionDTO.getEstudiante();
                estudiante.setIdPersona(inscripcion.getIdPersona());  // Asignar el ID de la Persona al Estudiante
                ResponseEntity<Estudiante> estudianteResponse = estudianteFeign.crearEstudianteDto(estudiante);
                if (estudianteResponse.getBody() == null) {
                    throw new RuntimeException("No se pudo crear el Estudiante.");
                }
                inscripcion.setIdEstudiante(estudianteResponse.getBody().getIdEstudiante());

            } else if (inscripcionDTO.getDocente() != null && inscripcionDTO.getEstudiante() == null) {
                // Crear Docente y obtener el ID
                Docente docente = inscripcionDTO.getDocente();
                docente.setIdPersona(inscripcion.getIdPersona());  // Asignar el ID de la Persona al Docente
                ResponseEntity<Docente> docenteResponse = docenteFeign.crearDocenteDto(docente);
                if (docenteResponse.getBody() == null) {
                    throw new RuntimeException("No se pudo crear el Docente.");
                }
                inscripcion.setIdDocente(docenteResponse.getBody().getIdDocente());

            } else {
                throw new RuntimeException("Debe proveerse solo un Estudiante o un Docente, no ambos.");
            }

        } catch (FeignException e) {
            throw new RuntimeException("Error al comunicarse con los microservicios: " + e.getMessage(), e);
        }

        // Guardar la inscripción y asignar el Rol
        inscripcion.setInscripcionRol("Con Rol");
        inscripcionesRepository.save(inscripcion);

        return inscripcion;
    }

    @Override
    public Inscripcion editarInscripcionConRol(Long id, Inscripcion inscripcionDTO) {
        // Buscar la inscripción existente por ID
        Optional<Inscripcion> inscripcionExistente = inscripcionesRepository.findById(id);
        if (!inscripcionExistente.isPresent()) {
            throw new RuntimeException("Inscripción no encontrada con el ID: " + id);
        }
        Inscripcion inscripcion = inscripcionExistente.get();

        try {
            // Actualizar el Rol
            if (inscripcionDTO.getRol() != null) {
                ResponseEntity<Rol> rolResponse = rolFeign.actualizarRolDto(inscripcion.getIdRol(), inscripcionDTO.getRol());
                if (rolResponse.getBody() == null) {
                    throw new RuntimeException("No se pudo actualizar el Rol.");
                }
                inscripcion.setIdRol(rolResponse.getBody().getIdRol());
            }

            // Actualizar el Usuario
            if (inscripcionDTO.getUsuario() != null) {
                inscripcionDTO.getUsuario().setIdRol(inscripcion.getIdRol());  // Asignar el ID del rol actualizado
                ResponseEntity<Usuario> usuarioResponse = usuarioFeign.actualizarUsuarioDto(inscripcion.getIdUsuario(), inscripcionDTO.getUsuario());
                if (usuarioResponse.getBody() == null) {
                    throw new RuntimeException("No se pudo actualizar el Usuario.");
                }
                inscripcion.setIdUsuario(usuarioResponse.getBody().getIdUsuario());
            }

            // Actualizar la Persona
            if (inscripcionDTO.getPersona() != null) {
                inscripcionDTO.getPersona().setIdUsuario(inscripcion.getIdUsuario()); // Asegurar que la Persona está asociada al Usuario actualizado
                ResponseEntity<Persona> personaResponse = personaFeign.actualizarPersonaDto(inscripcion.getIdPersona(), inscripcionDTO.getPersona());
                if (personaResponse.getBody() == null) {
                    throw new RuntimeException("No se pudo actualizar la Persona.");
                }
                inscripcion.setIdPersona(personaResponse.getBody().getId());
            }

            // Actualizar Estudiante o Docente según corresponda
            if (inscripcionDTO.getEstudiante() != null && inscripcionDTO.getDocente() == null) {
                Estudiante estudiante = inscripcionDTO.getEstudiante();
                estudiante.setIdPersona(inscripcion.getIdPersona());  // Asociar al ID de la Persona actualizada
                ResponseEntity<Estudiante> estudianteResponse = estudianteFeign.actualizarEstudianteDto(inscripcion.getIdEstudiante(), estudiante);
                if (estudianteResponse.getBody() == null) {
                    throw new RuntimeException("No se pudo actualizar el Estudiante.");
                }
                inscripcion.setIdEstudiante(estudianteResponse.getBody().getIdEstudiante());
                inscripcion.setIdDocente(null);  // Asegurarse de que no hay un docente asociado

            } else if (inscripcionDTO.getDocente() != null && inscripcionDTO.getEstudiante() == null) {
                Docente docente = inscripcionDTO.getDocente();
                docente.setIdPersona(inscripcion.getIdPersona());  // Asociar al ID de la Persona actualizada
                ResponseEntity<Docente> docenteResponse = docenteFeign.actualizarDocenteDto(inscripcion.getIdDocente(), docente);
                if (docenteResponse.getBody() == null) {
                    throw new RuntimeException("No se pudo actualizar el Docente.");
                }
                inscripcion.setIdDocente(docenteResponse.getBody().getIdDocente());
                inscripcion.setIdEstudiante(null);  // Asegurarse de que no hay un estudiante asociado
            } else {
                throw new RuntimeException("Debe actualizarse solo un Estudiante o un Docente, no ambos.");
            }

            // Guardar la inscripción actualizada
            inscripcionesRepository.save(inscripcion);
        } catch (FeignException e) {
            throw new RuntimeException("Error al comunicarse con los microservicios: " + e.getMessage(), e);
        }

        return inscripcion;
    }

    @Override
    public void eliminarInscripcionConRol(Long id) {
        // Buscar la inscripción existente por ID
        Optional<Inscripcion> inscripcionExistente = inscripcionesRepository.findById(id);
        if (!inscripcionExistente.isPresent()) {
            throw new RuntimeException("Inscripción no encontrada con el ID: " + id);
        }
        Inscripcion inscripcion = inscripcionExistente.get();

        try {
            // Eliminar Usuario
            if (inscripcion.getIdUsuario() != null) {
                usuarioFeign.eliminarUsuarioDto(inscripcion.getIdUsuario());
            }

            // Eliminar Persona
            if (inscripcion.getIdPersona() != null) {
                personaFeign.eliminarPersonaDto(inscripcion.getIdPersona());
            }

            // Eliminar Estudiante o Docente según corresponda
            if (inscripcion.getIdEstudiante() != null) {
                estudianteFeign.eliminarEstudianteDto(inscripcion.getIdEstudiante());
            }

            if (inscripcion.getIdDocente() != null) {
                docenteFeign.eliminarDocenteDto(inscripcion.getIdDocente());
            }

            // Eliminar Rol
            if (inscripcion.getIdRol() != null) {
                rolFeign.eliminarRolDto(inscripcion.getIdRol());
            }

            // Finalmente, eliminar la inscripción
            inscripcionesRepository.deleteById(id);
        } catch (FeignException e) {
            throw new RuntimeException("Error al comunicarse con los microservicios: " + e.getMessage(), e);
        }
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

            // Obtener el Estudiante si existe
            if (inscripcion.getIdEstudiante() != null) {
                try {
                    ResponseEntity<Estudiante> estudianteResponse = estudianteFeign.listarEstudianteDtoPorId(inscripcion.getIdEstudiante());
                    if (estudianteResponse.getBody() != null) {
                        inscripcion.setEstudiante(estudianteResponse.getBody());
                    }
                } catch (FeignException e) {
                    System.out.println("Error al obtener el Estudiante: " + e.getMessage());
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
        Optional<Inscripcion> optionalInscripcion = inscripcionesRepository.findById(id);

        if (!optionalInscripcion.isPresent()) {
            throw new RuntimeException("No se pudo encontrar la inscripción con ID: " + id);
        }

        Inscripcion inscripcion = optionalInscripcion.get();

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

        // Obtener el Estudiante si existe
        if (inscripcion.getIdEstudiante() != null) {
            try {
                ResponseEntity<Estudiante> estudianteResponse = estudianteFeign.listarEstudianteDtoPorId(inscripcion.getIdEstudiante());
                if (estudianteResponse.getBody() != null) {
                    inscripcion.setEstudiante(estudianteResponse.getBody());
                }
            } catch (FeignException e) {
                System.out.println("Error al obtener el Estudiante: " + e.getMessage());
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

        return inscripcion;
    }
}
