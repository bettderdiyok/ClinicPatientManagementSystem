package clinic.exception;

public class PatientAppointmentAlreadyBookedException extends BusinessRuleException {
    public PatientAppointmentAlreadyBookedException(String message) {
        super(message);
    }
}
