package upeu.edu.pe.mscuentafinancierauniversitaria.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import upeu.edu.pe.mscuentafinancierauniversitaria.entity.Voucher;
import upeu.edu.pe.mscuentafinancierauniversitaria.repository.VoucherRepository;
import upeu.edu.pe.mscuentafinancierauniversitaria.service.VoucherService;

import java.util.List;

@Service
public class VoucherServiceImpl implements VoucherService {

    @Autowired
    private VoucherRepository voucherRepository;

    @Override
    public List<Voucher> listarTodos() {
        return voucherRepository.findAll();
    }

    @Override
    public Voucher obtenerPorId(Long id) {
        return voucherRepository.findById(id).get();
    }

    @Override
    public Voucher crear(Voucher voucher) {
        return voucherRepository.save(voucher);
    }

    @Override
    public Voucher actualizar(Voucher voucher) {
        return voucherRepository.save(voucher);
    }

    @Override
    public void eliminar(Long id) {
        voucherRepository.deleteById(id);
    }
}