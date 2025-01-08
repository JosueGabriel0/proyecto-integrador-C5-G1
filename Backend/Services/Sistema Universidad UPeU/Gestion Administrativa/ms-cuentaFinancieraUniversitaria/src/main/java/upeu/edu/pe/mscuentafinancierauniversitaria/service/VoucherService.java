package upeu.edu.pe.mscuentafinancierauniversitaria.service;

import upeu.edu.pe.mscuentafinancierauniversitaria.entity.Voucher;

import java.util.List;

public interface VoucherService {
    public List<Voucher> listarTodos();
    public Voucher obtenerPorId(Long id);
    public Voucher crear(Voucher voucher);
    public Voucher actualizar(Voucher voucher);
    public void eliminar(Long id);
    public List<Voucher> listarPorAnio(int anio);
}
