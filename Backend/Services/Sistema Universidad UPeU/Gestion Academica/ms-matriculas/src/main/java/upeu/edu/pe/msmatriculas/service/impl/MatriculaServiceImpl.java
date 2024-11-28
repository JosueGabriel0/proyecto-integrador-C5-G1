package upeu.edu.pe.msmatriculas.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import upeu.edu.pe.msmatriculas.dto.Pago;
import upeu.edu.pe.msmatriculas.entity.EstadoMatricula;
import upeu.edu.pe.msmatriculas.entity.Matricula;
import upeu.edu.pe.msmatriculas.feign.*;
import upeu.edu.pe.msmatriculas.repository.MatriculaRepository;
import upeu.edu.pe.msmatriculas.service.MatriculaService;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class MatriculaServiceImpl implements MatriculaService {

    private final MatriculaRepository matriculaRepository;

    // Feign Clients
    @Autowired
    private EstudianteFeign estudianteFeign;
    @Autowired
    private CarreraFeign carreraFeign;
    @Autowired
    private CalendarioAcademicoFeign calendarioAcademicoFeign;
    @Autowired
    private PagoFeign pagoFeign;
    @Autowired
    private RequisitoFeign requisitoFeign;
    @Autowired
    private AdministrativoFeign administrativoFeign;
    @Autowired
    private DocenteFeign docenteFeign;
    @Autowired
    private CursoFeign cursoFeign;

    // Crear nueva matrícula con validaciones adicionales
    @Override
    public Matricula crearMatricula(Matricula matricula) {
        // Validar entidades relacionadas
        validarEntidadExistente(matricula.getIdEstudiante(), "Estudiante", estudianteFeign::listarEstudianteDtoPorId);
        validarEntidadExistente(matricula.getIdCarrera(), "Carrera", carreraFeign::listarCarreraDtoPorId);
        validarEntidadExistente(matricula.getIdCalendarioAcademico(), "Calendario Académico", calendarioAcademicoFeign::listarCalendarioAcademicoDtoPorId);

        // Verificar requisitos académicos
        boolean requisitosCumplidos = requisitoFeign.validarRequisitos(matricula.getIdEstudiante(), matricula.getIdCarrera()).getBody();
        if (!requisitosCumplidos) {
            throw new RuntimeException("El estudiante no cumple con los requisitos académicos necesarios.");
        }

        // Validar pago
        Pago pago = pagoFeign.listarPagoDtoPorId(matricula.getIdPago()).getBody();
        if (pago == null || !pago.getEstado().equalsIgnoreCase("APROBADO")) {
            throw new RuntimeException("El pago no es válido o no ha sido aprobado.");
        }

        // Validar cursos seleccionados
        if (!matricula.getCursos().isEmpty()) {
            matricula.getCursos().forEach(idCurso -> {
                validarEntidadExistente(idCurso, "Curso", cursoFeign::listarCursoDtoPorId);
            });
        }

        // Lógica adicional: validar matrícula única
        if (matriculaRepository.existsByIdEstudianteAndIdCarreraAndIdCalendarioAcademico(
                matricula.getIdEstudiante(), matricula.getIdCarrera(), matricula.getIdCalendarioAcademico())) {
            throw new RuntimeException("El estudiante ya está matriculado en esta carrera para el periodo seleccionado.");
        }

        // Asignar datos automáticos
        matricula.setFechaMatricula(LocalDateTime.now());
        matricula.setEstado(EstadoMatricula.PENDIENTE);
        return matriculaRepository.save(matricula);
    }

    // Obtener matrícula por ID
    @Override
    public Matricula obtenerMatriculaPorId(Long idMatricula) {
        Matricula matricula = matriculaRepository.findById(idMatricula)
                .orElseThrow(() -> new RuntimeException("Matrícula no encontrada con ID: " + idMatricula));
        completarDatosTransitorios(matricula);
        return matricula;
    }

    // Obtener todas las matrículas
    @Override
    public List<Matricula> obtenerMatriculas() {
        List<Matricula> matriculas = matriculaRepository.findAll();
        matriculas.forEach(this::completarDatosTransitorios);
        return matriculas;
    }

    // Actualizar matrícula con validaciones adicionales
    @Override
    public Matricula actualizarMatricula(Long idMatricula, Matricula nuevaMatricula) {
        Matricula matriculaExistente = obtenerMatriculaPorId(idMatricula);

        // Validar actualización de estado
        if (nuevaMatricula.getEstado() != null) {
            if (nuevaMatricula.getEstado() == EstadoMatricula.CANCELADO
                    && matriculaExistente.getEstado() != EstadoMatricula.PENDIENTE) {
                throw new RuntimeException("Solo las matrículas pendientes pueden ser canceladas.");
            }
            matriculaExistente.setEstado(nuevaMatricula.getEstado());
        }

        // Actualizar campos permitidos
        matriculaExistente.setObservaciones(nuevaMatricula.getObservaciones());
        return matriculaRepository.save(matriculaExistente);
    }

    // Eliminar matrícula con validación de estado
    @Override
    public void eliminarMatricula(Long idMatricula) {
        Matricula matricula = obtenerMatriculaPorId(idMatricula);
        if (matricula.getEstado() != EstadoMatricula.PENDIENTE) {
            throw new RuntimeException("Solo las matrículas pendientes pueden ser eliminadas.");
        }
        matriculaRepository.deleteById(idMatricula);
    }

    // Métodos auxiliares
    private <T> void validarEntidadExistente(Long id, String nombreEntidad, FeignFunction<Long, T> feignFunction) {
        T entidad = feignFunction.apply(id);
        if (entidad == null) {
            throw new RuntimeException(nombreEntidad + " no encontrado con ID: " + id);
        }
    }

    private void completarDatosTransitorios(Matricula matricula) {
        matricula.setEstudiante(estudianteFeign.listarEstudianteDtoPorId(matricula.getIdEstudiante()).getBody());
        matricula.setCarrera(carreraFeign.listarCarreraDtoPorId(matricula.getIdCarrera()).getBody());
        matricula.setCalendarioAcademico(calendarioAcademicoFeign.listarCalendarioAcademicoDtoPorId(matricula.getIdCalendarioAcademico()).getBody());
        matricula.setPago(pagoFeign.listarPagoDtoPorId(matricula.getIdPago()).getBody());
    }

    @FunctionalInterface
    private interface FeignFunction<T, R> {
        R apply(T t);
    }
}