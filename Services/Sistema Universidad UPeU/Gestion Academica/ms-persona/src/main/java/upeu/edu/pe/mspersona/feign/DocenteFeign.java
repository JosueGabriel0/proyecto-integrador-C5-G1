package upeu.edu.pe.mspersona.feign;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import upeu.edu.pe.mspersona.dto.Docente;

import java.util.List;

@FeignClient(name = "ms-docente-service", path = "/docente")
public interface DocenteFeign {

    @GetMapping("/{id}")
    public ResponseEntity<Docente> listarDocenteDtoPorId(@PathVariable(required = true) Long id);

    @GetMapping
    public ResponseEntity<List<Docente>> listarDocentesDto();
}
