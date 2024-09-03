package upeu.edu.pe.mscalendarioacademico.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import upeu.edu.pe.mscalendarioacademico.entity.CalendarioAcademico;

@Repository
public interface CalendarioAcademicoRepository extends JpaRepository<CalendarioAcademico, Long> {

}
