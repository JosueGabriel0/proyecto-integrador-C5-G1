package upeu.edu.pe.mspagos.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import upeu.edu.pe.mspagos.entity.Factura;

@Repository
public interface FacturaRepository extends JpaRepository<Factura, Long> {
}
