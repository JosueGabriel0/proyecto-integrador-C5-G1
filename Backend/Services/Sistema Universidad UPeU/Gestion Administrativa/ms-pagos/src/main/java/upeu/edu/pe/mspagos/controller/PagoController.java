package upeu.edu.pe.mspagos.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import upeu.edu.pe.mspagos.entity.*;
import upeu.edu.pe.mspagos.service.PagoService;
import upeu.edu.pe.mspagos.service.PdfService;

import java.util.List;

@RestController
@RequestMapping("/pago")
public class PagoController {
    @Autowired
    private PagoService pagoService;

    @Autowired
    private PdfService pdfService;

    @PostMapping("/boleta")
    public String crearPagoConBoleta(@RequestBody PagoBoletaRequest pagoBoletaRequest) {
        try {
            pagoService.crearPagoConBoleta(pagoBoletaRequest);
            return "Pago con Boleta creada y PDF generado correctamente.";
        } catch (Exception e) {
            e.printStackTrace();
            return "Error al generar el pago con boleta o el PDF.";
        }
    }

    @PostMapping("/factura")
    public String crearPagoConFactura(@RequestBody PagoFacturaRequest pagoFacturaRequest) {
        try {
            pagoService.crearPagoConFactura(pagoFacturaRequest);
            return "Pago con Factura creada y PDF generado correctamente.";
        } catch (Exception e) {
            e.printStackTrace();
            return "Error al generar el pago con la factura o el PDF.";
        }
    }

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
