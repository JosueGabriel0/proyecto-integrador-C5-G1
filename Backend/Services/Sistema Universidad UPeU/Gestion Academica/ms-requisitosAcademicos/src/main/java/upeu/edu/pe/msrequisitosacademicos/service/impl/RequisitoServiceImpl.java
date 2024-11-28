package upeu.edu.pe.msrequisitosacademicos.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import upeu.edu.pe.msrequisitosacademicos.entity.Requisito;
import upeu.edu.pe.msrequisitosacademicos.repository.RequisitoRepository;
import upeu.edu.pe.msrequisitosacademicos.service.RequisitoService;

import java.util.List;

@Service
public class RequisitoServiceImpl implements RequisitoService {
    @Autowired
    private RequisitoRepository requisitoRepository;

    @Override
    public Requisito guardarRequisito(Requisito requisito) {
        return requisitoRepository.save(requisito);
    }

    @Override
    public List<Requisito> listarRequisitos(){
        return requisitoRepository.findAll();
    }

    @Override
    public Requisito buscarRequisitoPorId(Long id){
        return requisitoRepository.findById(id).get();
    }

    @Override
    public Requisito editarRequisito(Requisito requisito) {
        return requisitoRepository.save(requisito);
    }

    @Override
    public void eliminarRequisito(Long id){
        requisitoRepository.deleteById(id);
    }

    /**
     * Validar si un estudiante cumple con los requisitos de una carrera.
     * @param idEstudiante ID del estudiante
     * @param idCarrera ID de la carrera
     * @return true si cumple con los requisitos, false en caso contrario.
     */
    @Override
    public boolean validarRequisitos(Long idEstudiante, Long idCarrera) {
        // Obtener la lista de requisitos de la carrera
        List<Requisito> requisitosCarrera = requisitoRepository.findByIdCarrera(idCarrera);
        if (requisitosCarrera.isEmpty()) {
            return true; // Si no hay requisitos, se considera que los cumple
        }

        // Verificar si el estudiante cumple con los requisitos
        for (Requisito requisito : requisitosCarrera) {
            if (!cumpleRequisito(idEstudiante, requisito)) {
                return false; // Si no cumple con algún requisito, retorna false
            }
        }
        return true; // Cumple con todos los requisitos
    }

    /**
     * Verificar si un estudiante cumple con un requisito específico.
     * Este método se puede implementar con lógica específica o usando datos de otra fuente.
     * @param idEstudiante ID del estudiante
     * @param requisito Requisito a verificar
     * @return true si cumple el requisito, false en caso contrario
     */
    private boolean cumpleRequisito(Long idEstudiante, Requisito requisito) {
        // Aquí va la lógica para verificar si el estudiante cumple con el requisito.
        // Por ejemplo, podría conectarse a otro servicio o consultar una tabla relacionada.
        // Por ahora, devolvemos true como predeterminado.
        return true;
    }
}