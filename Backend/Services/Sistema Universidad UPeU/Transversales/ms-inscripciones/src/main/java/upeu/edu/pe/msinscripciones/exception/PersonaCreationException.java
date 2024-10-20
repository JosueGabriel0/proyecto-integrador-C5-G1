package upeu.edu.pe.msinscripciones.exception;

public class PersonaCreationException extends RuntimeException {
    public PersonaCreationException(String message) {
        super(message);
    }

    public PersonaCreationException(String message, Throwable cause) {
        super(message, cause);
    }
}
