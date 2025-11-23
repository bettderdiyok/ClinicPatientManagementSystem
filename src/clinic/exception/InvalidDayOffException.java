package clinic.exception;

public class InvalidDayOffException extends RuntimeException{
    public InvalidDayOffException(String message) {
        super(message);
    }
}
