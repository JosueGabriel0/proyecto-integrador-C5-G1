package upeu.edu.pe.msinscripciones.service;

import org.springframework.web.multipart.MultipartFile;
import upeu.edu.pe.msinscripciones.dto.Persona;
import upeu.edu.pe.msinscripciones.entity.Inscripcion;

import java.util.List;

public interface InscripcionesService {

    //CUD DE INSCRIPCION
    public Inscripcion crearInscripcion(Inscripcion inscripcionDTO, MultipartFile fotoPerfil);

    public Inscripcion editarInscripcion(Long id, Inscripcion inscripcionDTO);

    public void eliminarInscripcion(Long idInscripcion);

    //CUD DE INSCRIPCION CON ROL
    public Inscripcion crearInscripcionConRol(Inscripcion inscripcionDTO, MultipartFile fotoPerfil);

    public void crearPersonaConFoto(Persona personaDTO, MultipartFile fotoPerfil);

    public void actualizarPersonaConFoto(Long idPersona, Persona personaDTO, MultipartFile fotoPerfil);

    public Inscripcion editarInscripcionConRol(Long idInscripcion, Inscripcion inscripcionDTO, MultipartFile fotoPerfil);

    public void eliminarInscripcionConRol(Long id);

    //R INSCRIPCION GENERAL
    public List<Inscripcion> listarInscripcion();

    public Inscripcion buscarInscripcionPorId(Long id);
}
