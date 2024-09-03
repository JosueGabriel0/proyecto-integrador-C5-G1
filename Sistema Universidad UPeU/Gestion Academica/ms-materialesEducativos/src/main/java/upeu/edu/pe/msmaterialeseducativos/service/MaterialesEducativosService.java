package upeu.edu.pe.msmaterialeseducativos.service;

import upeu.edu.pe.msmaterialeseducativos.entity.MaterialesEducativos;

import java.util.List;

public interface MaterialesEducativosService {

    public MaterialesEducativos guardarMaterialesEducativos(MaterialesEducativos MaterialesEducativos);

    public List<MaterialesEducativos> listarMaterialesEducativos();

    public MaterialesEducativos buscarMaterialesEducativosPorId(Long id);

    public MaterialesEducativos editarMaterialesEducativos(MaterialesEducativos MaterialesEducativos);

    public void eliminarMaterialesEducativos(Long id);
}
