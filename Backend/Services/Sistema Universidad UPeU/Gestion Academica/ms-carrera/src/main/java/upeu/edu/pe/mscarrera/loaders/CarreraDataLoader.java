package upeu.edu.pe.mscarrera.loaders;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import upeu.edu.pe.mscarrera.entity.Carrera;
import upeu.edu.pe.mscarrera.repository.CarreraRepository;

import jakarta.annotation.PostConstruct;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;

@Component
public class CarreraDataLoader {

    @Autowired
    private CarreraRepository carreraRepository;

    @PostConstruct
    public void loadData() {
        if (carreraRepository.count() == 0) {
            // IDs de las planificaciones academicas para Ingeniería de Sistemas (deben corresponder a los ID de la base de datos de planificaciones academicas)
            List<Long> planificacionesAcademicasIngenieriaSistemas = Arrays.asList(1L);
            // IDs de los cursos para Ingeniería de Sistemas (deben corresponder a los ID de la base de datos de cursos)
            List<Long> cursosIngenieriaSistemas = Arrays.asList(1L, 2L, 3L, 4L, 5L, 6L, 7L, 8L, 9L);

            // IDs de los cursos para Administración
            List<Long> cursosAdministracion = Arrays.asList(10L, 11L, 12L, 13L, 14L);

            // Crear carrera de Ingeniería de Sistemas
            Carrera ingenieriaSistemas = new Carrera();
            ingenieriaSistemas.setCodigo("IS101");
            ingenieriaSistemas.setNombre("Ingeniería de Sistemas");
            ingenieriaSistemas.setDescripcion("Carrera enfocada en la creación y gestión de sistemas informáticos");
            ingenieriaSistemas.setDuracion(5);
            ingenieriaSistemas.setCursos(cursosIngenieriaSistemas);
            ingenieriaSistemas.setFechaCreacionCarrera(LocalDateTime.now());

            // Crear carrera de Administración
            Carrera administracion = new Carrera();
            administracion.setCodigo("ADM101");
            administracion.setNombre("Administración");
            administracion.setDescripcion("Carrera orientada a la gestión y organización de recursos empresariales");
            administracion.setDuracion(4);
            administracion.setCursos(cursosAdministracion);
            administracion.setFechaCreacionCarrera(LocalDateTime.now());

            // Guardar carreras en la base de datos
            carreraRepository.save(ingenieriaSistemas);
            carreraRepository.save(administracion);

            System.out.println("Carreras de Ingeniería de Sistemas y Administración inicializadas correctamente.");
        } else {
            System.out.println("Las carreras ya están cargadas en la base de datos.");
        }
    }
}
