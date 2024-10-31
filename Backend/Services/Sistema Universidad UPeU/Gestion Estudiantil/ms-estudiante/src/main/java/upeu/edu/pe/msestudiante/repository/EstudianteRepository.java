package upeu.edu.pe.msestudiante.repository;

import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import upeu.edu.pe.msestudiante.entity.Estudiante;

import java.util.List;
import java.util.Optional;

@Repository
public interface EstudianteRepository extends JpaRepository<Estudiante, Long> {
    @Query("SELECT e FROM Estudiante e LEFT JOIN FETCH e.historialAcademico")
    List<Estudiante> findAllWithHistorial();
}
