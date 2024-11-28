package upeu.edu.pe.mspagos.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import upeu.edu.pe.mspagos.entity.Pago;
import upeu.edu.pe.mspagos.repository.PagoRepository;
import upeu.edu.pe.mspagos.service.PagoService;

import java.util.List;

@Service
public class PagoServiceImpl implements PagoService {
    @Autowired
    private PagoRepository pagoRepository;

    @Override
    public Pago guardarPago(Pago pago) {
        return pagoRepository.save(pago);
    }

    @Override
    public List<Pago> listarPago(){
        return pagoRepository.findAll();
    }

    @Override
    public Pago buscarPagoPorId(Long id){
        return pagoRepository.findById(id).get();
    }

    @Override
    public Pago editarPago(Pago pago) {
        return pagoRepository.save(pago);
    }

    @Override
    public void eliminarPago(Long id){
        pagoRepository.deleteById(id);
    }
}
