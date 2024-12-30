package upeu.edu.pe.mscurso.loaders;

import upeu.edu.pe.mscurso.entity.Curso;
import upeu.edu.pe.mscurso.repository.CursoRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.Arrays;

@Component
public class CursoDataLoader implements CommandLineRunner {

    private final CursoRepository cursoRepository;

    public CursoDataLoader(CursoRepository cursoRepository) {
        this.cursoRepository = cursoRepository;
    }

    @Override
    public void run(String... args) throws Exception {
        // Verificar si ya existen cursos en la base de datos
        if (cursoRepository.count() == 0) {
            // ID de la carrera de Ingeniería de Sistemas
            Long idCarreraIngenieriaSistemas = 1L;

            // Crear cursos de la carrera de Ingeniería de Sistemas
            Curso curso1 = new Curso();
            curso1.setNombre("Matemáticas Básicas");
            curso1.setCodigo("MAT101");
            curso1.setDescripcion("Curso introductorio a las matemáticas");
            curso1.setCreditos(3);
            curso1.setHorasTeoricas(3);
            curso1.setHorasPracticas(0);
            curso1.setTipo("Obligatorio");
            curso1.setNivel("Básico");
            curso1.setIdCarrera(idCarreraIngenieriaSistemas);
            curso1.setPreRequisito(null);
            curso1.setGrupo("G1");
            curso1.setFechaCreacionCurso(LocalDateTime.now());

            Curso curso2 = new Curso();
            curso2.setNombre("Introducción a la Programación");
            curso2.setCodigo("PROG101");
            curso2.setDescripcion("Fundamentos de programación y algoritmos");
            curso2.setCreditos(4);
            curso2.setHorasTeoricas(3);
            curso2.setHorasPracticas(1);
            curso2.setTipo("Obligatorio");
            curso2.setNivel("Básico");
            curso2.setIdCarrera(idCarreraIngenieriaSistemas);
            curso2.setPreRequisito("MAT101"); // Requiere Matemáticas Básicas
            curso2.setGrupo("G1");
            curso2.setFechaCreacionCurso(LocalDateTime.now());

            Curso curso3 = new Curso();
            curso3.setNombre("Inglés Intermedio");
            curso3.setCodigo("ENG201");
            curso3.setDescripcion("Desarrollo de habilidades en inglés a nivel intermedio");
            curso3.setCreditos(2);
            curso3.setHorasTeoricas(2);
            curso3.setHorasPracticas(0);
            curso3.setTipo("Electivo");
            curso3.setNivel("Intermedio");
            curso3.setIdCarrera(idCarreraIngenieriaSistemas);
            curso3.setPreRequisito(null);
            curso3.setGrupo("G1");
            curso3.setFechaCreacionCurso(LocalDateTime.now());

            // Más cursos pueden agregarse siguiendo el mismo patrón...

            // Guardar cursos en la base de datos
            cursoRepository.saveAll(Arrays.asList(curso1, curso2, curso3));
            System.out.println("Cursos de ejemplo cargados en la base de datos.");
        } else {
            System.out.println("Los cursos ya están cargados en la base de datos.");
        }
    }
}