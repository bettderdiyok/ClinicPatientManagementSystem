package clinic.exception;

public class DuplicateDoctorException extends BusinessRuleException {
    public DuplicateDoctorException(String message) {
        super(message);
    }
}
