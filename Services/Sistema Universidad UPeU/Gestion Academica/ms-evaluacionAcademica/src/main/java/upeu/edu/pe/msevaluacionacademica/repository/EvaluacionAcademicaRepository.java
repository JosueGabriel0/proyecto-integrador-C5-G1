package upeu.edu.pe.msevaluacionacademica.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import upeu.edu.pe.msevaluacionacademica.entity.EvaluacionAcademica;

@Repository
public interface EvaluacionAcademicaRepository extends JpaRepository<EvaluacionAcademica, Long> {

}
