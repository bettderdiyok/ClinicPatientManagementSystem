package clinic.exception;

public class GuardianRequiredException extends RuntimeException {
    public GuardianRequiredException(String message) {
        super(message);
    }
}
