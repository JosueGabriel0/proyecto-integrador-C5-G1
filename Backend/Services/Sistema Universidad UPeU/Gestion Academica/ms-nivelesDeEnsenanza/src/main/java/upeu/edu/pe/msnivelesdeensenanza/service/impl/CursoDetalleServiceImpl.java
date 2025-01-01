package upeu.edu.pe.msnivelesdeensenanza.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import upeu.edu.pe.msnivelesdeensenanza.entity.CicloDetalle;
import upeu.edu.pe.msnivelesdeensenanza.entity.CursoDetalle;
import upeu.edu.pe.msnivelesdeensenanza.repository.CursoDetalleRepository;
import upeu.edu.pe.msnivelesdeensenanza.service.CursoDetalleService;

import java.util.List;

@Service
public class CursoDetalleServiceImpl implements CursoDetalleService {

    @Autowired
    private CursoDetalleRepository cursoDetalleRepository;

    @Override
    public List<CursoDetalle> listarTodos() {
        return cursoDetalleRepository.findAll();
    }

    @Override
    public CursoDetalle obtenerPorId(Long id) {
        return cursoDetalleRepository.findById(id).get();
    }

    @Override
    public CursoDetalle crear(CursoDetalle cursoDetalle) {
        return cursoDetalleRepository.save(cursoDetalle);
    }

    @Override
    public CursoDetalle actualizar(CursoDetalle cursoDetalle) {
        return cursoDetalleRepository.save(cursoDetalle);
    }

    @Override
    public void eliminar(Long id) {
        cursoDetalleRepository.deleteById(id);
    }
}
