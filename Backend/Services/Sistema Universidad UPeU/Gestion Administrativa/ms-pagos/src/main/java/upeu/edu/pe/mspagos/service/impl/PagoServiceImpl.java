package upeu.edu.pe.mspagos.service.impl;

import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import upeu.edu.pe.mspagos.entity.*;
import upeu.edu.pe.mspagos.repository.BoletaRepository;
import upeu.edu.pe.mspagos.repository.FacturaRepository;
import upeu.edu.pe.mspagos.repository.PagoRepository;
import upeu.edu.pe.mspagos.service.PagoService;
import upeu.edu.pe.mspagos.service.PdfService;

import java.math.BigDecimal;
import java.math.RoundingMode;
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

    @Override
    public boolean isOtherOperationsZeroBoleta(Boleta boleta) {
        return (boleta.getOperacionGravada() == null || boleta.getOperacionGravada().compareTo(BigDecimal.ZERO) == 0) &&
                (boleta.getOperacionInafecta() == null || boleta.getOperacionInafecta().compareTo(BigDecimal.ZERO) == 0) &&
                (boleta.getOperacionExonerada() == null || boleta.getOperacionExonerada().compareTo(BigDecimal.ZERO) == 0) &&
                (boleta.getOperacionGratuita() == null || boleta.getOperacionGratuita().compareTo(BigDecimal.ZERO) == 0);
    }

    @Override
    @Transactional
    public void crearPagoConBoleta(PagoBoletaRequest pagoBoletaRequest) {

        Pago pago = pagoBoletaRequest.getPago();
        Boleta boleta = pagoBoletaRequest.getBoleta();

        pagoRepository.save(pago);

        //Calculos
        BigDecimal cantidadConvertida = BigDecimal.valueOf(boleta.getCantidad());

        BigDecimal valorTotal = (boleta.getValorUnitario()).multiply(cantidadConvertida);
        BigDecimal valorDescuento = valorTotal.multiply((boleta.getValorDescuento()).divide(BigDecimal.valueOf(100), 2, RoundingMode.HALF_UP));
        BigDecimal valorTotalConDescuento = valorTotal.subtract(valorDescuento);

        BigDecimal operacionGravada = BigDecimal.ZERO;
        BigDecimal operacionInafecta = BigDecimal.ZERO;
        BigDecimal operacionExonerada = BigDecimal.ZERO;
        BigDecimal operacionesGratuitas = BigDecimal.ZERO;

        if (boleta.getOperacionGravada() != null &&
                boleta.getOperacionGravada().compareTo(BigDecimal.ZERO) > 0 &&
                isOtherOperationsZeroBoleta(boleta)) {

            operacionGravada = boleta.getOperacionGravada();

        } else if (boleta.getOperacionInafecta() != null &&
                boleta.getOperacionInafecta().compareTo(BigDecimal.ZERO) > 0 &&
                isOtherOperationsZeroBoleta(boleta)) {

            operacionInafecta = boleta.getOperacionInafecta();

        } else if (boleta.getOperacionExonerada() != null &&
                boleta.getOperacionExonerada().compareTo(BigDecimal.ZERO) > 0 &&
                isOtherOperationsZeroBoleta(boleta)) {

            operacionExonerada = boleta.getOperacionExonerada();

        } else if (boleta.getOperacionGratuita() != null &&
                boleta.getOperacionGratuita().compareTo(BigDecimal.ZERO) > 0 &&
                isOtherOperationsZeroBoleta(boleta)) {

            operacionesGratuitas = boleta.getOperacionGratuita();

        } else {
            throw new IllegalArgumentException("Solo se puede ingresar una operación. Vuelva a intentarlo.");
        }


        BigDecimal igvPorcentage = BigDecimal.valueOf(18);
        BigDecimal igv = operacionGravada.multiply(igvPorcentage.divide(BigDecimal.valueOf(100), 2, RoundingMode.HALF_UP));

        BigDecimal precioVentaTotal = operacionGravada.add(igv);

        //Guardar los calculos
        boleta.setValorDescuento(valorDescuento);
        boleta.setValorTotal(valorTotalConDescuento);
        boleta.setOperacionGravada(operacionGravada);
        boleta.setOperacionInafecta(operacionInafecta);
        boleta.setOperacionExonerada(operacionExonerada);
        boleta.setOperacionGratuita(operacionesGratuitas);
        boleta.setDescuentosTotales(valorDescuento);
        boleta.setIgv(igv);
        boleta.setPrecioVentaTotal(precioVentaTotal);

        boleta.setPago(pago);
        boleta.setBoletaUrl("src/main/resources/static/boleta_" + boleta.getNumeroBoleta() + ".pdf");
        boletaRepository.save(boleta);

        // Generar el PDF de la boleta
        try {
            pdfService.generarPdfBoleta(boleta);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public boolean isOtherOperationsZeroFactura(Factura factura) {
        return (factura.getOperacionGravada() == null || factura.getOperacionGravada().compareTo(BigDecimal.ZERO) == 0) &&
                (factura.getOperacionInafecta() == null || factura.getOperacionInafecta().compareTo(BigDecimal.ZERO) == 0) &&
                (factura.getOperacionExonerada() == null || factura.getOperacionExonerada().compareTo(BigDecimal.ZERO) == 0) &&
                (factura.getOperacionGratuita() == null || factura.getOperacionGratuita().compareTo(BigDecimal.ZERO) == 0);
    }

    @Override
    @Transactional
    public void crearPagoConFactura(PagoFacturaRequest pagoFacturaRequest) {
        Pago pago = pagoFacturaRequest.getPago();
        Factura factura = pagoFacturaRequest.getFactura();

        pagoRepository.save(pago);

        //Calculos
        BigDecimal cantidadConvertida = BigDecimal.valueOf(factura.getCantidad());

        BigDecimal valorTotal = (factura.getValorUnitario()).multiply(cantidadConvertida);
        BigDecimal valorDescuento = valorTotal.multiply((factura.getValorDescuento()).divide(BigDecimal.valueOf(100), 2, RoundingMode.HALF_UP));
        BigDecimal valorTotalConDescuento = valorTotal.subtract(valorDescuento);

        BigDecimal operacionGravada = BigDecimal.ZERO;
        BigDecimal operacionInafecta = BigDecimal.ZERO;
        BigDecimal operacionExonerada = BigDecimal.ZERO;
        BigDecimal operacionesGratuitas = BigDecimal.ZERO;

        if (factura.getOperacionGravada() != null &&
                factura.getOperacionGravada().compareTo(BigDecimal.ZERO) > 0 &&
                isOtherOperationsZeroFactura(factura)) {

            operacionGravada = factura.getOperacionGravada();

        } else if (factura.getOperacionInafecta() != null &&
                factura.getOperacionInafecta().compareTo(BigDecimal.ZERO) > 0 &&
                isOtherOperationsZeroFactura(factura)) {

            operacionInafecta = factura.getOperacionInafecta();

        } else if (factura.getOperacionExonerada() != null &&
                factura.getOperacionExonerada().compareTo(BigDecimal.ZERO) > 0 &&
                isOtherOperationsZeroFactura(factura)) {

            operacionExonerada = factura.getOperacionExonerada();

        } else if (factura.getOperacionGratuita() != null &&
                factura.getOperacionGratuita().compareTo(BigDecimal.ZERO) > 0 &&
                isOtherOperationsZeroFactura(factura)) {

            operacionesGratuitas = factura.getOperacionGratuita();

        } else {
            throw new IllegalArgumentException("Solo se puede ingresar una operación. Vuelva a intentarlo.");
        }


        BigDecimal igvPorcentage = BigDecimal.valueOf(18);
        BigDecimal igv = operacionGravada.multiply(igvPorcentage.divide(BigDecimal.valueOf(100), 2, RoundingMode.HALF_UP));

        BigDecimal precioVentaTotal = operacionGravada.add(igv);

        //Guardar los calculos
        factura.setValorDescuento(valorDescuento);
        factura.setValorTotal(valorTotalConDescuento);
        factura.setOperacionGravada(operacionGravada);
        factura.setOperacionInafecta(operacionInafecta);
        factura.setOperacionExonerada(operacionExonerada);
        factura.setOperacionGratuita(operacionesGratuitas);
        factura.setDescuentosTotales(valorDescuento);
        factura.setIgv(igv);
        factura.setPrecioVentaTotal(precioVentaTotal);

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