package upeu.edu.pe.msnivelesdeensenanza.service;

import upeu.edu.pe.msnivelesdeensenanza.entity.HorarioDetalle;

import java.util.List;

public interface HorarioDetalleService {
    public List<HorarioDetalle> listarTodos();
    public HorarioDetalle obtenerPorId(Long id);
    public HorarioDetalle crear(HorarioDetalle horarioDetalle);
    public HorarioDetalle actualizar(HorarioDetalle horarioDetalle);
    public void eliminar(Long id);
}
