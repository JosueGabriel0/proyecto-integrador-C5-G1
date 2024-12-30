package upeu.edu.pe.mspagos.loaders;

import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
import upeu.edu.pe.mspagos.entity.*;
import upeu.edu.pe.mspagos.repository.*;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

@Component
public class PagoDataLoader implements CommandLineRunner {

    private final PagoRepository pagoRepository;
    private final BecaRepository becaRepository;
    private final BoletaRepository boletaRepository;
    private final DeudaRepository deudaRepository;
    private final FacturaRepository facturaRepository;
    private final PlanDePagoRepository planDePagoRepository;
    private final TransaccionRepository transaccionRepository;

    public PagoDataLoader(PagoRepository pagoRepository,
                          BecaRepository becaRepository,
                          BoletaRepository boletaRepository,
                          DeudaRepository deudaRepository,
                          FacturaRepository facturaRepository,
                          PlanDePagoRepository planDePagoRepository,
                          TransaccionRepository transaccionRepository) {
        this.pagoRepository = pagoRepository;
        this.becaRepository = becaRepository;
        this.boletaRepository = boletaRepository;
        this.deudaRepository = deudaRepository;
        this.facturaRepository = facturaRepository;
        this.planDePagoRepository = planDePagoRepository;
        this.transaccionRepository = transaccionRepository;
    }

    @Override
    public void run(String... args) throws Exception {
        if (pagoRepository.count() == 0) {
            // Cargar datos de ejemplo para las entidades relacionadas con pagos

            // Crear datos de Beca
            Beca beca = new Beca();
            beca.setTipoBeca("Beca Completa");
            beca.setMontoDescuento(BigDecimal.valueOf(5000));
            beca.setFechaInicio(LocalDate.now().minusMonths(1));
            beca.setFechaFin(LocalDate.now().plusMonths(11));
            beca.setIdEstudiante(1L);
            becaRepository.save(beca);

            // Crear datos de Pago
            Pago pago = new Pago();
            pago.setMonto(BigDecimal.valueOf(1500));
            pago.setMoneda("PEN");
            pago.setMetodoPago("Tarjeta");
            pago.setDescripcion("Pago inicial de matrícula");
            pago.setEstado("Pagado");
            pago.setIdEstudiante(1L);
            pago.setFechaPago(LocalDate.now());
            pagoRepository.save(pago);

            // Crear datos de Factura
            Factura factura = new Factura();
            factura.setPago(pago);
            factura.setNumeroFactura("F123456");
            factura.setFechaEmision(LocalDate.now());
            factura.setDescripcion("Factura del pago inicial");
            factura.setImpuestos(BigDecimal.valueOf(270));
            factura.setSubtotal(BigDecimal.valueOf(1230));
            factura.setTotal(BigDecimal.valueOf(1500));
            factura.setEstadoFactura("Emitida");
            factura.setIdEstudiante(1L);
            facturaRepository.save(factura);

            // Crear datos de Boleta
            Boleta boleta = new Boleta();
            boleta.setPago(pago);
            boleta.setNumeroBoleta("B789012");
            boleta.setFechaEmision(LocalDate.now());
            boleta.setDescripcion("Boleta del pago inicial");
            boleta.setImpuestos(BigDecimal.valueOf(270));
            boleta.setSubtotal(BigDecimal.valueOf(1230));
            boleta.setTotal(BigDecimal.valueOf(1500));
            boleta.setTipoDocumento("DNI");
            boleta.setIdEstudiante(1L);
            boletaRepository.save(boleta);

            // Crear datos de Deuda
            Deuda deuda = new Deuda();
            deuda.setIdEstudiante(2L);
            deuda.setMontoPendiente(BigDecimal.valueOf(2000));
            deuda.setFechaLimite(LocalDate.now().plusMonths(2));
            deuda.setEstadoDeuda("Pendiente");
            deudaRepository.save(deuda);

            // Crear datos de PlanDePago
            PlanDePago planDePago = new PlanDePago();
            planDePago.setNombre("Plan de Cuotas Mensuales");
            planDePago.setDescripcion("Pago fraccionado en 6 meses");
            planDePago.setMontoTotal(BigDecimal.valueOf(6000));
            planDePago.setNumeroCuotas(6);
            planDePago.setMontoPorCuota(BigDecimal.valueOf(1000));
            planDePago.setFechaInicio(LocalDate.now());
            planDePago.setFechaFin(LocalDate.now().plusMonths(6));

            // Crear Cuotas asociadas al PlanDePago
            for (int i = 1; i <= 6; i++) {
                Cuota cuota = new Cuota();
                cuota.setPlanDePago(planDePago);
                cuota.setNumeroCuota(i);
                cuota.setMonto(BigDecimal.valueOf(1000));
                cuota.setFechaVencimiento(LocalDate.now().plusMonths(i));
                cuota.setEstadoCuota("Pendiente");
                planDePago.getCuotas().add(cuota);
            }
            planDePagoRepository.save(planDePago);

            // Crear datos de Transaccion
            Transaccion transaccion = new Transaccion();
            transaccion.setPago(pago);
            transaccion.setEstadoTransaccion("Completado");
            transaccion.setFechaTransaccion(LocalDateTime.now());
            transaccionRepository.save(transaccion);

            System.out.println("Datos de ejemplo para pagos cargados en la base de datos.");
        } else {
            System.out.println("Los datos de pagos ya están cargados en la base de datos.");
        }
    }
}