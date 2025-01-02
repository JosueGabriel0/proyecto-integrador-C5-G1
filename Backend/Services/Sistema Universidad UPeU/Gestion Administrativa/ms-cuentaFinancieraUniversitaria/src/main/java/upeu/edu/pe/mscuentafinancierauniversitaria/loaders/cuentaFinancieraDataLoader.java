package upeu.edu.pe.mscuentafinancierauniversitaria.loaders;

import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
import upeu.edu.pe.mscuentafinancierauniversitaria.entity.CuentaFinanciera;
import upeu.edu.pe.mscuentafinancierauniversitaria.entity.MovimientoAcademico;
import upeu.edu.pe.mscuentafinancierauniversitaria.repository.CuentaFinancieraRepository;
import upeu.edu.pe.mscuentafinancierauniversitaria.repository.MovimientoAcademicoRepository;

import java.time.LocalDate;
import java.util.Arrays;

@Component
public class cuentaFinancieraDataLoader implements CommandLineRunner {

    private final CuentaFinancieraRepository cuentaFinancieraRepository;
    private final MovimientoAcademicoRepository movimientoAcademicoRepository;

    public cuentaFinancieraDataLoader(CuentaFinancieraRepository cuentaFinancieraRepository, MovimientoAcademicoRepository movimientoAcademicoRepository) {
        this.cuentaFinancieraRepository = cuentaFinancieraRepository;
        this.movimientoAcademicoRepository = movimientoAcademicoRepository;
    }

    @Override
    public void run(String... args) throws Exception {
        // Verificar si ya existen registros en CuentaFinanciera
        if (cuentaFinancieraRepository.count() == 0) {
            CuentaFinanciera cuenta1 = new CuentaFinanciera();
            cuenta1.setEntidad("Entidad A");
            cuenta1.setDepartamento("Departamento X");
            cuenta1.setAnio(LocalDate.of(2023, 12, 31));
            cuenta1.setSumasDebito(10000.0);
            cuenta1.setSumasCredito(8000.0);
            cuenta1.setSaldoFinalDebito(2000.0);
            cuenta1.setSaldoFinalCredito(0.0);
            cuenta1.setSaldoAfavor(2000.0);

            CuentaFinanciera cuenta2 = new CuentaFinanciera();
            cuenta2.setEntidad("Entidad B");
            cuenta2.setDepartamento("Departamento Y");
            cuenta2.setAnio(LocalDate.of(2024, 12, 31));
            cuenta2.setSumasDebito(15000.0);
            cuenta2.setSumasCredito(14000.0);
            cuenta2.setSaldoFinalDebito(1000.0);
            cuenta2.setSaldoFinalCredito(0.0);
            cuenta2.setSaldoAfavor(1000.0);

            cuentaFinancieraRepository.saveAll(Arrays.asList(cuenta1, cuenta2));
            System.out.println("Cuentas financieras cargadas en la base de datos.");
        } else {
            System.out.println("Las cuentas financieras ya están cargadas en la base de datos.");
        }

        // Verificar si ya existen registros en MovimientoAcademico
        if (movimientoAcademicoRepository.count() == 0) {
            MovimientoAcademico movimiento1 = new MovimientoAcademico();
            movimiento1.setFecha(LocalDate.of(2023, 12, 1));
            movimiento1.setVoucher(12345);
            movimiento1.setLote("Lote-001");
            movimiento1.setDocumento("Doc-12345");
            movimiento1.setMovimiento("Pago matrícula");
            movimiento1.setDescripcion("Pago de matrícula universitaria");
            movimiento1.setDebito("500.00");
            movimiento1.setCredito("0.00");
            movimiento1.setIdBoleta(1L);
            movimiento1.setIdFactura(101L);

            MovimientoAcademico movimiento2 = new MovimientoAcademico();
            movimiento2.setFecha(LocalDate.of(2024, 1, 15));
            movimiento2.setVoucher(12346);
            movimiento2.setLote("Lote-002");
            movimiento2.setDocumento("Doc-12346");
            movimiento2.setMovimiento("Pago curso");
            movimiento2.setDescripcion("Pago por curso adicional");
            movimiento2.setDebito("0.00");
            movimiento2.setCredito("300.00");
            movimiento2.setIdBoleta(2L);
            movimiento2.setIdFactura(102L);

            movimientoAcademicoRepository.saveAll(Arrays.asList(movimiento1, movimiento2));
            System.out.println("Movimientos académicos cargados en la base de datos.");
        } else {
            System.out.println("Los movimientos académicos ya están cargados en la base de datos.");
        }
    }
}