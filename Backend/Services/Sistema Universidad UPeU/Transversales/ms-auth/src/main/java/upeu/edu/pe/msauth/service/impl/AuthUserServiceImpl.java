package upeu.edu.pe.msauth.service.impl;

import org.springframework.http.ResponseEntity;
import upeu.edu.pe.msauth.dto.AuthUserDto;
import upeu.edu.pe.msauth.dto.Usuario;
import upeu.edu.pe.msauth.entity.AuthUser;
import upeu.edu.pe.msauth.entity.TokenDto;

import upeu.edu.pe.msauth.feign.UsuarioFeign;
import upeu.edu.pe.msauth.repository.AuthUserRepository;
import upeu.edu.pe.msauth.security.JwtProvider;
import upeu.edu.pe.msauth.service.AuthUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;


import java.util.Optional;


@Service
public class AuthUserServiceImpl implements AuthUserService {
    @Autowired
    UsuarioFeign usuarioFeign;
    @Autowired
    PasswordEncoder passwordEncoder;
    @Autowired
    JwtProvider jwtProvider;

    @Override
    public TokenDto login(Usuario usuarioLogin) {
        ResponseEntity<Usuario> response = usuarioFeign.listarUsuarioDtoPorId(usuarioLogin.getIdUsuario());

        if (!response.getStatusCode().is2xxSuccessful() || response.getBody() == null)
            return null;

        Usuario usuario = response.getBody();
        if (passwordEncoder.matches(usuarioLogin.getPassword(), usuario.getPassword())) {
            return new TokenDto(jwtProvider.createToken(usuario));
        }
        return null;
    }

    @Override
    public TokenDto validate(String token) {
        if (!jwtProvider.validate(token))
            return null;
        String username = jwtProvider.getUserNameFromToken(token);
        ResponseEntity<Usuario> response = usuarioFeign.listarUsuarioDtoPorId(Long.parseLong(username));

        if (!response.getStatusCode().is2xxSuccessful() || response.getBody() == null)
            return null;

        return new TokenDto(token);
    }
}
