package clinic.exception;

public class DoctorAppointmentAlreadyBookedException extends BusinessRuleException {
    public DoctorAppointmentAlreadyBookedException(String message) {
        super(message);
    }
}
