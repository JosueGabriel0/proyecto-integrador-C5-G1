package upeu.edu.pe.msinscripciones.dto;

import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

@Data
public class PersonaRequest {
    private Persona personaDTO;
    private MultipartFile fotoPerfil;
}