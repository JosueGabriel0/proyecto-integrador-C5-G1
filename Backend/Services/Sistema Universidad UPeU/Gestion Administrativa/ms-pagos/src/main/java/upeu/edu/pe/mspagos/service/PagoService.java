package upeu.edu.pe.mspagos.service;

import upeu.edu.pe.mspagos.entity.*;

import java.util.List;

public interface PagoService {
    public Pago guardarPago(Pago pago);

    public Pago editarPago(Pago pago);

    public List<Pago> listarPago();

    public Pago buscarPagoPorId(Long id);

    public void crearPagoConBoleta(PagoBoletaRequest pagoBoletaRequest);

    public void crearPagoConFactura(PagoFacturaRequest pagoFacturaRequest);

    public void actualizarPagoConComprobante(Long idPago, PagoRequest pagoRequest);

    public void eliminarPago(Long id);
}
