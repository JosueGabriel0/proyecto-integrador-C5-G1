package upeu.edu.pe.msevaluador.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import upeu.edu.pe.msevaluador.entity.Evaluador;
import upeu.edu.pe.msevaluador.repository.EvaluadorRepository;
import upeu.edu.pe.msevaluador.service.EvaluadorService;

import java.util.List;

@Service
public class EvaluadorServiceImpl implements EvaluadorService {
    @Autowired
    EvaluadorRepository evaluadorRepository;
    @Override
    public Evaluador guardarEvaluador(Evaluador Evaluador) {
        return evaluadorRepository.save(Evaluador);
    }

    @Override
    public List<Evaluador> listarEvaluadors() {
        return evaluadorRepository.findAll();
    }

    @Override
    public Evaluador buscarEvaluadorPorId(long id) {
        return evaluadorRepository.findById(id).get();
    }

    @Override
    public Evaluador editarEvaluador(Evaluador Evaluador) {
        return evaluadorRepository.save(Evaluador);
    }

    @Override
    public void eliminarEvaluador(long id) {
        evaluadorRepository.deleteById(id);
    }
}
