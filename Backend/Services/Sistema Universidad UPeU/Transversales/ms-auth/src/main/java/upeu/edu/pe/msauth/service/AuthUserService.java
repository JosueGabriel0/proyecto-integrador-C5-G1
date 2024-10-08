package upeu.edu.pe.msauth.service;
import upeu.edu.pe.msauth.dto.Usuario;
import upeu.edu.pe.msauth.entity.TokenDto;


public interface AuthUserService {
    // Método login ahora utiliza la clase Usuario en lugar de AuthUserDto
    public TokenDto login(Usuario usuarioLogin);

    // El método validate() se mantiene igual ya que sigue validando el token JWT
    public TokenDto validate(String token);
}
