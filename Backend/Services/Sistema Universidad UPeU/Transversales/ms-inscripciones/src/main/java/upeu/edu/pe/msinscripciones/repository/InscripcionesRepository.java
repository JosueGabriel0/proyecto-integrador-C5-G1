package upeu.edu.pe.msinscripciones.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import upeu.edu.pe.msinscripciones.entity.Inscripcion;

import java.util.Optional;

@Repository
public interface InscripcionesRepository extends JpaRepository<Inscripcion, Long> {
    Optional<Inscripcion> findByIdUsuario(Long idUsuario);
}
