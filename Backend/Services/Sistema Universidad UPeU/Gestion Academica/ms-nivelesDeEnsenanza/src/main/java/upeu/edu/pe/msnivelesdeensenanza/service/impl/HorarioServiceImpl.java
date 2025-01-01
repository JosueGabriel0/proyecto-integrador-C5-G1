package upeu.edu.pe.msnivelesdeensenanza.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import upeu.edu.pe.msnivelesdeensenanza.entity.Horario;
import upeu.edu.pe.msnivelesdeensenanza.repository.HorarioRepository;
import upeu.edu.pe.msnivelesdeensenanza.service.HorarioService;

import java.util.List;

@Service
public class HorarioServiceImpl implements HorarioService {

    @Autowired
    private HorarioRepository horarioRepository;

    @Override
    public List<Horario> listarTodos() {
        return horarioRepository.findAll();
    }

    @Override
    public Horario obtenerPorId(Long id) {
        return horarioRepository.findById(id).get();
    }

    @Override
    public Horario crear(Horario horario) {
        return horarioRepository.save(horario);
    }

    @Override
    public Horario actualizar(Horario horario) {
        return horarioRepository.save(horario);
    }

    @Override
    public void eliminar(Long id) {
        horarioRepository.deleteById(id);
    }
}
