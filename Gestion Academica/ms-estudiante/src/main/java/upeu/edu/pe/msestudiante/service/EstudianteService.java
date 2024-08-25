package upeu.edu.pe.msestudiante.service;

import upeu.edu.pe.msestudiante.entity.Estudiante;

import java.util.List;

public interface EstudianteService {

    public Estudiante guardarEstudiante(Estudiante estudiante);

    public List<Estudiante> listarEstudiantes();

    public Estudiante buscarEstudiantePorId(Long id);

    public Estudiante editarEstudiante(Estudiante estudiante);

    public void eliminarEstudiante(Long id);
}
