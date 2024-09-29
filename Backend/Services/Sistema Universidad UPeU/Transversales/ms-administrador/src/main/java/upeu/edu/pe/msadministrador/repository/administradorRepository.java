package upeu.edu.pe.msadministrador.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import upeu.edu.pe.msadministrador.entity.Administrador;

@Repository
public interface administradorRepository extends JpaRepository<Administrador, Long> {

}
