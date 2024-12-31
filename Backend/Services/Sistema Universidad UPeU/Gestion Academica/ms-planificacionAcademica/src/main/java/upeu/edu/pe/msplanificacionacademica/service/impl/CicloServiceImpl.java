package upeu.edu.pe.msplanificacionacademica.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import upeu.edu.pe.msplanificacionacademica.entity.Ciclo;
import upeu.edu.pe.msplanificacionacademica.repository.CicloRepository;
import upeu.edu.pe.msplanificacionacademica.service.CicloService;

import java.util.List;

@Service
public class CicloServiceImpl implements CicloService {

    @Autowired
    private CicloRepository cicloRepository;

    @Override
    public List<Ciclo> listarTodosLosCiclos() {
        return cicloRepository.findAll();
    }

    @Override
    public Ciclo listarCicloPorId(Long idCiclo) {
        return cicloRepository.findById(idCiclo).get();
    }

    @Override
    public Ciclo crearCiclo(Ciclo ciclo) {
        return cicloRepository.save(ciclo);
    }

    @Override
    public Ciclo modificarCiclo(Ciclo ciclo) {
        return cicloRepository.save(ciclo);
    }

    @Override
    public void eliminarCiclo(Long idCiclo) {
        cicloRepository.deleteById(idCiclo);
    }
}
