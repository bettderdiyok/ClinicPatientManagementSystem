package clinic.exception;

public class DayOffNotFoundException extends RuntimeException{
    public DayOffNotFoundException(String message) {
        super(message);
    }
}
