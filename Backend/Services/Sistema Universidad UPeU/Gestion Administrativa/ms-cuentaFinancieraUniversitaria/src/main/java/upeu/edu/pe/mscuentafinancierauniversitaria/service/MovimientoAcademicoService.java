package upeu.edu.pe.mscuentafinancierauniversitaria.service;

import upeu.edu.pe.mscuentafinancierauniversitaria.entity.MovimientoAcademico;

import java.time.LocalDate;
import java.util.List;

public interface MovimientoAcademicoService {
    public List<MovimientoAcademico> listarTodos();
    public MovimientoAcademico obtenerPorId(Long id);
    public MovimientoAcademico crear(MovimientoAcademico movimientoAcademico);
    public MovimientoAcademico actualizar(MovimientoAcademico movimientoAcademico);
    public void eliminar(Long id);
    public List<MovimientoAcademico> obtenerPorCuentaFinanciera(Long idCuentaFinanciera);
    public List<MovimientoAcademico> buscarPorCuentaYAnio(Long idCuentaFinanciera, int anio);
}
