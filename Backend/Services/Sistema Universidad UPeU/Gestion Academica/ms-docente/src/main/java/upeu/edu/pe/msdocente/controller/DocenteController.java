package upeu.edu.pe.msdocente.controller;

import feign.FeignException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import upeu.edu.pe.msdocente.dto.Curso;
import upeu.edu.pe.msdocente.dto.Persona;
import upeu.edu.pe.msdocente.entity.Docente;
import upeu.edu.pe.msdocente.feign.CursoFeign;
import upeu.edu.pe.msdocente.feign.PersonaFeign;
import upeu.edu.pe.msdocente.service.DocenteService;

import java.util.List;

@RestController
@RequestMapping("/docente")

public class DocenteController {
    @Autowired
    private DocenteService docenteService;
    @Autowired
    private CursoFeign cursoFeign;
    @Autowired
    private PersonaFeign personaFeign;

    @PostMapping
    public ResponseEntity<?> guardarDocenteResponseEntity(@RequestBody Docente docente){
        try {
            // Verificar si el curso existe
            ResponseEntity<Persona> personaResponse = personaFeign.listarPersonaDtoPorId(docente.getIdPersona());
            if (personaResponse.getStatusCode() == HttpStatus.NOT_FOUND || personaResponse.getBody() == null) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body("No existe la persona");
            }
            Persona persona = personaResponse.getBody();

            // Verificar si el curso existe
            ResponseEntity<Curso> cursoResponse = cursoFeign.listarCursoDtoPorId(docente.getCursoId());
            if (cursoResponse.getStatusCode() == HttpStatus.NOT_FOUND || cursoResponse.getBody() == null) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body("No existe el curso");
            }
                Curso curso = cursoResponse.getBody();



            // Verificar si cada producto en los detalles existe antes de procesarlos
            /*
            for (PedidoDetalle pedidoDetalle : pedido.getDetalle()) {
                ResponseEntity<Producto> productoResponse = productoFeign.listarProductoDtoPorId(pedidoDetalle.getProductoId());
                if (productoResponse.getStatusCode() == HttpStatus.NOT_FOUND || productoResponse.getBody() == null) {
                    return ResponseEntity.status(HttpStatus.NOT_FOUND).body("No existe el producto");
                }
                    Producto producto = productoResponse.getBody();
                    // Asignar el producto al detalle
                    pedidoDetalle.setProducto(producto);

            }

            // Asignar los detalles actualizados
            pedido.setDetalle(pedido.getDetalle());
            */

            // Asignar el curso al docente
            docente.setPersona(persona);
            // Asignar el curso al docente
            docente.setCurso(curso);

            // Guardar el pedido si todas las validaciones pasaron
            Docente docenteGuardado = docenteService.guardarDocente(docente);

            // Retornar respuesta exitosa
            return ResponseEntity.status(HttpStatus.CREATED).body(docenteGuardado);

        } catch (FeignException e) {
            // Imprimir los detalles del error que Feign está arrojando
            String errorMensaje = "Error al comunicarse con otro servicio: " + e.getMessage();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(errorMensaje);

        } catch (Exception e) {
            // Manejo de cualquier otro error inesperado
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error interno del servidor: " + e.getMessage());
        }
    }

    @GetMapping
    public ResponseEntity<List<Docente>> listarDocentesResponseEntity(){
        return ResponseEntity.ok(docenteService.listarDocente());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Docente> buscarDocentePorIdResponseEntity(@PathVariable( required = true) Long id){
        return ResponseEntity.ok(docenteService.buscarDocentePorId(id));
    }

    @PutMapping("/{id}")
    public ResponseEntity<Docente> editarDocenteResponseEntity(@PathVariable( required = true) Long id, @RequestBody Docente docente){
        docente.setIdDocente(id);
        return ResponseEntity.ok(docenteService.editarDocente(docente));
    }

    @DeleteMapping
    public String eliminarDocenteResponseEntity(@PathVariable( required = true) Long id){
        docenteService.eliminarDocente(id);
        return "Docente eliminado";
    }
}
