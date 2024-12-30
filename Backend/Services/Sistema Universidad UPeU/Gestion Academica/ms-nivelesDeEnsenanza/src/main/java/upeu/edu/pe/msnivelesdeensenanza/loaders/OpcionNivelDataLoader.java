package upeu.edu.pe.msnivelesdeensenanza.loaders;

import upeu.edu.pe.msnivelesdeensenanza.entity.NivelEnsenanza;
import upeu.edu.pe.msnivelesdeensenanza.entity.OpcionNivel;
import upeu.edu.pe.msnivelesdeensenanza.repository.NivelEnsenanzaRepository;
import upeu.edu.pe.msnivelesdeensenanza.repository.OpcionNivelRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.Arrays;

@Component
public class OpcionNivelDataLoader implements CommandLineRunner {

    private final OpcionNivelRepository opcionNivelRepository;
    private final NivelEnsenanzaRepository nivelEnsenanzaRepository;

    public OpcionNivelDataLoader(OpcionNivelRepository opcionNivelRepository, NivelEnsenanzaRepository nivelEnsenanzaRepository) {
        this.opcionNivelRepository = opcionNivelRepository;
        this.nivelEnsenanzaRepository = nivelEnsenanzaRepository;
    }

    @Override
    public void run(String... args) throws Exception {
        // Verificar si ya existen opciones de nivel en la base de datos
        if (opcionNivelRepository.count() == 0) {
            NivelEnsenanza pregrado = nivelEnsenanzaRepository.findByNombre("Pregrado").orElse(null);
            NivelEnsenanza postgrado = nivelEnsenanzaRepository.findByNombre("Postgrado").orElse(null);

            if (pregrado != null && postgrado != null) {
                OpcionNivel opcion1 = new OpcionNivel();
                opcion1.setNivelEnsenanza(pregrado);
                opcion1.setTipoEstudio("Ingeniería");
                opcion1.setSemestre("2024-I");
                opcion1.setCampus("Campus Central");
                opcion1.setIdCarrera(1L);
                opcion1.setIdPLanificacionAcademica(101L);
                opcion1.setModalidad("Presencial");
                opcion1.setEstado("Activo");
                opcion1.setFechaCreacionOpcionNivel(LocalDateTime.now());

                OpcionNivel opcion2 = new OpcionNivel();
                opcion2.setNivelEnsenanza(postgrado);
                opcion2.setTipoEstudio("MBA");
                opcion2.setSemestre("2024-II");
                opcion2.setCampus("Campus Sur");
                opcion2.setIdCarrera(2L);
                opcion2.setIdPLanificacionAcademica(102L);
                opcion2.setModalidad("Semipresencial");
                opcion2.setEstado("Activo");
                opcion2.setFechaCreacionOpcionNivel(LocalDateTime.now());

                opcionNivelRepository.saveAll(Arrays.asList(opcion1, opcion2));
                System.out.println("Opciones de nivel cargadas en la base de datos.");
            } else {
                System.out.println("No se encontraron niveles de enseñanza para asociar con las opciones.");
            }
        } else {
            System.out.println("Las opciones de nivel ya están cargadas en la base de datos.");
        }
    }
}