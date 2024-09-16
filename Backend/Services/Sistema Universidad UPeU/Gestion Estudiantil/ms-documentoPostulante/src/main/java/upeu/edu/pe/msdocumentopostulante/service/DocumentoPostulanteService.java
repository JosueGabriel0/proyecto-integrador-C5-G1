package upeu.edu.pe.msdocumentopostulante.service;

import lombok.Data;
import upeu.edu.pe.msdocumentopostulante.entity.DocumentoPostulante;

import java.util.List;
public interface DocumentoPostulanteService {
    public DocumentoPostulante guardarDocumentoPostulante(DocumentoPostulante documentoPostulante);
    public List<DocumentoPostulante> listarDocumentoPostulantes();
    public DocumentoPostulante buscarDocumentoPostulantePorId(long id);
    public DocumentoPostulante editarDocumentoPostulante(DocumentoPostulante documentoPostulante);
    public void eliminarDocumentoPostulante(long id);
}
