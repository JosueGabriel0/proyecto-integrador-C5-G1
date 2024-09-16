package upeu.edu.pe.mspostulante.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import upeu.edu.pe.mspostulante.entity.Postulante;
import upeu.edu.pe.mspostulante.repository.PostulanteRepository;
import upeu.edu.pe.mspostulante.service.PostulanteService;

import java.util.List;

@Service
public class PostulanteServiceImpl implements PostulanteService{
    @Autowired
    PostulanteRepository postulanteRepository;
    @Override
    public Postulante guardarPostulante(Postulante postulante) {
        return postulanteRepository.save(postulante);
    }

    @Override
    public List<Postulante> listarPostulantes() {
        return postulanteRepository.findAll();
    }

    @Override
    public Postulante buscarPostulantePorId(long id) {
        return postulanteRepository.findById(id).get();
    }

    @Override
    public Postulante editarPostulante(Postulante postulante) {
        return postulanteRepository.save(postulante);
    }

    @Override
    public void eliminarPostulante(long id) {
        postulanteRepository.deleteById(id);
    }
}
