package upeu.edu.pe.msadministrativo.loaders;

import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import upeu.edu.pe.msadministrativo.entity.Administrativo;
import upeu.edu.pe.msadministrativo.repository.AdministrativoRepository;

import java.time.LocalDateTime;
import java.util.List;

@Configuration
public class AdministrativoDataLoader {

    @Bean
    public CommandLineRunner loadAdministrativos(AdministrativoRepository administrativoRepository) {
        return args -> {
            // Verificar si existen administrativos en la base de datos
            if (administrativoRepository.count() == 0) {

                // Crear administrativos con datos de ejemplo
                Administrativo admin1 = new Administrativo();
                admin1.setRegistroPagos("Pago de matrículas");
                admin1.setMontoTotalPagos(1500.00);
                admin1.setGestionEmpleados("Contratación");
                admin1.setCargoEmpleado("Auxiliar administrativo");
                admin1.setSolicitudesPendientes("Solicitud de material de oficina");
                admin1.setIdPersona(1); // ID de la persona asociada

                Administrativo admin2 = new Administrativo();
                admin2.setRegistroPagos("Pago de inscripciones");
                admin2.setMontoTotalPagos(500.00);
                admin2.setGestionEmpleados("Desvinculación");
                admin2.setCargoEmpleado("Secretaria");
                admin2.setSolicitudesPendientes("Solicitud de renovación de contrato");
                admin2.setIdPersona(2); // ID de la persona asociada

                Administrativo admin3 = new Administrativo();
                admin3.setRegistroPagos("Pago de servicios");
                admin3.setMontoTotalPagos(200.00);
                admin3.setGestionEmpleados("Contratación");
                admin3.setCargoEmpleado("Auxiliar administrativo");
                admin3.setSolicitudesPendientes("Solicitud de equipo informático");
                admin3.setIdPersona(3); // ID de la persona asociada

                Administrativo admin4 = new Administrativo();
                admin4.setRegistroPagos("Pago de matrículas");
                admin4.setMontoTotalPagos(750.00);
                admin4.setGestionEmpleados("Contratación");
                admin4.setCargoEmpleado("Coordinador");
                admin4.setSolicitudesPendientes("Solicitud de material de oficina");
                admin4.setIdPersona(4); // ID de la persona asociada

                // Guardar administrativos en la base de datos
                administrativoRepository.saveAll(List.of(admin1, admin2, admin3, admin4));

                System.out.println("Administrativos iniciales creados correctamente.");
            }
        };
    }
}
