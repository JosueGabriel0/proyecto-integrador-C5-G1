package upeu.edu.pe.mspersona.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import upeu.edu.pe.mspersona.entity.Persona;

@Repository
public interface PersonaRepository extends JpaRepository<Persona, Long> {

}
