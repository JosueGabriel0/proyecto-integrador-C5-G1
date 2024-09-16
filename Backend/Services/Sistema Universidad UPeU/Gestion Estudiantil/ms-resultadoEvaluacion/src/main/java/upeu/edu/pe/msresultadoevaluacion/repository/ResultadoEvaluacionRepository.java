package upeu.edu.pe.msresultadoevaluacion.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import upeu.edu.pe.msresultadoevaluacion.entity.ResultadoEvaluacion;

@Repository
public interface ResultadoEvaluacionRepository extends JpaRepository<ResultadoEvaluacion, Long> {
}
