package upeu.edu.pe.msdocumentopostulante.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import upeu.edu.pe.msdocumentopostulante.entity.DocumentoPostulante;
import upeu.edu.pe.msdocumentopostulante.service.DocumentoPostulanteService;

import java.util.List;

@RestController("/DocumentoPostulante")
@RequestMapping
public class DocumentoPostulanteController {
    @Autowired
    private DocumentoPostulanteService documentoPostulanteService;

    @PostMapping
    public ResponseEntity<DocumentoPostulante> guardarDocumentoPostulanteResponseEntity(@RequestBody DocumentoPostulante DocumentoPostulante) {
        return ResponseEntity.status(HttpStatus.CREATED).body(documentoPostulanteService.guardarDocumentoPostulante(DocumentoPostulante));
    }
    @GetMapping
    public ResponseEntity<List<DocumentoPostulante>> listarDocumentoPostulantesResponseEntity() {
        return ResponseEntity.status(HttpStatus.OK).body(documentoPostulanteService.listarDocumentoPostulantes());
    }
    @GetMapping("/{id}")
    public ResponseEntity<DocumentoPostulante> listarDocumentoPostulantes(@PathVariable(required = true) long id) {
        return ResponseEntity.status(HttpStatus.OK).body(documentoPostulanteService.buscarDocumentoPostulantePorId(id));
    }
    @PutMapping("/{id}")
    public ResponseEntity<DocumentoPostulante> editarDocumentoPostulante(@PathVariable(required = true) long id, @RequestBody DocumentoPostulante
            DocumentoPostulante) {
        DocumentoPostulante.setIdDocumentoPostulante(id);
        return ResponseEntity.status(HttpStatus.OK).body(documentoPostulanteService.editarDocumentoPostulante(DocumentoPostulante));
    }
    @DeleteMapping("/{id}")
    public String eliminarDocumentoPostulante(@PathVariable(required = true) long id) {
        documentoPostulanteService.eliminarDocumentoPostulante(id);
        return "DocumentoPostulante eliminado";
    }
}
