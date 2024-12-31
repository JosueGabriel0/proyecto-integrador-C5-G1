package upeu.edu.pe.msmatriculas.service.impl;

import feign.FeignException;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import upeu.edu.pe.msmatriculas.dto.*;
import upeu.edu.pe.msmatriculas.entity.EstadoMatricula;
import upeu.edu.pe.msmatriculas.entity.Matricula;
import upeu.edu.pe.msmatriculas.exception.ResourceNotFoundException;
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
    @Autowired
    private InscripcionFeign inscripcionFeign;
    @Autowired
    private NivelEnsenanzaFeign nivelEnsenanzaFeign;
    @Autowired
    private OpcionNivelFeign opcionNivelFeign;

    // Crear nueva matrícula con validaciones adicionales
    @Override
    public Matricula crearMatricula(Matricula matricula) {
        return matriculaRepository.save(matricula);
    }

    // Obtener matrícula por ID
    @Override
    public Matricula obtenerMatriculaPorId(Long idMatricula) {
        // Buscar la matricula por ID en el repositorio
        Matricula matricula = matriculaRepository.findById(idMatricula).orElseThrow(() -> new ResourceNotFoundException("Matricula con ID " + idMatricula + " no existe"));

        try {
            // Obtener el Nivel de Ensenanza del usuario usando Feign
            ResponseEntity<NivelEnsenanza> nivelEnsenanzaResponse = nivelEnsenanzaFeign.listarNivelDeEnsenanzaDtoPorId(matricula.getIdNivelEnsenanza());
            if (nivelEnsenanzaResponse.getBody() == null) {
                // Manejar el caso en el que el rol no existe
                throw new ResourceNotFoundException("Nivel de Ensenanza con ID " + matricula.getIdNivelEnsenanza() + " no existe");
            }
            matricula.setNivelEnsenanza(nivelEnsenanzaResponse.getBody());
        } catch (FeignException e) {
            // Manejar el error en el servidor de OpenFeign para rol
            throw new RuntimeException("Error al obtener el Nivel de Ensenanza con ID " + matricula.getIdNivelEnsenanza(), e);
        }

        try {
            ResponseEntity<Estudiante> estudianteResponse = estudianteFeign.listarEstudianteDtoPorId(matricula.getIdEstudiante());
            if (estudianteResponse.getBody() == null) {
                throw new ResourceNotFoundException("Estudiante con ID " + matricula.getIdEstudiante() + " no existe");
            }
            matricula.setEstudiante(estudianteResponse.getBody());
        } catch (FeignException e) {
            throw new RuntimeException("Error al obtener el Estudiante con ID " + matricula.getIdEstudiante(), e);
        }

        try {
            ResponseEntity<Carrera> carreraResponse = carreraFeign.listarCarreraDtoPorId(matricula.getIdCarrera());
            if (carreraResponse.getBody() == null) {
                throw new ResourceNotFoundException("Carrera con ID " + matricula.getIdCarrera() + " no existe");
            }
            matricula.setCarrera(carreraResponse.getBody());
        } catch (FeignException e) {
            throw new RuntimeException("Error al obtener la Carrera con ID " + matricula.getIdCarrera(), e);
        }

        try {
            ResponseEntity<CalendarioAcademico> calendarioAcademicoResponse = calendarioAcademicoFeign.listarCalendarioAcademicoDtoPorId(matricula.getIdCalendarioAcademico());
            if (calendarioAcademicoResponse.getBody() == null) {
                throw new ResourceNotFoundException("Rol con ID " + matricula.getIdCalendarioAcademico() + " no existe");
            }
            matricula.setCalendarioAcademico(calendarioAcademicoResponse.getBody());
        } catch (FeignException e) {
            throw new RuntimeException("Error al obtener el Rol con ID " + matricula.getIdCalendarioAcademico(), e);
        }

        try {

            ResponseEntity<Pago> rolResponse = rolFeign.listarRolDtoPorId(usuario.getIdRol());
            if (rolResponse.getBody() == null) {
                throw new ResourceNotFoundException("Rol con ID " + usuario.getIdRol() + " no existe");
            }
            usuario.setRol(rolResponse.getBody());
        } catch (FeignException e) {
            throw new RuntimeException("Error al obtener el Rol con ID " + usuario.getIdRol(), e);
        }

        return matricula;
    }

    // Obtener todas las matrículas
    @Override
    public List<Matricula> obtenerMatriculas() {
        return matriculaRepository.findAll();
    }

    // Actualizar matrícula con validaciones adicionales
    @Override
    public Matricula actualizarMatricula(Matricula matricula) {
        return matriculaRepository.save(matricula);
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

    @Override
    public boolean validarEstudiante(Long idInscripcion) {
        Inscripcion inscripcionResponse = inscripcionFeign.listarInscripcionPorId(idInscripcion).getBody();

        // Verificar nullabilidad
        if (inscripcionResponse != null && inscripcionResponse.getRol() != null && "ESTUDIANTE".equals(inscripcionResponse.getRol().getNombreRol())) {
            return true;
        } else {
            return false;
        }
    }

}