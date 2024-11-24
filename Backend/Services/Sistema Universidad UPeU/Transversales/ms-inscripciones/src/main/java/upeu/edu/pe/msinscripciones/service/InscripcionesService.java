package upeu.edu.pe.msinscripciones.service;

import org.springframework.web.multipart.MultipartFile;
import upeu.edu.pe.msinscripciones.dto.Persona;
import upeu.edu.pe.msinscripciones.entity.Inscripcion;

import java.util.List;

public interface InscripcionesService {

    //CRUD DE INSCRIPCION
    public Inscripcion crearInscripcion(Inscripcion inscripcionDTO, MultipartFile fotoPerfil);

    public void crearPersonaConFoto(Persona personaDTO, MultipartFile fotoPerfil);

    public void actualizarPersonaConFoto(Long idPersona, Persona personaDTO, MultipartFile fotoPerfil);

    public Inscripcion editarInscripcion(Long idInscripcion,Inscripcion inscripcionDTO, MultipartFile fotoPerfil);

    public void eliminarInscripcion(Long id);

    public List<Inscripcion> listarInscripcion();

    public Inscripcion buscarInscripcionPorId(Long id);

    public Inscripcion buscarInscripcionPorIdUsuario(Long idUsuario);
}
