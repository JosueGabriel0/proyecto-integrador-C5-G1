package upeu.edu.pe.msinscripciones.feign;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import upeu.edu.pe.msinscripciones.dto.TokenDto;
import upeu.edu.pe.msinscripciones.dto.Usuario;

@FeignClient(name = "ms-auth-service", path = "/auth")
public interface AuthFeignClient {

    @PostMapping("/login")
    ResponseEntity<TokenDto> login(@RequestBody Usuario usuarioLogin);
}
