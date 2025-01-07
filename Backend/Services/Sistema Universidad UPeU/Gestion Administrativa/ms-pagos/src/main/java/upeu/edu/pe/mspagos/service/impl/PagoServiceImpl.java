package upeu.edu.pe.mspagos.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import upeu.edu.pe.mspagos.entity.*;
import upeu.edu.pe.mspagos.repository.BoletaRepository;
import upeu.edu.pe.mspagos.repository.FacturaRepository;
import upeu.edu.pe.mspagos.repository.PagoRepository;
import upeu.edu.pe.mspagos.service.PagoService;
import upeu.edu.pe.mspagos.service.PdfService;

import java.util.List;

@Service
public class PagoServiceImpl implements PagoService {
    @Autowired
    private PagoRepository pagoRepository;

    @Autowired
    private PdfService pdfService;
    @Autowired
    private BoletaRepository boletaRepository;
    @Autowired
    private FacturaRepository facturaRepository;

    @Override
    public Pago guardarPago(Pago pago) {
        return pagoRepository.save(pago);
    }

    @Override
    public List<Pago> listarPago() {
        return pagoRepository.findAll();
    }

    @Override
    public Pago buscarPagoPorId(Long id) {
        return pagoRepository.findById(id).orElse(null);
    }

    @Override
    public Pago editarPago(Pago pago) {
        return pagoRepository.save(pago);
    }

    @Override
    public void eliminarPago(Long id) {
        pagoRepository.deleteById(id);
    }

    // Método para crear pago con boleta
    public void crearPagoConBoleta(PagoBoletaRequest pagoBoletaRequest) {

        Pago pago = pagoBoletaRequest.getPago();
        Boleta boleta = pagoBoletaRequest.getBoleta();

        // Guardar el pago y la boleta
        pagoRepository.save(pago);
        boleta.setBoletaUrl("src/main/resources/static/boleta_" + boleta.getNumeroBoleta() + ".pdf");
        boletaRepository.save(boleta);

        boleta.setPago(pago);

        // Generar el PDF de la boleta
        try {
            pdfService.generarPdfBoleta(boleta);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // Método para crear pago con factura
    public void crearPagoConFactura(PagoFacturaRequest pagoFacturaRequest) {
        Pago pago = pagoFacturaRequest.getPago();
        Factura factura = pagoFacturaRequest.getFactura();

        pagoRepository.save(pago);

        factura.setPago(pago);
        factura.setFacturaUrl("src/main/resources/static/factura_" + factura.getNumeroFactura() + ".pdf");
        facturaRepository.save(factura);

        // Generar el PDF de la factura
        try {
            pdfService.generarPdfFactura(factura);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}