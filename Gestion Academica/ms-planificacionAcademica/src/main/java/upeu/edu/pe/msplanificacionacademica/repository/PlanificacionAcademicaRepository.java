package upeu.edu.pe.msplanificacionacademica.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import upeu.edu.pe.msplanificacionacademica.entity.PlanificacionAcademica;

@Repository
public interface PlanificacionAcademicaRepository extends JpaRepository<PlanificacionAcademica, Long> {
}
