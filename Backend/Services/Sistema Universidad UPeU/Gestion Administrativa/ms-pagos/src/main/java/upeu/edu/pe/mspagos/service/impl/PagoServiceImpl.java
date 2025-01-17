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

        BigDecimal igvPorcentage = BigDecimal.ZERO;
        BigDecimal igv = BigDecimal.ZERO;
        BigDecimal precioVentaTotal = BigDecimal.ZERO;
        BigDecimal operacionGravada = BigDecimal.ZERO;
        BigDecimal operacionInafecta = BigDecimal.ZERO;
        BigDecimal operacionExonerada = BigDecimal.ZERO;
        BigDecimal operacionesGratuitas = BigDecimal.ZERO;

        if ((boleta.getOperacionGravada() != null && boleta.getOperacionGravada().compareTo(BigDecimal.ZERO) > 0) &&
                (boleta.getOperacionInafecta() == null || boleta.getOperacionInafecta().compareTo(BigDecimal.ZERO) == 0) &&
                (boleta.getOperacionExonerada() == null || boleta.getOperacionExonerada().compareTo(BigDecimal.ZERO) == 0) &&
                (boleta.getOperacionGratuita() == null || boleta.getOperacionGratuita().compareTo(BigDecimal.ZERO) == 0)) {

            operacionGravada = valorTotalConDescuento;

            igvPorcentage = BigDecimal.valueOf(18);
            igv = operacionGravada.multiply(igvPorcentage.divide(BigDecimal.valueOf(100), 2, RoundingMode.HALF_UP));

            precioVentaTotal = operacionGravada.add(igv);

        } else if ((boleta.getOperacionInafecta() != null && boleta.getOperacionInafecta().compareTo(BigDecimal.ZERO) > 0) &&
                (boleta.getOperacionGravada() == null || boleta.getOperacionGravada().compareTo(BigDecimal.ZERO) == 0) &&
                (boleta.getOperacionExonerada() == null || boleta.getOperacionExonerada().compareTo(BigDecimal.ZERO) == 0) &&
                (boleta.getOperacionGratuita() == null || boleta.getOperacionGratuita().compareTo(BigDecimal.ZERO) == 0)) {

            operacionInafecta = valorTotalConDescuento;
            precioVentaTotal = operacionInafecta;

        } else if ((boleta.getOperacionExonerada() != null && boleta.getOperacionExonerada().compareTo(BigDecimal.ZERO) > 0) &&
                (boleta.getOperacionGravada() == null || boleta.getOperacionGravada().compareTo(BigDecimal.ZERO) == 0) &&
                (boleta.getOperacionInafecta() == null || boleta.getOperacionInafecta().compareTo(BigDecimal.ZERO) == 0) &&
                (boleta.getOperacionGratuita() == null || boleta.getOperacionGratuita().compareTo(BigDecimal.ZERO) == 0)) {

            operacionExonerada = valorTotalConDescuento;
            precioVentaTotal = operacionExonerada;

        } else if ((boleta.getOperacionGratuita() != null && boleta.getOperacionGratuita().compareTo(BigDecimal.ZERO) > 0) &&
                (boleta.getOperacionGravada() == null || boleta.getOperacionGravada().compareTo(BigDecimal.ZERO) == 0) &&
                (boleta.getOperacionInafecta() == null || boleta.getOperacionInafecta().compareTo(BigDecimal.ZERO) == 0) &&
                (boleta.getOperacionExonerada() == null || boleta.getOperacionExonerada().compareTo(BigDecimal.ZERO) == 0)) {

            operacionesGratuitas = valorTotalConDescuento;
            precioVentaTotal = operacionesGratuitas;

        } else {
            throw new IllegalArgumentException("Solo se puede ingresar una operación. Vuelva a intentarlo.");
        }

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

        BigDecimal igvPorcentage = BigDecimal.ZERO;
        BigDecimal igv = BigDecimal.ZERO;
        BigDecimal precioVentaTotal = BigDecimal.ZERO;
        BigDecimal operacionGravada = BigDecimal.ZERO;
        BigDecimal operacionInafecta = BigDecimal.ZERO;
        BigDecimal operacionExonerada = BigDecimal.ZERO;
        BigDecimal operacionesGratuitas = BigDecimal.ZERO;

        if ((factura.getOperacionGravada() != null && factura.getOperacionGravada().compareTo(BigDecimal.ZERO) > 0) &&
                (factura.getOperacionInafecta() == null || factura.getOperacionInafecta().compareTo(BigDecimal.ZERO) == 0) &&
                (factura.getOperacionExonerada() == null || factura.getOperacionExonerada().compareTo(BigDecimal.ZERO) == 0) &&
                (factura.getOperacionGratuita() == null || factura.getOperacionGratuita().compareTo(BigDecimal.ZERO) == 0)) {

            operacionGravada = valorTotalConDescuento;

            igvPorcentage = BigDecimal.valueOf(18);
            igv = operacionGravada.multiply(igvPorcentage.divide(BigDecimal.valueOf(100), 2, RoundingMode.HALF_UP));

            precioVentaTotal = operacionGravada.add(igv);

        } else if ((factura.getOperacionInafecta() != null && factura.getOperacionInafecta().compareTo(BigDecimal.ZERO) > 0) &&
                (factura.getOperacionGravada() == null || factura.getOperacionGravada().compareTo(BigDecimal.ZERO) == 0) &&
                (factura.getOperacionExonerada() == null || factura.getOperacionExonerada().compareTo(BigDecimal.ZERO) == 0) &&
                (factura.getOperacionGratuita() == null || factura.getOperacionGratuita().compareTo(BigDecimal.ZERO) == 0)) {

            operacionInafecta = valorTotalConDescuento;

            precioVentaTotal = operacionInafecta;

        } else if ((factura.getOperacionExonerada() != null && factura.getOperacionExonerada().compareTo(BigDecimal.ZERO) > 0) &&
                (factura.getOperacionGravada() == null || factura.getOperacionGravada().compareTo(BigDecimal.ZERO) == 0) &&
                (factura.getOperacionInafecta() == null || factura.getOperacionInafecta().compareTo(BigDecimal.ZERO) == 0) &&
                (factura.getOperacionGratuita() == null || factura.getOperacionGratuita().compareTo(BigDecimal.ZERO) == 0)) {

            operacionExonerada = valorTotalConDescuento;

            precioVentaTotal = operacionExonerada;

        } else if ((factura.getOperacionGratuita() != null && factura.getOperacionGratuita().compareTo(BigDecimal.ZERO) > 0) &&
                (factura.getOperacionGravada() == null || factura.getOperacionGravada().compareTo(BigDecimal.ZERO) == 0) &&
                (factura.getOperacionInafecta() == null || factura.getOperacionInafecta().compareTo(BigDecimal.ZERO) == 0) &&
                (factura.getOperacionExonerada() == null || factura.getOperacionExonerada().compareTo(BigDecimal.ZERO) == 0)) {

            operacionesGratuitas = valorTotalConDescuento;
            precioVentaTotal = operacionesGratuitas;

        } else {
            throw new IllegalArgumentException("Solo se puede ingresar una operación. Vuelva a intentarlo.");
        }

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

    private void actualizarDatosBoleta(Boleta boletaExistente, Boleta datosActualizados) {
        BigDecimal cantidadConvertida = BigDecimal.valueOf(datosActualizados.getCantidad());
        BigDecimal valorTotal = datosActualizados.getValorUnitario().multiply(cantidadConvertida);
        BigDecimal valorDescuento = valorTotal.multiply(datosActualizados.getValorDescuento()
                .divide(BigDecimal.valueOf(100), 2, RoundingMode.HALF_UP));
        BigDecimal valorTotalConDescuento = valorTotal.subtract(valorDescuento);

        BigDecimal igvPorcentage = BigDecimal.ZERO;
        BigDecimal igv = BigDecimal.ZERO;
        BigDecimal operacionGravada = BigDecimal.ZERO;
        BigDecimal precioVentaTotal = BigDecimal.ZERO;
        BigDecimal operacionInafecta = BigDecimal.ZERO;
        BigDecimal operacionExonerada = BigDecimal.ZERO;
        BigDecimal operacionesGratuitas = BigDecimal.ZERO;

        if ((datosActualizados.getOperacionGravada() != null && datosActualizados.getOperacionGravada().compareTo(BigDecimal.ZERO) > 0) &&
                (datosActualizados.getOperacionInafecta() == null || datosActualizados.getOperacionInafecta().compareTo(BigDecimal.ZERO) == 0) &&
                (datosActualizados.getOperacionExonerada() == null || datosActualizados.getOperacionExonerada().compareTo(BigDecimal.ZERO) == 0) &&
                (datosActualizados.getOperacionGratuita() == null || datosActualizados.getOperacionGratuita().compareTo(BigDecimal.ZERO) == 0)) {

            operacionGravada = valorTotalConDescuento;

            igvPorcentage = BigDecimal.valueOf(18);
            igv = operacionGravada.multiply(igvPorcentage.divide(BigDecimal.valueOf(100), 2, RoundingMode.HALF_UP));

            precioVentaTotal = operacionGravada.add(igv);

        } else if ((datosActualizados.getOperacionInafecta() != null && datosActualizados.getOperacionInafecta().compareTo(BigDecimal.ZERO) > 0) &&
                (datosActualizados.getOperacionGravada() == null || datosActualizados.getOperacionGravada().compareTo(BigDecimal.ZERO) == 0) &&
                (datosActualizados.getOperacionExonerada() == null || datosActualizados.getOperacionExonerada().compareTo(BigDecimal.ZERO) == 0) &&
                (datosActualizados.getOperacionGratuita() == null || datosActualizados.getOperacionGratuita().compareTo(BigDecimal.ZERO) == 0)) {

            operacionInafecta = valorTotalConDescuento;
            precioVentaTotal = operacionInafecta;

        } else if ((datosActualizados.getOperacionExonerada() != null && datosActualizados.getOperacionExonerada().compareTo(BigDecimal.ZERO) > 0) &&
                (datosActualizados.getOperacionGravada() == null || datosActualizados.getOperacionGravada().compareTo(BigDecimal.ZERO) == 0) &&
                (datosActualizados.getOperacionInafecta() == null || datosActualizados.getOperacionInafecta().compareTo(BigDecimal.ZERO) == 0) &&
                (datosActualizados.getOperacionGratuita() == null || datosActualizados.getOperacionGratuita().compareTo(BigDecimal.ZERO) == 0)) {

            operacionExonerada = valorTotalConDescuento;
            precioVentaTotal = operacionExonerada;

        } else if ((datosActualizados.getOperacionGratuita() != null && datosActualizados.getOperacionGratuita().compareTo(BigDecimal.ZERO) > 0) &&
                (datosActualizados.getOperacionGravada() == null || datosActualizados.getOperacionGravada().compareTo(BigDecimal.ZERO) == 0) &&
                (datosActualizados.getOperacionInafecta() == null || datosActualizados.getOperacionInafecta().compareTo(BigDecimal.ZERO) == 0) &&
                (datosActualizados.getOperacionExonerada() == null || datosActualizados.getOperacionExonerada().compareTo(BigDecimal.ZERO) == 0)) {

            operacionesGratuitas = valorTotalConDescuento;
            precioVentaTotal = operacionesGratuitas;

        } else {
            throw new IllegalArgumentException("Solo se puede ingresar una operación. Vuelva a intentarlo.");
        }

        // Asignar los valores actualizados a la boleta existente
        boletaExistente.setNombreCliente(datosActualizados.getNombreCliente());
        boletaExistente.setDocumentoDeIdentidad(datosActualizados.getDocumentoDeIdentidad());
        boletaExistente.setDireccion(datosActualizados.getDireccion());
        boletaExistente.setNumeroBoleta(datosActualizados.getNumeroBoleta());
        boletaExistente.setFechaEmision(datosActualizados.getFechaEmision());
        boletaExistente.setDescripcionBoleta(datosActualizados.getDescripcionBoleta());
        boletaExistente.setTipoDocumento(datosActualizados.getTipoDocumento());
        boletaExistente.setSucursal(datosActualizados.getSucursal());
        boletaExistente.setOrganizacionDeVentas(datosActualizados.getOrganizacionDeVentas());
        boletaExistente.setTipoMoneda(datosActualizados.getTipoMoneda());
        boletaExistente.setCodigoProductoServicio(datosActualizados.getCodigoProductoServicio());
        boletaExistente.setDescripcionProductoServicio(datosActualizados.getDescripcionProductoServicio());
        boletaExistente.setUnidadDeMedida(datosActualizados.getUnidadDeMedida());

        boletaExistente.setCantidad(datosActualizados.getCantidad());
        boletaExistente.setValorUnitario(datosActualizados.getValorUnitario());
        boletaExistente.setValorDescuento(valorDescuento);
        boletaExistente.setValorTotal(valorTotalConDescuento);
        boletaExistente.setOperacionGravada(operacionGravada);
        boletaExistente.setDescuentosTotales(valorDescuento);
        boletaExistente.setIgv(igv);
        boletaExistente.setPrecioVentaTotal(precioVentaTotal);
        boletaExistente.setDescripcionBoleta(datosActualizados.getDescripcionBoleta());
    }

    private void actualizarDatosFactura(Factura facturaExistente, Factura datosActualizados) {
        BigDecimal cantidadConvertida = BigDecimal.valueOf(datosActualizados.getCantidad());
        BigDecimal valorTotal = datosActualizados.getValorUnitario().multiply(cantidadConvertida);
        BigDecimal valorDescuento = valorTotal.multiply(datosActualizados.getValorDescuento()
                .divide(BigDecimal.valueOf(100), 2, RoundingMode.HALF_UP));
        BigDecimal valorTotalConDescuento = valorTotal.subtract(valorDescuento);

        BigDecimal igvPorcentage = BigDecimal.ZERO;
        BigDecimal igv = BigDecimal.ZERO;
        BigDecimal precioVentaTotal = BigDecimal.ZERO;
        BigDecimal operacionGravada = BigDecimal.ZERO;
        BigDecimal operacionInafecta = BigDecimal.ZERO;
        BigDecimal operacionExonerada = BigDecimal.ZERO;
        BigDecimal operacionesGratuitas = BigDecimal.ZERO;


        if ((datosActualizados.getOperacionGravada() != null && datosActualizados.getOperacionGravada().compareTo(BigDecimal.ZERO) > 0) &&
                (datosActualizados.getOperacionInafecta() == null || datosActualizados.getOperacionInafecta().compareTo(BigDecimal.ZERO) == 0) &&
                (datosActualizados.getOperacionExonerada() == null || datosActualizados.getOperacionExonerada().compareTo(BigDecimal.ZERO) == 0) &&
                (datosActualizados.getOperacionGratuita() == null || datosActualizados.getOperacionGratuita().compareTo(BigDecimal.ZERO) == 0)) {

            operacionGravada = valorTotalConDescuento;

            igvPorcentage = BigDecimal.valueOf(18);
            igv = operacionGravada.multiply(igvPorcentage.divide(BigDecimal.valueOf(100), 2, RoundingMode.HALF_UP));

            precioVentaTotal = operacionGravada.add(igv);
        } else if ((datosActualizados.getOperacionInafecta() != null && datosActualizados.getOperacionInafecta().compareTo(BigDecimal.ZERO) > 0) &&
                (datosActualizados.getOperacionGravada() == null || datosActualizados.getOperacionGravada().compareTo(BigDecimal.ZERO) == 0) &&
                (datosActualizados.getOperacionExonerada() == null || datosActualizados.getOperacionExonerada().compareTo(BigDecimal.ZERO) == 0) &&
                (datosActualizados.getOperacionGratuita() == null || datosActualizados.getOperacionGratuita().compareTo(BigDecimal.ZERO) == 0)) {

            operacionInafecta = valorTotalConDescuento;

            precioVentaTotal = operacionInafecta;

        } else if ((datosActualizados.getOperacionExonerada() != null && datosActualizados.getOperacionExonerada().compareTo(BigDecimal.ZERO) > 0) &&
                (datosActualizados.getOperacionGravada() == null || datosActualizados.getOperacionGravada().compareTo(BigDecimal.ZERO) == 0) &&
                (datosActualizados.getOperacionInafecta() == null || datosActualizados.getOperacionInafecta().compareTo(BigDecimal.ZERO) == 0) &&
                (datosActualizados.getOperacionGratuita() == null || datosActualizados.getOperacionGratuita().compareTo(BigDecimal.ZERO) == 0)) {

            operacionExonerada = valorTotalConDescuento;

            precioVentaTotal = operacionExonerada;

        } else if ((datosActualizados.getOperacionGratuita() != null && datosActualizados.getOperacionGratuita().compareTo(BigDecimal.ZERO) > 0) &&
                (datosActualizados.getOperacionGravada() == null || datosActualizados.getOperacionGravada().compareTo(BigDecimal.ZERO) == 0) &&
                (datosActualizados.getOperacionInafecta() == null || datosActualizados.getOperacionInafecta().compareTo(BigDecimal.ZERO) == 0) &&
                (datosActualizados.getOperacionExonerada() == null || datosActualizados.getOperacionExonerada().compareTo(BigDecimal.ZERO) == 0)) {

            operacionesGratuitas = valorTotalConDescuento;
            precioVentaTotal = operacionesGratuitas;

        } else {
            throw new IllegalArgumentException("Solo se puede ingresar una operación. Vuelva a intentarlo.");
        }

        // Asignar los valores actualizados a la factura existente
        facturaExistente.setNombreCliente(datosActualizados.getNombreCliente());
        facturaExistente.setDocumentoDeIdentidad(datosActualizados.getDocumentoDeIdentidad());
        facturaExistente.setDireccion(datosActualizados.getDireccion());
        facturaExistente.setNumeroFactura(datosActualizados.getNumeroFactura());
        facturaExistente.setFechaEmision(datosActualizados.getFechaEmision());
        facturaExistente.setDescripcionFactura(datosActualizados.getDescripcionFactura());
        facturaExistente.setTipoDocumento(datosActualizados.getTipoDocumento());
        facturaExistente.setSucursal(datosActualizados.getSucursal());
        facturaExistente.setOrganizacionDeVentas(datosActualizados.getOrganizacionDeVentas());
        facturaExistente.setTipoMoneda(datosActualizados.getTipoMoneda());
        facturaExistente.setEstadoFactura(datosActualizados.getEstadoFactura());
        facturaExistente.setCodigoProductoServicio(datosActualizados.getCodigoProductoServicio());
        facturaExistente.setDescripcionProductoServicio(datosActualizados.getDescripcionProductoServicio());
        facturaExistente.setUnidadDeMedida(datosActualizados.getUnidadDeMedida());

        facturaExistente.setCantidad(datosActualizados.getCantidad());
        facturaExistente.setValorUnitario(datosActualizados.getValorUnitario());
        facturaExistente.setValorDescuento(valorDescuento);
        facturaExistente.setValorTotal(valorTotalConDescuento);
        facturaExistente.setOperacionGravada(operacionGravada);
        facturaExistente.setDescuentosTotales(valorDescuento);
        facturaExistente.setIgv(igv);
        facturaExistente.setPrecioVentaTotal(precioVentaTotal);
        facturaExistente.setDescripcionFactura(datosActualizados.getDescripcionFactura());
    }

    @Override
    @Transactional
    public void actualizarPagoConComprobante(Long idPago, PagoRequest pagoRequest) {
        // Buscar el pago existente o lanzar una excepción si no se encuentra
        Pago pagoExistente = pagoRepository.findById(idPago)
                .orElseThrow(() -> new IllegalArgumentException("No se encontró el pago con ID " + idPago));

        // Actualizar datos del pago
        Pago datosPagoActualizados = pagoRequest.getPago();
        pagoExistente.setMontoTotal(datosPagoActualizados.getMontoTotal());
        pagoExistente.setMetodoDePago(datosPagoActualizados.getMetodoDePago());
        pagoExistente.setMedioDePago(datosPagoActualizados.getMedioDePago());
        pagoExistente.setEstado(datosPagoActualizados.getEstado());
        pagoExistente.setDescripcion(datosPagoActualizados.getDescripcion());
        pagoExistente.setIdEstudiante(datosPagoActualizados.getIdEstudiante());
        pagoExistente.setFechaPago(datosPagoActualizados.getFechaPago());

        // Determinar si el pago tiene boleta o factura y actualizar según corresponda
        if (pagoExistente.getBoleta() != null) {
            // Actualizar boleta
            Boleta boletaExistente = pagoExistente.getBoleta();
            Boleta datosBoletaActualizados = pagoRequest.getBoleta();

            actualizarDatosBoleta(boletaExistente, datosBoletaActualizados);
            boletaRepository.save(boletaExistente);

            // Generar PDF de la boleta
            try {
                pdfService.generarPdfBoleta(boletaExistente);
            } catch (Exception e) {
                e.printStackTrace();
            }
        } else if (pagoExistente.getFactura() != null) {
            // Actualizar factura
            Factura facturaExistente = pagoExistente.getFactura();
            Factura datosFacturaActualizados = pagoRequest.getFactura();

            actualizarDatosFactura(facturaExistente, datosFacturaActualizados);
            facturaRepository.save(facturaExistente);

            // Generar PDF de la factura
            try {
                pdfService.generarPdfFactura(facturaExistente);
            } catch (Exception e) {
                e.printStackTrace();
            }
        } else {
            throw new IllegalArgumentException("El pago con ID " + idPago + " no tiene ni boleta ni factura asociada.");
        }

        // Guardar los cambios en el pago
        pagoRepository.save(pagoExistente);
    }


    @Override
    public void eliminarPago(Long id) {
        // Buscar el pago en la base de datos
        Pago pagoEncontrado = pagoRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("El pago con ID " + id + " no existe"));

        // Verificar y eliminar comprobantes asociados
        if (pagoEncontrado.getBoleta() != null) {
            boletaRepository.deleteById(pagoEncontrado.getBoleta().getIdBoleta());
        }

        if (pagoEncontrado.getFactura() != null) {
            facturaRepository.deleteById(pagoEncontrado.getFactura().getIdFactura());
        }

        // Eliminar el pago
        pagoRepository.deleteById(id);

        // Registrar acción
        System.out.println("Pago con ID " + id + " y sus comprobantes asociados han sido eliminados correctamente.");
    }
}