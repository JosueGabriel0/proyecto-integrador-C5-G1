package upeu.edu.pe.mspagos.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import upeu.edu.pe.mspagos.entity.Pago;
import upeu.edu.pe.mspagos.service.PagoService;

import java.util.List;

@RestController
@RequestMapping("/pago")
public class PagoController {
    @Autowired
    private PagoService pagoService;

    @PostMapping
    public ResponseEntity<Pago> guardarPagoResponseEntity(@RequestBody Pago pago){
        return ResponseEntity.ok(pagoService.guardarPago(pago));
    }

    @GetMapping
    public ResponseEntity<List<Pago>> listarPagoResponseEntity(){
        return ResponseEntity.ok(pagoService.listarPago());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Pago> buscarPagoPorIdResponseEntity(@PathVariable(required = true) Long id){
        return ResponseEntity.ok(pagoService.buscarPagoPorId(id));
    }

    @PutMapping("/{id}")
    public ResponseEntity<Pago> editarPagoResponseEntity(@PathVariable (required = true) Long id,@RequestBody Pago pago){
        pago.setIdPago(id);
        return ResponseEntity.ok(pagoService.editarPago(pago));
    }

    @DeleteMapping("{id}")
    public ResponseEntity<String> eliminarPago(@PathVariable Long id) {
        try {
            // Lógica para eliminar la Pago
            pagoService.eliminarPago(id);

            // Retornar código 200 OK con mensaje de éxito
            return ResponseEntity.ok("Pago eliminado exitosamente.");
        } catch (Exception e) {
            // En caso de error, retornar un código de error y mensaje apropiado
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Error al eliminar el Pago: " + e.getMessage());
        }
    }
}
