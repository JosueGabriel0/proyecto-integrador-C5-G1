package upeu.edu.pe.msestudiante.service;

import upeu.edu.pe.msestudiante.dto.EstudianteRequest;
import upeu.edu.pe.msestudiante.entity.Estudiante;

import java.util.List;

public interface EstudianteService {

    public Estudiante crearEstudianteConPersona(EstudianteRequest estudianteRequest);

    public Estudiante guardarEstudiante(Estudiante estudiante);

    public List<Estudiante> listarEstudiantesConPersona();

    public List<Estudiante> listarEstudiantes();

    public Estudiante listarEstudianteConPersonaPorId(Long id);

    public Estudiante listarEstudiantePorId(Long id);

    public Estudiante editarEstudianteConPersona(Long id, EstudianteRequest estudianteRequest);

    public Estudiante editarSoloEstudiante(Long id, Estudiante estudianteRequest);

    public void eliminarEstudiante(Long id);
}
