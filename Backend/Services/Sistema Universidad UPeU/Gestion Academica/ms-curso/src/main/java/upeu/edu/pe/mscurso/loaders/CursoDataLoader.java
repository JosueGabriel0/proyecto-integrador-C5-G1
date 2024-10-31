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
            curso1.setTipo("Obligatorio");
            curso1.setNivel("Básico");
            curso1.setIdCarrera(idCarreraIngenieriaSistemas);
            curso1.setIdDocente(1L);
            curso1.setFechaCreacionCurso(LocalDateTime.now());

            Curso curso2 = new Curso();
            curso2.setNombre("Introducción a la Programación");
            curso2.setCodigo("PROG101");
            curso2.setDescripcion("Fundamentos de programación y algoritmos");
            curso2.setCreditos(4);
            curso2.setTipo("Obligatorio");
            curso2.setNivel("Básico");
            curso2.setIdCarrera(idCarreraIngenieriaSistemas);
            curso1.setIdDocente(2L);
            curso2.setFechaCreacionCurso(LocalDateTime.now());

            Curso curso3 = new Curso();
            curso3.setNombre("Inglés Intermedio");
            curso3.setCodigo("ENG201");
            curso3.setDescripcion("Desarrollo de habilidades en inglés a nivel intermedio");
            curso3.setCreditos(2);
            curso3.setTipo("Electivo");
            curso3.setNivel("Intermedio");
            curso3.setIdCarrera(idCarreraIngenieriaSistemas);
            curso1.setIdDocente(3L);
            curso3.setFechaCreacionCurso(LocalDateTime.now());

            Curso curso4 = new Curso();
            curso4.setNombre("Cálculo Diferencial");
            curso4.setCodigo("MAT201");
            curso4.setDescripcion("Conceptos y aplicaciones del cálculo diferencial");
            curso4.setCreditos(5);
            curso4.setTipo("Obligatorio");
            curso4.setNivel("Avanzado");
            curso4.setIdCarrera(idCarreraIngenieriaSistemas);
            curso1.setIdDocente(4L);
            curso4.setFechaCreacionCurso(LocalDateTime.now());

            Curso curso5 = new Curso();
            curso5.setNombre("Álgebra Lineal");
            curso5.setCodigo("MAT202");
            curso5.setDescripcion("Estudio de matrices, vectores y transformaciones lineales");
            curso5.setCreditos(4);
            curso5.setTipo("Obligatorio");
            curso5.setNivel("Intermedio");
            curso5.setIdCarrera(idCarreraIngenieriaSistemas);
            curso1.setIdDocente(5L);
            curso5.setFechaCreacionCurso(LocalDateTime.now());

            Curso curso6 = new Curso();
            curso6.setNombre("Estructura de Datos");
            curso6.setCodigo("CS201");
            curso6.setDescripcion("Fundamentos de estructuras de datos y algoritmos");
            curso6.setCreditos(4);
            curso6.setTipo("Obligatorio");
            curso6.setNivel("Intermedio");
            curso6.setIdCarrera(idCarreraIngenieriaSistemas);
            curso1.setIdDocente(6L);
            curso6.setFechaCreacionCurso(LocalDateTime.now());

            Curso curso7 = new Curso();
            curso7.setNombre("Sistemas Operativos");
            curso7.setCodigo("CS301");
            curso7.setDescripcion("Principios de diseño y funcionamiento de sistemas operativos");
            curso7.setCreditos(5);
            curso7.setTipo("Obligatorio");
            curso7.setNivel("Avanzado");
            curso7.setIdCarrera(idCarreraIngenieriaSistemas);
            curso1.setIdDocente(7L);
            curso7.setFechaCreacionCurso(LocalDateTime.now());

            Curso curso8 = new Curso();
            curso8.setNombre("Redes de Computadoras");
            curso8.setCodigo("CS302");
            curso8.setDescripcion("Introducción a los fundamentos de redes y protocolos de comunicación");
            curso8.setCreditos(4);
            curso8.setTipo("Obligatorio");
            curso8.setNivel("Avanzado");
            curso8.setIdCarrera(idCarreraIngenieriaSistemas);
            curso1.setIdDocente(8L);
            curso8.setFechaCreacionCurso(LocalDateTime.now());

            Curso curso9 = new Curso();
            curso9.setNombre("Inteligencia Artificial");
            curso9.setCodigo("AI401");
            curso9.setDescripcion("Conceptos y aplicaciones de la inteligencia artificial");
            curso9.setCreditos(5);
            curso9.setTipo("Electivo");
            curso9.setNivel("Avanzado");
            curso9.setIdCarrera(idCarreraIngenieriaSistemas);
            curso1.setIdDocente(9L);
            curso9.setFechaCreacionCurso(LocalDateTime.now());

            // ID de la carrera de Administración
            Long idCarreraAdministracion = 2L;

            // Crear cursos de la carrera de Administración
            Curso curso10 = new Curso();
            curso10.setNombre("Introducción a la Economía");
            curso10.setCodigo("ADM101");
            curso10.setDescripcion("Fundamentos de la economía y su rol en la administración");
            curso10.setCreditos(3);
            curso10.setTipo("Obligatorio");
            curso10.setNivel("Básico");
            curso10.setIdCarrera(idCarreraAdministracion);
            curso1.setIdDocente(10L);
            curso10.setFechaCreacionCurso(LocalDateTime.now());

            Curso curso11 = new Curso();
            curso11.setNombre("Contabilidad Financiera");
            curso11.setCodigo("ADM201");
            curso11.setDescripcion("Conceptos básicos de contabilidad y análisis financiero");
            curso11.setCreditos(4);
            curso11.setTipo("Obligatorio");
            curso11.setNivel("Intermedio");
            curso11.setIdCarrera(idCarreraAdministracion);
            curso1.setIdDocente(11L);
            curso11.setFechaCreacionCurso(LocalDateTime.now());

            Curso curso12 = new Curso();
            curso12.setNombre("Gestión de Recursos Humanos");
            curso12.setCodigo("ADM202");
            curso12.setDescripcion("Principios y prácticas de gestión de recursos humanos");
            curso12.setCreditos(4);
            curso12.setTipo("Obligatorio");
            curso12.setNivel("Intermedio");
            curso12.setIdCarrera(idCarreraAdministracion);
            curso1.setIdDocente(12L);
            curso12.setFechaCreacionCurso(LocalDateTime.now());

            Curso curso13 = new Curso();
            curso13.setNombre("Marketing Estratégico");
            curso13.setCodigo("ADM301");
            curso13.setDescripcion("Conceptos de marketing aplicados a la estrategia empresarial");
            curso13.setCreditos(4);
            curso13.setTipo("Electivo");
            curso13.setNivel("Avanzado");
            curso13.setIdCarrera(idCarreraAdministracion);
            curso1.setIdDocente(13L);
            curso13.setFechaCreacionCurso(LocalDateTime.now());

            Curso curso14 = new Curso();
            curso14.setNombre("Finanzas Corporativas");
            curso14.setCodigo("ADM302");
            curso14.setDescripcion("Estudio de la gestión financiera de las corporaciones");
            curso14.setCreditos(5);
            curso14.setTipo("Obligatorio");
            curso14.setNivel("Avanzado");
            curso14.setIdCarrera(idCarreraAdministracion);
            curso1.setIdDocente(14L);
            curso14.setFechaCreacionCurso(LocalDateTime.now());

            // Guardar cursos en la base de datos
            cursoRepository.saveAll(Arrays.asList(curso1, curso2, curso3, curso4, curso5, curso6, curso7, curso8, curso9, curso10, curso11, curso12, curso13, curso14));
            System.out.println("Cursos de ejemplo cargados en la base de datos.");
        } else {
            System.out.println("Los cursos ya están cargados en la base de datos.");
        }
    }
}
