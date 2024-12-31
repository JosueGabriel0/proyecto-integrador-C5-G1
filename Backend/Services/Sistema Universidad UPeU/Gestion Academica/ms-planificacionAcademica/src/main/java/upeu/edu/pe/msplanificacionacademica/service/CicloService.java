package upeu.edu.pe.msplanificacionacademica.service;

import upeu.edu.pe.msplanificacionacademica.entity.Ciclo;

import java.util.List;

public interface CicloService {
    public List<Ciclo> listarTodosLosCiclos();
    public Ciclo listarCicloPorId(Long idCiclo);
    public Ciclo crearCiclo(Ciclo ciclo);
    public Ciclo modificarCiclo(Ciclo ciclo);
    public void eliminarCiclo(Long idCiclo);
}
