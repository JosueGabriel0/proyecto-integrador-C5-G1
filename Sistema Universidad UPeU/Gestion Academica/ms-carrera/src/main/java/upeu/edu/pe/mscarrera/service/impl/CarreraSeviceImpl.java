package upeu.edu.pe.mscarrera.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import upeu.edu.pe.mscarrera.entity.Carrera;
import upeu.edu.pe.mscarrera.repository.CarreraRepository;
import upeu.edu.pe.mscarrera.service.CarreraService;

import java.util.List;

@Service
public class CarreraSeviceImpl implements CarreraService {
    @Autowired
    private CarreraRepository CarreraRepository;

    @Override
    public Carrera guardarCarrera(Carrera Carrera) {
        return CarreraRepository.save(Carrera);
    }

    @Override
    public List<Carrera> listarCarrera(){
        return CarreraRepository.findAll();
    }

    @Override
    public Carrera buscarCarreraPorId(Long id){
        return CarreraRepository.findById(id).get();
    }

    @Override
    public Carrera editarCarrera(Carrera Carrera) {
        return CarreraRepository.save(Carrera);
    }

    @Override
    public void eliminarCarrera(Long id){
        CarreraRepository.deleteById(id);
    }

}
