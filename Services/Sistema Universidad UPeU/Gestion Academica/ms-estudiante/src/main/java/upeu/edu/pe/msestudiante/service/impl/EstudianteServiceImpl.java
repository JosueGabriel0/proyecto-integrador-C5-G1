package upeu.edu.pe.msestudiante.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import upeu.edu.pe.msestudiante.entity.Estudiante;
import upeu.edu.pe.msestudiante.repository.EstudianteRepository;
import upeu.edu.pe.msestudiante.service.EstudianteService;

import java.util.List;

@Service

public class EstudianteServiceImpl implements EstudianteService {

    @Autowired
    private EstudianteRepository estudianteRepository;

    @Override
    public Estudiante guardarEstudiante(Estudiante estudiante) {
        return estudianteRepository.save(estudiante);
    }

    @Override
    public List<Estudiante> listarEstudiantes() {
        return estudianteRepository.findAll();
    }

    @Override
    public Estudiante buscarEstudiantePorId(Long id) {
        return estudianteRepository.findById(id).get();
    }

    @Override
    public Estudiante editarEstudiante(Estudiante estudiante) {
        return estudianteRepository.save(estudiante);
    }

    @Override
    public void eliminarEstudiante(Long id) {
        estudianteRepository.deleteById(id);
    }
}
