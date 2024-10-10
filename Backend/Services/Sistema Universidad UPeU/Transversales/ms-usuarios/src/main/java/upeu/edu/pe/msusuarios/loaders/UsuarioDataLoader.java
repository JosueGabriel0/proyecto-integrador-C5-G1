package upeu.edu.pe.msusuarios.loaders;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
import org.springframework.security.crypto.password.PasswordEncoder;
import upeu.edu.pe.msusuarios.entity.Usuario;
import upeu.edu.pe.msusuarios.repository.UsuarioRepository;

import java.time.LocalDateTime;

@Component
public class UsuarioDataLoader implements CommandLineRunner {

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Override
    public void run(String... args) throws Exception {
        // Crear y guardar algunos usuarios de ejemplo
        Usuario usuario1 = new Usuario();
        usuario1.setUsername("josuexd");
        usuario1.setPassword(passwordEncoder.encode("12345")); // Cifrar la contraseña
        usuario1.setEmail("josueochoa2003cm@gmail.com");
        usuario1.setEnabled(true);
        usuario1.setIdRol(1); // Asignar un rol (por ejemplo, rol con ID 1)

        Usuario usuario2 = new Usuario();
        usuario2.setUsername("josue");
        usuario2.setPassword(passwordEncoder.encode("12345")); // Cifrar la contraseña
        usuario2.setEmail("josue.ochoa@upeu.edu.pe");
        usuario2.setEnabled(true);
        usuario2.setIdRol(2); // Asignar un rol (por ejemplo, rol con ID 2)

        usuarioRepository.save(usuario1);
        usuarioRepository.save(usuario2);

        System.out.println("Usuarios iniciales poblados en la base de datos.");
    }
}