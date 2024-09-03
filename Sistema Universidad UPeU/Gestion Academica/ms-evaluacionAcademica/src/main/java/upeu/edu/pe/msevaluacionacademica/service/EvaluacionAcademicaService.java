package upeu.edu.pe.msevaluacionacademica.service;

import upeu.edu.pe.msevaluacionacademica.entity.EvaluacionAcademica;

import java.util.List;

public interface EvaluacionAcademicaService {

    public EvaluacionAcademica guardarEvaluacionAcademica(EvaluacionAcademica EvaluacionAcademica);

    public List<EvaluacionAcademica> listarEvaluacionAcademica();

    public EvaluacionAcademica buscarEvaluacionAcademicaPorId(Long id);

    public EvaluacionAcademica editarEvaluacionAcademica(EvaluacionAcademica EvaluacionAcademica);

    public void eliminarEvaluacionAcademica(Long id);
}
