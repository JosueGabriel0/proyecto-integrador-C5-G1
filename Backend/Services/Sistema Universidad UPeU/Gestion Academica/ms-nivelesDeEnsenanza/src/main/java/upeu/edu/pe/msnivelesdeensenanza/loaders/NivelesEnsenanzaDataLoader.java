package upeu.edu.pe.msnivelesdeensenanza.loaders;

import upeu.edu.pe.msnivelesdeensenanza.entity.NivelEnsenanza;
import upeu.edu.pe.msnivelesdeensenanza.entity.OpcionNivel;
import upeu.edu.pe.msnivelesdeensenanza.repository.NivelEnsenanzaRepository;
import upeu.edu.pe.msnivelesdeensenanza.repository.OpcionNivelRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.util.Arrays;

@Component
public class NivelesEnsenanzaDataLoader implements CommandLineRunner {

    private final NivelEnsenanzaRepository nivelEnsenanzaRepository;
    private final OpcionNivelRepository opcionNivelRepository;

    public NivelesEnsenanzaDataLoader(NivelEnsenanzaRepository nivelEnsenanzaRepository,
                                      OpcionNivelRepository opcionNivelRepository) {
        this.nivelEnsenanzaRepository = nivelEnsenanzaRepository;
        this.opcionNivelRepository = opcionNivelRepository;
    }

    @Override
    public void run(String... args) throws Exception {
        // Verificar si ya existen niveles en la base de datos
        if (nivelEnsenanzaRepository.count() == 0) {
            // Crear niveles de enseñanza
            NivelEnsenanza nivelRegular = new NivelEnsenanza();
            nivelRegular.setNombre("Regular");
            nivelRegular.setDescripcion("Nivel de enseñanza regular para estudiantes.");

            NivelEnsenanza nivelVariacion = new NivelEnsenanza();
            nivelVariacion.setNombre("Variación");
            nivelVariacion.setDescripcion("Nivel de enseñanza especial o alternativo.");

            // Guardar niveles de enseñanza
            nivelEnsenanzaRepository.saveAll(Arrays.asList(nivelRegular, nivelVariacion));

            // Crear opciones para el nivel "Regular"
            OpcionNivel opcionRegular1 = new OpcionNivel();
            opcionRegular1.setNivelEnsenanza(nivelRegular);
            opcionRegular1.setTipoEstudio("Pregrado");
            opcionRegular1.setSemestre("2022-3");
            opcionRegular1.setCampus("Sede Juliaca");
            opcionRegular1.setIdPLanificacionAcademica(1L);
            opcionRegular1.setIdCarrera(1L);
            opcionRegular1.setModalidad("Presencial");
            opcionRegular1.setEstado("Ninguno");

            OpcionNivel opcionRegular2 = new OpcionNivel();
            opcionRegular2.setNivelEnsenanza(nivelRegular);
            opcionRegular2.setTipoEstudio("Pregrado");
            opcionRegular2.setSemestre("2023-1");
            opcionRegular2.setCampus("Sede Lima");
            opcionRegular2.setIdPLanificacionAcademica(2L);
            opcionRegular2.setIdCarrera(2L);
            opcionRegular2.setModalidad("Semipresencial");
            opcionRegular2.setEstado("Ninguno");

            // Crear opciones para el nivel "Variación"
            OpcionNivel opcionVariacion1 = new OpcionNivel();
            opcionVariacion1.setNivelEnsenanza(nivelVariacion);
            opcionVariacion1.setTipoEstudio("Posgrado");
            opcionVariacion1.setSemestre("2023-2");
            opcionVariacion1.setCampus("Sede Tarapoto");
            opcionVariacion1.setIdPLanificacionAcademica(3L);
            opcionVariacion1.setIdCarrera(3L);
            opcionVariacion1.setModalidad("Virtual");
            opcionVariacion1.setEstado("Ninguno");

            // Guardar opciones en la base de datos
            opcionNivelRepository.saveAll(Arrays.asList(opcionRegular1, opcionRegular2, opcionVariacion1));

            System.out.println("Datos de niveles de enseñanza cargados en la base de datos.");
        } else {
            System.out.println("Los niveles de enseñanza ya están cargados en la base de datos.");
        }
    }
}