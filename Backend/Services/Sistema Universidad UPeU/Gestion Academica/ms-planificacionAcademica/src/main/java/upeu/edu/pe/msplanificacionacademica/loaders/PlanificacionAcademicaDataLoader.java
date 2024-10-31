package upeu.edu.pe.msplanificacionacademica.loaders;

import upeu.edu.pe.msplanificacionacademica.entity.EstadoPlanificacion;
import upeu.edu.pe.msplanificacionacademica.entity.PlanificacionAcademica;
import upeu.edu.pe.msplanificacionacademica.repository.PlanificacionAcademicaRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.Arrays;

@Component
public class PlanificacionAcademicaDataLoader implements CommandLineRunner {

    private final PlanificacionAcademicaRepository planificacionAcademicaRepository;

    public PlanificacionAcademicaDataLoader(PlanificacionAcademicaRepository planificacionAcademicaRepository) {
        this.planificacionAcademicaRepository = planificacionAcademicaRepository;
    }

    @Override
    public void run(String... args) throws Exception {
        // Verificar si ya existen planificaciones en la base de datos
        if (planificacionAcademicaRepository.count() == 0) {
            // Crear planificaciones para Ingeniería de Sistemas
            PlanificacionAcademica planificacionIngenieriaSistemas2024_1 = new PlanificacionAcademica();
            planificacionIngenieriaSistemas2024_1.setNombrePlanEstudio("Plan de Estudios Ingeniería de Sistemas 2024-1");
            planificacionIngenieriaSistemas2024_1.setCodigoPlanEstudio("PLAN-ING-SIS");
            planificacionIngenieriaSistemas2024_1.setVersionPlanEstudio("2024-1");
            planificacionIngenieriaSistemas2024_1.setDescripcionGeneral("Planificación académica para la carrera de Ingeniería de Sistemas.");
            planificacionIngenieriaSistemas2024_1.setEstado(EstadoPlanificacion.ACTIVO);
            planificacionIngenieriaSistemas2024_1.setFechaCreacionPlanificacionAcademica(LocalDateTime.now());
            planificacionIngenieriaSistemas2024_1.setCursosProgramadosIds(Arrays.asList(1L, 2L, 3L, 4L, 5L, 6L, 7L, 8L, 9L)); // IDs de cursos de ejemplo
            planificacionIngenieriaSistemas2024_1.setProfesoresIds(Arrays.asList(1L, 2L, 3L, 4L, 5L, 6L, 7L, 8L, 9L)); // IDs de profesores de ejemplo
            planificacionIngenieriaSistemas2024_1.setCalendarioAcademicoIds(Arrays.asList(1L)); // IDs de eventos de ejemplo

            PlanificacionAcademica planificacionIngenieriaSistemas2024_2 = new PlanificacionAcademica();
            planificacionIngenieriaSistemas2024_2.setNombrePlanEstudio("Plan de Estudios Ingeniería de Sistemas 2024-2");
            planificacionIngenieriaSistemas2024_2.setCodigoPlanEstudio("PLAN-ING-SIS");
            planificacionIngenieriaSistemas2024_2.setVersionPlanEstudio("2024-2");
            planificacionIngenieriaSistemas2024_2.setDescripcionGeneral("Planificación académica para la carrera de Ingeniería de Sistemas.");
            planificacionIngenieriaSistemas2024_2.setEstado(EstadoPlanificacion.ACTIVO);
            planificacionIngenieriaSistemas2024_2.setFechaCreacionPlanificacionAcademica(LocalDateTime.now());
            planificacionIngenieriaSistemas2024_2.setCursosProgramadosIds(Arrays.asList(1L, 2L, 3L, 4L, 5L, 6L, 7L, 8L, 9L)); // IDs de cursos de ejemplo
            planificacionIngenieriaSistemas2024_2.setProfesoresIds(Arrays.asList(1L, 2L, 3L, 4L, 5L, 6L, 7L, 8L, 9L)); // IDs de profesores de ejemplo
            planificacionIngenieriaSistemas2024_2.setCalendarioAcademicoIds(Arrays.asList(2L)); // IDs de eventos de ejemplo

            // Crear planificación para Administración
            PlanificacionAcademica planificacionAdministracion2024_1 = new PlanificacionAcademica();
            planificacionAdministracion2024_1.setNombrePlanEstudio("Plan de Estudios Administración 2024-1");
            planificacionAdministracion2024_1.setCodigoPlanEstudio("PLAN-ADM");
            planificacionAdministracion2024_1.setVersionPlanEstudio("2024-1");
            planificacionAdministracion2024_1.setDescripcionGeneral("Planificación académica para la carrera de Administración.");
            planificacionAdministracion2024_1.setEstado(EstadoPlanificacion.ACTIVO);
            planificacionAdministracion2024_1.setFechaCreacionPlanificacionAcademica(LocalDateTime.now());
            planificacionAdministracion2024_1.setCursosProgramadosIds(Arrays.asList(10L, 11L, 12L, 13L, 14L)); // IDs de cursos de ejemplo
            planificacionAdministracion2024_1.setProfesoresIds(Arrays.asList(10L, 11L, 12L, 13L, 14L)); // IDs de profesores de ejemplo
            planificacionAdministracion2024_1.setCalendarioAcademicoIds(Arrays.asList(1L)); // IDs de eventos de ejemplo

            PlanificacionAcademica planificacionAdministracion2024_2 = new PlanificacionAcademica();
            planificacionAdministracion2024_2.setNombrePlanEstudio("Plan de Estudios Administración 2024-2");
            planificacionAdministracion2024_2.setCodigoPlanEstudio("PLAN-ADM");
            planificacionAdministracion2024_2.setVersionPlanEstudio("2024-2");
            planificacionAdministracion2024_2.setDescripcionGeneral("Planificación académica para la carrera de Administración.");
            planificacionAdministracion2024_2.setEstado(EstadoPlanificacion.ACTIVO);
            planificacionAdministracion2024_2.setFechaCreacionPlanificacionAcademica(LocalDateTime.now());
            planificacionAdministracion2024_2.setCursosProgramadosIds(Arrays.asList(10L, 11L, 12L, 13L, 14L)); // IDs de cursos de ejemplo
            planificacionAdministracion2024_2.setProfesoresIds(Arrays.asList(10L, 11L, 12L, 13L, 14L)); // IDs de profesores de ejemplo
            planificacionAdministracion2024_2.setCalendarioAcademicoIds(Arrays.asList(2L)); // IDs de eventos de ejemplo

            // Guardar las planificaciones en la base de datos
            planificacionAcademicaRepository.saveAll(Arrays.asList(planificacionIngenieriaSistemas2024_1, planificacionAdministracion2024_1));
            System.out.println("Planificaciones académicas de ejemplo cargadas en la base de datos.");
        } else {
            System.out.println("Las planificaciones académicas ya están cargadas en la base de datos.");
        }
    }
}
