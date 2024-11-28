package upeu.edu.pe.msrequisitosacademicos.service;

import org.bouncycastle.cert.ocsp.Req;
import upeu.edu.pe.msrequisitosacademicos.entity.Requisito;

import java.util.List;

public interface RequisitoService {
    public Requisito guardarRequisito(Requisito requisito);

    public List<Requisito> listarRequisitos();

    public Requisito buscarRequisitoPorId(Long id);

    public Requisito editarRequisito(Requisito requisito);

    public void eliminarRequisito(Long id);

    public boolean validarRequisitos(Long idEstudiante, Long idCarrera);
}
