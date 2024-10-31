package upeu.edu.pe.mscalendarioacademico.loaders;

import upeu.edu.pe.mscalendarioacademico.entity.CalendarioAcademico;
import upeu.edu.pe.mscalendarioacademico.entity.EstadoCalendario;
import upeu.edu.pe.mscalendarioacademico.entity.FechaImportante;
import upeu.edu.pe.mscalendarioacademico.repository.CalendarioAcademicoRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Arrays;

@Component
public class CalendarioAcademicoDataLoader implements CommandLineRunner {

    private final CalendarioAcademicoRepository calendarioAcademicoRepository;

    public CalendarioAcademicoDataLoader(CalendarioAcademicoRepository calendarioAcademicoRepository) {
        this.calendarioAcademicoRepository = calendarioAcademicoRepository;
    }

    @Override
    public void run(String... args) throws Exception {
        // Verificar si ya existen calendarios académicos en la base de datos
        if (calendarioAcademicoRepository.count() == 0) {
            // Crear calendario académico para el semestre 1
            CalendarioAcademico calendarioSemestre1 = new CalendarioAcademico();
            calendarioSemestre1.setAnioAcademico(2024);
            calendarioSemestre1.setPeriodo("Semestre 1");
            calendarioSemestre1.setEstado(EstadoCalendario.ACTIVO); // Estado activo
            calendarioSemestre1.setDescripcion("Calendario Académico para el primer semestre de 2024.");
            calendarioSemestre1.setFechaCreacionCalendarioAcademico(LocalDateTime.now());

            // Crear fechas importantes para el semestre 1
            FechaImportante fechaInicioClases1 = new FechaImportante();
            fechaInicioClases1.setDescripcion("Inicio de Clases");
            fechaInicioClases1.setFecha(LocalDate.of(2024, 3, 1));
            fechaInicioClases1.setCalendarioAcademico(calendarioSemestre1);

            FechaImportante fechaFinClases1 = new FechaImportante();
            fechaFinClases1.setDescripcion("Finalización de Clases");
            fechaFinClases1.setFecha(LocalDate.of(2024, 6, 30));
            fechaFinClases1.setCalendarioAcademico(calendarioSemestre1);

            // Agregar fechas importantes al calendario
            calendarioSemestre1.setFechasImportantes(Arrays.asList(fechaInicioClases1, fechaFinClases1));

            // Crear calendario académico para el semestre 2
            CalendarioAcademico calendarioSemestre2 = new CalendarioAcademico();
            calendarioSemestre2.setAnioAcademico(2024);
            calendarioSemestre2.setPeriodo("Semestre 2");
            calendarioSemestre2.setEstado(EstadoCalendario.EN_PREPARACION); // Estado en preparación
            calendarioSemestre2.setDescripcion("Calendario Académico para el segundo semestre de 2024.");
            calendarioSemestre2.setFechaCreacionCalendarioAcademico(LocalDateTime.now());

            // Crear fechas importantes para el semestre 2
            FechaImportante fechaInicioClases2 = new FechaImportante();
            fechaInicioClases2.setDescripcion("Inicio de Clases");
            fechaInicioClases2.setFecha(LocalDate.of(2024, 8, 1));
            fechaInicioClases2.setCalendarioAcademico(calendarioSemestre2);

            FechaImportante fechaFinClases2 = new FechaImportante();
            fechaFinClases2.setDescripcion("Finalización de Clases");
            fechaFinClases2.setFecha(LocalDate.of(2024, 12, 15));
            fechaFinClases2.setCalendarioAcademico(calendarioSemestre2);

            // Agregar fechas importantes al calendario
            calendarioSemestre2.setFechasImportantes(Arrays.asList(fechaInicioClases2, fechaFinClases2));

            // Guardar calendarios en la base de datos
            calendarioAcademicoRepository.saveAll(Arrays.asList(calendarioSemestre1, calendarioSemestre2));
            System.out.println("Calendarios académicos de ejemplo cargados en la base de datos.");
        } else {
            System.out.println("Los calendarios académicos ya están cargados en la base de datos.");
        }
    }
}
