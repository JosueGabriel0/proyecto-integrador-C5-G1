package upeu.edu.pe.msresultadoevaluacion.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import upeu.edu.pe.msresultadoevaluacion.entity.ResultadoEvaluacion;
import upeu.edu.pe.msresultadoevaluacion.repository.ResultadoEvaluacionRepository;
import upeu.edu.pe.msresultadoevaluacion.service.ResultadoEvaluacionService;

import java.util.List;

@Service
public class ResultadoEvaluacionServiceImpl implements ResultadoEvaluacionService {
    @Autowired
    ResultadoEvaluacionRepository resultadoEvaluacionRepository;
    @Override
    public ResultadoEvaluacion guardarResultadoEvaluacion(ResultadoEvaluacion ResultadoEvaluacion) {
        return resultadoEvaluacionRepository.save(ResultadoEvaluacion);
    }

    @Override
    public List<ResultadoEvaluacion> listarResultadoEvaluacions() {
        return resultadoEvaluacionRepository.findAll();
    }

    @Override
    public ResultadoEvaluacion buscarResultadoEvaluacionPorId(long id) {
        return resultadoEvaluacionRepository.findById(id).get();
    }

    @Override
    public ResultadoEvaluacion editarResultadoEvaluacion(ResultadoEvaluacion ResultadoEvaluacion) {
        return resultadoEvaluacionRepository.save(ResultadoEvaluacion);
    }

    @Override
    public void eliminarResultadoEvaluacion(long id) {
        resultadoEvaluacionRepository.deleteById(id);
    }
}
