package upeu.edu.pe.mspostulante.service;

import upeu.edu.pe.mspostulante.entity.Postulante;

import java.util.List;

public interface PostulanteService {
    public Postulante guardarPostulante(Postulante postulante);
    public List<Postulante> listarPostulantes();
    public Postulante buscarPostulantePorId(long id);
    public Postulante editarPostulante(Postulante postulante);
    public void eliminarPostulante(long id);


}
