package upeu.edu.pe.msinscripciones.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
public class TokenDto {
    private String accessToken;  // Token de acceso
    private String refreshToken; // Token de refresco
}