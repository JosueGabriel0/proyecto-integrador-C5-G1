package upeu.edu.pe.msroles.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import upeu.edu.pe.msroles.entity.Rol;
import upeu.edu.pe.msroles.repository.RolRepository;
import upeu.edu.pe.msroles.service.RolService;

import java.util.List;

@Service
public class    RolSeviceImpl implements RolService {
    @Autowired
    private RolRepository rolRepository;

    @Override
    public Rol guardarRol(Rol rol) {
        return rolRepository.save(rol);
    }

    @Override
    public List<Rol> listarRol(){
        return rolRepository.findAll();
    }

    @Override
    public Rol buscarRolPorId(Long id){
        return rolRepository.findById(id).get();
    }

    @Override
    public Rol editarRol(Rol rol) {
        return rolRepository.save(rol);
    }

    @Override
    public void eliminarRol(Long id){
        rolRepository.deleteById(id);
    }
}
