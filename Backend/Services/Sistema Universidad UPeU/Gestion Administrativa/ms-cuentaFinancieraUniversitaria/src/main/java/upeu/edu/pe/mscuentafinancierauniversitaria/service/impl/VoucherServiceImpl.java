package upeu.edu.pe.mscuentafinancierauniversitaria.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import upeu.edu.pe.mscuentafinancierauniversitaria.entity.CuentaFinanciera;
import upeu.edu.pe.mscuentafinancierauniversitaria.entity.Voucher;
import upeu.edu.pe.mscuentafinancierauniversitaria.repository.CuentaFinancieraRepository;
import upeu.edu.pe.mscuentafinancierauniversitaria.repository.VoucherRepository;
import upeu.edu.pe.mscuentafinancierauniversitaria.service.VoucherService;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Service
public class VoucherServiceImpl implements VoucherService {

    @Autowired
    private CuentaFinancieraRepository cuentaFinancieraRepository;

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

    @Override
    public List<Voucher> obtenerPorCuentaFinanciera(Long idCuentaFinanciera){
        return voucherRepository.findByCuentaFinancieraIdCuentaFinanciera(idCuentaFinanciera);
    }

    @Override
    public List<Voucher> buscarPorCuentaYAnio(Long idCuentaFinanciera, int anio){
        LocalDate startDate = LocalDate.of(anio, 1, 1);
        LocalDate endDate = LocalDate.of(anio, 12, 31);
        return voucherRepository.findByCuentaFinancieraIdCuentaFinancieraAndFechaDeOperacionBetween(idCuentaFinanciera, startDate, endDate);
    }

    @Override
    public Voucher crearVoucherParaCuentaFinanciera(Long idCuentaFinanciera, Voucher voucher) {
        // Buscar la cuenta financiera por ID
        Optional<CuentaFinanciera> cuentaFinancieraOptional = cuentaFinancieraRepository.findById(idCuentaFinanciera);

        if (cuentaFinancieraOptional.isPresent()) {
            CuentaFinanciera cuentaFinanciera = cuentaFinancieraOptional.get();

            // Asociar el voucher con la cuenta financiera
            voucher.setCuentaFinanciera(cuentaFinanciera);

            // Guardar el voucher
            return voucherRepository.save(voucher);
        } else {
            throw new RuntimeException("Cuenta financiera no encontrada con el ID: " + idCuentaFinanciera);
        }
    }

    @Override
    public List<Voucher> buscarPorEstado(String estado){
        return voucherRepository.findByEstado(estado);
    }
}
