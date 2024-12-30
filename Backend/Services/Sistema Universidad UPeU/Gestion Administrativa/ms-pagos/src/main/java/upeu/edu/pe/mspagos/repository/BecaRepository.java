package upeu.edu.pe.mspagos.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import upeu.edu.pe.mspagos.entity.Beca;

@Repository
public interface BecaRepository extends JpaRepository<Beca, Long> {
}
