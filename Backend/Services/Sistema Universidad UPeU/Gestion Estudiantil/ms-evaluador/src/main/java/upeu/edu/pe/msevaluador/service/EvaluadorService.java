package upeu.edu.pe.msevaluador.service;

import upeu.edu.pe.msevaluador.entity.Evaluador;

import java.util.List;

public interface EvaluadorService {
    public Evaluador guardarEvaluador(Evaluador Evaluador);
    public List<Evaluador> listarEvaluadors();
    public Evaluador buscarEvaluadorPorId(long id);
    public Evaluador editarEvaluador(Evaluador Evaluador);
    public void eliminarEvaluador(long id);


}
