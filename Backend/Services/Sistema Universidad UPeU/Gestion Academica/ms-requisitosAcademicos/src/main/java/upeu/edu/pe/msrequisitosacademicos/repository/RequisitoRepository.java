package upeu.edu.pe.msrequisitosacademicos.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import upeu.edu.pe.msrequisitosacademicos.entity.Requisito;

import java.util.List;

@Repository
public interface RequisitoRepository extends JpaRepository<Requisito, Long> {
    /**
     * Buscar los requisitos asociados a una carrera específica.
     * @param idCarrera ID de la carrera.
     * @return Lista de requisitos asociados a la carrera.
     */
    List<Requisito> findByIdCarrera(Long idCarrera);
}
