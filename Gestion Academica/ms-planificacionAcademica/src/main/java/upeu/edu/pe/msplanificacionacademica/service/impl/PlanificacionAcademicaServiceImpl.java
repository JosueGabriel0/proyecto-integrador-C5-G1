package upeu.edu.pe.msplanificacionacademica.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import upeu.edu.pe.msplanificacionacademica.entity.PlanificacionAcademica;
import upeu.edu.pe.msplanificacionacademica.repository.PlanificacionAcademicaRepository;
import upeu.edu.pe.msplanificacionacademica.service.PlanificacionAcademicaService;

import java.util.List;

@Service
public class PlanificacionAcademicaServiceImpl implements PlanificacionAcademicaService {

    @Autowired
    PlanificacionAcademicaRepository planificacionAcademicaRepository;

    @Override
    public PlanificacionAcademica guardarPlanificacionAcademica(PlanificacionAcademica planificacionAcademica) {
        return planificacionAcademicaRepository.save(planificacionAcademica);
    }

    @Override
    public List<PlanificacionAcademica> listarPlanificacionAcademica() {
        return planificacionAcademicaRepository.findAll();
    }

    @Override
    public PlanificacionAcademica buscarPlanificacionAcademicaPorId(long id) {
        return planificacionAcademicaRepository.findById(id).get();
    }

    @Override
    public PlanificacionAcademica editarPlanificacionAcademica(PlanificacionAcademica planificacionAcademica) {
        return planificacionAcademicaRepository.save(planificacionAcademica);
    }

    @Override
    public void eliminarPlanificacionAcademica(long id) {
        planificacionAcademicaRepository.deleteById(id);
    }
}
