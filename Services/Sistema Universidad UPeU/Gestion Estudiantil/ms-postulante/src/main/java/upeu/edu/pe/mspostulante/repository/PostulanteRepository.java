package upeu.edu.pe.mspostulante.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import upeu.edu.pe.mspostulante.entity.Postulante;

@Repository
public interface PostulanteRepository extends JpaRepository<Postulante, Long> {
}
