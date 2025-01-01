package upeu.edu.pe.mscuentafinancierauniversitaria.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import upeu.edu.pe.mscuentafinancierauniversitaria.entity.CuentaFinanciera;

@Repository
public interface CuentaFinancieraRepository extends JpaRepository<CuentaFinanciera, Long> {
}
