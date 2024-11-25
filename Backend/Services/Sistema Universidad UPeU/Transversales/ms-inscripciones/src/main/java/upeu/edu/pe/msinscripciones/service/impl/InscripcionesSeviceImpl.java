package upeu.edu.pe.msinscripciones.service.impl;

import com.fasterxml.jackson.databind.ObjectMapper;
import feign.FeignException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import upeu.edu.pe.msinscripciones.dto.*;
import upeu.edu.pe.msinscripciones.entity.Inscripcion;
import upeu.edu.pe.msinscripciones.exception.PersonaCreationException;
import upeu.edu.pe.msinscripciones.exception.PersonaUpdateException;
import upeu.edu.pe.msinscripciones.feign.*;
import upeu.edu.pe.msinscripciones.repository.InscripcionesRepository;
import upeu.edu.pe.msinscripciones.service.InscripcionesService;

import org.springframework.http.ResponseEntity;

import java.util.List;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Service
public class InscripcionesSeviceImpl implements InscripcionesService {

    private final ObjectMapper objectMapper;

    @Autowired
    public InscripcionesSeviceImpl(ObjectMapper objectMapper) {
        this.objectMapper = objectMapper;
    }

    // Aquí iría el resto de tu código, incluyendo métodos y lógica de negocio...

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
    private AdministradorFeign administradorFeign;
    @Autowired
    private AdministrativoFeign administrativoFeign;
    @Autowired
    private EstudianteFeign estudianteFeign;
    @Autowired
    private DocenteFeign docenteFeign;

    //CUD DE INSCRIPCION
    @Override
    public Inscripcion crearInscripcion(Inscripcion inscripcionDTO, MultipartFile fotoPerfil) {
        Inscripcion inscripcion = new Inscripcion();

        try {
            System.out.println("El id del rol que se creo y se esta asignando a Usuario es este: "+inscripcionDTO.getIdRol());

            // Asignar el ID del Rol al objeto Inscripcion
            inscripcion.setIdRol(inscripcionDTO.getIdRol());
            inscripcionDTO.getUsuario().setIdRol(inscripcionDTO.getIdRol()); // Asignar el Rol al Usuario

            // 2. Crear el Usuario y obtener el ID
            ResponseEntity<?> usuarioResponse = usuarioFeign.crearUsuarioDto(inscripcionDTO.getUsuario());
            if (usuarioResponse.getBody() == null || !usuarioResponse.getStatusCode().is2xxSuccessful()) {
                throw new RuntimeException("No se pudo crear el Usuario.");
            }
            Usuario usuarioCreado = objectMapper.convertValue(usuarioResponse.getBody(), Usuario.class);
            inscripcion.setIdUsuario(usuarioCreado.getIdUsuario());

            // 3. Crear la Persona asociada al Usuario
            inscripcionDTO.getPersona().setIdUsuario(usuarioCreado.getIdUsuario());
            System.out.println("ID de Usuario asignado a Persona: " + inscripcionDTO.getPersona().getIdUsuario());

// Crear la persona con foto
            crearPersonaConFoto(inscripcionDTO.getPersona(), fotoPerfil);
            System.out.println("Persona después de crear con foto: " + inscripcionDTO.getPersona());

// Verificar que la Persona tiene un ID asignado
            if (inscripcionDTO.getPersona().getId() == null) {
                throw new RuntimeException("No se pudo asignar un ID a la persona.");
            } else {
                System.out.println("ID de Persona asignado correctamente: " + inscripcionDTO.getPersona().getId());
            }

// Imprimir la Persona y asignarla a la inscripción
            System.out.println("Persona completa: " + inscripcionDTO.getPersona());
            inscripcion.setIdPersona(inscripcionDTO.getPersona().getId());

            // 4. Crear Administrador, Administrativo, Estudiante o Docente, según corresponda
            if (inscripcionDTO.getAdministrador() != null && inscripcionDTO.getAdministrativo() == null && inscripcionDTO.getEstudiante() == null && inscripcionDTO.getDocente() == null) {
                Administrador administrador = inscripcionDTO.getAdministrador();
                administrador.setIdPersona(inscripcionDTO.getPersona().getId());
                ResponseEntity<?> administradorResponse = administradorFeign.crearAdministradorDto(administrador);
                if (administradorResponse.getBody() == null) {
                    throw new RuntimeException("No se pudo crear el Administrador.");
                }
                Administrador administradorCreado = objectMapper.convertValue(administradorResponse.getBody(), Administrador.class);
                inscripcion.setIdAdministrador(administradorCreado.getIdAdministrador());

            } else if (inscripcionDTO.getAdministrativo() != null && inscripcionDTO.getAdministrador() == null && inscripcionDTO.getEstudiante() == null && inscripcionDTO.getDocente() == null) {
                Administrativo administrativo = inscripcionDTO.getAdministrativo();
                administrativo.setIdPersona(inscripcionDTO.getPersona().getId());
                ResponseEntity<?> administrativoResponse = administrativoFeign.crearAdministrativoDto(administrativo);
                if (administrativoResponse.getBody() == null) {
                    throw new RuntimeException("No se pudo crear el Administrativo.");
                }
                Administrativo administrativoCreado = objectMapper.convertValue(administrativoResponse.getBody(), Administrativo.class);
                inscripcion.setIdAdministrativo(administrativoCreado.getIdAdministrativo());

            } else if (inscripcionDTO.getEstudiante() != null && inscripcionDTO.getAdministrador() == null && inscripcionDTO.getAdministrativo() == null && inscripcionDTO.getDocente() == null) {
                Estudiante estudiante = inscripcionDTO.getEstudiante();
                estudiante.setIdPersona(inscripcionDTO.getPersona().getId());
                ResponseEntity<?> estudianteResponse = estudianteFeign.crearEstudianteDto(estudiante);
                if (estudianteResponse.getBody() == null) {
                    throw new RuntimeException("No se pudo crear el Estudiante.");
                }
                Estudiante estudianteCreado = objectMapper.convertValue(estudianteResponse.getBody(), Estudiante.class);
                inscripcion.setIdEstudiante(estudianteCreado.getIdEstudiante());

            } else if (inscripcionDTO.getDocente() != null && inscripcionDTO.getAdministrador() == null && inscripcionDTO.getAdministrativo() == null && inscripcionDTO.getEstudiante() == null) {
                Docente docente = inscripcionDTO.getDocente();
                docente.setIdPersona(inscripcionDTO.getPersona().getId());
                ResponseEntity<?> docenteResponse = docenteFeign.crearDocenteDto(docente);
                if (docenteResponse.getBody() == null) {
                    throw new RuntimeException("No se pudo crear el Docente.");
                }
                Docente docenteCreado = objectMapper.convertValue(docenteResponse.getBody(), Docente.class);
                inscripcion.setIdDocente(docenteCreado.getIdDocente());

            } else if (inscripcionDTO.getAdministrador() == null && inscripcionDTO.getAdministrativo() == null && inscripcionDTO.getDocente() == null && inscripcionDTO.getEstudiante() == null){
                System.out.println("Se creo una persona con el nuevo Rol:");
            } else {
                throw new RuntimeException("Debe proveerse solo un Administrador, Administrativo, Estudiante o un Docente, no varios.");
            }

        } catch (FeignException e) {
            throw new RuntimeException("Error al comunicarse con los microservicios: " + e.getMessage(), e);
        }

        // Guardar la inscripción y asignar el Rol
        inscripcion.setInscripcionRol("Con Rol");
        System.out.println(inscripcion);
        inscripcionesRepository.save(inscripcion);

        return inscripcion;
    }


    @Override
    public void crearPersonaConFoto(Persona personaDTO, MultipartFile fotoPerfil) {
        try {
            // Llama al cliente de OpenFeign pasando el objeto Persona y el archivo MultipartFile
            System.out.println("Esta es la Persona que se esta enviando con Feign en el metodo crearPersonaConFoto: "+personaDTO);
            ResponseEntity<?> response = personaFeign.crearPersonaDto(personaDTO, fotoPerfil);

            if (response.getStatusCode() == HttpStatus.OK) {
                // Convertir la respuesta a un objeto Persona
                Persona personaCreada = objectMapper.convertValue(response.getBody(), Persona.class);
                // Asegúrate de tener un método setId en Persona
                personaDTO.setId(personaCreada.getId());
            } else {
                throw new PersonaCreationException("Error al crear la persona: " + response.getBody());
            }
        } catch (Exception e) {
            throw new PersonaCreationException("Error inesperado al crear la persona.", e);
        }
    }

    @Override
    public void actualizarPersonaConFoto(Long idPersona, Persona personaDTO, MultipartFile fotoPerfil) {
        try {
            // Log de depuración para ver los datos enviados
            System.out.println("Esta es la Persona que se está enviando con Feign en el método actualizarPersonaConFoto: " + personaDTO);

            // Llama al método de actualización de Persona en el cliente Feign
            ResponseEntity<?> response = personaFeign.editarPersonaDto(idPersona, personaDTO, fotoPerfil);

            // Verifica si la respuesta fue exitosa
            if (response.getStatusCode() == HttpStatus.OK) {
                // Convierte la respuesta a un objeto Persona
                Persona personaActualizada = objectMapper.convertValue(response.getBody(), Persona.class);
                // Actualiza el ID y otros datos en personaDTO si es necesario
                personaDTO.setId(personaActualizada.getId());
                personaDTO.setFotoPerfil(personaActualizada.getFotoPerfil());
                // Actualizar otros datos relevantes de personaDTO en caso de cambios
            } else {
                throw new PersonaUpdateException("Error al actualizar la persona: " + response.getBody());
            }
        } catch (Exception e) {
            // Lanza una excepción personalizada en caso de error
            throw new PersonaUpdateException("Error inesperado al actualizar la persona.", e);
        }
    }

    @Override
    public Inscripcion editarInscripcion(Long idInscripcion, Inscripcion inscripcionDTO, MultipartFile fotoPerfil) {
        final int maxRetries = 5;  // Número máximo de intentos
        final int retryInterval = 2000;  // Intervalo de espera entre intentos (en milisegundos)

        // Validar que la inscripción existe
        Inscripcion inscripcionExistente = inscripcionesRepository.findById(idInscripcion)
                .orElseThrow(() -> new RuntimeException("Inscripción no encontrada con ID: " + idInscripcion));

        try {
            // 2. Actualizar Usuario
            if (inscripcionDTO.getUsuario() != null) {
                inscripcionDTO.getUsuario().setIdRol(inscripcionExistente.getIdRol());
                ResponseEntity<?> usuarioResponse = usuarioFeign.actualizarUsuarioDto(inscripcionExistente.getIdUsuario(), inscripcionDTO.getUsuario());
                if (usuarioResponse.getBody() == null || !usuarioResponse.getStatusCode().is2xxSuccessful()) {
                    throw new RuntimeException("No se pudo actualizar el Usuario.");
                }
                Usuario usuarioActualizado = objectMapper.convertValue(usuarioResponse.getBody(), Usuario.class);
                inscripcionExistente.setIdUsuario(usuarioActualizado.getIdUsuario());
            }

            // 3. Actualizar Persona y Foto de Perfil
            if (inscripcionDTO.getPersona() != null) {
                inscripcionDTO.getPersona().setIdUsuario(inscripcionExistente.getIdUsuario());
                if (fotoPerfil != null && !fotoPerfil.isEmpty()) {
                    crearPersonaConFoto(inscripcionDTO.getPersona(), fotoPerfil);
                } else {
                    actualizarPersonaConFoto(inscripcionExistente.getIdPersona(), inscripcionDTO.getPersona(),fotoPerfil);
                }
                inscripcionExistente.setIdPersona(inscripcionDTO.getPersona().getId());
            }

            // 4. Actualizar rol específico (Administrador, Administrativo, Estudiante o Docente)
            if (inscripcionDTO.getAdministrador() != null) {
                Administrador administrador = inscripcionDTO.getAdministrador();
                administrador.setIdPersona(inscripcionExistente.getIdPersona());
                ResponseEntity<?> administradorResponse = administradorFeign.actualizarAdministradorDto(inscripcionExistente.getIdAdministrador(), administrador);
                if (administradorResponse.getBody() == null) {
                    throw new RuntimeException("No se pudo actualizar el Administrador.");
                }
            } else if (inscripcionDTO.getAdministrativo() != null) {
                Administrativo administrativo = inscripcionDTO.getAdministrativo();
                administrativo.setIdPersona(inscripcionExistente.getIdPersona());
                ResponseEntity<?> administrativoResponse = administrativoFeign.actualizarAdministrativoDto(inscripcionExistente.getIdAdministrativo(), administrativo);
                if (administrativoResponse.getBody() == null) {
                    throw new RuntimeException("No se pudo actualizar el Administrativo.");
                }
            } else if (inscripcionDTO.getEstudiante() != null) {
                Estudiante estudiante = inscripcionDTO.getEstudiante();
                estudiante.setIdPersona(inscripcionExistente.getIdPersona());
                ResponseEntity<?> estudianteResponse = estudianteFeign.actualizarEstudianteDto(inscripcionExistente.getIdEstudiante(), estudiante);
                if (estudianteResponse.getBody() == null) {
                    throw new RuntimeException("No se pudo actualizar el Estudiante.");
                }
            } else if (inscripcionDTO.getDocente() != null) {
                Docente docente = inscripcionDTO.getDocente();
                docente.setIdPersona(inscripcionExistente.getIdPersona());
                ResponseEntity<?> docenteResponse = docenteFeign.actualizarDocenteDto(inscripcionExistente.getIdDocente(), docente);
                if (docenteResponse.getBody() == null) {
                    throw new RuntimeException("No se pudo actualizar el Docente.");
                }
            }

            // 5. Actualizar los datos propios de la inscripción
            inscripcionExistente.setInscripcionRol("Con Rol");
            inscripcionesRepository.save(inscripcionExistente);

        } catch (FeignException e) {
            throw new RuntimeException("Error al comunicarse con los microservicios: " + e.getMessage(), e);
        }

        return inscripcionExistente;
    }


    @Override
    public void eliminarInscripcion(Long id) {
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

            // Eliminar Administrador, Administrativo, Estudiante o Docente según corresponda
            if (inscripcion.getIdAdministrador() != null) {
                administradorFeign.eliminarAdministradorDto(inscripcion.getIdAdministrador());
            }

            if (inscripcion.getIdAdministrativo() != null) {
                administrativoFeign.eliminarAdministrativoDto(inscripcion.getIdAdministrativo());
            }

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

            // Obtener el Administrador si existe
            if (inscripcion.getIdAdministrador() != null) {
                try {
                    ResponseEntity<Administrador> administradorResponse = administradorFeign.listarAdministradorDtoPorId(inscripcion.getIdAdministrador());
                    if (administradorResponse.getBody() != null) {
                        inscripcion.setAdministrador(administradorResponse.getBody());
                    }
                } catch (FeignException e) {
                    System.out.println("Error al obtener el Administrador: " + e.getMessage());
                }
            }

            // Obtener el Administrativo si existe
            if (inscripcion.getIdAdministrativo() != null) {
                try {
                    ResponseEntity<Administrativo> administrativoResponse = administrativoFeign.listarAdministrativoDtoPorId(inscripcion.getIdAdministrativo());
                    if (administrativoResponse.getBody() != null) {
                        inscripcion.setAdministrativo(administrativoResponse.getBody());
                    }
                } catch (FeignException e) {
                    System.out.println("Error al obtener el Administrativo: " + e.getMessage());
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

        // Obtener el Administrador si existe
        if (inscripcion.getIdAdministrador() != null) {
            try {
                ResponseEntity<Administrador> adiministradorResponse = administradorFeign.listarAdministradorDtoPorId(inscripcion.getIdAdministrador());
                if (adiministradorResponse.getBody() != null) {
                    inscripcion.setAdministrador(adiministradorResponse.getBody());
                }
            } catch (FeignException e) {
                System.out.println("Error al obtener el Administrador: " + e.getMessage());
            }
        }

        // Obtener el Administrativo si existe
        if (inscripcion.getIdAdministrativo() != null) {
            try {
                ResponseEntity<Administrativo> administrativoResponse = administrativoFeign.listarAdministrativoDtoPorId(inscripcion.getIdAdministrativo());
                if (administrativoResponse.getBody() != null) {
                    inscripcion.setAdministrativo(administrativoResponse.getBody());
                }
            } catch (FeignException e) {
                System.out.println("Error al obtener el Administrativo: " + e.getMessage());
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

    @Override
    public Inscripcion buscarInscripcionPorIdUsuario(Long idUsuario){
        Optional<Inscripcion> optionalInscripcion = inscripcionesRepository.findByIdUsuario(idUsuario);

        if (!optionalInscripcion.isPresent()) {
            throw new RuntimeException("No se pudo encontrar la inscripción con ID: " + idUsuario);
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

        // Obtener el Administrador si existe
        if (inscripcion.getIdAdministrador() != null) {
            try {
                ResponseEntity<Administrador> adiministradorResponse = administradorFeign.listarAdministradorDtoPorId(inscripcion.getIdAdministrador());
                if (adiministradorResponse.getBody() != null) {
                    inscripcion.setAdministrador(adiministradorResponse.getBody());
                }
            } catch (FeignException e) {
                System.out.println("Error al obtener el Administrador: " + e.getMessage());
            }
        }

        // Obtener el Administrativo si existe
        if (inscripcion.getIdAdministrativo() != null) {
            try {
                ResponseEntity<Administrativo> administrativoResponse = administrativoFeign.listarAdministrativoDtoPorId(inscripcion.getIdAdministrativo());
                if (administrativoResponse.getBody() != null) {
                    inscripcion.setAdministrativo(administrativoResponse.getBody());
                }
            } catch (FeignException e) {
                System.out.println("Error al obtener el Administrativo: " + e.getMessage());
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