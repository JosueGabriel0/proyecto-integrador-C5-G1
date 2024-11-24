package upeu.edu.pe.msinscripciones.loaders;

import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.beans.factory.annotation.Autowired;
import upeu.edu.pe.msinscripciones.entity.Inscripcion;
import upeu.edu.pe.msinscripciones.repository.InscripcionesRepository;

import java.time.LocalDateTime;

@Configuration
public class InscripcionesDataLoader {

    @Bean
    CommandLineRunner initInscripciones(@Autowired InscripcionesRepository inscripcionesRepository) {
        return args -> {
            // Verificar si ya existen datos en la base de datos
            if (inscripcionesRepository.count() == 0) {
                System.out.println("No se encontraron inscripciones. Creando datos predeterminados...");

                // Crear y guardar inscripciones predeterminadas
                Inscripcion inscripcion1 = new Inscripcion();
                inscripcion1.setIdInscripcion(null); // Se generará automáticamente
                inscripcion1.setInscripcionRol("Con Rol");
                inscripcion1.setFechaCreacionInscripcion(LocalDateTime.now());
                inscripcion1.setIdRol(1L);
                inscripcion1.setIdUsuario(1L);
                inscripcion1.setIdPersona(1L);
                inscripcion1.setIdAdministrador(1L);

                inscripcionesRepository.save(inscripcion1);

                Inscripcion inscripcion2 = new Inscripcion();
                inscripcion2.setIdInscripcion(null); // Se generará automáticamente
                inscripcion2.setInscripcionRol("Con Rol");
                inscripcion2.setFechaCreacionInscripcion(LocalDateTime.now());
                inscripcion2.setIdRol(2L);
                inscripcion2.setIdUsuario(2L);
                inscripcion2.setIdPersona(2L);
                inscripcion2.setIdAdministrativo(1L);

                inscripcionesRepository.save(inscripcion2);

                System.out.println("Datos predeterminados creados con éxito.");
            } else {
                System.out.println("Ya existen inscripciones en la base de datos. No se generarán datos predeterminados.");
            }

            // Verificar los datos guardados
            inscripcionesRepository.findAll().forEach(inscripcion -> {
                System.out.println("Inscripción en la base de datos: " + inscripcion);
            });
        };
    }
}