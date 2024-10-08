package upeu.edu.pe.msroles.loaders;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
import upeu.edu.pe.msroles.entity.Rol;
import upeu.edu.pe.msroles.repository.RolRepository;

import java.time.LocalDateTime;

@Component
public class RolDataLoader implements CommandLineRunner {

    @Autowired
    private RolRepository rolRepository;

    @Override
    public void run(String... args) throws Exception {
        // Crear y guardar algunos roles de ejemplo
        Rol rol1 = new Rol();
        rol1.setNombreRol("ROLE_ADMIN");
        rol1.setDescription("Rol para administradores");
        rol1.setFechaCreacionRol(LocalDateTime.now());

        Rol rol2 = new Rol();
        rol2.setNombreRol("ROLE_DOCENTE");
        rol2.setDescription("Rol para docentes");
        rol2.setFechaCreacionRol(LocalDateTime.now());

        Rol rol3 = new Rol();
        rol3.setNombreRol("ROLE_ESTUDIANTE");
        rol3.setDescription("Rol para estudiantes");
        rol3.setFechaCreacionRol(LocalDateTime.now());

        Rol rol4 = new Rol();
        rol4.setNombreRol("ROLE_ADMINISTRATIVO");
        rol4.setDescription("Rol para personal administrativo");
        rol4.setFechaCreacionRol(LocalDateTime.now());

        Rol rol5 = new Rol();
        rol5.setNombreRol("ROLE_COORDINADOR");
        rol5.setDescription("Rol para coordinadores");
        rol5.setFechaCreacionRol(LocalDateTime.now());

        // Guardar roles en la base de datos
        rolRepository.save(rol1);
        rolRepository.save(rol2);
        rolRepository.save(rol3);
        rolRepository.save(rol4);
        rolRepository.save(rol5);

        System.out.println("Roles iniciales poblados en la base de datos.");
    }
}