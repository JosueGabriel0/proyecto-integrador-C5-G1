package upeu.edu.pe.msauth.service.impl;

import org.springframework.http.ResponseEntity;
import upeu.edu.pe.msauth.dto.Usuario;
import upeu.edu.pe.msauth.entity.TokenDto;
import upeu.edu.pe.msauth.feign.UsuarioFeign;
import upeu.edu.pe.msauth.security.JwtProvider;
import upeu.edu.pe.msauth.service.AuthUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class AuthUserServiceImpl implements AuthUserService {

    @Autowired
    private UsuarioFeign usuarioFeign;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private JwtProvider jwtProvider;

    @Override
    public TokenDto login(Usuario usuarioLogin) {
        ResponseEntity<Usuario> response = usuarioFeign.buscarUsuarioPorUsername(usuarioLogin.getUsername());

        if (!response.getStatusCode().is2xxSuccessful() || response.getBody() == null) {
            return null; // Usuario no encontrado o error al obtener el usuario
        }

        Usuario usuario = response.getBody();
        if (passwordEncoder.matches(usuarioLogin.getPassword(), usuario.getPassword())) {
            String accessToken = jwtProvider.createToken(usuario);
            String refreshToken = jwtProvider.createRefreshToken(usuario);
            return TokenDto.builder()
                    .accessToken(accessToken)
                    .refreshToken(refreshToken)
                    .build(); // Devuelve ambos tokens
        }
        return null; // Contraseña incorrecta
    }

    @Override
    public TokenDto validate(String token) {
        // Validar el token
        if (!jwtProvider.validate(token)) {
            return null; // Token inválido
        }

        // Obtener el username a partir del token
        String username = jwtProvider.getUserNameFromToken(token);
        ResponseEntity<Usuario> response = usuarioFeign.buscarUsuarioPorUsername(username);

        if (!response.getStatusCode().is2xxSuccessful() || response.getBody() == null) {
            return null; // Usuario no encontrado o error al obtener el usuario
        }

        // Crear el TokenDto usando el builder
        return TokenDto.builder()
                .accessToken(token) // Asigna el token recibido como accessToken
                .refreshToken(null) // O maneja el refreshToken según sea necesario
                .build();
    }
}