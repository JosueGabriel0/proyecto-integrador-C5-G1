package upeu.edu.pe.msevaluador.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import upeu.edu.pe.msevaluador.entity.Evaluador;


@Repository
public interface EvaluadorRepository extends JpaRepository<Evaluador, Long> {
}
