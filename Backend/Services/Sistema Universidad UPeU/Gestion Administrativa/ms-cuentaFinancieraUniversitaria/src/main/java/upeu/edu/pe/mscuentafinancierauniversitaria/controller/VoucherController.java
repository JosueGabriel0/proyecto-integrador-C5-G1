package upeu.edu.pe.mscuentafinancierauniversitaria.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.FileSystemResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import upeu.edu.pe.mscuentafinancierauniversitaria.entity.Voucher;
import upeu.edu.pe.mscuentafinancierauniversitaria.service.VoucherService;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

@RestController
@RequestMapping("/voucher")
public class VoucherController {

    @Autowired
    private VoucherService voucherService;

    @GetMapping("/images/{nombreImagen}")
    public ResponseEntity<FileSystemResource> getImagen(@PathVariable String nombreImagen) {
        String directorioImagenes = "src/main/resources/static/images";
        File imagen = new File(directorioImagenes, nombreImagen);

        if(imagen.exists()) {
            return ResponseEntity.ok()
                    .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + imagen.getName() + "\"")
                    .body(new FileSystemResource(imagen));
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }

    @PostMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<Voucher> guardarVoucherResponseEntity(@ModelAttribute Voucher voucher, @RequestParam("file") MultipartFile voucherURL) {
        if(!voucherURL.isEmpty()) {
            Path directorioImagenes = Paths.get("src//main//resources//static/images");
            String rutaAbsoluta = directorioImagenes.toFile().getAbsolutePath();
            try {
                byte[] bytesImg = voucherURL.getBytes();
                Path rutaCompleta = Paths.get(rutaAbsoluta + "//" + voucherURL.getOriginalFilename());
                Files.write(rutaCompleta, bytesImg);

                voucher.setVoucherURL(voucherURL.getOriginalFilename());
            }catch (IOException e){
                e.printStackTrace();
            }
        }

        System.out.println("Este es el voucher que se esta guardando mediante el controller y el service: "+voucher);
        Voucher nuevoVoucher = voucherService.crear(voucher);

        return ResponseEntity.ok(nuevoVoucher);
    }

    @GetMapping
    public ResponseEntity<List<Voucher>> listarVouchers() {
        return ResponseEntity.ok(voucherService.listarTodos());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Voucher> listarVouchePorId(@PathVariable Long id) {
        return ResponseEntity.ok(voucherService.obtenerPorId(id));
    }

    @PutMapping("/{id]")
    public ResponseEntity<Voucher> actualizarVoucher(@PathVariable Long id, @RequestBody Voucher voucher) {
        voucher.setIdVoucher(id);
        return ResponseEntity.ok(voucherService.actualizar(voucher));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> borrarVoucher(@PathVariable Long id) {
        voucherService.eliminar(id);
        return ResponseEntity.noContent().build();
    }
}
