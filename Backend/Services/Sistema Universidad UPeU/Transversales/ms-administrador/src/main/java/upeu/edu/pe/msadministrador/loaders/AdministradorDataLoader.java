package upeu.edu.pe.msadministrador.loaders;

import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import upeu.edu.pe.msadministrador.entity.Administrador;
import upeu.edu.pe.msadministrador.repository.AdministradorRepository;

import java.time.LocalDateTime;
import java.util.List;

@Configuration
public class AdministradorDataLoader {

    @Bean
    public CommandLineRunner loadAdministradores(AdministradorRepository administradorRepository) {
        return args -> {
            // Verificar si existen administradores en la base de datos
            if (administradorRepository.count() == 0) {

                // Crear administradores con datos de ejemplo
                Administrador admin1 = new Administrador();
                admin1.setActividadReciente("Modificación de roles");
                admin1.setEstadoSistema("Activo");
                admin1.setPermisosEspeciales("Gestión de usuarios");
                admin1.setLogsAcceso("Acceso al sistema el 2024-01-15");
                admin1.setCambiosConfiguracion("Actualización de configuraciones de seguridad");
                admin1.setIdPersona(1); // ID de la persona asociada

                Administrador admin2 = new Administrador();
                admin2.setActividadReciente("Auditoría de acceso");
                admin2.setEstadoSistema("Mantenimiento");
                admin2.setPermisosEspeciales("Gestión de auditorías");
                admin2.setLogsAcceso("Acceso al sistema el 2024-02-20");
                admin2.setCambiosConfiguracion("Restablecimiento de contraseñas de usuario");
                admin2.setIdPersona(2); // ID de la persona asociada

                Administrador admin3 = new Administrador();
                admin3.setActividadReciente("Revisión de permisos");
                admin3.setEstadoSistema("Activo");
                admin3.setPermisosEspeciales("Gestión de configuraciones");
                admin3.setLogsAcceso("Acceso al sistema el 2024-03-10");
                admin3.setCambiosConfiguracion("Actualización de roles");
                admin3.setIdPersona(3); // ID de la persona asociada

                Administrador admin4 = new Administrador();
                admin4.setActividadReciente("Gestión de usuarios");
                admin4.setEstadoSistema("Activo");
                admin4.setPermisosEspeciales("Acceso completo");
                admin4.setLogsAcceso("Acceso al sistema el 2024-04-25");
                admin4.setCambiosConfiguracion("Configuración del sistema de seguridad");
                admin4.setIdPersona(4); // ID de la persona asociada

                // Guardar administradores en la base de datos
                administradorRepository.saveAll(List.of(admin1, admin2, admin3, admin4));

                System.out.println("Administradores iniciales creados correctamente.");
            }
        };
    }
}
