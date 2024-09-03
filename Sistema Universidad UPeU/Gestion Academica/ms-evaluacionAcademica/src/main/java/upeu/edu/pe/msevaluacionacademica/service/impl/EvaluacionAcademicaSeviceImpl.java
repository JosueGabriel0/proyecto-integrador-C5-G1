package upeu.edu.pe.msevaluacionacademica.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import upeu.edu.pe.msevaluacionacademica.entity.EvaluacionAcademica;
import upeu.edu.pe.msevaluacionacademica.repository.EvaluacionAcademicaRepository;
import upeu.edu.pe.msevaluacionacademica.service.EvaluacionAcademicaService;

import java.util.List;

@Service
public class EvaluacionAcademicaSeviceImpl implements EvaluacionAcademicaService {
    @Autowired
    private EvaluacionAcademicaRepository evaluacionAcademicaRepository;

    @Override
    public EvaluacionAcademica guardarEvaluacionAcademica(EvaluacionAcademica EvaluacionAcademica) {
        return evaluacionAcademicaRepository.save(EvaluacionAcademica);
    }

    @Override
    public List<EvaluacionAcademica> listarEvaluacionAcademica(){
        return evaluacionAcademicaRepository.findAll();
    }

    @Override
    public EvaluacionAcademica buscarEvaluacionAcademicaPorId(Long id){
        return evaluacionAcademicaRepository.findById(id).get();
    }

    @Override
    public EvaluacionAcademica editarEvaluacionAcademica(EvaluacionAcademica EvaluacionAcademica) {
        return evaluacionAcademicaRepository.save(EvaluacionAcademica);
    }

    @Override
    public void eliminarEvaluacionAcademica(Long id){
        evaluacionAcademicaRepository.deleteById(id);
    }

}
