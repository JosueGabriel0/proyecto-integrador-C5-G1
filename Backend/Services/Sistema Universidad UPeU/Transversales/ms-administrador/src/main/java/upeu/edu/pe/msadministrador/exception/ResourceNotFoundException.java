package upeu.edu.pe.msadministrador.exception; // Nota: La palabra "exception" está mal escrita como "exeption"

public class ResourceNotFoundException extends RuntimeException { // Extender de RuntimeException

    public ResourceNotFoundException(String message) {
        super(message); // Llamar al constructor de RuntimeException
    }
}