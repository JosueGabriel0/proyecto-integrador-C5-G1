package upeu.edu.pe.mscuentafinancierauniversitaria.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import upeu.edu.pe.mscuentafinancierauniversitaria.entity.MovimientoAcademico;
import upeu.edu.pe.mscuentafinancierauniversitaria.repository.MovimientoAcademicoRepository;
import upeu.edu.pe.mscuentafinancierauniversitaria.service.MovimientoAcademicoService;

import java.util.List;

@Service
public class MovimientoAcademicoServiceImpl implements MovimientoAcademicoService {

    @Autowired
    private MovimientoAcademicoRepository movimientoAcademicoRepository;

    @Override
    public List<MovimientoAcademico> listarTodos() {
        return movimientoAcademicoRepository.findAll();
    }

    @Override
    public MovimientoAcademico obtenerPorId(Long id) {
        return movimientoAcademicoRepository.findById(id).get();
    }

    @Override
    public MovimientoAcademico crear(MovimientoAcademico movimientoAcademico) {
        return movimientoAcademicoRepository.save(movimientoAcademico);
    }

    @Override
    public MovimientoAcademico actualizar(MovimientoAcademico movimientoAcademico) {
        return movimientoAcademicoRepository.save(movimientoAcademico);
    }

    @Override
    public void eliminar(Long id) {
        movimientoAcademicoRepository.deleteById(id);
    }
}
