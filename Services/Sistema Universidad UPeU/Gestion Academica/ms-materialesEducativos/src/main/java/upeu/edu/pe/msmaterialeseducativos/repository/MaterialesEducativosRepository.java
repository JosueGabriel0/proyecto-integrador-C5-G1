package upeu.edu.pe.msmaterialeseducativos.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import upeu.edu.pe.msmaterialeseducativos.entity.MaterialesEducativos;

@Repository
public interface MaterialesEducativosRepository extends JpaRepository<MaterialesEducativos, Long> {

}
