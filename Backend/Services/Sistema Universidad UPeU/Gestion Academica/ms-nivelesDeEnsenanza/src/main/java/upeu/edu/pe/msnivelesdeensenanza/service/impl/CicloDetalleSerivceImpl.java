package upeu.edu.pe.msnivelesdeensenanza.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import upeu.edu.pe.msnivelesdeensenanza.entity.CicloDetalle;
import upeu.edu.pe.msnivelesdeensenanza.repository.CicloDetalleRepository;
import upeu.edu.pe.msnivelesdeensenanza.service.CicloDetalleService;

import java.util.List;

@Service
public class CicloDetalleSerivceImpl implements CicloDetalleService {

    @Autowired
    private CicloDetalleRepository cicloDetalleRepository;

    @Override
    public List<CicloDetalle> listarTodos() {
        return cicloDetalleRepository.findAll();
    }

    @Override
    public CicloDetalle obtenerPorId(Long id) {
        return cicloDetalleRepository.findById(id).get();
    }

    @Override
    public CicloDetalle crear(CicloDetalle cicloDetalle) {
        return cicloDetalleRepository.save(cicloDetalle);
    }

    @Override
    public CicloDetalle actualizar(CicloDetalle cicloDetalle) {
        return cicloDetalleRepository.save(cicloDetalle);
    }

    @Override
    public void eliminar(Long id) {
        cicloDetalleRepository.deleteById(id);
    }
}
