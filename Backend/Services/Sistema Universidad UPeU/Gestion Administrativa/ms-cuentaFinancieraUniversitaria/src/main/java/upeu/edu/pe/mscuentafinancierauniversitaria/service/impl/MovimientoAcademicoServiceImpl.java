package upeu.edu.pe.mscuentafinancierauniversitaria.service.impl;

import feign.FeignException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import upeu.edu.pe.mscuentafinancierauniversitaria.entity.MovimientoAcademico;
import upeu.edu.pe.mscuentafinancierauniversitaria.exception.ResourceNotFoundException;
import upeu.edu.pe.mscuentafinancierauniversitaria.feign.PagoFeign;
import upeu.edu.pe.mscuentafinancierauniversitaria.repository.MovimientoAcademicoRepository;
import upeu.edu.pe.mscuentafinancierauniversitaria.service.MovimientoAcademicoService;

import java.util.List;

@Service
public class MovimientoAcademicoServiceImpl implements MovimientoAcademicoService {

    @Autowired
    private MovimientoAcademicoRepository movimientoAcademicoRepository;

    @Autowired
    private PagoFeign pagoFeign;

    @Override
    public List<MovimientoAcademico> listarTodos() {
        List<MovimientoAcademico> movimientosAcademicos = movimientoAcademicoRepository.findAll();

        movimientosAcademicos.forEach(movimientoAcademico -> {
            try {
                ResponseEntity<Pago> pagoResponse = pagoFeign.listarPagoDtoPorId(movimientoAcademico.getIdPago());
                if(pagoResponse.getBody() == null){
                    throw new ResourceNotFoundException("Pago con ID "+ movimientoAcademico.getIdPago() + " no existe");
                }
                movimientoAcademico.setPago(pagoResponse.getBody());
            } catch (FeignException e){
                throw new RuntimeException("Error al obtener el pago con ID " + movimientoAcademico.getIdPago() + e);
            }
        });

        return movimientosAcademicos;
    }

    @Override
    public MovimientoAcademico obtenerPorId(Long id) {
        MovimientoAcademico movimientoAcademico = movimientoAcademicoRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Movimiento Academico con ID " + id + " no existe"));

        try {
            ResponseEntity<Pago> pagoResponse = pagoFeign.listarPagoDtoPorId(movimientoAcademico.getIdPago());
            if(pagoResponse.getBody() == null){
                throw new ResourceNotFoundException("Pago con ID "+ movimientoAcademico.getIdPago() + " no existe");
            }
            movimientoAcademico.setPago(pagoResponse.getBody());
        } catch (FeignException e){
            throw new RuntimeException("Error al obtener el pago con ID " + movimientoAcademico.getIdPago() + e);
        }

        return movimientoAcademico;
    }

    @Override
    public MovimientoAcademico crear(MovimientoAcademico movimientoAcademico) {
        return movimientoAcademicoRepository.save(movimientoAcademico);
    }

    @Override
    public MovimientoAcademico actualizar(MovimientoAcademico movimientoAcademico) {
        return movimientoAcademicoRepository.save(movimientoAcademico);
    }

    @Override
    public void eliminar(Long id) {
        movimientoAcademicoRepository.deleteById(id);
    }
}
