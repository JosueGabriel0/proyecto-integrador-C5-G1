package upeu.edu.pe.mspagos.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import upeu.edu.pe.mspagos.entity.Boleta;
import upeu.edu.pe.mspagos.entity.Factura;
import upeu.edu.pe.mspagos.entity.Pago;
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
    public void crearPagoConBoleta(Boleta boleta) {
        // Crear el pago (suponiendo que el pago tiene un ID de estudiante o algo similar)
        Pago pago = new Pago();
        pago.setMontoTotal(boleta.getPrecioVentaTotal());
        pago.setMedioDePago("Efectivo"); // O el método de pago correspondiente
        pago.setDescripcion("Pago por boleta de " + boleta.getNumeroBoleta());
        pago.setEstado("Pagado");
        pago.setFechaPago(boleta.getFechaEmision());

        // Relacionar la boleta con el pago
        boleta.setPago(pago);

        // Guardar el pago y la boleta
        pagoRepository.save(pago);

        // Generar el PDF de la boleta
        try {
            pdfService.generarPdfBoleta(boleta);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // Método para crear pago con factura
    public void crearPagoConFactura(Factura factura) {
        // Crear el pago (suponiendo que el pago tiene un ID de estudiante o algo similar)
        Pago pago = new Pago();
        //pago.setMonto(factura.getTotal());
        //pago.setMoneda("S/."); // O la moneda que se corresponda
        //pago.setMetodoPago("Tarjeta"); // O el método de pago correspondiente
        pago.setDescripcion("Pago por factura de " + factura.getNumeroFactura());
        pago.setEstado("Pagado");
        pago.setFechaPago(factura.getFechaEmision());

        // Relacionar la factura con el pago
        factura.setPago(pago);

        // Guardar el pago y la factura
        pagoRepository.save(pago);

        // Generar el PDF de la factura
        try {
            pdfService.generarPdfFactura(factura);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}