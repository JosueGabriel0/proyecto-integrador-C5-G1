package upeu.edu.pe.msinscripciones.exception;

public class PersonaUpdateException extends RuntimeException {

    public PersonaUpdateException(String message) {
        super(message);
    }

    public PersonaUpdateException(String message, Throwable cause) {
        super(message, cause);
    }
}
