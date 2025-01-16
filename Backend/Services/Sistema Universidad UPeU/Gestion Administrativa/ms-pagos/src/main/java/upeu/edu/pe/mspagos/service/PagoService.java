package upeu.edu.pe.mspagos.service;

import upeu.edu.pe.mspagos.entity.*;

import java.util.List;

public interface PagoService {
    public Pago guardarPago(Pago pago);

    public List<Pago> listarPago();

    public Pago buscarPagoPorId(Long id);

    public Pago editarPago(Pago pago);

    public void eliminarPago(Long id);

    public boolean isOtherOperationsZeroBoleta(Boleta boleta);

    public void crearPagoConBoleta(PagoBoletaRequest pagoBoletaRequest);

    public boolean isOtherOperationsZeroFactura(Factura factura);

    public void crearPagoConFactura(PagoFacturaRequest pagoFacturaRequest);
}
