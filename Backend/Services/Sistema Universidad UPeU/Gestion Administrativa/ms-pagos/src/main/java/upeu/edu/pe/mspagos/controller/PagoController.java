package upeu.edu.pe.mspagos.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import upeu.edu.pe.mspagos.entity.Boleta;
import upeu.edu.pe.mspagos.entity.Factura;
import upeu.edu.pe.mspagos.entity.Pago;
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
    public String crearPagoConBoleta(@RequestBody Boleta boleta) {
        try {
            pagoService.crearPagoConBoleta(boleta);
            pdfService.generarPdfBoleta(boleta);
            return "Boleta creada y PDF generado correctamente.";
        } catch (Exception e) {
            e.printStackTrace();
            return "Error al generar la boleta o el PDF.";
        }
    }

    @PostMapping("/factura")
    public String crearPagoConFactura(@RequestBody Factura factura) {
        try {
            pagoService.crearPagoConFactura(factura);
            pdfService.generarPdfFactura(factura);
            return "Factura creada y PDF generado correctamente.";
        } catch (Exception e) {
            e.printStackTrace();
            return "Error al generar la factura o el PDF.";
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
