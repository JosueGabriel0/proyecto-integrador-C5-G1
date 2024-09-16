package upeu.edu.pe.msdocumentopostulante.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import upeu.edu.pe.msdocumentopostulante.entity.DocumentoPostulante;
import upeu.edu.pe.msdocumentopostulante.service.DocumentoPostulanteService;

import java.util.List;

@Service
public class DocumentoPostulanteServiceImpl implements DocumentoPostulanteService {
    @Autowired
    DocumentoPostulante documentoPostulante;
    @Override
    public DocumentoPostulante guardarDocumentoPostulante(DocumentoPostulante DocumentoPostulante) {
        return documentoPostulante.save(DocumentoPostulante);
    }

    @Override
    public List<DocumentoPostulante> listarDocumentoPostulantes() {
        return documentoPostulante.findAll();
    }

    @Override
    public DocumentoPostulante buscarDocumentoPostulantePorId(long id) {
        return documentoPostulante.findById(id).get();
    }

    @Override
    public DocumentoPostulante editarDocumentoPostulante(DocumentoPostulante DocumentoPostulante) {
        return documentoPostulante.save(DocumentoPostulante);
    }

    @Override
    public void eliminarDocumentoPostulante(long id) {
        documentoPostulante.deleteById(id);
    }
}
