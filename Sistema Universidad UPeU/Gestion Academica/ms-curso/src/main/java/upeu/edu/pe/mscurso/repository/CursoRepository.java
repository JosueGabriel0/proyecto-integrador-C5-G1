package upeu.edu.pe.mscurso.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import upeu.edu.pe.mscurso.entity.Curso;

@Repository
public interface CursoRepository extends JpaRepository<Curso, Long> {

}
