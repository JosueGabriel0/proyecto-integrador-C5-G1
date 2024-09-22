package upeu.edu.pe.msinscripciones.service;

import upeu.edu.pe.msinscripciones.entity.Inscripcion;

import java.util.List;

public interface InscripcionesService {

    //CRUD DE INSCRIPCION
    public Inscripcion crearInscripcion(Inscripcion inscripcionDTO);

    public List<Inscripcion> listarInscripcion();

    public Inscripcion buscarInscripcionPorId(Long id);

    public Inscripcion editarInscripcion(Long id, Inscripcion inscripcionDTO);

    public void eliminarInscripcion(Long idInscripcion);

    //CUD DE INSCRIPCION CON ROL
    public Inscripcion crearInscripcionConRol(Inscripcion inscripcionDTO);

    public Inscripcion editarInscripcionConRol(Long id, Inscripcion inscripcionDTO);

    public void eliminarInscripcionConRol(Long id);
}
