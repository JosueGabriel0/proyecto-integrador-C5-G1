package upeu.edu.pe.msauth.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import upeu.edu.pe.msauth.dto.Usuario;
import upeu.edu.pe.msauth.entity.TokenDto;
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

    @PostMapping("/login")
    @Operation(summary = "Iniciar sesión", description = "Permite a los usuarios iniciar sesión con sus credenciales.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Inicio de sesión exitoso"),
            @ApiResponse(responseCode = "400", description = "Credenciales inválidas"),
            @ApiResponse(responseCode = "500", description = "Error interno del servidor")
    })
    public ResponseEntity<TokenDto> login(@Parameter(description = "Credenciales del usuario") @RequestBody Usuario usuarioLogin) {
        TokenDto tokenDto = authUserService.login(usuarioLogin);
        if (tokenDto == null)
            return ResponseEntity.badRequest().build();
        return ResponseEntity.ok(tokenDto);
    }

    @PostMapping("/validate")
    public ResponseEntity<TokenDto> validate(@RequestParam String token) {
        TokenDto tokenDto = authUserService.validate(token);
        if (tokenDto == null)
            return ResponseEntity.badRequest().build();
        return ResponseEntity.ok(tokenDto);
    }
}

