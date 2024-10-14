package upeu.edu.pe.msauth.security;

import io.jsonwebtoken.*;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import upeu.edu.pe.msauth.dto.Rol;
import upeu.edu.pe.msauth.dto.Usuario;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import upeu.edu.pe.msauth.feign.RolFeign;

import java.util.Base64;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Component
public class JwtProvider {

    @Autowired
    private RolFeign rolFeign;

    @Value("${jwt.secret}")
    private String secret;

    @PostConstruct
    protected void init() {
        secret = Base64.getEncoder().encodeToString(secret.getBytes());
    }

    public String createToken(Usuario usuario) {
        Map<String, Object> claims = new HashMap<>();
        claims = Jwts.claims().setSubject(usuario.getUsername());
        claims.put("idUsuario", usuario.getIdUsuario());
        claims.put("email", usuario.getEmail());

        // Obtener el rol desde el microservicio de roles
        ResponseEntity<Rol> response = rolFeign.listarRolDtoPorId(usuario.getRol().getIdRol());
        String nombreRol = "DEFAULT_ROLE"; // Valor por defecto en caso de que no se encuentre el rol

        if (response.getStatusCode().is2xxSuccessful() && response.getBody() != null) {
            nombreRol = response.getBody().getNombreRol(); // Obtiene el nombre del rol
        }

        claims.put("nombreRol", nombreRol); // Incluye el nombre del rol en el token

        Date now = new Date();
        Date exp = new Date(now.getTime() + 3600000); // El token expira en 1 hora

        return Jwts.builder()
                .setClaims(claims)
                .setIssuedAt(now)
                .setExpiration(exp)
                .signWith(SignatureAlgorithm.HS256, secret)
                .compact();
    }

    public boolean validate(String token) {
        try {
            Jwts.parser().setSigningKey(secret).parseClaimsJws(token);
            return true;
        } catch (ExpiredJwtException e) {
            // Manejar token expirado
            System.out.println("El token ha expirado");
        } catch (UnsupportedJwtException e) {
            // Manejar token no soportado
            System.out.println("El token no es compatible");
        } catch (MalformedJwtException e) {
            // Manejar token mal formado
            System.out.println("El token está mal formado");
        } catch (SignatureException e) {
            // Manejar firma inválida
            System.out.println("La firma del token no es válida");
        } catch (IllegalArgumentException e) {
            // Manejar argumento ilegal
            System.out.println("El token es nulo o vacío");
        }
        return false;
    }

    public String getUserNameFromToken(String token) {
        try {
            return Jwts.parser().setSigningKey(secret).parseClaimsJws(token).getBody().getSubject();
        } catch (Exception e) {
            return "bad token";
        }
    }

    public String createRefreshToken(Usuario usuario) {
        Map<String, Object> claims = new HashMap<>();
        claims = Jwts.claims().setSubject(usuario.getUsername());
        claims.put("idUsuario", usuario.getIdUsuario());
        claims.put("email", usuario.getEmail());
        claims.put("roles", usuario.getRol());

        Date now = new Date();
        Date exp = new Date(now.getTime() + 604800000); // Expira en 7 días (7 * 24 * 60 * 60 * 1000 ms)

        return Jwts.builder()
                .setClaims(claims)
                .setIssuedAt(now)
                .setExpiration(exp)
                .signWith(SignatureAlgorithm.HS256, secret)
                .compact();
    }
}