package upeu.edu.pe.mscuentafinancierauniversitaria.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import upeu.edu.pe.mscuentafinancierauniversitaria.entity.CuentaFinanciera;
import upeu.edu.pe.mscuentafinancierauniversitaria.repository.CuentaFinancieraRepository;
import upeu.edu.pe.mscuentafinancierauniversitaria.service.CuentaFinancieraService;

import java.util.List;

@Service
public class CuentaFinancieraServiceImpl implements CuentaFinancieraService {
    @Autowired
    private CuentaFinancieraRepository cuentaFinancieraRepository;

    @Override
    public List<CuentaFinanciera> listarTodos() {
        return cuentaFinancieraRepository.findAll();
    }

    @Override
    public CuentaFinanciera obtenerPorId(Long id) {
        return cuentaFinancieraRepository.findById(id).get();
    }

    @Override
    public CuentaFinanciera crear(CuentaFinanciera cuentaFinanciera) {
        return cuentaFinancieraRepository.save(cuentaFinanciera);
    }

    @Override
    public CuentaFinanciera actualizar(CuentaFinanciera cuentaFinanciera) {
        return cuentaFinancieraRepository.save(cuentaFinanciera);
    }

    @Override
    public void eliminar(Long id) {
        cuentaFinancieraRepository.deleteById(id);
    }
}
