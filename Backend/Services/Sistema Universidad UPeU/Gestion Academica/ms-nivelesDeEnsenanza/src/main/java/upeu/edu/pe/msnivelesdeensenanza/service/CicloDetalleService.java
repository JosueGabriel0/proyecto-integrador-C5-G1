package upeu.edu.pe.msnivelesdeensenanza.service;

import upeu.edu.pe.msnivelesdeensenanza.entity.CicloDetalle;

import java.util.List;

public interface CicloDetalleService {
    public List<CicloDetalle> listarTodos();
    public CicloDetalle obtenerPorId(Long id);
    public CicloDetalle crear(CicloDetalle cicloDetalle);
    public CicloDetalle actualizar(CicloDetalle cicloDetalle);
    public void eliminar(Long id);
}
