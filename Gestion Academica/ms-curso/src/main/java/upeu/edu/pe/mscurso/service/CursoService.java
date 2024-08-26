package upeu.edu.pe.mscurso.service;

import upeu.edu.pe.mscurso.entity.Curso;

import java.util.List;

public interface CursoService {

    public Curso crearCurso(Curso curso);

    public List<Curso> listarCursos();

    public Curso listarCursosPorId(Long id);

    public Curso editarCurso(Curso curso);

    public void eliminarCurso(Long id);
}
