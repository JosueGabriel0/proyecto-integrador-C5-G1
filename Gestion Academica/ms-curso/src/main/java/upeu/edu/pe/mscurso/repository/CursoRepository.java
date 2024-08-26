package upeu.edu.pe.mscurso.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import upeu.edu.pe.mscurso.entity.Curso;

public interface CursoRepository extends JpaRepository<Curso, Long> {

}
