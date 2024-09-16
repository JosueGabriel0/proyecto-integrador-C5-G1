package upeu.edu.pe.mscalendarioacademico.service;

import upeu.edu.pe.mscalendarioacademico.entity.CalendarioAcademico;

import java.util.List;

public interface CalendarioAcademicoService {

    public CalendarioAcademico guardarCalendarioAcademico(CalendarioAcademico CalendarioAcademico);

    public List<CalendarioAcademico> listarCalendarioAcademico();

    public CalendarioAcademico buscarCalendarioAcademicoPorId(Long id);

    public CalendarioAcademico editarCalendarioAcademico(CalendarioAcademico CalendarioAcademico);

    public void eliminarCalendarioAcademico(Long id);
}
