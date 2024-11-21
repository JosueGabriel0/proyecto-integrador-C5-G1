package upeu.edu.pe.msestudiante.loaders;

import upeu.edu.pe.msestudiante.entity.EstadoEstudiante;
import upeu.edu.pe.msestudiante.entity.Estudiante;
import upeu.edu.pe.msestudiante.entity.RegistroAcademico;
import upeu.edu.pe.msestudiante.repository.EstudianteRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;

@Component
public class EstudianteDataLoader implements CommandLineRunner {

    private final EstudianteRepository estudianteRepository;

    public EstudianteDataLoader(EstudianteRepository estudianteRepository) {
        this.estudianteRepository = estudianteRepository;
    }

    @Override
    public void run(String... args) throws Exception {
        // Verificar si ya existen estudiantes en la base de datos
        if (estudianteRepository.count() == 0) {
            // Crear estudiante 1
            Estudiante estudiante1 = new Estudiante();
            estudiante1.setMatricula("2024-001");
            estudiante1.setCicloActual(1);
            estudiante1.setPromedioGeneral(15.5);
            estudiante1.setFechaIngreso(LocalDate.of(2024, 3, 1));
            estudiante1.setEstado(EstadoEstudiante.ACTIVO);
            estudiante1.setTipoEstudiante("Regular");
            estudiante1.setBeca("Beca 100%");
            estudiante1.setNumeroMatricula("123456");
            estudiante1.setCarrerasIngresadas(Arrays.asList("Ingeniería de Sistemas", "Administración"));
            estudiante1.setAsignaturasMatriculadas(Arrays.asList("Matemáticas", "Programación"));
            estudiante1.setHorario("Lunes a Viernes 8am - 2pm");
            estudiante1.setConsejeroAcademico("Prof. Juan Pérez");
            estudiante1.setFechaGraduacion(LocalDate.of(2028, 12, 1));
            estudiante1.setPracticasRealizadas(Arrays.asList("Práctica 1", "Práctica 2"));
            estudiante1.setHistorialAcademico(createHistorialAcademico(estudiante1));
            estudiante1.setIdPersona(4L);

            // Crear estudiante 2
            Estudiante estudiante2 = new Estudiante();
            estudiante2.setMatricula("2024-002");
            estudiante2.setCicloActual(2);
            estudiante2.setPromedioGeneral(14.0);
            estudiante2.setFechaIngreso(LocalDate.of(2024, 3, 1));
            estudiante2.setEstado(EstadoEstudiante.ACTIVO);
            estudiante2.setTipoEstudiante("Regular");
            estudiante2.setBeca("Beca parcial");
            estudiante2.setNumeroMatricula("654321");
            estudiante2.setCarrerasIngresadas(Arrays.asList("Ingeniería de Sistemas"));
            estudiante2.setAsignaturasMatriculadas(Arrays.asList("Química", "Física"));
            estudiante2.setHorario("Lunes a Viernes 9am - 3pm");
            estudiante2.setConsejeroAcademico("Prof. María López");
            estudiante2.setFechaGraduacion(LocalDate.of(2028, 12, 1));
            estudiante2.setPracticasRealizadas(Arrays.asList("Práctica 3"));
            estudiante2.setHistorialAcademico(createHistorialAcademico(estudiante2));
            estudiante2.setIdPersona(5L);

            // Guardar estudiantes en la base de datos
            estudianteRepository.saveAll(Arrays.asList(estudiante1, estudiante2));
            System.out.println("Estudiantes de ejemplo cargados en la base de datos.");
        } else {
            System.out.println("Los estudiantes ya están cargados en la base de datos.");
        }
    }

    private List<RegistroAcademico> createHistorialAcademico(Estudiante estudiante) {
        // Crear historial académico de ejemplo para el estudiante
        RegistroAcademico registro1 = new RegistroAcademico();
        registro1.setEstudiante(estudiante);
        registro1.setCursos(Arrays.asList(101L, 102L)); // IDs de cursos de ejemplo
        registro1.setNombreCurso("Matemáticas");
        registro1.setCalificacion(16.0);
        registro1.setFechaFinalizacion(LocalDate.of(2024, 6, 30));

        RegistroAcademico registro2 = new RegistroAcademico();
        registro2.setEstudiante(estudiante);
        registro2.setCursos(Arrays.asList(103L)); // ID de curso de ejemplo
        registro2.setNombreCurso("Programación");
        registro2.setCalificacion(18.5);
        registro2.setFechaFinalizacion(LocalDate.of(2024, 12, 15));

        return Arrays.asList(registro1, registro2);
    }
}
