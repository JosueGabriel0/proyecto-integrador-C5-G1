package upeu.edu.pe.msinscripciones.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import upeu.edu.pe.msinscripciones.dto.TokenDto;
import upeu.edu.pe.msinscripciones.dto.Usuario;
import upeu.edu.pe.msinscripciones.feign.AuthFeignClient;
import upeu.edu.pe.msinscripciones.service.TokenService;

@Service
public class TokenServiceImpl implements TokenService {

    @Autowired
    private AuthFeignClient authFeignClient;

    @Override
    public String obtenerTokenJwt() {
        // Crear un objeto Usuario con las credenciales de inicio de sesión
        Usuario usuarioLogin = new Usuario();
        usuarioLogin.setUsername("josuexd"); // Cambia esto a tu nombre de usuario real
        usuarioLogin.setPassword("12345");     // Cambia esto a tu contraseña real

        // Llamar al endpoint de login para obtener el token JWT
        ResponseEntity<TokenDto> response = authFeignClient.login(usuarioLogin);

        if (response.getStatusCode().is2xxSuccessful() && response.getBody() != null) {
            // Retorna el access token obtenido
            return response.getBody().getAccessToken();
        } else {
            throw new RuntimeException("No se pudo obtener el token JWT");
        }
    }
}
