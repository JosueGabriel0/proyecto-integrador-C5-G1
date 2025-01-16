package upeu.edu.pe.mspagos.loaders;

import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
import upeu.edu.pe.mspagos.entity.*;
import upeu.edu.pe.mspagos.repository.*;

import java.math.BigDecimal;
import java.time.LocalDate;

@Component
public class PagoDataLoader implements CommandLineRunner {

    private final PagoRepository pagoRepository;
    private final BoletaRepository boletaRepository;
    private final FacturaRepository facturaRepository;

    public PagoDataLoader(
            PagoRepository pagoRepository,
            BoletaRepository boletaRepository,
            FacturaRepository facturaRepository) {
        this.pagoRepository = pagoRepository;
        this.boletaRepository = boletaRepository;
        this.facturaRepository = facturaRepository;
    }

    @Override
    public void run(String... args) throws Exception {
        if (pagoRepository.count() == 0) {
            // Crear datos de Pago
            Pago pago1 = new Pago();
            pago1.setMontoTotal(BigDecimal.valueOf(1200.00));
            pago1.setMetodoDePago("Tarjeta");
            pago1.setMedioDePago("Al contado");
            pago1.setEstado("Pagado");
            pago1.setDescripcion("Pago de matrícula");
            pago1.setIdEstudiante(1L);
            pago1.setFechaPago(LocalDate.now());
            pagoRepository.save(pago1);

            // Crear datos de Boleta
            Boleta boleta1 = new Boleta();
            boleta1.setPago(pago1);
            boleta1.setNombreCliente("Juan Pérez");
            boleta1.setDocumentoDeIdentidad("12345678");
            boleta1.setDireccion("Av. Siempre Viva 742");
            boleta1.setNumeroBoleta("B0001");
            boleta1.setFechaEmision(LocalDate.now());
            boleta1.setDescripcionBoleta("Pago de matrícula");
            boleta1.setTipoDocumento("DNI");
            boleta1.setSucursal("Sucursal Lima");
            boleta1.setOrganizacionDeVentas("Universidad");
            boleta1.setTipoMoneda("PEN");

            boleta1.setCodigoProductoServicio("MAT001");
            boleta1.setDescripcionProductoServicio("Matrícula universitaria");
            boleta1.setUnidadDeMedida("Servicio");
            boleta1.setCantidad(1);
            boleta1.setValorUnitario(BigDecimal.valueOf(1000.00));
            boleta1.setValorDescuento(BigDecimal.valueOf(0.00));
            boleta1.setValorTotal(BigDecimal.valueOf(1000.00));

            boleta1.setOperacionGravada(BigDecimal.valueOf(1000.00));
            boleta1.setOperacionInafecta(BigDecimal.valueOf(0.00));
            boleta1.setOperacionExonerada(BigDecimal.valueOf(0.00));
            boleta1.setOperacionGratuita(BigDecimal.valueOf(0.00));
            boleta1.setDescuentosTotales(BigDecimal.valueOf(0.00));
            boleta1.setIgv(BigDecimal.valueOf(200.00));
            boleta1.setPrecioVentaTotal(BigDecimal.valueOf(1200.00));
            boleta1.setBoletaUrl("http://localhost/boletas/B0001.pdf");
            boletaRepository.save(boleta1);

            // Crear datos de Factura
            Factura factura1 = new Factura();
            factura1.setPago(pago1);
            factura1.setNombreCliente("Empresa XYZ");
            factura1.setDocumentoDeIdentidad("20123456789");
            factura1.setDireccion("Av. Empresarial 123");
            factura1.setNumeroFactura("F0001");
            factura1.setFechaEmision(LocalDate.now());
            factura1.setDescripcionFactura("Pago de matrícula corporativa");
            factura1.setTipoDocumento("RUC");
            factura1.setSucursal("Sucursal Lima");
            factura1.setOrganizacionDeVentas("Universidad");
            factura1.setTipoMoneda("PEN");
            factura1.setEstadoFactura("Emitida");

            factura1.setCodigoProductoServicio("MAT002");
            factura1.setDescripcionProductoServicio("Matrícula corporativa");
            factura1.setUnidadDeMedida("Servicio");
            factura1.setCantidad(1);
            factura1.setValorUnitario(BigDecimal.valueOf(2000.00));
            factura1.setValorDescuento(BigDecimal.valueOf(0.00));
            factura1.setValorTotal(BigDecimal.valueOf(2000.00));

            factura1.setOperacionGravada(BigDecimal.valueOf(2000.00));
            factura1.setOperacionInafecta(BigDecimal.valueOf(0.00));
            factura1.setOperacionExonerada(BigDecimal.valueOf(0.00));
            factura1.setOperacionGratuita(BigDecimal.valueOf(0.00));
            factura1.setDescuentosTotales(BigDecimal.valueOf(0.00));
            factura1.setIgv(BigDecimal.valueOf(360.00));
            factura1.setPrecioVentaTotal(BigDecimal.valueOf(2360.00));
            factura1.setFacturaUrl("http://localhost/facturas/F0001.pdf");
            facturaRepository.save(factura1);

            System.out.println("Datos de ejemplo para pagos, boletas y facturas cargados en la base de datos.");
        } else {
            System.out.println("Los datos de pagos, boletas y facturas ya están cargados en la base de datos.");
        }
    }
}