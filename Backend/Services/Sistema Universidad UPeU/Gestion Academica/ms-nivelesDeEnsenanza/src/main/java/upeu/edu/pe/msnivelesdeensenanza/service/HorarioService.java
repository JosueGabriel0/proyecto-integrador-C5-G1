package upeu.edu.pe.msnivelesdeensenanza.service;

import upeu.edu.pe.msnivelesdeensenanza.entity.Horario;

import java.util.List;

public interface HorarioService {
    public List<Horario> listarTodos();
    public Horario obtenerPorId(Long id);
    public Horario crear(Horario horario);
    public Horario actualizar(Horario horario);
    public void eliminar(Long id);
}
