package upeu.edu.pe.mscuentafinancierauniversitaria.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import upeu.edu.pe.mscuentafinancierauniversitaria.entity.MovimientoAcademico;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface MovimientoAcademicoRepository extends JpaRepository<MovimientoAcademico, Long> {
    List<MovimientoAcademico> findByCuentaFinancieraIdCuentaFinanciera(Long idCuentaFinanciera);

    // Alternativamente, puedes usar una consulta más precisa con un rango de fechas
    List<MovimientoAcademico> findByCuentaFinancieraIdCuentaFinancieraAndFechaBetween(
            Long idCuentaFinanciera, LocalDate startDate, LocalDate endDate);
}
