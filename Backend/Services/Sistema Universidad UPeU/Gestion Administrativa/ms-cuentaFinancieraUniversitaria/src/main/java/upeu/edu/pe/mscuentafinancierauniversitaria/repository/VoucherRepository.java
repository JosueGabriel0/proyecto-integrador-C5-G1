package upeu.edu.pe.mscuentafinancierauniversitaria.repository;

import jakarta.persistence.LockModeType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Lock;
import org.springframework.stereotype.Repository;
import upeu.edu.pe.mscuentafinancierauniversitaria.entity.Voucher;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface VoucherRepository extends JpaRepository<Voucher, Long> {
    List<Voucher> findByCuentaFinancieraIdCuentaFinanciera(Long idCuentaFinanciera);

    List<Voucher> findByCuentaFinancieraIdCuentaFinancieraAndFechaDeOperacionBetween(
            Long idCuentaFinanciera, LocalDate startDate, LocalDate endDate);

    @Lock(LockModeType.PESSIMISTIC_WRITE)
    List<Voucher> findByEstado(String estado);
}
