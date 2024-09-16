package upeu.edu.pe.msestadosolicitud.service.impl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import upeu.edu.pe.msestadosolicitud.entity.EstadoSolicitud;
import upeu.edu.pe.msestadosolicitud.repository.EstadoSolicitudRepository;
import upeu.edu.pe.msestadosolicitud.service.EstadoSolicitudService;

import java.util.List;

@Service
public class EstadoSolicitudServiceImpl implements EstadoSolicitudService{
    @Autowired
    EstadoSolicitudRepository estadoSolicitudRepository;
    @Override
    public EstadoSolicitud guardarEstadoSolicitud(EstadoSolicitud estadoSolicitud) {
        return estadoSolicitudRepository.save(estadoSolicitud);
    }

    @Override
    public List<EstadoSolicitud> listarEstadoSolicituds() {
        return estadoSolicitudRepository.findAll();
    }

    @Override
    public EstadoSolicitud buscarEstadoSolicitudPorId(long id) {
        return estadoSolicitudRepository.findById(id).get();
    }

    @Override
    public EstadoSolicitud editarEstadoSolicitud(EstadoSolicitud estadoSolicitud) {
        return estadoSolicitudRepository.save(estadoSolicitud);
    }

    @Override
    public void eliminarEstadoSolicitud(long id) {
        estadoSolicitudRepository.deleteById(id);
    }
}
