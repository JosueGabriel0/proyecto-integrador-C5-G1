package upeu.edu.pe.msmaterialeseducativos.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import upeu.edu.pe.msmaterialeseducativos.entity.MaterialesEducativos;
import upeu.edu.pe.msmaterialeseducativos.service.MaterialesEducativosService;

import java.util.List;

@RestController
@RequestMapping("/materialesEducativos")
public class MaterialesEducativosController {
    @Autowired
    private MaterialesEducativosService materialesEducativosService;

    @PostMapping
    public ResponseEntity<MaterialesEducativos> guardarMaterialesEducativosResponseEntity(@RequestBody MaterialesEducativos MaterialesEducativos){
        return ResponseEntity.ok(materialesEducativosService.guardarMaterialesEducativos(MaterialesEducativos));
    }

    @GetMapping
    public ResponseEntity<List<MaterialesEducativos>> listarMaterialesEducativosResponseEntity(){
        return ResponseEntity.ok(materialesEducativosService.listarMaterialesEducativos());
    }

    @GetMapping("/{id}")
    public ResponseEntity<MaterialesEducativos> buscarMaterialesEducativosPorIdResponseEntity(@PathVariable(required = true) Long id){
        return ResponseEntity.ok(materialesEducativosService.buscarMaterialesEducativosPorId(id));
    }

    @PutMapping("/{id}")
    public ResponseEntity<MaterialesEducativos> editarMaterialesEducativosResponseEntity(@PathVariable (required = true) Long id,@RequestBody MaterialesEducativos MaterialesEducativos){
        MaterialesEducativos.setId(id);
        return ResponseEntity.ok(materialesEducativosService.editarMaterialesEducativos(MaterialesEducativos));
    }

    @DeleteMapping("/{id}")
    public String eliminarMaterialesEducativosResponseEntity(@PathVariable Long id){
        materialesEducativosService.eliminarMaterialesEducativos(id);
        return "MaterialesEducativos eliminada";
    }
}
