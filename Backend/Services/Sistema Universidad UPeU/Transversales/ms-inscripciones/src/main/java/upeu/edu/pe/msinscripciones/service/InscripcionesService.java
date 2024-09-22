package upeu.edu.pe.msinscripciones.service;

import upeu.edu.pe.msinscripciones.entity.Inscripcion;

import java.util.List;

public interface InscripcionesService {

    public Inscripcion crearInscripcionConRol(Inscripcion inscripcionDTO);

    public Inscripcion crearInscripcion(Inscripcion inscripcionDTO);

    public List<Inscripcion> listarInscripcion();

    public Inscripcion buscarInscripcionPorId(Long id);

    public Inscripcion editarInscripcion(Long id, Inscripcion inscripcionDTO);

    public void eliminarInscripcion(Long id);

}
