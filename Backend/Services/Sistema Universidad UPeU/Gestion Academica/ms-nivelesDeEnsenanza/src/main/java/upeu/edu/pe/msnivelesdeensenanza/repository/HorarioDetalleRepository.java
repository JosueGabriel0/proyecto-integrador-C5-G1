package upeu.edu.pe.msnivelesdeensenanza.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import upeu.edu.pe.msnivelesdeensenanza.entity.HorarioDetalle;

@Repository
public interface HorarioDetalleRepository extends JpaRepository<HorarioDetalle, Long> {
}
