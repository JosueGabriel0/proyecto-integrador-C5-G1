package upeu.edu.pe.msinscripciones.service.impl;

import com.fasterxml.jackson.databind.ObjectMapper;
import feign.FeignException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import upeu.edu.pe.msinscripciones.dto.*;
import upeu.edu.pe.msinscripciones.entity.Inscripcion;
import upeu.edu.pe.msinscripciones.feign.*;
import upeu.edu.pe.msinscripciones.repository.InscripcionesRepository;
import upeu.edu.pe.msinscripciones.service.InscripcionesService;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
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



@Service
public class InscripcionesSeviceImpl implements InscripcionesService {

    private final RestTemplate restTemplate;
    private final ObjectMapper objectMapper;
    private static final String BASE_URL_PERSONA = "http://localhost:9090/persona";

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
    public Inscripcion crearInscripcion(Inscripcion inscripcionDTO, MultipartFile fotoPerfil){
        Inscripcion inscripcion = new Inscripcion();

        try {
            // Verificar y asignar el Rol
            ResponseEntity<Rol> rolResponse = rolFeign.listarRolDtoPorId(inscripcionDTO.getIdRol());
            if (rolResponse.getBody() == null) {
                throw new RuntimeException("No se pudo encontrar el Rol con ID: " + inscripcionDTO.getIdRol());
            }
            inscripcionDTO.getUsuario().setIdRol(inscripcionDTO.getIdRol());

            // Crear Usuario
            ResponseEntity<?> usuarioResponse = usuarioFeign.crearUsuarioDto(inscripcionDTO.getUsuario());
            if (usuarioResponse.getBody() == null) {
                throw new RuntimeException("No se pudo crear el Usuario.");
            }
            try {
                Usuario usuarioCreado = objectMapper.convertValue(usuarioResponse.getBody(), Usuario.class);
                inscripcion.setIdUsuario(usuarioCreado.getIdUsuario());
            }catch (IllegalArgumentException e){
                throw new RuntimeException("Error al convertir la respuesta a Usuario", e);
            }
            Usuario usuarioCreado = (Usuario) usuarioResponse.getBody();

            // Crear Persona
            inscripcionDTO.getPersona().setIdUsuario(usuarioCreado.getIdUsuario());
            ResponseEntity<?> personaResponse = personaFeign.crearPersonaDto(inscripcionDTO.getPersona(), fotoPerfil);
            if (personaResponse.getBody() == null) {
                throw new RuntimeException("No se pudo crear la Persona.");
            }
            try {
                Persona personaCreada = objectMapper.convertValue(personaResponse.getBody(), Persona.class);
                inscripcion.setIdPersona(personaCreada.getId());
            }catch (IllegalArgumentException e){
                throw new RuntimeException("Error al convertir la respuesta a Persona", e);
            }


            // Verificar si se crea un Administrador, Administrativo, Estudiante o un Docente, no varios a la vez
            if(inscripcionDTO.getAdministrador() != null && inscripcionDTO.getAdministrativo() == null && inscripcionDTO.getEstudiante() == null && inscripcionDTO.getDocente() == null){
                // Crear Administrador y obtener el ID
                Administrador administrador = inscripcionDTO.getAdministrador();
                administrador.setIdPersona(inscripcion.getIdPersona()); // Asignar el ID de la Persona al Administrador
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
                administrativo.setIdPersona(inscripcion.getIdPersona()); // Asignar el ID de la Persona al Administrativo
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
                estudiante.setIdPersona(inscripcion.getIdPersona()); // Asignar el ID de la Persona al Estudiante
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
                docente.setIdPersona(inscripcion.getIdPersona()); // Asignar el ID de la Persona al Docente
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
                throw new RuntimeException("Debe proveerse solo un Administrador, Administrativo, Estudiante o un Docente, no varios a la vez.");
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

        try {
            // Crear el Rol y obtener el ID
            ResponseEntity<Rol> rolResponse = rolFeign.crearRolDto(inscripcionDTO.getRol());
            if (rolResponse.getBody() == null || rolResponse.getBody().getIdRol() == null) {
                throw new RuntimeException("No se pudo crear el Rol o el ID del Rol es nulo.");
            }

            Long idRolCreado =  rolResponse.getBody().getIdRol(); //Long.valueOf(1); //Obtener el ID del Rol creado
            inscripcion.setIdRol(idRolCreado);
            //inscripcionDTO.getUsuario().setIdRol(idRolCreado);  // Asignar el Rol al Usuario

            // Crear Usuario
            inscripcionDTO.getUsuario().setIdRol(idRolCreado);
            ResponseEntity<?> usuarioResponse = usuarioFeign.crearUsuarioDto(inscripcionDTO.getUsuario());

            if (usuarioResponse.getBody() == null || !usuarioResponse.getStatusCode().is2xxSuccessful()) {
                throw new RuntimeException("No se pudo crear el Usuario.");
            }

            try {
                // Usar el ObjectMapper inyectado para convertir la respuesta a un objeto Usuario
                Usuario usuarioCreado = objectMapper.convertValue(usuarioResponse.getBody(), Usuario.class);
                inscripcion.setIdUsuario(usuarioCreado.getIdUsuario());
            } catch (IllegalArgumentException e) {
                throw new RuntimeException("Error al convertir la respuesta a Usuario", e);
            }
            Usuario usuarioCreado = (Usuario) usuarioResponse.getBody();

            // Crear Persona usando RestTemplate
            inscripcionDTO.getPersona().setIdUsuario(usuarioCreado.getIdUsuario());
            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.MULTIPART_FORM_DATA);

            try {
                // Crear MultipartBodyBuilder para la solicitud
                MultiValueMap<String, Object> builder = new LinkedMultiValueMap<>();
                builder.add("persona", inscripcionDTO.getPersona());
                builder.add("file", new ByteArrayResource(fotoPerfil.getBytes()) {
                    @Override
                    public String getFilename() {
                        return fotoPerfil.getOriginalFilename();
                    }
                });

                HttpEntity<MultiValueMap<String, Object>> requestEntity = new HttpEntity<>(builder, headers);

                // Llamar al servicio de persona
                ResponseEntity<?> personaResponse = restTemplate.exchange(BASE_URL_PERSONA, HttpMethod.POST, requestEntity, Object.class);
                if (personaResponse.getBody() == null) {
                    throw new RuntimeException("No se pudo crear la Persona.");
                }

                Persona personaCreada = objectMapper.convertValue(personaResponse.getBody(), Persona.class);
                inscripcion.setIdPersona(personaCreada.getId());

            } catch (IOException e) {
                throw new RuntimeException("Error al procesar el archivo de la foto de perfil.", e);
            }

            // Verificar si se crea un Administrador, Administrativo, Estudiante o un Docente, no varios a la vez
            if(inscripcionDTO.getAdministrador() != null && inscripcionDTO.getAdministrativo() == null && inscripcionDTO.getEstudiante() == null && inscripcionDTO.getDocente() == null){
                // Crear Administrador y obtener el ID
                Administrador administrador = inscripcionDTO.getAdministrador();
                administrador.setIdPersona(1);
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
                administrativo.setIdPersona(1);
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
                estudiante.setIdPersona(1);
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
                docente.setIdPersona(1);
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

            // Actualizar Administrador, Administrativo, Estudiante o Docente según corresponda
            if(inscripcionDTO.getAdministrador() != null && inscripcionDTO.getAdministrativo() == null && inscripcionDTO.getEstudiante() == null && inscripcionDTO.getDocente() == null){
                Administrador administrador = inscripcionDTO.getAdministrador();
                administrador.setIdPersona(inscripcion.getIdPersona());  // Asociar al ID de la Persona actualizada
                ResponseEntity<Administrador> administradorResponse = administradorFeign.actualizarAdministradorDto(inscripcion.getIdAdministrador(), administrador);
                if (administradorResponse.getBody() == null) {
                    throw new RuntimeException("No se pudo actualizar el Administrador.");
                }
                inscripcion.setIdAdministrador(administradorResponse.getBody().getIdAdministrador());
                inscripcion.setIdAdministrativo(null);  // Asegurarse de que no hay un administrativo asociado
                inscripcion.setIdEstudiante(null);  // Asegurarse de que no hay un estudiante asociado
                inscripcion.setIdDocente(null);  // Asegurarse de que no hay un docente asociado

            } else if(inscripcionDTO.getAdministrativo() != null && inscripcionDTO.getAdministrador() == null && inscripcionDTO.getEstudiante() == null && inscripcionDTO.getDocente() == null){
                Administrativo administrativo = inscripcionDTO.getAdministrativo();
                administrativo.setIdPersona(inscripcion.getIdPersona());  // Asociar al ID de la Persona actualizada
                ResponseEntity<Administrativo> administrativoResponse = administrativoFeign.actualizarAdministrativoDto(inscripcion.getIdAdministrativo(), administrativo);
                if (administrativoResponse.getBody() == null) {
                    throw new RuntimeException("No se pudo actualizar el Administrativo.");
                }
                inscripcion.setIdAdministrativo(administrativoResponse.getBody().getIdAdministrativo());
                inscripcion.setIdAdministrador(null);  // Asegurarse de que no hay un administrador asociado
                inscripcion.setIdEstudiante(null);  // Asegurarse de que no hay un estudiante asociado
                inscripcion.setIdDocente(null);  // Asegurarse de que no hay un docente asociado

            }else if (inscripcionDTO.getEstudiante() != null && inscripcionDTO.getAdministrador() == null && inscripcionDTO.getAdministrativo() == null && inscripcionDTO.getDocente() == null) {
                Estudiante estudiante = inscripcionDTO.getEstudiante();
                estudiante.setIdPersona(inscripcion.getIdPersona());  // Asociar al ID de la Persona actualizada
                ResponseEntity<Estudiante> estudianteResponse = estudianteFeign.actualizarEstudianteDto(inscripcion.getIdEstudiante(), estudiante);
                if (estudianteResponse.getBody() == null) {
                    throw new RuntimeException("No se pudo actualizar el Estudiante.");
                }
                inscripcion.setIdEstudiante(estudianteResponse.getBody().getIdEstudiante());
                inscripcion.setIdAdministrador(null);  // Asegurarse de que no hay un administrador asociado
                inscripcion.setIdAdministrativo(null);  // Asegurarse de que no hay un administrativo asociado
                inscripcion.setIdDocente(null);  // Asegurarse de que no hay un docente asociado

            } else if (inscripcionDTO.getDocente() != null && inscripcionDTO.getAdministrador() == null && inscripcionDTO.getAdministrativo() == null && inscripcionDTO.getEstudiante() == null) {
                Docente docente = inscripcionDTO.getDocente();
                docente.setIdPersona(inscripcion.getIdPersona());  // Asociar al ID de la Persona actualizada
                ResponseEntity<Docente> docenteResponse = docenteFeign.actualizarDocenteDto(inscripcion.getIdDocente(), docente);
                if (docenteResponse.getBody() == null) {
                    throw new RuntimeException("No se pudo actualizar el Docente.");
                }
                inscripcion.setIdDocente(docenteResponse.getBody().getIdDocente());
                inscripcion.setIdAdministrador(null);  // Asegurarse de que no hay un administrador asociado
                inscripcion.setIdAdministrativo(null);  // Asegurarse de que no hay un administrativo asociado
                inscripcion.setIdEstudiante(null);  // Asegurarse de que no hay un estudiante asociado
            } else {
                throw new RuntimeException("Debe actualizarse solo un Administrador, Administrativo, Estudiante o un Docente, no varios a la vez.");
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