package upeu.edu.pe.mscuentafinancierauniversitaria.exception;

public class ResourceNotFoundException extends RuntimeException { // Extender de RuntimeException

    public ResourceNotFoundException(String message) {
        super(message); // Llamar al constructor de RuntimeException
    }
}