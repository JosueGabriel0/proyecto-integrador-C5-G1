package upeu.edu.pe.msplanificacionacademica.service;

import upeu.edu.pe.msplanificacionacademica.entity.PlanificacionAcademica;

import java.util.List;

public interface PlanificacionAcademicaService {

    public PlanificacionAcademica guardarPlanificacionAcademica(PlanificacionAcademica planificacionAcademica);
    public List<PlanificacionAcademica> listarPlanificacionAcademica();
    public PlanificacionAcademica buscarPlanificacionAcademicaPorId(long id);
    public PlanificacionAcademica editarPlanificacionAcademica(PlanificacionAcademica planificacionAcademica);
    public void eliminarPlanificacionAcademica(long id);
}
