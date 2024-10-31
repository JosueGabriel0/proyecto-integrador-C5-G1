package upeu.edu.pe.msusuarios.loaders;

import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import upeu.edu.pe.msusuarios.entity.Usuario;
import upeu.edu.pe.msusuarios.repository.UsuarioRepository;

import java.util.List;

@Configuration
public class UsuarioDataLoaders {

    @Bean
    public CommandLineRunner loadUsuarios(UsuarioRepository usuarioRepository) {
        return args -> {
            // Verificar si existen usuarios en la base de datos
            if (usuarioRepository.count() == 0) {

                // Crear usuarios por defecto
                Usuario usuarioAdministrador1 = new Usuario();
                usuarioAdministrador1.setUsername("administrador");
                usuarioAdministrador1.setPassword("passwordAdministrador123"); // Asegúrate de cifrar la contraseña
                usuarioAdministrador1.setEmail("administrador@universidad.com");
                usuarioAdministrador1.setEnabled(true);
                usuarioAdministrador1.setIdRol(1L);

                Usuario usuarioAdministrativo1 = new Usuario();
                usuarioAdministrativo1.setUsername("administrativo");
                usuarioAdministrativo1.setPassword("passwordAdministrativo123"); // Asegúrate de cifrar la contraseña
                usuarioAdministrativo1.setEmail("administrativo@universidad.com");
                usuarioAdministrativo1.setEnabled(true);
                usuarioAdministrativo1.setIdRol(2L);

                Usuario usuarioDocente1 = new Usuario();
                usuarioDocente1.setUsername("docente");
                usuarioDocente1.setPassword("passwordDocente1123"); // Asegúrate de cifrar la contraseña
                usuarioDocente1.setEmail("docente@universidad.com");
                usuarioDocente1.setEnabled(true);
                usuarioDocente1.setIdRol(3L);

                Usuario usuarioEstudiante1 = new Usuario();
                usuarioEstudiante1.setUsername("estudiante");
                usuarioEstudiante1.setPassword("passwordEstudiante1123"); // Asegúrate de cifrar la contraseña
                usuarioEstudiante1.setEmail("estudiante@universidad.com");
                usuarioEstudiante1.setEnabled(true);
                usuarioEstudiante1.setIdRol(4L);

                Usuario usuarioEstudiante2 = new Usuario();
                usuarioEstudiante2.setUsername("estudiante");
                usuarioEstudiante2.setPassword("passwordEstudiante2123"); // Asegúrate de cifrar la contraseña
                usuarioEstudiante2.setEmail("estudiante@universidad.com");
                usuarioEstudiante2.setEnabled(true);
                usuarioEstudiante2.setIdRol(4L);

                Usuario usuarioDocente2 = new Usuario();
                usuarioDocente2.setUsername("docente");
                usuarioDocente2.setPassword("passwordDocente2123"); // Asegúrate de cifrar la contraseña
                usuarioDocente2.setEmail("docente@universidad.com");
                usuarioDocente2.setEnabled(true);
                usuarioDocente2.setIdRol(3L);

                Usuario usuarioDocente3 = new Usuario();
                usuarioDocente3.setUsername("docente");
                usuarioDocente3.setPassword("passwordDocente3123"); // Asegúrate de cifrar la contraseña
                usuarioDocente3.setEmail("docente@universidad.com");
                usuarioDocente3.setEnabled(true);
                usuarioDocente3.setIdRol(3L);

                Usuario usuarioDocente4 = new Usuario();
                usuarioDocente4.setUsername("docente");
                usuarioDocente4.setPassword("passwordDocente4123"); // Asegúrate de cifrar la contraseña
                usuarioDocente4.setEmail("docente@universidad.com");
                usuarioDocente4.setEnabled(true);
                usuarioDocente4.setIdRol(3L);

                Usuario usuarioDocente5 = new Usuario();
                usuarioDocente5.setUsername("docente");
                usuarioDocente5.setPassword("passwordDocente5123"); // Asegúrate de cifrar la contraseña
                usuarioDocente5.setEmail("docente@universidad.com");
                usuarioDocente5.setEnabled(true);
                usuarioDocente5.setIdRol(3L);

                Usuario usuarioDocente6 = new Usuario();
                usuarioDocente6.setUsername("docente");
                usuarioDocente6.setPassword("passwordDocente6123"); // Asegúrate de cifrar la contraseña
                usuarioDocente6.setEmail("docente@universidad.com");
                usuarioDocente6.setEnabled(true);
                usuarioDocente6.setIdRol(3L);

                Usuario usuarioDocente7 = new Usuario();
                usuarioDocente7.setUsername("docente");
                usuarioDocente7.setPassword("passwordDocente7123"); // Asegúrate de cifrar la contraseña
                usuarioDocente7.setEmail("docente@universidad.com");
                usuarioDocente7.setEnabled(true);
                usuarioDocente7.setIdRol(3L);

                Usuario usuarioDocente8 = new Usuario();
                usuarioDocente8.setUsername("docente");
                usuarioDocente8.setPassword("passwordDocente8123"); // Asegúrate de cifrar la contraseña
                usuarioDocente8.setEmail("docente@universidad.com");
                usuarioDocente8.setEnabled(true);
                usuarioDocente8.setIdRol(3L);

                Usuario usuarioDocente9 = new Usuario();
                usuarioDocente9.setUsername("docente");
                usuarioDocente9.setPassword("passwordDocente9123"); // Asegúrate de cifrar la contraseña
                usuarioDocente9.setEmail("docente@universidad.com");
                usuarioDocente9.setEnabled(true);
                usuarioDocente9.setIdRol(3L);

                Usuario usuarioDocente10 = new Usuario();
                usuarioDocente10.setUsername("docente");
                usuarioDocente10.setPassword("passwordDocente10123"); // Asegúrate de cifrar la contraseña
                usuarioDocente10.setEmail("docente@universidad.com");
                usuarioDocente10.setEnabled(true);
                usuarioDocente10.setIdRol(3L);

                Usuario usuarioDocente11 = new Usuario();
                usuarioDocente11.setUsername("docente");
                usuarioDocente11.setPassword("passwordDocente11123"); // Asegúrate de cifrar la contraseña
                usuarioDocente11.setEmail("docente@universidad.com");
                usuarioDocente11.setEnabled(true);
                usuarioDocente11.setIdRol(3L);

                Usuario usuarioDocente12 = new Usuario();
                usuarioDocente12.setUsername("docente");
                usuarioDocente12.setPassword("passwordDocente12123"); // Asegúrate de cifrar la contraseña
                usuarioDocente12.setEmail("docente@universidad.com");
                usuarioDocente12.setEnabled(true);
                usuarioDocente12.setIdRol(3L);

                Usuario usuarioDocente13 = new Usuario();
                usuarioDocente13.setUsername("docente");
                usuarioDocente13.setPassword("passwordDocente13123"); // Asegúrate de cifrar la contraseña
                usuarioDocente13.setEmail("docente@universidad.com");
                usuarioDocente13.setEnabled(true);
                usuarioDocente13.setIdRol(3L);

                Usuario usuarioDocente14 = new Usuario();
                usuarioDocente14.setUsername("docente");
                usuarioDocente14.setPassword("passwordDocente14123"); // Asegúrate de cifrar la contraseña
                usuarioDocente14.setEmail("docente@universidad.com");
                usuarioDocente14.setEnabled(true);
                usuarioDocente14.setIdRol(3L);

                Usuario usuarioAdministrador2 = new Usuario();
                usuarioAdministrador2.setUsername("administrador");
                usuarioAdministrador2.setPassword("passwordAdministrador2123"); // Asegúrate de cifrar la contraseña
                usuarioAdministrador2.setEmail("administrador@universidad.com");
                usuarioAdministrador2.setEnabled(true);
                usuarioAdministrador2.setIdRol(1L);

                Usuario usuarioAdministrador3 = new Usuario();
                usuarioAdministrador3.setUsername("administrador");
                usuarioAdministrador3.setPassword("passwordAdministrador3123"); // Asegúrate de cifrar la contraseña
                usuarioAdministrador3.setEmail("administrador@universidad.com");
                usuarioAdministrador3.setEnabled(true);
                usuarioAdministrador3.setIdRol(1L);

                Usuario usuarioAdministrador4 = new Usuario();
                usuarioAdministrador4.setUsername("administrador");
                usuarioAdministrador4.setPassword("passwordAdministrador4123"); // Asegúrate de cifrar la contraseña
                usuarioAdministrador4.setEmail("administrador@universidad.com");
                usuarioAdministrador4.setEnabled(true);
                usuarioAdministrador4.setIdRol(1L);

                Usuario usuarioAdministrativo2 = new Usuario();
                usuarioAdministrativo2.setUsername("administrativo");
                usuarioAdministrativo2.setPassword("passwordAdministrativo1223"); // Asegúrate de cifrar la contraseña
                usuarioAdministrativo2.setEmail("administrativo@universidad.com");
                usuarioAdministrativo2.setEnabled(true);
                usuarioAdministrativo2.setIdRol(2L);

                Usuario usuarioAdministrativo3 = new Usuario();
                usuarioAdministrativo3.setUsername("administrativo");
                usuarioAdministrativo3.setPassword("passwordAdministrativo3123"); // Asegúrate de cifrar la contraseña
                usuarioAdministrativo3.setEmail("administrativo@universidad.com");
                usuarioAdministrativo3.setEnabled(true);
                usuarioAdministrativo3.setIdRol(2L);

                Usuario usuarioAdministrativo4 = new Usuario();
                usuarioAdministrativo4.setUsername("administrativo");
                usuarioAdministrativo4.setPassword("passwordAdministrativo4123"); // Asegúrate de cifrar la contraseña
                usuarioAdministrativo4.setEmail("administrativo@universidad.com");
                usuarioAdministrativo4.setEnabled(true);
                usuarioAdministrativo4.setIdRol(2L);

                // Guardar usuarios en la base de datos
                usuarioRepository.saveAll(List.of(usuarioAdministrador1, usuarioAdministrador2, usuarioAdministrador3, usuarioAdministrador4, usuarioAdministrativo1, usuarioAdministrativo2, usuarioAdministrativo3, usuarioAdministrativo4, usuarioDocente1, usuarioDocente2, usuarioDocente3, usuarioDocente4, usuarioDocente5, usuarioDocente6, usuarioDocente7, usuarioDocente8, usuarioDocente9, usuarioDocente10, usuarioDocente11, usuarioDocente12, usuarioDocente13, usuarioDocente14, usuarioEstudiante1, usuarioEstudiante2));

                System.out.println("Usuarios iniciales creados correctamente.");
            }
        };
    }
}
