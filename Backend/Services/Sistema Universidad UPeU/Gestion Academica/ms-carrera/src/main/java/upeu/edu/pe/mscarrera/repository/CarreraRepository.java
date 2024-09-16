package upeu.edu.pe.mscarrera.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import upeu.edu.pe.mscarrera.entity.Carrera;

@Repository
public interface CarreraRepository extends JpaRepository<Carrera, Long> {

}
