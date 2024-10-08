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
        // Buscar el usuario por su ID
        ResponseEntity<Usuario> response = usuarioFeign.buscarUsuarioPorId(usuarioLogin.getIdUsuario());

        if (!response.getStatusCode().is2xxSuccessful() || response.getBody() == null) {
            return null; // Usuario no encontrado o error al obtener el usuario
        }

        Usuario usuario = response.getBody();
        // Verificar si la contraseña ingresada coincide con la almacenada
        if (passwordEncoder.matches(usuarioLogin.getPassword(), usuario.getPassword())) {
            // Crear un token si la autenticación es exitosa
            return new TokenDto(jwtProvider.createToken(usuario));
        }
        return null; // Contraseña incorrecta
    }

    @Override
    public TokenDto validate(String token) {
        // Validar el token
        if (!jwtProvider.validate(token)) {
            return null; // Token inválido
        }

        // Obtener el ID de usuario a partir del token
        Long userId = Long.parseLong(jwtProvider.getUserNameFromToken(token));
        ResponseEntity<Usuario> response = usuarioFeign.buscarUsuarioPorId(userId);

        if (!response.getStatusCode().is2xxSuccessful() || response.getBody() == null) {
            return null; // Usuario no encontrado o error al obtener el usuario
        }

        return new TokenDto(token); // Token válido
    }
}