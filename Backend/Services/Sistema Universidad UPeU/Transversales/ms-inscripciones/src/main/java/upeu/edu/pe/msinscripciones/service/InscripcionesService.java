package upeu.edu.pe.msinscripciones.service;

import upeu.edu.pe.msinscripciones.entity.Inscripcion;

import java.util.List;

public interface InscripcionesService {

    public Inscripcion crearInscripcion(Inscripcion inscripcionDTO);

    public Inscripcion crearInscripcionConRol(Inscripcion inscripcionDTO);
/*
    public List<Inscripcion> listarInscripcion();

    public Inscripcion buscarInscripcionPorId(Long id);

    public Inscripcion editarInscripcion(Long id, Inscripcion nuevaInscripcionDTO);

    public Inscripcion eliminarInscripcion(Long id);

 */
}
