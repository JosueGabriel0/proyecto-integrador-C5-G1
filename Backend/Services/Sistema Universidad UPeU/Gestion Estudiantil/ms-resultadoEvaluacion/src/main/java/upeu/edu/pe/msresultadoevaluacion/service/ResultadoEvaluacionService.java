package upeu.edu.pe.msresultadoevaluacion.service;
import upeu.edu.pe.msresultadoevaluacion.entity.ResultadoEvaluacion;

import java.util.List;

public interface ResultadoEvaluacionService {
    public ResultadoEvaluacion guardarResultadoEvaluacion(ResultadoEvaluacion resultadoEvaluacion);
    public List<ResultadoEvaluacion> listarResultadoEvaluacions();
    public ResultadoEvaluacion buscarResultadoEvaluacionPorId(long id);
    public ResultadoEvaluacion editarResultadoEvaluacion(ResultadoEvaluacion resultadoEvaluacion);
    public void eliminarResultadoEvaluacion(long id);


}