package upeu.edu.pe.mscuentafinancierauniversitaria.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import upeu.edu.pe.mscuentafinancierauniversitaria.entity.Voucher;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface VoucherRepository extends JpaRepository<Voucher, Long> {
    List<Voucher> findByFechaDeOperacionBetween(LocalDate startDate, LocalDate endDate);
}
