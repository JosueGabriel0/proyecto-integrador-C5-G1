package upeu.edu.pe.msroles.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import upeu.edu.pe.msroles.entity.Rol;

@Repository
public interface RolRepository extends JpaRepository<Rol, Long> {

}
