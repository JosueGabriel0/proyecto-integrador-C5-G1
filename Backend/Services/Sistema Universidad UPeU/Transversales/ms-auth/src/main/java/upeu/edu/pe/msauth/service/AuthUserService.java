package upeu.edu.pe.msauth.service;
import upeu.edu.pe.msauth.dto.AuthUserDto;
import upeu.edu.pe.msauth.entity.AuthUser;
import upeu.edu.pe.msauth.entity.TokenDto;


public interface AuthUserService {
    public AuthUser save(AuthUserDto authUserDto);


    public TokenDto login(AuthUserDto authUserDto);


    public TokenDto validate(String token);
}
