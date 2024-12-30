package upeu.edu.pe.msnivelesdeensenanza.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import upeu.edu.pe.msnivelesdeensenanza.entity.NivelEnsenanza;

import java.util.Optional;

@Repository
public interface NivelEnsenanzaRepository extends JpaRepository<NivelEnsenanza, Long> {
    Optional<NivelEnsenanza> findByNombre(String nombre);
}