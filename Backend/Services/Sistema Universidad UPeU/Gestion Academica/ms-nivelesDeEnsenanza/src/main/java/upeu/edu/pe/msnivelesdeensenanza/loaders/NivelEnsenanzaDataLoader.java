package upeu.edu.pe.msnivelesdeensenanza.loaders;

import upeu.edu.pe.msnivelesdeensenanza.entity.NivelEnsenanza;
import upeu.edu.pe.msnivelesdeensenanza.repository.NivelEnsenanzaRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.Arrays;

@Component
public class NivelEnsenanzaDataLoader implements CommandLineRunner {

    private final NivelEnsenanzaRepository nivelEnsenanzaRepository;

    public NivelEnsenanzaDataLoader(NivelEnsenanzaRepository nivelEnsenanzaRepository) {
        this.nivelEnsenanzaRepository = nivelEnsenanzaRepository;
    }

    @Override
    public void run(String... args) throws Exception {
        // Verificar si ya existen niveles de enseñanza en la base de datos
        if (nivelEnsenanzaRepository.count() == 0) {
            NivelEnsenanza nivel1 = new NivelEnsenanza();
            nivel1.setNombre("Pregrado");
            nivel1.setDescripcion("Niveles académicos de pregrado");
            nivel1.setFechaCreacionNivelEnsenanza(LocalDateTime.now());

            NivelEnsenanza nivel2 = new NivelEnsenanza();
            nivel2.setNombre("Postgrado");
            nivel2.setDescripcion("Niveles académicos de postgrado");
            nivel2.setFechaCreacionNivelEnsenanza(LocalDateTime.now());

            NivelEnsenanza nivel3 = new NivelEnsenanza();
            nivel3.setNombre("Técnico");
            nivel3.setDescripcion("Niveles técnicos y tecnológicos");
            nivel3.setFechaCreacionNivelEnsenanza(LocalDateTime.now());

            nivelEnsenanzaRepository.saveAll(Arrays.asList(nivel1, nivel2, nivel3));
            System.out.println("Niveles de enseñanza cargados en la base de datos.");
        } else {
            System.out.println("Los niveles de enseñanza ya están cargados en la base de datos.");
        }
    }
}