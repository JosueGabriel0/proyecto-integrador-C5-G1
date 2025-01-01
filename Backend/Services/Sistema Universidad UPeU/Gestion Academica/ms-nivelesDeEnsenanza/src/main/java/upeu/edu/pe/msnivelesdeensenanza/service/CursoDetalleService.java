package upeu.edu.pe.msnivelesdeensenanza.service;

import upeu.edu.pe.msnivelesdeensenanza.entity.CursoDetalle;

import java.util.List;

public interface CursoDetalleService {
    public List<CursoDetalle> listarTodos();
    public CursoDetalle obtenerPorId(Long id);
    public CursoDetalle crear(CursoDetalle cursoDetalle);
    public CursoDetalle actualizar(CursoDetalle cursoDetalle);
    public void eliminar(Long id);
}
