package upeu.edu.pe.msdocente.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import upeu.edu.pe.msdocente.entity.Docente;

@Repository
public interface DocenteRepository extends JpaRepository<Docente, Long> {

}
