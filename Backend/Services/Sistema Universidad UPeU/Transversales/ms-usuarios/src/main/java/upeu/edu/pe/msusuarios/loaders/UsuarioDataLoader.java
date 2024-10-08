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
        usuario1.setUsername("usuario1");
        usuario1.setPassword(passwordEncoder.encode("password1")); // Cifrar la contraseña
        usuario1.setEmail("usuario1@example.com");
        usuario1.setEnabled(true);
        usuario1.setIdRol(1); // Asignar un rol (por ejemplo, rol con ID 1)
        usuario1.setUltimoLogin(LocalDateTime.now());

        Usuario usuario2 = new Usuario();
        usuario2.setUsername("usuario2");
        usuario2.setPassword(passwordEncoder.encode("password2")); // Cifrar la contraseña
        usuario2.setEmail("usuario2@example.com");
        usuario2.setEnabled(true);
        usuario2.setIdRol(2); // Asignar un rol (por ejemplo, rol con ID 2)
        usuario2.setUltimoLogin(LocalDateTime.now());

        usuarioRepository.save(usuario1);
        usuarioRepository.save(usuario2);

        System.out.println("Datos iniciales poblados en la base de datos.");
    }
}