package exception;

public class CouldNotDestroyConnectionPoolException extends ConnectionPoolException {
    public CouldNotDestroyConnectionPoolException(String message) {
        super(message);
    }

    public CouldNotDestroyConnectionPoolException(String message, Throwable cause) {
        super(message, cause);
    }
}
