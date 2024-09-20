package upeu.edu.pe.msinscripciones.service;

import upeu.edu.pe.msinscripciones.entity.Inscripcion;

import java.util.List;

public interface InscripcionesService {

    public Inscripcion guardarInscripcion(Inscripcion Inscripcion);

    public List<Inscripcion> listarInscripcion();

    public Inscripcion buscarInscripcionPorId(Long id);

    public Inscripcion editarInscripcion(Inscripcion Inscripcion);

    public void eliminarInscripcion(Long id);
}
