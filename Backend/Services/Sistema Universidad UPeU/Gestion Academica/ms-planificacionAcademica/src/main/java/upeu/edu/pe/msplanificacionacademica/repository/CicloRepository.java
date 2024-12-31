package upeu.edu.pe.msplanificacionacademica.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import upeu.edu.pe.msplanificacionacademica.entity.Ciclo;

@Repository
public interface CicloRepository extends JpaRepository<Ciclo, Long> {
}
