package upeu.edu.pe.mscarrera.service;

import upeu.edu.pe.mscarrera.entity.Carrera;

import java.util.List;

public interface CarreraService {

    public Carrera guardarCarrera(Carrera Carrera);

    public List<Carrera> listarCarrera();

    public Carrera buscarCarreraPorId(Long id);

    public Carrera editarCarrera(Carrera Carrera);

    public void eliminarCarrera(Long id);
}
