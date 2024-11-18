package upeu.edu.pe.msinscripciones.service.impl;

import com.fasterxml.jackson.databind.ObjectMapper;
import feign.FeignException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.client.HttpStatusCodeException;
import org.springframework.web.multipart.MultipartFile;
import upeu.edu.pe.msinscripciones.dto.*;
import upeu.edu.pe.msinscripciones.entity.Inscripcion;
import upeu.edu.pe.msinscripciones.exception.PersonaCreationException;
import upeu.edu.pe.msinscripciones.exception.PersonaUpdateException;
import upeu.edu.pe.msinscripciones.feign.*;
import upeu.edu.pe.msinscripciones.repository.InscripcionesRepository;
import upeu.edu.pe.msinscripciones.service.InscripcionesService;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.client.RestTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import feign.FeignException; // Asegúrate de importar esta clase si la estás utilizando

import java.io.IOException;
import java.util.List;
import java.util.Optional;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import upeu.edu.pe.msinscripciones.service.TokenService;

@Service
public class InscripcionesSeviceImpl implements InscripcionesService {

    private final RestTemplate restTemplate;
    private final ObjectMapper objectMapper;
    private static final String BASE_URL_PERSONA = "http://localhost:9090/persona";

    private static final Logger logger = LoggerFactory.getLogger(InscripcionesSeviceImpl.class);


    @Autowired
    public InscripcionesSeviceImpl(RestTemplate restTemplate, ObjectMapper objectMapper) {
        this.restTemplate = restTemplate;
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
            // Verificar y asignar el Rol
            ResponseEntity<Rol> rolResponse = rolFeign.listarRolDtoPorId(inscripcionDTO.getIdRol());
            if (rolResponse.getBody() == null) {
                throw new RuntimeException("No se pudo encontrar el Rol con ID: " + inscripcionDTO.getIdRol());
            }

            // Crear Usuario
            inscripcionDTO.getUsuario().setIdRol(inscripcionDTO.getIdRol());
            ResponseEntity<?> usuarioResponse = usuarioFeign.crearUsuarioDto(inscripcionDTO.getUsuario());

            if (usuarioResponse.getBody() == null || !usuarioResponse.getStatusCode().is2xxSuccessful()) {
                throw new RuntimeException("No se pudo crear el Usuario.");
            }

            try {
                // Usar el ObjectMapper para convertir la respuesta a un objeto Usuario
                Usuario usuarioCreado = objectMapper.convertValue(usuarioResponse.getBody(), Usuario.class);
                inscripcion.setIdUsuario(usuarioCreado.getIdUsuario());
            } catch (IllegalArgumentException e) {
                throw new RuntimeException("Error al convertir la respuesta a Usuario", e);
            }
            Usuario usuarioCreado = objectMapper.convertValue(usuarioResponse.getBody(), Usuario.class);

//          // Crear Persona
            inscripcionDTO.getPersona().setIdUsuario(usuarioCreado.getIdUsuario());
            // Establecer otros atributos de personaDTO según lo necesites

            crearPersonaConFoto(inscripcionDTO.getPersona(), fotoPerfil); // Crear la persona con foto
            inscripcion.setIdPersona(inscripcionDTO.getPersona().getId()); // Asegúrate de que `personaDTO` tenga el ID correcto después de crearla

            // Verificar si se crea un Administrador, Administrativo, Estudiante o un Docente, no varios a la vez
            if(inscripcionDTO.getAdministrador() != null && inscripcionDTO.getAdministrativo() == null && inscripcionDTO.getEstudiante() == null && inscripcionDTO.getDocente() == null){
                // Crear Administrador y obtener el ID
                Administrador administrador = inscripcionDTO.getAdministrador();
                administrador.setIdPersona(inscripcionDTO.getPersona().getId());
                ResponseEntity<?> administradorResponse = administradorFeign.crearAdministradorDto(administrador);
                if (administradorResponse.getBody() == null) {
                    throw new RuntimeException("No se pudo crear el Administrador.");
                }
                try {
                    Administrador administradorCreado = objectMapper.convertValue(administradorResponse.getBody(), Administrador.class);
                    inscripcion.setIdAdministrador(administradorCreado.getIdAdministrador());
                }catch (IllegalArgumentException e){
                    throw new RuntimeException("Error al convertir la respuesta a Administrador", e);
                }


            } else if(inscripcionDTO.getAdministrativo() != null && inscripcionDTO.getAdministrador() == null && inscripcionDTO.getEstudiante() == null && inscripcionDTO.getDocente() == null){
                // Crear Administrativo y obtener el ID
                Administrativo administrativo = inscripcionDTO.getAdministrativo();
                administrativo.setIdPersona(inscripcionDTO.getPersona().getId());
                ResponseEntity<?> administrativoResponse = administrativoFeign.crearAdministrativoDto(administrativo);
                if (administrativoResponse.getBody() == null) {
                    throw new RuntimeException("No se pudo crear el Administrativo.");
                }
                try {
                    Administrativo administrativoCreado = objectMapper.convertValue(administrativoResponse.getBody(), Administrativo.class);
                    inscripcion.setIdAdministrativo(administrativoCreado.getIdAdministrativo());
                }catch (IllegalArgumentException e){
                    throw new RuntimeException("Error al convertir la respuesta a Administrativo", e);
                }

            }else if (inscripcionDTO.getEstudiante() != null && inscripcionDTO.getAdministrador() == null && inscripcionDTO.getAdministrativo() == null && inscripcionDTO.getDocente() == null) {
                // Crear Estudiante y obtener el ID
                Estudiante estudiante = inscripcionDTO.getEstudiante();
                estudiante.setIdPersona(inscripcionDTO.getPersona().getId());
                ResponseEntity<?> estudianteResponse = estudianteFeign.crearEstudianteDto(estudiante);
                if (estudianteResponse.getBody() == null) {
                    throw new RuntimeException("No se pudo crear el Estudiante.");
                }
                try {
                    Estudiante estudianteCreado = objectMapper.convertValue(estudianteResponse.getBody(), Estudiante.class);
                    inscripcion.setIdEstudiante(estudianteCreado.getIdEstudiante());
                }catch (IllegalArgumentException e){
                    throw new RuntimeException("Error al convertir la respuesta a Estudiante", e);
                }

            } else if (inscripcionDTO.getDocente() != null && inscripcionDTO.getAdministrador() == null && inscripcionDTO.getAdministrativo() == null && inscripcionDTO.getEstudiante() == null) {
                // Crear Docente y obtener el ID
                Docente docente = inscripcionDTO.getDocente();
                docente.setIdPersona(inscripcionDTO.getPersona().getId());
                ResponseEntity<?> docenteResponse = docenteFeign.crearDocenteDto(docente);
                if (docenteResponse.getBody() == null) {
                    throw new RuntimeException("No se pudo crear el Docente.");
                }
                try {
                    Docente docenteCreado = objectMapper.convertValue(docenteResponse.getBody(), Docente.class);
                    inscripcion.setIdDocente(docenteCreado.getIdDocente());
                }catch (IllegalArgumentException e){
                    throw new RuntimeException("Error al convertir la respuesta a Docente", e);
                }

            } else {
                throw new RuntimeException("Debe proveerse solo un Administrador, Administrativo, Estudiante o un Docente, no varios.");
            }

        } catch (FeignException e) {
            throw new RuntimeException("Error al comunicarse con los microservicios: " + e.getMessage(), e);
        }

        // Guardar la inscripción y asignar el Rol
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
            /*ResponseEntity<Persona> personaResponse = personaFeign.editarPersonaDto(inscripcion.getIdPersona(), inscripcionDTO.getPersona());
            if (personaResponse.getBody() == null) {
                throw new RuntimeException("No se pudo actualizar la Persona.");
            }*/

            // Verificar si se actualiza un Administrador, Administrativo, Estudiante o un Docente, no varios a la vez
            if(inscripcionDTO.getAdministrador() != null && inscripcionDTO.getAdministrativo() == null && inscripcionDTO.getEstudiante() == null && inscripcionDTO.getDocente() == null){
                // Actualizar Administrador
                Administrador administrador = inscripcionDTO.getAdministrador();
                administrador.setIdPersona(inscripcion.getIdPersona());
                ResponseEntity<Administrador> administradorResponse = administradorFeign.actualizarAdministradorDto(inscripcion.getIdAdministrador(), administrador);
                if (administradorResponse.getBody() == null) {
                    throw new RuntimeException("No se pudo actualizar el Administrador.");
                }
                inscripcion.setIdAdministrador(administradorResponse.getBody().getIdAdministrador());

                // Limpiar ID de Administrativo si existía
                inscripcion.setIdAdministrativo(null);
                // Limpiar ID de Estudiante si existía
                inscripcion.setIdEstudiante(null);
                // Limpiar ID de Docente si existía
                inscripcion.setIdDocente(null);

            } else if(inscripcionDTO.getAdministrativo() != null && inscripcionDTO.getAdministrador() == null && inscripcionDTO.getEstudiante() == null && inscripcionDTO.getDocente() == null){
                // Actualizar Administrativo
                Administrativo administrativo = inscripcionDTO.getAdministrativo();
                administrativo.setIdPersona(inscripcion.getIdPersona());
                ResponseEntity<Administrativo> administrativoResponse = administrativoFeign.actualizarAdministrativoDto(inscripcion.getIdAdministrativo(), administrativo);
                if (administrativoResponse.getBody() == null) {
                    throw new RuntimeException("No se pudo actualizar el Administrativo.");
                }
                inscripcion.setIdAdministrativo(administrativoResponse.getBody().getIdAdministrativo());

                // Limpiar ID de Administrador si existía
                inscripcion.setIdAdministrador(null);
                // Limpiar ID de Estudiante si existía
                inscripcion.setIdEstudiante(null);
                // Limpiar ID de Docente si existía
                inscripcion.setIdDocente(null);

            }else if (inscripcionDTO.getEstudiante() != null && inscripcionDTO.getDocente() == null) {
                // Actualizar Estudiante
                Estudiante estudiante = inscripcionDTO.getEstudiante();
                estudiante.setIdPersona(inscripcion.getIdPersona());
                ResponseEntity<Estudiante> estudianteResponse = estudianteFeign.actualizarEstudianteDto(inscripcion.getIdEstudiante(), estudiante);
                if (estudianteResponse.getBody() == null) {
                    throw new RuntimeException("No se pudo actualizar el Estudiante.");
                }
                inscripcion.setIdEstudiante(estudianteResponse.getBody().getIdEstudiante());

                // Limpiar ID de Administrador si existía
                inscripcion.setIdAdministrador(null);
                // Limpiar ID de Administrativo si existía
                inscripcion.setIdAdministrativo(null);
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

                // Limpiar ID de Administrador si existía
                inscripcion.setIdAdministrador(null);
                // Limpiar ID de Administrativo si existía
                inscripcion.setIdAdministrativo(null);
                // Limpiar ID de Estudiante si existía
                inscripcion.setIdEstudiante(null);
            } else {
                throw new RuntimeException("Debe proveerse solo un Administrador, Adminitrativo, Estudiante o un Docente, no varios a la vez.");
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
            // Eliminar Administrador si existe
            if (inscripcion.getIdAdministrador() != null) {
                ResponseEntity<String> administradorResponse = administradorFeign.eliminarAdministradorDto(inscripcion.getIdAdministrador());
                if (administradorResponse.getStatusCode().isError()) {
                    throw new RuntimeException("No se pudo eliminar el Administrador con ID: " + inscripcion.getIdAdministrador());
                }
            }

            // Eliminar Administrativo si existe
            if (inscripcion.getIdAdministrativo() != null) {
                ResponseEntity<String> administrativoResponse = administrativoFeign.eliminarAdministrativoDto(inscripcion.getIdAdministrativo());
                if (administrativoResponse.getStatusCode().isError()) {
                    throw new RuntimeException("No se pudo eliminar el Administrativo con ID: " + inscripcion.getIdAdministrativo());
                }
            }

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
    public Inscripcion crearInscripcionConRol(Inscripcion inscripcionDTO, MultipartFile fotoPerfil) {
        Inscripcion inscripcion = new Inscripcion();
        final int maxRetries = 5;  // Número máximo de intentos
        final int retryInterval = 2000;  // Intervalo de espera entre intentos (en milisegundos)

        try {
            // 1. Intentar crear el Rol y obtener el ID con un ciclo de espera
            Long idRolCreado = null;
            for (int i = 0; i < maxRetries; i++) {
                ResponseEntity<Rol> rolResponse = rolFeign.crearRolDto(inscripcionDTO.getRol());
                if (rolResponse.getBody() != null && rolResponse.getBody().getIdRol() != null) {
                    idRolCreado = rolResponse.getBody().getIdRol();
                    break;  // Si el rol se creó correctamente, salir del ciclo
                }
                try {
                    Thread.sleep(retryInterval);  // Esperar antes de intentar de nuevo
                } catch (InterruptedException e) {
                    // Manejar la interrupción si ocurre
                    Thread.currentThread().interrupt();  // Restablecer el estado de interrupción
                    throw new RuntimeException("El hilo fue interrumpido mientras esperaba para crear el Rol.", e);
                }
            }

            if (idRolCreado == null) {
                throw new RuntimeException("No se pudo crear el Rol después de varios intentos.");
            }

            System.out.println("El id del rol que se creo y se esta asignando a Usuario es este: "+idRolCreado);

            // Asignar el ID del Rol al objeto Inscripcion
            inscripcion.setIdRol(idRolCreado);
            inscripcionDTO.getUsuario().setIdRol(idRolCreado); // Asignar el Rol al Usuario

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
    public Inscripcion editarInscripcionConRol(Long idInscripcion, Inscripcion inscripcionDTO, MultipartFile fotoPerfil) {
        // Buscar la inscripción existente por ID
        Optional<Inscripcion> inscripcionExistenteOpt = inscripcionesRepository.findById(idInscripcion);
        if (!inscripcionExistenteOpt.isPresent()) {
            throw new RuntimeException("Inscripción no encontrada para el ID: " + idInscripcion);
        }
        Inscripcion inscripcionExistente = inscripcionExistenteOpt.get();

        try {
            // 1. Actualizar el Rol
            ResponseEntity<Rol> rolResponse = rolFeign.actualizarRolDto(inscripcionDTO.getRol().getIdRol(), inscripcionDTO.getRol());
            if (rolResponse.getBody() == null || rolResponse.getBody().getIdRol() == null) {
                throw new RuntimeException("No se pudo actualizar el Rol o el ID del Rol es nulo. Este es el body: "+inscripcionDTO);
            }
            Long idRolActualizado = rolResponse.getBody().getIdRol();
            inscripcionExistente.setIdRol(idRolActualizado);
            inscripcionDTO.getUsuario().setIdRol(idRolActualizado); // Asignar el Rol al Usuario

            // 2. Actualizar el Usuario
            ResponseEntity<?> usuarioResponse = usuarioFeign.actualizarUsuarioDto(inscripcionExistente.getIdUsuario(), inscripcionDTO.getUsuario());
            if (usuarioResponse.getBody() == null || !usuarioResponse.getStatusCode().is2xxSuccessful()) {
                throw new RuntimeException("No se pudo actualizar el Usuario.");
            }
            Usuario usuarioActualizado = objectMapper.convertValue(usuarioResponse.getBody(), Usuario.class);
            inscripcionExistente.setIdUsuario(usuarioActualizado.getIdUsuario());

            // 3. Actualizar la Persona asociada
            inscripcionDTO.getPersona().setIdUsuario(usuarioActualizado.getIdUsuario());
            actualizarPersonaConFoto(inscripcionExistente.getIdPersona(), inscripcionDTO.getPersona(), fotoPerfil);

            // 4. Actualizar según el tipo específico (Administrador, Administrativo, Estudiante, Docente)
            if (inscripcionDTO.getAdministrador() != null) {
                Administrador administrador = inscripcionDTO.getAdministrador();
                administrador.setIdPersona(inscripcionExistente.getIdPersona());
                ResponseEntity<?> administradorResponse = administradorFeign.actualizarAdministradorDto(inscripcionExistente.getIdAdministrador(), administrador);
                if (administradorResponse.getBody() == null) {
                    throw new RuntimeException("No se pudo actualizar el Administrador.");
                }
                Administrador administradorActualizado = objectMapper.convertValue(administradorResponse.getBody(), Administrador.class);
                inscripcionExistente.setIdAdministrador(administradorActualizado.getIdAdministrador());

            } else if (inscripcionDTO.getAdministrativo() != null) {
                Administrativo administrativo = inscripcionDTO.getAdministrativo();
                administrativo.setIdPersona(inscripcionExistente.getIdPersona());
                ResponseEntity<?> administrativoResponse = administrativoFeign.actualizarAdministrativoDto(inscripcionExistente.getIdAdministrativo(), administrativo);
                if (administrativoResponse.getBody() == null) {
                    throw new RuntimeException("No se pudo actualizar el Administrativo.");
                }
                Administrativo administrativoActualizado = objectMapper.convertValue(administrativoResponse.getBody(), Administrativo.class);
                inscripcionExistente.setIdAdministrativo(administrativoActualizado.getIdAdministrativo());

            } else if (inscripcionDTO.getEstudiante() != null) {
                Estudiante estudiante = inscripcionDTO.getEstudiante();
                estudiante.setIdPersona(inscripcionExistente.getIdPersona());
                ResponseEntity<?> estudianteResponse = estudianteFeign.actualizarEstudianteDto(inscripcionExistente.getIdEstudiante(), estudiante);
                if (estudianteResponse.getBody() == null) {
                    throw new RuntimeException("No se pudo actualizar el Estudiante.");
                }
                Estudiante estudianteActualizado = objectMapper.convertValue(estudianteResponse.getBody(), Estudiante.class);
                inscripcionExistente.setIdEstudiante(estudianteActualizado.getIdEstudiante());

            } else if (inscripcionDTO.getDocente() != null) {
                Docente docente = inscripcionDTO.getDocente();
                docente.setIdPersona(inscripcionExistente.getIdPersona());
                ResponseEntity<?> docenteResponse = docenteFeign.actualizarDocenteDto(inscripcionExistente.getIdDocente(), docente);
                if (docenteResponse.getBody() == null) {
                    throw new RuntimeException("No se pudo actualizar el Docente.");
                }
                Docente docenteActualizado = objectMapper.convertValue(docenteResponse.getBody(), Docente.class);
                inscripcionExistente.setIdDocente(docenteActualizado.getIdDocente());

            } else {
                throw new RuntimeException("Debe proveerse solo un Administrador, Administrativo, Estudiante o un Docente, no varios.");
            }

            // Actualizar otros campos de inscripción
            inscripcionExistente.setInscripcionRol("Actualizado Con Rol");

            // Guardar la inscripción actualizada
            inscripcionesRepository.save(inscripcionExistente);

        } catch (FeignException e) {
            throw new RuntimeException("Error al comunicarse con los microservicios: " + e.getMessage(), e);
        }

        return inscripcionExistente;
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
}