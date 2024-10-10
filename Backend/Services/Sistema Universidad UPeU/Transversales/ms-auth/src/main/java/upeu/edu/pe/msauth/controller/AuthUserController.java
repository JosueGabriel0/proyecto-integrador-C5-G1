package upeu.edu.pe.msauth.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import upeu.edu.pe.msauth.dto.Usuario;
import upeu.edu.pe.msauth.entity.TokenDto;
import upeu.edu.pe.msauth.feign.UsuarioFeign;
import upeu.edu.pe.msauth.security.JwtProvider;
import upeu.edu.pe.msauth.service.AuthUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
@Tag(name = "Auth", description = "Endpoints para autenticación de usuarios")
public class AuthUserController {
    @Autowired
    AuthUserService authUserService;
    @Autowired
    private JwtProvider jwtProvider;
    @Autowired
    private UsuarioFeign usuarioFeign;

    @PostMapping("/login")
    @Operation(summary = "Iniciar sesión", description = "Permite a los usuarios iniciar sesión con sus credenciales.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Inicio de sesión exitoso"),
            @ApiResponse(responseCode = "400", description = "Credenciales inválidas"),
            @ApiResponse(responseCode = "500", description = "Error interno del servidor")
    })
    public ResponseEntity<TokenDto> login(@Parameter(description = "Credenciales del usuario") @RequestBody Usuario usuarioLogin) {
        TokenDto tokenDto = authUserService.login(usuarioLogin);
        if (tokenDto == null) {
            return ResponseEntity.badRequest().build();
        }
        return ResponseEntity.ok(tokenDto);
    }

    @PostMapping("/refresh")
    public ResponseEntity<TokenDto> refresh(@RequestParam String refreshToken) {
        if (!jwtProvider.validate(refreshToken)) {
            return ResponseEntity.badRequest().build(); // Token de refresco inválido
        }

        String username = jwtProvider.getUserNameFromToken(refreshToken);
        ResponseEntity<Usuario> response = usuarioFeign.buscarUsuarioPorUsername(username);

        if (!response.getStatusCode().is2xxSuccessful() || response.getBody() == null) {
            return ResponseEntity.badRequest().build(); // Usuario no encontrado o error
        }

        Usuario usuario = response.getBody();
        String newAccessToken = jwtProvider.createToken(usuario);
        return ResponseEntity.ok(TokenDto.builder()
                .accessToken(newAccessToken)
                .refreshToken(refreshToken) // Puedes devolver el mismo refreshToken o uno nuevo si lo decides
                .build()); // Devuelve el nuevo token de acceso
    }

    @PostMapping("/validate")
    public ResponseEntity<TokenDto> validate(@RequestParam String token) {
        TokenDto tokenDto = authUserService.validate(token);
        if (tokenDto == null)
            return ResponseEntity.badRequest().build();
        return ResponseEntity.ok(tokenDto);
    }
}

