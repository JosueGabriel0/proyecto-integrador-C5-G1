package upeu.edu.pe.msestadosolicitud.service;

import upeu.edu.pe.msestadosolicitud.entity.EstadoSolicitud;

import java.util.List;

public interface EstadoSolicitudService {
    public EstadoSolicitud guardarEstadoSolicitud(EstadoSolicitud estadoSolicitud);
    public List<EstadoSolicitud> listarEstadoSolicituds();
    public EstadoSolicitud buscarEstadoSolicitudPorId(long id);
    public EstadoSolicitud editarEstadoSolicitud(EstadoSolicitud estadoSolicitud);
    public void eliminarEstadoSolicitud(long id);


}
