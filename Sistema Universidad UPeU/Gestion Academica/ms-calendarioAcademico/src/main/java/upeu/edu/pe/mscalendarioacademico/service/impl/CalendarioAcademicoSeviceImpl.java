package upeu.edu.pe.mscalendarioacademico.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import upeu.edu.pe.mscalendarioacademico.entity.CalendarioAcademico;
import upeu.edu.pe.mscalendarioacademico.repository.CalendarioAcademicoRepository;
import upeu.edu.pe.mscalendarioacademico.service.CalendarioAcademicoService;

import java.util.List;

@Service
public class CalendarioAcademicoSeviceImpl implements CalendarioAcademicoService {
    @Autowired
    private CalendarioAcademicoRepository calendarioAcademicoRepository;

    @Override
    public CalendarioAcademico guardarCalendarioAcademico(CalendarioAcademico CalendarioAcademico) {
        return calendarioAcademicoRepository.save(CalendarioAcademico);
    }

    @Override
    public List<CalendarioAcademico> listarCalendarioAcademico(){
        return calendarioAcademicoRepository.findAll();
    }

    @Override
    public CalendarioAcademico buscarCalendarioAcademicoPorId(Long id){
        return calendarioAcademicoRepository.findById(id).get();
    }

    @Override
    public CalendarioAcademico editarCalendarioAcademico(CalendarioAcademico CalendarioAcademico) {
        return calendarioAcademicoRepository.save(CalendarioAcademico);
    }

    @Override
    public void eliminarCalendarioAcademico(Long id){
        calendarioAcademicoRepository.deleteById(id);
    }

}
