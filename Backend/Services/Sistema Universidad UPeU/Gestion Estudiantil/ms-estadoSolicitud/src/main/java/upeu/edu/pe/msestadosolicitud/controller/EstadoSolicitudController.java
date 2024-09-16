package upeu.edu.pe.msestadosolicitud.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import upeu.edu.pe.msestadosolicitud.entity.EstadoSolicitud;
import upeu.edu.pe.msestadosolicitud.service.EstadoSolicitudService;

import java.util.List;

@RestController("/estadoSolicitud")
@RequestMapping
public class EstadoSolicitudController {
    @Autowired
    private EstadoSolicitudService estadoSolicitudService;

    @PostMapping
    public ResponseEntity<EstadoSolicitud> guardarEstadoSolicitudResponseEntity(@RequestBody EstadoSolicitud estadoSolicitud) {
        return ResponseEntity.status(HttpStatus.CREATED).body(estadoSolicitudService.guardarEstadoSolicitud(estadoSolicitud));
    }
    @GetMapping
    public ResponseEntity<List<EstadoSolicitud>> listarEstadoSolicitudsResponseEntity() {
        return ResponseEntity.status(HttpStatus.OK).body(estadoSolicitudService.listarEstadoSolicituds());
    }
    @GetMapping("/{id}")
    public ResponseEntity<EstadoSolicitud> listarEstadoSolicituds(@PathVariable(required = true) long id) {
        return ResponseEntity.status(HttpStatus.OK).body(estadoSolicitudService.buscarEstadoSolicitudPorId(id));
    }
    @PutMapping("/{id}")
    public ResponseEntity<EstadoSolicitud> editarEstadoSolicitud(@PathVariable(required = true) long id, @RequestBody EstadoSolicitud
            EstadoSolicitud) {
        EstadoSolicitud.setIdEstadoSolicitud(id);
        return ResponseEntity.status(HttpStatus.OK).body(estadoSolicitudService.editarEstadoSolicitud(EstadoSolicitud));
    }
    @DeleteMapping("/{id}")
    public String eliminarEstadoSolicitud(@PathVariable(required = true) long id) {
        estadoSolicitudService.eliminarEstadoSolicitud(id);
        return "EstadoSolicitud eliminado";
    }

}
