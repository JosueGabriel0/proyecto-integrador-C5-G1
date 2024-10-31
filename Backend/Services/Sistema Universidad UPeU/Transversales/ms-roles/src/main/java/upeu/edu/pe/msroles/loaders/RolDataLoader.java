package upeu.edu.pe.msroles.loaders;

import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import upeu.edu.pe.msroles.entity.Rol;
import upeu.edu.pe.msroles.repository.RolRepository;

import java.util.List;

@Configuration
public class RolDataLoader {

    @Bean
    public CommandLineRunner loadRoles(RolRepository rolRepository) {
        return args -> {
            // Verificar si existen roles en la base de datos
            if (rolRepository.count() == 0) {

                // Crear roles por defecto
                Rol adminRole = new Rol();
                adminRole.setNombreRol("ADMINISTRADOR");
                adminRole.setDescription("Acceso completo a todas las funcionalidades.");

                Rol administrativoRole = new Rol();
                administrativoRole.setNombreRol("ADMINISTRATIVO");
                administrativoRole.setDescription("Acceso a la gestión administrativa.");

                Rol estudianteRole = new Rol();
                estudianteRole.setNombreRol("ESTUDIANTE");
                estudianteRole.setDescription("Acceso a los recursos del estudiante.");

                Rol docenteRole = new Rol();
                docenteRole.setNombreRol("DOCENTE");
                docenteRole.setDescription("Acceso a la gestión académica como docente.");

                // Guardar roles en la base de datos
                rolRepository.saveAll(List.of(adminRole, administrativoRole, estudianteRole, docenteRole));

                System.out.println("Roles iniciales creados correctamente.");
            }
        };
    }
}
