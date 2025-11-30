package clinic.exception;

public class DuplicatePatientException extends BusinessRuleException {
    public DuplicatePatientException(String message) {
        super(message);
    }
}
