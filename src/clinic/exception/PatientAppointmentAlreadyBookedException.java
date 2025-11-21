package clinic.exception;

public class PatientAppointmentAlreadyBookedException extends RuntimeException {
    public PatientAppointmentAlreadyBookedException(String message) {
        super(message);
    }
}
