package upeu.edu.pe.mscuentafinancierauniversitaria.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import upeu.edu.pe.mscuentafinancierauniversitaria.entity.CuentaFinanciera;
import upeu.edu.pe.mscuentafinancierauniversitaria.service.CuentaFinancieraService;

import java.util.List;

@RestController
@RequestMapping("/cuentaFinanciera")
public class CuentaFinancieraController {

    @Autowired
    private CuentaFinancieraService cuentaFinancieraService;

    @GetMapping
    public ResponseEntity<List<CuentaFinanciera>> listarTodos() {
        return ResponseEntity.ok(cuentaFinancieraService.listarTodos());
    }

    @GetMapping("/{id}")
    public ResponseEntity<CuentaFinanciera> obtenerPorId(@PathVariable Long id) {
        return ResponseEntity.ok(cuentaFinancieraService.obtenerPorId(id));
    }

    @PostMapping
    public ResponseEntity<CuentaFinanciera> crear(@RequestBody CuentaFinanciera cuentaFinanciera) {
        return ResponseEntity.ok(cuentaFinancieraService.crear(cuentaFinanciera));
    }

    @PutMapping("/{id}")
    public ResponseEntity<CuentaFinanciera> actualizar(@PathVariable Long id, @RequestBody CuentaFinanciera cuentaFinanciera) {
        cuentaFinanciera.setIdCuentaFinanciera(id);
        return ResponseEntity.ok(cuentaFinancieraService.actualizar(cuentaFinanciera));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminar(@PathVariable Long id) {
        cuentaFinancieraService.eliminar(id);
        return ResponseEntity.noContent().build();
    }
}