package clinic.exception;

public class InvalidAppointmentTimeException extends BusinessRuleException {
    public InvalidAppointmentTimeException(String message) {
        super(message);
    }
}
