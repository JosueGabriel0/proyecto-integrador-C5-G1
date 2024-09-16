package upeu.edu.pe.msmaterialeseducativos.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import upeu.edu.pe.msmaterialeseducativos.entity.MaterialesEducativos;
import upeu.edu.pe.msmaterialeseducativos.repository.MaterialesEducativosRepository;
import upeu.edu.pe.msmaterialeseducativos.service.MaterialesEducativosService;

import java.util.List;

@Service
public class MaterialesEducativosSeviceImpl implements MaterialesEducativosService {
    @Autowired
    private MaterialesEducativosRepository materialesEducativosRepository;

    @Override
    public MaterialesEducativos guardarMaterialesEducativos(MaterialesEducativos MaterialesEducativos) {
        return materialesEducativosRepository.save(MaterialesEducativos);
    }

    @Override
    public List<MaterialesEducativos> listarMaterialesEducativos(){
        return materialesEducativosRepository.findAll();
    }

    @Override
    public MaterialesEducativos buscarMaterialesEducativosPorId(Long id){
        return materialesEducativosRepository.findById(id).get();
    }

    @Override
    public MaterialesEducativos editarMaterialesEducativos(MaterialesEducativos MaterialesEducativos) {
        return materialesEducativosRepository.save(MaterialesEducativos);
    }

    @Override
    public void eliminarMaterialesEducativos(Long id){
        materialesEducativosRepository.deleteById(id);
    }

}
