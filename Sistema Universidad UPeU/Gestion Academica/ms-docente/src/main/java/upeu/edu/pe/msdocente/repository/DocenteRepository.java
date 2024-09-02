package upeu.edu.pe.msdocente.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import upeu.edu.pe.msdocente.entity.Docente;

public interface DocenteRepository extends JpaRepository<Docente, Long> {

}
