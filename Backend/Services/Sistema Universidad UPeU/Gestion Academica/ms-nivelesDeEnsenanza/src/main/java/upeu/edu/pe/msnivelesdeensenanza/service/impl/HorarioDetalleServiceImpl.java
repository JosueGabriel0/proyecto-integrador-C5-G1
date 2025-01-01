package upeu.edu.pe.msnivelesdeensenanza.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import upeu.edu.pe.msnivelesdeensenanza.entity.HorarioDetalle;
import upeu.edu.pe.msnivelesdeensenanza.repository.HorarioDetalleRepository;
import upeu.edu.pe.msnivelesdeensenanza.service.HorarioDetalleService;

import java.util.List;

@Service
public class HorarioDetalleServiceImpl implements HorarioDetalleService {

    @Autowired
    private HorarioDetalleRepository horarioDetalleRepository;

    @Override
    public List<HorarioDetalle> listarTodos() {
        return horarioDetalleRepository.findAll();
    }

    @Override
    public HorarioDetalle obtenerPorId(Long id) {
        return horarioDetalleRepository.findById(id).get();
    }

    @Override
    public HorarioDetalle crear(HorarioDetalle horarioDetalle) {
        return horarioDetalleRepository.save(horarioDetalle);
    }

    @Override
    public HorarioDetalle actualizar(HorarioDetalle horarioDetalle) {
        return horarioDetalleRepository.save(horarioDetalle);
    }

    @Override
    public void eliminar(Long id) {
        horarioDetalleRepository.deleteById(id);
    }
}
